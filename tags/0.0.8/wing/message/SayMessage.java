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

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import wing.Block;

/*
nngs
19 aaa hi:

wing  * が名前の前後について 区切りに :
19 *spaman*: よろしくお願いいたします
 */
public class SayMessage extends Message {
    private String name;
    private String text;
    
    private static final Pattern p = Pattern.compile("\\*(.*?)\\*:");
    
    /*
    public static void main(String[] arg){
        Matcher m = p.matcher("*spaman*:");
        if (m.matches()){
            System.out.println("match:" + m.group(1));
        }else{
            System.out.println("not match");
        }
    }
    */
    public SayMessage(Block block){
        super(block);
        
        String[] s = block.get(0).split("\\s");
        
        Matcher m = p.matcher(s[0]);
        if (m.matches()){
            this.name = m.group(1);
        }else{
            this.name = s[0];
        }
        
        this.text = s[1];
    }
    
    public String getText(){
        StringBuilder str = new StringBuilder();
        str.append(name).append(" : ").append(text);
        return str.toString();
    }
}
