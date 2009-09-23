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

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.TexturePaint;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class WoodTexture {
    private static WoodTexture woodTexture = null;
    
    private BufferedImage texture;
    
    public static WoodTexture getInstance(){
        if(woodTexture == null){
            woodTexture = new WoodTexture();
        }
        return woodTexture;
    }
    
    private WoodTexture() {
        Color c0 = new Color(231, 190, 106);
        Color c1 = new Color(232, 185, 96);
        Color c2 = new Color(216, 168, 82);
        
        Point2D start = new Point2D.Float(0, 0);
        Point2D end = new Point2D.Float(100, 10);
        float[] dist = {0.0f, 0.6f, 1.0f};
        Color[] colors = {c0, c1, c2};
        LinearGradientPaint p = new LinearGradientPaint(start, end, dist, colors);
        
        texture = new BufferedImage(100, 10, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = texture.createGraphics();
        
        g.setPaint(p);
        g.fill(new Rectangle2D.Double(0, 0, 100, 10));
        
    }
    
    public BufferedImage getBufferedImage(){
        return texture;
    }
    /*
    public void paintWood(Graphics2D g2, int x, int y){
        TexturePaint tp = new TexturePaint(texture, new Rectangle2D.Double(0.0, 0.0, 100.0, 10.0));
        
        AffineTransform orig = g2.getTransform();
        {
            double sc = 25.0;
            
            double d = unit * 0.15;
            double xx = (unit * x + d) * sc;
            double yy = (unit * y + d) * sc;
            double w = (unit - 2 * d) * sc;
            
            g2.scale( 1.0 / sc, 1.0 / sc);
            
            g2.setPaint(tp);
            g2.fill(new Rectangle2D.Double(xx, yy, w, w));
        }
        g2.setTransform(orig);
    }
    */
    public void draw(Graphics2D g2, double x, double y, double w, double h){
        TexturePaint tp = new TexturePaint(texture, new Rectangle2D.Double(0.0, 0.0, 100.0, 10.0));
        
        AffineTransform orig = g2.getTransform();
        {
            double sc = 25.0;
            
            g2.scale( 1.0 / sc, 1.0 / sc);
            
            g2.setPaint(tp);
            g2.fill(new Rectangle2D.Double(x * sc, y *sc, w * sc, h * sc));
        }
        g2.setTransform(orig);
    }
}