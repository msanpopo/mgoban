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

package go.board.gui;

import sgf.property.PropertyId;


public enum MarkType {
    CIRCLE(0, PropertyId.CR),
    TRIANGLE(1, PropertyId.TR),
    SQUARE(2, PropertyId.SQ),
    MARK(3, PropertyId.MA),
    SELECTED(4, PropertyId.SL);
    
    private int n;
    private PropertyId id;
    
    private MarkType(int n, PropertyId id){
        this.n = n;
        this.id = id;
    }
    
    public int getN(){
        return n;
    }
    
    public PropertyId getPropertyId(){
        return id;
    }
    
    public MarkType getNextType(){
        int next = n + 1;
        if(next > 4){
            next = 0;
        }
        return get(next);
    }
    
    public MarkType getPrevType(){
        int prev = n - 1;
        if(prev < 0){
            prev = 4;
        }
        return get(prev);
    }
    
    private MarkType get(int n){
        for(MarkType type : MarkType.values()){
            if(n == type.n){
                return type;
            }
        }
        return CIRCLE;
    }
}