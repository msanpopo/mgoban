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

import go.GoMove;
import go.GoVertex;
import go.board.GoBoardState;
import sgf.property.GameInfoProperty;
import sgf.property.MoveProperty;
import sgf.property.NoTypeProperty;
import sgf.property.Property;
import sgf.property.PropertyType;
import sgf.property.RootProperty;
import sgf.property.SetupProperty;
public class GoNode {

    private GoNode parent;
    private GoNode child;
    private GoNode prev;
    private GoNode next;
    
    private GoNode current;
    
    private MoveProperty moveProp;
    private SetupProperty setupProp;
    private RootProperty rootProp;
    private GameInfoProperty gameInfoProp;
    private NoTypeProperty noTypeProp;
    private GoBoardState boardState;

    public GoNode() {
        parent = null;
        child = null;
        prev = null;
        next = null;

        current = null;
        
        moveProp = null;
        setupProp = null;
        rootProp = null;
        gameInfoProp = null;
        noTypeProp = null;
    }

    public static GoNode createRootNode(int boardsize) {
        GoNode node = new GoNode();
        node.rootProp = new RootProperty(boardsize);

        return node;
    }

    public static GoNode createMoveNode(GoMove move) {
        GoNode node = new GoNode();
        node.moveProp = new MoveProperty(move);

        return node;
    }

    ///////////////////////////////////////////


    public boolean hasParent() {
        return parent != null ? true : false;
    }

    public boolean hasChild() {
        return child != null ? true : false;
    }

    public boolean hasPrev() {
        return prev != null ? true : false;
    }

    public boolean hasNext() {
        return next != null ? true : false;
    }

    public boolean isRoot() {
        return hasParent() ? false : true;
    }

