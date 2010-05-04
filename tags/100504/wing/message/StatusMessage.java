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
縦軸と横軸が入れ替わっていることに注意。
0: 黒石
1: 白石
2: 未使用?
3: ダメ
4: 白地
5: 黒地
 
最初の２行の段位以降は、アゲハマ、残り秒、残り手数、T 以降は コミ、置き石。
 
 *
22 Ronin 6d* 40 217 0 T 0.5 0
22 toli 5d* 22 181 25 T 0.5 0
22  0: 5555005055555555555
22  1: 5055010000005005555
22  2: 5055011010110010000
22  3: 0000111311110111101
22  4: 0101114141441114111
22  5: 1100114141441001414
22  6: 1411414114410050144
22  7: 4141441444411000111
22  8: 1141144444411105010
22  9: 4444444414110005000
22 10: 4444114111105050105
22 11: 4111444100000550100
22 12: 4141411111110050111
22 13: 4414141000005000141
22 14: 1141110030550110014
22 15: 3110011110550011111
22 16: 0000501005501111001
22 17: 5555500555500010500
22 18: 5555555555555505055
9 {Game 355: Ronin vs toli : W 101.5 B 93.0}		// nngs では 9 でなく 22 の一部としてこのデータが末尾に付く
1 8
 */
public class StatusMessage  extends Message{
    private static final Pattern p = Pattern.compile("\\s*(\\d+): (\\d+)");
    
    String bName, wName;
    String bRank, wRank;
    int bPrisoner, wPrisoner;
    int bSec, wSec;
    int bMove, wMove;
    
    double komi;
    int handicap;
    
    int[][] board;
    
    public StatusMessage(Block block){
        super(block);
        
        String[] white = block.get(0).split("\\s+");
        String[] black = block.get(1).split("\\s+");
        
        bName = black[0];
        bRank = black[1];
        bPrisoner = Integer.parseInt(black[2]);
        bSec = Integer.parseInt(black[3]);
        bMove = Integer.parseInt(black[4]);
        
        wName = white[0];
        wRank = black[1];
        wPrisoner = Integer.parseInt(white[2]);
        wSec = Integer.parseInt(white[3]);
        wMove = Integer.parseInt(white[4]);
        
        komi = Double.parseDouble(white[6]);
        handicap = Integer.parseInt(white[7]);
        
        board = null;
        
        int line = 0;
        for(String s : block.getMessageCollection()){
            if(line < 2){
                line += 1;
                continue;
            }
            
            Matcher m = p.matcher(s);
            if(m.matches()){
                int x = Integer.parseInt(m.group(1));
                String yLine = m.group(2);
                int size = yLine.length();
                if(board == null){
                    board = new int[size][size];
                }
                
                //System.out.println("StatusParser:x:" + x);
                //System.out.println("StatusParser:yline:" + yLine);
                
                char[] yChar = yLine.toCharArray();
                int i;
                for(i = 0; i < size; ++i){
                    char y = yChar[i];
                    //System.out.print("StatusParser:y:" + y + ":"  + Character.digit(y, 10));
                    board[x][i] = Character.digit(y, 10);
                }
            }else{
                System.err.println("StatusParser parse:" + s);
            }
        }
/*
                System.out.println("############################");
                for(int[] y: board){
                        for(int x : y){
                                System.out.print(x);
                        }
                        System.out.print("\n");
                }
 */
        
    }
    
    public String getBlackName(){
        return bName;
    }
    
    public String getWhiteName(){
        return wName;
    }
    
    public int[][] getBoard(){
        return board;
    }
}