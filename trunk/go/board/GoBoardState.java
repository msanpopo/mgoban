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

import go.GoColor;
import go.GoMove;
import go.GoVertex;
import java.util.ArrayList;
import sgf.GoNode;
import sgf.property.MoveProperty;
import sgf.property.SetupProperty;

/**
 * GoBoard のスナップショット
 */
public class GoBoardState {
    private int boardsize;
    private GoMove move;				// 直前の着手
    private FieldType[][] matrix;		// 着手後の盤面
    private int capturedBlack;		// とられた黒石の数（白がとった数）
    private int capturedWhite;
    
    private boolean hasKo;
    private int ko_x, ko_y;
    
    public GoBoardState(int boardsize){		// ルートノードの状態（着手前）
        this.boardsize = boardsize;
        move = null;
        capturedBlack = 0;
        capturedWhite = 0;
        
        hasKo = false;
        ko_x = 0;
        ko_y = 0;
        
        int msize = boardsize + 2;
        matrix = new FieldType[msize][msize];
        for(int i = 0; i < msize; ++i){
            for(int j = 0; j < msize; ++j){
                matrix[j][i] = FieldType.EMPTY;
            }
        }
        for(int i = 0; i < msize; ++i){
            matrix[0][i] = FieldType.EDGE;
            matrix[msize - 1][i] = FieldType.EDGE;
            matrix[i][0] = FieldType.EDGE;
            matrix[i][msize - 1] = FieldType.EDGE;
        }
    }
    
    // コピーを作る
    public GoBoardState(GoBoardState prev){
        this.boardsize = prev.boardsize;
        this.move = prev.move;
        
        this.capturedBlack = prev.capturedBlack;
        this.capturedWhite = prev.capturedWhite;
        
        this.hasKo = false;
        this.ko_x = 0;
        this.ko_y = 0;
        
        int msize = boardsize + 2;
        
        matrix = new FieldType[msize][msize];
        for(int i = 0; i < msize; ++i){
            for(int j = 0; j < msize; ++j){
                matrix[j][i] = prev.matrix[j][i];
            }
        }
    }
    
    // move が不正な着手であってはならない。事前に isValidMove でチェックしとく。不正な場合の動作は不明。
    private void move(GoMove move){
        this.move = move;
        
        if(move.isPass()){
            return;
        }
        
        FieldType type = (move.getColor() == GoColor.BLACK) ? FieldType.BLACK : FieldType.WHITE;
        int x = move.getX();
        int y = move.getY();
        matrix[x][y] = type;
        
        int n = checkKoAndCapture(move.getColor(), x, y);
        if(type == FieldType.BLACK){
            capturedWhite += n;
        }else{
            capturedBlack += n;
        }
    }
    
    private void addStone(GoColor color, GoVertex v){
        FieldType type = (color == GoColor.BLACK) ? FieldType.ADD_BLACK : FieldType.ADD_WHITE;
        int x = v.getX();
        int y = v.getY();
        matrix[x][y] = type;
    }
    
    // v は必ず石のある座標でないといけない。事前に isStone でチェックする。
    private  void removeStone(GoVertex v){
        int x = v.getX();
        int y = v.getY();
        matrix[x][y] = FieldType.ADD_EMPTY;
    }
    
    public void addStone(GoNode node){
        //System.out.println("BoardState.addStone" + node.toSgfString());
        if(node.hasMoveProperty()){
            //System.out.println("BoardState.addStone:move prop");
            MoveProperty mp = node.getMoveProperty();
            GoMove newMove = mp.getMove();
            if(newMove == null){
                // SGF の仕様上はあってはならないけど、新浪囲棋 で KO をコミとして使っていて
                // KO のあるノードを着手プロパティーがあるように判断してしまう。
                System.err.println("err:GoBoardState:");
            }else{
                move(newMove);
            }
        }else if(node.hasSetupProperty()){
            //System.out.println("BoardState.addStone:setup prop");
            SetupProperty sp = node.getSetupProperty();
            
            for(GoVertex v : sp.getBlackCollection()){
                addStone(GoColor.BLACK, v);
            }
            
            for(GoVertex v : sp.getWhiteCollection()){
                addStone(GoColor.WHITE, v);
            }
            
            for(GoVertex v : sp.getEmptyCollection()){
                removeStone(v);
            }
        }
    }

    public int getBoardSize(){
        return boardsize;
    }
    
    public boolean hasKo(){
        return hasKo;
    }
    
    public int getKoX(){
        return ko_x;
    }
    
    public int getKoY(){
        return ko_y;
    }
    
    public GoMove getMove(){
        return move;
    }

    public int getPrisoner(GoColor color){
        if(color == GoColor.BLACK){
            return capturedBlack;
        }else{
            return capturedWhite;
        }
    }
    
    public FieldType getType(int x, int y){
        return matrix[x][y];
    }

