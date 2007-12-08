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

import app.sgf.*;
import go.GoGame;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import sgf.GameTree;
import sgf.GoNode;

public class AddTreeAction extends AbstractAction{
    private static final String OK = "OK";
    private static final String CANCEL = "キャンセル";
    
    private static final String ADD_TREE = "ツリー追加";
    //private static final String ICON = "image/**.png";
    
    private GoGame goGame;

    public AddTreeAction(GoGame goGame) {
        ClassLoader cl = this.getClass().getClassLoader();
        //Icon icon  = new ImageIcon(cl.getResource(ICON));
        
        putValue(Action.NAME, ADD_TREE);
        //putValue(Action.LARGE_ICON_KEY, icon);
        
        this.goGame = goGame;
    }

    public void actionPerformed(ActionEvent e) {
        NewGameTreePanel panel = new NewGameTreePanel();
        
        String[] options = {OK, CANCEL};
        int retval = JOptionPane.showOptionDialog(goGame.getWindow(), panel, "Add Tree",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);

        if(retval == 0){
            GoNode root = panel.getRootNode();
            
            GameTree tree = goGame.getGameTree();
            tree.addRoot(root);
            goGame.start();
        }
    }
}