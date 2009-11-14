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

package go.board.gui;

import go.board.GoBoard;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import sgf.GameTree;

public class PreviewPanel extends JPanel implements PropertyChangeListener {
    private JFileChooser fs;
    private BoardImage image;
    
    private GameTree tree;
    private GoBoard board;
    
    public PreviewPanel(JFileChooser fs) {
        this.fs = fs;
        
        fs.addPropertyChangeListener(this);
        
        this.image = null;
    
        this.tree = new GameTree();
        this.board = new GoBoard(19);
    }
    
    public void propertyChange(PropertyChangeEvent e) {
        String prop = e.getPropertyName();

        if (JFileChooser.SELECTED_FILE_CHANGED_PROPERTY.equals(prop)) {
            File file = (File) e.getNewValue();
            if(file == null){
                return;
            }

            if(file.getName().matches(".+\\.sgf$") == false){
                return;
            }

            tree.setNewTree(file, "UTF-8");
            tree.bottom();
            board.setBoardState(tree);
            int length = getWidth();
            
            image = new BoardImage(board, length);
            System.out.println("PreviewPanel:" + length);
            
            repaint();
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        if(image != null){
            int w = getWidth();
            int h = getHeight();
            int l = image.getWidth();
            int x0 = (w - l) / 2;
            int y0 = (h - l) / 2;
            g2.drawImage(image.getImage(), null, x0, y0);
        }
    }
}