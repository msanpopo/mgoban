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

public class ValueNumber implements Value{
    private int num;
    
    public ValueNumber() {
        num = 0;
    }

    public ValueNumber(int n){
        num = n;
    }

    public void setSgfString(String str) {
        num = Integer.parseInt(str);
    }
    
    public String getSgfString(){
        return Integer.toString(num);
    }
    
    public String getValueString() {
        StringBuilder str = new StringBuilder();
        str.append("[");
        str.append(getSgfString());
        str.append("]");
        
        return str.toString();
    }
    
    public int getNumber(){
        return num;
    }
        
    public void setNumber(int n){
        num = n;
    }
}