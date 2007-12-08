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

package go.board.gui.image;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

public class CircleImage extends GoSimpleImage{
    public CircleImage(Color c) {
        super(c);
    }
    
    public void draw(Graphics2D g, int col, int row) {
        draw(g, col, row, color);
    }
    
    public void draw(Graphics2D g, int col, int row, Color color){
        int x = col * size;
        int y = row * size;
        
        AffineTransform orig = g.getTransform();
        g.translate(x, y);
        {
            g.setPaint(color);
            
            double r2 = size * 0.6;
            double a0 = (size - r2) / 2.0;
            
            RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHints(rh);
            
            BasicStroke line = new BasicStroke(size * 0.08f);
            g.setStroke(line);
            g.draw(new Ellipse2D.Double(a0, a0, r2, r2));
        }
        g.setTransform(orig);
    }
}