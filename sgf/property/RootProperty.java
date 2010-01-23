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

import sgf.value.ValueNumber;
import sgf.value.ValueSimpleText;
import sgf.value.ValueSize;

public class RootProperty {
    private ValueSimpleText application;
    private ValueSimpleText charset;
    private ValueNumber fileFormat;
    private ValueNumber game;
    private ValueNumber style;
    private ValueSize boardSize;

    public RootProperty() {
        application = null;
        charset = null;
        fileFormat = null;
        game = null;
        style = null;
        boardSize = null;
    }

    public RootProperty(int size) {
        application = new ValueSimpleText("mGo:?");
        charset = new ValueSimpleText("UTF-8");
        fileFormat = new ValueNumber(4);
        game = new ValueNumber(1);
        //style = new ValueNumber(0);
        boardSize = new ValueSize(size);
    }

    private boolean hasValue(Object obj){
        return (obj != null) ? true : false;
    }
    
    public boolean hasProperty(PropertyId id) {
        switch (id) {
            case AP:
                return hasApplication();
            case CA:
                return hasCharset();
            case FF:
                return hasFileFormat();
            case GM:
                return hasGame();
            case ST:
                return hasStyle();
            case SZ:
                return hasBoardSize();
            default:
                System.err.println("error:RootProperty.setProperty:" + id);
                return false;
        }
    }
    
    public void removeProperty(PropertyId id) {
        switch (id) {
            case AP:
                application = null;
                break;
            case CA:
                charset = null;
                break;
            case FF:
                fileFormat = null;
                break;
            case GM:
                game = null;
                break;
            case ST:
                style = null;
                break;
            case SZ:
                boardSize = null;
                break;
            default:
                System.err.println("error:RootProperty.removeProperty:" + id);
                break;
        }
    }
    
    public boolean hasApplication() {
        return hasValue(application) && application.isEmpty() == false;
    }

    public boolean hasCharset() {
        return hasValue(charset) && charset.isEmpty() == false;
    }

    public boolean hasFileFormat() {
        return hasValue(fileFormat);
    }

    public boolean hasGame() {
        return hasValue(game);
    }

    public boolean hasStyle() {
        return hasValue(style);
    }

    public boolean hasBoardSize() {
        return hasValue(boardSize);
    }

    public String getApplication() {
        if (application != null) {
            return application.getText();
        } else {
            return "";
        }
    }

    public String getCharset() {
        if (charset != null) {
            return charset.getText();
        } else {
            return "";
        }
    }

    public void setCharset(String newCharset) {
        if (charset == null) {
            charset = new ValueSimpleText();
        }

        charset.setText(newCharset);
    }

    public int getFileFormat() {
        if (fileFormat != null) {
            return fileFormat.getNumber();
        } else {
            return -1;
        }
    }

    public int getGame() {
        if (game != null) {
            return game.getNumber();
        } else {
            return -1;
        }
    }

    public int getStyle() {
        if (style != null) {
            return style.getNumber();
        } else {
            return -1;
        }
    }

    public void setStyle(int n){
        if(n < 0 || n > 3){
            System.err.println("err:RootProperty: style :" + n);
            style = null;
            return;
        }
        
        if(style == null){
            style = new ValueNumber();
        }
        
        style.setNumber(n);
    }
    
    public int getBoardSize() {
        if (boardSize != null) {
            return boardSize.getSize();
        } else {
            return 19;
        }
    }

    // sgf ファイルからの組み立て用。

    public void setProperty(Property p) {
        switch (p.getId()) {
            case AP:
                application = (ValueSimpleText) p.getValue();
                break;
            case CA:
                charset = (ValueSimpleText) p.getValue();
                break;
            case FF:
                fileFormat = (ValueNumber) p.getValue();
                break;
            case GM:
                game = (ValueNumber) p.getValue();
                break;
            case ST:
                style = (ValueNumber) p.getValue();
                break;
            case SZ:
                boardSize = (ValueSize) p.getValue();
                break;
            default:
                System.err.println("error:RootProperty.setProperty:" + p.toSgfString());
                break;
        }
    }

    public String toSgfString() {
        StringBuilder str = new StringBuilder();

        if (hasApplication()) {
            str.append(new Property(PropertyId.AP, application).toSgfString());
        }
        if (hasCharset()) {
            str.append(new Property(PropertyId.CA, charset).toSgfString());
        }
        if (hasFileFormat()) {
            str.append(new Property(PropertyId.FF, fileFormat).toSgfString());
        }
        if (hasGame()) {
            str.append(new Property(PropertyId.GM, game).toSgfString());
        }
        if (hasStyle()) {
            str.append(new Property(PropertyId.ST, style).toSgfString());
        }
        if (hasBoardSize()) {
            str.append(new Property(PropertyId.SZ, boardSize).toSgfString());
        }

        return str.toString();
    }
}