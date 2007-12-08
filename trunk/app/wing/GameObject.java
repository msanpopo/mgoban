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

import go.GoColor;
import go.GoMove;
import go.GoVertex;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import go.gui.InputListener;
import go.GoState;
import go.Handicap;
import javax.swing.JOptionPane;
import sgf.GameTree;
import sgf.GoNode;
import sgf.property.GameInfoProperty;
import sgf.property.SetupProperty;
import wing.Game;

import wing.MessageReceiver;
import wing.WingMove;
import wing.message.AdjournRequestMessage;
import wing.message.GamesMessage;
import wing.message.KibitzMessage;
import wing.message.Message;
import wing.message.MoveMessage;
import wing.message.RemoveVertexMessage;
import wing.message.ResultAdjournMessage;
import wing.message.ResultForfeitMessage;
import wing.message.ResultResignMessage;
import wing.message.SayMessage;
import wing.message.ScoreModeMessage;
import wing.message.StatusMessage;

public class GameObject implements  InputListener, ComponentListener, MessageReceiver{
    private static final String ADJOURN_REQUEST = "相手が中断を要請しています。\n中断しますか？";
    
    private static final int TYPE_GAME = 0;
    private static final int TYPE_OBSERVE = 1;
    
    private NetGo netGo;
    
    private Game game;
    private int gameNo;
    private String blackName;
    private String whiteName;
    
    private GameObjectManager gameObjectManager;
    
    private String myName;
    
    private int type;
    
    private NetGoGame goGame;
    private WingGoWindow gw;
    
    private NetPlayer blackPlayer, whitePlayer;
    
    private int lastMoveNo; // 直前に受けた着手の番号（受ける前は -1)
    private ArrayList<WingMove> moveTmp;
    
    private boolean initialized;	// game 取得済なら true
    
    public GameObject(NetGo netGo, int gameNo, String bName, String wName, GameObjectManager gom, String myName){
        this.netGo = netGo;
        
        this.game = null;
        this.gameNo = gameNo;
        this.blackName = bName;
        this.whiteName = wName;
        
        this.gameObjectManager = gom;
        
        this.myName = myName;
        
        this.initialized = false;
        
        netGo.getServer().addReceiver(this);
        netGo.sendCommand("games " + gameNo);
    }
    
    // 対局を「開く」した時
    public GameObject(NetGo netGo, Game game, GameObjectManager gom, String myName){
        this.netGo = netGo;
        
        this.game = game;
        this.gameNo = game.getGameNo();
        this.blackName = game.getBlackName();
        this.whiteName = game.getWhiteName();
        
        this.gameObjectManager = gom;
        
        this.myName = myName;
        
        this.initialized = false;
        
        netGo.getServer().addReceiver(this);
        
        setGame(game);
    }
    
    private void setGame(Game game){
        this.game = game;
        this.initialized = true;
        
        int boardSize = game.getBoardSize();
        int handicap = game.getHandicap();
        double komi = game.getKomi();
        int byoYomiSec = game.getByoYomiTime();
        
        goGame = new NetGoGame(netGo.getWindow(), 0.0, byoYomiSec * 60, 25, 0.0);
        
        gw = new WingGoWindow(goGame);
        gw.addComponentListener(this);
        gw.setInputListener(this);
        
        System.out.println("GameObject:myName:" + myName);
        
        type = TYPE_OBSERVE;
        if(myName.equals(blackName)){
            type = TYPE_GAME;
            blackPlayer = new WingHumanPlayer(GoColor.BLACK, this, goGame.getBoard(), gw);
        }else{
            blackPlayer = new WingServerPlayer(GoColor.BLACK, gw);
        }
        if(myName.equals(whiteName)){
            type = TYPE_GAME;
            whitePlayer = new WingHumanPlayer(GoColor.WHITE, this, goGame.getBoard(), gw);
        }else{
            whitePlayer = new WingServerPlayer(GoColor.WHITE, gw);
        }
        
        if(type == TYPE_OBSERVE){
            gw.setInputList(netGo.getKibitzInputList());
        }else{
            gw.setInputList(netGo.getSayInputList());
        }
        
        lastMoveNo = -1;
        moveTmp = new ArrayList<WingMove>();
        
        GoNode root = GoNode.createRootNode(boardSize);

        GameInfoProperty gip = new GameInfoProperty();
        root.setGameInfoProperty(gip);
        
        gip.setKomi(komi);
        // ハンディキャップはゼロ手目で送られてくる handicap 2 とかのメッセージを受けてから追加する
        
        gip.setPlayerName(GoColor.BLACK, blackName);
        gip.setPlayerName(GoColor.WHITE, whiteName);
        gip.setPlayerRank(GoColor.BLACK, game.getBlackRank().toString());
        gip.setPlayerRank(GoColor.WHITE, game.getWhiteRank().toString());

        GameTree tree = goGame.getGameTree();
        tree.setNewTree(root);
        
        goGame.setPlayer(blackPlayer, whitePlayer);
        goGame.setWindow(gw);
        goGame.setOperationPanel(gw);
        goGame.addGoGameListener(gw);
        goGame.connectObject();
        goGame.start();
        
        if(type == TYPE_OBSERVE){
            netGo.sendCommand("observe " + game.getGameNo());
        }
        netGo.sendCommand("moves " + game.getGameNo());
    }
    
