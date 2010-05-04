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

package go;

/**
 * 着手を表現する。<br>
 * 色と座標で表現される。
 * パスも着手に含む。
 *<pre>
 * gtp
 * 	大文字小文字関係なし
 * 	"black pass"(パス)
 * 	"w a1"
 *
 * sgf
 * 	小文字のみ
 * 	"B[ac]"
 * 	"W[]" or "B[tt]" パス
 *
 * xy(このアプリケーション内での表現)
 * 	[x, y] = [0, 0] をパスにする
 * </pre>
 */
public class GoMove{
    private GoColor color;
    private GoVertex vertex;
    private boolean pass;
    
    public GoMove(GoColor c, int x, int y){
        color = c;
        
        if(x == 0 && y == 0){
            vertex = null;
            pass = true;
        }else{
            vertex = new GoVertex(x, y);
            pass = false;
        }
    }
    
    public GoMove(int size, GoColor c, String gtpStr){
        color = c;
        String s = gtpStr.toLowerCase();
        
        if(s.equals("pass")){
            vertex = null;
            pass = true;
        }else{
            vertex = new GoVertex(size, gtpStr);
            pass = false;
        }
    }
    
    public GoMove(GoColor color, String sgfStr){
        String s = sgfStr.toLowerCase();
        
        this.color = color;
        
        if(s.equals("") || s.equals("tt")){
            vertex = null;
            pass = true;
        }else{
            vertex = new GoVertex(s);
            pass = false;
        }
    }
    
    public GoColor getColor(){
        return color;
    }
    
    public GoVertex getVertex(){
        return vertex;
    }
    
    public int getX(){
        if(pass){
            return 0;
        }else{
            return vertex.getX();
        }
    }
    
    public int getY(){
        if(pass){
            return 0;
        }else{
            return vertex.getY();
        }
    }
    
    public boolean isPass(){
        return pass;
    }
    
    public String toGtpString(int boardSize){
        if(pass){
            return color.toGtpString() + " pass";
        }else{
            return color.toGtpString() + " " + vertex.toGtpString(boardSize);
        }
    }
    
    public String toSgfString(){
        if(pass){
            return color.toSgfString() + "[]";
        }else{
            return color.toSgfString() + "[" + vertex.toSgfString() + "]";
        }
    }
    
    public boolean equals(GoMove m){
        if(color == m.color){
            if((isPass() == true) && (m.isPass() == true)){
                return true;
            }else if((isPass() == false) && (m.isPass() == false)){
                return vertex.equals(m.vertex);
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
    
    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        
        if(pass){
            str.append(color.toString()).append(" pass");
        }else{
            str.append(color.toString()).append(" ").append(vertex.toString());
        }
        
        return str.toString();
    }
}