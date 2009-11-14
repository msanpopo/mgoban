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

import go.board.gui.BoardOperation;
import go.board.gui.NullOperation;
import go.board.gui.ScoringOperation;
import go.board.gui.ScoringOperator;
import go.gui.GoOperator;
import go.gui.OperationPanel;
import go.GoColor;
import go.GoMove;
import go.GoPlayer;
import go.GoResult;
import go.GoVertex;
import go.board.GoBoard;
import go.board.gui.EditOperation;
import go.board.gui.EditOperator;
import go.controller.EditController;
import go.controller.ScoringController;

public class SgfEditor extends GoPlayer implements GoOperator, EditOperator, ScoringOperator{
    private EditController editController;
    private OperationPanel operationPanel;
    
    private BoardOperation nullOperation;
    private BoardOperation editOperation;
    private BoardOperation scoringOperation;
    
    public SgfEditor(GoColor c, GoBoard board, OperationPanel operationPanel){
        super(c);

        this.editController = null;
        
        this.operationPanel = operationPanel;
        
        this.nullOperation = new NullOperation();
        this.editOperation = new EditOperation(board, this, c);
        this.scoringOperation = new ScoringOperation(board, this);
    }
    
    public void setEditController(EditController controller){
        this.editController = controller;
    }

    public void genMove(){
        operationPanel.setOperator(this, editOperation);
    }
    
    public void opponentMove(GoMove move){}
    
    public void doScoring(ScoringController con) {
        scoringController = con;
        operationPanel.setOperator(this, scoringOperation);
    }
    
    public void stopScoring() {
        operationPanel.setOperator(null, nullOperation);
        scoringController = null;
    }
    
    public void moveGenerated(GoMove move){
        operationPanel.setOperator(null, nullOperation);
        
        editController.move(move);
    }
    
    public void vertexGenerated(GoVertex vertex){
        scoringController.toggleDeadStoneGroup(vertex);
    }
    
    public void wheelUp(boolean isShiftDown){
        if(isShiftDown){
            editController.prevVariation();
        }else{
            editController.backward();
        }
    }
    public void wheelDown(boolean isShiftDown){
        if(isShiftDown){
            editController.nextVariation();
        }else{
            editController.forward();
        }
    }
    
    public void select(GoVertex vertex){
        editController.jump(vertex);
    }
    
    public void passButtonPressed() {
        operationPanel.setOperator(null, nullOperation);
        GoMove move = new GoMove(color, "");
        
        editController.move(move);
    }
    
    public void undoButtonPressed() {}
    public void resignButtonPressed() {}
    
    public void doneButtonPressed(){
        GoResult result = scoringController.getLocalResult();
        scoringController.setResultAndDone(this, result);
    }

    public void adjournButtonPressed() {}
}