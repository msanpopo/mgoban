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

public enum FieldType {
    /** 番の外 */
    EDGE,
    /** 石がない */
    EMPTY,
    /** 着手で置いた黒石 */
    BLACK,
    /** 着手で置いた白石 */
    WHITE,
    /** AE プロパティーで石を取り除いた場所 */
    ADD_EMPTY,
    /** AB プロパティーで置いた黒石 */
    ADD_BLACK,
    /** AW プロパティーで置いた白石 */
    ADD_WHITE;
    
    public static FieldType get(GoColor c){
        if(c == GoColor.BLACK){
            return BLACK;
        }else if(c == GoColor.WHITE){
            return WHITE;
        }else{
            System.err.println("FieldType.get:" + c.toString());
            return EMPTY;   // TODO あるのか？
        }
    }
    /*
    private final boolean hasStone;
     
    private FieldType(Boolean hasStone){
        this.hasStone = hasStone;
    }
     */
    
    public boolean isEdge(){
        if(this == EDGE){
            return true;
        }else{
           return false;
        }
    }
    
    public boolean isEmpty(){
        if(this == EMPTY || this == ADD_EMPTY){
            return true;
        }else{
            return false;
        }
    }
    
    public boolean isBlack(){
        if(this == BLACK || this == ADD_BLACK){
            return true;
        }else{
            return false;
        }
    }
    
    public boolean isWhite(){
        if(this == WHITE || this == ADD_WHITE){
            return true;
        }else{
            return false;
        }
    }
    
    public boolean isColor(GoColor color){
        if(color.isBlack()){
            return isBlack();
        }else if(color.isWhite()){
            return isWhite();
        }else{
            return false;
        }
    }
    
    public boolean isStone(){
        if(isBlack() || isWhite()){
            return true;
        }else{
            return false;
        }
    }
    
    public GoColor getColor(){
        if(isBlack()){
            return GoColor.BLACK;
        }else if(isWhite()){
            return GoColor.WHITE;
        }else{
            System.err.println("err: FieldType.getColor: this type is not stone:" + this);
            return null;
        }
    }
}