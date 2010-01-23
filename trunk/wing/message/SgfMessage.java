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

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import wing.Block;

/*
##### receive:43   1: kyhci-TakuXP-20070811-1756          9: kyhci-mayayu-20070802-2254        :
##### receive:43   2: zwalk-kyhci-20070809-2110          10: kyhci-mirai-20070730-2140         :
##### receive:43   3: kyhci-Tadao-20070808-2224          11: kyhci-mirai-20070729-1947         :
##### receive:43   4: kyhci-igo123456-20070807-2200      12: kyhci-mayayu-20070728-2348        :
##### receive:43   5: kyhci-apt-20070805-1718            13: kyhci-skoei-20070728-2206         :
##### receive:43   6: zwalk-kyhci-20070805-1548          14: kyhci-mirai-20070727-1821         :
##### receive:43   7: Asage-kyhci-20070805-1108          15: kyhci-k117-20070727-1529          :
##### receive:43   8: kyhci-n555-20070803-2245           16: kyhci-tomkit-20070726-2313        :
##### receive:9 対局ファイルが 16 個あります。:
##### receive:1 5:
 */
public class SgfMessage extends Message {
    private static final Pattern p2 = Pattern.compile("\\s*(\\d+): (\\S+)\\s+(\\d+): (\\S+)\\s*");
    private static final Pattern p1 = Pattern.compile("\\s*(\\d+): (\\S+)\\s*");
        
    private ArrayList<String> sgfList;
    
    public SgfMessage(Block block) {
        super(block);
        
        sgfList = new ArrayList<String>();
        
        for(String s : block.getMessageCollection()){
            Matcher m2 = p2.matcher(s);
            Matcher m1 = p1.matcher(s);
            if(m2.matches()){
                sgfList.add(m2.group(2));
                sgfList.add(m2.group(4));
            }else if(m1.matches()){
                sgfList.add(m1.group(2));
            }
        }
    }
    
    public ArrayList<String> getSgfList(){
        return sgfList;
    }
}