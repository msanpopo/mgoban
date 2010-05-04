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

package go.board.gui.image;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.RadialGradientPaint;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public class WhiteStone extends GoBufferedImage{
    public WhiteStone() {
    }
    
    void createImage(Graphics2D g, int size){
        float r = size / 2.0f;
        
        float r2 = size * 0.9f;	// 直径
        float a0 = (size - r2) / 2.0f;
        
        AlphaComposite orig = (AlphaComposite) g.getComposite();
        AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);
        
        g.setComposite(alpha);
        
        float offset = a0;
        g.setPaint(new Color(0, 0, 0));
        g.fill(new Ellipse2D.Double(a0 + offset, a0 + offset, r2, r2));
        
        g.setComposite(orig);
        
        Color c0 = new Color(255, 255, 255);
        Color c1 = new Color(190, 190, 190);
        
        Point2D center = new Point2D.Double(r - (r2 * 0.1), r - (r2 * 0.1));
        float radius = r2 * 0.54f;
        Point2D focus = new Point2D.Double(r - (r2 * 0.1), r - (r2 * 0.1));
        float[] dist = {0.0f, 1.0f};
        Color[] colors = {c0, c1};
        RadialGradientPaint p = new RadialGradientPaint(center, radius, focus, dist, colors, CycleMethod.NO_CYCLE);
        
        
        g.setPaint(p);
        g.fill(new Ellipse2D.Double(a0, a0, r2, r2));
    }
}