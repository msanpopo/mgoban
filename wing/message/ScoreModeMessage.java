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

package wing.message;

import wing.Block;

/*
 * サーバーからのメッセージにゲーム番号が入ってないのでどのゲームへのものか区別できないが、いいのか？
 * 一人で同時に一つの対局しかできないのかも？
 *
9 You can check your score with the score command, type 'done' when finished.
 */
public class ScoreModeMessage extends Message{
    
    public ScoreModeMessage(Block block) {
        super(block);
    }
}