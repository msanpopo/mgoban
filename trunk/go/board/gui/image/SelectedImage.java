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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;

public class SelectedImage extends GoSimpleImage{
    
    public SelectedImage(Color c) {
        super(c);
    }
    
    @Override
    public void draw(Graphics2D g, int col, int row, Color color){
        int x = col * size;
        int y = row * size;
        
        AffineTransform orig = g.getTransform();
        g.translate(x, y);
        {
            g.setPaint(color);
            
            double x0 = size * 0.25;
            double y0 = size * 0.3;
            double x1 = size * 0.55;
            double y1 = size * 0.75;
            double x2 = size * 0.75;
            double y2 = size * 0.5;
            
            RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHints(rh);
            
            BasicStroke line = new BasicStroke(size * 0.075f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
            g.setStroke(line);

            int[] xa = {(int)x0, (int)x1, (int)x2};
            int[] ya = {(int)y0, (int)y1, (int)y2};
            g.drawPolyline(xa, ya, xa.length);
        }
        g.setTransform(orig);
    }
}