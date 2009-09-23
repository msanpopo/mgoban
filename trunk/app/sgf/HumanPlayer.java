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

package app.sgf;

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
import go.GoState;
import go.GoVertex;
import go.board.GoBoard;
import go.controller.ScoringController;

public class HumanPlayer extends GtpPlayer implements GoOperator, MoveOperator, ScoringOperator{
    private OperationPanel operationPanel;
    
    private BoardOperation nullOperation;
    private BoardOperation moveOperation;
    private BoardOperation scoringOperation;
    
    private boolean pass;
    
    public HumanPlayer(GoColor c, GoBoard board, OperationPanel operationPanel){
        super(c);
        this.operationPanel = operationPanel;
        
        this.nullOperation = new NullOperation();
        this.moveOperation = new MoveOperation(board, this, c);
        this.scoringOperation = new ScoringOperation(board, this);
        
        this.pass = false;
    }
    
    @Override
    public void genMove(GoClock clock){
        operationPanel.setOperator(this, moveOperation);
    }
    
    @Override
    public void opponentMove(GoMove move){
        if(pass && move.isPass()){
            pass = false;
            gameController.changeState(GoState.SCORE);
        }
    }
    
    @Override
    public void doScoring(ScoringController con) {
        scoringController = con;
        operationPanel.setOperator(this, scoringOperation);
    }
    
    @Override
    public void stopScoring() {
        operationPanel.setOperator(null, nullOperation);
        scoringController = null;
    }
    
    public void moveGenerated(GoMove move){
        operationPanel.setOperator(null, nullOperation);
        
        gameController.move(move);
    }
    
    public void vertexGenerated(GoVertex vertex){
        scoringController.toggleDeadStoneGroup(vertex);
    }
    
    public void wheelUp(boolean isShiftDown){}
    public void wheelDown(boolean isShiftDown){}
    
    public void passButtonPressed() {
        operationPanel.setOperator(null, nullOperation);
        GoMove move = new GoMove(color, "");
        
        pass = true;
        
        gameController.move(move);
    }
    
    public void undoButtonPressed() {
        operationPanel.setOperator(null, nullOperation);
        
        if(scoringController == null){
            gameController.requestUndo(color, 2);
        }else{
            // TODO スコアモードの undo に未対応
        }
    }
    
    public void resignButtonPressed() {
    }
    
    public void doneButtonPressed(){
        GoResult result = scoringController.getLocalResult();
        scoringController.setResultAndDone(this, result);
    }

    public void adjournButtonPressed() {}
}