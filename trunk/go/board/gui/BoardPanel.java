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

import go.board.gui.image.BlackStone;
import go.board.gui.image.BlackTerritory;
import go.board.gui.image.CircleImage;
import go.board.gui.image.MarkImage;
import go.board.gui.image.GoImage;
import go.board.gui.image.GoSimpleImage;
import go.board.gui.image.LabelImage;
import go.board.gui.image.PlusImage;
import go.board.gui.image.SelectedImage;
import go.board.gui.image.SquareImage;
import go.board.gui.image.TriangleImage;
import go.board.gui.image.WhiteStone;
import go.board.gui.image.WhiteTerritory;
import go.GoColor;
import go.GoMove;
import go.GoVertex;
import go.Handicap;
import go.board.GoBoard;
import go.board.FieldType;
import go.board.GoBoardListener;
import go.board.TerritoryType;
import sgf.value.ValueLabel;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import java.awt.event.MouseWheelListener;
import java.util.Collection;

public class BoardPanel extends JPanel implements ComponentListener, MouseMotionListener, MouseListener, MouseWheelListener, GoBoardListener {

    private static final Color BGCOLOR = new Color(0xf5, 0xf5, 0xdc);
    private static final int DEFAULT_BOARDSIZE = 19;
    private int l; // 碁盤（座標の文字含む）の画像の辺のサイズ（ピクセル)
    private int x0,  y0; // パネル中の盤を描く左上の位置
    private int unit; // 碁盤の枡目の間隔
    private int cursorX,  cursorY;
    private int oldX,  oldY;
    private int boardSize;
    private BufferedImage boardImage; // 背景、碁盤の目、星、軸の文字
    private BufferedImage boardAndStone; // boardImage + 石
    private GoImage blackStone;
    private GoImage whiteStone;
    private GoImage sBlackStone;
    private GoImage sWhiteStone;
    private GoSimpleImage[] markImage;
    private GoSimpleImage plusImage;
    private LabelImage labelImage;
    private WoodTexture woodTexture;
    private BoardOperation operation;
    private GoBoard board;
    private boolean showMark;
    private boolean showLabel;
    private boolean showKo;
    private boolean showMoveNumber;
    private boolean showLastMove;
    /** 対局中は、マーク、ラベル、着手番号を表示しないのでそのフラグとして使う */
    private boolean gameMode;

    public BoardPanel(GoBoard board) {
        blackStone = new BlackStone();
        whiteStone = new WhiteStone();
        sBlackStone = new BlackTerritory();
        sWhiteStone = new WhiteTerritory();

        markImage = new GoSimpleImage[5];
        markImage[MarkType.CIRCLE.getN()] = new CircleImage(Color.GREEN);
        markImage[MarkType.SQUARE.getN()] = new SquareImage(Color.GREEN);
        markImage[MarkType.TRIANGLE.getN()] = new TriangleImage(Color.GREEN);
        markImage[MarkType.MARK.getN()] = new MarkImage(Color.GREEN);
        markImage[MarkType.SELECTED.getN()] = new SelectedImage(Color.GREEN);

        plusImage = new PlusImage(Color.RED);

        labelImage = new LabelImage(Color.BLACK);

        addComponentListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);

        /*
         * TODO
         * TextArea がとったフォーカスを奪わないと矢印が効かないみたいなのでフォーカスを取れるようにしてみたが、
         * ctrl + tab というあやしげな操作をしなければフォーカスがとれない。
         * どうにかする。
         */
        setFocusable(true);

        setBackground(BGCOLOR);

        woodTexture = WoodTexture.getInstance();

        boardSize = DEFAULT_BOARDSIZE;
        cursorX = 0;
        cursorY = 0;
        oldX = 0;
        oldY = 0;
        operation = new NullOperation();

        this.board = board;
        this.board.setGoBoardListener(this);

        showMark = true;
        showLabel = true;
        showKo = true;
        showMoveNumber = false;
        showLastMove = true;

