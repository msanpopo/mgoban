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
 * igs では 9 の info で対局結果を送ってくるが、wing と nngs では 21 の shout で送ってくる。
 *
9 {Game 297: MelXu vs sisi : W 93.5 B 111.0}        // igs
 
21 {Game 11: kamaROBOT vs DOM : W 52.5 B 126.0 Black wins by 73.5.}	// wing (kamakama さんのページから)
 
21 {Game 1: white vs black : Black resigns. W 37.0 B 28.0}      // nngs (nngs だと数えた碁なのに resign の文字が付く・・。)
 */
public class ResultNormalMessage extends Message{
    private static final Pattern p = Pattern.compile("\\{Game\\s+(\\d+):\\s+([^\\s]+)\\s+vs\\s+([^\\s]+)\\s+:\\s+(.*)\\}");
    
    private int gameNo;
    private String bName;
    private String wName;
    
    public ResultNormalMessage(Block block) {
        super(block);
        
        String line0 = block.get(0);
        
        Matcher m = p.matcher(line0);
        if(m.matches()){
            gameNo = Integer.parseInt(m.group(1));
            wName = m.group(2);
            bName = m.group(3);
            
            
            System.out.println("ResultNormalMessage :" + gameNo + ":" + bName + ":" + wName + ":");
            
        }else{
            System.err.println("ResultNormalMessage:" + line0);
        }
    }
    
    public int getGameNo(){
        return gameNo;
    }
}