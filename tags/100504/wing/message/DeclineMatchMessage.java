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
9 aaa declines your request for a match.
 */
public class DeclineMatchMessage extends Message{
    // nngs 
    private static final Pattern pNngs = Pattern.compile("([^\\s]+) declines your request for a match\\." );
    // wing
    private static final Pattern pWing = Pattern.compile("([^\\s]+) があなたの対局申し込みを断りました。" );
    private String opponentName;
    
    public DeclineMatchMessage(Block block) {
        super(block);
        
        String firstline = block.get(0);
        
        Matcher mNngs= pNngs.matcher(firstline);
        Matcher mWing= pWing.matcher(firstline);
        if(mNngs.matches()){
            System.out.println("DeclineMatchMessage:" + firstline);
            opponentName = mNngs.group(1);
        }else if(mWing.matches()){
            System.out.println("DeclineMatchMessage:" + firstline);
            opponentName = mWing.group(1);
        }else{
            System.err.println("DeclineMatchMessage:" + firstline);
        }
    }
    
    public String getName(){
        return opponentName;
    }
}