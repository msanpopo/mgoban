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

package sgf.value;

import go.GoVertex;

public class ValueLabel implements Value{
    private GoVertex vertex;
    private String label;
    
    public ValueLabel(GoVertex v, String s){
        vertex = v;
        label = s;
    }
    
    public ValueLabel(String sgfStr){
        setSgfString(sgfStr);
    }

    public GoVertex getVertex(){
        return vertex;
    }
    
    public String getLabel(){
        return label;
    }
    
    public void setSgfString(String str) {
        String[] a = str.split(":");
        vertex = new GoVertex(a[0]);
        label = a[1];
    }
    
    public String getSgfString(){
        StringBuilder str = new StringBuilder();
        str.append(vertex.toSgfString());
        str.append(":");
        str.append(label);
        
        return str.toString();
    }

    public String getValueString() {
        StringBuilder str = new StringBuilder();
        str.append("[");
        str.append(getSgfString());
        str.append("]");
        
        return str.toString();
    }
}