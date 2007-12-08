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

import app.App;
import app.Config;
import app.GoConfig;
import go.board.gui.BoardPanel;
import go.board.gui.BoardOperation;
import go.gui.InOutPanel;
import go.gui.InputList;
import go.gui.InputListener;
import go.gui.PlayerInfoPanel;
import go.gui.GoOperator;
import go.gui.OperationPanel;
import go.GoClock;
import go.GoColor;
import go.GoGame;
import go.GoGameListener;
import go.GoMove;
import go.GoState;
import java.awt.BorderLayout;
import javax.swing.JOptionPane;
import sgf.GameTree;
import sgf.GoNode;
import sgf.property.MoveProperty;

public class WingGoWindow extends javax.swing.JFrame implements GoGameListener, OperationPanel{
    private static final String FILE = "ファイル";
    private static final String CLOSE = "閉じる";
    
    private static final String PASS = "パス";
    private static final String RESIGN = "投了";
    private static final String UNDO = "戻す";
    private static final String DONE = "終了";
    private static final String ADJOURN = "中断";
    
    private static final String RESIGN_QUESTION = "投了しますか？";
    
    private BoardPanel board;
    private PlayerInfoPanel bPlayerInfo;
    private PlayerInfoPanel wPlayerInfo;
    
    private InOutPanel ioPanel;
    
    private GoOperator operator;
    private GoGame goGame;
    
    public WingGoWindow(GoGame goGame) {
        this.goGame = goGame;
        
        operator = null;
        
        initComponents();
        
        Config config = App.getInstance().getConfig();
        int w = config.getIntProperty(GoConfig.GO_WINDOW_WIDTH);
        int h = config.getIntProperty(GoConfig.GO_WINDOW_HEIGHT);
        setSize(w, h);
        
        int hDivider = config.getIntProperty(GoConfig.GO_WINDOW_H_DIVIDER_LOCATION);
        hSplitPane.setDividerLocation(hDivider);
        
        fileMenu.setText(FILE);
        
        closeMenuItem.setText(CLOSE);
        
        passButton.setText(PASS);
        resignButton.setText(RESIGN);
        undoButton.setText(UNDO);
        doneButton.setText(DONE);
        adjournButton.setText(ADJOURN);
        
        board = new BoardPanel(goGame.getBoard());
        boardPanelBase.setLayout(new BorderLayout());
        boardPanelBase.add(board, BorderLayout.CENTER);
        
        bPlayerInfo = new PlayerInfoPanel(GoColor.BLACK);
        wPlayerInfo = new PlayerInfoPanel(GoColor.WHITE);
        ioPanel = new InOutPanel();
        
        blackPlayerInfoBase.setLayout(new BorderLayout());
        blackPlayerInfoBase.add(bPlayerInfo, BorderLayout.CENTER);
        
        whitePlayerInfoBase.setLayout(new BorderLayout());
        whitePlayerInfoBase.add(wPlayerInfo, BorderLayout.CENTER);
        
        ioPanelBase.setLayout(new BorderLayout());
        ioPanelBase.add(ioPanel, BorderLayout.CENTER);
    }
    
    public void setInputList(InputList list){
        ioPanel.setInputList(list);
    }
    
    public void setInputListener(InputListener listener){
        ioPanel.setInputListener(listener);
    }
    
    public void addText(String s){
        ioPanel.addText(s);
    }
    
    public void setOperator(GoOperator operator, BoardOperation operation){
        board.setOperation(operation);
        
        this.operator = operator;
        
        operatorChanged(goGame);
    }
    
    private void setMoveLabel(GameTree tree){
        GoNode node = tree.getCurrentNode();
        
        if(node.hasMoveProperty()){
            MoveProperty mp = node.getMoveProperty();
            GoMove move = mp.getMove();
            
            int n = node.getMoveNumber();
            
            String v = move.toGtpString(tree.getBoardSize()).toUpperCase();
            
            StringBuilder str = new StringBuilder();
            str.append("Move ").append(n).append("  :  ").append(v);
            moveInfoLabel.setText(str.toString());
            
        }else{
            moveInfoLabel.setText("");
        }
    }
    
    private void operatorChanged(GoGame goGame){
        GoState state = goGame.getGoState();
        if(operator == null || state == GoState.EDIT){
            passButton.setEnabled(false);
            resignButton.setEnabled(false);
            undoButton.setEnabled(false);
            doneButton.setEnabled(false);
            adjournButton.setEnabled(false);
        }else{
            if(state == GoState.NET_GAME){
                passButton.setEnabled(true);
                resignButton.setEnabled(true);
                if(goGame.getGameTree().getCurrentNode().isRoot()){
                    undoButton.setEnabled(false);
                }else{
                    undoButton.setEnabled(true);
                }
                doneButton.setEnabled(false);
                adjournButton.setEnabled(true);
            }else if(state == GoState.SCORE){
                passButton.setEnabled(false);
                resignButton.setEnabled(true);
                undoButton.setEnabled(true);
                doneButton.setEnabled(true);
                adjournButton.setEnabled(false);
            }
        }
    }
    
