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

package wing.message;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import wing.Block;

/*
9 Removing @ C2
 */
public class RemoveVertexMessage extends Message{
    private static final Pattern p = Pattern.compile("Removing @ (.*)");
    
    private String vertexStr;
    
    public RemoveVertexMessage(Block block) {
        super(block);

        String line0 = block.get(0);
        
        Matcher m = p.matcher(line0);
        if(m.matches()){
            vertexStr = m.group(1);
            
            System.out.println("RemoveVertexMessage:" + vertexStr + ":");
            
        }else{
            System.err.println("RemoveVertexMessage:" + line0);
        }
    }
    
    public String getVertexString(){
        return vertexStr;
    }
}