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

package sgf;

import go.GoColor;
import go.board.GoBoardState;
import java.io.File;
import go.GoMove;
import go.GoVertex;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.JOptionPane;
import sgf.property.GameInfoProperty;
import sgf.property.MoveProperty;
import sgf.property.RootProperty;
import sgf.property.SetupProperty;

public class GameTree{
    private static final String DEFAULT_CHARSET = "UTF-8";
    private File file;
    
    private GoNode root;      // コレクションの先頭ノードのこと。ツリーの先頭ノードは getTopNode で取ること。
    private GoNode current;
    private GoNode pivot;
    
    private int boardSize;
    
    private ArrayList<GameTreeListener> listener;
    
    // サイズが 19 でコミも置き石も何も設定されてないツリーを用意する
    public GameTree(){
        this.file = null;
        
        this.root = GoNode.createRootNode(19);
        this.current = root;
        this.pivot = null;
        this.boardSize = 19;
        this.listener = new ArrayList<GameTreeListener>();
    }

    public void addGameTreeListener(GameTreeListener newListener){
        listener.add(newListener);
    }
    
    private void fireTreeChanged(){
        for(GameTreeListener l : listener){
            l.treeChanged(this, boardSize);
        }
    }
    
    private void fireNodeMoved(GoNode old){
        for(GameTreeListener l : listener){
            l.nodeMoved(this, old);
        }
    }
    
    public void fireNodeStateChanged(){
        current.clearBoardState();
        
        for(GameTreeListener l : listener){
            l.nodeStateChanged(this);
        }
    }
    
    public String getCharset(){
        if(root.hasRootProperty()){
            RootProperty rp = root.getRootProperty();
            String charsetTmp = rp.getCharset();
            if(charsetTmp != null && charsetTmp.isEmpty() == false){
                return charsetTmp;
            }
        }
        
        return DEFAULT_CHARSET;
    }

    public Collection<GoMove> getMoveCollection(){
        ArrayList<GoMove> moveList = new ArrayList<GoMove>();
        
        GoNode n = current;
        while(n.isRoot() == false){
            if(n.hasMoveProperty()){
                MoveProperty mp = n.getMoveProperty();
                GoMove move = mp.getMove();
                
                moveList.add(0, move);
            }
            
            n = n.getParent();
        }
        
        return moveList;
    }

    public String getGameDescription(){
        GameInfoProperty gip = getGameInfoProperty();
        if(gip != null){
            return gip.getGameInfoDescription();
        }else{
            return "";
        }

    }
    
    public void setNewTree(File sgfFile, String charset){
        if(sgfFile == null){
            System.err.println("GameTree.setNewTree: file == null");
            return;
        }
        
        if(sgfFile.exists() == false){
            System.err.println("GameTree.setNewTree: file not exists");
            return;
        }
        
        file = sgfFile;
        GoNode rootNode = openFile(sgfFile, charset);
        
        root = rootNode;
        current = rootNode;
        pivot = null;
        boardSize = getBoardSizeFromProperty();
        
        fireTreeChanged();
        fireNodeMoved(null);
    }

    public void setNewTree(String text) {
        SgfParser parser = new SgfParser(); // TODO:文字コード決め打ちでいいのか？
        parser.setSgfText(text);
        parser.parse();

        GoNode rootNode = parser.getTopNode();
        
        file = null;
        root = rootNode;
        current = rootNode;
        pivot = null;
        boardSize = getBoardSizeFromProperty();

        fireTreeChanged();
        fireNodeMoved(null);
    }

    public void setNewTree(GoNode newTree){
        file = null;
        root = newTree;
        current = newTree;
        pivot = null;
        boardSize = getBoardSizeFromProperty();
        
        fireTreeChanged();
        fireNodeMoved(null);
    }



    public boolean hasFileName(){
        if(file == null){
            return false;
        }else{
            return true;
        }
    }

    private GoNode openFile(File file, String charset){
        SgfParser parser = new SgfParser(charset);
        parser.setSgfFile(file);
        parser.parse();
        
        return parser.getTopNode();
    }
    
    public void save(){
        save(file);
    }

