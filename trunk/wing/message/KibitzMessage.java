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

package wing.message;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import wing.Block;

/*
11 Kibitz jansteen [ 5d*]: Game mainichi vs mainichi [13]
11    m12 also aiming q8
1 8
 */
public class KibitzMessage extends Message{
    private static final Pattern p0 = Pattern.compile("Kibitz (.*) \\[\\s*([^ ]*)\\s*\\]: Game (.*) vs (.*) \\[(\\d+)\\]");
    private static final Pattern p1 = Pattern.compile("\\s*(.*)");
    
    public String name;
    public String rank;
    public String blackPlayerName;
    public String whitePlayerName;
    public int gameNo;
    public String kibitz;
    
    public String text;
    
    public KibitzMessage(Block block){
        super(block);
        
        name = "";
        rank = "";
        blackPlayerName = "";
        whitePlayerName = "";
        gameNo = 0;
        kibitz = "";
        
        Matcher m = p0.matcher(block.get(0));
        if(m.matches()){
            name = m.group(1);
            rank = m.group(2);
            whitePlayerName = m.group(3);
            blackPlayerName = m.group(4);
            gameNo = Integer.parseInt(m.group(5));
            
            Matcher m1 = p1.matcher(block.get(1));
            if(m1.matches()){
                kibitz = m1.group(1);
            }else{
                System.err.println("KibitzParser:parse() : parse line1 :" + block.get(1));
            }
            
        }else{
            System.err.println("KibitzParser : parse line0 :" + block.get(0));
        }
        
        StringBuffer str = new StringBuffer();
        str.append(name);
        str.append("[");
        str.append(rank);
        str.append("] : ");
        str.append(kibitz);
        
        text = str.toString();
    }
    
    public int getGameNo(){
        return gameNo;
    }
    
    public String getText(){
        return text;
    }
}