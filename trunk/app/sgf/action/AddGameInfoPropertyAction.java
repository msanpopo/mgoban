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
package app.sgf.action;

import go.GoGame;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import sgf.GameTree;
import sgf.GoNode;
import sgf.property.GameInfoProperty;

public class AddGameInfoPropertyAction extends AbstractAction {

    private static final String ADD_GAME_INFO = "追加";
    private GoGame goGame;

    public AddGameInfoPropertyAction(GoGame goGame) {
        putValue(Action.NAME, ADD_GAME_INFO);

        this.goGame = goGame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GoNode node = goGame.getGameTree().getCurrentNode();
        if (node.hasGameInfoProperty()) {
            return; // 既にあった
        } else {
            GameTree tree = goGame.getGameTree();
            GameInfoProperty gip = new GameInfoProperty();
            tree.addGameInfoProperty(gip);

        }
    }
}