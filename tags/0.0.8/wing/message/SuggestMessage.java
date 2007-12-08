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
9 mugaと互角な対局をするには、黒番で打って下さい。
9 19 路盤の場合：
9 互角な対局は設定できません。
9 13 路盤の場合：
9 手合割は 4 子局で、コミ 5.5 目が適切です。
9 9 路盤の場合：
9 手合割は 2 子局で、コミ -1.5 目が適切です。

 */
public class SuggestMessage extends Message{
    private static final Pattern p = Pattern.compile("(.*)と互角な対局をするには.*");
    
    private String name;
    private String suggest;
    
    public SuggestMessage(Block block) {
        super(block);
        
        String firstline = block.get(0);
        Matcher m = p.matcher(firstline);
        
        if(m.matches()){
            name = m.group(1);
            
            StringBuilder str = new StringBuilder();
            for(String s : block.getMessageCollection()){
                str.append(s).append("\n");
            }
            suggest = str.toString();
            System.out.println("SuggestMessage:" + suggest);
        }else{
            System.err.println("SuggestMessage: parse fail:" + firstline);
        }
    }
    
    public String getSuggestString(){
        return suggest;
    }
}