    public int getGameNo(){
        return gameNo;
    }
    
    private void setHandicap(int handicap){
        GoNode root = goGame.getGameTree().getTopNode();
        GameInfoProperty gip = root.getGameInfoProperty();
        gip.setHandicap(handicap);

        SetupProperty sp = null;
        if(handicap != 0){
            sp = new SetupProperty();
            int boardSize = goGame.getGameTree().getBoardSize();
            GoVertex[] fixedHandicap = Handicap.getGtpVertex(boardSize, handicap);
            for (GoVertex v : fixedHandicap) {
                sp.addBlack(v);
            }
        }
        root.setSetupProperty(sp);
        
        root.clearBoardState();
        game.setHandicap(handicap);
        
        // ここにくるのは、move コマンドのレスポンスを受けたときか、置き碁の一手目 undo の時
        goGame.getGameTree().top();
    }
    
    public void receive(Message wm) {
        //System.out.println("GameObject:" + wm.toString());
        
        if(initialized == false){
            if(wm instanceof GamesMessage){
                GamesMessage m = (GamesMessage)wm;
                
                if(m.size() == 1){
                    Game g = m.first();
                    
                    System.out.println("GameObject:games received:" + g.toString());
                    
                    if(g.getGameNo() == gameNo && g.getBlackName().equals(blackName) && g.getWhiteName().equals(whiteName)){
                        setGame(g);
                    }
                }
            }
        }else{
            if(wm instanceof MoveMessage){
                MoveMessage m = (MoveMessage)wm;
                if(m.getGameNo() == gameNo){
                    int bSec = m.getBlackSec();
                    int bMove = m.getBlackMove();
                    int wSec = m.getWhiteSec();
                    int wMove = m.getWhiteMove();
                    
                    System.out.println("GameObject:" + m.toString());
                    
                    goGame.getClock().stop();
                    goGame.getClock().adjust(GoColor.BLACK, bSec, bMove);
                    goGame.getClock().adjust(GoColor.WHITE, wSec, wMove);
                    
                    ArrayList<WingMove> moveList = m.getWingMoveList();
                    if(moveList.size() == 0){
                        // wing で次の場合に着手のない時間情報だけのメッセージがくる
                        // ようするに一手も打ってないし、置き石も置かれてない状態になるようだ。
                        // ・wing の互先で 1手目 undo の時
                        // ・ 一手も打ってない状態で moves コマンドを送信した時
                        // ・ 置き碁の一手目を打つ前に undo した時（ fmatch だと置き石も無効になる。match がどうなるかはテスト未）
                        // (参考)wing の置き石ありの時の 1手目の白の着手 undo の時、handicap の着手がくる

                        lastMoveNo = -1;
                        setHandicap(0);
              
                    }else{
                        goGame.setMoveSoundEnabled(false);
                        
                        WingMove last = moveList.get(moveList.size() - 1);
                        for(WingMove wingMove : moveList){
                            if(wingMove == last){
                                goGame.setMoveSoundEnabled(true);
                            }
                            
                            int wingMoveType = wingMove.getType();
                            
                            if(wingMoveType == WingMove.DUMMY){ // nngs 用
                                lastMoveNo = -1;
                                setHandicap(0);
                                
                            }else if(wingMoveType == WingMove.HANDICAP){
                                // wing の置き石ありの白の一手目を undo すると handicap のメッセージが帰る。
                                // その時、着手がきたことを知らせないと画面が更新されずに白の着手が画面上に残る。
                                lastMoveNo = 0;
                                int newHandicap = wingMove.getHandicap(); 
                                setHandicap(newHandicap);
                            }else{
                                int moveNo = wingMove.getMoveNo();
                                
                                if (moveTmp.size() > 0 && moveTmp.get(0).getMoveNo() == moveNo) {
                                    // 保存してある着手の先頭に同じ番号の着手があったら、そちらを使う
                                    WingMove tmp = moveTmp.remove(0);
                                    System.out.println("LOAD ##########:" + tmp.getMoveNo() + ":(" + tmp.getColor() + "):" + tmp.getMove());
                                    
                                    checkMove(tmp);
                                }else{
                                    checkMove(wingMove);
                                }
                            }
                        }
                    }
                    
                    // 保存している着手列が直近にきた着手の続きなら処理する
                    if (moveTmp.size() > 0 && moveTmp.get(0).getMoveNo() == lastMoveNo + 1) {
                        while(moveTmp.size() > 0){
                            WingMove tmp = moveTmp.remove(0);
                            System.out.println("LOAD ##########:" + tmp.getMoveNo() + ":(" + tmp.getColor() + "):" + tmp.getMove());
                            
                            checkMove(tmp);
                        }
                    }
                }
            }else if(wm instanceof KibitzMessage){
                KibitzMessage m = (KibitzMessage)wm;
                if(m.getGameNo() == game.getGameNo()){
                    gw.addText(m.getText());
                }
                
            }else if(wm instanceof SayMessage){
                SayMessage m = (SayMessage)wm;
                gw.addText(m.getText());
                
            }else if(wm instanceof StatusMessage){
                StatusMessage m = (StatusMessage)wm;
                
                if(m.getBlackName().equals(game.getBlackName()) && m.getWhiteName().equals(game.getWhiteName())){
                    System.out.println("GameObject.status");
                    if(goGame.getGoState() != GoState.SCORE){
                        if(type == TYPE_GAME){
                            goGame.showDoScoringDialog();
                        }
                        goGame.setGoState(GoState.SCORE);
                    }
                    goGame.getBoard().setResultBoard(m.getBoard());
                    
                    blackPlayer.done();
                    //whitePlayer.done();
                }
                
            }else if(wm instanceof ScoreModeMessage){
                ScoreModeMessage m = (ScoreModeMessage)wm;
                
                System.out.println("GameObject: score mode:");
                if(type == TYPE_GAME){
                    goGame.showDoScoringDialog();
                }
                goGame.setGoState(GoState.SCORE);
                
            }else if(wm instanceof RemoveVertexMessage){
                RemoveVertexMessage m = (RemoveVertexMessage)wm;
                System.out.println("GameObject: RmoveVertexMessage:" + m.toString());
                
                String vertexString = m.getVertexString();
                GoVertex vertex = new GoVertex(game.getBoardSize(), vertexString);
                blackPlayer.removeDeadStoneGroup(vertex);
                whitePlayer.removeDeadStoneGroup(vertex);
                
            }else if(wm instanceof AdjournRequestMessage){
                AdjournRequestMessage m = (AdjournRequestMessage)wm;
                System.out.println("GameObject: adjourn request:");
                
                int retval = JOptionPane.showConfirmDialog(gw, ADJOURN_REQUEST, "", JOptionPane.YES_NO_OPTION);
                if(retval == JOptionPane.YES_OPTION){
                    sendCommand("adjourn");
                }else{
                    // 何もしない。nngs でテストしたら decline を送っても相手には何のメッセージも返らないので放っとけばいいようだ。
                }
                
            }else if(wm instanceof ResultForfeitMessage){
                ResultForfeitMessage m = (ResultForfeitMessage)wm;
                
                goGame.getClock().stop();
                goGame.setGoState(GoState.EDIT);
                
                if(m.getGameNo() == game.getGameNo()){
                    System.out.println("GameObject: ResultForfeitMessage detected");
                    String text;
                    if(m.getColor() == GoColor.WHITE){
                        text = "黒の勝ち（白の時間切れ）";
                    }else{
                        text = "白の勝ち（黒の時間切れ）";
                    }
                    JOptionPane.showMessageDialog(null, text);
                }
                
            }else if(wm instanceof ResultResignMessage){
                ResultResignMessage m = (ResultResignMessage)wm;
                
                if(m.getGameNo() == game.getGameNo()){
                    GoColor color = m.getColor();
                    if(color == GoColor.BLACK){
                        blackPlayer.resign();
                    }else if(color == GoColor.WHITE){
                        whitePlayer.resign();
                    }else{
                        System.err.println("GameObject:resign ??:" + m.toString());
                    }
                }
                
            }else if(wm instanceof ResultAdjournMessage){
                ResultAdjournMessage m = (ResultAdjournMessage)wm;
                
                if(m.getGameNo() == game.getGameNo() && goGame.getGoState() == GoState.NET_GAME){
                    /*
                     * GoState.NET_GAME から変えない。
                     * 回線が不安定で切れたり戻ったりを繰り返す相手の場合に、中断して NET_GAME モードから落ちてしまうと
                     * 再開できないから。 
                     */
                    //goGame.setGoState(GoState.EDIT);
                    
                    String ADJOURNED = "中断しました。";
                    JOptionPane.showMessageDialog(null, ADJOURNED);
                }
                
                /*
                 * wing で statusMessage よりも前でくるので、最終盤面の前に結果を計算してそれを表示結果に使ってしまうので消す
            }else if(wm instanceof ResultNormalMessage){
                ResultNormalMessage m = (ResultNormalMessage)wm;
                 
                if(m.getGameNo() == game.getGameNo()){
                    System.out.println("GameObject:normal:" + m.toString());
                    blackPlayer.done();
                    whitePlayer.done();
                }
                 **/
            }
        }
    }
    
