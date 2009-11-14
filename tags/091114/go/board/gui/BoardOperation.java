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
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public abstract class BoardOperation {
    protected GoBoard board;
    protected int cursorX;
    protected int cursorY;
    protected boolean isVisible;
    
    public BoardOperation(GoBoard board) {
        this.board = board;
        this.cursorX = 0;
        this.cursorY = 0;
        this.isVisible = false;
    }

    public void setVisible(boolean bool){
        isVisible = bool;
        cursorX = 0;
        cursorY = 0;
    }
    
    // 操作の状態を BoardPanel に表示する。
    // 普通はカーソルを表示する。
    abstract void draw(Graphics2D g, BoardPanel boardPanel);
    
    abstract void mouseMoved(int x, int y, BoardPanel boardPanel);
    
    abstract void mousePressed(int x, int y, BoardPanel boardPanel, MouseEvent e);
    abstract void mouseReleased(int x, int y, BoardPanel boardPanel, MouseEvent e);
    
    abstract void wheelUp(BoardPanel boardPanel, MouseWheelEvent e);
    abstract void wheelDown(BoardPanel boardPanel, MouseWheelEvent e);
}