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

package wing;

import java.util.ArrayList;

import wing.message.Message;

public class ReceiverList {
    private ArrayList<MessageReceiver> array;
    
    public ReceiverList(){
        array = new ArrayList<MessageReceiver>();
    }
    
    public void add(MessageReceiver r){
        array.add(r);
        System.out.println("ReceiverList.add: size:" + array.size());
    }
    
    public void remove(MessageReceiver r){
        array.remove(r);
        System.out.println("ReceiverList.remove: size:" + array.size());
    }
    
    public void receive(Message wm){
        // リストの要素を回している中でリストの要素の削除をしているので、コピーを作ってそれでまわす
        ArrayList<MessageReceiver> copyList = ArrayCopy(array);
        
        for(MessageReceiver r : copyList){
            //System.out.println("ReceiverList:receive:r:" + r);
            r.receive(wm);
        }
    }
    
    private static ArrayList<MessageReceiver> ArrayCopy(ArrayList<MessageReceiver> rList){
        ArrayList<MessageReceiver> newList = new ArrayList<MessageReceiver>();
        for(MessageReceiver r : rList){
            newList.add(r);
        }
        return newList;
    }
}