    private void checkMove(WingMove wingMove) {
        System.out.println("GameObject.checkMove:" + wingMove.toString());
        
        int newMoveNo = wingMove.getMoveNo();
        GoColor color = wingMove.getColor();
        
        NetPlayer player;
        if (color == GoColor.BLACK) {
            player = blackPlayer;
        } else {
            player = whitePlayer;
        }
        
        if (newMoveNo == lastMoveNo + 1) {
            lastMoveNo = newMoveNo;
            
            GoMove goMove = new GoMove(goGame.getGameTree().getBoardSize(), color, wingMove.getMove());
            int realNo;
            if(game.getHandicap() == 0){
                realNo = lastMoveNo + 1;
            }else{
                realNo = lastMoveNo;
            }
            player.move(goMove, realNo);
            
        }else if(newMoveNo < lastMoveNo + 1) {  // undo
            if(goGame.getGoState() == GoState.SCORE){
                goGame.setGoState(GoState.NET_GAME);
            }
            lastMoveNo = newMoveNo;
            
            GoMove goMove = new GoMove(goGame.getGameTree().getBoardSize(), color, wingMove.getMove());
            int realNo;
            if(game.getHandicap() == 0){
                realNo = lastMoveNo + 1;
            }else{
                realNo = lastMoveNo;
            }
            player.move(goMove, realNo);
            
        } else {	// n > lastMoveNo + 1
            // moves の結果を受ける以前にその時点での着手を受けるとここにくる。
            // moves の結果を全て受け取った後でそれらの着手を処理するので、それまで処理せずに保存しておく。
            System.out.println("SAVE ##########:" + wingMove.toString());
            
            moveTmp.add(wingMove);
        }
    }
    
