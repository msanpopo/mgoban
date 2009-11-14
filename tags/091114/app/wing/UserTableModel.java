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

package app.wing;

import java.util.Collection;

import javax.swing.table.AbstractTableModel;

import wing.WingUser;

@SuppressWarnings("serial")
public class UserTableModel  extends AbstractTableModel{
    private static final String USER = "入場者一覧";
    
    private static final String[] header = {USER};
    private static final int[] columnWidth = {250};
    
    private WingUser[] userArray;
    
    public UserTableModel(){
        userArray = new WingUser[0];
    }
    
    public WingUser getUser(int index){
        return userArray[index];
    }
    
    public void clear(){
        userArray = new WingUser[0];
        
        fireTableDataChanged();
    }
    
    public void setUser(Collection<WingUser> userSet){
        int size = userSet.size();
        
        userArray = new WingUser[size];
        int i = 0;
        for(WingUser u : userSet){
            // userSet の順番そのままだと高段が下になるのでこざかしいけどここで入れ替える。
            userArray[size - 1 - i] = u;
            i += 1;
        }
        
        fireTableDataChanged();
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
        return userArray.length;
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex){
        switch(columnIndex){
            case 0:
                return WingUser.class;
            default:
                return Object.class;
        }
    }
    
    public Object getValueAt(int row, int column) {
        WingUser user = userArray[row];
        Object o;
        
        switch(column){
            case 0:
                o = user;
                break;
            default:
                o = "Unknown";
                break;
        }
        return o;
    }
}