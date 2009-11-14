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

package go;

import go.controller.GameController;
import go.controller.Scorer;
import go.controller.ScoringController;

public class GoPlayer implements Scorer {
    protected GoColor color;
    protected GameController gameController;
    protected ScoringController scoringController;
    
    public GoPlayer(GoColor c){
        this.color = c;
        this.gameController = null;
        this.scoringController = null;
    }
    
    public GoColor getColor(){
        return color;
    }
    
    public void setGameController(GameController con){
        gameController = con;
    }
    
    // コントローラーからの指示
    public void opponentMove(GoMove move){}         // 直前の相手の着手は move だ
    public void genMove(GoClock clock){}            // 着手しろ。時計にも自分で気をつけろ
    public void requestUndo(int n){}                // undo 依頼がきたからどうにかしろ
    
    public void close(){}
    
    public void doScoring(ScoringController con) {
        scoringController = con;
    }
    public void stopScoring() {}
}