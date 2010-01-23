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

import wing.Block;

/*
25 File
igo3       [ 3k*](B) : zwalk      [ 3d*](W) H 5 K  2.5 19x19 B+6.5 0619-2104 R
pop        [ 1k*](B) : zwalk      [ 3d*](W) H 3 K  2.5 19x19 B+Resign 0620-2234 R
S2         [ 2d*](B) : zwalk      [ 3d*](W) H 0 K -1.5 19x19 B+Resign 0621-0001 R
zwalk      [ 2d*](W) : wingx      [ 1k*](B) H 3 K -0.5 19x19 W+Resign 0621-0115 R
zwalk      [ 2d*](W) : kazunori   [ 3k*](B) H 5 K -3.5 19x19 W+Time 0622-0145 R
makareko   [ 2d*](B) : zwalk      [ 3d*](W) H 0 K -2.5 19x19 B+Resign 0622-2228 R
TakuXP     [ 1k*](B) : zwalk      [ 2d*](W) H 3 K -0.5 19x19 B+Resign 0622-2253 R
zwalk      [ 2d*](B) : nove9      [ 3d*](W) H 0 K  3.5 19x19 B+Resign 0622-2345 R
ybando     [ 1d*](B) : zwalk      [ 2d*](W) H 2 K -3.5 19x19 B+Resign 0623-1847 R
zwalk      [ 2d*](W) : makareko   [ 2d*](B) H 0 K -0.5 19x19 W+Resign 0623-2152 R
25 File
 */
// results コマンドへの返事(直近の対局記録)
public class ThistMessage extends Message{
    public String resultString;
    
    public ThistMessage(Block block){
        super(block);
        
        StringBuilder str = new StringBuilder();
        
        for(String s : block.getMessageCollection()){
            if(s.startsWith("File")){
                // 何もしない
            }else{
                str.append(s);
                str.append("\n");
            }
        }
        
        resultString = str.toString();
    }
    
    public String getResultString(){
        return resultString;
    }
}