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

import app.App;
import app.Config;
import app.GoConfig;
import go.board.gui.BoardPanel;
import go.board.gui.BoardOperation;
import go.gui.PlayerInfoPanel;
import go.gui.TreePanel;
import go.gui.GoOperator;
import go.gui.OperationPanel;
import go.GoClock;
import go.GoColor;
import go.GoGame;
import go.GoGameListener;
import go.GoMove;
import go.GoState;
import go.controller.GameController;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import sgf.GameTree;
import sgf.GoNode;
import sgf.property.MoveProperty;
import sgf.property.NoTypeProperty;

public class SgfGoWindow extends javax.swing.JFrame implements GoGameListener, OperationPanel, DocumentListener, ActionListener{

    private BoardPanel board;
    private PlayerInfoPanel bPlayerInfo;
    private PlayerInfoPanel wPlayerInfo;
    private TreePanel treePanel;
    
    private GoOperator operator;
    private GoGame goGame;
    
    private SgfActionList actionList;
    
    public SgfGoWindow(GoGame goGame, SgfActionList actionList) {
        this.operator = null;
        this.goGame = goGame;
        this.actionList = actionList;
        
        initComponents();
        
        Config config = App.getInstance().getConfig();
        int w = config.getIntProperty(GoConfig.GO_WINDOW_WIDTH);
        int h = config.getIntProperty(GoConfig.GO_WINDOW_HEIGHT);
        setSize(w, h);
        
        int hDivider = config.getIntProperty(GoConfig.GO_WINDOW_H_DIVIDER_LOCATION);
        hSplitPane.setDividerLocation(hDivider);
        
        int vDivider = config.getIntProperty(GoConfig.GO_WINDOW_V_DIVIDER_LOCATION);
        vSplitPane.setDividerLocation(vDivider);
        
        
        moveModeToggleButton.setSelected(true);
        
        board = new BoardPanel(goGame.getBoard());
        boardPanelBase.setLayout(new BorderLayout());
        boardPanelBase.add(board, BorderLayout.CENTER);
        
        bPlayerInfo = new PlayerInfoPanel(GoColor.BLACK);
        wPlayerInfo = new PlayerInfoPanel(GoColor.WHITE);
        
        blackPlayerInfoBase.setLayout(new BorderLayout());
        blackPlayerInfoBase.add(bPlayerInfo, BorderLayout.CENTER);
        
        whitePlayerInfoBase.setLayout(new BorderLayout());
        whitePlayerInfoBase.add(wPlayerInfo, BorderLayout.CENTER);
        
        treePanel = new TreePanel(goGame);
        treePanelBase.setLayout(new BorderLayout());
        treePanelBase.add(treePanel, BorderLayout.CENTER);
        
        setAction(newButton, actionList.newAction);
        setAction(openButton, actionList.openAction);
        setAction(saveButton, actionList.saveAction);
        
        setAction(deleteNodeButton, actionList.deleteNodeAction);
        setAction(insertNodeButton, actionList.insertNodeAfterAction);
        
        setAction(pivotToggleButton, actionList.pivotAction);
        setAction(topButton, actionList.goTopAction);
        setAction(parentButton, actionList.goParentAction);
        setAction(childButton, actionList.goChildAction);
        setAction(bottomButton, actionList.goBottomAction);
        setAction(prevButton, actionList.goPrevAction);
        setAction(nextButton, actionList.goNextAction);
        
        setAction(startButton, actionList.startAction);
        setAction(stopButton, actionList.stopAction);
        
        newMenuItem.setAction(actionList.newAction);
        openMenuItem.setAction(actionList.openAction);
        openClipboardMenuItem.setAction(actionList.openClipboardAction);
        saveMenuItem.setAction(actionList.saveAction);
        saveAsMenuItem.setAction(actionList.saveAsAction);
        saveImageMenuItem.setAction(actionList.exportImageAction);
        closeMenuItem.setAction(actionList.closeAction);
        
        editRootMenuItem.setAction(actionList.editRootAction);
        editMoveMenuItem.setAction(actionList.editMoveAction);
        editGameInfoMenuItem.setAction(actionList.editGameInfoAction);
        editNoTypeMenuItem.setAction(actionList.editNoTypeAction);
        editSetupMenuItem.setAction(actionList.editSetupAction);
        
        addGameInfoMenuItem.setAction(actionList.addGameInfoAction);
        addNoTypeMenuItem.setAction(actionList.addNoTypeAction);
        addSetupMenuItem.setAction(actionList.addSetupAction);
        
        deleteNodeMenuItem.setAction(actionList.deleteNodeAction);
        insertBeforeMenuItem.setAction(actionList.insertNodeBeforeAction);
        insertAfterMenuItem.setAction(actionList.insertNodeAfterAction);
        
        addTreeMenuItem.setAction(actionList.addRootNodeAction);
        
        topMenuItem.setAction(actionList.goTopAction);
        backwardMenuItem.setAction(actionList.goParentAction);
        forwardMenuItem.setAction(actionList.goChildAction);
        bottomMenuItem.setAction(actionList.goBottomAction);
        prevMenuItem.setAction(actionList.goPrevAction);
        nextMenuItem.setAction(actionList.goNextAction);
        
        startMenuItem.setAction(actionList.startAction);
        stopMenuItem.setAction(actionList.stopAction);
        
        nodeNameTextField.addActionListener(this);
        
        moveNumberCheckBoxMenuItem.setSelected(board.isShowMoveNumber());
        koCheckBoxMenuItem.setSelected(board.isShowKo());

    }
    
