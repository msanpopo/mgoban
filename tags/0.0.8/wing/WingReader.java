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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class WingReader extends BufferedReader{
    public WingReader(Reader r) {
        super(r);
    }
    
    public String receive() throws IOException {
        String line = "";
        int maxlenght = 4096;
        char[] str = new char[maxlenght];
        int l = 0;
        
        do {
            int ic = read();
            // System.out.print(" " + (int)ic);
            if(ic == -1){
                return null;
            }
            
            char c = (char)ic;
            
            if (l >= maxlenght){
                throw new IOException("Buffer Overflow");
            }
            if(c == '\n')
                break;
            
            //System.out.println("wingstream receive: c:" + ic);
            str[l] = c;
            ++l;
            
        } while(ready());
        
        line = new String(str, 0, l);
        
        //System.out.println("wingstream receive: l:" + l + " line:" + line + ":");
        
        return line;
    }
}