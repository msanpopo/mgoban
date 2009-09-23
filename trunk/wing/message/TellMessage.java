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

package wing.message;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import wing.Block;

/*
24 *jloup*:  test
 */
public class TellMessage extends Message {
    private static final Pattern p = Pattern.compile("\\*(.*)\\*:\\s+(.*)");
    
    private String name;
    private String text;
    
    public TellMessage(Block block){
        super(block);
        
        int size = block.messageSize();
        String line0 = block.get(0);
        name = "";
        text = "";
        
        if(size == 1){
            Matcher m = p.matcher(line0);
            if(m.matches()){
                name = m.group(1);
                text = m.group(2);
                System.out.println("TellMessage:" + name + ":" + text);
            }else{
                System.err.println("TellMessage fail :" + line0);
            }
        }else{
            System.err.println("TellMessage:size() != 1");
        }
    }
    
    public String getName(){
        return name;
    }
    
    public String getText(){
        return text;
    }
}