    private void setAction(AbstractButton button, Action action){
        button.setAction(action);
        button.setText("");
        
        //JComponent component = getRootPane();
        JComponent component = board;
        //InputMap inputMap = component.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        InputMap inputMap = component.getInputMap();
        ActionMap actionMap = component.getActionMap();
        KeyStroke keyStroke = (KeyStroke)action.getValue(Action.ACCELERATOR_KEY);
        if (keyStroke != null) {
            String name = (String)action.getValue(Action.NAME);
            inputMap.put(keyStroke, name);
            actionMap.put(name, action);
        }
    }
    
    public void setEditMoveController(GameController controller, BoardOperation operation){
        board.setOperation(operation);
        
        operatorChanged();
    }
    
    @Override
    public void setOperator(GoOperator operator, BoardOperation operation){
        board.setOperation(operation);
        
        this.operator = operator;
        
        operatorChanged();
    }
    
    private void setMoveLabel(GameTree tree){
        GoNode node = tree.getCurrentNode();
        
        if(node.hasMoveProperty()){
            MoveProperty mp = node.getMoveProperty();
            GoMove move = mp.getMove();
            
            int n = node.getMoveNumber();
            String color;
            if(move.getColor() == GoColor.BLACK){
                color = java.util.ResourceBundle.getBundle("app/resource/Resource").getString("Black");
            }else{
                color = java.util.ResourceBundle.getBundle("app/resource/Resource").getString("White");
            }
            String v;
            if(move.isPass()){
                v = java.util.ResourceBundle.getBundle("app/resource/Resource").getString("Pass");
            }else{
                v = move.getVertex().toGtpString(tree.getBoardSize()).toUpperCase();
            }

            StringBuilder str = new StringBuilder();
            str.append(n).append("  :  ").append(color).append(" ").append(v);
            if(mp.hasAnnotation()){
                str.append(" - ").append(mp.getAnnotation().getMessage());
            }
            moveInfoLabel.setText(str.toString());
            
        }else{
            moveInfoLabel.setText("");
        }
    }
    
    private void setNodeName(GoNode node){
        
        if(node.hasNoTypeProperty()){
            NoTypeProperty ntp = node.getNoTypeProperty();
            
            String name = ntp.getNodeName();
            nodeNameTextField.setText(name);
        }else{
            nodeNameTextField.setText("");
        }
    }
    
    private void setComment(GoNode node){
        textArea.getDocument().removeDocumentListener(this);

        textArea.setText("");
        
        if(node.hasNoTypeProperty()){
            NoTypeProperty ntp = node.getNoTypeProperty();
            
            String comment = ntp.getComment();
            textArea.setText(comment);
            textArea.setCaretPosition(0);
        }
        
        textArea.getDocument().addDocumentListener(this);
    }
    
    private void setPlayingLabel(GoState state){
        if(state == GoState.GTP_GAME){
            playingLabel.setEnabled(true);
        }else{
            playingLabel.setEnabled(false);
        }
    }

    private void setWindowTitle(GoGame goGame){
        String description = goGame.getGameTree().getGameDescription();

        StringBuilder title = new StringBuilder("mGoban");

        if(!description.isEmpty()){
            title.append("  :  ").append(description);
        }

        setTitle(title.toString());
    }

    private void operatorChanged(){
        GoState state = goGame.getGoState();
        
        if(operator == null){
            passButton.setEnabled(false);
            undoButton.setEnabled(false);
            scoreButton.setEnabled(false);
            doneButton.setEnabled(false);
        }else{
            if(state == GoState.EDIT){
                passButton.setEnabled(true);
                undoButton.setEnabled(true);
                scoreButton.setEnabled(true);
                doneButton.setEnabled(false);
            }else if(state == GoState.GTP_GAME){
                passButton.setEnabled(true);
                undoButton.setEnabled(true);
                scoreButton.setEnabled(false);
                doneButton.setEnabled(false);
            }else if(state == GoState.SCORE){
                passButton.setEnabled(false);
                undoButton.setEnabled(false);
                scoreButton.setEnabled(false);
                doneButton.setEnabled(true);
            }
        }
    }
    
    @Override
    public void updateClock(GoClock clock){
        bPlayerInfo.updateClock(clock);
        wPlayerInfo.updateClock(clock);
    }
    
    @Override
    public void treeChanged(GoGame goGame, int boardSize) {
        GameTree tree = goGame.getGameTree();
        GoNode current = tree.getCurrentNode();
        
        bPlayerInfo.treeChanged(goGame, boardSize);
        wPlayerInfo.treeChanged(goGame, boardSize);
        
        setMoveLabel(tree);
        setNodeName(current);
        setComment(current);
        
        treePanel.update();
        
        GoState state = goGame.getGoState();
        
        operatorChanged();
        setPlayingLabel(state);
    }
    
