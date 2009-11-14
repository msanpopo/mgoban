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
package go.gui;

import go.board.gui.image.BlackStone;
import go.board.gui.image.GoBufferedImage;
import go.board.gui.image.WhiteStone;
import go.GoClock;
import go.GoColor;
import go.GoGame;
import go.GoGameListener;
import go.GoState;
import go.board.GoBoard;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import sgf.GameTree;
import sgf.GoNode;
import sgf.property.GameInfoProperty;

public class PlayerInfoPanel extends JPanel implements ComponentListener, GoGameListener {

    private static final int PANEL_HEIGHT = 60;
    private static final Color BGCOLOR0 = new Color(0xf5, 0xf5, 0xdc);
    private static final Color BGCOLOR1 = new Color(0xff, 0xdd, 0xff);
    private static final Color SELECTED_COLOR0 = Color.GREEN;
    private static final Color SELECTED_COLOR1 = Color.RED;
    private Color bgColor;
    private Color selectedColor;
    private GoColor color;
    private String info;
    private int prisoner;
    private String timer;
    private boolean selected;
    private BufferedImage baseImage;
    private BufferedImage largeStone;
    private BufferedImage smallStone;
    private Font font;

    public PlayerInfoPanel(GoColor color) {
        addComponentListener(this);

        this.bgColor = BGCOLOR0;
        this.selectedColor = SELECTED_COLOR0;
        this.color = color;

        clear();

        font = new Font(Font.SANS_SERIF, Font.BOLD, 14);

        this.setPreferredSize(new Dimension(150, PANEL_HEIGHT));
    }

    public void clear() {
        this.info = "???";
        this.prisoner = 0;
        this.timer = "-";

        this.selected = false;
    }

    private boolean setNameAndRank(GameTree tree) {
        boolean changed = false;

        GameInfoProperty gip = tree.getGameInfoProperty();
        if (gip != null) {
            String name = gip.getPlayerName(color);
            String rank = gip.getPlayerRank(color);

            if (name.isEmpty()) {
                name = "???";
            }
            if (rank.isEmpty()) {
                rank = "?";
            }

            StringBuilder str = new StringBuilder();
            String infoTmp = str.append(name).append(" [").append(rank).append("]").toString();
            if (info.equals(infoTmp) == false) {
                info = infoTmp;
                changed = true;
            }
        } else {
            clear();
            changed = true;
        }

        return changed;
    }

    private boolean setPrisoner(GoBoard board) {
        boolean changed = false;

        int prisonerTmp = board.getPrisoner(color.opponentColor());
        if (prisoner != prisonerTmp) {
            prisoner = prisonerTmp;
            changed = true;
        }

        return changed;
    }

    private boolean setSelection(GoGame goGame) {
        boolean changed = false;
        boolean selectedTmp = false;

        GameTree tree = goGame.getGameTree();
        if (tree.getNextColor() == color) {
            selectedTmp = true;
        }

        if (selected != selectedTmp) {
            selected = selectedTmp;
            changed = true;
        }

        return changed;
    }

    private boolean setTimer(GoClock clock) {
        boolean changed = false;
        String timerTmp = clock.getTimeString(color);
        if (timer.equals(timerTmp) == false) {
            timer = timerTmp;
            changed = true;
        }
        return changed;
    }

    private void paintInfo(Graphics2D g2, int w, int h) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setFont(font);
        g2.setPaint(Color.BLACK);
        FontRenderContext frc = g2.getFontRenderContext();

