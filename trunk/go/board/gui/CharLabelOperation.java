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

import go.board.gui.image.LabelImage;
import go.GoVertex;
import go.board.FieldType;
import go.board.GoBoard;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class CharLabelOperation extends BoardOperation{
    private static final String az = "abcdefghijklmnopqrstuvwxyz";
    
    private CharLabelOperator operator;
    private int index;
    
    public CharLabelOperation(GoBoard board, CharLabelOperator operator) {
        super(board);
        
        this.operator = operator;
        this.index = 0;
    }
    
    public void reset(){
        index = 0;
    }
    
    void draw(Graphics2D g, BoardPanel boardPanel) {
        if(isVisible && board.checkRange(cursorX, cursorY) == true){
            LabelImage cursor = boardPanel.getLabelImage();
            String str = String.valueOf(az.charAt(index));
            FieldType type = board.getType(cursorX, cursorY);
            Color color;
            switch(type){
                case BLACK:
                    color = Color.WHITE;
                    break;
                default:
                    color = Color.BLACK;
                    break;
            }
            cursor.draw(g, cursorX, cursorY, color, str, 0.6);
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
            operator.charLabelGenerated(v, az.charAt(index));
            if(index < az.length() - 1){
                index += 1;
            }
            boardPanel.repaintCursor();
        }
    }
    
    void wheelUp(BoardPanel boardPanel, MouseWheelEvent e) {
        if(index > 0){
            index -= 1;
            boardPanel.repaintCursor();
        }
    }
    
    void wheelDown(BoardPanel boardPanel, MouseWheelEvent e) {
        if(index < az.length() - 1){
            index += 1;
            boardPanel.repaintCursor();
        }
    }
}