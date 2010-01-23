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

package sgf.value;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValueSize implements Value{
    private static final Pattern p0 = Pattern.compile("\\s*(\\d+)\\s*");
    private static final Pattern p1 = Pattern.compile("\\s*(\\d+)\\s*:\\s*(\\d+)\\s*");
    
    private int size;
    
    public ValueSize() {
        size = 0;
    }
    
    public ValueSize(int boardSize) {
        size = boardSize;
    }
        
    public void setSgfString(String str) {
        Matcher m0 = p0.matcher(str);
        Matcher m1 = p1.matcher(str);
        if(m0.matches()){
            size = Integer.parseInt(m0.group(1));
        }else if(m1.matches()){
            size = Integer.parseInt(m0.group(1));
        }else{
            System.err.println("error:ValueSize:" + str + ":");
        }
    }
    
    public String getSgfString(){
        return Integer.toString(size);
    }
    
    public String getValueString() {
        StringBuilder str = new StringBuilder();
        str.append("[");
        str.append(getSgfString());
        str.append("]");
        
        return str.toString();
    }
    
    public int getSize(){
        return size;
    }
}