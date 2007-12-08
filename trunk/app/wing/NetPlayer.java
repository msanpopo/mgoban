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

import go.GoColor;
import go.GoMove;
import go.GoPlayer;
import go.GoVertex;

public class NetPlayer extends GoPlayer{
    public NetPlayer(GoColor c){
        super(c);
    }

    //  操作者（人、サーバー）からの指示
    public void move(GoMove move){}
    public void move(GoMove move, int moveNo){}
    public void pass(){}
    public void resign(){}
    public void undo(){}
    public void done(){}		// 終局処理がおわった
    
    public void removeDeadStoneGroup(GoVertex v){}
}