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

public class ValueSimpleText implements Value{
    private String text;
    
    public ValueSimpleText() {
        text = "";
    }
    
    public ValueSimpleText(String str) {
        text = str;
    }
    
    public void setSgfString(String sgfStr) {
        //System.out.println("\n###ValueText0:" + sgfStr);
        
        StringBuilder str = new StringBuilder();
        boolean escaped = false;
        
        for(char c : sgfStr.toCharArray()){
            if(escaped){
                if(c == '\t' || c == '\f' || c == '\n' || c == '\r'){
                    str.append(' ');
                }else{
                    str.append(c);
                }
                escaped = false;
            }else{
                if(c == '\\'){
                    escaped = true;
                }else if(c == '\t' || c == '\f' || c == '\n' || c == '\r'){
                    str.append(' ');
                }else{
                    str.append(c);
                }
            }
        }
        
        text = str.toString();
    }
    
    public String getSgfString(){
        StringBuilder str = new StringBuilder();
        for(char c : text.toCharArray()){
            if(c == ']' || c == '\\' || c == ':'){
                str.append('\\');
            }
            str.append(c);
        }
        
        return str.toString();
    }
    
    public String getValueString() {
        StringBuilder str = new StringBuilder();
        
        str.append("[");
        str.append(getSgfString());
        str.append("]");
        
        return str.toString();
    }
    
    public String getText(){
        return text;
    }
    
    public void setText(String text){
        this.text = text;
    }
    
    public boolean isEmpty(){
        return text.isEmpty();
    }
}