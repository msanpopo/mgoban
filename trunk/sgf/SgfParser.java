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

package sgf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedList;
import sgf.property.Property;

public class SgfParser {
    private GoNode topNode; // コレクションの一番最初のルートノードのこと
    private Reader reader;
    private String charset;
    
    public SgfParser() {
        topNode = null;
        reader = null;
        charset = "UTF-8";
    }
    
    public SgfParser(String charset){
        this.topNode = null;
        this.reader = null;
        this.charset = charset;
    }
    
    public void setSgfFile(File file){
        File sgfFile = file;
        
        if (!sgfFile.exists()) {
            System.out.println("SgfParser setSgfFile :: not exist");
            return;
        }
        
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(sgfFile), charset));
        } catch (IOException ex) {
            System.out.println("SgfParser setSgfFile :: err:" + sgfFile.getAbsolutePath() + " :: " + ex);
            reader = null;
        }
    }
    
    public void setSgfFile(String filename) {
        File sgfFile = new File(filename);
        
        setSgfFile(sgfFile);
    }

    public void setSgfText(String text){
        reader = new BufferedReader(new StringReader(text));
    }

    class RawProp {
        private StringBuilder idStr;
        private StringBuilder valueStr;
        private ArrayList<String> valueList;
        
        public RawProp(){
            idStr = new StringBuilder();
            valueStr = new StringBuilder();
            valueList = new ArrayList<String>();
        }
        
        public void clear(){
            idStr = new StringBuilder();
            valueStr = new StringBuilder();
            valueList.clear();
        }
        
        public boolean isValidProperty(){
            if(hasId() && hasValue()){
                return true;
            }else{
                return false;
            }
        }
        
        public boolean hasId(){
            if(idStr.length() != 0){
                return true;
            }else{
                return false;
            }
        }
        
        public boolean hasValue(){
            if(valueList.size() != 0){
                return true;
            }else{
                return false;
            }
        }
        
        public void appendId(char c){
            idStr.append(c);
            if(hasValue()){
                valueStr = new StringBuilder();
                valueList.clear();
            }
        }
        
        public void appendValue(char c){
            valueStr.append(c);
        }
        
        public void pushValue(){
            //System.out.println("RawProp.pushValue:" + valueStr.toString());
            valueList.add(valueStr.toString());
            
            valueStr = new StringBuilder();
        }
        
        public Property getProperty(){
            Property p = new Property(idStr.toString(), valueList);
            
            return p;
        }
    }
    
    public void parse() {
        if (reader == null) {
            return;
        }
        
        try {
            int ic;
            
            int STATE_TOP = 0;
            int STATE_VALUE = 2;
            
            int state = STATE_TOP;
            boolean escaped = false;
            
            GoNode current_node = null;
            ArrayList<GoNode> current_tree = null;
            RawProp prop = new RawProp();
            
            LinkedList<ArrayList<GoNode>> stack_tree = new LinkedList<ArrayList<GoNode>>();
            LinkedList<GoNode> stack_node = new LinkedList<GoNode>();
            
            while ((ic = reader.read()) != -1) {
                char c = (char)ic;
                //System.out.print("" + c);
                
                if (state == STATE_VALUE) {                   // [] 括弧の中を読み込み中
                    if (c == '\\') {
                        escaped = !escaped;
                        
                        prop.appendValue(c);
                    } else if (c == ']') {
                        if (escaped) {
                            prop.appendValue(c);
                            escaped = false;
                        } else {
                            prop.pushValue();
                            state = STATE_TOP;
                        }
                    } else {
                        prop.appendValue(c);
                        escaped = false;
                    }
                }else{
                    if(c == '('){
                        current_tree = new ArrayList<GoNode>();
                        
                        stack_tree.push(current_tree);
                        stack_node.push(current_node);
                        
                    } else if(c == ')'){
                        if(prop.isValidProperty()){
                            current_node.addProperty(prop.getProperty());
                            
                            prop.clear();
                        }
                        
                        ArrayList<GoNode> tree = stack_tree.pop();
                        
                        current_node = stack_node.pop();
                        if(current_node == null){
                            if(topNode == null){
                                topNode = arrayToGameTree(tree);
                            }else{
                                topNode.appendSibling(arrayToGameTree(tree));
                            }
                        }else{
                            current_node.appendChild(arrayToGameTree(tree));
                        }
                    }else if(c == ';'){
                        if(prop.isValidProperty()){
                            current_node.addProperty(prop.getProperty());
                            
                            prop.clear();
                        }
                        
                        current_node = new GoNode();
                        current_tree.add(current_node);
                        
                    }else if( c >= 'A' && c <= 'Z'){
                        if(prop.isValidProperty()){
                            current_node.addProperty(prop.getProperty());
                            
                            prop.clear();
                        }
                        prop.appendId(c);
                        
                    }else if( c == '['){
                        state = STATE_VALUE;
                    }
                }
            }
        } catch (IOException ex) {
            System.err.println("err:SgfParser.parser:" + ex);
            topNode = null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public GoNode getTopNode() {
        return topNode;
    }
    
    private GoNode arrayToGameTree(ArrayList<GoNode> list){
        GoNode top = list.get(0);
        GoNode last = null;
        
        for(GoNode node : list){
            if(last != null){
                last.appendChild(node);
            }
            last = node;
        }
        
        return top;
    }
}