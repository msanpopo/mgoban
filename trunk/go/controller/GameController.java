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
import go.GoClock;
import go.GoColor;
import go.GoGame;
import go.GoMove;
import go.GoPlayer;
import go.GoState;
import sgf.GameTree;

public class GameController{
    private GoGame goGame;
    private GameTree tree;
    
    private GoClock clock;
    
    private GoPlayer blackPlayer;
    private GoPlayer whitePlayer;
    private GoPlayer current;
    
    public GameController() {
        this.goGame = null;
        this.tree = null;
        this.clock = null;
        
        blackPlayer = null;
        whitePlayer = null;
        
        this.current = null;
    }
    
    public void changeState(GoState newState){
        goGame.setGoState(newState);
    }

    public GoClock getClock(){
        return clock;
    }
    
    public GameTree getGameTree(){
        return tree;
    }
    
    public void setPlayer(GoPlayer black, GoPlayer white){
        setBlackPlayer(black);
        setWhitePlayer(white);
    }
    
    public void setBlackPlayer(GoPlayer player){
        blackPlayer = player;
        blackPlayer.setGameController(this);
    }
    
    public void setWhitePlayer(GoPlayer player){
        whitePlayer = player;
        whitePlayer.setGameController(this);
    }
    
    public void start(GoGame goGame) {
        this.goGame = goGame;
        this.tree = goGame.getGameTree();
        this.clock = goGame.getClock();

        next(goGame);
    }
    
    public void next(GoGame goGame){
        if(tree.getNextColor().isBlack()){
            current = blackPlayer;
        }else{
            current = whitePlayer;
        }

        current.genMove(clock);
        clock.start(current.getColor());
    }
    
    public void stop() {
        if(clock != null){
            clock.stop();
        }
        if(blackPlayer != null){
            blackPlayer.close();
        }
        if(whitePlayer != null){
            whitePlayer.close();
        }
    }
    
    // Player から呼ばれる。通常の着手以外に pass も含む。
    public void move(GoMove m){
        clock.stop();
        
        if(goGame.isMoveSoundEnabled()){
            App.getInstance().soundPlay(SoundType.CLICK);
        }
        
        if(current == blackPlayer){
            whitePlayer.opponentMove(m);
        }else{
            blackPlayer.opponentMove(m);
        }
        
        tree.move(m);
    }
    
    // wing 用
    public void move(GoMove m, int moveNo){
        clock.stop();
        
        if(goGame.isMoveSoundEnabled()){
            App.getInstance().soundPlay(SoundType.CLICK);
        }

        /*
         * 相手には直接しらせない。
         * undo 直後で着手が戻ってるかもしれないから
         */
        tree.move(m, moveNo);
    }
        
    public void pass(GoColor c){
        GoMove m = new GoMove(c, "");
        move(m);
    }

    public void resign(GoColor c){
        if(goGame.getGoState() == GoState.EDIT){
            // nngs で投了すると info と shout の両方で投了メッセージを拾ってしまってダイアログが二つでるので苦し紛れの方法で抑制する。
            return;
        }
        
        clock.stop();

        System.out.println("GoController:" + c.toString() + " resign");
        
        GoColor winner = c.opponentColor();
        goGame.setGoState(GoState.EDIT);       
        goGame.showResignDialog(winner);
    }
    
    // undo のお願いをするのは HumanPlayer のみ。
    public void requestUndo(GoColor c, int n){
        if(c == GoColor.BLACK){
            whitePlayer.requestUndo(n);
        }else{
            blackPlayer.requestUndo(n);
        }
    }
    
    public void acceptUndo(GoColor c, int n){
        tree.backward(n);
    }
    
    public void backward(){}
    public void forward(){}
    public void prevVariation(){}
    public void nextVariation(){}
}