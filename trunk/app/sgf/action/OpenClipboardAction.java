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
import go.GoState;
import go.gui.ClipboardPanel;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import sgf.GameTree;

public class OpenClipboardAction extends AbstractAction {

    private static final String OK = java.util.ResourceBundle.getBundle("app/resource/Resource").getString("OK");
    private static final String OPEN_CLIPBOARD = java.util.ResourceBundle.getBundle("app/resource/Resource").getString("OpenClipboard");
//    private static final String ICON = "image/document-open.png";
    private GoGame goGame;

    public OpenClipboardAction(GoGame goGame) {
        ClassLoader cl = this.getClass().getClassLoader();
//        Icon icon = new ImageIcon(cl.getResource(ICON));

        putValue(Action.NAME, OPEN_CLIPBOARD);
        putValue(Action.SHORT_DESCRIPTION, OPEN_CLIPBOARD);
//        putValue(Action.SMALL_ICON, icon);
//        putValue(Action.LARGE_ICON_KEY, icon);

        this.goGame = goGame;
    }

    public void actionPerformed(ActionEvent e) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable object = clipboard.getContents(null);
        String clipText = "";
        try {
            clipText = (String) object.getTransferData(DataFlavor.stringFlavor);
            System.out.println(clipText);
        } catch (UnsupportedFlavorException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        ClipboardPanel panel = new ClipboardPanel();

        panel.setText(clipText);

        String[] options = {OK};
        int retval = JOptionPane.showOptionDialog(goGame.getWindow(), panel, java.util.ResourceBundle.getBundle("app/resource/Resource").getString("MoveProperty"),
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);

        if (retval == 0) {
            String text = panel.getText();

            if (text.isEmpty() == false) {
                goGame.setGoState(GoState.EDIT);

                GameTree tree = goGame.getGameTree();
                tree.setNewTree(text);
                goGame.getWindow().setTitle("");
                goGame.start();
            } else {
                System.out.println("textarea is empty");
            }
        }
    }
}
