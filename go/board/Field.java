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

public class Field{
    private int x;  // 1-19
    private int y;
    private FieldType fieldType;
    
    // 下は終局処理の時のみ使用
    
    /*
     * その Field の石が死んでいるかどうかをあらわす。
     * EMPTY や EDGE では true にしてはいけない。
     */
    private boolean isDead;
    
    /*
     * その Field が黒地か白地かをあらわす。
     * 石がある場所でも、その石が死んでいれば地として数える。
     * いきている石は NOT_TERRITORY になる。
     */
    private TerritoryType territoryType;
    
    
    public Field(int x, int y){
        this.x = x;
        this.y = y;
        this.fieldType = FieldType.EMPTY;
        
        this.isDead = false;
        this.territoryType = TerritoryType.NOT_TERRITORY;
    }
    
    public Field(Field f){
        x = f.x;
        y = f.y;
        
        FieldType type = f.fieldType;
        if(type.isEmpty()){
            type = FieldType.EMPTY;
        }else if(type.isBlack()){
            type = FieldType.BLACK;
        }else if(type.isWhite()){
            type = FieldType.WHITE;
        }
        
        isDead = false;
        territoryType = TerritoryType.NOT_TERRITORY;
    }
    
    public FieldType getType(){
        return fieldType;
    }
    
    public void setType(FieldType t){
        fieldType = t;
    }
    
    public boolean isEdge(){
        return fieldType.isEdge();
    }
    
    public boolean isEmpty(){
        return fieldType.isEmpty();

    }
    
    public boolean isBlack(){
        return fieldType.isBlack();
    }
    
    public boolean isWhite(){
        return fieldType.isWhite();
    }
    
    public boolean isColor(GoColor color){
        if(color.isBlack()){
            return isBlack();
        }else{
            return isWhite();
        }
    }

    public boolean isStone(){
        if(isBlack() || isWhite()){
            return true;
        }else{
            return false;
        }
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    public void setTerritoryType(TerritoryType ttype){
        territoryType = ttype;
    }
    
    public TerritoryType getTerritoryType(){
        return territoryType;
    }
    
    public boolean isBlackTerritory(){
        if(territoryType == TerritoryType.BLACK){
            return true;
        }else{
            return false;
        }
    }
    
    public boolean isWhiteTerritory(){
        if(territoryType == TerritoryType.WHITE){
            return true;
        }else{
            return false;
        }
    }
    
    public void setDead(boolean b){
        isDead = b;
    }
    
    public void toggleDead(){
        if(isDead){
            isDead = false;
        }else{
            isDead = true;
        }
    }
    
    public boolean isDead(){
        return isDead;
    }
    
    public boolean isLive(){
        return !isDead;
    }
}