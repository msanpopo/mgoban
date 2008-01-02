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
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SaveAsAction extends AbstractAction{
    private static final String SAVE_AS = "別名で保存";
    
    private static final String ICON = "image/document-save-as.png";
       
    private GoGame goGame;
    
    public SaveAsAction(GoGame goGame) {
        ClassLoader cl = this.getClass().getClassLoader();
        Icon icon  = new ImageIcon(cl.getResource(ICON));
        
        putValue(Action.NAME, SAVE_AS);
        putValue(Action.SHORT_DESCRIPTION, SAVE_AS);
        putValue(Action.SMALL_ICON, icon);
        putValue(Action.LARGE_ICON_KEY, icon);
        
        this.goGame = goGame;
    }
    
    public void actionPerformed(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("SGF file", "sgf");
        
        chooser.setFileFilter(filter);
        
        int returnVal = chooser.showSaveDialog(null);
        
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            System.out.println("You chose : " + file.getName());
            
            String name = file.getName();
            if(name.matches(".+\\.sgf$") == false){
                file = new File(file.getParentFile(), name + ".sgf");
                System.out.println("file new name:" + file.getAbsolutePath());
            }
            
            goGame.getGameTree().save(file);
            goGame.getWindow().setTitle(file.getName());
        }
    }
}