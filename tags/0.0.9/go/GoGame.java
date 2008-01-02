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

package go;

import app.App;
import app.SoundType;
import go.gui.OperationPanel;
import go.board.GoBoard;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import sgf.GameTree;
import sgf.GameTreeListener;
import sgf.GoNode;
import sgf.property.MoveProperty;

public class GoGame implements GameTreeListener, ComponentListener, GoClockListener{
    
    private static final String DO_SCORING = "死んだ石をクリックした後、終了ボタンを押してください。";
    
    private static final String BLACK = "黒";
    private static final String WHITE = "白";
    private static final String RESULT_RESIGN = " の中押し勝ち";
    
    private JFrame owner;
    
    protected GoBoard board;
    private GameTree tree;
    protected JFrame window;
    protected OperationPanel opePane;
    
    protected GoClock clock;
    
    private boolean moveSoundEnable;
    
    private ArrayList<GoGameListener> listenerList;
    
    protected GoState state;
    
    public GoGame(){
        this.owner = null;
        
        init();
        
        clock = new GoClock();
        clock.setListener(this);
    }
    
    public GoGame(JFrame owner, double mainSec, double byoYomiSec, int byoYomiStones, double kouryoSec){
        this.owner = owner;
        this.owner.addComponentListener(this);
        
        init();
        
        clock = new GoClock(mainSec, byoYomiSec, byoYomiStones, kouryoSec);
        clock.setListener(this);
    }
    
    private void init(){
        this.board = new GoBoard(19);
        this.tree = new GameTree();
        
        this.moveSoundEnable = true;
        
        this.listenerList = new ArrayList<GoGameListener>();
        
        this.state = GoState.EDIT;
    }

    public void connectObject(){
        tree.addGameTreeListener(this);
        
        window.addComponentListener(this);
    }
    
    public void showDoScoringDialog(){
        JOptionPane.showMessageDialog(null, DO_SCORING);
    }
    
    public void showResignDialog(GoColor winner){
        if(winner == GoColor.BLACK){
            JOptionPane.showMessageDialog(null, BLACK + RESULT_RESIGN);
        }else{
            JOptionPane.showMessageDialog(null, WHITE + RESULT_RESIGN);
        }
    }
    
    public void showResultDialog(String str){
        JOptionPane.showMessageDialog(null, str);
    }
    
    public GoBoard getBoard(){
        return board;
    }
    
    private void updateGoBoard(){
        board.setBoardState(tree);
    }
    
    private void adjustClock(MoveProperty p, GoColor color){
        if(p.hasTimingProperty(color)){
            double sec = p.getTimeLeft(color);
            int stone = p.getStones(color);
            
            clock.adjust(color, sec, stone);
        }
    }
    
    private void updateClock(){
        if(tree.getCurrentNode().isMoveNode()){
            MoveProperty mp = tree.getCurrentNode().getMoveProperty();
            adjustClock(mp, GoColor.BLACK);
            adjustClock(mp, GoColor.WHITE);
        }
    }
    
    public GameTree getGameTree(){
        return tree;
    }
    
    public void setWindow(JFrame window){
        this.window = window;
    }
    
    public JFrame getWindow(){
        return window;
    }
    
    public void setOperationPanel(OperationPanel panel){
        this.opePane = panel;
    }
    
    public void addGoGameListener(GoGameListener listener){
        listenerList.add(listener);
    }
    
    public GoClock getClock(){
        return clock;
    }
    
    public void setClock(GoClock newClock){
        if(newClock == null){
            clock.stop();
            clock.shutdown();
            clock = new GoClock();
        }else{
            clock = newClock;
            clock.setListener(this);
        }
    }
    
    public void start(){
        window.setVisible(true);
        
        this.treeChanged(tree, tree.getBoardSize());
        
        start(this);
    }
    
    public GoState getGoState(){
        return state;
    }
    
    public void setGoState(GoState newState){
        GoState oldState = state;
        this.state = newState;

    }
    
    public void start(GoGame goGame){
    }
    public void next(GoGame goGame){
    }
    public void stop(){
    }
    
    public void setMoveSoundEnabled(boolean b){
        moveSoundEnable = b;
    }
    
    public boolean isMoveSoundEnabled(){
        return moveSoundEnable;
    }
    
    /**********************/
    
    public void clockChanged(GoClock clock) {
        for(GoGameListener l : listenerList){
            l.updateClock(clock);
        }
    }

    public void clockSoundTiming(GoClock clock) {
        App.getInstance().soundPlay(SoundType.TIME);
    }

    public void clockTimeOver(GoClock clock, GoColor color) {
        //
    }

    public void treeChanged(GameTree tree, int boardSize) {
        updateGoBoard();
        updateClock();
        for(GoGameListener l : listenerList){
            l.treeChanged(this, boardSize);
        }
    }
    
    public void nodeMoved(GameTree tree, GoNode old) {
        updateGoBoard();
        updateClock();
        for(GoGameListener l : listenerList){
            l.nodeMoved(this, old);
        }
        next(this);
    }
    
    public void nodeStateChanged(GameTree tree) {
        updateGoBoard();
        updateClock();
        for(GoGameListener l : listenerList){
            l.nodeStateChanged(this);
        }
        next(this);
    }
    
    public void goStateChanged(GoState state){
        System.out.println("goStateChanged:" + state);
        for(GoGameListener l : listenerList){
            l.goStateChanged(this, state);
        }
    }
    
    /**********************/
    
    public void componentHidden(ComponentEvent e) {
        if(e.getSource() == window){
            System.out.println("GoGame.componentHidden: go window");
            if(owner != null){
                owner.removeComponentListener(this);
            }
        }else if(e.getSource() == owner){
            // 親が閉じられた
            System.out.println("GoGame.componentHidden: main window");
            window.setVisible(false);
        }
        
        stop();
        clock.stop();
        clock.shutdown();
    }
    public void componentMoved(ComponentEvent e) {}
    public void componentResized(ComponentEvent e) {}
    public void componentShown(ComponentEvent e) {}
}