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

package sgf;

public interface GameTreeListener {
    /*
     * ・新しいツリーがセットされた
     * ・コレクション内でカレントツリーが変更になった
     *
     * ツリーが変更になった場合、盤のサイズやカレントノードがかわる
     */
    public void treeChanged(GameTree tree, int boardSize);
    
    public void nodeMoved(GameTree tree, GoNode old);
    
    /*
     * ノード内のプロパティーや BoardState が変化した
     */
    public void nodeStateChanged(GameTree tree);
}