    public boolean isStone(int x, int y){
        return matrix[x][y].isStone();
    }
    
    public boolean isEmpty(int x, int y){
        return matrix[x][y].isEmpty();
    }
    
    // [x, y] の位置に c の色の石を置けるか？
    public boolean isValidMove(int x, int y, GoColor color){
        if(x < 1 || x > boardsize || y < 1 || y > boardsize){
            return false;
        }
        
        if(matrix[x][y].isStone()){
            return false;		// 既に石があるので置けない
        }
        
        if(hasKo && x == ko_x && y == ko_y){
            // System.out.println("isValidMove:false:ko");
            return false;
        }
        
        if(isSuicide(x, y, color) == true){
            //System.out.println("isValidMove:false:suicide");
            return false;
        }
        
        return true;
    }

    public boolean checkRange(int x, int y){
        if(x < 1 || x > boardsize || y < 1 || y > boardsize){
            return false;
        }else{
            return true;
        }
    }
    
    // liberty がなくなる場所にはおけないが、相手の石が取れるなら置ける。
    // [x, y] に置かれた color 色の石は自殺手か調べる
    private boolean isSuicide(int x, int y, GoColor color){
        FieldType c = FieldType.get(color);                     // 次の着手の色
        GoColor oc = color.opponentColor();
        
        matrix[x][y] = c;		// 仮置き
        
        boolean suicide = false;
        
        if(hasLiberty(x, y)){
            suicide = false;		// liberty があったので自殺手でない
        }else{
            // liberty がなかったので隣の相手の石が取れるか調べる
            
            boolean liberty = true;
            if(matrix[x - 1][y].isColor(oc)){
                //System.out.println("isSuicide:check w");
                liberty = hasLiberty(x - 1 , y);
            }
            if(liberty == true && matrix[x + 1][y].isColor(oc)){
                //System.out.println("isSuicide:check e");
                liberty = hasLiberty(x + 1 , y);
            }
            if(liberty == true && matrix[x][y - 1].isColor(oc)){
                //System.out.println("isSuicide:check s");
                liberty = hasLiberty(x, y - 1);
            }
            if(liberty == true && matrix[x][y + 1].isColor(oc)){
                //System.out.println("isSuicide:check n");
                liberty = hasLiberty(x, y + 1);
            }
            
            if(liberty == true){
                suicide = true;	// 相手の石に liberty があり取れないので自殺手
            }else{
                suicide = false;	// 相手の石に liberty がなく取れるので自殺手でない
            }
            //System.out.println("isSuicide:true:has no liberty");
        }
        
        matrix[x][y] = FieldType.EMPTY;	// 仮置き復旧
        
        return suicide;
    }
    
    // x,y に置かれた石のグループに呼吸点があれば true
    private boolean hasLiberty(int x, int y){
        // boolean の配列は false で初期化される。
        boolean[][] b = new boolean[boardsize + 2][boardsize + 2];
        
        FieldType type = matrix[x][y];
        GoColor color = type.getColor();
        return hasLibertyRecur(color, x ,y, b);
    }
    
    private boolean hasLibertyRecur(GoColor color, int x, int y, boolean[][] b){
        if(b[x][y] == true){ // 既に調べた場所にダメが空いてれば、処理が終わっているはず。調べた場所に再度ブチあたる
            //ということは、その場所のダメが空いてなかったということなので false。
            return false;
        }
        
        b[x][y] = true;
        
        FieldType type = matrix[x][y];
        if(type.isEmpty()){
            //System.out.println("isCaptured:empty");
            return true;
        }else if(type.isEdge()){
            //System.out.println("isCaptured:edge");
            return false;
        }else if(type.isColor(color)){
            if(hasLibertyRecur(color, x - 1, y, b) == true
                    || hasLibertyRecur(color, x + 1, y, b) == true
                    || hasLibertyRecur(color, x, y - 1, b) == true
                    || hasLibertyRecur(color, x, y + 1, b) == true){
                return true;
            }else{
                return false;
            }
        }else{	// 相手の石で詰まっている
            return false;
        }
    }
    
