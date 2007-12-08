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
import javax.swing.SwingUtilities;

/**
 * GTP エンジンがエラー出力に出力した文字列を受け取るためのスレッド。
 * <pre>
 * 受け取った出力はイベントディスパッチスレッドで GtpEngine が受け取る。
 * </pre>
 */
public class GtpErrorThread extends Thread {
    private BufferedReader in;
    private GtpEngine engine;
    
    public GtpErrorThread(BufferedReader in, GtpEngine engine) {
        this.in = in;
        this.engine = engine;
    }
    
    public void run() {
        while (true) {
            String line;
            try {
                line = in.readLine();
            } catch (IOException ex) {
                System.err.println("error GtpErrorThread :" + ex);
                ex.printStackTrace();
                String msg = ex.toString();
                invoke(msg, true);
                
                break;
            }
            
            if(line == null){
                String msg = "GtpErrorThread: line == null";
                invoke(msg, true);
                
                break;
                
            }else{
                invoke(line, false);
            }
        }
    }
    
    private void invoke(String msg, boolean isError){
        GtpResponse response = new GtpResponse(msg, isError);
        SwingUtilities.invokeLater(new ErrorMessage(engine, response));
    }
    
    class ErrorMessage extends Thread{
        private GtpEngine engine;
        private GtpResponse response;
        
        public ErrorMessage(GtpEngine engine, GtpResponse response){
            this.engine = engine;
            this.response = response;
        }
        
        public void run(){
            if(engine != null){
                engine.receiveError(response);
            }
        }
    }
}