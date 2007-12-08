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

import java.awt.Color;
import java.awt.Graphics2D;

public abstract class GoSimpleImage implements GoImage{
    protected int size;   // 一辺の長さ
    protected Color color;
    
    public GoSimpleImage(Color c) {
        this.size = 0;
        this.color = c;
    }
    
    public void setSize(int size) {
        this.size = size;
    }
    
    public void draw(Graphics2D g, int col, int row) {
        draw(g, col, row, color);
    }
   
    public void draw(Graphics2D g, int col, int row, Color color){
        ;
    }
}