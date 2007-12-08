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
package go.board;

import go.board.gui.MarkType;
import go.GoColor;
import go.GoMove;
import go.GoResult;
import go.GoVertex;
import java.util.ArrayList;
import java.util.Collection;
import sgf.GameTree;
import sgf.GoNode;
import sgf.property.MoveProperty;
import sgf.property.NoTypeProperty;
import sgf.value.ValueLabel;

/**
 * 盤の状態
 * 
 */
public class GoBoard {

    private GoBoardListener listener;
    private int boardSize;
    private GoBoardState board;
    private GoBoardTerritoryState territory;
    private boolean[][] diff;
    private MarkType[][] mark;
    private String[][] label;
    /** 盤面上に着手の番号を表示する最大値。
     * 例えば、この数字が 50 で 100手目を表示している時、51 から 100 の 50 手分の番号を表示する 。
     */
    private int maxNumber;
    /**
     * [x, y] の石の着手番号。着手番号を表示しない時には -1。
     */
    private int[][] number;
    private Collection<ValueLabel> labelCollection;
    private ArrayList<GoVertex> variationList;

    public GoBoard(int boardSize) {		// ルートノードの状態（着手前）
        this.listener = null;
        this.boardSize = boardSize;
        this.board = new GoBoardState(boardSize);
        this.territory = null;

        this.maxNumber = 30;

        setBoardSize(boardSize);

        this.variationList = new ArrayList<GoVertex>();

        clearMarkAndLabel();
    }

    public int getBoardSize() {
        return boardSize;
    }

    private void setBoardSize(int newSize) {
        boardSize = newSize;

        int msize = boardSize + 2;

        diff = new boolean[msize][msize];
        mark = new MarkType[msize][msize];
        label = new String[msize][msize];
        number = new int[msize][msize];
    }

    public void setGoBoardListener(GoBoardListener listener) {
        this.listener = listener;
    }

    private void clearDiffArray(boolean changed) {
        int msize = boardSize + 2;
        for (int i = 0; i < msize; ++i) {
            for (int j = 0; j < msize; ++j) {
                diff[j][i] = changed;
            }
        }
    }

    private void checkMark() {
        // 古いマークのあった位置は更新する必要があるのでマークする
        int msize = boardSize + 2;
        for (int i = 0; i < msize; ++i) {
            for (int j = 0; j < msize; ++j) {
                MarkType type = mark[j][i];
                if (type != null) {
                    diff[j][i] = true;
                }

                String l = label[j][i];
                if (l != null) {
                    diff[j][i] = true;
                }
            }
        }
    }

    private void clearMarkAndLabel() {
        int msize = boardSize + 2;
        for (int i = 0; i < msize; ++i) {
            for (int j = 0; j < msize; ++j) {
                mark[j][i] = null;
                label[j][i] = null;
                number[j][i] = -1;
            }
        }
        labelCollection = new ArrayList<ValueLabel>();
    }

    private int[][] copyNumber() {
        int msize = boardSize + 2;
        int[][] num = new int[msize][msize];
        for (int i = 0; i < msize; ++i) {
            for (int j = 0; j < msize; ++j) {
                num[j][i] = number[j][i];
            }
        }
        return num;
    }

    private void checkNumber(int oldNumber[][]) {
        int msize = boardSize + 2;
        for (int i = 0; i < msize; ++i) {
            for (int j = 0; j < msize; ++j) {
                if (oldNumber[j][i] != number[j][i]) {
                    diff[j][i] = true;
                }
            }
        }
    }

    private void checkVariation(GameTree tree) {
        for(GoVertex v : variationList){
            diff[v.getX()][v.getY()] = true;
        }
        variationList.clear();
        
        GoNode node;

        node = tree.getCurrentNode();
        while (node.hasPrev()) {
            node = node.getPrev();
            if (node.hasMoveProperty()) {
                MoveProperty mp = node.getMoveProperty();
                GoMove move = mp.getMove();
                if(!move.isPass()){
                    GoVertex v = move.getVertex();
                    variationList.add(v);
                    diff[v.getX()][v.getY()] = true;
                }
            }
        }

        node = tree.getCurrentNode();
        while (node.hasNext()) {
            node = node.getNext();
            if (node.hasMoveProperty()) {
                MoveProperty mp = node.getMoveProperty();
                GoMove move = mp.getMove();
                if(!move.isPass()){
                    GoVertex v = move.getVertex();
                    variationList.add(v);
                    diff[v.getX()][v.getY()] = true;
                }
            }
        }
    }

