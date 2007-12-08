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

package go.board.gui;

import go.board.gui.image.GoSimpleImage;
import go.GoVertex;
import go.board.FieldType;
import go.board.GoBoard;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class MarkOperation extends BoardOperation{
    private MarkOperator operator;
    private MarkType mark;   // 0-4
    
    public MarkOperation(GoBoard board, MarkOperator operator) {
        super(board);
        
        this.operator = operator;
        this.mark = MarkType.CIRCLE;
    }
    
    public void reset(){
        mark = MarkType.CIRCLE;
    }
    
    void draw(Graphics2D g, BoardPanel boardPanel) {
        if(isVisible && board.checkRange(cursorX, cursorY) == true){
            GoSimpleImage cursor = boardPanel.getMarkImage(mark);
            FieldType type = board.getType(cursorX, cursorY);
            Color color;
            if(type.isBlack()){
                color = Color.WHITE;
            }else{
                color = Color.BLACK;
            }
            cursor.draw(g, cursorX, cursorY, color);
        }
    }
    
    void mouseMoved(int x, int y, BoardPanel boardPanel) {
        if(board.checkRange(x, y) == true){
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
        if(e.getButton() == MouseEvent.BUTTON1 && board.checkRange(x, y) == true){
            GoVertex v = new GoVertex(x, y);
            operator.markGenerated(v, mark);
        }
    }
    
    void wheelUp(BoardPanel boardPanel, MouseWheelEvent e) {
        mark = mark.getPrevType();
        
        boardPanel.repaintCursor();
    }
    
    void wheelDown(BoardPanel boardPanel, MouseWheelEvent e) {
        mark = mark.getNextType();
        
        boardPanel.repaintCursor();
    }
}