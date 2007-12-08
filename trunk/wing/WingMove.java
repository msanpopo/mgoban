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

package wing;

import go.GoColor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * サーバーが move(15) で送ってくる着手を表現する。
 * ハンディキャップの情報が入ってたり、取り上げられた石の座標が入っている点に注意。
 *
 * かならず 0 手目が黒になる。置き石があり初手が白番の時は、下のように黒の着手として置き石の数がくる。
15   0(B): Handicap 4
 
15  41(W): H17
15  42(B): G18
15  43(W): H18
15  44(B): F19 F17 F18		// 着手 の後にとられた石が並ぶ
15  45(W): H16
15  46(B): P4
 
15  84(B): Pass
 */
public class WingMove {
    private static final Pattern p1 = Pattern.compile("\\s*([-\\d]+)\\((.)\\): (.*)");
    
    public static final int MOVE = 0;
    public static final int HANDICAP = 1;
    // -1 手目。着手も置き石も置かれてない状態。
    // 着手のない Move メッセージとして表現される
    public static final int DUMMY = 2;      

    
    private int moveNo; 		// -1〜 :: 1手目が 0。1手目を undo すると -1 の着手が次にくる
    private GoColor color;
    private String move_orig;		// "@0"(着手番号 -1 の時), "Handicap *", "Pass", "H17", "F19 F17 F18"(後ろのふたつは取った石の座標)
    private String move;
    private int type;
    private int handicap;

    public WingMove(String line) {
        Matcher m1 = p1.matcher(line);
        if (m1.matches()) { // 着手 と 取り上げた石 の位置
            moveNo = Integer.parseInt(m1.group(1)); // 着手番号（ゼロからはじまる）
            color = GoColor.get(m1.group(2));
            move_orig = m1.group(3);

            handicap = 0;
            
            String[] a = move_orig.split(" ");
            if(a[0].equals("Handicap")){
                type = HANDICAP;
                move = null;
                handicap = Integer.parseInt(a[1]);
            }else if(a[0].equals("@0")){
                type = DUMMY;
                move = null;
            }else{
                type = MOVE;
                move = a[0];
            }
            
            //System.out.println("WingMove:" + n + ":(" + color + "):" + move);
        } else {
            System.err.println("WingMove:" + line);
        }
    }
    
    public int getMoveNo() {
        return moveNo;
    }
    
    public GoColor getColor() {
        return color;
    }
    
    public String getMove() {
        return move;
    }
    
    public int getType(){
        return type;
    }
    
    public int getHandicap(){
        return handicap;
    }
    
    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append(Integer.toString(moveNo));
        str.append("(");
        str.append(color.toGtpString());
        str.append("):");
        str.append(move_orig);
        
        return str.toString();
    }
}