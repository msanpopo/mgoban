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
package sgf.property;

import sgf.value.ValueDouble;

public enum MoveAnnotation {
    UNKNOWN("", PropertyId.UNKNOWN),
    GOOD("Good", PropertyId.TE),
    VERY_GOOD("Very good", PropertyId.TE),
    BAD("Bad", PropertyId.BM),
    VERY_BAD("Very bad", PropertyId.BM),
    DOUBTFUL("Doubtful", PropertyId.DO),
    INTERESTING("Interesting", PropertyId.IT);
    
    private String message;
    private PropertyId id;

    private MoveAnnotation(String message, PropertyId id) {
        this.message = message;
        this.id = id;
    }
    
    public static MoveAnnotation propertyToAnnotation(Property p){
        PropertyId pid = p.getId();
        switch(pid){
            case TE:
            {
                ValueDouble vd = (ValueDouble)p.getValue();
                if(vd.isNormal()){
                    return GOOD;
                }else{
                    return VERY_GOOD;
                }
            }
            case BM:
            {
                ValueDouble vd = (ValueDouble)p.getValue();
                if(vd.isNormal()){
                    return BAD;
                }else{
                    return VERY_BAD;
                }
            }
            case DO:
                return DOUBTFUL;
            case IT:
                return INTERESTING;
            default:
                return UNKNOWN;
        }
    }

    public String getMessage() {
        return message;
    }

    public Property getProperty() {
        Property p = new Property(id);

        switch (this) {
            case GOOD:
                p.setValue(new ValueDouble(ValueDouble.SgfDouble.NORMAL));
                break;
            case VERY_GOOD:
                p.setValue(new ValueDouble(ValueDouble.SgfDouble.EMPHASIZED));
                break;
            case BAD:
                p.setValue(new ValueDouble(ValueDouble.SgfDouble.NORMAL));
                break;
            case VERY_BAD:
                p.setValue(new ValueDouble(ValueDouble.SgfDouble.EMPHASIZED));
                break;
            default:
                break;
        }


        return p;
    }
    
    public boolean equals(PropertyId propId){
        if(id == propId){
            return true;
        }else{
            return false;
        }
    }
    
    @Override
    public String toString(){
        return message;
    }
}