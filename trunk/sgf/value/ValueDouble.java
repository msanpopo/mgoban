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

public class ValueDouble implements Value{
    public enum SgfDouble {
        NORMAL("1"),
        EMPHASIZED("2");
        
        private String str;
        
        private SgfDouble(String str){
            this.str = str;
        }
        
        public static SgfDouble get(String str){
            for(SgfDouble d : SgfDouble.values()){
                if(str.equals(d.str)){
                    return d;
                }
            }
            
            System.err.println("error:SgfDouble.get: str:" + str);
            return NORMAL;
        }
        
        @Override
        public String toString(){
            return str;
        }
    }
    
    private SgfDouble value;
    
    public ValueDouble() {
        value = SgfDouble.NORMAL;
    }
    
    public ValueDouble(SgfDouble d){
        value = d;
    }
    
    public void setSgfString(String str) {
        value = SgfDouble.get(str);
    }
    
    public String getSgfString(){
        return value.toString();
    }
    
    public String getValueString() {
        StringBuilder str = new StringBuilder();
        str.append("[");
        str.append(getSgfString());
        str.append("]");
        
        return str.toString();
    }
    
    public boolean isNormal(){
        if(value == SgfDouble.NORMAL){
            return true;
        }else{
            return false;
        }
    }
    
    public void setDouble(SgfDouble d){
        value = d;
    }
}