    public void updateClock(GoClock clock){
        bPlayerInfo.updateClock(clock);
        wPlayerInfo.updateClock(clock);
    }
    
    public void treeChanged(GoGame goGame, int boardSize) {
        GameTree tree = goGame.getGameTree();
        
        bPlayerInfo.treeChanged(goGame, boardSize);
        wPlayerInfo.treeChanged(goGame, boardSize);
        
        setMoveLabel(tree);
    }
    
    public void nodeMoved(GoGame goGame, GoNode old) {
        GameTree tree = goGame.getGameTree();
        
        bPlayerInfo.nodeMoved(goGame, old);
        wPlayerInfo.nodeMoved(goGame, old);
        
        operatorChanged(goGame);
        setMoveLabel(tree);
    }
    
    public void nodeStateChanged(GoGame goGame) {
        GameTree tree = goGame.getGameTree();
        
        bPlayerInfo.nodeStateChanged(goGame);
        wPlayerInfo.nodeStateChanged(goGame);
        
        operatorChanged(goGame);
        setMoveLabel(tree);
    }
    
    public void goStateChanged(GoGame goGame, GoState state){
        operatorChanged(goGame);
        
        bPlayerInfo.goStateChanged(goGame, state);
        wPlayerInfo.goStateChanged(goGame, state);
    }
    
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        hSplitPane = new javax.swing.JSplitPane();
        boardPanelBase = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        whitePlayerInfoBase = new javax.swing.JPanel();
        blackPlayerInfoBase = new javax.swing.JPanel();
        moveInfoPanel = new javax.swing.JPanel();
        moveInfoLabel = new javax.swing.JLabel();
        ioPanelBase = new javax.swing.JPanel();
        passButton = new javax.swing.JButton();
        resignButton = new javax.swing.JButton();
        doneButton = new javax.swing.JButton();
        undoButton = new javax.swing.JButton();
        adjournButton = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        closeMenuItem = new javax.swing.JMenuItem();

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 226, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 444, Short.MAX_VALUE)
        );

        setLocationByPlatform(true);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                formComponentHidden(evt);
            }
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        hSplitPane.setBorder(null);
        hSplitPane.setDividerLocation(600);
        hSplitPane.setDividerSize(5);

        boardPanelBase.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        boardPanelBase.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                boardPanelBaseComponentResized(evt);
            }
        });

        javax.swing.GroupLayout boardPanelBaseLayout = new javax.swing.GroupLayout(boardPanelBase);
        boardPanelBase.setLayout(boardPanelBaseLayout);
        boardPanelBaseLayout.setHorizontalGroup(
            boardPanelBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 596, Short.MAX_VALUE)
        );
        boardPanelBaseLayout.setVerticalGroup(
            boardPanelBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 562, Short.MAX_VALUE)
        );

        hSplitPane.setLeftComponent(boardPanelBase);

        whitePlayerInfoBase.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout whitePlayerInfoBaseLayout = new javax.swing.GroupLayout(whitePlayerInfoBase);
        whitePlayerInfoBase.setLayout(whitePlayerInfoBaseLayout);
        whitePlayerInfoBaseLayout.setHorizontalGroup(
            whitePlayerInfoBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 248, Short.MAX_VALUE)
        );
        whitePlayerInfoBaseLayout.setVerticalGroup(
            whitePlayerInfoBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 86, Short.MAX_VALUE)
        );

        blackPlayerInfoBase.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout blackPlayerInfoBaseLayout = new javax.swing.GroupLayout(blackPlayerInfoBase);
        blackPlayerInfoBase.setLayout(blackPlayerInfoBaseLayout);
        blackPlayerInfoBaseLayout.setHorizontalGroup(
            blackPlayerInfoBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 248, Short.MAX_VALUE)
        );
        blackPlayerInfoBaseLayout.setVerticalGroup(
            blackPlayerInfoBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 82, Short.MAX_VALUE)
        );

        moveInfoPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        moveInfoLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        moveInfoLabel.setText("jLabel1");

        javax.swing.GroupLayout moveInfoPanelLayout = new javax.swing.GroupLayout(moveInfoPanel);
        moveInfoPanel.setLayout(moveInfoPanelLayout);
        moveInfoPanelLayout.setHorizontalGroup(
            moveInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(moveInfoLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
        );
        moveInfoPanelLayout.setVerticalGroup(
            moveInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(moveInfoLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout ioPanelBaseLayout = new javax.swing.GroupLayout(ioPanelBase);
        ioPanelBase.setLayout(ioPanelBaseLayout);
        ioPanelBaseLayout.setHorizontalGroup(
            ioPanelBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 252, Short.MAX_VALUE)
        );
        ioPanelBaseLayout.setVerticalGroup(
            ioPanelBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 258, Short.MAX_VALUE)
        );

        passButton.setText("Pass");
        passButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passButtonActionPerformed(evt);
            }
        });

        resignButton.setText("Resign");
        resignButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resignButtonActionPerformed(evt);
            }
        });

        doneButton.setText("Done");
        doneButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doneButtonActionPerformed(evt);
            }
        });

        undoButton.setText("Undo");
        undoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                undoButtonActionPerformed(evt);
            }
        });

        adjournButton.setText("Adjourn");
        adjournButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adjournButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(whitePlayerInfoBase, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(blackPlayerInfoBase, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(moveInfoPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(passButton, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(doneButton, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                .addGap(34, 34, 34))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(resignButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(undoButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(adjournButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(ioPanelBase, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(whitePlayerInfoBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(blackPlayerInfoBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(moveInfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passButton)
                    .addComponent(doneButton))
                .addGap(24, 24, 24)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(resignButton)
                    .addComponent(undoButton)
                    .addComponent(adjournButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ioPanelBase, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        hSplitPane.setRightComponent(jPanel3);

        fileMenu.setText("File");

        closeMenuItem.setText("Close");
        closeMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(closeMenuItem);

        jMenuBar1.add(fileMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(hSplitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 728, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(hSplitPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 566, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void formComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentHidden
        System.out.println("GoWindow Hidden");
        
        dispose();
    }//GEN-LAST:event_formComponentHidden
    
    private void closeMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeMenuItemActionPerformed
        setVisible(false);
    }//GEN-LAST:event_closeMenuItemActionPerformed
    
    private void adjournButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adjournButtonActionPerformed
        if(operator != null){
            operator.adjournButtonPressed();
        }
    }//GEN-LAST:event_adjournButtonActionPerformed
    
    private void undoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_undoButtonActionPerformed
        if(operator != null){
            operator.undoButtonPressed();
        }
    }//GEN-LAST:event_undoButtonActionPerformed
    
    private void doneButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doneButtonActionPerformed
        if(operator != null){
            operator.doneButtonPressed();
        }
    }//GEN-LAST:event_doneButtonActionPerformed
    
    private void resignButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resignButtonActionPerformed
        if(operator != null){
            int retval = JOptionPane.showConfirmDialog(null, RESIGN_QUESTION, RESIGN, JOptionPane.YES_NO_OPTION);
            
            if(retval == JOptionPane.YES_OPTION){
                operator.resignButtonPressed();
            }
        }
    }//GEN-LAST:event_resignButtonActionPerformed
    
    private void passButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passButtonActionPerformed
        if(operator != null){
            operator.passButtonPressed();
        }
    }//GEN-LAST:event_passButtonActionPerformed

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        int w = getWidth();
        int h = getHeight();
        Config config = App.getInstance().getConfig();
        config.setIntProperty(GoConfig.GO_WINDOW_WIDTH, w);
        config.setIntProperty(GoConfig.GO_WINDOW_HEIGHT, h);
    }//GEN-LAST:event_formComponentResized

    private void boardPanelBaseComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_boardPanelBaseComponentResized
        int hDivider = hSplitPane.getDividerLocation();
        Config config = App.getInstance().getConfig();
        config.setIntProperty(GoConfig.GO_WINDOW_H_DIVIDER_LOCATION, hDivider);
    }//GEN-LAST:event_boardPanelBaseComponentResized
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton adjournButton;
    private javax.swing.JPanel blackPlayerInfoBase;
    private javax.swing.JPanel boardPanelBase;
    private javax.swing.JMenuItem closeMenuItem;
    private javax.swing.JButton doneButton;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JSplitPane hSplitPane;
    private javax.swing.JPanel ioPanelBase;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel moveInfoLabel;
    private javax.swing.JPanel moveInfoPanel;
    private javax.swing.JButton passButton;
    private javax.swing.JButton resignButton;
    private javax.swing.JButton undoButton;
    private javax.swing.JPanel whitePlayerInfoBase;
    // End of variables declaration//GEN-END:variables
}