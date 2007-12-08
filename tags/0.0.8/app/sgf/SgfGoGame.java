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

package app.sgf;

import go.GoClock;
import go.GoColor;
import go.GoGame;
import go.GoPlayer;
import go.GoState;
import go.controller.EditController;
import go.controller.GameController;
import go.controller.MarkController;
import go.controller.ScoringController;
import go.controller.SetupController;
import gtp.GtpEngine;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class SgfGoGame extends GoGame{
    private static final String BLACK = "黒";
    private static final String WHITE = "白";
    private static final String NO = "の";
    private static final String TIME_OVER = "時間切れ";
    private static final String WIN = "勝ち";
    
    private GameController gameController;
    private ScoringController scoringController;
    
    private GoPlayer blackPlayer;
    private GoPlayer whitePlayer;
    
    private HumanPlayer humanBlack;
    private HumanPlayer humanWhite;
    private EnginePlayer engineBlack;
    private EnginePlayer engineWhite;

    private SgfEditor blackEditor;
    private SgfEditor whiteEditor;
    
    private EditController editController;
    private MarkController markController;
    private SetupController setupController;
    
    public SgfGoGame(JFrame owner){
        super(owner);
        
        gameController = new GameController();
        scoringController = new ScoringController();
        
        blackPlayer = null;
        whitePlayer = null;
        
        state = GoState.EDIT;

        editController = null;
        markController = null;
        setupController = null;
    }
    
    @Override
    public void connectObject(){
        super.connectObject();
        
        humanBlack = new HumanPlayer(GoColor.BLACK, board, opePane);
        humanWhite = new HumanPlayer(GoColor.WHITE, board, opePane);
        engineBlack = new EnginePlayer(GoColor.BLACK, opePane);
        engineWhite = new EnginePlayer(GoColor.WHITE, opePane);

        blackEditor = new SgfEditor(GoColor.BLACK, board, opePane);
        whiteEditor = new SgfEditor(GoColor.WHITE, board, opePane);
        
        blackPlayer = blackEditor;
        whitePlayer = whiteEditor;
        
        editController = new EditController(this, blackEditor, whiteEditor);
        
        markController = new MarkController(this, opePane);
        setupController = new SetupController(this, opePane);
    }
    
    @Override
    public void clockTimeOver(GoClock clock, GoColor color) {
        StringBuilder msg = new StringBuilder();
        if(color.isBlack()){
            msg.append(BLACK).append(NO).append(TIME_OVER).append("\n");
            msg.append(WHITE).append(NO).append(WIN);
        }else{
            msg.append(WHITE).append(NO).append(TIME_OVER).append("\n");
            msg.append(BLACK).append(NO).append(WIN);
        }
        
                
        setClock(null);

        JOptionPane.showMessageDialog(null, msg.toString(), TIME_OVER,  JOptionPane.INFORMATION_MESSAGE);
        
        setGoState(GoState.EDIT);
    }
    
    @Override
    public void setGoState(GoState newState){
        GoState oldState = state;
        state = newState;
        
        if(state == GoState.SCORE){
            showDoScoringDialog();
        }
        
        if(oldState == GoState.SCORE || (oldState == GoState.GTP_GAME && newState == GoState.EDIT)){
            gameController.stop();
        }
        
        if(state == GoState.SCORE){
            scoringController.setScorer(blackPlayer, whitePlayer);
            scoringController.start(this);

        }else if(state == GoState.EDIT){
            clock = new GoClock();
            blackPlayer = blackEditor;
            whitePlayer = whiteEditor;
            editController.start(this);
            
        }else if(state == GoState.GTP_GAME){
            gameController.setPlayer(blackPlayer, whitePlayer);
            gameController.start(this);
            
        }else if(state == GoState.MARK){
            markController.setMarkType(state);
            markController.start(this);
            
        }else if(state == GoState.CHAR_LABEL){
            markController.setMarkType(state);
            markController.start(this);
            
        }else if(state == GoState.NUMBER_LABEL){
            markController.setMarkType(state);
            markController.start(this);
            
        }else if(state == GoState.SETUP){
            setupController.start(this);
        }
        
        goStateChanged(state);
    }

    public void setHumanPlayer(GoColor color){
        if(color == GoColor.BLACK){
            blackPlayer = humanBlack;
        }else{
            whitePlayer = humanWhite;
        }
    }
    
    public void setEnginePlayer(GoColor color, GtpEngine engine){
        if(color == GoColor.BLACK){
            engineBlack.setEngine(engine);
            blackPlayer = engineBlack;
        }else{
            engineWhite.setEngine(engine);
            whitePlayer = engineWhite;
        }
    }
    
    @Override
    public void start(GoGame goGame){
        setGoState(GoState.EDIT);
    }
    
    @Override
    public void next(GoGame goGame){
        if(state == GoState.GTP_GAME){
            gameController.next(goGame);
        }else if(state == GoState.EDIT){
            editController.next(goGame);
        }
    }
    
    @Override
    public void stop(){
        gameController.stop();
        humanBlack.close();
        humanWhite.close();
        engineBlack.close();
        engineWhite.close();
    }
}