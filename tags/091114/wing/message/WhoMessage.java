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

import java.util.Collection;
import java.util.TreeSet;
import java.util.regex.Pattern;

import wing.WingUser;
import java.util.regex.Matcher;
import wing.Block;

/*
27  Info       Name       Idle   Rank |  Info       Name       Idle   Rank
27  Qa --   29 DOKIDOKI*  22m     3d* |   X --   -- Red         3m     NR
27  QX --   -- guest0086   3m     NR  |     --   -- guest0096  57s     NR
27     40   -- guest0115   4m     NR  |     29   -- guest0120  16s     NR
27     22   -- guest0130   6m     NR  |     --   -- guest0131   3m     NR
 
略
 
27   g --    7 Niko        7s     1k* |  Qg --   23 mirai      10s     1d*
27  QX --   -- tomkit      2s     2d* |  Q  23   -- nove9      33s     2d*
27  Q  --   -- Tadao       3s     2d* |  Qg --   23 kyhci       8s     2d*
27                 ******** 170 Players 47 Total Games ********
 */
public class WhoMessage extends Message{
    private static final Pattern p = Pattern.compile("(.*)\\| (.*)");
    
    private TreeSet<WingUser> userSet;
    
    public WhoMessage(Block block){
        super(block);
        
        userSet = new TreeSet<WingUser>();
        
        int i = 0;
        int remove0 = 0;                        // 先頭行はラベルなので削除
        int rmeove1 = block.messageSize() - 1;	// 最終行は対局数やプレーヤー数なので削除
        
        for (String s : block.getMessageCollection()){
            if(i == remove0 || i == rmeove1){
                // skip
            }else{
                Matcher m = p.matcher(s);
                if(m.matches()){
                    // igs でテストしていると、時々ユーザー情報のかわりに "               Logon" などの文字だけの時がある
                    // 左右どちらにある場合もあるし、"Login" 以外の文字列の時もある。それぞれチェックしてはじく。
                    if(m.group(1).indexOf("             ") == -1){
                        userSet.add(new WingUser(m.group(1)));
                    }
                    if(m.group(2).indexOf("             ") == -1){
                        userSet.add(new WingUser(m.group(2)));
                    }
                }else{
                    System.err.println("error :WhoPanel parse :" +  s);
                }
            }
            ++i;
        }
    }
    
    public Collection<WingUser> getUserCollection(){
        return userSet;
    }
}