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

package go;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 碁盤上の座標をあらわす。
 *<pre>
 * gtp
 * 	大文字小文字関係なし
 * 	19 路なら、横軸が左から右へ A - T ( I はない)、縦軸が下から上へ 1 - 19
 *	25x25 が最大サイズ
 *	19 路の場合
 *	左上 'a19'   右上 't19'
 *	左下 'a1'    右下 't1'
 *
 * sgf
 * 	小文字のみ
 * 	19 路なら、横軸が左から右へ a - s、縦軸が上から下へ a - s
 *	規格では a-z で 1-26 を表現し A-Z で 27-52 を表現する
 *	左上 'aa'   右上 'sa'
 *	左下 'as'   右下 'ss'
 *
 *
 * xy(このアプリケーション内での表現)
 * 	19 路なら、横軸が左から右へ 1 - 19、縦軸が上から下へ 1 - 19
 *	19 路の場合 [x, y]
 *	左上 [ 1, 1]   右上 [19, 1]
 *	左下 [ 1,19]   右下 [19,19]
 * </pre>
 */

public class GoVertex {
    private static final String sgfChar = "abcdefghijklmnopqrstuvwxyz";
    private static final String gtpChar = "abcdefghjklmnopqrstuvwxyz";
    
    private int x;	// 横軸 column
    private int y;	// 縦軸 row
    
    private static final Pattern pSgf = Pattern.compile("([a-z])([a-z])");
    private static final Pattern pGtp = Pattern.compile("([a-z])(\\d+)");
    
    
    public GoVertex(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    // sgf 用
    public GoVertex(String sgfStr){
        String s = sgfStr.toLowerCase();
        Matcher mSgf = pSgf.matcher(s);
        
        if(mSgf.matches()){
            char[] c = s.toCharArray();
            this.x = c[0] - 'a' + 1;
            this.y = c[1] - 'a' + 1;
        }else{
            System.err.println("error:GoVertex: sgf string ?:" + sgfStr + ":");
            this.x = 0;
            this.y = 0;
        }
    }
    
    // gtp 用
    public GoVertex(int size, String gtpStr){
        String s = gtpStr.toLowerCase();
        
        //System.out.println("GoVertex s:" + s + ":");
        
        Matcher mGtp = pGtp.matcher(s);
        
        if(mGtp.matches()){
            char[] c = s.toCharArray();
            String n = mGtp.group(2);
            if(c[0] > 'i'){
                this.x = c[0] - 'a' + 0;
            }else{
                this.x = c[0] - 'a' + 1;
            }
            this.y = size + 1 - Integer.parseInt(n);
            // System.out.println("GoVertex 1 x:" + x + " y:" + y);
        }else{
            System.err.println("error:GoVertex: gtp string ?:" + gtpStr);
            this.x = 0;
            this.y = 0;
        }
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    public String toGtpString(int boardSize){
        char c = gtpChar.charAt(x - 1);
        int n = boardSize + 1 - y;
        
        return Character.toString(c) + Integer.toString(n);
    }
    
    // [x, y] ｰ> sgf
    public String toSgfString(){
        char[] c = new char[2];
        
        c[0] = sgfChar.charAt(x - 1);
        c[1] = sgfChar.charAt(y - 1);
        
        return new String(c);
    }
    
    public boolean equals(GoVertex v){
        if(x == v.x && y == v.y){
            return true;
        }else{
            return false;
        }
    }
    
    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        
        str.append("vertex [").append(Integer.toString(x)).append(", ").append(Integer.toString(y)).append("]");
        
        return str.toString();
    }
}