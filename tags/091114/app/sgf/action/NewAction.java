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

package app.sgf.action;

import app.sgf.*;
import go.GoGame;
import go.GoState;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import sgf.GameTree;
import sgf.GoNode;

public class NewAction extends AbstractAction{
    private static final String OK = java.util.ResourceBundle.getBundle("app/resource/Resource").getString("OK");
    private static final String CANCEL = java.util.ResourceBundle.getBundle("app/resource/Resource").getString("Cancel");
    
    private static final String NEW = java.util.ResourceBundle.getBundle("app/resource/Resource").getString("New");
    
    private static final String ICON = "image/document-new.png";
    
    private GoGame goGame;
    
    public NewAction(GoGame goGame) {
        ClassLoader cl = this.getClass().getClassLoader();
        Icon icon  = new ImageIcon(cl.getResource(ICON));
        
        putValue(Action.NAME, NEW);
        putValue(Action.SHORT_DESCRIPTION, NEW);
        putValue(Action.SMALL_ICON, icon);
        putValue(Action.LARGE_ICON_KEY, icon);
        
        this.goGame = goGame;
    }
    
    public void actionPerformed(ActionEvent e) {
        NewGameTreePanel panel = new NewGameTreePanel();
        
        String[] options = {OK, CANCEL};
        int retval = JOptionPane.showOptionDialog(goGame.getWindow(), panel, "New Game Tree",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);

        if(retval == 0){
            goGame.setGoState(GoState.EDIT);
            
            GoNode root = panel.getRootNode();
            
            GameTree tree = goGame.getGameTree();
            tree.setNewTree(root);
            goGame.start();
        }
    }
}