    /**
     * 着手の結果、コウになるかどうかをチェックしてメモるとともに、石がとれるなら盤面から取り上げる
     * @param color 着手の色
     * @param x 着手の x 座標(1 - 19))
     * @param y 着手の y 座標(1 - 19))
     * @return とられた石の数
     */
    private int checkKoAndCapture(GoColor color, int x, int y){
        GoColor oc = color.opponentColor();
        
        boolean ko = false;
        
        // 石を取る前に ko の可能性チェック
        if((matrix[x - 1][y].isColor(oc) || matrix[x - 1][y].isEdge()) &&
                (matrix[x + 1][y].isColor(oc) || matrix[x + 1][y].isEdge()) &&
                (matrix[x][y - 1].isColor(oc) || matrix[x][y - 1].isEdge()) &&
                (matrix[x][y + 1].isColor(oc) || matrix[x][y + 1].isEdge())){
            ko = true;
        }else{
            ko = false;
        }
        
        // ko の可能性チェックの後に取る
        int nw = checkCapture(x - 1, y, oc);
        int ne = checkCapture(x + 1, y, oc);
        int ns = checkCapture(x, y - 1, oc);
        int nn = checkCapture(x, y + 1, oc);
        int nAll = nw + ne + ns + nn;
        
        // 本当の ko なら記憶
        if(ko && nAll == 1){
            hasKo = true;
            if(nw == 1){
                ko_x = x -1;
                ko_y = y;
            }else if(ne == 1){
                ko_x = x + 1;
                ko_y = y;
            }else if(ns == 1){
                ko_x = x;
                ko_y = y - 1;
            }else if(nn == 1){
                ko_x = x;
                ko_y = y + 1;
            }
        }
        
        return nAll;
    }
    
    // x,y の石が color 色かチェックして true ならつながる石も含めて取り上げる
    private int checkCapture(int x, int y, GoColor color){
        if(matrix[x][y].isColor(color) && hasLiberty(x, y) == false){
            return doCapture(color, x, y);
        }else{
            return 0;
        }
    }
    
    private int doCapture(GoColor color, int x, int y){
        int n = 0;
        
        if(matrix[x][y].isColor(color)){
            n += 1;
            matrix[x][y] = FieldType.EMPTY;
            n += doCapture(color, x - 1, y);
            n += doCapture(color, x + 1, y);
            n += doCapture(color, x, y - 1);
            n += doCapture(color, x, y + 1);
        }
        
        return n;
    }
    
    /*
     * 指定した座標のグループ（黒、白、空）をかえす。
     * グループは縦か横につながったもので、黒か白の時には string と呼ばれてるらしい。
     */
    private ArrayList<FieldType> getGroup(int x, int y){
        boolean[][] b = new boolean[boardsize + 2][boardsize + 2];
        ArrayList<FieldType> group = new ArrayList<FieldType>();
        
        FieldType type = matrix[x][y];
        return getGroupRecur(type, x ,y, b, group);
    }
    
    private ArrayList<FieldType> getGroupRecur(FieldType targetType, int x, int y, boolean[][] b, ArrayList<FieldType> g){
        if(b[x][y] == true){		// 既に調べた場所なら追加の石なし
            return g;
        }
        
        b[x][y] = true;
        
        FieldType type = matrix[x][y];
        if(targetType.isStone()){
            GoColor color = targetType.getColor();
            if(type.isColor(color)){			// 探している色でないなら追加の石なし
                g.add(type);

                g = getGroupRecur(targetType, x - 1, y, b, g);
                g = getGroupRecur(targetType, x + 1, y, b, g);
                g = getGroupRecur(targetType, x, y - 1, b, g);
                g = getGroupRecur(targetType, x, y + 1, b, g);
            }
            
        }else{
            if(type == targetType){			// 探している色でないなら追加の石なし
                g.add(type);

                g = getGroupRecur(targetType, x - 1, y, b, g);
                g = getGroupRecur(targetType, x + 1, y, b, g);
                g = getGroupRecur(targetType, x, y - 1, b, g);
                g = getGroupRecur(targetType, x, y + 1, b, g);
            }
        }
        return g;
    }

    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer();
        int i, j;
        int msize = boardsize + 2;
        
        for(i = 0; i < msize; ++i){
            for(j = 0; j < msize; ++j){
                FieldType type = matrix[j][i];
                if(type.isEmpty()){
                    sb.append("-");
                }else if(type.isBlack()){
                    sb.append("B");
                }else if(type.isWhite()){
                    sb.append("W");
                }else if(type.isEdge()){
                    sb.append("#");
                }else{
                    sb.append("?");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    
    /*
     *二つの BoardState の変更点一覧をメモる。変更点とは再描画が必要な点ということで以下のもの。
     *・古い着手マーク
     *・古いコウマーク
     *・石が追加削除された位置
     */
    public boolean[][] diff(GoBoardState oldBs, boolean[][] diff){
        int msize = boardsize + 2;
       
        for(int i = 0; i < msize; ++i){
            for(int j = 0; j < msize; ++j){
                FieldType f0 = matrix[j][i];
                FieldType f1 = oldBs.matrix[j][i];
                if(f0 != f1){
                    diff[j][i] = true;
                }
            }
        }
        if(oldBs.hasKo()){
            diff[oldBs.ko_x][oldBs.ko_y] = true;
        }
        GoMove oldMove = oldBs.move;
        if(oldMove != null && oldMove.isPass() == false){
            diff[oldMove.getX()][oldMove.getY()] = true;
        }
        return diff;
    }
}