    public void setBoardState(GameTree tree) {
        GoBoardState newBoard = tree.getBoardState();

        GoBoardState oldBoard = board;
        int oldBoardSize = boardSize;

        board = newBoard;
        boardSize = newBoard.getBoardSize();

        if (boardSize != oldBoardSize) {
            setBoardSize(boardSize);
            clearDiffArray(true);
        } else {
            clearDiffArray(false);
            diff = board.diff(oldBoard, diff);
        }

        int[][] oldNumber = copyNumber();

        checkMark();
        clearMarkAndLabel();
        
        NoTypeProperty ntp = tree.getCurrentNode().getNoTypeProperty();
        if (ntp != null) {
            for (MarkType type : MarkType.values()) {
                setMark(type, ntp.getMarkCollection(type));
            }

            labelCollection = ntp.getLabelCollection();
            for (ValueLabel l : labelCollection) {
                GoVertex v = l.getVertex();
                String s = l.getLabel();
                label[v.getX()][v.getY()] = s;
            }
        }

        GoNode node = tree.getCurrentNode();
        int count = 0;
        while (true) {
            if (node.hasMoveProperty()) {
                MoveProperty mp = node.getMoveProperty();
                GoMove move = mp.getMove();
                
                // 新浪囲棋 で KO をコミとして使っていて
                // KO のあるノードを着手プロパティーがあるように判断してしまう。
                // そのようなノードで着手はとれないので、チャックする。
                if(move != null){
                    int n = node.getMoveNumber();
                    number[move.getX()][move.getY()] = n;

                    count += 1;
                }
            }

            if (count >= maxNumber) {
                break;
            }

            if (node.hasParent()) {
                node = node.getParent();
            } else {
                break;
            }
        }

        checkNumber(oldNumber);
        checkVariation(tree);

        if (boardSize != oldBoardSize) {
            fireBoardSizeChanged(boardSize);
        }

        fireBoardStateChanged(false);
    }

    private void setMark(MarkType type, Collection<GoVertex> c) {
        for (GoVertex v : c) {
            mark[v.getX()][v.getY()] = type;
        }
    }

    public MarkType getMark(int x, int y) {
        return mark[x][y];
    }

    public Collection<ValueLabel> getLabelCollection() {
        return labelCollection;
    }

    public Collection<GoVertex> getVariationCollection(){
        return variationList;
    }
    
    public int getNumber(int x, int y) {
        return number[x][y];
    }

    // v は必ず石のある座標でないといけない。事前に isStone でチェックする。
    public void markDeadStone(GoVertex v) {
        territory.markDeadStone(v);
        clearDiffArray(false);

        fireBoardStateChanged(true);
    }

    // v は必ず石のある座標でないといけない。事前に isStone でチェックする。
    public void markDeadStoneGroup(GoVertex v) {
        territory.markDeadStoneGroup(v);
        clearDiffArray(false);

        fireBoardStateChanged(true);
    }

    public void toggleDeadStoneGroup(GoVertex v) {
        territory.toggleDeadStoneGroup(v);
        clearDiffArray(false);

        fireBoardStateChanged(true);
    }

    public void setResultBoard(int[][] boardArray) {
        territory.setResultBoard(boardArray);
        clearDiffArray(false);

        fireBoardStateChanged(true);
    }

    public boolean hasKo() {
        return board.hasKo();
    }

    public int getKoX() {
        return board.getKoX();
    }

    public int getKoY() {
        return board.getKoY();
    }

    public GoMove getMove() {
        return board.getMove();
    }

    public int getPrisoner(GoColor color) {
        return board.getPrisoner(color);
    }

    public FieldType getType(int x, int y) {
        return board.getType(x, y);
    }

    public boolean isChanged(int x, int y) {
        return diff[x][y];
    }

    public TerritoryType getTerritoryType(int x, int y) {
        return territory.getTerritoryType(x, y);
    }

    public boolean isDead(int x, int y) {
        return territory.isDead(x, y);
    }

    public boolean isStone(int x, int y) {
        return board.isStone(x, y);
    }

    public boolean isEmpty(int x, int y) {
        return board.isEmpty(x, y);
    }

    // [x, y] の位置に c の色の石を置けるか？
    public boolean isValidMove(int x, int y, GoColor color) {
        return board.isValidMove(x, y, color);
    }

    public boolean checkRange(int x, int y) {
        return board.checkRange(x, y);
    }

    public boolean hasMark(int x, int y) {
        MarkType type = mark[x][y];
        if (type == null) {
            return false;
        } else {
            return true;
        }
    }

    public boolean hasLabel(int x, int y) {
        String s = label[x][y];
        if (s == null) {
            return false;
        } else {
            return true;
        }
    }

    public boolean hasNumber(int x, int y) {
        if (number[x][y] != -1) {
            return true;
        } else {
            return false;
        }
    }

    public void checkTerritory() {
        if (territory == null) {
            territory = new GoBoardTerritoryState(board);
        }
        territory.checkTerritory();
        clearDiffArray(false);

        fireBoardStateChanged(true);
    }

    public void clearTerritory() {
        territory = null;
        clearDiffArray(false);
        fireBoardStateChanged(true);
    }

    public boolean hasTerritoryData() {
        if (territory == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public String toString() {
        return board.toString();
    }

    public String toTerritoryString() {
        return territory.toTerritoryString();
    }

    public String getResultString(double komi) {
        return territory.getResultString(komi);
    }

    public GoResult getResult(double komi) {
        return territory.getResult(komi);
    }

    private void fireBoardSizeChanged(int newBoardSize) {
        if (listener != null) {
            listener.boardSizeChanged(newBoardSize);
        }
    }

    private void fireBoardStateChanged(boolean all) {
        if (listener != null) {
            listener.boardStateChanged(all);
        }
    }
}
