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
 nngs
 9 Creating match [1] with white.
 
 wing
 9 Creating match [34] with spaman (H:0 Komi:-0.5). 
 */
public class CreatingMatchMessage extends Message{
    private static final Pattern pnngs = Pattern.compile("Creating match \\[(\\d+)\\] with (\\S+?)\\." );
    private static final Pattern pwing = Pattern.compile("Creating match \\[(\\d+)\\] with (\\S+?)\\s.*" );
    
    private int gameNo;
    private String opponentName;
    /*
    public static void main(String[] args) {
        Matcher m = pnngs.matcher("Creating match [1] with white.");
        if(m.matches()){
            System.out.println("match:" + m.group(1) + ":" + m.group(2));
            
        }else{
            System.out.println("not match");
        }
        
        m = pwing.matcher("Creating match [1] with white.");
        if(m.matches()){
            System.out.println("match:" + m.group(1) + ":" + m.group(2));
            
        }else{
            System.out.println("not match");
        }

    }
    */
    public CreatingMatchMessage(Block block) {
        super(block);

        String firstline = block.get(0);
        System.out.println("CreatingMatchMessage:" + firstline);
        
        Matcher mnngs= pnngs.matcher(firstline);
        Matcher mwing= pwing.matcher(firstline);
        if(mnngs.matches()){
            System.out.println("CreatingMatchMessage:" + mnngs.group(1) + ":" + mnngs.group(2));
            gameNo = Integer.parseInt(mnngs.group(1));
            opponentName = mnngs.group(2);
        }else if(mwing.matches()){
            System.out.println("CreatingMatchMessage:" + mwing.group(1) + ":" + mwing.group(2));
            gameNo = Integer.parseInt(mwing.group(1));
            opponentName = mwing.group(2);
        }else{
            System.err.println("CreatingMatchMessage:" + firstline);
        }
    }
    
    public String getName(){
        return opponentName;
    }
}