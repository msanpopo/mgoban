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

/*
 
3.2. Text
 
Text is a formatted text. White spaces other than linebreaks are converted to space (e.g. no tab, vertical tab, ..).
 
Formatting:
Soft line break: linebreaks preceded by a "\" (soft linebreaks are converted to "", i.e. they are removed)
Hard line breaks: any other linebreaks encountered
 
Attention: a single linebreak is represented differently on different systems, e.g. "LFCR" for DOS, "LF" on Unix. An application should be able to deal with following linebreaks: LF, CR, LFCR, CRLF.
 
Applications must be able to handle Texts of any size. The text should be displayed the way it is, though long lines may be word-wrapped, if they don't fit the display.
 
Escaping: "\" is the escape character. Any char following "\" is inserted verbatim (exception: whitespaces still have to be converted to space!). Following chars have to be escaped, when used in Text: "]", "\" and ":" (only if used in compose data type).
 
Encoding: texts can be encoded in different charsets. See CA property.
 
 
3.2.1. Example:
 
C[Meijin NR: yeah, k4 is won\
derful
sweat NR: thank you! :\)
dada NR: yup. I like this move too. It's a move only to be expected from a pro. I really like it :)
jansteen 4d: Can anyone\
 explain [me\] k4?]
 
could be rendered as:
 
Meijin NR: yeah, k4 is wonderful
sweat NR: thank you! :)
dada NR: yup. I like this move too. It's a move only to be expected
from a pro. I really like it :)
jansteen 4d: Can anyone explain [me] k4?
 
 */
public class ValueText implements Value{
    private String text;
    
    public ValueText() {
        text = "";
    }
    
    public ValueText(String str){
        text = str;
    }
    
    public void setSgfString(String sgfStr) {
        StringBuilder str = new StringBuilder();
        boolean escaped = false;
        
        //System.out.println("\n###ValueText0:" + sgfStr);
        
        for(char c : sgfStr.toCharArray()){
            if(escaped){
                if(c == '\n' || c == '\r'){
                    // \n, \n\r, \r\n, \r の全部に対応するにはどうするかわからない。こんなでいいのか？
                    // escaped = true;
                }else if(c == '\t' || c == '\f'){
                    str.append(' ');
                    escaped = false;
                }else{
                    str.append(c);
                    escaped = false;
                }
            }else{
                if(c == '\\'){
                    escaped = true;
                }else if(c == '\t' || c == '\f'){
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