    @Override
    public void nodeMoved(GoGame goGame, GoNode old) {
        GameTree tree = goGame.getGameTree();
        GoNode current = tree.getCurrentNode();
        
        bPlayerInfo.nodeMoved(goGame, old);
        wPlayerInfo.nodeMoved(goGame, old);
        
        setMoveLabel(tree);
        setNodeName(current);
        setComment(current);
        setWindowTitle(goGame);
        
        treePanel.update();
    }
    
    @Override
    public void nodeStateChanged(GoGame goGame) {
        GameTree tree = goGame.getGameTree();
        GoNode current = tree.getCurrentNode();
        
        bPlayerInfo.nodeStateChanged(goGame);
        wPlayerInfo.nodeStateChanged(goGame);
        
        setMoveLabel(tree);
        setNodeName(current);
        setComment(current);
        
        treePanel.update();
    }
    
    @Override
    public void goStateChanged(GoGame goGame, GoState state){
        operatorChanged();
        
        bPlayerInfo.goStateChanged(goGame, state);
        wPlayerInfo.goStateChanged(goGame, state);
        
        if(state == GoState.EDIT && moveModeToggleButton.isSelected() == false){
            moveModeToggleButton.setSelected(true);
        }
        
        setPlayingLabel(state);
        
        if(state == GoState.GTP_GAME){
            board.setGameMove(true);
        }else{
            board.setGameMove(false);
        }
            
    }
    
    @Override
    public void insertUpdate(DocumentEvent e) {
        commentChanged();
    }
    
    @Override
    public void removeUpdate(DocumentEvent e) {
        commentChanged();
    }
    
    @Override
    public void changedUpdate(DocumentEvent e) {}
    
