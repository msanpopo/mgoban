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

import go.gui.SetupPropertyPanel;
import go.GoGame;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import sgf.GameTree;
import sgf.GoNode;
import sgf.property.SetupProperty;

public class EditSetupPropertyAction extends AbstractAction {

    private static final String OK = java.util.ResourceBundle.getBundle("app/resource/Resource").getString("OK");
    private static final String EDIT_SETUP = java.util.ResourceBundle.getBundle("app/resource/Resource").getString("Edit");
    private GoGame goGame;

    public EditSetupPropertyAction(GoGame goGame) {
        putValue(Action.NAME, EDIT_SETUP);

        this.goGame = goGame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GoNode node = goGame.getGameTree().getCurrentNode();
        SetupProperty sp = node.getSetupProperty();
        if(sp == null){
            return;
        }
        
        SetupPropertyPanel panel = new SetupPropertyPanel(sp);

        String[] options = {OK};
        int retval = JOptionPane.showOptionDialog(goGame.getWindow(), panel, "Edit setup property",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);

        if (retval == 0) {
            panel.done();
            GameTree tree = goGame.getGameTree();
            tree.fireNodeStateChanged();
        }
    }
}