    public boolean hasSibling() {
        if (hasPrev() || hasNext()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean hasVariation() {
        return hasSibling();
    }

    private boolean isAlone() {
        if (parent == null && child == null && prev == null && next == null) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean hasChild(GoNode node){
        GoNode c = child;
        while(c != null){
            if(c == node){
                return true;
            }
            c = c.next;
        }
        return false;
    }
    
    public boolean hasStone(GoVertex vertex){
        if(hasMoveProperty()){
            GoMove move = moveProp.getMove();
            
            if(vertex.getX() == move.getVertex().getX() && vertex.getY() == move.getVertex().getY()){
                return true;
            }
        }
        
        if(hasSetupProperty()){
            if(setupProp.hasStone(vertex)){
                return true;
            }
        }
        
        return false;
    }

    /////

    public GoNode getParent() {
        return parent;
    }

    public GoNode getPrev() {
        return prev;
    }

    public GoNode getNext() {
        return next;
    }

    public GoNode getChild() {
        return child;
    }

    public GoNode getFirstSibling() {
        GoNode n = this;

        while (n.hasPrev()) {
            n = n.prev;
        }

        return n;
    }

    public GoNode getLastSibling() {
        GoNode n = this;

        while (n.hasNext()) {
            n = n.next;
        }

        return n;
    }

    
    /////

    /**
     * 自分とその子の間にノードを追加する。子が複数ある場合は、それらの共通の親としてはさむ。
     * @param node 追加するノード。
     * @return 追加したノード。
     */
    public GoNode insertAfter(GoNode node) {
        if (node == null) {
            System.err.println("Node.insertAfter: node == null");
            return null;
        }

        if (node.isAlone() == false) {
            System.err.println("Node.insertAfter: node is not alone");
            return null;
        }

        if (hasChild()) {
            GoNode oldChild = child;

            node.parent = this;
            node.child = child;
            child = node;

            oldChild.parent = node;
            while (oldChild.hasNext()) {
                oldChild.parent = node;
                oldChild = oldChild.next;
            }
        } else {
            child = node;
            node.parent = this;
        }

        return node;
    }

    /**
     * 自分と親の間にノードを挿入する。自分の兄弟が node の兄弟になり、自分の兄弟はいなくなる。<br>
     * 自分がルートの時には無効。
     * @param node 追加するノード。
     * @return 追加したノード。
     */
    public GoNode insertBefore(GoNode node) {
        if (node == null) {
            System.err.println("Node.insertBefore: node == null");
            return null;
        }

        if (node.isAlone() == false) {
            System.err.println("Node.insertBefore: node is not alone");
            return null;
        }

        if (this.isRoot()) {
            System.err.println("Node.insertBefore: can not insert before root");
            return null;
        }

        node.parent = parent;
        node.child = this;

        if (parent.child == this) {
            parent.child = node;
        }

        if (hasPrev()) {
            prev.next = node;
            node.prev = prev;
        }
        if (hasNext()) {
            node.next = next;
            next.prev = node;
        }

        parent = node;
        prev = null;
        next = null;

        return node;
    }

    /**
     * 指定した子の前にノードを挿入する。指定した子が null なら末尾に追加。<br>
     * 追加する node は、親も兄弟もないものだけに制限する。
     * @param sibling このノードの前に挿入する。null なら末尾に追加する。
     * @param node 挿入するノード。
     * @return
     */
    public GoNode addChildBefore(GoNode sibling, GoNode node) {
        if (node == null) {
            System.err.println("Node.addSiblingBefore: node == null");
            return null;
        }

        if (node.hasParent() || node.hasSibling()) {
            System.err.println("Node.addSiblingBefore: node has parent or sibling");
            return null;
        }

        if (hasChild() == false) {
            node.parent = this;
            child = node;

        } else {
            child.addSiblingBefore(sibling, node);
        }

        return node;
    }
    
    /**
     * 指定した子の前にノードを挿入する。指定した子が null なら末尾に追加。<br>
     * 追加する node は、親も兄弟もないものだけに制限する。
     * @param sibling このノードの前に挿入する。null なら末尾に追加する。
     * @param node 挿入するノード。
     * @return
     */
    public GoNode addSiblingBefore(GoNode sibling, GoNode node) {
        if (node == null) {
            System.err.println("Node.addSiblingBefore: node == null");
            return null;
        }

        if (node.hasParent() || node.hasSibling()) {
            System.err.println("Node.addSiblingBefore: node has parent or sibling");
            return null;
        }

        if (sibling != null && sibling.parent != parent) {
            System.err.println("Node.addSiblingBefore: sibling do not has same parent");
            return null;
        }

        node.parent = parent;

        if (sibling != null) {
            if (sibling.hasPrev()) {
                node.prev = sibling.prev;
                node.prev.next = node;
                node.next = sibling;
                sibling.prev = node;

            } else {
                node.next = sibling;
                sibling.prev = node;
                if (hasParent()) {
                    parent.child = node;
                }
            }
        } else {
            GoNode last = getLastSibling();
            node.prev = last;
            last.next = node;
        }

        return node;
    }

    // 子の末尾に追加

    public GoNode appendChild(GoNode node) {
        return addChildBefore(null, node);
    }

    // 子の先頭に追加

    public GoNode prependChild(GoNode node) {
        return addChildBefore(child, node);
    }

    // 兄弟の末尾に追加

    public GoNode appendSibling(GoNode node) {
        return addSiblingBefore(null, node);
    }

    // 兄弟の先頭に追加

    public GoNode prependSibling(GoNode node) {
        GoNode first = getFirstSibling();

        return addSiblingBefore(first, node);
    }

    //////

    // root は深さ 0

    public int getDepthX() {
        if (hasParent()) {
            return parent.getDepthX() + 1;
        } else {
            return 0;
        }
    }

    public int getDepthY() {
        int d = getSiblingIndex();
        if (hasParent()) {
            return parent.getDepthY() + d;
        } else {
            return d;
        }
    }

    private int getSiblingIndex() {
        if (hasPrev()) {
            return prev.getSiblingIndex() + 1;
        } else {
            return 0;
        }
    }

    public void setCurrentVariation(GoNode node){
        if(hasChild(node)){
            current = node;
        }else{
            System.err.println("err:GoNode.setCurrentVariation: node is not variation:" + node);
        }
    }
    
    public GoNode getCurrentVariation(){
        return current;
    }
    
    /**
     * 自分以降のノードをバッサリ削除する。<br>
     * 自分が最後のノードの時には削除しない。<br>
     * 
     * @return 削除後に移動するノード
     */
    public GoNode remove() {
        GoNode retNode;
        
        if (hasPrev() && hasNext()) {
            retNode = next;
            prev.next = next;
            next.prev = prev;
        } else if (hasPrev() && !hasNext()) {
            retNode = prev;
            prev.next = null;
        } else if (!hasPrev() && hasNext()) {
            retNode = next;
            next.prev = null;
            if(hasParent()){
                parent.child = next;
            }
        } else {
            if(hasParent()){
                retNode = parent;
                parent.child = null;
            }else{
                retNode = this;
            }
        }
        
        if(hasParent()){
            parent.current = retNode;
        }

        return retNode;
    }

    /**
     * 指定したノードだけを削除する。<br>
     * 次のノードには適応できない。<br>
     * ・ルートノード
     * ・着手プロパティーを持つ<br>
     * ・自分に兄弟があり、子にも兄弟がある<br>
     * 
     * 
     * @return 削除後に移動するノード
     */
    public GoNode removeSingle() {
        if(hasMoveProperty()){
            System.err.println("err:GoNode.removeSingle: this node has move property.");
            return this;
        }
        
        if(isRoot()){
            System.err.println("err:GoNode.removeSingle: this node is root");
            return this;
        }
        
        if(hasVariation() && hasChild() && child.hasVariation()){
            System.err.println("err:GoNode.removeSingle: this node and child has variation.");
            return this;
        }
        
        GoNode retNode;
        
        if(hasChild()){
            retNode = child;
            child.parent = parent;
            if(hasPrev()){
                prev.next = child;
                child.prev = prev;
            }
            if(hasNext()){
                next.prev = child;
                child.next = next;
            }
            if(parent.child == this){
                parent.child = child;
            }
        }else{
            retNode = remove();
        }
            
        return retNode;
    }
    
///////////////////////////////////////////

    // 問い合わせ

    public boolean isMoveNode() {
        return hasMoveProperty();
    }

    public boolean hasRootProperty() {
        return (rootProp == null) ? false : true;
    }

    public boolean hasMoveProperty() {
        return (moveProp == null) ? false : true;
    }

    public boolean hasGameInfoProperty() {
        return (gameInfoProp == null) ? false : true;
    }

    public boolean hasSetupProperty() {
        return (setupProp == null) ? false : true;
    }

    public boolean hasNoTypeProperty() {
        return (noTypeProp == null) ? false : true;
    }

    //

    public MoveProperty getMoveProperty() {
        return moveProp;
    }

    public SetupProperty getSetupProperty() {
        return setupProp;
    }

    public RootProperty getRootProperty() {
        return rootProp;
    }

    public GameInfoProperty getGameInfoProperty() {
        return gameInfoProp;
    }

    public NoTypeProperty getNoTypeProperty() {
        return noTypeProp;
    }

    // 後付けで MoveProperty や RootProperty を追加することはありえない
    /*
    public void setMoveProperty(MoveProperty p){ moveProp = p;}
    public void setRootProperty(RootProperty p){ rootProp = p;}
     */

    public void setSetupProperty(SetupProperty p) {
        setupProp = p;
    }

    public void setGameInfoProperty(GameInfoProperty p) {
        gameInfoProp = p;
    }

    public void setNoTypeProperty(NoTypeProperty p) {
        noTypeProp = p;
    }

    public GoNode getGameInfoNode() {
        if (hasGameInfoProperty()) {
            return this;
        } else {
            if (hasParent()) {
                return parent.getGameInfoNode();
            } else {
                return null;
            }
        }
    }

    /**
     * ノードが属するパス上に GameInfoNode があるかどうか調べる。
     * @return あれば true を返す。
     */
    public boolean pathHasGameInfoNode() {
        // 先祖をチェック
        if (getGameInfoNode() != null) {
            return true;
        }
        // 自分と子孫をチェック
        if (treeHasGameInfoNode()) {
            return true;
        }
        return false;
    }

    private boolean treeHasGameInfoNode() {
        if (hasGameInfoProperty()) {
            return true;
        } else {
            if (hasChild()){
                GoNode n = child;
                while(true){
                    if(n.treeHasGameInfoNode()){
                        return true;
                    }
                    
                    if(n.hasChild()){
                        n = n.child;
                    }else{
                        break;
                    }
                }
            }
        }
        return false;
    }

    // GoBoardState を取得する。
// なければ作ってノードにセットする。

    public GoBoardState getBoardState(int boardSize) {
        if (boardState == null) {
            if (hasParent()) {
                GoBoardState pbs = parent.getBoardState(boardSize);

                boardState = new GoBoardState(pbs);
            } else {
                boardState = new GoBoardState(boardSize);

            }
            boardState.addStone(this);
        }

        return boardState;
    }

    // 自分自身と自分に続くノードが持っているBoardState を null でクリアーする。

    public void clearBoardState() {
        boardState = null;

        if (hasChild()) {
            child.clearBoardStateRecursive();
        }
    }

    // this, next, child の持っている BoardState を null でクリアーする。

    private void clearBoardStateRecursive() {
        if (boardState != null) {
            boardState = null;
            if (hasChild()) {
                child.clearBoardStateRecursive();
            }

        }

        if (hasNext()) {
            next.clearBoardStateRecursive();
        }
    }

    // 連続したパスの数を返す

    public int getPassCount() {
        if (hasMoveProperty()) {
            GoMove move = getMoveProperty().getMove();
            if (move.isPass()) {
                int count = 1;

                if (hasParent()) {
                    count += parent.getPassCount();
                }
                return count;

            } else {
                return 0;
            }

        } else {
            if (hasParent()) {
                return parent.getPassCount();
            } else {
                return 0;
            }
        }
    }

    public int getMoveNumber() {
        if (hasMoveProperty()) {
            MoveProperty p = getMoveProperty();
            int n = p.getNumber();
            if (n == 0) {
                n = lastMoveNumber() + 1;
                p.setNumber(n);
            }
            return n;
        } else {
            return 0;
        }
    }

    private int lastMoveNumber() {
        if (hasParent()) {
            if (parent.hasMoveProperty()) {
                MoveProperty mp = parent.getMoveProperty();

                int n = mp.getNumber();
                if (n == 0) {
                    n = parent.lastMoveNumber() + 1;

                    mp.setNumber(n);
                }

                return n;
            } else {
                return parent.lastMoveNumber();
            }
        } else {
            return 0;
        }
    }

    // sgf ファイルから作るときに使う。
// 何もチャックしない。

    public void addProperty(Property p) {
        PropertyType type = p.getType();
        switch (type) {
        case MOVE:
            if (hasMoveProperty() == false) {
                moveProp = new MoveProperty();
            }
            moveProp.setProperty(p);
            break;

        case SETUP:
            if (hasSetupProperty() == false) {
                setupProp = new SetupProperty();
            }
            setupProp.setProperty(p);
            break;

        case ROOT:
            if (hasRootProperty() == false) {
                rootProp = new RootProperty();
            }
            rootProp.setProperty(p);
            break;

        case GAME_INFO:
            if (hasGameInfoProperty() == false) {
                gameInfoProp = new GameInfoProperty();
            }
            gameInfoProp.setProperty(p);
            break;

        case NO_TYPE:
            if (hasNoTypeProperty() == false) {
                noTypeProp = new NoTypeProperty();
            }
            noTypeProp.setProperty(p);
            break;

        default:
            System.err.println("err :Node.addProperty:" + p.toSgfString());
            break;
        }
    }

    public String toSgfString() {
        StringBuilder sb = new StringBuilder();

        if (hasSibling() || isRoot()) {
            sb.append("(");
        }

        sb.append(";");

        if (hasRootProperty()) {
            sb.append(rootProp.toSgfString());
        }
        if (hasMoveProperty()) {
            sb.append(moveProp.toSgfString());
        }
        if (hasSetupProperty()) {
            sb.append(setupProp.toSgfString());
        }
        if (hasGameInfoProperty()) {
            sb.append(gameInfoProp.toSgfString());
        }
        if (hasNoTypeProperty()) {
            sb.append(noTypeProp.toSgfString());
        }

        if (child != null) {
            sb.append("\n");
            sb.append(child.toSgfString());
        }

        if (hasSibling() || isRoot()) {
            sb.append(")\n");
        }

        if (next != null) {
            sb.append(next.toSgfString());
        }

        return sb.toString();
    }
}