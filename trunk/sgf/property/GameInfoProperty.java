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

import go.GoColor;
import java.util.ArrayList;
import sgf.value.Value;
import sgf.value.ValueNumber;
import sgf.value.ValueReal;
import sgf.value.ValueSimpleText;
import sgf.value.ValueText;

public class GameInfoProperty {

    private ArrayList<Property> propList;
    private ValueNumber handicap;
    private ValueReal komi;
    private ValueSimpleText blackName;
    private ValueSimpleText whiteName;
    private ValueSimpleText blackRank;
    private ValueSimpleText whiteRank;

    public GameInfoProperty() {
        propList = new ArrayList<Property>();

        handicap = null;
        komi = null;

        blackName = null;
        whiteName = null;

        blackRank = null;
        whiteRank = null;
    }

    public int getHandicap() {
        if (handicap == null) {
            return 0;
        } else {
            return handicap.getNumber();
        }
    }

    public void setHandicap(int newHandicap) {
        if (newHandicap == 0) {
            handicap = null;
        } else {
            handicap = new ValueNumber(newHandicap);
        }
    }

    public double getKomi() {
        if (komi == null) {
            return 0.0;
        } else {
            return komi.getReal();
        }
    }

    public void setKomi(double newKomi) {
        komi = new ValueReal(newKomi);
    }

    private String getText(ValueSimpleText simpleText) {
        if (simpleText == null) {
            return "";
        } else {
            return simpleText.getText();
        }
    }

    public String getPlayerName(GoColor color) {
        if (color == GoColor.BLACK) {
            return getText(blackName);
        } else {
            return getText(whiteName);
        }
    }

    public void setPlayerName(GoColor color, String name) {
        ValueSimpleText tmp;
        if (name == null || name.isEmpty()) {
            tmp = null;
        } else {
            tmp = new ValueSimpleText(name);
        }

        if (color == GoColor.BLACK) {
            blackName = tmp;
        } else {
            whiteName = tmp;
        }
    }

    public String getPlayerRank(GoColor color) {
        if (color == GoColor.BLACK) {
            return getText(blackRank);
        } else {
            return getText(whiteRank);
        }
    }

    public void setPlayerRank(GoColor color, String rank) {
        ValueSimpleText tmp;
        if (rank == null || rank.isEmpty()) {
            tmp = null;
        } else {
            tmp = new ValueSimpleText(rank);
        }

        if (color == GoColor.BLACK) {
            blackRank = tmp;
        } else {
            whiteRank = tmp;
        }
    }

    private boolean hasValue(Object obj) {
        return (obj != null) ? true : false;
    }

    public boolean hasProperty(PropertyId id) {
        switch (id) {
        case HA:
            return hasValue(handicap);
        case KM:
            return hasValue(komi);
        case PB:
            return hasValue(blackName);
        case BR:
            return hasValue(blackRank);
        case PW:
            return hasValue(whiteName);
        case WR:
            return hasValue(whiteRank);
        default:
            Property p = findProperty(id);
            return hasValue(p);
        }
    }

    public void removeProperty(PropertyId id) {
        switch (id) {
        case HA:
            handicap = null;
            break;
        case KM:
            komi = null;
            break;
        case PB:
            blackName = null;
            break;
        case BR:
            blackRank = null;
            break;
        case PW:
            whiteName = null;
            break;
        case WR:
            whiteRank = null;
            break;
        default:
            Property p = findProperty(id);
            if (p != null) {
                propList.remove(p);
            }
            break;
        }
    }

