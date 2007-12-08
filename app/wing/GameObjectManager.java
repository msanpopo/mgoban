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

package app.wing;

import app.*;
import java.util.ArrayList;
import wing.Game;

import wing.MessageReceiver;
import wing.message.Message;
import wing.message.MoveMessage;

public class GameObjectManager implements MessageReceiver{
    private NetGo netGo;
    private ArrayList<GameObject> gameObjectList;
    
    public GameObjectManager(NetGo netGo){
        this.netGo = netGo;
        
        this.gameObjectList = new ArrayList<GameObject>();
    }
    
    public void receive(Message wm) {
        if(wm instanceof MoveMessage){
            MoveMessage m = (MoveMessage)wm;
            
            int gameNo = m.getGameNo();
            
            if(hasGameObject(gameNo) == false){
                String bName = m.getBlackName();
                String wName = m.getWhiteName();
                
                String myName = netGo.getServer().getLoginName();
                
                System.out.println("GameObjectManager:new GameObject :" + gameNo + ":" + bName + ":" + wName);
                GameObject newObj = new GameObject(netGo, gameNo, bName, wName, this, myName);
                gameObjectList.add(newObj);
            }
        }
    }
    
    public void createNewGameObject(Game game){
        String myName = netGo.getServer().getLoginName();
        if(hasGameObject(game.getGameNo()) == false){
            System.out.println("GameObjectManager.createNewGameObject:" + game);
            GameObject newObj = new GameObject(netGo, game, this, myName);
            gameObjectList.add(newObj);
        }
    }
    
    public void removeGameObject(GameObject obj){
        gameObjectList.remove(obj);
    }
    
    private boolean hasGameObject(int gameNo){
        for(GameObject go : gameObjectList){
            if(go.getGameNo() == gameNo){
                System.out.println("GameObjectManager.hasGameObject : true : gameNo:" + gameNo);
                return true;
            }
        }
        
        return false;
    }
}