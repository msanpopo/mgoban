/*
 *  mGoban - GUI for Go
 *  Copyright (C) 2007, 2009, 2010  sanpo
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

package sgf.property;

import go.GoColor;
import go.GoVertex;
import java.util.Collection;
import sgf.value.Value;
import sgf.value.ValueColor;
import sgf.value.ValuePointList;

public class SetupProperty {
    private ValuePointList black;
    private ValuePointList white;
    private ValuePointList empty;
    
    private ValueColor pl;
    
    public SetupProperty() {
        black = new ValuePointList();
        white = new ValuePointList();
        empty = new ValuePointList();
        
        pl = null;
    }
    
    private boolean hasValue(Value v){
        return (v != null) ? true : false;
    }
    
    public boolean hasNextColor(){
        return hasValue(pl);
    }
    
    public boolean hasProperty(PropertyId id){
        switch(id){
        case AB:
            return hasValue(black);
        case AW:
            return hasValue(white);
        case AE:
            return hasValue(empty);
        case PL:
            return hasValue(pl);
        default:
            System.err.println("err:SetupProperty.hasProperty: id is not setup property:" + id);
            return false;
        }
    }
    
    public void removeProperty(PropertyId id){
        switch(id){
        case AB:
            black = null;
            break;
        case AW:
            white = null;
            break;
        case AE:
            empty = null;
            break;
        case PL:
            pl = null;
            break;
        default:
            System.err.println("err:SetupProperty.removeProperty: id is not setup property:" + id);
            break;
        }
    }
    
    /*
     * AB, AW, AE に渡す v は、「黒石に黒石を上書きする」のような状態でないことを事前にチェックすること。
     *
     * 現状ではもとから空の場所と AE で空になった場所の区別がつかないので以下のような変な処理になってる。＃＃＃＃＃＃＃
     * addBlack では、元の場所に AE があった場合には AB を追加せずに AE を削除だけする。
     */
    public void addBlack(GoVertex v){
        ValuePointList list = findPointList(v);
        if(list == empty){
            empty.removePoint(v);
        }else{
            removeDuplicate(v);
            black.addPoint(v);
        }
    }
    
    public void addWhite(GoVertex v){
        ValuePointList list = findPointList(v);
        if(list == empty){
            empty.removePoint(v);
        }else{
            removeDuplicate(v);
            white.addPoint(v);
        }
    }
    
    // v に AB, AW, AE があればそれを削除する（その時 AE は追加しない)。なければ AE を追加する。
    public void removeStone(GoVertex v){
        ValuePointList list = findPointList(v);
        if(list == null){
            empty.addPoint(v);
        }else{
            list.removePoint(v);
        }
    }
    
    public boolean hasEmptyPoint(GoVertex v){
        ValuePointList list = findPointList(v);
        if(list == empty){
            return true;
        }else{
            return false;
        }
    }
    
    public boolean hasStone(GoVertex v){
        ValuePointList list = findPointList(v);
        if(list == black || list == white){
            return true;
        }else{
            return false;
        }
    }
    
    // ダブリを削除。なければ何もしない。
    private void removeDuplicate(GoVertex v){
        ValuePointList list = findPointList(v);
        if(list != null){
            list.removePoint(v);
        }
    }
    
    private ValuePointList findPointList(GoVertex v){
        if(black.hasPoint(v)){
            return black;
        }else if(white.hasPoint(v)){
            return white;
        }else if(empty.hasPoint(v)){
            return empty;
        }else{
            return null;
        }
    }
    
    public Collection<GoVertex> getBlackCollection(){
        return black.getCollection();
    }
    
    public Collection<GoVertex> getWhiteCollection(){
        return white.getCollection();
    }
    
    public Collection<GoVertex> getEmptyCollection(){
        return empty.getCollection();
    }
    
    public GoColor getNextColor(){
        if(pl != null){
            return pl.getColor();
        }else{
            return null;
        }
    }
    
    public void setNextColor(GoColor color){
        if(color == null){
            pl = null;
            return;
        }
        
        if(pl == null){
            pl = new ValueColor(color);
        }else{
            pl.setColor(color);
        }
    }
    
    public void setProperty(Property p){
        PropertyId id = p.getId();
        if(id == PropertyId.AB){
            black = (ValuePointList)p.getValue();
            
        }else if(id == PropertyId.AW){
            white = (ValuePointList)p.getValue();
            
        }else if(id == PropertyId.AE){
            empty = (ValuePointList)p.getValue();
            
        }else if(id == PropertyId.PL){
            pl = (ValueColor)p.getValue();
        }
    }
    
    private void valuePointListToSgfString(StringBuilder str, ValuePointList list, PropertyId id){
        if(list.isEmpty() == false){
            str.append(new Property(id, list).toSgfString());
        }
    }
    
    public String toSgfString(){
        StringBuilder str = new StringBuilder();
        
        valuePointListToSgfString(str, black, PropertyId.AB);
        valuePointListToSgfString(str, white, PropertyId.AW);
        valuePointListToSgfString(str, empty, PropertyId.AE);
        
        if(pl != null){
            str.append(new Property(PropertyId.PL, pl).toSgfString());
        }
        
        return str.toString();
    }
}