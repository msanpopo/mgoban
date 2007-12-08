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

import go.GoClock;
import go.board.gui.BoardOperation;
import go.board.gui.NullOperation;
import go.gui.OperationPanel;
import go.GoColor;
import go.GoMove;
import go.GoResult;

public class WingServerPlayer extends NetPlayer{
    private OperationPanel operationPanel;
    private BoardOperation nullOperation;
    
    public WingServerPlayer(GoColor c, OperationPanel operationPanel) {
        super(c);

        this.operationPanel = operationPanel;
        this.nullOperation = new NullOperation();
    }
    
    @Override
    public void genMove(GoClock clock){
        operationPanel.setOperator(null, nullOperation);
    }
    
    @Override
    public void move(GoMove move, int moveNo){
        gameController.move(move, moveNo);
    }
    
    @Override
    public void resign(){
        gameController.resign(color);
    }
    
    @Override
    public void undo(){
        if(scoringController == null){
            gameController.acceptUndo(color, 2);
        }else{
            scoringController.undo(1);
        }
    }
    
    @Override
    public void done(){
        GoResult result = scoringController.getLocalResult();
        scoringController.setResultAndDone(this, result);
    }
}