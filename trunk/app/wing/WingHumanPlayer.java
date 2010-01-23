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

package app.wing;

import go.GoClock;
import go.board.gui.BoardOperation;
import go.board.gui.MoveOperation;
import go.board.gui.MoveOperator;
import go.board.gui.NullOperation;
import go.board.gui.ScoringOperation;
import go.board.gui.ScoringOperator;
import go.gui.GoOperator;
import go.gui.OperationPanel;
import go.GoColor;
import go.GoMove;
import go.GoResult;
import go.GoVertex;
import go.board.GoBoard;
import go.controller.ScoringController;

public class WingHumanPlayer  extends NetPlayer implements GoOperator, MoveOperator, ScoringOperator{
    private OperationPanel operationPanel;
    private GameObject gameObject;
    
    private BoardOperation nullOperation;
    private BoardOperation moveOperation;
    private BoardOperation scoringOperation;
    
    public WingHumanPlayer(GoColor c, GameObject obj, GoBoard board, OperationPanel operationPanel) {
        super(c);
        this.gameObject = obj;
        this.operationPanel = operationPanel;
        
        this.nullOperation = new NullOperation();
        this.moveOperation = new MoveOperation(board, this, c);
        this.scoringOperation = new ScoringOperation(board, this);
    }
    
    @Override
    public void genMove(GoClock clock){
        int passCount = gameController.getGameTree().getPassCount();
        System.out.println("passCount:" + passCount);
        if(passCount == 2){
            passButtonPressed();
        }else{
            operationPanel.setOperator(this, moveOperation);
        }
    }
    
    @Override
    public void doScoring(ScoringController con){
        scoringController = con;
        operationPanel.setOperator(this, scoringOperation);
    }

    @Override
    public void stopScoring() {
        operationPanel.setOperator(null, nullOperation);
        scoringController = null;
    }
    
    @Override
    public void move(GoMove move, int moveNo){
        operationPanel.setOperator(this, nullOperation);
        gameController.move(move, moveNo);
    }
    
    @Override
    public void resign(){
        operationPanel.setOperator(this, nullOperation);
        gameController.resign(color);
    }
    
    @Override
    public void undo(){
        operationPanel.setOperator(this, nullOperation);

        if(scoringController == null){
            gameController.acceptUndo(color, 2);
        }else{
            scoringController.undo(1);
        }
    }
    
    @Override
    public void done(){
        operationPanel.setOperator(this, nullOperation);
        
        GoResult result = scoringController.getLocalResult();
        scoringController.setResultAndDone(this, result);
    }
    
    @Override
    public void removeDeadStoneGroup(GoVertex v){
        scoringController.markDeadStoneGroup(v);
    }
    
    public void moveGenerated(GoMove move){
        operationPanel.setOperator(this, nullOperation);
        
        int sec = (int)gameController.getClock().stop();
        
        gameObject.sendCommandMove(move, sec);
    }
    
    public void vertexGenerated(GoVertex vertex){
        gameObject.sendCommandVertex(vertex);
    }
    
    public void wheelUp(boolean isShiftDown){}
    public void wheelDown(boolean isShiftDown){}
    
    public void passButtonPressed(){
        operationPanel.setOperator(this, nullOperation);
        gameObject.sendCommand("pass");
    }
    
    public void resignButtonPressed(){
        operationPanel.setOperator(this, nullOperation);
        gameObject.sendCommand("resign");
    }
    
    public void undoButtonPressed(){
        operationPanel.setOperator(this, nullOperation);
        gameObject.sendCommand("undo");
    }
    
    public void doneButtonPressed(){
        gameObject.sendCommand("done");
    }
    
    public void adjournButtonPressed(){
        gameObject.sendCommand("adjourn");
    }
}