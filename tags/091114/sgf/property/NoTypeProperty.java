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

import go.board.gui.MarkType;
import go.GoVertex;
import java.util.ArrayList;
import java.util.Collection;
import sgf.value.ValueDouble;
import sgf.value.ValueLabel;
import sgf.value.ValueLabelList;
import sgf.value.ValuePointList;
import sgf.value.ValueReal;
import sgf.value.ValueSimpleText;
import sgf.value.ValueText;

public class NoTypeProperty {
    private ValueText comment;
    private ValueSimpleText nodeName;
    
    private PositionAnnotation annotation;
    private ValueDouble hotspot;
    private ValueReal value;
    
    private ValuePointList[] mark;
    
    private ValueLabelList label;
    
    private ArrayList<Property> propList;
    
    private ArrayList<GoVertex> dummyVertexList;
    private ArrayList<ValueLabel> dummyLabelList;
    
    public NoTypeProperty() {
        comment = null;
        nodeName = null;
        annotation = null;
        hotspot = null;
        value = null;
        
        mark = new ValuePointList[5];
        for(int i = 0; i < 5; ++i){
            mark[i] = null;
        }
        
        label = null;
        
        propList = new ArrayList<Property>();
        
        dummyVertexList = new ArrayList<GoVertex>();
        dummyLabelList = new ArrayList<ValueLabel>();
    }
    
    public boolean hasComment(){
        return comment != null ? true : false;
    }
    
    public boolean hasNodeName(){
        return nodeName != null ? true : false;
    }
    
    public boolean hasAnnotation(){
        if(annotation != null && annotation != PositionAnnotation.UNKNOWN){
            return true;
        }else{
            return false;
        }
    }
    
    public boolean hasHotspot(){
        return hotspot != null ? true : false;
    }
    
    public boolean hasValue(){
        return value != null ? true : false;
    }
    
    public String getComment(){
        if(comment == null){
            return "";
        }else{
            return comment.getText();
        }
    }
    
    public void setComment(String text){
        if(text == null || text.isEmpty()){
            comment = null;
        }else{
            if(comment == null){
                comment = new ValueText();
            }
            comment.setText(text);
        }
    }
    
    public String getNodeName(){
        if(nodeName == null){
            return "";
        }else{
            return nodeName.getText();
        }
    }
    
    public void setNodeName(String text){
        if(text == null || text.isEmpty()){
            nodeName = null;
        }else{
            if(nodeName == null){
                nodeName = new ValueSimpleText();
            }
            nodeName.setText(text);
        }
    }
    
    public PositionAnnotation getAnnotation(){
        if(hasAnnotation()){
            return annotation;
        }else{
            return PositionAnnotation.UNKNOWN;
        }
    } 
    
    public void setAnnotation(PositionAnnotation annotation){
        this.annotation = annotation;
    }
    
    public int getHotspot(){
        if(hasHotspot()){
            if(hotspot.isNormal()){
                return 1;
            }else{
                return 2;
            }
        }else{
            return 0;
        }
    }
    
    public void setHotspot(int n){
        if(n == 0){
            hotspot = null;
        }else if(n == 1){
            hotspot = new ValueDouble(ValueDouble.SgfDouble.NORMAL);
        }else if(n == 2){
            hotspot = new ValueDouble(ValueDouble.SgfDouble.EMPHASIZED);
        }else{
            System.err.println("err:NoTypeProperty:setHotspot:" + n);
        }
    }
    
    public double getValue(){
        if(hasValue()){
            return value.getReal();
        }else{
            return 0.0;
        }
    }
    
    public void setValue(double v){
        // TODO さすがにマズすぎる
        if(v < -1000.0 || v > 1000.0){
            value = null;
            return;
        }
        
        if(hasValue() == false){
            value = new ValueReal();
        }
        value.setReal(v);
    }
    
    public Collection<GoVertex> getMarkCollection(MarkType type) {
        ValuePointList vpl = mark[type.getN()];
        if(vpl == null){
            return dummyVertexList;
        }else{
            return vpl.getCollection();
        }
    }
    
    public Collection<ValueLabel> getLabelCollection(){
        if(label == null){
            return dummyLabelList;
        }else{
            return label.getCollection();
        }
    }
    
    public void addMark(GoVertex v, MarkType type){
        ValuePointList vplOld = findMarkList(v);
        if(vplOld != null){
            removeMark(v);
        }
        
        if(mark[type.getN()] == null){
            mark[type.getN()] = new ValuePointList();
        }
        mark[type.getN()].addPoint(v);
    }
    
    public void removeMark(GoVertex v){
        ValuePointList vpl = findMarkList(v);
        if(vpl == null){
            // v にマークはない
            System.err.println("error:NoTypeProperty.removeMark: mark not found :" + v.toSgfString());
        }else{
            vpl.removePoint(v);
        }
    }
    
    public void addLabel(GoVertex v, String s){
        if(label == null){
            label = new ValueLabelList();
        }
        label.addLabel(v, s);
    }
    
    public void removeLabel(GoVertex v){
        label.removeLabel(v);
    }
    
    private ValuePointList findMarkList(GoVertex v){
        for(MarkType type : MarkType.values()){
            ValuePointList vpl = mark[type.getN()];
            if(vpl == null){
                continue;
            }
            if(vpl.hasPoint(v)){
                return vpl;
            }
        }
        return null;
    }
    
    public void setProperty(Property p){
        PropertyId id = p.getId();
        if(id == PropertyId.C){
            comment = (ValueText)p.getValue();
        }else if(id == PropertyId.N){
            nodeName = (ValueSimpleText)p.getValue();
            
        }else if(id == PropertyId.DM || id == PropertyId.GB || id == PropertyId.GW || id == PropertyId.UC){
            annotation = PositionAnnotation.propertyToAnnotation(p);
            
        }else if(id == PropertyId.CR){
            mark[MarkType.CIRCLE.getN()] = (ValuePointList)p.getValue();
            
        }else if(id == PropertyId.TR){
            mark[MarkType.TRIANGLE.getN()] = (ValuePointList)p.getValue();
            
        }else if(id == PropertyId.SQ){
            mark[MarkType.SQUARE.getN()] = (ValuePointList)p.getValue();
            
        }else if(id == PropertyId.MA){
            mark[MarkType.MARK.getN()] = (ValuePointList)p.getValue();
            
        }else if(id == PropertyId.SL){
            mark[MarkType.SELECTED.getN()] = (ValuePointList)p.getValue();
            
        }else if(id == PropertyId.LB){
            label = (ValueLabelList)p.getValue();
            
        }else{
            propList.add(p);
        }
    }
    
    public String toSgfString(){
        StringBuilder str = new StringBuilder();
        
        if(comment != null && comment.isEmpty() == false){
            Property p = new Property(PropertyId.C, comment);
            str.append(p.toSgfString());
        }
        
        if(nodeName != null && nodeName.isEmpty() == false){
            Property p = new Property(PropertyId.N, nodeName);
            str.append(p.toSgfString());
        }
        
        for(MarkType type : MarkType.values()){
            ValuePointList vpl = mark[type.getN()];
            if(vpl != null && vpl.isEmpty() == false){
                str.append(new Property(type.getPropertyId(), vpl).toSgfString());
            }
        }
        
        if(label != null && label.isEmpty() == false){
            str.append(new Property(PropertyId.LB, label).toSgfString());
        }
        
        for(Property p : propList){
            str.append(p.toSgfString());
        }
        
        return str.toString();
    }
}