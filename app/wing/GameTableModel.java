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

package app.wing;

import java.util.Collection;
import javax.swing.table.AbstractTableModel;

import wing.Game;
import wing.User;

@SuppressWarnings("serial")
public class GameTableModel extends AbstractTableModel{
    private static final String NO = "番号";
    private static final String WHITE = "白";
    private static final String BLACK = "黒";
    private static final String GAME_STATE = "状態";
    
    private static final String[] header = {NO, WHITE, BLACK, GAME_STATE};
    private static final int[] columnWidth = {30, 120, 120, 290};
    
    private Game[] gameArray;
    
    public GameTableModel(){
        gameArray = new Game[0];
    }
    
    public void clear(){
        gameArray = new Game[0];
        
        fireTableDataChanged();
    }
    
    public void setGame(Collection<Game> gameSet){
        int size = gameSet.size();
        
        gameArray = new Game[size];
        int i = 0;
        for(Game g : gameSet){
            gameArray[size - 1 - i] = g;     // gameSet は、ランクの弱い順になっているので入れ替える
            i += 1;
        }
        
        fireTableDataChanged();
    }
    
    public Game getGame(int index){
        return gameArray[index];
    }

    public int[] getColumnWidth(){
        return columnWidth;
    }
    
    @Override
    public String getColumnName(int column){
        return header[column];
    }
    
    public int getColumnCount() {
        return header.length;
    }
    
    public int getRowCount() {
        return gameArray.length;
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex){
        //System.out.println("GameTableModel.getColumnClass:" + columnIndex);
        
        switch(columnIndex){
            case 0:
                return Integer.class;
            case 1:
            case 2:
                return User.class;
            case 3:
                return Game.class;
            default:
                return Object.class;
        }
    }
    
    public Object getValueAt(int row, int column) {
        // System.out.println("row:" + row + " ar.lenght:" + gameArray.length);
        Game game = gameArray[row];
        Object o;
        
        switch(column){
            case 0:
                o = game.getGameNo();
                break;
            case 1:
                o = game.getWhite();
                break;
            case 2:
                o = game.getBlack();
                break;
            case 3:
                o = game.getGameString();
                break;
            default:
                o = "Unknown";
                break;
        }
        return o;
    }
}