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
wing:
##### receive:9 EvenMatch [19x19] in 1 minutes requested with atest1 as White.:
##### receive:9 対局を受け入れるには "match atest1 B 19 1 10"  断るには "decline atest1" を入力して下さい.:
##### receive:1 5:
##### receive:9 Use <match atest1 B 19 1 10> or <decline atest1> to respond.:
##### receive:1 5:
 
nngs:
##### receive:9 Match [9x9] in 1 minutes requested with aaa as White.:
##### receive:9 Use <match aaa B 9 1 10> or <decline aaa> to respond.:
##### receive:1 5:
 *
 *
 *
 *
 
対局の申込とその変更の流れ
 
1 から 2 へ申し込み
##### receive:9 EvenMatch [19x19] in 1 minutes requested with atest1 as White.:
##### receive:9 対局を受け入れるには "match atest1 B 19 1 10"  断るには "decline atest1" を入力して下さい.:
##### receive:1 5:
##### receive:9 Use <match atest1 B 19 1 10> or <decline atest1> to respond.:
##### receive:1 5:
 
メイン時間を修正して 2 から 1 へ申し込み
##### sendCommand #####:match atest1 B 19 2 10:#####
##### receive:9 atest1 からの対局条件を変更して申し込みます。:
##### receive:9 19x19 路盤の対局をあなたの黒番で atest1 に申し込みました。:
##### receive:9 持ち時間 :   2 分:
##### receive:9 秒読み   :  10 分:
##### receive:9 考慮時間 :   0 分:
##### receive:1 5:
 
秒読み時間を修正して 1 から 2 へ申し込み（一度断ってから再度申し込むかたちになってる）
##### receive:9 atest1 はあなたの対局申し込みを断りました。:
##### receive:1 5:
##### receive:9 EvenMatch [19x19] in 2 minutes requested with atest1 as White.:
##### receive:9 対局を受け入れるには "match atest1 B 19 2 15"  断るには "decline atest1" を入力して下さい.:
##### receive:1 5:
##### receive:9 Use <match atest1 B 19 2 15> or <decline atest1> to respond.:
##### receive:1 5:
##### sendCommand #####:decline atest1:#####
##### receive:9 atest1 からの対局申し込みを断りました.:
##### receive:1 5:
 */
public class MatchRequestMessage extends Message{
    private static final Pattern pMatch = Pattern.compile("(\\w+) \\[(\\d+)x\\d+\\] in (\\d+) minutes requested with ([^\\s]+) as ([^\\s]+)\\.");
    private static final Pattern pMatch2 = Pattern.compile("Use <(.*?)> or <(.*?)> to respond\\.");
    private static final Pattern pMatch2wing = Pattern.compile("対局を受け入れるには \"(.*?)\"  断るには \"(.*?)\" を入力して下さい\\.");
    
    private String opponentName;
    private String acceptString;
    private String declineString;
    
    public MatchRequestMessage(Block block) {
        super(block);
        
        String firstline = block.get(0);
        
        Matcher mMatch= pMatch.matcher(firstline);
        if(mMatch.matches()){
            opponentName = mMatch.group(4);
            
            System.out.println("MatchRequestMessage:name:" + opponentName + ":" + firstline);
            
            String line2 = block.get(1);
            Matcher mLine2 = pMatch2.matcher(line2);
            Matcher mLine2wing = pMatch2wing.matcher(line2);
            
            if(mLine2.matches()){
                acceptString = mLine2.group(1);
                declineString = mLine2.group(2);
            }else if(mLine2wing.matches()){
                acceptString = mLine2wing.group(1);
                declineString = mLine2wing.group(2);
            }else{
                System.err.println("MatchRequestMessage: ???:" + firstline);
            }
            System.out.println("MatchRequestMessage 2: " + acceptString + ":" + declineString);
        }else{
            System.err.println("MatchRequestMessage: ???:" + firstline);
        }
    }
    
    public String getName(){
        return opponentName;
    }
    
    public String getAcceptString(){
        return acceptString;
    }
}