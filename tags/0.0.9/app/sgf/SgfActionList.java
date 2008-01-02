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

package app.sgf;

import app.sgf.action.AddGameInfoPropertyAction;
import app.sgf.action.AddNoTypePropertyAction;
import app.sgf.action.AddSetupPropertyAction;
import app.sgf.action.AddTreeAction;
import app.sgf.action.GoParentAction;
import app.sgf.action.GoBottomAction;
import app.sgf.action.CloseAction;
import app.sgf.action.DeleteNodeAction;
import app.sgf.action.EditMovePropertyAction;
import app.sgf.action.EditGameInfoPropertyAction;
import app.sgf.action.EditNoTypePropertyAction;
import app.sgf.action.EditRootPropertyAction;
import app.sgf.action.EditSetupPropertyAction;
import app.sgf.action.GoChildAction;
import app.sgf.action.InsertNodeAfterAction;
import app.sgf.action.NewAction;
import app.sgf.action.GoNextAction;
import app.sgf.action.OpenAction;
import app.sgf.action.PauseGameAction;
import app.sgf.action.GoPrevAction;
import app.sgf.action.SaveAction;
import app.sgf.action.SaveAsAction;
import app.sgf.action.StartGameAction;
import app.sgf.action.StopGameAction;
import app.sgf.action.GoTopAction;
import app.sgf.action.InsertNodeBeforeAction;
import app.sgf.action.PivotAction;
import app.sgf.action.SaveImageAction;
import go.GoClock;
import go.GoGame;
import go.GoGameListener;
import go.GoState;
import javax.swing.Action;
import sgf.GameTree;
import sgf.GoNode;

public class SgfActionList implements GoGameListener{
    public Action closeAction;
    
    public Action newAction;
    public OpenAction openAction;
    public Action saveAction;
    public Action saveAsAction;
    public SaveImageAction saveImageAction;
    public Action addRootNodeAction;
    
    public Action editRootAction;
    public Action editMoveAction;
    public Action editNoTypeAction;
    public Action editGameInfoAction;
    public Action editSetupAction;
    
    public Action addNoTypeAction;
    public Action addGameInfoAction;
    public Action addSetupAction;
    
    public Action deleteNodeAction;
    public Action insertNodeAfterAction;
    public Action insertNodeBeforeAction;
    
    public Action pivotAction;
    
    public Action goTopAction;
    public Action goBottomAction;
    public Action goParentAction;
    public Action goChildAction;
    public Action goPrevAction;
    public Action goNextAction;
    
    public Action startAction;
    public Action pauseAction;
    public Action stopAction;
    
    public SgfActionList(SgfGoGame goGame) {
        closeAction = new CloseAction(goGame);
        
        newAction = new NewAction(goGame);
        openAction = new OpenAction(goGame);
        saveAsAction = new SaveAsAction(goGame);
        saveAction = new SaveAction(goGame, saveAsAction);
        saveImageAction = new SaveImageAction(goGame);
        
        addRootNodeAction = new AddTreeAction(goGame);
        
        editRootAction = new EditRootPropertyAction(goGame);
        editMoveAction = new EditMovePropertyAction(goGame);
        editNoTypeAction = new EditNoTypePropertyAction(goGame);
        editGameInfoAction = new EditGameInfoPropertyAction(goGame);
        editSetupAction = new EditSetupPropertyAction(goGame);
        
        addNoTypeAction = new AddNoTypePropertyAction(goGame);
        addGameInfoAction = new AddGameInfoPropertyAction(goGame);
        addSetupAction = new AddSetupPropertyAction(goGame);
        
        deleteNodeAction = new DeleteNodeAction(goGame);
        insertNodeAfterAction = new InsertNodeAfterAction(goGame);
        insertNodeBeforeAction = new InsertNodeBeforeAction(goGame);
        
        pivotAction = new PivotAction(goGame);
        
        goTopAction = new GoTopAction(goGame);
        goBottomAction = new GoBottomAction(goGame);
        goParentAction = new GoParentAction(goGame);
        goChildAction = new GoChildAction(goGame);
        goPrevAction = new GoPrevAction(goGame);
        goNextAction = new GoNextAction(goGame);
        
        startAction = new StartGameAction(goGame);
        pauseAction = new PauseGameAction(goGame);
        stopAction = new StopGameAction(goGame);
    }

    private void updateAll(GoGame goGame) {
        GameTree tree = goGame.getGameTree();
        GoNode current = tree.getCurrentNode();
        GoState state = goGame.getGoState();
        
        boolean lastRoot = current.isRoot() && !current.hasSibling();     // 最後のルートノードで削除不可
        
        if(state == GoState.EDIT){
            editRootAction.setEnabled(true);
            editMoveAction.setEnabled(current.hasMoveProperty());
            editNoTypeAction.setEnabled(true);
            editGameInfoAction.setEnabled(current.hasGameInfoProperty());
            editSetupAction.setEnabled(current.hasSetupProperty());
            
            addNoTypeAction.setEnabled(!current.hasNoTypeProperty());
            addGameInfoAction.setEnabled(!current.hasGameInfoProperty());
            addSetupAction.setEnabled(!current.hasSetupProperty() && !current.hasMoveProperty());
            
            addRootNodeAction.setEnabled(true);
            deleteNodeAction.setEnabled(!lastRoot);
            insertNodeAfterAction.setEnabled(true);
            insertNodeBeforeAction.setEnabled(!current.isRoot());
            
            pivotAction.setEnabled(true);
            
            goTopAction.setEnabled(current.hasParent() && tree.checkPivot() == false);
            goParentAction.setEnabled(current.hasParent() && tree.checkPivot() == false);
            goChildAction.setEnabled(current.hasChild());
            goBottomAction.setEnabled(current.hasChild());
            goPrevAction.setEnabled(current.hasPrev());
            goNextAction.setEnabled(current.hasNext());
        }else{
            editRootAction.setEnabled(false);
            editMoveAction.setEnabled(false);
            editNoTypeAction.setEnabled(false);
            editGameInfoAction.setEnabled(false);
            editSetupAction.setEnabled(false);
            
            addNoTypeAction.setEnabled(false);
            addGameInfoAction.setEnabled(false);
            addSetupAction.setEnabled(false);
            
            addRootNodeAction.setEnabled(false);
            deleteNodeAction.setEnabled(false);
            insertNodeAfterAction.setEnabled(false);
            insertNodeBeforeAction.setEnabled(false);
            
            pivotAction.setEnabled(false);
            
            goTopAction.setEnabled(false);
            goParentAction.setEnabled(false);
            goChildAction.setEnabled(false);
            goBottomAction.setEnabled(false);
            goPrevAction.setEnabled(false);
            goNextAction.setEnabled(false);
        }
        
        if(state == GoState.GTP_GAME){
            startAction.setEnabled(false);
            pauseAction.setEnabled(false);
            stopAction.setEnabled(true);
        }else if(state == GoState.SCORE){
            startAction.setEnabled(false);
            pauseAction.setEnabled(false);
            stopAction.setEnabled(false);
        }else{
            startAction.setEnabled(true);
            pauseAction.setEnabled(false);
            stopAction.setEnabled(false);
        }
    }
        
    public void updateClock(GoClock clock) {}
    
    public void treeChanged(GoGame goGame, int boardSize) {
        updateAll(goGame);
    }
    public void nodeMoved(GoGame goGame, GoNode old) {
        updateAll(goGame);
    }
    public void nodeStateChanged(GoGame goGame) {
        updateAll(goGame);
    }
    
    public void goStateChanged(GoGame goGame, GoState state){
        updateAll(goGame);
    }
}