        gameMode = false;
    }

    public boolean isGameMode() {
        return gameMode;
    }

    public void setGameMove(boolean bool) {
        gameMode = bool;
        repaintAll(true);
    }

    public boolean isShowLastMove() {
        return showLastMove;
    }

    public void setShowLastMove(boolean bool) {
        showLastMove = bool;
        repaintAll(true);
    }

    public boolean isShowKo() {
        return showKo;
    }

    public void setShowKo(boolean bool) {
        showKo = bool;
        repaintAll(true);
    }

    public boolean isShowLabel() {
        return showLabel;
    }

    public void setShowLabel(boolean bool) {
        showLabel = bool;
        repaintAll(true);
    }

    public boolean isShowMark() {
        return showMark;
    }

    public void setShowMark(boolean bool) {
        showMark = bool;
        repaintAll(true);
    }

    public boolean isShowMoveNumber() {
        return showMoveNumber;
    }

    public void setShowMoveNumber(boolean bool) {
        showMoveNumber = bool;
        repaintAll(true);
    }

    private void repaintLocalAll() {
        repaint(0, 0, this.getWidth(), this.getHeight());
    }

    private void repaintLocal(int x, int y) {
        int xx = x * unit + x0;
        int yy = y * unit + y0;
        repaint(xx, yy, unit, unit);
    }

    private void repaintLocal(int x1, int y1, int x2, int y2) {
        int xx0 = x1 * unit + x0;
        int yy0 = y1 * unit + y0;
        int xx1 = x2 * unit + x0;
        int yy1 = y2 * unit + y0;
        repaint(xx0, yy0, xx1 - xx0, yy1 - yy0);
    }

    public void repaintCursor() {
        repaintLocal(oldX, oldY);
        repaintLocal(cursorX, cursorY);
    }

    public void repaintAll(boolean force) {
        paintBoardAndStone(force);
    }

    private void initialize(int boardSize) {
        this.boardSize = boardSize;
        cursorX = 0;
        cursorY = 0;
        oldX = 0;
        oldY = 0;
        operation = new NullOperation();

        sizeInit();
        repaintAll(true);
    }

    public GoImage getStoneImage(GoColor color) {
        if (color == GoColor.BLACK) {
            return blackStone;
        } else {
            return whiteStone;
        }
    }

    public GoSimpleImage getMarkImage(MarkType type) {
        return markImage[type.getN()];
    }

    public LabelImage getLabelImage() {
        return labelImage;
    }

    public void setOperation(BoardOperation operation) {
        this.operation = operation;
        this.operation.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        g2.translate(x0, y0);
        g2.drawImage(boardAndStone, null, 0, 0);

        // カーソル表示
        AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f);
        g2.setComposite(composite);

        operation.draw(g2, this);
    }

    public void boardSizeChanged(int size) {
        initialize(size);
    }

    public void boardStateChanged(boolean all) {
        repaintAll(all);
    }

    private void paintAllTerritory(Graphics2D g2) {
        AlphaComposite orig = (AlphaComposite) g2.getComposite();
        AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
        for (int i = 1; i <= boardSize; ++i) {
            for (int j = 1; j <= boardSize; ++j) {
                FieldType type = board.getType(j, i);
                TerritoryType ttype = board.getTerritoryType(j, i);
                boolean isDead = board.isDead(j, i);
                if (type.isBlack()) {
                    if (isDead) {
                        g2.setComposite(alpha);
                        blackStone.draw(g2, j, i);
                        g2.setComposite(orig);
                        sWhiteStone.draw(g2, j, i);
                    } else {
                        blackStone.draw(g2, j, i);
                    }
                } else if (type.isWhite()) {
                    if (isDead) {
                        g2.setComposite(alpha);
                        whiteStone.draw(g2, j, i);
                        g2.setComposite(orig);
                        sBlackStone.draw(g2, j, i);
                    } else {
                        whiteStone.draw(g2, j, i);
                    }
                } else if (type.isEmpty()) {
                    g2.setComposite(orig);
                    if (ttype.isBlackTerritory()) {
                        sBlackStone.draw(g2, j, i);

                    } else if (ttype.isWhiteTerritory()) {
                        sWhiteStone.draw(g2, j, i);
                    }
                }
            }
        }
    }

    private void paintWood(Graphics2D g2, int x, int y) {
        double d = unit * 0.15;
        double xx = (unit * x + d);
        double yy = (unit * y + d);
        double w = (unit - 2 * d);
        woodTexture.draw(g2, xx, yy, w, w);
    }

    private void paintBoard(Graphics2D g2, int x, int y) {
        g2.setClip(x * unit, y * unit, unit, unit);
        g2.drawImage(boardImage, null, 0, 0);
        g2.setClip(null);
    }

    // 指定した位置に石があれば描く、なければ何もしない。
    private void paintStone(Graphics2D g2, int x, int y) {
        FieldType type = board.getType(x, y);

        if (type.isBlack()) {
            blackStone.draw(g2, x, y);
        } else if (type.isWhite()) {
            whiteStone.draw(g2, x, y);
        }
    }

    private void paintMoveNumber(Graphics2D g2, int x, int y) {
        int number = board.getNumber(x, y);

        if (number != -1) {
            String label = Integer.toString(number);
            if (label.length() >= 3) {
                label = String.copyValueOf(label.toCharArray(), label.length() - 2, 2);
            }

            Color color = selectColor(x, y);
            labelImage.draw(g2, x, y, color, label, 0.65);
        }
    }

    private void paintAllStoneDiff(Graphics2D g2) {
        for (int i = 1; i <= boardSize; ++i) {
            for (int j = 1; j <= boardSize; ++j) {
                if (board.isChanged(j, i)) {
                    paintBoard(g2, j, i);
                    paintStone(g2, j, i);

                    // 石の有無を確認しないと取り上げられた石のない場所に数字が表示される
                    if (board.isStone(j, i) && !gameMode && showMoveNumber) {
                        paintMoveNumber(g2, j, i);
                    }
                    repaintLocal(j, i);
                }
            }
        }
    }

    private void paintAllStone(Graphics2D g2) {
        for (int i = 1; i <= boardSize; ++i) {
            for (int j = 1; j <= boardSize; ++j) {
                paintStone(g2, j, i);

                // 石の有無を確認しないと取り上げられた石のない場所に数字が表示される
                if (board.isStone(j, i) && !gameMode && showMoveNumber) {
                    paintMoveNumber(g2, j, i);
                }
                repaintLocal(j, i);
            }
        }
    }

    private void paintLastMove(Graphics2D g2) {
        // 直前の着手
        GoMove m = board.getMove();
        if (m != null && m.isPass() == false) {
            Color c;
            if (m.getColor() == GoColor.BLACK) {
                c = Color.WHITE;
            } else {
                c = Color.BLACK;
            }

            plusImage.draw(g2, m.getX(), m.getY(), c);

            repaintLocal(m.getX(), m.getY());
        }
    }

    private void paintKo(Graphics2D g2) {
        // コウマーク
        if (board.hasKo()) {
            int x = board.getKoX();
            int y = board.getKoY();

            markImage[MarkType.SQUARE.getN()].draw(g2, x, y, Color.BLUE);

            repaintLocal(x, y);
        }
    }

    private Color selectColor(int x, int y) {
        FieldType type = board.getType(x, y);
        if (type.isBlack()) {
            return Color.WHITE;
        } else {
            return Color.BLACK;
        }
    }

    // 注意：ラベルを描く時にまず木目で塗りつぶす。なのでラベルと他のもの（マークとか）を重ねる時は最初にラベルを描く必要がある。
    private void paintLabel(Graphics2D g2) {
        Collection<ValueLabel> c = board.getLabelCollection();
        if (c != null) {
            for (ValueLabel vl : c) {
                GoVertex v = vl.getVertex();

                if (board.getType(v.getX(), v.getY()).isEmpty()) {
                    paintWood(g2, v.getX(), v.getY());
                }

                String label = vl.getLabel();
                Color color = selectColor(v.getX(), v.getY());
                labelImage.draw(g2, v.getX(), v.getY(), color, label, 0.6);

                repaintLocal(v.getX(), v.getY());
            }
        }
    }

    private void paintMark(Graphics2D g2) {
        for (int i = 1; i <= boardSize; ++i) {
            for (int j = 1; j <= boardSize; ++j) {
                MarkType type = board.getMark(j, i);

                if (type != null) {
                    Color color = selectColor(j, i);
                    GoSimpleImage image = markImage[type.getN()];
                    image.draw(g2, j, i, color);

                    repaintLocal(j, i);
                }
            }
        }
    }

    private void paintVariation(Graphics2D g2) {
        GoMove m = board.getMove();
        if(m == null){
            return;
        }
        
        GoImage image;
        if (m.getColor() == GoColor.BLACK) {
            image = sBlackStone;
        } else {
            image = sWhiteStone;
        }

        AlphaComposite orig = (AlphaComposite) g2.getComposite();
        AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
        g2.setComposite(alpha);
        
        for (GoVertex v : board.getVariationCollection()) {
            image.draw(g2, v.getX(), v.getY());
        }
        
        g2.setComposite(orig);
    }

    private void paintBoardAndStone(boolean forceUpdate) {
        if (boardAndStone == null) {
            return;
        }

        Graphics2D g2 = boardAndStone.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);

        if (board.hasTerritoryData()) {
            g2.drawImage(boardImage, null, 0, 0);
            paintAllTerritory(g2);

            repaint();  // 全盤面更新
        } else {
            if (forceUpdate) {
                g2.drawImage(boardImage, null, 0, 0);
                paintAllStone(g2);

                repaint();  // 全盤面更新

            } else {
                paintAllStoneDiff(g2);
            }

            if (showLastMove) {
                paintLastMove(g2);
            }
        }

        if (showKo) {
            paintKo(g2);
        }

        if (!gameMode && showLabel) {
            paintLabel(g2);
        }

        if (!gameMode && showMark) {
            paintMark(g2);
        }

        // TODO あまりに見にくい。
        //paintVariation(g2);
    }

    private void boardAndStoneInit() {
        boardAndStone = new BufferedImage(l, l, BufferedImage.TYPE_INT_RGB);
    }

    private void boardImageInit() {
        boardImage = new BufferedImage(l, l, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2 = boardImage.createGraphics();

        g2.setPaint(BGCOLOR);
        g2.fill(new Rectangle(l, l));

        double d = unit * 0.1;
        double a = (unit - d);
        double b = l - a * 2.0;
        woodTexture.draw(g2, a, a, b, b);

        BasicStroke line1 = new BasicStroke(1.0f);
        BasicStroke line2 = new BasicStroke(2.0f);

        g2.setPaint(Color.BLACK);
        {
            double xx0 = col_to_xImage(1);
            double yy0 = row_to_yImage(1);
            double ww = unit * (boardSize - 1);
            double hh = unit * (boardSize - 1);

            g2.setStroke(line2);
            g2.draw(new Rectangle2D.Double(xx0, yy0, ww, hh)); // 外枠

            g2.setStroke(line1);
            for (int i = 1; i <= boardSize; ++i) {
                double xa0 = col_to_xImage(i);
                double ya0 = row_to_yImage(i);
                g2.draw(new Line2D.Double(xx0, ya0, xx0 + ww, ya0)); // 横線
                g2.draw(new Line2D.Double(xa0, yy0, xa0, yy0 + hh)); // 縦線
            }
        }

        {
            GoVertex[] hosi = Handicap.getVertex(boardSize, 9);

            for (GoVertex v : hosi) {
                double xx0 = col_to_xImage(v.getX());
                double yy0 = row_to_yImage(v.getY());
                // 円を描くと小さくて真四角に見えるので四角を２回描く
                g2.fill(new Rectangle2D.Double(xx0 - 2, yy0 - 1, 5.0, 3.0));
                g2.fill(new Rectangle2D.Double(xx0 - 1, yy0 - 2, 3.0, 5.0));
            }
        }

        {
            String abc = "ABCDEFGHJKLMNOPQRSTUVWXYZ";

            for (int i = 1; i <= boardSize; ++i) {
                String xLabel = String.valueOf(abc.charAt(i - 1));
                String yLabel = String.valueOf(boardSize + 1 - i);

                labelImage.draw(g2, i, 0, Color.BLACK, xLabel);
                labelImage.draw(g2, i, boardSize + 1, Color.BLACK, xLabel);

                labelImage.draw(g2, 0, i, Color.BLACK, yLabel);
                labelImage.draw(g2, boardSize + 1, i, Color.BLACK, yLabel);
            }
        }
    }

    private void stoneInit() {
        blackStone.setSize(unit);
        whiteStone.setSize(unit);
        sBlackStone.setSize(unit);
        sWhiteStone.setSize(unit);

        for (MarkType type : MarkType.values()) {
            markImage[type.getN()].setSize(unit);
        }

        plusImage.setSize(unit);

        labelImage.setSize(unit);
    }

    private void sizeInit() {
        int w = getWidth();
        int h = getHeight();
        int wh;

        if (w == 0 || h == 0) {
            return;
        }

        if (w < h) {
            wh = w;
        } else {
            wh = h;
        }
        unit = wh / (boardSize + 2);
        l = unit * (boardSize + 2);
        x0 = (w - l) / 2;
        y0 = (h - l) / 2;
        /*
        System.out.println("paintComponent :(" + w + ", " + h + ")  unit:" + unit + "(x0, y0):(" + x0 + "," + y0 + ")");
         */
        stoneInit();
        boardImageInit();
        boardAndStoneInit();
    }

    private int col_to_xImage(int col) {
        return col * unit + unit / 2;
    }

    private int row_to_yImage(int row) {
        return row * unit + unit / 2;
    }

    private int x_to_col(int x) {
        return (x - x0) / unit;
    }

    private int y_to_row(int y) {
        return (y - y0) / unit;
    }

    // Listener interface
    public void componentHidden(ComponentEvent e) {
    }

    public void componentMoved(ComponentEvent e) {
    }

    public void componentShown(ComponentEvent e) {
    }

    public void componentResized(ComponentEvent e) {
        System.out.println("componentResized");
        sizeInit();
        repaintAll(true);
    }

    // ドラッグ中の移動のみ拾う
    public void mouseDragged(MouseEvent e) {
        mouseMoved(e);
    }
    // 何もボタンを押してない時の移動のみ拾う
    public void mouseMoved(MouseEvent e) {
        oldX = cursorX;
        oldY = cursorY;
        cursorX = x_to_col(e.getX());
        cursorY = y_to_row(e.getY());

        // System.out.println("mouseMoved old x, y:" + oldX + "," + oldY);
        //System.out.println("mouseMoved new x, y:" + cursorX + "," + cursorY);

        if (oldX == cursorX && oldY == cursorY) {
            return;
        }

        operation.mouseMoved(cursorX, cursorY, this);
    }

    /* MouseListener */
    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
        operation.setVisible(false);
        oldX = cursorX;
        oldY = cursorY;
        cursorX = 0;
        cursorY = 0;
        repaintCursor();
    }

    public void mousePressed(MouseEvent e) {
        operation.mousePressed(cursorX, cursorY, this, e);
    }

    public void mouseReleased(MouseEvent e) {
        operation.mouseReleased(cursorX, cursorY, this, e);
    }

    public void mouseClicked(MouseEvent e) {
    // clicked は押してから離すまでの間にマウスカーソルが動いてしまうとクリックとして拾わない。
    // 使いづらいので released を使う。
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        //System.out.println("BoardPanel.mouseWheelMoved:" + e);
        int r = e.getWheelRotation();
        if (r > 0) {
            operation.wheelDown(this, e);
        } else if (r < 0) {
            operation.wheelUp(this, e);
        }
    }
}
