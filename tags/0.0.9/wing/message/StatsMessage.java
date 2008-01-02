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
9 Player:            atest0
9 端末・ガイド:      日本語 (SJIS)
9 会話言語:          日本語
9 ユーザ経験レベル:  未経験
9 レーティング:      25k    520
9 対局数:            0  (0 勝 0 敗)
9 認定対局数:        0  (0 勝 0 敗)
9 置碁:              最大 9 子まで
9 クレジット:        10/10
9 メールアドレス:    msanpopo@.co.jp
9 登録日:            2007/6/24  14:46
9 接続時間:          4:27
9 atest0は対局を受け入れていません。
9 対局ＯＫ  対局希望  出入通知  対局通知  SHOUT   観戦コメント  CJK受信
9    ×        ×        ○        ○      ○          ○         ×
9 Info: hi
9 Info: 作っているクライアントのテスト用アカウントです
 */
public class StatsMessage extends Message{
    private static final Pattern pStats = Pattern.compile("Player:\\s+(.*)\\s*");
        
    private String name;
    private String stats;
    
    public StatsMessage(Block block) {
        super(block);

        Matcher mStats = pStats.matcher(block.get(0));
        
        if(mStats.matches()){
            name = mStats.group(1);
            
            StringBuilder str = new StringBuilder();
            for(String s : block.getMessageCollection()){
                str.append(s).append("\n");
            }
            stats = str.toString();
            System.out.println("StatsMessage:" + stats);
        }
    }
    
    public String getStatsString(){
        return stats;
    }
}