    private void commentChanged(){
        System.out.println("SgfGoWindow.commentChanged");
        
        GoNode current = goGame.getGameTree().getCurrentNode();
        NoTypeProperty ntp;
        String comment = textArea.getText();
        
        if(current.hasNoTypeProperty()){
            ntp = current.getNoTypeProperty();
        }else{
            ntp = new NoTypeProperty();
            current.setNoTypeProperty(ntp);
        }
        
        ntp.setComment(comment);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == nodeNameTextField){
            System.out.println("SgfGoWindow.nodeNameChanged:" + nodeNameTextField.getText());
            
            GoNode current = goGame.getGameTree().getCurrentNode();
            NoTypeProperty ntp;
            String nodeName = nodeNameTextField.getText();
            
            if(current.hasNoTypeProperty()){
                ntp = current.getNoTypeProperty();
            }else{
                ntp = new NoTypeProperty();
                current.setNoTypeProperty(ntp);
            }
            
            ntp.setNodeName(nodeName);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup = new javax.swing.ButtonGroup();
        hSplitPane = new javax.swing.JSplitPane();
        boardPanelBase = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        whitePlayerInfoBase = new javax.swing.JPanel();
        blackPlayerInfoBase = new javax.swing.JPanel();
        moveInfoPanel = new javax.swing.JPanel();
        moveInfoLabel = new javax.swing.JLabel();
        undoButton = new javax.swing.JButton();
        passButton = new javax.swing.JButton();
        scoreButton = new javax.swing.JButton();
        doneButton = new javax.swing.JButton();
        nodeNameTextField = new javax.swing.JTextField();
        vSplitPane = new javax.swing.JSplitPane();
        treePanelBase = new javax.swing.JPanel();
        scrollPane = new javax.swing.JScrollPane();
        textArea = new javax.swing.JTextArea();
        jToolBar2 = new javax.swing.JToolBar();
        newButton = new javax.swing.JButton();
        openButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        jToolBar1 = new javax.swing.JToolBar();
        pivotToggleButton = new javax.swing.JToggleButton();
        jSeparator5 = new javax.swing.JSeparator();
        topButton = new javax.swing.JButton();
        parentButton = new javax.swing.JButton();
        childButton = new javax.swing.JButton();
        bottomButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        prevButton = new javax.swing.JButton();
        nextButton = new javax.swing.JButton();
        jToolBar3 = new javax.swing.JToolBar();
        deleteNodeButton = new javax.swing.JButton();
        insertNodeButton = new javax.swing.JButton();
        jToolBar4 = new javax.swing.JToolBar();
        playingLabel = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        startButton = new javax.swing.JButton();
        stopButton = new javax.swing.JButton();
        jToolBar5 = new javax.swing.JToolBar();
        moveModeToggleButton = new javax.swing.JToggleButton();
        setupModeToggleButton = new javax.swing.JToggleButton();
        markModeToggleButton = new javax.swing.JToggleButton();
        charLabelModeToggleButton = new javax.swing.JToggleButton();
        numberLabelModeToggleButton = new javax.swing.JToggleButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        newMenuItem = new javax.swing.JMenuItem();
        openMenuItem = new javax.swing.JMenuItem();
        openClipboardMenuItem = new javax.swing.JMenuItem();
        saveMenuItem = new javax.swing.JMenuItem();
        saveAsMenuItem = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JSeparator();
        saveImageMenuItem = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JSeparator();
        closeMenuItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        editRootMenu = new javax.swing.JMenu();
        editRootMenuItem = new javax.swing.JMenuItem();
        editMoveMenu = new javax.swing.JMenu();
        editMoveMenuItem = new javax.swing.JMenuItem();
        editGameInfoMenu = new javax.swing.JMenu();
        editGameInfoMenuItem = new javax.swing.JMenuItem();
        addGameInfoMenuItem = new javax.swing.JMenuItem();
        editSetupMenu = new javax.swing.JMenu();
        editSetupMenuItem = new javax.swing.JMenuItem();
        addSetupMenuItem = new javax.swing.JMenuItem();
        editNoTypeMenu = new javax.swing.JMenu();
        editNoTypeMenuItem = new javax.swing.JMenuItem();
        addNoTypeMenuItem = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JSeparator();
        plMenu = new javax.swing.JMenu();
        plBlackMenuItem = new javax.swing.JMenuItem();
        plWhiteMenuItem = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JSeparator();
        deleteNodeMenuItem = new javax.swing.JMenuItem();
        insertBeforeMenuItem = new javax.swing.JMenuItem();
        insertAfterMenuItem = new javax.swing.JMenuItem();
        jSeparator8 = new javax.swing.JSeparator();
        addTreeMenuItem = new javax.swing.JMenuItem();
        moveMenu = new javax.swing.JMenu();
        topMenuItem = new javax.swing.JMenuItem();
        backwardMenuItem = new javax.swing.JMenuItem();
        forwardMenuItem = new javax.swing.JMenuItem();
        bottomMenuItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        prevMenuItem = new javax.swing.JMenuItem();
        nextMenuItem = new javax.swing.JMenuItem();
        gameMenu = new javax.swing.JMenu();
        startMenuItem = new javax.swing.JMenuItem();
        stopMenuItem = new javax.swing.JMenuItem();
        settingMenu = new javax.swing.JMenu();
        settingBoardMenu = new javax.swing.JMenu();
        moveNumberCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
        koCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
        markCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
        labelCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
        lastMoveCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();

        setLocationByPlatform(true);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                formComponentHidden(evt);
            }
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

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
            .addGap(0, 510, Short.MAX_VALUE)
        );

        hSplitPane.setLeftComponent(boardPanelBase);

        whitePlayerInfoBase.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout whitePlayerInfoBaseLayout = new javax.swing.GroupLayout(whitePlayerInfoBase);
        whitePlayerInfoBase.setLayout(whitePlayerInfoBaseLayout);
        whitePlayerInfoBaseLayout.setHorizontalGroup(
            whitePlayerInfoBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 325, Short.MAX_VALUE)
        );
        whitePlayerInfoBaseLayout.setVerticalGroup(
            whitePlayerInfoBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 69, Short.MAX_VALUE)
        );

        blackPlayerInfoBase.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout blackPlayerInfoBaseLayout = new javax.swing.GroupLayout(blackPlayerInfoBase);
        blackPlayerInfoBase.setLayout(blackPlayerInfoBaseLayout);
        blackPlayerInfoBaseLayout.setHorizontalGroup(
            blackPlayerInfoBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 325, Short.MAX_VALUE)
        );
        blackPlayerInfoBaseLayout.setVerticalGroup(
            blackPlayerInfoBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 57, Short.MAX_VALUE)
        );

        moveInfoPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        moveInfoLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        moveInfoLabel.setText("jLabel1");

        javax.swing.GroupLayout moveInfoPanelLayout = new javax.swing.GroupLayout(moveInfoPanel);
        moveInfoPanel.setLayout(moveInfoPanelLayout);
        moveInfoPanelLayout.setHorizontalGroup(
            moveInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(moveInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(moveInfoLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                .addContainerGap())
        );
        moveInfoPanelLayout.setVerticalGroup(
            moveInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(moveInfoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("app/resource/Resource"); // NOI18N
        undoButton.setText(bundle.getString("Undo")); // NOI18N
        undoButton.setFocusPainted(false);
        undoButton.setFocusable(false);
        undoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                undoButtonActionPerformed(evt);
            }
        });

        passButton.setText(bundle.getString("Pass")); // NOI18N
        passButton.setFocusPainted(false);
        passButton.setFocusable(false);
        passButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passButtonActionPerformed(evt);
            }
        });

        scoreButton.setText(bundle.getString("Score")); // NOI18N
        scoreButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scoreButtonActionPerformed(evt);
            }
        });

        doneButton.setText(bundle.getString("Done")); // NOI18N
        doneButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doneButtonActionPerformed(evt);
            }
        });

        vSplitPane.setDividerLocation(180);
        vSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        treePanelBase.setPreferredSize(new java.awt.Dimension(100, 50));
        treePanelBase.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                treePanelBaseComponentResized(evt);
            }
        });

        javax.swing.GroupLayout treePanelBaseLayout = new javax.swing.GroupLayout(treePanelBase);
        treePanelBase.setLayout(treePanelBaseLayout);
        treePanelBaseLayout.setHorizontalGroup(
            treePanelBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 329, Short.MAX_VALUE)
        );
        treePanelBaseLayout.setVerticalGroup(
            treePanelBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 39, Short.MAX_VALUE)
        );

        vSplitPane.setBottomComponent(treePanelBase);

        scrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setMinimumSize(new java.awt.Dimension(0, 0));
        scrollPane.setPreferredSize(new java.awt.Dimension(223, 78));

        textArea.setColumns(20);
        textArea.setLineWrap(true);
        textArea.setRows(5);
        textArea.setWrapStyleWord(true);
        scrollPane.setViewportView(textArea);

        vSplitPane.setLeftComponent(scrollPane);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(passButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(undoButton)
                .addContainerGap(217, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scoreButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(doneButton)
                .addContainerGap(217, Short.MAX_VALUE))
            .addComponent(blackPlayerInfoBase, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(whitePlayerInfoBase, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(moveInfoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(nodeNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
            .addComponent(vSplitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {doneButton, passButton, scoreButton, undoButton});

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
                    .addComponent(undoButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(scoreButton)
                    .addComponent(doneButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nodeNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(vSplitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE))
        );

        hSplitPane.setRightComponent(jPanel3);

        jToolBar2.setFloatable(false);
        jToolBar2.setBorderPainted(false);

        newButton.setText("New");
        newButton.setBorderPainted(false);
        newButton.setFocusable(false);
        jToolBar2.add(newButton);

        openButton.setText("Open");
        openButton.setBorderPainted(false);
        openButton.setFocusable(false);
        jToolBar2.add(openButton);

        saveButton.setText("Save");
        saveButton.setBorderPainted(false);
        saveButton.setFocusable(false);
        jToolBar2.add(saveButton);

        jToolBar1.setFloatable(false);
        jToolBar1.setBorderPainted(false);

        pivotToggleButton.setText("Pivot");
        pivotToggleButton.setBorderPainted(false);
        pivotToggleButton.setFocusable(false);
        jToolBar1.add(pivotToggleButton);

        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jToolBar1.add(jSeparator5);

        topButton.setText("Top");
        topButton.setBorderPainted(false);
        topButton.setFocusable(false);
        jToolBar1.add(topButton);

        parentButton.setBorderPainted(false);
        parentButton.setFocusable(false);
        jToolBar1.add(parentButton);

        childButton.setBorderPainted(false);
        childButton.setFocusable(false);
        jToolBar1.add(childButton);

        bottomButton.setText("Bottom");
        bottomButton.setBorderPainted(false);
        bottomButton.setFocusable(false);
        jToolBar1.add(bottomButton);

        jLabel1.setText("   ");
        jToolBar1.add(jLabel1);

        prevButton.setBorderPainted(false);
        prevButton.setFocusable(false);
        jToolBar1.add(prevButton);

        nextButton.setBorderPainted(false);
        nextButton.setFocusable(false);
        jToolBar1.add(nextButton);

        jToolBar3.setFloatable(false);
        jToolBar3.setBorderPainted(false);

        deleteNodeButton.setText("Delete");
        deleteNodeButton.setBorderPainted(false);
        deleteNodeButton.setFocusable(false);
        jToolBar3.add(deleteNodeButton);

        insertNodeButton.setText("Insert");
        insertNodeButton.setBorderPainted(false);
        insertNodeButton.setFocusable(false);
        jToolBar3.add(insertNodeButton);

        jToolBar4.setFloatable(false);
        jToolBar4.setBorderPainted(false);

        playingLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/media-record.png"))); // NOI18N
        playingLabel.setEnabled(false);
        playingLabel.setFocusable(false);
        jToolBar4.add(playingLabel);

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jToolBar4.add(jSeparator3);

        startButton.setText("Start");
        startButton.setBorderPainted(false);
        startButton.setFocusable(false);
        jToolBar4.add(startButton);

        stopButton.setText("Stop");
        stopButton.setBorderPainted(false);
        stopButton.setFocusable(false);
        jToolBar4.add(stopButton);

        jToolBar5.setFloatable(false);

        buttonGroup.add(moveModeToggleButton);
        moveModeToggleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/move.png"))); // NOI18N
        moveModeToggleButton.setBorderPainted(false);
        moveModeToggleButton.setFocusable(false);
        moveModeToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveModeToggleButtonActionPerformed(evt);
            }
        });
        jToolBar5.add(moveModeToggleButton);

        buttonGroup.add(setupModeToggleButton);
        setupModeToggleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/setup.png"))); // NOI18N
        setupModeToggleButton.setBorderPainted(false);
        setupModeToggleButton.setFocusable(false);
        setupModeToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setupModeToggleButtonActionPerformed(evt);
            }
        });
        jToolBar5.add(setupModeToggleButton);

        buttonGroup.add(markModeToggleButton);
        markModeToggleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/mark.png"))); // NOI18N
        markModeToggleButton.setBorderPainted(false);
        markModeToggleButton.setFocusable(false);
        markModeToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                markModeToggleButtonActionPerformed(evt);
            }
        });
        jToolBar5.add(markModeToggleButton);

        buttonGroup.add(charLabelModeToggleButton);
        charLabelModeToggleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/char.png"))); // NOI18N
        charLabelModeToggleButton.setBorderPainted(false);
        charLabelModeToggleButton.setFocusable(false);
        charLabelModeToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                charLabelModeToggleButtonActionPerformed(evt);
            }
        });
        jToolBar5.add(charLabelModeToggleButton);

        buttonGroup.add(numberLabelModeToggleButton);
        numberLabelModeToggleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/number.png"))); // NOI18N
        numberLabelModeToggleButton.setBorderPainted(false);
        numberLabelModeToggleButton.setFocusable(false);
        numberLabelModeToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numberLabelModeToggleButtonActionPerformed(evt);
            }
        });
        jToolBar5.add(numberLabelModeToggleButton);

        fileMenu.setText(bundle.getString("File")); // NOI18N

        newMenuItem.setText("New");
        fileMenu.add(newMenuItem);

        openMenuItem.setText("Open");
        fileMenu.add(openMenuItem);

        openClipboardMenuItem.setText("OpenClipboard");
        fileMenu.add(openClipboardMenuItem);

        saveMenuItem.setText("Save");
        fileMenu.add(saveMenuItem);

        saveAsMenuItem.setText("Save As");
        fileMenu.add(saveAsMenuItem);
        fileMenu.add(jSeparator7);

        saveImageMenuItem.setText("Save Image");
        fileMenu.add(saveImageMenuItem);
        fileMenu.add(jSeparator2);

        closeMenuItem.setText("Close");
        fileMenu.add(closeMenuItem);

        jMenuBar1.add(fileMenu);

        editMenu.setText(bundle.getString("Edit")); // NOI18N

        editRootMenu.setText(bundle.getString("RootProperty")); // NOI18N

        editRootMenuItem.setText("Item");
        editRootMenu.add(editRootMenuItem);

        editMenu.add(editRootMenu);

        editMoveMenu.setText(bundle.getString("MoveProperty")); // NOI18N

        editMoveMenuItem.setText("Item");
        editMoveMenu.add(editMoveMenuItem);

        editMenu.add(editMoveMenu);

        editGameInfoMenu.setText(bundle.getString("GameInfoProperty")); // NOI18N

        editGameInfoMenuItem.setText("Item");
        editGameInfoMenu.add(editGameInfoMenuItem);

        addGameInfoMenuItem.setText("Item");
        editGameInfoMenu.add(addGameInfoMenuItem);

        editMenu.add(editGameInfoMenu);

        editSetupMenu.setText(bundle.getString("SetupProperty")); // NOI18N

        editSetupMenuItem.setText("Item");
        editSetupMenu.add(editSetupMenuItem);

        addSetupMenuItem.setText("Item");
        editSetupMenu.add(addSetupMenuItem);

        editMenu.add(editSetupMenu);

        editNoTypeMenu.setText(bundle.getString("NoTypeProperty")); // NOI18N

        editNoTypeMenuItem.setText("Item");
        editNoTypeMenu.add(editNoTypeMenuItem);

        addNoTypeMenuItem.setText("Item");
        editNoTypeMenu.add(addNoTypeMenuItem);

        editMenu.add(editNoTypeMenu);
        editMenu.add(jSeparator6);

        plMenu.setText(bundle.getString("PlayerToPlay")); // NOI18N

        plBlackMenuItem.setText(bundle.getString("Black")); // NOI18N
        plBlackMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                plBlackMenuItemActionPerformed(evt);
            }
        });
        plMenu.add(plBlackMenuItem);

        plWhiteMenuItem.setText(bundle.getString("White")); // NOI18N
        plWhiteMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                plWhiteMenuItemActionPerformed(evt);
            }
        });
        plMenu.add(plWhiteMenuItem);

        editMenu.add(plMenu);
        editMenu.add(jSeparator4);

        deleteNodeMenuItem.setText("Delete Node");
        editMenu.add(deleteNodeMenuItem);

        insertBeforeMenuItem.setText("Insert Before");
        editMenu.add(insertBeforeMenuItem);

        insertAfterMenuItem.setText("Inset After");
        editMenu.add(insertAfterMenuItem);
        editMenu.add(jSeparator8);

        addTreeMenuItem.setText("Add Tree");
        editMenu.add(addTreeMenuItem);

        jMenuBar1.add(editMenu);

        moveMenu.setText(bundle.getString("Move")); // NOI18N

        topMenuItem.setText("Item");
        moveMenu.add(topMenuItem);

        backwardMenuItem.setText("Item");
        moveMenu.add(backwardMenuItem);

        forwardMenuItem.setText("Item");
        moveMenu.add(forwardMenuItem);

        bottomMenuItem.setText("Item");
        moveMenu.add(bottomMenuItem);
        moveMenu.add(jSeparator1);

        prevMenuItem.setText("Item");
        moveMenu.add(prevMenuItem);

        nextMenuItem.setText("Item");
        moveMenu.add(nextMenuItem);

        jMenuBar1.add(moveMenu);

        gameMenu.setText(bundle.getString("Game")); // NOI18N

        startMenuItem.setText("Start");
        gameMenu.add(startMenuItem);

        stopMenuItem.setText("Stop");
        gameMenu.add(stopMenuItem);

        jMenuBar1.add(gameMenu);

        settingMenu.setText(bundle.getString("Setting")); // NOI18N

        settingBoardMenu.setText(bundle.getString("View")); // NOI18N

        moveNumberCheckBoxMenuItem.setSelected(true);
        moveNumberCheckBoxMenuItem.setText(bundle.getString("MoveNumber")); // NOI18N
        moveNumberCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveNumberCheckBoxMenuItemActionPerformed(evt);
            }
        });
        settingBoardMenu.add(moveNumberCheckBoxMenuItem);

        koCheckBoxMenuItem.setSelected(true);
        koCheckBoxMenuItem.setText(bundle.getString("Ko")); // NOI18N
        koCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                koCheckBoxMenuItemActionPerformed(evt);
            }
        });
        settingBoardMenu.add(koCheckBoxMenuItem);

        markCheckBoxMenuItem.setSelected(true);
        markCheckBoxMenuItem.setText(bundle.getString("Mark")); // NOI18N
        markCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                markCheckBoxMenuItemActionPerformed(evt);
            }
        });
        settingBoardMenu.add(markCheckBoxMenuItem);

        labelCheckBoxMenuItem.setSelected(true);
        labelCheckBoxMenuItem.setText(bundle.getString("Label")); // NOI18N
        labelCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                labelCheckBoxMenuItemActionPerformed(evt);
            }
        });
        settingBoardMenu.add(labelCheckBoxMenuItem);

        lastMoveCheckBoxMenuItem.setSelected(true);
        lastMoveCheckBoxMenuItem.setText(bundle.getString("LastMove")); // NOI18N
        lastMoveCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lastMoveCheckBoxMenuItemActionPerformed(evt);
            }
        });
        settingBoardMenu.add(lastMoveCheckBoxMenuItem);

        settingMenu.add(settingBoardMenu);

        jMenuBar1.add(settingMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(180, 180, 180))
            .addComponent(hSplitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 934, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jToolBar4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToolBar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jToolBar5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jToolBar3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(hSplitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 514, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void numberLabelModeToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numberLabelModeToggleButtonActionPerformed
        goGame.setGoState(GoState.NUMBER_LABEL);
    }//GEN-LAST:event_numberLabelModeToggleButtonActionPerformed
    
    private void charLabelModeToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_charLabelModeToggleButtonActionPerformed
        goGame.setGoState(GoState.CHAR_LABEL);
    }//GEN-LAST:event_charLabelModeToggleButtonActionPerformed
    
    private void setupModeToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setupModeToggleButtonActionPerformed
        goGame.setGoState(GoState.SETUP);
    }//GEN-LAST:event_setupModeToggleButtonActionPerformed
    
    private void moveModeToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moveModeToggleButtonActionPerformed
        goGame.setGoState(GoState.EDIT);
    }//GEN-LAST:event_moveModeToggleButtonActionPerformed
    
    private void markModeToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_markModeToggleButtonActionPerformed
        goGame.setGoState(GoState.MARK);
    }//GEN-LAST:event_markModeToggleButtonActionPerformed
    
    private void undoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_undoButtonActionPerformed
        operator.undoButtonPressed();
    }//GEN-LAST:event_undoButtonActionPerformed
    
    private void passButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passButtonActionPerformed
        operator.passButtonPressed();
    }//GEN-LAST:event_passButtonActionPerformed
    
    private void doneButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doneButtonActionPerformed
        operator.doneButtonPressed();
    }//GEN-LAST:event_doneButtonActionPerformed
    
    private void scoreButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scoreButtonActionPerformed
        goGame.setGoState(GoState.SCORE);
    }//GEN-LAST:event_scoreButtonActionPerformed
    
    private void formComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentHidden
        System.out.println("SgfGoWindow Hidden");
        
        dispose();
    }//GEN-LAST:event_formComponentHidden

    private void plBlackMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_plBlackMenuItemActionPerformed
        goGame.getGameTree().setPlayerToPlay(GoColor.BLACK);
    }//GEN-LAST:event_plBlackMenuItemActionPerformed

    private void plWhiteMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_plWhiteMenuItemActionPerformed
        goGame.getGameTree().setPlayerToPlay(GoColor.WHITE);
    }//GEN-LAST:event_plWhiteMenuItemActionPerformed

    private void moveNumberCheckBoxMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moveNumberCheckBoxMenuItemActionPerformed
        boolean selected = moveNumberCheckBoxMenuItem.isSelected();
        
        board.setShowMoveNumber(selected);
    }//GEN-LAST:event_moveNumberCheckBoxMenuItemActionPerformed

    private void koCheckBoxMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_koCheckBoxMenuItemActionPerformed
        boolean selected = koCheckBoxMenuItem.isSelected();
        
        board.setShowKo(selected);
    }//GEN-LAST:event_koCheckBoxMenuItemActionPerformed

    private void markCheckBoxMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_markCheckBoxMenuItemActionPerformed
        boolean selected = markCheckBoxMenuItem.isSelected();
        
        board.setShowMark(selected);
    }//GEN-LAST:event_markCheckBoxMenuItemActionPerformed

    private void labelCheckBoxMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_labelCheckBoxMenuItemActionPerformed
        boolean selected = labelCheckBoxMenuItem.isSelected();
        
        board.setShowLabel(selected);
    }//GEN-LAST:event_labelCheckBoxMenuItemActionPerformed

    private void lastMoveCheckBoxMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lastMoveCheckBoxMenuItemActionPerformed
        boolean selected = lastMoveCheckBoxMenuItem.isSelected();
        
        board.setShowLastMove(selected);
    }//GEN-LAST:event_lastMoveCheckBoxMenuItemActionPerformed

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

    private void treePanelBaseComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_treePanelBaseComponentResized
        int vDivider = vSplitPane.getDividerLocation();
        Config config = App.getInstance().getConfig();
        config.setIntProperty(GoConfig.GO_WINDOW_V_DIVIDER_LOCATION, vDivider);
    }//GEN-LAST:event_treePanelBaseComponentResized
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem addGameInfoMenuItem;
    private javax.swing.JMenuItem addNoTypeMenuItem;
    private javax.swing.JMenuItem addSetupMenuItem;
    private javax.swing.JMenuItem addTreeMenuItem;
    private javax.swing.JMenuItem backwardMenuItem;
    private javax.swing.JPanel blackPlayerInfoBase;
    private javax.swing.JPanel boardPanelBase;
    private javax.swing.JButton bottomButton;
    private javax.swing.JMenuItem bottomMenuItem;
    private javax.swing.ButtonGroup buttonGroup;
    private javax.swing.JToggleButton charLabelModeToggleButton;
    private javax.swing.JButton childButton;
    private javax.swing.JMenuItem closeMenuItem;
    private javax.swing.JButton deleteNodeButton;
    private javax.swing.JMenuItem deleteNodeMenuItem;
    private javax.swing.JButton doneButton;
    private javax.swing.JMenu editGameInfoMenu;
    private javax.swing.JMenuItem editGameInfoMenuItem;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenu editMoveMenu;
    private javax.swing.JMenuItem editMoveMenuItem;
    private javax.swing.JMenu editNoTypeMenu;
    private javax.swing.JMenuItem editNoTypeMenuItem;
    private javax.swing.JMenu editRootMenu;
    private javax.swing.JMenuItem editRootMenuItem;
    private javax.swing.JMenu editSetupMenu;
    private javax.swing.JMenuItem editSetupMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenuItem forwardMenuItem;
    private javax.swing.JMenu gameMenu;
    private javax.swing.JSplitPane hSplitPane;
    private javax.swing.JMenuItem insertAfterMenuItem;
    private javax.swing.JMenuItem insertBeforeMenuItem;
    private javax.swing.JButton insertNodeButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JToolBar jToolBar3;
    private javax.swing.JToolBar jToolBar4;
    private javax.swing.JToolBar jToolBar5;
    private javax.swing.JCheckBoxMenuItem koCheckBoxMenuItem;
    private javax.swing.JCheckBoxMenuItem labelCheckBoxMenuItem;
    private javax.swing.JCheckBoxMenuItem lastMoveCheckBoxMenuItem;
    private javax.swing.JCheckBoxMenuItem markCheckBoxMenuItem;
    private javax.swing.JToggleButton markModeToggleButton;
    private javax.swing.JLabel moveInfoLabel;
    private javax.swing.JPanel moveInfoPanel;
    private javax.swing.JMenu moveMenu;
    private javax.swing.JToggleButton moveModeToggleButton;
    private javax.swing.JCheckBoxMenuItem moveNumberCheckBoxMenuItem;
    private javax.swing.JButton newButton;
    private javax.swing.JMenuItem newMenuItem;
    private javax.swing.JButton nextButton;
    private javax.swing.JMenuItem nextMenuItem;
    private javax.swing.JTextField nodeNameTextField;
    private javax.swing.JToggleButton numberLabelModeToggleButton;
    private javax.swing.JButton openButton;
    private javax.swing.JMenuItem openClipboardMenuItem;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JButton parentButton;
    private javax.swing.JButton passButton;
    private javax.swing.JToggleButton pivotToggleButton;
    private javax.swing.JMenuItem plBlackMenuItem;
    private javax.swing.JMenu plMenu;
    private javax.swing.JMenuItem plWhiteMenuItem;
    private javax.swing.JLabel playingLabel;
    private javax.swing.JButton prevButton;
    private javax.swing.JMenuItem prevMenuItem;
    private javax.swing.JMenuItem saveAsMenuItem;
    private javax.swing.JButton saveButton;
    private javax.swing.JMenuItem saveImageMenuItem;
    private javax.swing.JMenuItem saveMenuItem;
    private javax.swing.JButton scoreButton;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JMenu settingBoardMenu;
    private javax.swing.JMenu settingMenu;
    private javax.swing.JToggleButton setupModeToggleButton;
    private javax.swing.JButton startButton;
    private javax.swing.JMenuItem startMenuItem;
    private javax.swing.JButton stopButton;
    private javax.swing.JMenuItem stopMenuItem;
    private javax.swing.JTextArea textArea;
    private javax.swing.JButton topButton;
    private javax.swing.JMenuItem topMenuItem;
    private javax.swing.JPanel treePanelBase;
    private javax.swing.JButton undoButton;
    private javax.swing.JSplitPane vSplitPane;
    private javax.swing.JPanel whitePlayerInfoBase;
    // End of variables declaration//GEN-END:variables
}