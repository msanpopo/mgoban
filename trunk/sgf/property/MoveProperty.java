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
package sgf.property;

import go.GoColor;
import go.GoMove;
import sgf.value.ValueMove;
import sgf.value.ValueNone;
import sgf.value.ValueNumber;
import sgf.value.ValueReal;

public class MoveProperty {

    private GoMove move;    // B, W

    private MoveAnnotation annotation;
    private ValueReal blackTimeLeft;
    private ValueNumber blackStones;
    private ValueReal whiteTimeLeft;
    private ValueNumber whiteStones;
    private ValueNone ko;
    private ValueNumber moveNumber;
    /* TODO
     * 着手番号。(初手からの連番。MN プロパティーがある場合は、MN の値で上書きされる)
     *
     * ここで持つことが適当だとは思わないが、他にいいところも思いつかない。
     * 初期値はゼロ。
     */
    private int num;

    public MoveProperty() {
        init();
    }

    public MoveProperty(GoMove m) {
        init();

        move = m;
    }

    private void init() {
        move = null;
        annotation = MoveAnnotation.UNKNOWN;

        blackTimeLeft = null;
        blackStones = null;
        whiteTimeLeft = null;
        whiteStones = null;

        ko = null;
        moveNumber = null;

        num = 0;
    }

    public GoMove getMove() {
        return move;
    }

    public int getNumber() {
        return num;
    }

    public void setNumber(int n) {
        num = n;
    }

    private boolean hasValue(Object obj) {
        return (obj != null) ? true : false;
    }

    public boolean hasAnnotation() {
        return annotation != MoveAnnotation.UNKNOWN;
    }

    public boolean hasTimeLeft(GoColor color) {
        if (color == GoColor.BLACK) {
            return hasValue(blackTimeLeft);
        } else {
            return hasValue(whiteTimeLeft);
        }
    }

    public boolean hasStones(GoColor color) {
        if (color == GoColor.BLACK) {
            return hasValue(blackStones);
        } else {
            return hasValue(whiteStones);
        }
    }

