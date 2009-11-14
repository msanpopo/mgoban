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

package sgf.value;

import go.GoVertex;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValuePointList implements Value{
    private static final Pattern p = Pattern.compile("([a-zA-Z][a-zA-Z]):([a-zA-Z][a-zA-Z])");
        
    private ArrayList<GoVertex> vertexList;

    public ValuePointList() {
        vertexList = new ArrayList<GoVertex>();
    }

    public void addPoint(GoVertex v){
        if(hasPoint(v) == false){
            vertexList.add(v);
        }
    }
    
    public void removePoint(GoVertex v){
        GoVertex vRemove = null;
        for(GoVertex vTmp : vertexList){
            if(v.equals(vTmp)){
                vRemove = vTmp;
                break;
            }
        }
        if(vRemove != null){
            vertexList.remove(vRemove);
        }
    }
    
    public boolean isEmpty(){
        return vertexList.isEmpty();
    }
    
    public boolean hasPoint(GoVertex v){
        for(GoVertex vTmp : vertexList){
            if(v.equals(vTmp)){
                return true;
            }
        }
        return false;
    }

    public void setSgfString(String str) {
        Matcher m = p.matcher(str);
        if(m.matches()){
            GoVertex v0 = new GoVertex(m.group(1));
            GoVertex v1 = new GoVertex(m.group(2));
            int x0 = v0.getX();
            int y0 = v0.getY();
            int x1 = v1.getX();
            int y1 = v1.getY();
            
            for(int i = x0; i <= x1; ++i){
                for(int j = y0; j <= y1; ++j){
                    GoVertex v = new GoVertex(i, j);
                    addPoint(v);
                }
            }
        }else{
            GoVertex v = new GoVertex(str);
            addPoint(v);
        }
    }

    public String getSgfString(){
        // TODO
        // 返すのに適当なものがない。どうしよう？
        return null;
    }
    
    public String getValueString() {
        StringBuilder str = new StringBuilder();
        
        for(GoVertex v : vertexList){
            str.append("[");
            str.append(v.toSgfString());
            str.append("]");
        }
        
        return str.toString();
    }
    
    public Collection<GoVertex> getCollection(){
        return vertexList;
    }
}