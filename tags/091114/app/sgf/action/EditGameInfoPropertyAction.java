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

import go.gui.GameInfoPropertyPanel1;
import go.gui.GameInfoPropertyPanel2;
import go.GoGame;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import sgf.GameTree;
import sgf.GoNode;
import sgf.property.GameInfoProperty;

public class EditGameInfoPropertyAction extends AbstractAction {

    private static final String OK = java.util.ResourceBundle.getBundle("app/resource/Resource").getString("OK");
    private static final String EDIT_GAME_INFO = java.util.ResourceBundle.getBundle("app/resource/Resource").getString("Edit");
    
    private static final String NO1 = java.util.ResourceBundle.getBundle("app/resource/Resource").getString("A");
    private static final String NO2 = java.util.ResourceBundle.getBundle("app/resource/Resource").getString("B");
    private GoGame goGame;

    public EditGameInfoPropertyAction(GoGame goGame) {
        putValue(Action.NAME, EDIT_GAME_INFO);

        this.goGame = goGame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GoNode node = goGame.getGameTree().getCurrentNode();
        GameInfoProperty gip;

        gip = node.getGameInfoProperty();
        
        GameInfoPropertyPanel1 panel1 = new GameInfoPropertyPanel1(gip);
        GameInfoPropertyPanel2 panel2 = new GameInfoPropertyPanel2(gip);
        JTabbedPane pane = new JTabbedPane();
        pane.add(NO1, panel1);
        pane.add(NO2, panel2);
        
        String[] options = {OK};
        int retval = JOptionPane.showOptionDialog(goGame.getWindow(), pane, "Edit game info property",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);

        if (retval == 0) {
            panel1.done();
            panel2.done();
            GameTree tree = goGame.getGameTree();
            tree.fireNodeStateChanged();
        }
    }
}