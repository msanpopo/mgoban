/*
 *  mGoban - GUI for Go
 *  Copyright (C) 2007, 2009, 2010  sanpo
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package gtp;

import app.sgf.GtpTerminal;
import go.gui.InputListener;
import go.GoColor;
import go.GoMove;

import go.GoVertex;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * GTP エンジンをあらわす
 * <pre>
 * 外部から GTP エンジンへの操作は全部このクラスのオブジェクトに対して行う。
 * </pre>
 */
public class GtpEngine implements InputListener{
    private String text;    // 例:'GNU Go - Linux'
    private String command; // 例:'gnugo --mode gtp'
    private String workDir;
    
    private String name;    // gtp コマンドの name と version で作る
    private String version;
    
    private int boardsize;
    
    private boolean useId;
    private int id;
    
    private GtpInputThread inThread;
    private GtpErrorThread errThread;
    
    private GtpReceiver receiver;
    private GtpTerminal term;
    
    private ArrayList<GtpMessage> listGtpMessage;
    
    private Process process;
    private BufferedReader in;
    private OutputStreamWriter out;
    //private PrintWriter out;
    private BufferedReader err;
    
    public GtpEngine(){
        init("new engine", "", "");
    }
    
    public GtpEngine(GtpEngine gtp){
        init(gtp.text, gtp.command, gtp.workDir);
    }
    
    public GtpEngine(String text, String command, String workDir){
        init(text, command, workDir);
    }
    
    public void init(String text, String command, String workDir){
        this.text = text;
        this.command = command;
        this.workDir = workDir;
        
        name = "";
        version = "";
        
        boardsize = 19;
        
        useId = false;
        id = 0;
        inThread = null;
        errThread = null;
        
        receiver = null;
        term = null;
        
        listGtpMessage = new ArrayList<GtpMessage>();
    }
    
    @Override
    public String toString(){
        return text;
    }
    
    public String getText(){
        return text;
    }
    
    public void setText(String text){
        this.text = text;
    }
    
    public String getCommand(){
        return command;
    }
    
    public void setCommand(String command){
        this.command = command;
    }
    
    public String getDirectory(){
        return workDir;
    }
    
    public void setDirectory(String workDir){
        this.workDir = workDir;
    }
    
    public void write(PrintWriter out) {
        if(workDir == null){
            out.println(text + "," + command + ",");
        }else{
            out.println(text + "," + command + "," + workDir);
        }
    }
    
    public String getName(){
        return name + " - " + version;
    }
    
    public void setReceiver(GtpReceiver r){
        receiver = r;
    }
    
    public void setTerminal(GtpTerminal term){
        this.term = term;
    }
    
    public void textInputed(Object source, String text) {
        debugCommand(text);
    }
    
    public int getBoardsize(){
        return boardsize;
    }
    
    public boolean connect(){
        Runtime runtime = Runtime.getRuntime();
        
        try{
            File dir = null;
            if(workDir.isEmpty() == false){
                dir = new File(workDir);
            }
            process = runtime.exec(command, null, dir);
            InputStream i = process.getInputStream();
            OutputStream o = process.getOutputStream();
            InputStream e = process.getErrorStream();
            in = new BufferedReader(new InputStreamReader(i, "US-ASCII"));
            out = new OutputStreamWriter(o);
            //out = new PrintWriter(o);
            err = new BufferedReader(new InputStreamReader(e));
        }catch (Exception e){
            System.err.println("err:GtpEngine:" + e);
            return false;
        }
        
        inThread = new GtpInputThread(in, this);
        inThread.start();
        
        errThread = new GtpErrorThread(err, this);
        errThread.start();
        
        return true;
    }
    
    public void initNameVersion(){
        name = getMessageSync(GtpCommand.NAME, "").getMessage();
        version = getMessageSync(GtpCommand.VERSION, "").getMessage();
        
        System.out.println("name:" + name + " version:" + version);
    }
    
    public GtpMessage getMessageSync(GtpCommand gtpCommand, String arg){
        ArrayBlockingQueue<GtpResponse> queue = new ArrayBlockingQueue<GtpResponse>(10);

        String commandStr = buildCommand(gtpCommand.toString(), arg);

        GtpMessage msg = new GtpMessage(gtpCommand, commandStr);
        GtpResponse response = null;
        
        inThread.setQueue(queue);
        {
            send(gtpCommand, arg);

            try {
                response = queue.take();
                
            } catch (InterruptedException ex) {
                ex.printStackTrace();
                response = new GtpResponse(ex.toString(), true);
            }
        }
        inThread.setQueue(null);
        
        msg.setResponse(response);
        
        return msg;
    }
    
    private boolean isClosed(){
        if(process == null){
            return true;
        }else{
            return false;
        }
    }
    
    public void close(){
        if(process != null){
            quit();
        }
        
        try {
            inThread.join();
            errThread.join();
        } catch (InterruptedException e) {
            System.err.println("GtpEngine.close(): join fail" + e);
            e.printStackTrace();
        }
        
        try{
            in.close();
            out.close();
            err.close();
        }catch(IOException e){
            System.err.println("err:GtpEngine.close():" + e);
        }
        
        if(term != null){
            term.closed(this);
            term = null;
        }
    }
    
