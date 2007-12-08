/*
 *  mGoban - GUI for Go
 *  Copyright (C) 2007  sanpo
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.SwingUtilities;

import wing.message.Message;

public class ReceiveThread extends Thread {
    private GoServer connection;
    private WingReader reader;
    private String name;
    private String password;
    private ReceiverList receiverList;
    
    private boolean loopOK;
    private boolean clientMode;
    private ServerCode code;        // File モードの時に直前の値を使うので関数ローカルの変数でなくここで持つ
    
    private boolean fileMode;
    
    private ArrayList<Block> blockList;
   
    private static final Pattern p0 = Pattern.compile("(.*?)\\s$");
    private static final Pattern p = Pattern.compile("(\\d+) (.*)");
    
    public ReceiveThread(GoServer con, WingReader reader, String name, String password, ReceiverList rList) {
        this.connection = con;
        this.reader = reader;
        this.name = name;
        this.password = password;
        this.receiverList = rList;
        
        loopOK = true;
        
        clientMode = false;
        fileMode = false;
        code = ServerCode.UNKNOWN;
        
        blockList = new ArrayList<Block>();
    }
    
    public void setStopFlag(){
        loopOK = false;
        // 放っておくと次の何らかの入力があるまで readLine() で待ってしまうので、
        // すぐに返事を返すように "ayt" を投げる。
        connection.sendCommand("ayt");
    }
    
    private static final int BEFORE_LOGIN = 0;
    private static final int AFTER_LOGIN = 1;
    private static final int AFTER_PASSWORD = 2;
    
    public void run(){
        int state = BEFORE_LOGIN;
        try {
            while (loopOK) {
                String line = reader.receive();
                Matcher m = p0.matcher(line);
                if(m.matches()){

                    line = m.group(1);
                    System.out.println("##### receive0:" + line + ":");
                }else{
                    System.err.println("ReceiverThread:match fail:");
                }
 
                if(line == null){
                    loopOK = false;
                }
                
                push(line);
                
                if (state == BEFORE_LOGIN && line.startsWith("Login") && !name.equals("")) {
                    System.out.println("ReceiveThread :send user name:" + name);
                    connection.sendCommand(name);
                    state = AFTER_LOGIN;
                }
                if (state == AFTER_LOGIN && !password.equals("")) {
                    System.out.println("ReceiveThread :send password:" + password);
                    connection.sendCommand(password);
                    break;
                }
                if (line.indexOf("#>") > -1){
                    // System.out.println("ReceiveThread #> found:" + line);
                    connection.sendCommand("toggle client true");
                    break;
                }
            }
            
            clientMode = true;
            
            while (loopOK) {
                String line = reader.readLine();
                
                if(line == null){
                    System.err.println("ReceiveThread:readLine() returns null");
                    break;
                }
                
                if(line.equals("")){
                    /* CR_LF が連続して空行を受け取ることがあるようだ。 */
                    //System.out.println("ReceiveThread:readLine() returns \"\"");
                    continue;
                }
                
                System.out.println("##### receive:" + line + ":");
                
                push(line);
            }
            
        } catch (IOException e) {
            System.err.println("ReceiveThread :ioexception :" + e);
        } catch (Exception e) {
            System.err.println("ReceiveThread :exception :" + e);
            e.printStackTrace();
        }
        System.out.println("ReceiveThread : loop stop:#########");
    }
    
    private void push(String line){
        if(clientMode == false){
            Block block = new Block(ServerCode.UNKNOWN);
            block.add(line);
            sendMessage(block);
            return;
        }
        
        String str = "";
        
        Matcher m = p.matcher(line);
        if(m.matches()){
            int no = Integer.parseInt(m.group(1));
            code = ServerCode.get(no);
            str = m.group(2);
        }else if(fileMode){
            str = line;
        }else{
            // File モードでないのに番号とメッセージへの分解に失敗した
            System.err.println("Distributor: parse fail:" + line + ":");
        }
        
        Block block = findBlock(code);
        if(block == null){
            block = new Block(code);
            blockList.add(block);
        }
        block.add(str);

        if(code == ServerCode.PROMPT){	// セッションの終了を検出したので溜った WingMessage を配送する
            for(Block b : blockList){
                //b.print();
                sendMessage(b);
            }
            blockList.clear();
        }
        
        if(str.equals("File")){
            fileMode = fileMode ? false : true;
            //System.out.println("fileMode:" + fileMode);
        }
    }
    
    private Block findBlock(ServerCode code){
        for(Block b : blockList){
            if(b.getCode() == code){
                return b;
            }
        }
        return null;
    }
    
    private void sendMessage(Block block){
        Message msg = block.createMessage();
        SwingUtilities.invokeLater(new Receive(receiverList, msg));
    }
    
    class Receive extends Thread{
        ReceiverList rList;
        Message msg;
        
        public Receive(ReceiverList l, Message msg){
            rList = l;
            this.msg = msg;
        }
        
        public void run(){
            rList.receive(msg);
        }
    }
}