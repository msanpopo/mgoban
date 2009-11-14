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

import go.GoColor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import wing.Block;

/*
21 {Game 51: nove9 vs narise : Black resigns.}          // wing, nngs
9 {Game 395: JINGO vs takapujo : White resigns.}        // igs
 */
public class ResultResignMessage extends Message{
    private static final Pattern p = Pattern.compile("\\{Game\\s+(\\d+):\\s+([^\\s]+)\\s+vs\\s+([^\\s]+)\\s+:\\s+(.*)\\s+resigns.\\}");
    
    private int gameNo;
    private String bName;
    private String wName;
    private GoColor color;
    
    public ResultResignMessage(Block block) {
        super(block);
    
        String line0 = block.get(0);
        
        Matcher m = p.matcher(line0);
        if(m.matches()){
            gameNo = Integer.parseInt(m.group(1));
            wName = m.group(2);
            bName = m.group(3);
            color = GoColor.get(m.group(4));
            
            System.out.println("ResultResignMessage :" + gameNo + ":" + bName + ":" + wName + ":" + color.toString() + ":");
            
        }else{
            System.err.println("ResultResignMessage parse:" + line0);
        }
    }
    
    public int getGameNo(){
        return gameNo;
    }
    
    public GoColor getColor(){
        return color;
    }
}