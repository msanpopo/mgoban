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

import java.util.ArrayList;
import wing.Block;

/*
9 iinamaの中断した対局：:
18  LC555-iinama              kazahana-iinama           iinama-masa1210          :
18  Tango-iinama              kiyoyasu-iinama          :
9 中断した対局が 5 個有ります。:
 */  
public class SavedMessage extends Message{
    private ArrayList<String> savedList;
    
    public SavedMessage(Block block){
        super(block);
        
        savedList = new ArrayList<String>();
        
        for(String line : block.getMessageCollection()){
            String[] a = line.split("\\s+");
            for(String s : a){
                savedList.add(s);
            }
        }
    }
    
    public ArrayList<String> getSavedList(){
        return savedList;
    }
}