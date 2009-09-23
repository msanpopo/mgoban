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

import go.GoGame;
import go.gui.SaveImagePanel;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;

public class SaveImageAction extends AbstractAction {
    private static final String OK = "OK";
    private static final String CANCEL = "キャンセル";
    
    private static final String SAVE_IMAGE = "画像で保存";
    private GoGame goGame;

    public SaveImageAction(GoGame goGame) {
        putValue(Action.NAME, SAVE_IMAGE);

        this.goGame = goGame;
    }

    public void actionPerformed(ActionEvent e) {
        SaveImagePanel panel = new SaveImagePanel(goGame);
        
        String[] options = {OK, CANCEL};
        int retval = JOptionPane.showOptionDialog(goGame.getWindow(), panel, "Save Image",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);

        if(retval == 0){
            panel.save();
        }
    }
}