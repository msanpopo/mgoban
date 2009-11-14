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

package go.controller;

import go.GoGame;
import go.GoResult;
import go.GoState;
import go.GoVertex;
import sgf.property.GameInfoProperty;

public class ScoringController{
    private GoGame goGame;
    private Scorer scorer0;
    private Scorer scorer1;
    
    private GoResult resultBlack;   // 黒が報告した結果
    private GoResult resultWhite;   // 白が報告した結果
    
    private boolean dialogShown;
    
    public ScoringController() {
        this.goGame = null;
        this.scorer0 = null;
        this.scorer1 = null;
        
        this.dialogShown = false;
        
        init();
    }
    
    private void init(){
        resultBlack = null;
        resultWhite = null;
    }
    
    public void setScorer(Scorer scorer0, Scorer scorer1){
        setBlackScorer(scorer0);
        setWhiteScorer(scorer1);
    }
    
    public void setBlackScorer(Scorer scorer){
        scorer0 = scorer;
    }
    
    public void setWhiteScorer(Scorer scorer){
        scorer1 = scorer;
    }
    
    public void start(GoGame goGame) {
        init();
        this.goGame = goGame;
        
        goGame.getBoard().checkTerritory();
        
        scorer0.doScoring(this);
        scorer1.doScoring(this);
    }
    
    public void stop() {
        scorer0.stopScoring();
        scorer1.stopScoring();
        goGame.getBoard().clearTerritory();
    }
    
    public void markDeadStone(GoVertex v){
        System.out.println("ScoringController: markDeadStone:" + v.toString());
        goGame.getBoard().markDeadStone(v);
    }
    
    public void markDeadStoneGroup(GoVertex v){
        System.out.println("ScoringController: markDeadStoneGroup:" + v.toString());
        goGame.getBoard().markDeadStoneGroup(v);
    }
    
    public void toggleDeadStoneGroup(GoVertex v){
        System.out.println("ScoringController: toggleDeadStoneGroup:" + v.toString());
        goGame.getBoard().toggleDeadStoneGroup(v);
    }
    
    public GoResult getLocalResult(){
        double komi = getKomi();
        GoResult result = goGame.getBoard().getResult(komi);
        
        return result;
    }

    public void setResult(Scorer scorer, GoResult result){
        setResultLocal(scorer, result);
        
        if(resultBlack != null && resultWhite != null && dialogShown == false){
            if(resultBlack.equals(resultWhite)){
                showResultDialog();
            }else{
                System.err.println("ScoringController: score mismatch:"
                        + resultBlack.toString() + ":" + resultWhite.toString());
            }
            
            stop();
            
            goGame.setGoState(GoState.EDIT);
        }
    }
    
    public void setResultAndDone(Scorer scorer, GoResult result){
        setResultLocal(scorer0, result);
        setResultLocal(scorer1, result);
        /*
        if(resultBlack.equals(resultWhite) == false){
            System.err.println("ScoringController: score mismatch:" 
                    + resultBlack.toString() + ":" + resultWhite.toString());
        }
        */
        showResultDialog();

        stop();
        
        goGame.setGoState(GoState.EDIT);
    }
    
    public void undo(int n){
        // TODO 
    }
    
    private void showResultDialog(){
        dialogShown = true;
        
        double komi = getKomi();
        String resultString = goGame.getBoard().getResultString(komi);
        goGame.showResultDialog(resultString);
    }
    
    private double getKomi(){
        GameInfoProperty gip = goGame.getGameTree().getGameInfoProperty();
        if(gip != null){
            return gip.getKomi();
        }else{
            return 0.0;
        }
    }
    
    private void setResultLocal(Scorer scorer, GoResult result){
        if(scorer == scorer0){
            resultBlack = result;
        }else if(scorer == scorer1){
            resultWhite = result;
        }
    }
}