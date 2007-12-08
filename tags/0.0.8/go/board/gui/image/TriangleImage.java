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
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

public class TriangleImage extends GoSimpleImage{
    public TriangleImage(Color c) {
        super(c);
    }
    
    public void draw(Graphics2D g, int col, int row, Color color){
        int x = col * size;
        int y = row * size;
        
        AffineTransform orig = g.getTransform();
        g.translate(x, y);
        {
            g.setPaint(color);
            
            double a = size / 2.0;
            double r = size * 0.75 / 2.0;
            double x0 = r * Math.cos(2.0 * Math.PI * 90.0 / 360.0);
            double y0 = r * Math.sin(2.0 * Math.PI * 90.0 / 360.0);
            double x1 = r * Math.cos(2.0 * Math.PI * 210.0 / 360.0);
            double y1 = r * Math.sin(2.0 * Math.PI * 210.0 / 360.0);
            double x2 = r * Math.cos(2.0 * Math.PI * 330.0 / 360.0);
            double y2 = r * Math.sin(2.0 * Math.PI * 330.0 / 360.0);
            
            RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHints(rh);
            
            BasicStroke line = new BasicStroke(size * 0.075f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
            g.setStroke(line);
            
            Polygon polygon = new Polygon();
            polygon.addPoint((int)(a - x0), (int)(a - y0));
            polygon.addPoint((int)(a - x1), (int)(a - y1));
            polygon.addPoint((int)(a - x2), (int)(a - y2));
            g.draw(polygon);
        }
        g.setTransform(orig);
    }
}