    public void quit(){
        if(isClosed()){
            return;
        }
        System.out.println("GtpEngine.quit");
        
        send(GtpCommand.QUIT, null);
        
        try {
            process.waitFor();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        process = null;
        System.out.println("GtpEngine.quit:finish");
    }
    
    public void name(){
        send(GtpCommand.NAME, null);
    }
    
    public void version(){
        send(GtpCommand.VERSION, null);
    }
    
    public void setBoardsize(int size){
        boardsize = size;
        send(GtpCommand.BOARDSIZE, Integer.toString(size));
    }
    
    public void setBoardsizeSync(int size){
        boardsize = size;
        getMessageSync(GtpCommand.BOARDSIZE, Integer.toString(size));
    }
    
    public void clearBoard(){
        send(GtpCommand.CLEAR_BOARD, null);
    }
    
    public void setFreeHandicap(Collection<GoVertex> vertexList){
        if(vertexList != null && vertexList.isEmpty() == false){
            StringBuilder str = new StringBuilder();
            for(GoVertex v : vertexList){
                str.append(v.toGtpString(boardsize)).append(" ");
            }
            send(GtpCommand.SET_FREE_HANDICAP, str.toString());
        }
    }
    
    public void setHandicapSync(int handicap){
        getMessageSync(GtpCommand.FIXED_HANDICAP, Integer.toString(handicap));
    }
    
    public void setKomi(double komi){
        send(GtpCommand.KOMI, Double.toString(komi));
    }
    
    public void setKomiSync(double komi){
        getMessageSync(GtpCommand.KOMI, Double.toString(komi));
    }
    
    public void play(GoMove move){
        send(GtpCommand.PLAY, move.toGtpString(boardsize));
    }
    
    public void genMove(GoColor color){
        send(GtpCommand.GENMOVE, color.toGtpString());
    }
    
    public void undo(){
        send(GtpCommand.UNDO, null);
    }
    
    public void showBoard(){
        send(GtpCommand.SHOWBOARD, null);
    }
    
    public void finalStatusList(String status){
        send(GtpCommand.FINAL_STATUS_LIST, status);
    }
    
    public void finalScore(){
        send(GtpCommand.FINAL_SCORE, null);
    }
    
    public void timeSettings(int mainTime, int byoYomiTime, int byoYomiStones){
        StringBuilder str = new StringBuilder();
        str.append(mainTime).append(" ").append(byoYomiTime).append(" ").append(byoYomiStones);
        send(GtpCommand.TIME_SETTINGS, str.toString());
    }
    
    public void timeLeft(GoColor color, int time, int stones){
        StringBuilder str = new StringBuilder();
        str.append(color.toGtpString()).append(" ").append(time).append(" ").append(stones);
        send(GtpCommand.TIME_LEFT, str.toString());
    }
    
    public void debugCommand(String command){
        System.out.println("GtpEngine:debugCommand : " + command);
        sendReal(GtpCommand.UNKNOWN, command);
    }
    
    private void send(GtpCommand gtpCommand, String arg){
        String commandStr = buildCommand(gtpCommand.toString(), arg);
        sendReal(gtpCommand, commandStr);
    }
    
    private String buildCommand(String command, String arg){
        StringBuilder str = new StringBuilder();
        
        if(command.isEmpty() == false){
            str.append(command);
        }
        
        if(arg != null && arg.isEmpty() == false){
            str.append(" ");
            str.append(arg);
        }
        
        return str.toString();
    }
    
    private void sendReal(GtpCommand gtpCommand, String commandStr){
        if(isClosed()){
            System.out.println("GtpEngine.send: already closed");
            return;
        }
        
        id += 1;
        
        StringBuilder str = new StringBuilder();
        if(useId){
            str.append(id);
            str.append(" ");
        }
        str.append(commandStr);

        if(term != null){
            term.append(this, "<" + str.toString());
        }
        
        GtpMessage msg = new GtpMessage(gtpCommand, str.toString(), id);
        if(msg.commandIsEmpty()){
            System.out.println("GtpEngine.send: command is empty");
        }else{
            listGtpMessage.add(msg);
        }
        
//        System.out.println("GtpEngien.send:" + str.toString() + ":");
//        showMessageList();
        
        char LF = 10;
        str.append(LF);
        
        try {
            out.write(str.toString());
            out.flush();
        } catch (IOException ex) {
            System.err.println("err:GtpEngine.send():" + ex);
            //ex.printStackTrace();
        }
        
        // MoGo に連続してコマンドを送ると返事がかえってこない時があるので sleep 入れる
        try {
            Thread.sleep(10);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    
    private void showMessageList(){
        System.out.println("MessageList ##########");
        for(GtpMessage msg : listGtpMessage){
            System.out.println(msg.toString());
        }
        System.out.println("######################");
    }
    
    public void receiveInput(GtpResponse response) {
        if(term != null){
            term.append(this, ">" + response.toString());
        }
        
        //System.out.println("GtpEngine.recieveInput");
        //showMessageList();
        
        if(response.isError()){
            close();
        }
        
        if(listGtpMessage.size() == 0){
            System.err.println("error:GtpEngine.receiveInput(): no command found : ");
            return;
        }
        GtpMessage msg = listGtpMessage.remove(0);
        msg.setResponse(response);
        
        //System.out.println("GtpEngine.receiveInput:" + msg.toString() + ":" + response.toString());
        
        receiver.receive(this, msg);
    }
    
    public void receiveError(GtpResponse response) {
        if(term != null){
            term.appendError(this, response.getResponse());
        }
        
        if(response.isError()){
            close();
        }
    }
}