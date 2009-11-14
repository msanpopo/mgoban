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
9 aaa updates the match request.
9 Match [9x9] in 1 minutes requested with aaa as Black.
9 Use <match aaa W 9 1 15> or <decline aaa> to respond.
 */
public class MatchUpdateMessage extends Message{
    private static final Pattern pMatchUpdate = Pattern.compile("(\\w+) updates the match request\\.");
    private static final Pattern pMatch = Pattern.compile("(\\w+) \\[(\\d+)x\\d+\\] in (\\d+) minutes requested with ([^\\s]+) as ([^\\s]+)\\.");
    private static final Pattern pMatch2 = Pattern.compile("Use <(.*?)> or <(.*?)> to respond\\.");
        
    private String opponentName;
    private String acceptString;
    private String declineString;
    
    public MatchUpdateMessage(Block block) {
        super(block);
        
        String firstline = block.get(0);
        
        Matcher mLine2= pMatch.matcher(block.get(1));
        if(mLine2.matches()){
            System.out.println("MatchUpdateMessage:" + firstline);
            opponentName = mLine2.group(4);
            
            String line3 = block.get(2);
            Matcher mLine3 = pMatch2.matcher(line3);
            if(mLine3.matches()){
                acceptString = mLine3.group(1);
                declineString = mLine3.group(2);
                
                System.out.println("MatchUpdateMessage 2: " + acceptString + ":" + declineString);
            }
        }else{
            System.err.println("MatchUpdateMessage:" + firstline);
        }
    }
}