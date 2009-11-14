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

package wing.translator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import wing.Block;
import wing.message.Message;
import wing.message.RawMessage;
import wing.message.RestoreMessage;
import wing.message.ResultAdjournMessage;
import wing.message.ResultForfeitMessage;
import wing.message.ResultNormalMessage;

import wing.message.ResultResignMessage;

public class ShoutTranslator extends Translator {
    private static final Pattern p = Pattern.compile("\\{Game\\s+(\\d+):\\s+(.*)\\}");
    
    public Message translate(Block block) {
        String line0 = block.get(0);
       
        Message msg;
        
        Matcher m = p.matcher(line0);
        if(m.matches()){
            int gameNo = Integer.parseInt(m.group(1));
            String resultString = m.group(2);
            
            if(resultString.indexOf("forfeits") > -1){
                msg = new ResultForfeitMessage(block);
                
            }else if(line0.indexOf("resigns.}") > -1){
                msg = new ResultResignMessage(block);
                
            }else if(line0.indexOf("adjourned.}") > -1){
                msg = new ResultAdjournMessage(block);
                
            }else if(line0.indexOf("@ Move") > -1 || line0.indexOf("手目から再開されました") > -1){
                msg = new RestoreMessage(block);
                
            }else{
                msg = new ResultNormalMessage(block);
            }
            System.out.println("ShoutTranslator:   :" + resultString);
            System.out.println("ShoutTranslator:msg:" + msg.getClass().getName());
            
        }else{
            System.err.println("ShoutTranslator:" + line0);
            msg = new RawMessage(block);
        }
        
        return msg;
    }
}