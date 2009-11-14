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

package wing;

import java.util.ArrayList;
import java.util.Collection;
import wing.message.Message;
import wing.translator.Translator;

/**
 * サーバーから送られてくるメッセージのかたまり。
 * <pre>
 * ServerCode.PROMPT がくるまでにきた、同じ ServerCode をもつメッセージをひとかたまりとしている。
 * 15(Move) を送っている途中で 9(Info) が混じる場合もあるので ServerCode.PROMPT を区切りにしてる。

15 Game 246 I: tc2006 (1 257 12) vs snps (1 181 8)
9 Handicap and komi are disable.
15   0(B): Handicap 2
15   1(W): C16
1 8
 * </pre>
 */
public class Block {
    
    private ServerCode serverCode;
    private ArrayList<String> message;
    
    public Block(ServerCode code) {
        this.serverCode = code;
        this.message = new ArrayList<String>();
    }
    
    public void add(String s){
        message.add(s);
    }

    public String get(int index){
        if(index > message.size()){
            System.err.println("Block.get: index:" + index);
            return "";
        }else{
            return message.get(index);
        }
    }
    
    public ServerCode getCode(){
        return serverCode;
    }

    public Collection<String> getMessageCollection(){
        return message;
    }

    public Message createMessage(){
        Translator translator = serverCode.getTranslator();
        
        Message msg = translator.translate(this);
        
        return msg;
    }
    
    public int messageSize(){
        return message.size();
    }
    
    public void print(){
        System.out.println("block ###################");
        System.out.println("no:" + serverCode.toString());
        for(String s : message){
            System.out.println(s);
        }
        System.out.println("#########################");
    }
    
    @Override
    public String toString(){
        String no = Integer.toString(serverCode.getNo());
        StringBuilder str = new StringBuilder();
        
        for(String s : message){
            str.append(no).append(":").append(s).append("\n");
        }
        
        return str.toString();
    }
}