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

package go.board;

import go.GoColor;
import go.GoResult;
import go.GoVertex;
import java.util.ArrayList;

public class GoBoardTerritoryState {
    private int boardSize;
    private GoBoardState board;
    private Field[][] territory;
    
    private int resultBlackTerritory;
    private int resultWhiteTerritory;
    private int resultBlackDead;	// 取られた黒石の数(終局処理で取り上げた死)
    private int resultWhiteDead;
    
    public GoBoardTerritoryState(GoBoardState board) {
        boardSize = board.getBoardSize();
        this.board = board;
        int msize = boardSize + 2;
        territory = new Field[msize][msize];
        
        for(int i = 0; i < msize; ++i){
            for(int j = 0; j < msize; ++j){
                territory[j][i] = new Field(j ,i);
                territory[j][i].setType(board.getType(j, i));
            }
        }
        
        resultBlackTerritory = 0;
        resultWhiteTerritory = 0;
        resultBlackDead = 0;
        resultWhiteDead = 0;
    }
    
    // v は必ず石のある座標でないといけない。事前に isStone でチェックする。
    public void markDeadStone(GoVertex v){
        int x = v.getX();
        int y = v.getY();
        Field f = territory[x][y];
        
        f.setDead(true);
        
        checkTerritory();
    }
    
    // v は必ず石のある座標でないといけない。事前に isStone でチェックする。
    public void markDeadStoneGroup(GoVertex v){
        int x = v.getX();
        int y = v.getY();
        ArrayList<Field> group = getGroup(x, y);
        for(Field f : group){
            f.setDead(true);
        }
        
        checkTerritory();
    }
    
    public void toggleDeadStoneGroup(GoVertex v){
        int x = v.getX();
        int y = v.getY();
        ArrayList<Field> group = getGroup(x, y);
        for(Field f : group){
            f.toggleDead();
        }
        
        checkTerritory();
    }
    
    public void setResultBoard(int[][] boardArray){
        for(int i = 0; i < boardSize; ++i){
            for(int j = 0; j < boardSize; ++j){
                int c = boardArray[j][i];
                Field f = territory[j + 1][i + 1];
                
                // 石があるのに黒地や白地とされている部分を死に石としてマークする
                if(c == 0){
                    // 黒石
                }else if(c == 1){
                    // 白石
                }else if(c == 3){
                    // 空
                }else if(c == 4){
                    // 白地
                    if(f.isBlack()){
                        f.setDead(true);
                    }
                    // f.setTerritoryType(WHITE); 後で計算させてるので不要
                }else if(c == 5){
                    // 黒地
                    if(f.isWhite()){
                        f.setDead(true);
                    }
                    // f.setTerritoryType(BLACK);
                }
            }
        }
        
        checkTerritory();
    }
    
    public TerritoryType getTerritoryType(int x, int y){
        Field f = territory[x][y];
        return f.getTerritoryType();
    }
    
    public boolean isDead(int x, int y){
        Field f = territory[x][y];
        return f.isDead();
    }
    
    /*
     * 指定した座標のグループ（黒、白、空）をかえす。
     * グループは縦か横につながったもので、黒か白の時には string と呼ばれてるらしい。
     */
    private ArrayList<Field> getGroup(int x, int y){
        boolean[][] b = new boolean[boardSize + 2][boardSize + 2];
        ArrayList<Field> group = new ArrayList<Field>();
        
        FieldType color = territory[x][y].getType();
        return getGroupRecur(color, x ,y, b, group);
    }
    
    private ArrayList<Field> getGroupRecur(FieldType targetType, int x, int y, boolean[][] b, ArrayList<Field> g){
        if(b[x][y] == true){		// 既に調べた場所なら追加の石なし
            return g;
        }
        
        b[x][y] = true;
        
        Field f = territory[x][y];
        FieldType type = f.getType();
        if(targetType.isStone()){
            GoColor color = targetType.getColor();
            if(type.isColor(color)){			// 探している色でないなら追加の石なし
                g.add(f);

                g = getGroupRecur(targetType, x - 1, y, b, g);
                g = getGroupRecur(targetType, x + 1, y, b, g);
                g = getGroupRecur(targetType, x, y - 1, b, g);
                g = getGroupRecur(targetType, x, y + 1, b, g);
            }
            
        }else{
            if(type == targetType){			// 探している色でないなら追加の石なし
                g.add(f);

                g = getGroupRecur(targetType, x - 1, y, b, g);
                g = getGroupRecur(targetType, x + 1, y, b, g);
                g = getGroupRecur(targetType, x, y - 1, b, g);
                g = getGroupRecur(targetType, x, y + 1, b, g);
            }
        }
        return g;
    }
    
