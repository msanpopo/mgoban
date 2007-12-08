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
package go;

/**
 * 囲碁での色をあらわす。黒または白。
 * <pre>
 * gtp:大文字小文字関係なし
 * 	黒 : "b" or "black"
 * 	白 : "w" or "white"
 *
 * sgf:大文字のみ
 * 	黒 : "B"
 * 	白 : "W"
 * </pre>
 */
public enum GoColor {

    BLACK("Black"),
    WHITE("White");
    private final String str;

    private GoColor(String str) {
        this.str = str;
    }

    /**
     * 文字から、それに相当する色を得る。<br>
     * 'B', 'b', 'W', 'w' の四文字だけが有効で、それ以外の場合は null を返す。
     * @param c 'B', 'b', 'W', 'w' の四文字だけが有効。
     * @return 黒または白。無効な文字の時には null が返る。
     */
    public static GoColor get(char c) {
        if (c == 'B' || c == 'b') {
            return BLACK;
        } else if (c == 'W' || c == 'w') {
            return WHITE;
        } else {
            System.err.println("error:Color.get() : unknown color c:" + c);
            return null;
        }
    }
    
    /**
     * 文字列から、それに相当する色を得る。<br>
     * 
     * @param c 'B', 'b', 'W', 'w' の四文字だけが有効。
     * @return 黒または白。無効な文字列の時には null が返る。
     */
    public static GoColor get(String str) {
        String s = str.toLowerCase();

        if (s.equals("b") || s.equals("black")) {
            return BLACK;
        } else if (s.equals("w") || s.equals("white")) {
            return WHITE;
        } else {
            System.err.println("error:Color.get() : unknown color str:" + str);
            return null;
        }
    }

    public String toGtpString() {
        if (this == BLACK) {
            return "b";
        } else {
            return "w";
        }
    }

    public String toSgfString() {
        if (this == BLACK) {
            return "B";
        } else {
            return "W";
        }
    }

    @Override
    public String toString() {
        return str;
    }

    public GoColor opponentColor() {
        if (this == BLACK) {
            return WHITE;
        } else {
            return BLACK;
        }
    }

    public boolean isBlack() {
        if (this == BLACK) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isWhite() {
        if (this == WHITE) {
            return true;
        } else {
            return false;
        }
    }
}