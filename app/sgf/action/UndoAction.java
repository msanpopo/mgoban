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

package app.sgf.action;

import go.GoGame;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;

public class UndoAction extends AbstractAction{
    private static final String UNDO = "戻す";
    
    private GoGame goGame;
    private UndoOperator operator;
    

    public UndoAction(GoGame goGame) {
        this.goGame = goGame;
        this.operator = null;
        
        putValue(Action.NAME, UNDO);
    }
    
    public void actionPerformed(ActionEvent e) {
        if(operator != null){
            operator.undo();
        }
    }
    
    public void setOperator(UndoOperator operator){
        this.operator = operator;
        if(operator == null){
            setEnabled(false);
        }else{
            setEnabled(true);
        }
    }
}