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

package app.sgf;

import go.GoClock;
import go.board.gui.BoardOperation;
import go.board.gui.NullOperation;
import go.gui.OperationPanel;
import go.GoColor;
import go.GoMove;
import go.GoResult;
import go.GoState;
import go.GoVertex;
import go.controller.ScoringController;
import gtp.GtpCommand;
import gtp.GtpMessage;

import gtp.GtpReceiver;
import gtp.GtpEngine;

public class EnginePlayer extends GtpPlayer implements GtpReceiver{
    private GtpEngine gtp;
    private OperationPanel window;
    
    private BoardOperation nullOperation;
    
    private boolean closed;
    private boolean pass;
    
    public EnginePlayer(GoColor c, OperationPanel window){
        super(c);
        
        this.gtp = null;
        this.window = window;
        
        this.nullOperation = new NullOperation();
        
        this.closed = false;
        this.pass = false;
    }
    
    public void setEngine(GtpEngine engine){
        this.gtp = engine;
        this.closed = false;
        this.gtp.setReceiver(this);
    }
    
    @Override
    public void opponentMove(GoMove move){
        gtp.play(move);
        
        if(pass && move.isPass()){
            pass = false;
            gameController.changeState(GoState.SCORE);
        }
    }
    
    @Override
    public void genMove(GoClock clock){
        window.setOperator(null, nullOperation);

        if(clock.isNoTimeLimit() == false){
            int time = (int)clock.getSeconds(color);
            int stone = clock.getStones(color);
        
            gtp.timeLeft(color, time, stone);
        }
        gtp.genMove(color);
    }
    
    @Override
    public void doScoring(ScoringController con){
        scoringController = con;
        gtp.finalStatusList("dead");
        gtp.finalScore();
    }
    
    @Override
    public void stopScoring(){
        scoringController = null;
    }
    
    @Override
    public void requestUndo(int n){
        for(int i = 0; i < n; ++i){
            gtp.undo();
        }
        gameController.acceptUndo(color, n);
    }
    
    @Override
    public void close(){
        if(gtp != null){
            gtp.quit();
            gtp.close();
            gtp = null;
            closed = true;
        }
    }
    
    public void receive(GtpEngine engine, GtpMessage msg){
        if(closed){
            System.out.println("GtpEnginePlayer.receive: closed");
            return;
        }
        //System.out.println("GtpPlayer.receive command:" + msg.toString());
        
        GtpCommand command = msg.getGtpCommand();
        String message = msg.getMessage();
        
        if(command == GtpCommand.GENMOVE){
            if (msg.success() == false) {
                gameController.changeState(GoState.EDIT);
                return;
            }


            if(message.equals("resign")){
                gameController.resign(color);
            }else{
                GoMove move = new GoMove(gtp.getBoardsize(), color, message);
                
                pass = move.isPass();
                
                gameController.move(move);
            }
        }else if(command == GtpCommand.FINAL_STATUS_LIST){
            // スコアモードの時には失敗しても手で処理できるのでモードを自動で変えない
            if(msg.success()){
                String[] stones = message.split("[ \\n]");
                for(String a : stones){
                    GoVertex v = new GoVertex(gtp.getBoardsize(), a);
                    scoringController.markDeadStone(v);
                }
            }
        }else if(command == GtpCommand.FINAL_SCORE){
            // スコアモードの時には失敗しても手で処理できるのでモードを自動で変えない
            // MoGo は final_score コマンドを持たない
            if(msg.success()){
                if(message.equals("0")){
                    GoResult result = GoResult.createDrawResult();
                    scoringController.setResult(this, result);
                }else{
                    String[] a = message.split("\\+");
                    GoColor winner = GoColor.get(a[0]);
                    double point = Double.parseDouble(a[1]);
                    GoResult result = GoResult.createScoreResult(winner, point);
                    scoringController.setResult(this, result);
                }
            }
        }
    }
}