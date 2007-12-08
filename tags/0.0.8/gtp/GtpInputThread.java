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

package gtp;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import javax.swing.SwingUtilities;

/**
 * GTP エンジンが標準出力に出力した文字列を受け取るためのスレッド。
 * <pre>
 * 受け取った出力はイベントディスパッチスレッドで GtpEngine が受け取る。
 * 
 * GTP コマンドを送って、その出力をすぐに受け取るための仕組みとして、外から queue を
 * セットすることができる。queue がセットされている場合は、イベントディスパッチスレッドに
 * 出力を投げるとともに、queue に出力を入れる。
 * </pre>
 */
public class GtpInputThread extends Thread {
    private BufferedReader in;
    private GtpEngine engine;
    
    private BlockingQueue<GtpResponse> queue;
    
    public GtpInputThread(BufferedReader in, GtpEngine engine) {
        this.in = in;
        this.engine = engine;
        this.queue = null;
    }
    
    public synchronized void setQueue(BlockingQueue<GtpResponse> queue){
        this.queue = queue;
    }
    
    private synchronized void putQueueData(GtpResponse response){
        if(queue == null){
            return;
        }
        
        try {
            queue.put(response);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    
    public void run() {
        while (true) {
            StringBuilder str = new StringBuilder();
            
            if(readResponse(str) == false){
                invoke(str.toString(), true);
                break;
            }
            
            String msg = str.toString();
            
            //System.out.println("GtpInputThread:msg:" + msg + ":");
            if(msg.isEmpty()){
                // 彩で死に石がある時 final_status_list dead と final_score の間で空行を受け取るのでその場しのぎで逃げる。
                System.err.println("Gtp read:msg.isEmpty");
            }else{
                // 空はありえない。= か ? が必ずついてる。が、wine 経由でアプリを起動すると wine が標準出力にいろいろ出す。
                invoke(msg, false);
            }
        }
    }
    
    /**
     * ストリームの文字列を読み込んで StringBuilder に入れて返す。
     * @param str 読み込んだ文字列を入れる入れ物
     * @return ストリームの終端を検出したり、例外が発生したら false を返す。正常なら true を返す
     */
    private boolean readResponse(StringBuilder str){
        String line;
        
        try {
            line = in.readLine();

            if(line == null){
                String msg = "GtpInputThread: line == null";
                str.append(msg);
                
                return false;
            }
            
            while(line.isEmpty() == false){
                //System.out.println("Gtp read:" + line + ":");
                
                if(str.length() == 0){
                    str.append(line);
                }else{
                    str.append("\n").append(line);
                }
                
                line = in.readLine();
                
                if(line == null){
                    String msg = "GtpInputThread: line == null";
                    str.append(msg);
                    
                    return false;
                }
            }
            
        } catch (IOException ex) {
            System.err.println("error GtpInputThread :" + ex);
            ex.printStackTrace();
            String msg = ex.toString();
            str.append(msg);
            
            return false;
        }
        return true;
    }
    
    private void invoke(String msg, boolean isError){
        GtpResponse response = new GtpResponse(msg, isError);
        SwingUtilities.invokeLater(new InputMessage(engine, response));
        putQueueData(response);
    }
    
    class InputMessage extends Thread{
        private GtpEngine engine;
        private GtpResponse response;
        
        public InputMessage(GtpEngine engine, GtpResponse response){
            this.engine = engine;
            this.response = response;
        }
        
        public void run(){
            if(engine != null){
                engine.receiveInput(response);
            }
        }
    }
}