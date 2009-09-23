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

import go.board.gui.image.GoSimpleImage;
import go.GoVertex;
import go.board.GoBoard;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class ScoringOperation extends BoardOperation{
    private static final Color COLOR = Color.RED;
    
    private ScoringOperator operator;
    
    public ScoringOperation(GoBoard board, ScoringOperator operator) {
        super(board);
        
        this.operator = operator;
    }
    
    void draw(Graphics2D g, BoardPanel boardPanel) {
        if(isVisible && board.checkRange(cursorX, cursorY) == true){
            GoSimpleImage cursor = boardPanel.getMarkImage(MarkType.MARK);
            cursor.draw(g, cursorX, cursorY, COLOR);
        }
    }
    
    void mouseMoved(int x, int y, BoardPanel boardPanel) {
        if(board.checkRange(x, y) == true && board.isStone(x, y) == true){
            this.cursorX = x;
            this.cursorY = y;
            this.isVisible = true;
        }else{
            this.isVisible = false;
        }
        boardPanel.repaintCursor();
    }
    
    void mousePressed(int x, int y, BoardPanel boardPanel, MouseEvent e){
    }
    
    void mouseReleased(int x, int y, BoardPanel boardPanel,MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1 && board.checkRange(x, y) == true && board.isStone(x, y) == true){
            GoVertex v = new GoVertex(x, y);
            operator.vertexGenerated(v);
        }
    }
    
    void wheelUp(BoardPanel boardPanel, MouseWheelEvent e) {
        //operator.wheelUp();
    }
    
    void wheelDown(BoardPanel boardPanel, MouseWheelEvent e) {
        //operator.wheelDown();
    }
}