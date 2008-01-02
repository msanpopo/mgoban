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
 * kamakama さんのページより
 *
9 対局 kamaROBOT-DOM の再開待ち時間が過ぎました。
9 相手が再開義務を果たしていませんので、'declare'または'dismiss'コマンドを
9 これから五分間内に入力することができます。

 * 10 手未満の場合は dismiss だけが選べる

9 対局 kamaROBOT-DOM の再開待ち時間が過ぎました。
9 相手が再開義務を果たしていませんので、'dismiss'コマンドを
9 これから五分間内に入力することができます。
 *
 */
public class DeclareMessage  extends Message{
    private static final Pattern pGame = Pattern.compile("対局 ([^\\s]+) の再開待ち時間が過ぎました。");
    
    private String gameName;
    private boolean declare;

    public DeclareMessage(Block block) {
        super(block);
        
        Matcher mGame = pGame.matcher(block.get(0));
        if (mGame.matches()) {
            gameName = mGame.group(1);
            
            if(block.get(1).indexOf("declare") > -1){
                declare = true;
            }else{
                declare = false;
            }
        }else{
            gameName = null;
            declare = false;
            System.err.println("DeclareMessage:" + block.get(0));
        }
    }
    
    public String getMessage(){
        return block.get(0);
    }
    
    public String getGameName(){
        return gameName;
    }
    
    public boolean hasDeclare(){
        return declare;
    }   
}