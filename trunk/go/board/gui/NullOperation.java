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

package go.board.gui;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class NullOperation extends BoardOperation{
    
    public NullOperation(){
        super(null);    
    }

    void draw(Graphics2D g, BoardPanel boardPanel) {
        // 何も描かない
    }
    
    void mousePressed(int x, int y, BoardPanel boardPanel, MouseEvent e){
    }
    
    void mouseReleased(int col, int row, BoardPanel boardPanel,MouseEvent e) {
        // 何もしない
    }
    
    void mouseMoved(int x, int y, BoardPanel boardPanel) {
        // 何もしない
    }

    void wheelUp(BoardPanel boardPanel, MouseWheelEvent e) {
        // 何もしない
    }

    void wheelDown(BoardPanel boardPanel, MouseWheelEvent e) {
        // 何もしない
    }
}