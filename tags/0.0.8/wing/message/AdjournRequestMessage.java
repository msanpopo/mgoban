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

package wing.message;

import wing.Block;

/*
 *
nngs
9 aaa would like to adjourn the game.
9 Use adjourn to adjourn the game.

wing
9 対局の相手が対局の中断を要請してきました。
9 中断に同意する場合は "adjourn"、拒否する場合は "decline adjourn" を入力して下さい。
1 6
9 Your opponent requests an adjournment.
9 Use <adjourn> to adjourn, or <decline adjourn> to decline.
1 6
 */
public class AdjournRequestMessage extends Message{
    
    public AdjournRequestMessage(Block block) {
        super(block);
    }
}