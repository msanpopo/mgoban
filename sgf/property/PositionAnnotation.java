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

import sgf.value.ValueDouble;

public enum PositionAnnotation {

    UNKNOWN("", PropertyId.UNKNOWN),
    EVEN("Even", PropertyId.DM),
    VERY_EVEN("Very even", PropertyId.DM),
    GOOD_FOR_BLACK("Good for black", PropertyId.GB),
    VERY_GOOD_FOR_BLACK("Very good for black", PropertyId.GB),
    GOOD_FOR_WHITE("Good for white", PropertyId.GW),
    VERY_GOOD_FOR_WHITE("Very good for white", PropertyId.GW),
    UNCLEAR("Unclear", PropertyId.UC),
    VERY_UNCLEAR("Very unclear", PropertyId.UC);
    private String message;
    private PropertyId id;

    private PositionAnnotation(String message, PropertyId id) {
        this.message = message;
        this.id = id;
    }

    public static PositionAnnotation propertyToAnnotation(Property p) {
        PropertyId pid = p.getId();
        switch (pid) {
            case DM:
                {
                    ValueDouble vd = (ValueDouble) p.getValue();
                    if (vd.isNormal()) {
                        return EVEN;
                    } else {
                        return VERY_EVEN;
                    }
                }
            case GB:
                {
                    ValueDouble vd = (ValueDouble) p.getValue();
                    if (vd.isNormal()) {
                        return GOOD_FOR_BLACK;
                    } else {
                        return VERY_GOOD_FOR_BLACK;
                    }
                }
            case GW:
                {
                    ValueDouble vd = (ValueDouble) p.getValue();
                    if (vd.isNormal()) {
                        return GOOD_FOR_WHITE;
                    } else {
                        return VERY_GOOD_FOR_WHITE;
                    }
                }
            case UC:
                {
                    ValueDouble vd = (ValueDouble) p.getValue();
                    if (vd.isNormal()) {
                        return UNCLEAR;
                    } else {
                        return VERY_UNCLEAR;
                    }
                }
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
            case EVEN:
            case GOOD_FOR_BLACK:
            case GOOD_FOR_WHITE:
            case UNCLEAR:
                p.setValue(new ValueDouble(ValueDouble.SgfDouble.NORMAL));
                break;
            case VERY_EVEN:
            case VERY_GOOD_FOR_BLACK:
            case VERY_GOOD_FOR_WHITE:
            case VERY_UNCLEAR:
                p.setValue(new ValueDouble(ValueDouble.SgfDouble.EMPHASIZED));
                break;

            default:
                break;
        }

        return p;
    }

    @Override
    public String toString() {
        return message;
    }
}