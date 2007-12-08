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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * GTP エンジンに送ったコマンドと、GTP エンジンから帰ってきたレスポンスの組。
 * <pre>
 * 
 * </pre>
 */
public class GtpMessage {
    private static final Pattern pFirst = Pattern.compile("([=?])\\s*(.*)");
    private static final Pattern pWithId = Pattern.compile("(\\d+)\\s+(.*)");
    
    private GtpCommand command;
    private String commandStr;      // 引数含む
    private int id;
    
    private GtpResponse response;
    private boolean success;
    private String message;
    
    public GtpMessage(GtpCommand command, String commandStr){
        init(command, commandStr, -1);
    }
    
    public GtpMessage(GtpCommand command, String commandStr, int id) {
        init(command, commandStr, id);
    }
    
    private void init(GtpCommand command, String commandStr, int id) {
        this.command = command;
        this.commandStr = commandStr;
        this.id = id;
        
        this.response = null;
        this.success = false;
        this.message = "";
    }
    
    public boolean commandIsEmpty(){
        if(commandStr.isEmpty()){
            return true;
        }else if(commandStr.matches("\\s+")){
            return true;
        }else{
            return false;
        }
    }
    
    public GtpCommand getGtpCommand(){
        return command;
    }
    
    public String getCommandString(){
        return commandStr;
    }

    public int getId(){
        return id;
    }
    
    public String getMessage(){
        return message;
    }
    
    public String getRawMessage(){
        return response.getResponse();
    }
    
    public boolean hasId(){
        if(id < 0){
            return false;
        }else{
            return true;
        }
    }
    
//    public boolean isError(){
//        if(response == null){
//            return true;
//        }else{
//            return response.isError();
//        }
//    }

    public void setResponse(GtpResponse newResponse){
        this.response = newResponse;
        if(response.isError() == false){
            parse(response.getResponse());
        }
    }

    public boolean success(){
        return success;
    }

    public String toString(){
        StringBuilder str = new StringBuilder();
        
        str.append(commandStr).append("--->").append(success).append(":").append(id).append(":").append(message);
        
        return str.toString();
    }
    
    private void parse(String raw){
        String[] a = raw.split("\n");
        
        StringBuilder str = new StringBuilder();
        boolean firstLine = true;
        
        for(String l : a){
            if(firstLine){
                Matcher mFirst = pFirst.matcher(l);
                if(mFirst.matches()){
                    String ret = mFirst.group(1);   // "?" or "="
                    String body = mFirst.group(2);
                    
                    if(ret.equals("=")){
                        success = true;
                    }else if(ret.equals("?")){
                        success = false;
                    }else{
                        System.err.println("error:GtpMessage.parse: ret :" + ret);
                        success = false;
                    }
                    
                    Matcher mWithId = pWithId.matcher(body);
                    if(mWithId.matches()){
                        int idTmp = Integer.parseInt(mWithId.group(1));
                        if(idTmp != -1 && idTmp != id){
                            System.err.println("err:GtpMessage.parse: id:" + id + " idTmp:" + idTmp + " str:" + l);
                            success = false;
                        }else{
                            id = idTmp;
                        }
                        body = mWithId.group(2);
                    }
                    str.append(body);
                }else{
                    System.err.println("GtpMessage.parse: first line? :" + l);
                }
                
                firstLine = false;
            }else{
                str.append("\n").append(l);
            }
        }
        message = str.toString();
    }
}