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

package sgf.property;

import java.util.ArrayList;
import sgf.value.Value;

public class Property {
    private PropertyId id;
    private String ident;   // mGoban で扱えないプロパティーの id を保持るために必要
    private Value value;
    
    // sgf から展開するとき用
    public Property(String ident, ArrayList<String> valueList){
        this.id = PropertyId.get(ident);
        this.ident = ident;
        this.value = (Value) id.newValueInstance();
        
        for(String s : valueList){
            value.setSgfString(s);
        }
    }
    
    public Property(PropertyId id){
        this.id = id;
        this.ident = id.getIdent();
        this.value = (Value) id.newValueInstance();
    }
    
    public Property(PropertyId id, Value value){
        this.id = id;
        this.ident = id.getIdent();
        this.value = value;
    }

    public PropertyId getId(){
        return id;
    }
    
    public PropertyType getType(){
        return id.getPropertyType();
    }
    
    public void setValue(Value value){
        this.value = value;
    }

    public Value getValue(){
        return value;
    }
    
    public String toSgfString(){
        // System.out.println("Property toString: ident:" + ident);
        
        StringBuilder sb = new StringBuilder();
        sb.append(ident).append(value.getValueString());
        
        return sb.toString();
    }
}