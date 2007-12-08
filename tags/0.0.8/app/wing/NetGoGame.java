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

package app.wing;

import go.GoGame;
import go.GoState;
import go.controller.GameController;
import go.controller.ScoringController;
import javax.swing.JFrame;

public class NetGoGame extends GoGame{
    private GameController gameController;
    private ScoringController scoringController;

    private NetPlayer blackPlayer;
    private NetPlayer whitePlayer;
    
    public NetGoGame(JFrame owner, double mainSec, double byoYomiSec, int byoYomiStones, double kouryoSec){
        super(owner, mainSec, byoYomiSec, byoYomiStones, kouryoSec);
        
        gameController = new GameController();
        scoringController = new ScoringController();
        
        this.blackPlayer = null;
        this.whitePlayer = null;
        
        state = GoState.NET_GAME;
    }

    public void setPlayer(NetPlayer blackPlayer, NetPlayer whitePlayer){
        this.blackPlayer = blackPlayer;
        this.whitePlayer = whitePlayer;
        gameController.setPlayer(blackPlayer, whitePlayer);
        scoringController.setScorer(blackPlayer, blackPlayer);
    }
    
    @Override
    public void setGoState(GoState newState){
        GoState oldState = state;
        this.state = newState;
        
        clock.stop();
        
        if(oldState == GoState.SCORE){
            gameController.stop();
        }
        
        if(state == GoState.SCORE){
            scoringController.setScorer(blackPlayer, whitePlayer);
            scoringController.start(this);
        
        }else if(state == GoState.NET_GAME){
            if(oldState == GoState.SCORE){
                scoringController.stop();
            }
            gameController.setPlayer(blackPlayer, whitePlayer);
            gameController.start(this);
            
        }else if(state == GoState.DONE){
        }
        
        goStateChanged(state);
    }
    
    @Override
    public void start(GoGame goGame){
        setGoState(GoState.NET_GAME);
    }
    
    @Override
    public void next(GoGame goGame){
        if(state == GoState.NET_GAME){
            gameController.next(goGame);
        }
    }
    
    @Override
    public void stop(){
        setGoState(GoState.DONE);
    }
}