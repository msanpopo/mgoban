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

package app.sgf.action;

import go.GoGame;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import sgf.GameTree;

public class DeleteNodeAction extends AbstractAction{
    private static final String DELETE = "ノード削除";
    
    private static final String ICON = "image/edit-delete.png";
    
    private GoGame goGame;
    
    public DeleteNodeAction(GoGame goGame) {
        ClassLoader cl = this.getClass().getClassLoader();
        Icon icon  = new ImageIcon(cl.getResource(ICON));
        
        putValue(Action.NAME, DELETE);
        putValue(Action.SHORT_DESCRIPTION, DELETE);
        putValue(Action.LARGE_ICON_KEY, icon);
        
        this.goGame = goGame;
    }
    
    public void actionPerformed(ActionEvent e) {
        GameTree tree = goGame.getGameTree();
        tree.removeCurrentNode();
    }
}