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

import go.board.gui.image.GoImage;
import go.board.gui.image.GoSimpleImage;
import go.GoColor;
import go.GoVertex;
import go.board.FieldType;
import go.board.GoBoard;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class SetupOperation extends BoardOperation{
    private static final int ADD_BLACK = 0;
    private static final int ADD_WHITE = 1;
    private static final int REMOVE = 2;
    
    private SetupOperator operator;
    
    private int ope;
    
    public SetupOperation(GoBoard board, SetupOperator operator) {
        super(board);
        
        this.operator = operator;
        this.ope = ADD_BLACK;
    }
    
    void draw(Graphics2D g, BoardPanel boardPanel) {
        if(isVisible && board.checkRange(cursorX, cursorY) == true){
            if(ope == ADD_BLACK){
                GoImage cursor = boardPanel.getStoneImage(GoColor.BLACK);
                cursor.draw(g, cursorX, cursorY);
                
            }else if(ope == ADD_WHITE){
                GoImage cursor = boardPanel.getStoneImage(GoColor.WHITE);
                cursor.draw(g, cursorX, cursorY);
                
            }else{
                GoSimpleImage cursor = boardPanel.getMarkImage(MarkType.MARK);
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
                cursor.draw(g, cursorX, cursorY, color);
            }
        }
    }
    
    void mouseMoved(int x, int y, BoardPanel boardPanel) {
        if(board.checkRange(x, y) == true){
            if((ope == ADD_BLACK && board.getType(x, y) != FieldType.BLACK) // ADD_BLACK や白石には上書きできる
            || (ope == ADD_WHITE && board.getType(x, y) != FieldType.WHITE)
            || (ope == REMOVE)){
                this.cursorX = x;
                this.cursorY = y;
                this.isVisible = true;
            }else{
                this.isVisible = false;
            }
        }else{
            this.isVisible = false;
        }
        boardPanel.repaintCursor();
    }
    
    void mousePressed(int x, int y, BoardPanel boardPanel, MouseEvent e){
    }
    
    void mouseReleased(int x, int y, BoardPanel boardPanel, MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1 && board.checkRange(x, y) == true){
            GoVertex v = new GoVertex(x, y);
            if(ope == ADD_BLACK && board.getType(x, y) != FieldType.BLACK){
                operator.addBlackStone(v);
            }else if(ope == ADD_WHITE && board.getType(x, y) != FieldType.WHITE){
                operator.addWhiteStone(v);
            }else if(ope == REMOVE)
                operator.removeStone(v);
        }
    }
    
    
    void wheelUp(BoardPanel boardPanel, MouseWheelEvent e) {
        ope -= 1;
        
        if(ope < 0){
            ope = REMOVE;
        }
        boardPanel.repaintCursor();
    }
    
    void wheelDown(BoardPanel boardPanel, MouseWheelEvent e) {
        ope += 1;
        
        if(ope > REMOVE){
            ope = ADD_BLACK;
        }
        boardPanel.repaintCursor();
    }
}