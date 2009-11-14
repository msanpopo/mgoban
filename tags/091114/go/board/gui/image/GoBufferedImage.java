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

package go.board.gui.image;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public abstract class GoBufferedImage implements GoImage{
    private int size;   // 一辺の長さ
    private BufferedImage image;
    
    public GoBufferedImage() {
        size = 0;
        image = null;
    }
    
    public BufferedImage getBufferedImage(){
        return image;
    }
    
    public void setSize(int s){
        size = s;
        image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        
        Graphics2D g = image.createGraphics();
                
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHints(rh);
        g.setPaint(new Color(0, 0, 0, 0));
        g.fill(new Rectangle(size, size));
        
        createImage(g, size);
    }
    
    abstract void createImage(Graphics2D g, int size);
    
    /*
     * row, col : 1 - 19
     */
    public void draw(Graphics2D g2, int col, int row){
        int x = col * size;
        int y = row * size;
        
        g2.drawImage(image, null, x, y);
    }
}