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
package go;

/**
 * <pre>
 * ボードサイズ毎に以下のものを求める関数の集まり。
 * ・置き石の最大数
 * ・置き石を隅に置く場合の隅からの距離
 * ・置き石の位置
 * 
 * 置き石が最大数の時、その位置が星の位置なので(本当か？)、星を描画する時に使える。
 * 
 * 世間一般には３子の時に黒から見て左上を空けるけど、GnuGo は右下を空ける。
 * </pre>
 */
public class Handicap {

    public static int getMaxHandicap(int boardSize) {
        int max = 0;

        switch (boardSize) {
            case 7:
            case 8:
            case 10:
            case 12:
            case 14:
            case 16:
            case 18:
            case 20:
            case 22:
            case 24:
            case 26:
            case 28:
                max = 4;
                break;
            case 9:
            case 11:
            case 13:
            case 15:
            case 17:
            case 19:
            case 21:
            case 23:
            case 25:
            case 27:
            case 29:
                max = 9;
                break;
            default:
                System.err.println("error Handicap.getMaxHandicap():" + boardSize);
                break;
        }

        return max;
    }

    public static int getEdgeDistance(int boardSize) {
        int edge = 0;

        if (boardSize >= 7 && boardSize <= 12) {
            edge = 3;
        } else if (boardSize >= 13 && boardSize <= 29) {
            edge = 4;
        } else {
            System.err.println("error Handicap.getEdgeDistance():" + boardSize);
        }

        return edge;
    }

        public static GoVertex[] getVertex(int boardSize, int handicap) {
        int edge = getEdgeDistance(boardSize);
        GoVertex[] v;

        if (handicap < 0 || handicap == 1 || handicap > 9) {
            System.err.println("error Handicap.getVertex(): s:" + boardSize + " h:" + handicap);
            v = new GoVertex[0];
            return v;
        }

        int a = edge;
        int b = boardSize - edge + 1;
        int c = boardSize / 2 + 1;
        
        v = new GoVertex[handicap];

        if (handicap >= 2) {
            v[0] = new GoVertex(b, a);	// 右上
            v[1] = new GoVertex(a, b);	// 左下
        }
        if (handicap >= 3) {
            v[2] = new GoVertex(b, b);	// 右下
        }
        if (handicap >= 4) {
            v[3] = new GoVertex(a, a);	// 左上
        }

        if (handicap == 5) {
            v[4] = new GoVertex(c, c);	// 天元
        }
        if (handicap >= 6) {
            v[4] = new GoVertex(a, c);	// 左辺
            v[5] = new GoVertex(b, c);	// 右辺

        }
        if (handicap == 7) {
            v[6] = new GoVertex(c, c);	// 天元
        }
        if (handicap >= 8) {
            v[6] = new GoVertex(c, a);	// 上辺
            v[7] = new GoVertex(c, b);	// 下辺
        }
        if (handicap == 9) {
            v[8] = new GoVertex(c, c);	// 天元
        }

        return v;
    }
        
    public static GoVertex[] getGtpVertex(int boardSize, int handicap) {
        int edge = getEdgeDistance(boardSize);
        GoVertex[] v;

        if (handicap < 0 || handicap == 1 || handicap > 9) {
            System.err.println("error Handicap.getVertex(): s:" + boardSize + " h:" + handicap);
            v = new GoVertex[0];
            return v;
        }

        int a = edge;
        int b = boardSize - edge + 1;
        int c = boardSize / 2 + 1;
        
        v = new GoVertex[handicap];

        if (handicap >= 2) {
            v[0] = new GoVertex(b, a);	// 右上
            v[1] = new GoVertex(a, b);	// 左下
        }
        if (handicap >= 3) {
            v[2] = new GoVertex(a, a);	// 左上  ここが違う＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃
        }
        if (handicap >= 4) {
            v[3] = new GoVertex(b, b);	// 右下  ここが違う＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃
        }

        if (handicap == 5) {
            v[4] = new GoVertex(c, c);	// 天元
        }
        if (handicap >= 6) {
            v[4] = new GoVertex(a, c);	// 左辺
            v[5] = new GoVertex(b, c);	// 右辺

        }
        if (handicap == 7) {
            v[6] = new GoVertex(c, c);	// 天元
        }
        if (handicap >= 8) {
            v[6] = new GoVertex(c, a);	// 上辺
            v[7] = new GoVertex(c, b);	// 下辺
        }
        if (handicap == 9) {
            v[8] = new GoVertex(c, c);	// 天元
        }

        return v;
    }
}
