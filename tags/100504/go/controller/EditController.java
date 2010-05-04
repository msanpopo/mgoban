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
package go.controller;

import app.App;
import app.SoundType;
import app.sgf.SgfEditor;
import go.GoColor;
import go.GoGame;
import go.GoMove;
import go.GoVertex;
import sgf.GameTree;
import sgf.GoNode;

public class EditController {

    private GoGame goGame;
    private GameTree tree;
    private SgfEditor blackEditor;
    private SgfEditor whiteEditor;
    private SgfEditor current;

    public EditController(GoGame goGame, SgfEditor blackEditor, SgfEditor whiteEditor) {
        this.goGame = goGame;
        this.tree = goGame.getGameTree();

        blackEditor.setEditController(this);
        whiteEditor.setEditController(this);
        
        this.blackEditor = blackEditor;
        this.whiteEditor = whiteEditor;
        this.current = blackEditor;
    }

    public void start(GoGame goGame) {
        next(goGame);
    }

    public void next(GoGame goGame) {
        if (tree.getNextColor() == GoColor.BLACK) {
            current = blackEditor;
        } else {
            current = whiteEditor;
        }

        current.genMove();
    }

    public void stop() {
    }

    // Player から呼ばれる。通常の着手以外に pass も含む。
    public void move(GoMove m) {
        if (goGame.isMoveSoundEnabled()) {
            App.getInstance().soundPlay(SoundType.CLICK);
        }

        if (current == blackEditor) {
            whiteEditor.opponentMove(m);
        } else {
            blackEditor.opponentMove(m);
        }

        tree.move(m);
    }

    public void pass(GoColor c) {
        GoMove m = new GoMove(c, "");
        move(m);
    }

    public void resign(GoColor c) {
    }

    public void backward() {
        tree.backward();
    }

    public void forward() {
        tree.forward();
    }

    public void prevVariation() {
        tree.prevVariation();
    }

    public void nextVariation() {
        tree.nextVariation();
    }
    
    public void jump(GoVertex vertex){
        GoNode node = tree.searchAncestor(vertex);
        if(node != null){
            tree.jump(node);
        }
    }
}