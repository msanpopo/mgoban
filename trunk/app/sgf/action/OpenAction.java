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
import go.gui.FileChooserAccessory;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import sgf.GameTree;

public class OpenAction extends AbstractAction {

    private static final String OPEN = java.util.ResourceBundle.getBundle("app/resource/Resource").getString("Open");
    private static final String ICON = "image/document-open.png";
    private GoGame goGame;

    public OpenAction(GoGame goGame) {
        ClassLoader cl = this.getClass().getClassLoader();
        Icon icon = new ImageIcon(cl.getResource(ICON));

        putValue(Action.NAME, OPEN);
        putValue(Action.SHORT_DESCRIPTION, OPEN);
        putValue(Action.SMALL_ICON, icon);
        putValue(Action.LARGE_ICON_KEY, icon);

        this.goGame = goGame;
    }

    public void actionPerformed(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        FileChooserAccessory fsAccessory = new FileChooserAccessory(chooser);
        
        FileNameExtensionFilter filter = new FileNameExtensionFilter("SGF file", "sgf");
        
        chooser.setAccessory(fsAccessory);
        chooser.setFileFilter(filter);

        int returnVal = chooser.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            goGame.setGoState(GoState.EDIT);

            System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
            File sgfFile = chooser.getSelectedFile();
            
            String charset = fsAccessory.getCharset();
            System.out.println("charset:" + charset);
            doAction(sgfFile, charset);
        }
    }

    public void doAction(File file){
        doAction(file, "UTF-8");
    }
    
    public void doAction(File file, String charset) {
        System.out.println("OpenAction.doAction:" + file);
        if (file.exists() == false) {
            return;
        }

        GameTree tree = goGame.getGameTree();
        tree.setNewTree(file, charset);
        goGame.getWindow().setTitle(file.getName());
        goGame.start();
    }
}