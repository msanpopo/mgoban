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

import go.board.gui.image.GoImage;
import go.GoColor;
import go.GoMove;
import go.GoVertex;
import go.board.GoBoard;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import sgf.GoNode;

public class EditOperation extends BoardOperation implements ActionListener {

    private static final String JUMP = "移動";
    private EditOperator operator;
    private GoColor color;
    private JPopupMenu popupMenu;
    private JMenuItem jumpMenuItem;
    
    private int popupX, popupY;

    public EditOperation(GoBoard board, EditOperator operator, GoColor color) {
        super(board);

        this.operator = operator;
        this.color = color;
        this.popupMenu = new JPopupMenu();

        jumpMenuItem = new JMenuItem(JUMP);
        jumpMenuItem.addActionListener(this);
        this.popupMenu.add(jumpMenuItem);
    }

    void draw(Graphics2D g, BoardPanel boardPanel) {
        if (isVisible && board.checkRange(cursorX, cursorY) == true) {
            GoImage cursor = boardPanel.getStoneImage(color);
            cursor.draw(g, cursorX, cursorY);
        }
    }

    void mouseMoved(int x, int y, BoardPanel boardPanel) {
        this.cursorX = x;
        this.cursorY = y;
        
        if (/* checkValidation 内で範囲チェックしてる board.checkRange(x, y) == true && */board.isValidMove(x, y, color) == true) {
            this.isVisible = true;
        } else {
            this.isVisible = false;
        }
        boardPanel.repaintCursor();
    }

    void mousePressed(int x, int y, BoardPanel boardPanel, MouseEvent e) {
        checkPopup(e, x, y);
    }

    void mouseReleased(int x, int y, BoardPanel boardPanel, MouseEvent e) {
        if (checkPopup(e, x, y)) {

        } else if (e.getButton() == MouseEvent.BUTTON1 && board.isValidMove(x, y, color) == true) {
            GoMove m = new GoMove(color, x, y);
            operator.moveGenerated(m);
        }
    }

    void wheelUp(BoardPanel boardPanel, MouseWheelEvent e) {
        operator.wheelUp(e.isShiftDown());
    }

    void wheelDown(BoardPanel boardPanel, MouseWheelEvent e) {
        operator.wheelDown(e.isShiftDown());
    }

    private boolean checkPopup(MouseEvent e, int x, int y) {
        if (board.isStone(x, y) && e.isPopupTrigger()) {
            popupMenu.show(e.getComponent(), e.getX(), e.getY());
            popupX = x;
            popupY = y;
            return true;
        } else {
            return false;
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jumpMenuItem) {
            GoVertex vertex = new GoVertex(popupX, popupY);
            operator.select(vertex);
            popupX = 0;
            popupY = 0;
        }
    }
}