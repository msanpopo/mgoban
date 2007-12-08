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

package wing.message;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import wing.Block;

/*
32 70:jl:  test 2
 */
public class YellMessage extends Message{
    private static final Pattern p = Pattern.compile("(\\d+):(.*):\\s+(.*)");
    
    private int channelNo;
    private String name;
    private String text;
    
    public YellMessage(Block block){
        super(block);
        
        channelNo = 0;
        name = "";
        text = "";
        
        if(block.messageSize() == 1){
            Matcher m = p.matcher(block.get(0));
            if(m.matches()){
                channelNo = Integer.parseInt(m.group(1));
                name = m.group(2);
                text = m.group(3);
                System.out.println("YellParser:" + channelNo + ":" + name + ":" + text);
            }else{
                System.err.println("YellParser: parse fail :" + block.get(0));
            }
        }else{
            System.err.println("YellParser:size() != 1");
        }
    }
    
    public int getChannelNo(){
        return channelNo;
    }
    
    public String getName(){
        return name;
    }
    
    public String getText(){
        return text;
    }
}