    /*
     * ひとまとまりの地を検索してかえす。
     * 空か死に石指定された石のある場所が検索対象。
     */
    private ArrayList<Field> getTerritoryGroup(int x, int y){
        Field f = territory[x][y];
        
        ArrayList<Field> group = new ArrayList<Field>();
        
        // 空のところと石があっても死に石指定されているところを地としてチェックする
        if(f.isEmpty() || f.isDead()){
            boolean[][] b = new boolean[boardSize + 2][boardSize + 2];
            
            group = getTerritoryGroupRecur(x ,y, b, group);
        }
        
        return group;
    }
    
    private ArrayList<Field> getTerritoryGroupRecur(int x, int y, boolean[][] b, ArrayList<Field> g){
        if(b[x][y] == true){		// 既に調べた場所なら追加の石なし
            return g;
        }
        
        b[x][y] = true;
        
        Field f = territory[x][y];
        if(f.isEmpty() || f.isDead()){
            g.add(f);
            
            g = getTerritoryGroupRecur(x - 1, y, b, g);
            g = getTerritoryGroupRecur(x + 1, y, b, g);
            g = getTerritoryGroupRecur(x, y - 1, b, g);
            g = getTerritoryGroupRecur(x, y + 1, b, g);
        }
        return g;
    }
    
    public void checkTerritory(){
        System.out.println("checkTerritory");
        
        // 地の情報を初期化
        for(int i = 1; i <= boardSize; ++i){
            for(int j = 1; j <= boardSize; ++j){
                Field f = territory[j][i];
                f.setTerritoryType(TerritoryType.UNKNOWN);
            }
        }
        
        for(int i = 1; i <= boardSize; ++i){
            for(int j = 1; j <= boardSize; ++j){
                Field f = territory[j][i];
                
                if(f.getTerritoryType() == TerritoryType.UNKNOWN){
                    ArrayList<Field> group = getTerritoryGroup(j, i);
                    boolean hasLiveBlack = false;
                    boolean hasLiveWhite = false;
                    for(Field tmpf : group){
                        int xx = tmpf.getX();
                        int yy = tmpf.getY();
                        Field w = territory[xx - 1][yy];
                        Field e = territory[xx + 1][yy];
                        Field s = territory[xx][yy + 1];
                        Field n = territory[xx][yy - 1];
                        if(w.isBlack() && w.isLive()){
                            hasLiveBlack = true;
                        }else if(w.isWhite() && w.isLive()){
                            hasLiveWhite = true;
                        }
                        if(e.isBlack() && e.isLive()){
                            hasLiveBlack = true;
                        }else if(e.isWhite() && e.isLive()){
                            hasLiveWhite = true;
                        }
                        if(n.isBlack() && n.isLive()){
                            hasLiveBlack = true;
                        }else if(n.isWhite() && n.isLive()){
                            hasLiveWhite = true;
                        }
                        if(s.isBlack() && s.isLive()){
                            hasLiveBlack = true;
                        }else if(s.isWhite() && s.isLive()){
                            hasLiveWhite = true;
                        }
                    }
                    
                    TerritoryType result;
                    if(hasLiveBlack && !hasLiveWhite){         // 黒地
                        result = TerritoryType.BLACK;
                        
                    }else if(!hasLiveBlack && hasLiveWhite){  // 白地
                        result = TerritoryType.WHITE;
                    }else{                                   // それ以外
                        result = TerritoryType.NOT_TERRITORY;
                    }
                    
                    for(Field tmpf : group){
                        tmpf.setTerritoryType(result);
                    }
                }
            }
        }
        
        resultBlackTerritory = 0;
        resultWhiteTerritory = 0;
        resultBlackDead = 0;
        resultWhiteDead = 0;
        
        for(int i = 1; i <= boardSize; ++i){
            for(int j = 1; j <= boardSize; ++j){
                Field f = territory[j][i];
                
                if(f.isBlackTerritory()){
                    resultBlackTerritory += 1;
                }else if(f.isWhiteTerritory()){
                    resultWhiteTerritory += 1;
                }
                
                if(f.isDead()){
                    if(f.isBlack()){
                        resultBlackDead += 1;
                    }else if(f.isWhite()){
                        resultWhiteDead += 1;
                    }
                }
            }
        }
        /*
        System.out.println(toString());
        System.out.println("---------------------------");
        System.out.println(toTerritoryString());
        */
    }
    
    public int getBlackTerritory(){
        return resultBlackTerritory;
    }
    
    public int getWhiteTerritory(){
        return resultWhiteTerritory;
    }
    
    public int getBlackDead(){
        return resultBlackDead;
    }
    public int getWhiteDead(){
        return resultWhiteDead;
    }
    
    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer();
        int msize = boardSize + 2;
        