        TextLayout layoutName = new TextLayout(info, font, frc);
        double y0 = h / 2.0 - 9.0;
        layoutName.draw(g2, (float) 30.0, (float) y0);
    }

    private void paintPrisoner(Graphics2D g2, int w, int h) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setFont(font);
        g2.setPaint(Color.BLACK);
        FontRenderContext frc = g2.getFontRenderContext();

        TextLayout layoutHama = new TextLayout(Integer.toString(prisoner), font, frc);
        double wHama = layoutHama.getBounds().getWidth();
        double x1 = w / 3.0 - wHama / 2.0;
        double y1 = h - 12.0;
        layoutHama.draw(g2, (float) 70, (float) y1);
    }

    private void paintTime(Graphics2D g2, int w, int h) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setFont(font);
        g2.setPaint(Color.BLACK);
        FontRenderContext frc = g2.getFontRenderContext();

        TextLayout layoutTime = new TextLayout(timer, font, frc);
        double wTime = layoutTime.getBounds().getWidth();
        double x2 = 2.0 * w / 3.0 - wTime / 2.0;
        double y1 = h - 12.0;
        layoutTime.draw(g2, (float) x2, (float) y1);
    }

    @Override
    protected void paintComponent(Graphics g) {
        //super.paintComponent(g);

        int w = getWidth();
        int h = getHeight();

        //System.out.println("w, h = " + w + "," + h);
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        g2.drawImage(baseImage, null, 0, 0);

        if (selected) {
            g2.setPaint(selectedColor);
            g2.setStroke(new BasicStroke(2.0f));
            g2.draw(new Rectangle2D.Double(2.0, 2.0, w - 6.0, h - 6.0));
        }

        paintTime(g2, w, h);
    }

    public void createBaseImage() {
        int w = getWidth();
        int h = getHeight();

        GoBufferedImage largeTmp;
        GoBufferedImage smallTmp;
        if (color == GoColor.BLACK) {
            largeTmp = new BlackStone();
            smallTmp = new WhiteStone();
        } else {
            largeTmp = new WhiteStone();
            smallTmp = new BlackStone();
        }

        largeTmp.setSize(PANEL_HEIGHT / 3);
        smallTmp.setSize(PANEL_HEIGHT / 5);

        largeStone = largeTmp.getBufferedImage();
        smallStone = smallTmp.getBufferedImage();

        baseImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = baseImage.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        g2.setPaint(bgColor);
        g2.fill(new Rectangle(w, h));

        g2.drawImage(largeStone, null, 5, 5);
        g2.drawImage(smallStone, null, 40, 36);

        paintInfo(g2, w, h);
        paintPrisoner(g2, w, h);
    }

    private boolean allUpdate(GoGame goGame) {
        boolean changed = false;

        GameTree tree = goGame.getGameTree();

        changed = setNameAndRank(tree);
        changed = setPrisoner(goGame.getBoard()) || changed;
        changed = setSelection(goGame) || changed;

        return changed;
    }

    public void updateClock(GoClock clock) {
        boolean changed = setTimer(clock);
        
        if (clock.isCounting(color) && clock.isBlinking(color)) {
            if (selectedColor == SELECTED_COLOR0) {
                selectedColor = SELECTED_COLOR1;
            } else {
                selectedColor = SELECTED_COLOR0;
            }
            changed = true;
            createBaseImage();
        } else {
            if (selectedColor != SELECTED_COLOR0) {
                selectedColor = SELECTED_COLOR0;
                changed = true;
                createBaseImage();
            }
        }
//        if (clock.isCounting(color) && clock.isBlinking(color)) {
//            if (bgColor == BGCOLOR0) {
//                bgColor = BGCOLOR1;
//            } else {
//                bgColor = BGCOLOR0;
//            }
//            changed = true;
//            createBaseImage();
//        } else {
//            if (bgColor != BGCOLOR0) {
//                bgColor = BGCOLOR0;
//                changed = true;
//                createBaseImage();
//            }
//        }

        if (changed) {
            repaint();
        }
    }

    public void treeChanged(GoGame goGame, int boardSize) {
        clear();
        allUpdate(goGame);
        createBaseImage();
        repaint();
    }

    public void nodeMoved(GoGame goGame, GoNode old) {
        boolean changed = allUpdate(goGame);
        if (changed) {
            createBaseImage();
        }
        repaint();
    }

    public void nodeStateChanged(GoGame goGame) {
        boolean changed = allUpdate(goGame);
        if (changed) {
            createBaseImage();
        }
        repaint();
    }

    public void goStateChanged(GoGame goGame, GoState state) {
    }

    public void componentHidden(ComponentEvent e) {
    }

    public void componentMoved(ComponentEvent e) {
    }

    public void componentShown(ComponentEvent e) {
    }

    public void componentResized(ComponentEvent e) {
        System.out.println("PlayerInfo.componentResized");
        createBaseImage();
        repaint();
    }
}
