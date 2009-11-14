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

package go.controller;

import go.board.gui.BoardOperation;
import go.board.gui.CharLabelOperation;
import go.board.gui.CharLabelOperator;
import go.board.gui.MarkOperation;
import go.board.gui.MarkOperator;
import go.board.gui.MarkType;
import go.board.gui.NumberLabelOperation;
import go.board.gui.NumberLabelOperator;
import go.gui.OperationPanel;
import go.GoGame;
import go.GoState;
import go.GoVertex;
import sgf.GoNode;
import sgf.property.NoTypeProperty;

public class MarkController implements MarkOperator, CharLabelOperator, NumberLabelOperator{
    private GoGame goGame;
    private OperationPanel operationPanel;
    
    private MarkOperation markOperation;
    private CharLabelOperation charOperation;
    private NumberLabelOperation numOperation;
    
    private BoardOperation current;
    
    public MarkController(GoGame goGame, OperationPanel operationPanel) {
        this.goGame = goGame;
        this.operationPanel = operationPanel;
        
        this.markOperation = new MarkOperation(goGame.getBoard(), this);
        this.charOperation = new CharLabelOperation(goGame.getBoard(), this);
        this.numOperation = new NumberLabelOperation(goGame.getBoard(), this);
        
        current = markOperation;
    }
    
    public void setMarkType(GoState state){
        switch(state){
            case MARK:
                markOperation.reset();
                current = markOperation;
                break;
            case CHAR_LABEL:
                charOperation.reset();
                current = charOperation;
                break;
            case NUMBER_LABEL:
                numOperation.reset();
                current = numOperation;
                break;
            default:
                break;
        }
    }

    public void start(GoGame goGame){
        operationPanel.setOperator(null, current);
    }
    
    public void markGenerated(GoVertex v, MarkType type) {
        NoTypeProperty ntp = getNoTypeProperty();
        
        MarkType typeOld = goGame.getBoard().getMark(v.getX(), v.getY());
        if(typeOld == null){
            ntp.addMark(v, type);
        }else if(typeOld == type){
            ntp.removeMark(v);
        }else{
            ntp.addMark(v, type);
        }
        
        goGame.getGameTree().fireNodeStateChanged();
    }

    public void charLabelGenerated(GoVertex v, char c) {
        labelGenerated(v, String.valueOf(c));
    }

    public void numberLabelGenerated(GoVertex v, int n) {
        labelGenerated(v, String.valueOf(n));
    }
    
    private void labelGenerated(GoVertex v, String s){
        NoTypeProperty ntp = getNoTypeProperty();
        
        if(goGame.getBoard().hasLabel(v.getX(), v.getY())){
            ntp.removeLabel(v);
        }else{
            ntp.addLabel(v, s);
        }
        
        goGame.getGameTree().fireNodeStateChanged();
    }
    
    private NoTypeProperty getNoTypeProperty(){
        GoNode node = goGame.getGameTree().getCurrentNode();
        NoTypeProperty ntp;
        if(node.hasNoTypeProperty() == false){
            ntp = new NoTypeProperty();
            node.setNoTypeProperty(ntp);
        }else{
            ntp = node.getNoTypeProperty();
        }
        return ntp;
    }
}