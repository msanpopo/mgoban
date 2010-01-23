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

package go.controller;

import go.board.gui.SetupOperation;
import go.board.gui.SetupOperator;
import go.gui.OperationPanel;
import go.GoGame;
import go.GoVertex;
import go.board.GoBoard;
import sgf.GoNode;
import sgf.property.SetupProperty;

public class SetupController implements SetupOperator{
    private GoGame goGame;
    private OperationPanel operationPanel;
    
    private SetupOperation operation;
    
    public SetupController(GoGame goGame, OperationPanel operationPanel) {
        this.goGame = goGame;
        this.operationPanel = operationPanel;
        
        this.operation = new SetupOperation(goGame.getBoard(), this);
    }
    
    public void start(GoGame goGame){
        GoNode node = goGame.getGameTree().getCurrentNode();
        if(node.isMoveNode()){
            GoNode newNode = new GoNode();
            goGame.getGameTree().insertAfter(newNode);
        }
        operationPanel.setOperator(null, operation);
    }
    
    public void addBlackStone(GoVertex v) {
        SetupProperty sp = getSetupProperty();
        
        sp.addBlack(v);
        
        goGame.getGameTree().fireNodeStateChanged();
    }
    
    public void addWhiteStone(GoVertex v) {
        SetupProperty sp = getSetupProperty();
        
        sp.addWhite(v);
        
        goGame.getGameTree().fireNodeStateChanged();
    }
    
    public void removeStone(GoVertex v) {
        SetupProperty sp = getSetupProperty();
        
        GoBoard board = goGame.getBoard();
        if(board.isEmpty(v.getX(), v.getY()) && sp.hasEmptyPoint(v) == false){
            // 画面上が空で AE 要素もないところには何もできない
        }else{
            // 石のある所はそれを削除する。画面上は空だが AE 要素がある所は AE 要素を削除する。
            sp.removeStone(v);
            goGame.getGameTree().fireNodeStateChanged();
        }
    }
    
    private SetupProperty getSetupProperty(){
        GoNode node = goGame.getGameTree().getCurrentNode();
        SetupProperty sp;
        if(node.hasSetupProperty() == false){
            if(node.hasMoveProperty()){
                GoNode newNode = new GoNode();
                goGame.getGameTree().insertAfter(newNode);
                node = newNode;
            }
            sp = new SetupProperty();
            node.setSetupProperty(sp);
        }else{
            sp = node.getSetupProperty();
        }
        return sp;
    }
}