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
package sgf.value;

import go.GoVertex;
import java.util.ArrayList;
import java.util.Collection;

public class ValueLabelList implements Value {

    private ArrayList<ValueLabel> labelList;

    public ValueLabelList() {
        labelList = new ArrayList<ValueLabel>();
    }

    public boolean isEmpty() {
        return labelList.isEmpty();
    }

    public Collection<ValueLabel> getCollection() {
        return labelList;
    }

    // 追加しようとした位置にすでにラベルがあれば、置き換える。
    public void addLabel(GoVertex v, String s) {
        if(hasLabel(v)){
            ValueLabel old = findLabel(v);
            labelList.remove(old);
        }
        ValueLabel vl = new ValueLabel(v, s);

        labelList.add(vl);
    }

    public boolean hasLabel(GoVertex v) {
        for (ValueLabel vl : labelList) {
            if (vl.getVertex().equals(v)) {
                return true;
            }
        }
        return false;
    }

    public ValueLabel findLabel(GoVertex v) {
        for (ValueLabel vl : labelList) {
            if (vl.getVertex().equals(v)) {
                return vl;
            }
        }
        return null;
    }

    public void removeLabel(GoVertex v) {
        ValueLabel labelRemove = findLabel(v);

        if (labelRemove != null) {
            labelList.remove(labelRemove);
        }
    }

    // 追加しようとした位置にすでにラベルがあれば、置き換える。
    public void setSgfString(String str) {
        ValueLabel vl = new ValueLabel(str);
        GoVertex v = vl.getVertex();
        if(hasLabel(v)){
            ValueLabel old = findLabel(v);
            labelList.remove(old);
        }
        labelList.add(vl);
    }

    public String getSgfString() {
        // TODO 返すのに適当なものがない。どうしよう？
        return null;
    }

    public String getValueString() {
        StringBuilder str = new StringBuilder();

        for (ValueLabel sl : labelList) {
            str.append(sl.getValueString());
        }

        return str.toString();
    }
}