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

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import wing.Block;

import wing.WingMove;

/*
"observe ***" で返ってくる着手。以下の２行が着手毎に送られてくる。
 
15 Game 436 I: POLARIS (0 534 5) vs bonzai (0 485 8)
15  60(B): O11
 
-----------------------------------
 
"moves ***" で返ってくる着手の列。まとめて送られてくる。置き石の数が着手として送られてくることに注意。
 
15 Game 436 I: POLARIS (0 534 5) vs bonzai (0 485 8)
9 Handicap and komi are disable.   置き石ありだとこんな感じで別のコマンドが混じる。
15   0(B): Handicap 4
 
15  41(W): H17
15  42(B): G18
15  43(W): H18
15  44(B): F19 F17 F18		// 着手 の後にとられた石が並ぶ
15  45(W): H16
15  46(B): P4
 
15  84(B): Pass
 
時間設定なしの例？
15 Game 78 I: aaa (0 0 -1) vs bbb (0 0 -1)
 */
public class MoveMessage extends Message{
    private static final Pattern p0 = Pattern.compile("Game (\\d+) I: (\\S+) \\(([ 0-9\\-]+)\\) vs (\\S+) \\(([ 0-9\\-]+)\\).*");
    private static final Pattern p1 = Pattern.compile("\\s*([-\\d]+)\\((.)\\): (.*)");
    
    private int gameNo;
    
    private String bName;
    private int bHama;
    private int bSec;
    private int bMove;
    
    private String wName;
    private int wHama;
    private int wSec;
    private int wMove;
    
    private ArrayList<WingMove> moveList;
    
    public MoveMessage(Block block){
        super(block);
        
        String firstLine = block.get(0);
        Matcher m0 = p0.matcher(firstLine);
        if (m0.matches()) { // ゲーム番号、アゲハマ、残り時間と残り着手数
            gameNo = Integer.parseInt(m0.group(1));
            wName = m0.group(2);
            String wInfo = m0.group(3);
            bName = m0.group(4);
            String bInfo = m0.group(5);
            
            String[] wi = wInfo.split("\\s");
            String[] bi = bInfo.split("\\s");
            
            wHama = Integer.parseInt(wi[0]);
            wSec = Integer.parseInt(wi[1]);
            wMove = Integer.parseInt(wi[2]);
            bHama = Integer.parseInt(bi[0]);
            bSec = Integer.parseInt(bi[1]);
            bMove = Integer.parseInt(bi[2]);
            
            moveList = new ArrayList<WingMove>();
            
            for (String s : block.getMessageCollection()) {
                if (s.equals(firstLine)) {
                    continue;
                }
                WingMove wingMove = new WingMove(s);
                moveList.add(wingMove);
            }

        } else {
            System.err.println("MoveMessage parse:" + firstLine);
        }
        
        System.out.println("MoveMessage:" + this.toString());
    }
    
    public int getGameNo(){
        return gameNo;
    }
    
    public String getBlackName(){
        return bName;
    }
    
    public int getBlackSec(){
        return bSec;
    }
    
    public int getBlackMove(){
        return bMove;
    }
    
    public String getWhiteName(){
        return wName;
    }
    
    public int getWhiteSec(){
        return wSec;
    }
    
    public int getWhiteMove(){
        return wMove;
    }
    
    public ArrayList<WingMove> getWingMoveList(){
        return moveList;
    }
}