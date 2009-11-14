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

package app.sgf.action;

import app.sgf.*;
import go.GoClock;
import go.GoColor;
import go.GoMove;
import go.GoState;
import go.GoVertex;
import gtp.GtpEngine;
import java.awt.event.ActionEvent;
import java.util.Collection;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import sgf.GameTree;
import sgf.GoNode;
import sgf.property.GameInfoProperty;
import sgf.property.SetupProperty;

public class StartGameAction extends AbstractAction{
    private static final String OK = java.util.ResourceBundle.getBundle("app/resource/Resource").getString("OK");
    private static final String CANCEL = java.util.ResourceBundle.getBundle("app/resource/Resource").getString("Cancel");
    
    private static final String START_GAME = java.util.ResourceBundle.getBundle("app/resource/Resource").getString("Start");
    
    private static final String ICON = "image/media-playback-start.png";
    
    private SgfGoGame goGame;
    
    public StartGameAction(SgfGoGame goGame) {
        ClassLoader cl = this.getClass().getClassLoader();
        Icon icon  = new ImageIcon(cl.getResource(ICON));
        
        putValue(Action.NAME, START_GAME);
        putValue(Action.SHORT_DESCRIPTION, START_GAME);
        putValue(Action.SMALL_ICON, icon);
        putValue(Action.LARGE_ICON_KEY, icon);
        
        this.goGame = goGame;
    }
    
    public void actionPerformed(ActionEvent e) {
        GameSettingPanel panel = new GameSettingPanel(goGame);
        
        String[] options = {OK, CANCEL};
        int retval = JOptionPane.showOptionDialog(goGame.getWindow(), panel, java.util.ResourceBundle.getBundle("app/resource/Resource").getString("GameSetting"),
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
        
        if(retval == 0){
            GameTree tree = goGame.getGameTree();
            GameInfoProperty gip = tree.getGameInfoProperty();
            
            int boardSize = tree.getBoardSize();
            double komi = 0.0;
            
            if(gip != null){
                komi = gip.getKomi();
            }
            
            String bName;
            String wName;
            
            GtpEngine blackEngine = null;
            GtpEngine whiteEngine = null;
            
            GtpTerminalWindow term = new GtpTerminalWindow();
            term.setVisible(true);
            goGame.getWindow().toFront();
            
            goGame.getWindow().addComponentListener(term);
            
            boolean hasTimerSetting = panel.hasTimerSetting();
            int mainTime = 60 * panel.getMainTime();
            int byoYomiTime = 60 * panel.getByoYomiTime();
            int byoYomiStones = panel.getByoYomiStones();
            if(hasTimerSetting){
                GoClock clock = new GoClock((double)mainTime, (double)byoYomiTime, byoYomiStones);
                goGame.setClock(clock);
            }
            
            GoNode root = tree.getTopNode();
            
            if(panel.blackIsEngine()){
                blackEngine = panel.getBlackEngien();
                blackEngine.setTerminal(term);
                term.setEngine1(blackEngine);
                blackEngine = engineInit(blackEngine, boardSize, komi);
                blackEngine = engineSetHandicap(blackEngine, root);
                if(hasTimerSetting){
                    blackEngine.timeSettings(mainTime, byoYomiTime, byoYomiStones);
                }
                goGame.setEnginePlayer(GoColor.BLACK, blackEngine);
                
                bName = blackEngine.getName();
                term.setEngine1Name(bName);
                
            }else{
                goGame.setHumanPlayer(GoColor.BLACK);
            }
            
            if(panel.whiteIsEngine()){
                whiteEngine = panel.getWhiteEngien();
                whiteEngine.setTerminal(term);
                term.setEngine2(whiteEngine);
                whiteEngine = engineInit(whiteEngine, boardSize, komi);
                whiteEngine = engineSetHandicap(whiteEngine, root);
                if(hasTimerSetting){
                    whiteEngine.timeSettings(mainTime, byoYomiTime, byoYomiStones);
                }
                goGame.setEnginePlayer(GoColor.WHITE, whiteEngine);
                
                wName = whiteEngine.getName();
                term.setEngine2Name(wName);
                
            }else{
                goGame.setHumanPlayer(GoColor.WHITE);
            }
            
            Collection<GoMove> moveList = tree.getMoveCollection();
            for(GoMove m : moveList){
                System.out.println("StartGameAction: move:" + m.toString());
                if(blackEngine != null){
                    blackEngine.play(m);
                }
                if(whiteEngine != null){
                    whiteEngine.play(m);
                }
            }
            goGame.setGoState(GoState.GTP_GAME);
        }
    }
    
    private GtpEngine engineSetHandicap(GtpEngine gtp, GoNode root){
            
            if(root.hasSetupProperty()){
                SetupProperty sp = root.getSetupProperty();
                Collection<GoVertex> vertexList = sp.getBlackCollection();
                gtp.setFreeHandicap(vertexList);
            }
            
            return gtp;
    }
    
    private GtpEngine engineInit(GtpEngine gtp, int boardSize, double komi){
        gtp.connect();
        gtp.initNameVersion();

        gtp.setBoardsize(boardSize);
        gtp.setKomi(komi);

        return gtp;
    }
}