    public String getString(PropertyId id) {
        String str = "";

        switch (id) {
        case HA:
            if (handicap != null) {
                str = Integer.toString(getHandicap());
            }
            break;
        case KM:
            if (komi != null) {
                str = Double.toString(getKomi());
            }
            break;
        case TM:
            {
                Property p = findProperty(id);
                if (p != null) {
                    ValueReal v = (ValueReal) p.getValue();
                    str = Double.toString(v.getReal());
                }
            }
            break;
        case PB:
            str = getPlayerName(GoColor.BLACK);
            break;
        case BR:
            str = getPlayerRank(GoColor.BLACK);
            break;
        case PW:
            str = getPlayerName(GoColor.WHITE);
            break;
        case WR:
            str = getPlayerRank(GoColor.WHITE);
            break;
        case GC:
            {
                Property p = findProperty(id);
                if (p != null) {
                    ValueText v = (ValueText) p.getValue();
                    str = v.getText();
                }
            }
            break;
        default:
            {
                Property p = findProperty(id);
                if (p != null) {
                    ValueSimpleText v = (ValueSimpleText) p.getValue();
                    str = v.getText();
                }
            }
            break;
        }
        return str;
    }

    public void setString(PropertyId id, String str) {
        if (str == null || str.isEmpty()) {
            removeProperty(id);
            return;
        }

        switch (id) {
        case HA:
            int h = Integer.parseInt(str);
            setHandicap(h);
            break;
        case KM:
            double k = Double.parseDouble(str);
            setKomi(k);
            break;
        case TM:
            {
                double t = Double.parseDouble(str);
                Property p = findProperty(id);
                ValueReal v;
                if (p == null) {
                    v = new ValueReal(t);
                    p = new Property(id, v);
                    propList.add(p);
                } else {
                    v = (ValueReal) p.getValue();
                    v.setReal(t);
                }
            }
            break;
        case PB:
            setPlayerName(GoColor.BLACK, str);
            break;
        case BR:
            setPlayerRank(GoColor.BLACK, str);
            break;
        case PW:
            setPlayerName(GoColor.WHITE, str);
            break;
        case WR:
            setPlayerRank(GoColor.WHITE, str);
            break;
        case GC:
            {
                Property p = findProperty(id);
                ValueText v;
                if (p == null) {
                    v = new ValueText(str);
                    p = new Property(id, v);
                    propList.add(p);
                } else {
                    v = (ValueText) p.getValue();
                    v.setText(str);
                }
            }
            break;
        default:
            {
                Property p = findProperty(id);
                ValueSimpleText v;
                if (p == null) {
                    v = new ValueSimpleText(str);
                    p = new Property(id, v);
                    propList.add(p);
                } else {
                    v = (ValueSimpleText) p.getValue();
                    v.setText(str);
                }
            }
            break;
        }
    }

    private Property findProperty(PropertyId id) {
        for (Property p : propList) {
            if (p.getId() == id) {
                return p;
            }
        }

        return null;
    }

    public void setProperty(Property p) {
        if (p.getId() == PropertyId.HA) {
            handicap = (ValueNumber) p.getValue();
        } else if (p.getId() == PropertyId.KM) {
            komi = (ValueReal) p.getValue();
        } else if (p.getId() == PropertyId.PB) {
            blackName = (ValueSimpleText) p.getValue();
        } else if (p.getId() == PropertyId.PW) {
            whiteName = (ValueSimpleText) p.getValue();
        } else if (p.getId() == PropertyId.BR) {
            blackRank = (ValueSimpleText) p.getValue();
        } else if (p.getId() == PropertyId.WR) {
            whiteRank = (ValueSimpleText) p.getValue();
        } else {
            propList.add(p);
        }
    }

    private void append(StringBuilder str, Value value, PropertyId id) {
        if (value != null) {
            str.append(new Property(id, value).toSgfString());
        }
    }

    public String toSgfString() {
        StringBuilder str = new StringBuilder();

        append(str, handicap, PropertyId.HA);
        append(str, komi, PropertyId.KM);
        append(str, blackName, PropertyId.PB);
        append(str, blackRank, PropertyId.BR);
        append(str, whiteName, PropertyId.PW);
        append(str, whiteRank, PropertyId.WR);

        for (Property p : propList) {
            str.append(p.toSgfString());
        }

        return str.toString();
    }
}