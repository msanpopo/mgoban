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
import go.GoMove;
import go.GoVertex;
import go.Handicap;
import go.board.GoBoard;
import go.board.FieldType;
import go.board.TerritoryType;
import sgf.value.ValueLabel;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import java.util.Collection;

public class BoardImage{
    
    private static final Color BGCOLOR = new Color(0xf5, 0xf5, 0xdc);
    
    
    /** 入力された横幅(ピクセル) */
    private int inputedLength;
    
    /** 表示する範囲。盤面 + ４辺の座標なら (0, 0) - (20, 20) */
    private int x0, y0, x1, y1;
        
    /** 画像の実際のサイズ(ピクセル)。inputedLenght より短くなる。 */
    private int width, height;
    
    /** 碁盤の枡目の間隔 */
    private int unit;
    

    
    private int boardSize;
    private BufferedImage boardImage; // 背景、碁盤の目、星、軸の文字
    private GoImage blackStone;
    private GoImage whiteStone;
    private GoImage sBlackStone;
    private GoImage sWhiteStone;
    private GoSimpleImage[] markImage;
    private GoSimpleImage plusImage;
    private LabelImage labelImage;
    private WoodTexture woodTexture;

    private GoBoard board;

    private boolean showMark;
    private boolean showLabel;
    private boolean showKo;
    private boolean showMoveNumber;
    private boolean showLastMove;
    
    public BoardImage(GoBoard board, int l) {
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

        woodTexture = WoodTexture.getInstance();

        boardSize = board.getBoardSize();

        this.board = board;
        
        showMark = true;
        showLabel = true;
        showKo = true;
        showMoveNumber = false;
        showLastMove = true;

        inputedLength = l;

        x0 = 0;
        x1 = boardSize + 1;
        y0 = 0;
        y1 = boardSize + 1;

        createImages();
    }

    private void createImages() {
        // 横と縦のブロックの数
        int w = x1 - x0 + 1;
        int h = y1 - y0 + 1;
        
        unit = inputedLength / w;
        width = unit * w;
        height = unit * h;
        
        blackStone.setSize(unit);
        whiteStone.setSize(unit);
        sBlackStone.setSize(unit);
        sWhiteStone.setSize(unit);

        for (MarkType type : MarkType.values()) {
            markImage[type.getN()].setSize(unit);
        }

        plusImage.setSize(unit);
        labelImage.setSize(unit);
        
        boardImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        paintAll();
    }

    public void setLength(int l){
        this.inputedLength = l;
        createImages();
    }
    
    public void setRange(int x0, int y0, int x1, int y1){
        this.x0 = x0;
        this.y0 = y0;
        this.x1 = x1;
        this.y1 = y1;
        createImages();
    }
    
    public BufferedImage getImage(){
        return boardImage;
    }
    
    public int getWidth(){
        return width;
    }
    
    public int getHeight(){
        return height;
    }
    
    public boolean isShowLastMove() {
        return showLastMove;
    }

    public void setShowLastMove(boolean bool){
        showLastMove = bool;
        paintAll();
    }
    
    public boolean isShowKo() {
        return showKo;
    }

    public void setShowKo(boolean bool){
        showKo = bool;
        paintAll();
    }
    
    public boolean isShowLabel() {
        return showLabel;
    }

    public void setShowLabel(boolean bool){
        showLabel = bool;
        paintAll();
    }
    
    public boolean isShowMark() {
        return showMark;
    }

    public void setShowMark(boolean bool){
        showMark = bool;
        paintAll();
    }
    
    public boolean isShowMoveNumber() {
        return showMoveNumber;
    }
    
    public void setShowMoveNumber(boolean bool){
        showMoveNumber = bool;
        paintAll();
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

    private void paintAllStone(Graphics2D g2) {
        for (int i = 1; i <= boardSize; ++i) {
            for (int j = 1; j <= boardSize; ++j) {
                paintStone(g2, j, i);
                
                // 石の有無を確認しないと取り上げられた石のない場所に数字が表示される
                if (board.isStone(j, i) && showMoveNumber) {
                    paintMoveNumber(g2, j, i);
                }
            }
        }
    }

    private void paintLastMove(Graphics2D g2) {
        // 直前の着手
        GoMove m = board.getMove();
        if (m != null && m.isPass() == false) {
            Color c;
            if (m.getColor().isBlack()) {
                c = Color.WHITE;
            } else {
                c = Color.BLACK;
            }

            plusImage.draw(g2, m.getX(), m.getY(), c);
        }
    }

    private void paintKo(Graphics2D g2) {
        // コウマーク
        if (board.hasKo()) {
            int x = board.getKoX();
            int y = board.getKoY();

            markImage[MarkType.SQUARE.getN()].draw(g2, x, y, Color.BLUE);
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
                labelImage.draw(g2, v.getX(), v.getY(), color, label, 0.65);
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
                }
            }
        }
    }

    private void paintAll() {
        Graphics2D g2 = boardImage.createGraphics();
        
        g2.setPaint(BGCOLOR);
        g2.fill(new Rectangle(width, height));
        
        g2.translate(-x0 * unit, -y0 * unit);

        double d = unit * 0.1;
        double a = unit - d;
        double b = unit * (boardSize + 2) - a * 2.0;
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
        
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);

        if (board.hasTerritoryData()) {
            paintAllTerritory(g2);

        } else {
            paintAllStone(g2);
            
            if(showLastMove){
                paintLastMove(g2);
            }
        }

        if(showKo){
            paintKo(g2);
        }
        
        if(showLabel){
            paintLabel(g2);
        }
        
        if(showMark){
            paintMark(g2);
        }
    }

    private int col_to_xImage(int col) {
        return col * unit + unit / 2;
    }

    private int row_to_yImage(int row) {
        return row * unit + unit / 2;
    }
}