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
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class LabelImage extends GoSimpleImage{
    public LabelImage(Color c) {
        super(c);
    }

    @Override
    public void draw(Graphics2D g, int col, int row, Color color){
        draw(g, col, row, color, "", 0.4);
    }
    
    public void draw(Graphics2D g, int col, int row, Color color, String str){
        draw(g, col, row, color, str, 0.4);
    }

    public void draw(Graphics2D g, int col, int row, Color color, String str, double ratio){
        int xOffset = col * size;
        int yOffset = row * size;
        
        AffineTransform orig = g.getTransform();
        g.translate(xOffset, yOffset);
        {
            g.setPaint(color);
            
            RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHints(rh);
            
            Font font = new Font(Font.SANS_SERIF, Font.BOLD, (int) (size * ratio));
            g.setFont(font);
            FontRenderContext frc = g.getFontRenderContext();
            
            TextLayout layout = new TextLayout(str, font, frc);
            Rectangle2D bounds = layout.getBounds();

            double x = bounds.getX(); // ごく小さい正の数（ベースライン上の原点から文字画像左端までの距離）
            double y = bounds.getY(); // 約文字の高さの不の数（ベースライン上の原点から文字画像上端までの距離）
            double w = bounds.getWidth();
            double h = bounds.getHeight();
            
            //System.out.println("bounds:" + str + ":x:" + layout.getBounds().getX() + " y:" + layout.getBounds().getY());
            
            double x0 = (size - w) / 2.0 - x;
            double y0 = size * (1.0 - 0.3) ; //(size + h) / 2.0;
            
            layout.draw(g, (float)x0, (float)y0);
        }
        g.setTransform(orig);
    }
}