    public boolean hasTimingProperty(GoColor color) {
        if (hasTimeLeft(color) || hasStones(color)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean hasKo() {
        return hasValue(ko);
    }

    public boolean hasMoveNumber() {
        return hasValue(moveNumber);
    }

    public void removeProperty(PropertyId id){
        switch(id){
        case B:
        case W:
            System.err.println("err:MoveProperty.removeProperty: can not remove B and W:" + id);
            break;
        case BL:
            blackTimeLeft = null;
            break;
        case WL:
            whiteTimeLeft = null;
            break;
        case OB:
            blackStones = null;
            break;
        case OW:
            whiteStones = null;
            break;
        case BM:
        case DO:
        case IT:
        case TE:
            if(annotation.equals(id)){
                annotation = MoveAnnotation.UNKNOWN;
            }
            break;
        case KO:
            ko = null;
            break;
        case MN:
            moveNumber = null;
            break;
        default:
            System.err.println("err:MoveProperty.removeProperty: id is not MoveProperty:" + id);
            break;   
        }
    }
    
    public MoveAnnotation getAnnotation() {
        return annotation;
    }

    public void setAnnotation(MoveAnnotation annotation) {
        if(annotation == null){
            this.annotation = MoveAnnotation.UNKNOWN;
        }else{
            this.annotation = annotation;
        }   
    }

    public double getTimeLeft(GoColor color) {
        ValueReal v;
        if (color == GoColor.BLACK) {
            v = blackTimeLeft;
        } else {
            v = whiteTimeLeft;
        }

        if (v != null) {
            return v.getReal();
        } else {
            return 0.0;
        }
    }

    public void setTimeLeft(GoColor color, double sec) {
        if (sec < 0) {
            if (color == GoColor.BLACK) {
                blackTimeLeft = null;
            } else {
                whiteTimeLeft = null;
            }
            return;
        }

        if (color == GoColor.BLACK) {
            if (hasTimeLeft(color) == false) {
                blackTimeLeft = new ValueReal();
            }
            blackTimeLeft.setReal(sec);
        } else {
            if (hasTimeLeft(color) == false) {
                whiteTimeLeft = new ValueReal();
            }
            whiteTimeLeft.setReal(sec);
        }
    }

    public int getStones(GoColor color) {
        ValueNumber v;
        if (color == GoColor.BLACK) {
            v = blackStones;
        } else {
            v = whiteStones;
        }

        if (v != null) {
            return v.getNumber();
        } else {
            return -1;
        }
    }

    public void setStones(GoColor color, int n) {
        if (n < 0) {
            if (color == GoColor.BLACK) {
                blackStones = null;
            } else {
                whiteStones = null;
            }
            return;
        }

        if (color == GoColor.BLACK) {
            if (hasStones(color) == false) {
                blackStones = new ValueNumber();
            }
            blackStones.setNumber(n);
        } else {
            if (hasStones(color) == false) {
                whiteStones = new ValueNumber();
            }
            whiteStones.setNumber(n);
        }
    }

    public boolean isKo() {
        return hasKo();
    }

    public void setKo(boolean bool) {
        if (bool) {
            ko = new ValueNone();
        }else{
            ko = null;
        }
    }

    public int getMoveNumber(){
        if(hasMoveNumber()){
            return moveNumber.getNumber();
        }else{
            return -1;
        }
    }
    
    public void setMoveNumber(int n){
        if(n < 0){
            moveNumber = null;
            return;
        }
        
        if(moveNumber == null){
            moveNumber = new ValueNumber();
        }
        moveNumber.setNumber(n);
        
        num = n;
    }
    
    public void setProperty(Property p) {
        PropertyId id = p.getId();

        if (id == PropertyId.B) {
            ValueMove m = (ValueMove) p.getValue();
            move = new GoMove(GoColor.BLACK, m.getSgfString());

        } else if (id == PropertyId.W) {
            ValueMove m = (ValueMove) p.getValue();
            move = new GoMove(GoColor.WHITE, m.getSgfString());

        } else if (id == PropertyId.BM || id == PropertyId.DO || id == PropertyId.IT || id == PropertyId.TE) {
            annotation = MoveAnnotation.propertyToAnnotation(p);

        } else if (id == PropertyId.BL) {
            blackTimeLeft = (ValueReal) p.getValue();

        } else if (id == PropertyId.OB) {
            blackStones = (ValueNumber) p.getValue();

        } else if (id == PropertyId.WL) {
            whiteTimeLeft = (ValueReal) p.getValue();

        } else if (id == PropertyId.OW) {
            whiteStones = (ValueNumber) p.getValue();

        } else if (id == PropertyId.KO) {
            ko = (ValueNone) p.getValue();

        } else if (id == PropertyId.MN) {
            moveNumber = (ValueNumber) p.getValue();
            num = moveNumber.getNumber();

        } else {
            System.err.println("err:MoveProperty: ??:" + p);
        }
    }

    public String toSgfString() {
        StringBuilder str = new StringBuilder();

        str.append(move.toSgfString());


        if (hasAnnotation()) {
            str.append(annotation.getProperty().toSgfString());
        }

        if (blackTimeLeft != null) {
            str.append(new Property(PropertyId.BL, blackTimeLeft).toSgfString());
        }

        if (blackStones != null) {
            str.append(new Property(PropertyId.OB, blackStones).toSgfString());
        }

        if (whiteTimeLeft != null) {
            str.append(new Property(PropertyId.WL, whiteTimeLeft).toSgfString());
        }

        if (whiteStones != null) {
            str.append(new Property(PropertyId.OW, whiteStones).toSgfString());
        }

        if (hasKo()) {
            str.append(new Property(PropertyId.KO, ko).toSgfString());
        }

        if (hasMoveNumber()) {
            str.append(new Property(PropertyId.MN, moveNumber).toSgfString());
        }

        return str.toString();
    }
}