    public void save(File file){
        if(file == null){
            System.err.println("GameTree.saveFile: file == null");
            return;
        }
        
        this.file = file;
        
        String sgf = root.toSgfString();
        
        if(sgf == null || sgf.isEmpty()){
            System.err.println("GameTree.saveFile: sgf == null || sgf.isEmpty");
            return;
        }
        
        String writeCharset = getCharset();

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), writeCharset));
            writer.write(sgf);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if(writer != null){
                try {
                    writer.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    private int getBoardSizeFromProperty(){
        int size;
        GoNode top = getTopNode();
        if(top.hasRootProperty()){
            RootProperty p = top.getRootProperty();
        
            size = p.getBoardSize();
        }else{
            // 新浪囲棋 の SGF ファイルにはルートプロパティーがない。
            size = 19;
        }
        return size;
    }
    
    // コレクションの先頭。複数のコレクションを含むファイルでは root とは別。
    public GoNode getTopNode(){
        GoNode n = current;
        while(n.hasParent()){
            n = n.getParent();
        }
        return n;
    }
    
    public GoNode getCurrentNode(){
        return current;
    }
    
    public int getBoardSize(){
        return boardSize;
    }
    
    // current の移動
    public void top(){
        GoNode old = current;
        
        while(current.hasParent() && checkPivot() == false){
            current = current.getParent();
        }
        
        fireNodeMoved(old);
    }
    
    public void bottom(){
        GoNode old = current;
        
        while(current.hasChild()){
            current = current.getChild();
        }
        
        fireNodeMoved(old);
    }
    
    public void backward(){
        if(current.hasParent() && checkPivot() == false){
            GoNode old = current;
            
            current = current.getParent();
            
            fireNodeMoved(old);
        }
    }
    
    // undo 用
    public void backward(int n){
        if(current.hasParent()){
            GoNode old = current;
            
            for(int i = 0; i < n; ++i){
                if(current.hasParent()){
                    current = current.getParent();
                }
            }
            fireNodeMoved(old);
        }
    }
    
    public void forward(){
        if(current.hasChild()){
            GoNode old = current;
            
            GoNode node = current.getCurrentVariation();
            if(node == null){
                current = current.getChild();
            }else{
                current = node;
            }
            fireNodeMoved(old);
        }
    }
    
    public void nextVariation(){
        if(current.hasNext()){
            GoNode old = current;
            
            current = current.getNext();
            if(current.hasParent()){
                current.getParent().setCurrentVariation(current);
            }
            
            if(current.isRoot()){
                boardSize = getBoardSizeFromProperty();
                fireTreeChanged();
            }
            fireNodeMoved(old);
        }
    }
    
    public void prevVariation(){
        if(current.hasPrev()){
            GoNode old = current;
            
            current = current.getPrev();
            if(current.hasParent()){
                current.getParent().setCurrentVariation(current);
            }
            
            if(current.isRoot()){
                boardSize = getBoardSizeFromProperty();
                fireTreeChanged();
            }
            fireNodeMoved(old);
        }
    }
    
    public void jump(GoNode node){
        if(node == null){
            System.err.println("err:GameTree.jump: node == null");
            return ;
        }
        
        GoNode old = current;
        current = node;
        fireNodeMoved(old);
    }
    
    //
    public GoColor getNextColor(){
        GoColor color = getNextColor(current);
        
        return color;
    }
    
    private static GoColor getNextColor(GoNode node){
        GoColor color = GoColor.BLACK;
        
        if(node.hasMoveProperty()){
            MoveProperty p = node.getMoveProperty();
            GoMove move = p.getMove();
            // 新浪囲棋 で KO をコミとして使っていて
            // KO のあるノードを着手プロパティーがあるように判断してしまう。
            // そのようなノードで着手はとれないので、チャックする。
            if(move != null){
                color = move.getColor().opponentColor();
            }
        }else if(node.hasSetupProperty()){
            SetupProperty p = node.getSetupProperty();
            Collection<GoVertex> c = p.getBlackCollection();
            
            if(p.hasNextColor()){
                color = p.getNextColor();
            }else{
                if(node.isRoot() && c.size() > 0){
                    color = GoColor.WHITE;
                }
            }
        }else{
            if(node.hasParent()){
                GoNode parent = node.getParent();
                color = getNextColor(parent);
            }
        }
        return color;
    }
    
    public void removeCurrentNode() {
        if(current.isRoot()){
            GoNode next = current.remove();
            if(current == root){
                root = next;
            }
            current = next;
            
            boardSize = getBoardSizeFromProperty();
            fireTreeChanged();
            fireNodeMoved(null);
        }else{
            if(current.hasMoveProperty()){
                current = current.remove();
            }else{
                current = current.removeSingle();
            }
            
            fireNodeMoved(null);
        }
    }
    
    // current の後に挿入する。挿入したノードに移動する。
    public void insertAfter(GoNode node){
        GoNode old = current;
        
        current = current.insertAfter(node);
        current.clearBoardState();
        
        fireNodeMoved(old);
    }
    
    // current の前に挿入する。挿入したノードに移動する。
    public void insertBefore(GoNode node){
        GoNode old = current;
        
        current = current.insertBefore(node);
        current.clearBoardState();
        
        fireNodeMoved(old);
    }
    
    public void addRoot(GoNode newRoot){
        root.appendSibling(newRoot);
        
        current = newRoot;
        boardSize = getBoardSizeFromProperty();
        
        fireTreeChanged();
        fireNodeMoved(null);
    }
    
    public void move(GoMove move){
        GoNode old = current;
        GoNode n = findChild(current, move);
        
        if(n == null){
            n = GoNode.createMoveNode(move);
            current.appendChild(n);
        }
        
        current = n;
        old.setCurrentVariation(current);
        
        fireNodeMoved(old);
    }
    
    public void move(GoMove move, int moveNo){
        GoNode old = current;
        int currentNo = current.getMoveNumber();
        
        if(moveNo > currentNo + 1){
            // ありえない
            System.err.println("GameTree.move wingmove:moveNo:" + moveNo + ": currentNo:" + currentNo + ":" + move.toString());
            return;
        }else if(moveNo == currentNo + 1){
        }else{
            // undo などの手のもどりがあった
            while(currentNo >= moveNo){
                if(current.hasParent()){
                    current = current.getParent();
                    currentNo = current.getMoveNumber();
                }else{
                    break;
                }
            }
        }
        
        GoNode n = GoNode.createMoveNode(move);
        current.appendChild(n);
        current = n;
        old.setCurrentVariation(current);
        
        fireNodeMoved(old);
    }
    
    private static GoNode findChild(GoNode node, GoMove move){
        if(node.hasChild()){
            GoNode child = node.getChild();
            while(true){
                if(child.hasMoveProperty()){
                    MoveProperty mp = child.getMoveProperty();
                    GoMove m = mp.getMove();
                    if(move.equals(m)){
                        return child;
                    }
                }
                
                if(child.hasNext()){
                    child = child.getNext();
                }else{
                    break;
                }
            }
        }
        
        return null;
    }
    
    public int getPassCount(){
        return current.getPassCount();
    }
    
    public String toSgfString(){
        return root.toSgfString();
    }
    
    public void setPivot() {
        pivot = current;
        fireNodeStateChanged();
    }
    
    public void resetPivot(){
        pivot = null;
        fireNodeStateChanged();
    }
    
    public boolean checkPivot(){
        if(current == pivot){
            return true;
        }else{
            return false;
        }
    }
    
    public boolean checkPivot(GoNode node){
        if(node == pivot){
            return true;
        }else{
            return false;
        }
    }
    
    public int getDepthX(){
        return current.getDepthX();
    }
    
    public int getDepthY(){
        return current.getDepthY();
    }
    
    public GoBoardState getBoardState(){
        return current.getBoardState(boardSize);
    }
    
    /*
     * game info property を持たない node でも先祖の node が持っていればそれを引き継いでるので、
     * game info property は先祖まで探しにいく。
     */
    public GameInfoProperty getGameInfoProperty(){
        GoNode n = current.getGameInfoNode();
        if(n != null){
            return n.getGameInfoProperty();
        }else{
            return null;
        }
    }
    
    public void setPlayerToPlay(GoColor color){
        if(current.hasSetupProperty()){
            SetupProperty sp = current.getSetupProperty();
            sp.setNextColor(color);
            
            fireNodeStateChanged();
        }else if(current.hasMoveProperty()){
            SetupProperty sp = new SetupProperty();
            sp.setNextColor(color);
            GoNode newNode = new GoNode();
            newNode.setSetupProperty(sp);
            insertAfter(newNode);
        }else{
            SetupProperty sp = new SetupProperty();
            sp.setNextColor(color);
            current.setSetupProperty(sp);
            
            fireNodeStateChanged();
        }
    }
        
    public void addGameInfoProperty(GameInfoProperty gip) {
        if(current.hasGameInfoProperty()){
            return;
        }
        
        
        if(current.pathHasGameInfoNode()){
            JOptionPane.showMessageDialog(null, "パス上に既に対局情報が存在するため実行できません。");
        }else{
            current.setGameInfoProperty(gip);
        }
        fireNodeStateChanged();
    }
    
    public GoNode searchAncestor(GoVertex vertex){
        GoNode n = current;
        while(true){
            if(n.hasStone(vertex)){
                return n;
            }
            
            if(n.hasParent()){
                n = n.getParent();
            }else{
                break;
            }
        }
        
        return null;
    }

    
}