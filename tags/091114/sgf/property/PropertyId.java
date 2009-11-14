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

import sgf.value.Value;
import sgf.value.ValueColor;
import sgf.value.ValueDouble;
import sgf.value.ValueLabelList;
import sgf.value.ValueMove;
import sgf.value.ValueNone;
import sgf.value.ValueNumber;
import sgf.value.ValuePointList;
import sgf.value.ValueReal;
import sgf.value.ValueSimpleText;
import sgf.value.ValueSize;
import sgf.value.ValueText;
import sgf.value.ValueUnknown;

public enum PropertyId {
    UNKNOWN("", PropertyType.UNKNOWN, false, ValueUnknown.class),
    B("B", PropertyType.MOVE, false, ValueMove.class),
    BL("BL", PropertyType.MOVE, false, ValueReal.class),
    BM("BM", PropertyType.MOVE, false, ValueDouble.class),
    DO("DO", PropertyType.MOVE, false, ValueNone.class),
    IT("IT", PropertyType.MOVE, false, ValueNone.class),
    KO("KO", PropertyType.MOVE, false, ValueNone.class),
    MN("MN", PropertyType.MOVE, false, ValueNumber.class),
    OB("OB", PropertyType.MOVE, false, ValueNumber.class),
    OW("OW", PropertyType.MOVE, false, ValueNumber.class),
    TE("TE", PropertyType.MOVE, false, ValueDouble.class),
    W("W", PropertyType.MOVE, false, ValueMove.class),
    WL("WL", PropertyType.MOVE, false, ValueReal.class),
    
    AB("AB", PropertyType.SETUP, false, ValuePointList.class),
    AE("AE", PropertyType.SETUP, false, ValuePointList.class),
    AW("AW", PropertyType.SETUP, false, ValuePointList.class),
    PL("PL", PropertyType.SETUP, false, ValueColor.class),
    
    AR("AR", PropertyType.NO_TYPE, false, ValueUnknown.class),
    C("C", PropertyType.NO_TYPE, false, ValueText.class),
    CR("CR", PropertyType.NO_TYPE, false, ValuePointList.class),
    DD("DD", PropertyType.NO_TYPE, true, ValueUnknown.class),
    DM("DM", PropertyType.NO_TYPE, false, ValueDouble.class),
    FG("FG", PropertyType.NO_TYPE, false, ValueUnknown.class),
    GB("GB", PropertyType.NO_TYPE, false, ValueDouble.class),
    GW("GW", PropertyType.NO_TYPE, false, ValueDouble.class),
    HO("HO", PropertyType.NO_TYPE, false, ValueDouble.class),
    LB("LB", PropertyType.NO_TYPE, false, ValueLabelList.class),
    LN("LN", PropertyType.NO_TYPE, false, ValueUnknown.class),
    MA("MA", PropertyType.NO_TYPE, false, ValuePointList.class),
    N("N", PropertyType.NO_TYPE, false, ValueSimpleText.class),
    PM("PM", PropertyType.NO_TYPE, true, ValueNumber.class),
    SL("SL", PropertyType.NO_TYPE, false, ValuePointList.class),
    SQ("SQ", PropertyType.NO_TYPE, false, ValuePointList.class),
    TR("TR", PropertyType.NO_TYPE, false, ValuePointList.class),
    UC("UC", PropertyType.NO_TYPE, false, ValueDouble.class),
    V("V", PropertyType.NO_TYPE, false, ValueReal.class),
    VM("VM", PropertyType.NO_TYPE, true, ValueUnknown.class),
    
    AP("AP", PropertyType.ROOT, false, ValueSimpleText.class),      // ちょっと手抜き
    CA("CA", PropertyType.ROOT, false, ValueSimpleText.class),
    FF("FF", PropertyType.ROOT, false, ValueNumber.class),
    GM("GM", PropertyType.ROOT, false, ValueNumber.class),
    ST("ST", PropertyType.ROOT, false, ValueNumber.class),
    SZ("SZ", PropertyType.ROOT, false, ValueSize.class),
    
    AN("AN", PropertyType.GAME_INFO, false, ValueSimpleText.class),
    BR("BR", PropertyType.GAME_INFO, false, ValueSimpleText.class),
    BT("BT", PropertyType.GAME_INFO, false, ValueSimpleText.class),
    CP("CP", PropertyType.GAME_INFO, false, ValueSimpleText.class),
    DT("DT", PropertyType.GAME_INFO, false, ValueSimpleText.class),
    EV("EV", PropertyType.GAME_INFO, false, ValueSimpleText.class),
    GC("GC", PropertyType.GAME_INFO, false, ValueText.class),
    GN("GN", PropertyType.GAME_INFO, false, ValueSimpleText.class),
    ON("ON", PropertyType.GAME_INFO, false, ValueSimpleText.class),
    OT("OT", PropertyType.GAME_INFO, false, ValueSimpleText.class),
    PB("PB", PropertyType.GAME_INFO, false, ValueSimpleText.class),
    PC("PC", PropertyType.GAME_INFO, false, ValueSimpleText.class),
    PW("PW", PropertyType.GAME_INFO, false, ValueSimpleText.class),
    RE("RE", PropertyType.GAME_INFO, false, ValueSimpleText.class),
    RO("RO", PropertyType.GAME_INFO, false, ValueSimpleText.class),
    RU("RU", PropertyType.GAME_INFO, false, ValueSimpleText.class),
    SO("SO", PropertyType.GAME_INFO, false, ValueSimpleText.class),
    TM("TM", PropertyType.GAME_INFO, false, ValueReal.class),
    US("US", PropertyType.GAME_INFO, false, ValueSimpleText.class),
    WR("WR", PropertyType.GAME_INFO, false, ValueSimpleText.class),
    WT("WT", PropertyType.GAME_INFO, false, ValueSimpleText.class),
    