        for(int i = 0; i < msize; ++i){
            for(int j = 0; j < msize; ++j){
                FieldType type = territory[j][i].getType();
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
    
    public String toTerritoryString(){
        StringBuffer sb = new StringBuffer();
        int msize = boardSize + 2;
        
        for(int i = 0; i < msize; ++i){
            for(int j = 0; j < msize; ++j){
                Field f = territory[j][i];
                if(f.isBlackTerritory()){
                    sb.append("b");
                }else if(f.isWhiteTerritory()){
                    sb.append("w");
                }else if(f.isEdge()){
                    sb.append("#");
                }else if(f.isBlack()){
                    sb.append("B");
                }else if(f.isWhite()){
                    sb.append("W");
                }else{
                    sb.append("?");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    
    public GoResult getResult(double komi){
        double point = getPoint(komi);
        GoColor winner = getWinnerColor(komi);
        
        GoResult result = GoResult.createScoreResult(winner, point);
        
        return result;
    }
    
    private GoColor getWinnerColor(double komi){
        checkTerritory();
        
        int bt = getBlackTerritory();
        int wt = getWhiteTerritory();
        int bp = board.getPrisoner(GoColor.BLACK);
        int wp = board.getPrisoner(GoColor.WHITE);
        int bd = getBlackDead();
        int wd = getWhiteDead();
        
        double bPoint = 0.0;
        double wPoint = 0.0;
        
        if(komi > 0){
            bPoint = bt + wp + wd;
            wPoint = wt + bp + bd + komi;
        }else{
            bPoint = bt + wp + wd - komi;
            wPoint = wt + bp + bd;
        }
        
        if(bPoint > wPoint){
            return GoColor.BLACK;
        }else if(bPoint == wPoint){
            return null;
        }else{
            return GoColor.WHITE;
        }
    }
    
    private double getPoint(double komi){
        checkTerritory();
        
        int bt = getBlackTerritory();
        int wt = getWhiteTerritory();
        int bp = board.getPrisoner(GoColor.BLACK);
        int wp = board.getPrisoner(GoColor.WHITE);
        int bd = getBlackDead();
        int wd = getWhiteDead();
        
        double bPoint = 0.0;
        double wPoint = 0.0;
        
        if(komi > 0){
            bPoint = bt + wp + wd;
            wPoint = wt + bp + bd + komi;
        }else{
            bPoint = bt + wp + wd - komi;
            wPoint = wt + bp + bd;
        }
        
        if(bPoint > wPoint){
            return bPoint - wPoint;
        }else if(bPoint == wPoint){
            return 0.0;
        }else{
            return wPoint - bPoint;
        }
    }
    
    public String getResultString(double komi){
        System.out.println("getResultString");
        
        checkTerritory();
        
        int bt = getBlackTerritory();
        int wt = getWhiteTerritory();
        int bp = board.getPrisoner(GoColor.BLACK);
        int wp = board.getPrisoner(GoColor.WHITE);
        int bd = getBlackDead();
        int wd = getWhiteDead();
        
        double bPoint = 0.0;
        double wPoint = 0.0;
        
        String BLACK = java.util.ResourceBundle.getBundle("app/resource/Resource").getString("Black");
        String WHITE = java.util.ResourceBundle.getBundle("app/resource/Resource").getString("White");
        
        StringBuilder str = new StringBuilder();
        if(komi > 0){
            bPoint = bt + wp + wd;
            wPoint = wt + bp + bd + komi;
            str.append(BLACK).append(": ").append(bt).append(" + ").append(wp + wd);
            str.append(" = ").append(bPoint).append("\n");
            str.append(WHITE).append(": ").append(wt).append(" + ").append(bp + bd).append(" + ").append(komi);
            str.append(" = ").append(wPoint).append("\n\n");
        }else{
            bPoint = bt + wp + wd - komi;
            wPoint = wt + bp + bd;
            str.append(BLACK).append(": ").append(bt).append(" + ").append(wp + wd).append(" + ").append(-komi);
            str.append(" = ").append(bPoint).append("\n");
            str.append(WHITE).append(": ").append(wt).append(" + ").append(bp + bd);
            str.append(" = ").append(wPoint).append("\n\n");
        }
        
        if(bPoint > wPoint){
            str.append(BLACK).append(java.util.ResourceBundle.getBundle("app/resource/Resource").getString("WinPoint1"));
            str.append(" ").append(bPoint - wPoint).append(" ").append(java.util.ResourceBundle.getBundle("app/resource/Resource").getString("WinPoint2"));
        }else if(bPoint == wPoint){
            str.append(java.util.ResourceBundle.getBundle("app/resource/Resource").getString("DRAW."));
        }else{
            str.append(WHITE).append(java.util.ResourceBundle.getBundle("app/resource/Resource").getString("WinPoint1"));
            str.append(" ").append(bPoint - wPoint).append(" ").append(java.util.ResourceBundle.getBundle("app/resource/Resource").getString("WinPoint2"));
        }
        return str.toString();
    }
}