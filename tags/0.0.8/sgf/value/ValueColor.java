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

import go.GoColor;

public class ValueColor implements Value{
    private GoColor color;
    
    public ValueColor() {
        color = null;
    }
    
    public ValueColor(GoColor color){
        this.color = color;
    }

    public void setSgfString(String str) {
        color = GoColor.get(str);
    }

    public String getSgfString(){
        if(color != null){
            return color.toSgfString();
        }else{
            return "";
        }
    }
    
    public String getValueString() {
        StringBuilder str =new StringBuilder();
        str.append("[");
        str.append(getSgfString());
        str.append("]");
        
        return str.toString();
    }
    
    public GoColor getColor(){
        return color;
    }
    
    public void setColor(GoColor color){
        this.color = color;
    }
}