    TB("TB", PropertyType.NO_TYPE, false, ValueUnknown.class),
    TW("TW", PropertyType.NO_TYPE, false, ValueUnknown.class),
    
    HA("HA", PropertyType.GAME_INFO, false, ValueNumber.class),
    KM("KM", PropertyType.GAME_INFO, false, ValueReal.class);
    
    private final String ident;
    private final PropertyType propType;
    private final boolean inherit;
    private final Class valueClass;
    
    private PropertyId(String ident, PropertyType propType, boolean inherit, Class valueClass){
        this.ident = ident;
        this.propType = propType;
        this.inherit = inherit;
        this.valueClass = valueClass;
    }
    
    public static PropertyId get(String str){
        for(PropertyId id : PropertyId.values()){
            if(str.equals(id.ident)){
                return id;
            }
        }
        return UNKNOWN;
    }
    
    public String getIdent(){
        return ident;
    }
    
    public PropertyType getPropertyType(){
        return propType;
    }
    
    public boolean isInherit(){
        return inherit;
    }
    
    public Value newValueInstance(){
        Value v = null;
        
        try {
            v = (Value) valueClass.newInstance();
        } catch (IllegalAccessException ex) {
            System.err.println("error:PropertyId.newValueInstance:" + ex);
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            System.err.println("error:PropertyId.newValueInstance:" + ex);
            ex.printStackTrace();
        }
        return v;
    }
    
    public Class getValueClass(){
        return valueClass;
    }
}
        /*
ID   Description     property type    property value
---- --------------- ---------------  --------------------------------------
B    Black           move             move
BL   Black time left move             real
BM   Bad move        move             double
DO   Doubtful        move             none
IT   Interesting     move             none
KO   Ko              move             none
MN   set MoveNumber  move             number
OB   OtStones Black  move             number
OW   OtStones White  move             number
TE   Tesuji          move             double
W    White           move             move
WL   White time left move             real
         
AB   Add Black       setup            list of stone
AE   Add Empty       setup            list of point
AW   Add White       setup            list of stone
PL   Player to play  setup            color
         
         *AR  Arrow           -                list of composed point ':' point
C    Comment         -                text
CR   Circle          -                list of point
         *DD  Dim points      - (inherit)      elist of point
DM   Even position   -                double
!FG  Figure          -                none | composed number ":" simpletext
GB   Good for Black  -                double
GW   Good for White  -                double
HO   Hotspot         -                double
!LB  Label           -                list of composed point ':' simpletext
         *LN  Line            -                list of composed point ':' point
MA   Mark            -                list of point
N    Nodename        -                simpletext
         *PM  Print move mode - (inherit)      number
SL   Selected        -                list of point
         *SQ  Square          -                list of point
TR   Triangle        -                list of point
UC   Unclear pos     -                double
V    Value           -                real
         *VW  View            - (inherit)      elist of point
         
         *AP  Application     root	      composed simpletext ':' number
         *CA  Charset         root	      simpletext
FF   Fileformat      root	      number (range: 1-4)
GM   Game            root	      number (range: 1-5,7-17)
         *ST  Style           root	      number (range: 0-3)
!SZ  Size            root	      (number | composed number ':' number)
         
AN   Annotation      game-info        simpletext
BR   Black rank      game-info        simpletext
BT   Black team      game-info        simpletext
CP   Copyright       game-info        simpletext
!DT  Date            game-info        simpletext
EV   Event           game-info        simpletext
GC   Game comment    game-info        text
GN   Game name       game-info        simpletext
ON   Opening         game-info        simpletext
         *OT  Overtime        game-info        simpletext
PB   Player Black    game-info        simpletext
PC   Place           game-info        simpletext
PW   Player White    game-info        simpletext
!RE  Result          game-info        simpletext
RO   Round           game-info        simpletext
!RU  Rules           game-info        simpletext
SO   Source          game-info        simpletext
TM   Timelimit       game-info        real
US   User            game-info        simpletext
WR   White rank      game-info        simpletext
WT   White team      game-info        simpletext
         
         
Go (GM[1]) specific properties
         
ID   Description     property type    property value
---- --------------- ---------------  --------------------------------------
TB   Territory Black -                elist of point
TW   Territory White -                elist of point
         
HA   Handicap        game-info        number
KM   Komi            game-info        real
         */