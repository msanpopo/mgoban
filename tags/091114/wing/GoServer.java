/*
 *  mGoban - GUI for Go
 *  Copyright (C) 2007, 2009  sanpo
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

package wing;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

public class GoServer {
    private static final int MOVE = 0, MOVE_TIME = 1, MOVE_N_TIME = 2;
    
    private String description;
    private String hostname;
    private int port;
    private String defaultLoginName;
    private String defaultPassword;
    private int moveStyle;
    private String encoding;
    
    private String loginName;	// 実際にログインしている名前。ゲストの時には正しくない。
    
    private Socket socket;
    private PrintWriter out;
    private WingReader in;
    
    private ReceiveThread receiveThread;
    private ReceiverList receiverList;
    
    public GoServer(){					// new
        description = "new server";
        hostname = "";
        defaultLoginName = "";
        defaultPassword = "";
        port = 0;
        moveStyle = MOVE;
        encoding = System.getProperty("file.encoding");
        
        loginName = "";
        
        socket = null;
        out = null;
        in = null;
        receiveThread = null;
        receiverList = null;
    }
    
    public GoServer(GoServer gs) {		// copy
        description = gs.description + "_copy";
        hostname = gs.hostname;
        port = gs.port;
        defaultLoginName = gs.defaultLoginName;
        defaultPassword = gs.defaultPassword;
        moveStyle = gs.moveStyle;
        encoding = gs.encoding;
        
        loginName = "";
        
        socket = null;
        out = null;
        in = null;
        receiveThread = null;
        receiverList = null;
    }
    
    public GoServer(String line) {
        String[] a = line.split(",");
        
        if(a.length != 7){
            System.err.println("WingConnection:server.cfg parse error" + line);
            return;
        }
        description = a[0];
        hostname = a[1];
        port = Integer.parseInt(a[2]);
        defaultLoginName = a[3];
        defaultPassword = a[4];
        moveStyle = Integer.parseInt(a[5]);
        encoding = a[6];
        
        loginName = "";
        
        socket = null;
        out = null;
        in = null;
        receiveThread = null;
        receiverList = null;
    }
    
    public String getName(){
        return description;
    }
    
    public void setName(String newName){
        description = newName;
    }
    
    public String getHostName(){
        return hostname;
    }
    
    public void setHostName(String newName){
        hostname = newName;
    }
    
    public int getPort(){
        return port;
    }
    
    public void setPort(int newNo){
        port = newNo;
    }
    
    public String getDefaultLoginName(){
        return defaultLoginName;
    }
    
    public void setDefaultLoginName(String name){
        defaultLoginName = name;
    }
    
    public String getDefaultPassword(){
        return defaultPassword;
    }
    
    public void setDefaultPassword(String pass){
        defaultPassword = pass;
    }
    
    public String getEncoding(){
        return encoding;
    }
    
    public void setEncoding(String enc){
        encoding = enc;
    }
    
    public String getLoginName(){
        return loginName;
    }
    
    public void write(PrintWriter out) {
        out.println(description + "," + hostname + "," + port + "," + defaultLoginName + "," + defaultPassword + "," + moveStyle + "," + encoding);
    }
    
    @Override
    public String toString(){
        return description;
    }
    
    public boolean connect(){
        return connect(defaultLoginName, defaultPassword);
    }
    
    public boolean connect(String loginName, String password){
        DataOutputStream Outstream;
        TelnetStream telnetstream;
        
        this.loginName = loginName;
        if(receiverList == null){
            receiverList = new ReceiverList();
        }
        
        try {
            socket = new Socket(hostname, port);
            Outstream = new DataOutputStream(socket.getOutputStream());
            telnetstream = new TelnetStream(socket.getInputStream(), Outstream);
        }catch(Exception e){
            System.err.println("WingConnection.connect():socket:" + e);
            return false;
        }
        
        try {
            if (encoding.equals("")){
                out = new PrintWriter(new OutputStreamWriter(Outstream), true);
                in = new WingReader(new InputStreamReader(telnetstream));
            }else{
                out = new PrintWriter(new OutputStreamWriter(Outstream, encoding), true);
                in = new WingReader(new InputStreamReader(telnetstream, encoding));
            }
        } catch (UnsupportedEncodingException e) {
            System.err.println("WingConnection.connect():encode:" + e);
            return false;
        }
        
        receiveThread = new ReceiveThread(this, in, loginName, password, receiverList);
        receiveThread.start();
        
        return true;
    }
    
    public void close(){
        if(receiveThread != null){
            receiveThread.setStopFlag();
            try {
                receiveThread.join();
            } catch (InterruptedException e) {
                System.err.println("Connection.close(): join fail" + e);
                e.printStackTrace();
            }
        }
        if(out != null){
            out.println("quit");
            out.flush();
            out.close();
            try {
                in.close();
                socket.close();
            } catch (IOException e) {
                System.err.println("err:Connection.close():" + e);
                e.printStackTrace();
            }
        }
    }
    
    public void sendCommand(String s){
        if(out == null){
            return;
        }
        
        System.out.println("##### sendCommand #####:" + s + ":#####");
        
        if(s.indexOf("quit") >= 0){
            // コマンドとして入力された quit は捨てる。quit は window を閉じるタイミングで送る。
            // ここから送ってしまうと receiveThread がブン回ってしまうから。何か間違ってるかも。
            System.out.println("##### sendCommand #####:" + s + ":drop");
        }else{
            out.println(s);
            out.flush();
        }
    }
    
    public void addReceiver(MessageReceiver r){
        System.out.println("addReceiver:" + r);
        if(receiverList == null){
            receiverList = new ReceiverList();
        }
        receiverList.add(r);
    }
    
    public void removeReceiver(MessageReceiver r){
        System.out.println("removeReceiver:" + r);
        receiverList.remove(r);
    }
}