    public void textInputed(Object source, String text) {
        System.out.println("textInputed : " + text);
        if(type == TYPE_GAME){
            gw.addText(myName + " : " + text);
            netGo.sendCommand("say " + text);
        }else if(type == TYPE_OBSERVE){
            netGo.sendCommand("kibitz " + gameNo + " " + text);
        }else{
            
        }
    }
    
    public void sendCommand(String command){
        netGo.sendCommand(command);
    }
    
    public void sendCommandMove(GoMove move, int sec){
        StringBuilder str = new StringBuilder();
        
        str.append(move.getVertex().toGtpString(goGame.getGameTree().getBoardSize()));
        str.append(" ");
        str.append(Integer.toString(sec));
        
        netGo.sendCommand(str.toString());
    }
    
    public void sendCommandVertex(GoVertex vertex){
        netGo.sendCommand(vertex.toGtpString(goGame.getGameTree().getBoardSize()));
    }
    
    // ComponentListener
    public void componentHidden(ComponentEvent e) {
        if(type == TYPE_GAME){
            // 何もしない
        }else if(type == TYPE_OBSERVE){
            netGo.sendCommand("unobserve " + game.getGameNo());
        }
        
        gameObjectManager.removeGameObject(this);
        netGo.getServer().removeReceiver(this);
        gw = null;
    }
    public void componentMoved(ComponentEvent e) {}
    public void componentResized(ComponentEvent e) {}
    public void componentShown(ComponentEvent e) {}
}