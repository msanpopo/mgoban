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

package sgf.value;

import go.GoMove;
import go.GoVertex;

public class ValueMove implements Value{
    private boolean isPass;
    private GoVertex vertex;
    
    public ValueMove() {
        isPass = false;
        vertex = null;
    }

    public ValueMove(GoMove move){
        isPass = move.isPass();

        if(isPass){
            vertex = null;
        }else{
            vertex = move.getVertex();
        }
    }

    public void setSgfString(String str) {
        if(str.isEmpty()){
            isPass = true;
            vertex = null;
        }else{
            isPass = false;
            vertex = new GoVertex(str);
        }
    }

    public String getSgfString(){
        if(isPass){
            return "";
        }else{
            return vertex.toSgfString();
        }
    }
    
    public String getValueString() {
        StringBuilder str = new StringBuilder();
        
        str.append("[");
        str.append(getSgfString());
        str.append("]");
        
        return str.toString();
    }
    
    public boolean isPass(){
        return isPass;
    }
    
    public GoVertex getGoVertex(){
        return vertex;
    }
}