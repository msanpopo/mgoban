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
package wing;

import go.GoRank;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * NNGS 系サーバーがあつかう「対局」。
 * <pre>
 * games コマンドでかえってくる各行が「対局」。
 * 
 * games コマンドで帰ってくる文字列の例。
 * [##]  white name [ rk ]      black name [ rk ] (Move size H Komi BY FR) (###)
 * [ 2]         tki [ 6k*] vs.    tnjsara2 [ 6k*] ( 78   19  0  0.5  5  I) (  0)
 * [ 4]        no02 [10k*] vs.     abc2002 [10k*] (159   19  0  2.5 10  I) (  0)
 * </pre>
 */
public class WingGame implements Comparable<WingGame> {

    private static final Pattern p0 = Pattern.compile("\\[\\s*(\\d+)\\]\\s*(.*) \\[\\s*([^\\s]+)\\s*\\] vs\\.\\s*(.*) \\[\\s*([^\\s]+)\\s*\\] \\((.*)\\) \\(\\s*(.*)\\)");
    private static final Pattern p1 = Pattern.compile("\\s*(\\d+)\\s*(\\d+)\\s*(\\d+)\\s*([-.0-9]*)\\s*(\\d+)\\s*(.*)");
    private static final Pattern pGameStr = Pattern.compile(".*\\] (.*)");
    
    private String origStr;
    private String gameStr; // 着手番号以降の文字列
    private int gameNo;
    private User black;
    private User white;
    private int moveNo;
    private int boardSize;
    private int handicap;
    private double komi;
    private int byoYomiTime;
    private String gameType;    // この文字が何を洗わしているのか不明。

    private int observer;
    private boolean initialized;
    
    /**
     * 
     * @param s サーバーからの対局をあらわす文字列。 
     * <pre>例："[ 2]         tki [ 6k*] vs.    tnjsara2 [ 6k*] ( 78   19  0  0.5  5  I) (  0)"</pre>
     */
    public WingGame(String s) {
        initialized = false;

        origStr = s;

        Matcher mGameStr = pGameStr.matcher(s);
        if (mGameStr.matches()) {
            gameStr = mGameStr.group(1);
        } else {
            System.err.println("error:Game :gameStr parse fail:" + s);
            initialized = false;
            return;
        }

        Matcher m0 = p0.matcher(s);
        if (m0.matches()) {
            gameNo = Integer.parseInt(m0.group(1));
            String wName = m0.group(2);
            String wRank = m0.group(3);
            String bName = m0.group(4);
            String bRank = m0.group(5);
            String gameInfo = m0.group(6);
            observer = Integer.parseInt(m0.group(7));

            black = new User(bName, bRank);
            white = new User(wName, wRank);

            Matcher m1 = p1.matcher(gameInfo);
            // System.out.println(gameInfo);
            if (m1.matches()) {
                moveNo = Integer.parseInt(m1.group(1));
                boardSize = Integer.parseInt(m1.group(2));
                handicap = Integer.parseInt(m1.group(3));
                komi = Double.parseDouble(m1.group(4));
                byoYomiTime = Integer.parseInt(m1.group(5));
                gameType = m1.group(6);
            } else {
                System.err.println("error:Game :can not parse gameInfo:" + s);
                initialized = false;
                return;
            }
        } else {
            System.err.println("error:GameObject :can not parse line:" + s);
            initialized = false;
            return;
        }

        initialized = true;
    }

    public boolean isInitialized(){
        return initialized;
    }
    
    public User getBlack(){
        return black;
    }
    
    public User getWhite(){
        return white;
    }
    
    public String getBlackName() {
        return black.getName();
    }

    public String getWhiteName() {
        return white.getName();
    }

    public GoRank getBlackRank() {
        return black.getRank();
    }

    public GoRank getWhiteRank() {
        return white.getRank();
    }

    public int getGameNo() {
        return gameNo;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public int getHandicap() {
        return handicap;
    }
    
    // 置き石は handicap コマンドで後で修正されることがある
    public void setHandicap(int newHandicap){
        handicap = newHandicap;
    }

    public double getKomi() {
        return komi;
    }

    public int getByoYomiTime() {
        return byoYomiTime;
    }

    public String getGameString(){
        return gameStr;
    }

    @Override
    public String toString() {
        return origStr;
    }

    public int compareTo(WingGame g) {
        if (white.compareTo(g.white) == 0) {
            if (white.getName().equals(g.white.getName()) && black.getName().equals(g.black.getName())) {
                return 0;
            } else {
                return 1;
            }
        } else {
            return white.compareTo(g.white);
        }
    }
}