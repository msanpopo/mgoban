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

import java.awt.BorderLayout;

import go.GoColor;

import app.wing.NetGo;


import go.gui.BoardSettingPanel;
import go.gui.InOutPanel;
import go.gui.InputListener;
import app.wing.UserInfoWindow;
import go.gui.WingTimeSettingPanel;
import wing.MatchType;
import wing.MessageReceiver;
import wing.message.Message;
import wing.message.TellMessage;

@SuppressWarnings("serial")
public class MatchSettingDialog extends javax.swing.JFrame implements InputListener, MessageReceiver{
    private static final String OK = "OK";
    private static final String DECLINE = "断る";
    private static final String CANCEL = "取り消し";
    private static final String CLOSE = "閉じる";
    
    private static final String MATCH_TYPE = "対局種類";
    private static final String MY_COLOR = "自分の色";
    private static final String OPPONENT = "相手の名前";
    
    private static final String PARAM_CHANGED = "条件が変更されました";
    
    private static final String INFO = "?";
    
    private NetGo netGo;
    
    private BoardSettingPanel boardSettingPanel;
    private WingTimeSettingPanel timeSettingPanel;
    private InOutPanel ioPanel;
    
    private String command;
    
    private boolean requested;
    private boolean waiting;

    public MatchSettingDialog(NetGo netGo) {
        this.netGo = netGo;
        netGo.getServer().addReceiver(this);
        
        boardSettingPanel = new BoardSettingPanel();
        timeSettingPanel = new WingTimeSettingPanel();
        ioPanel = new InOutPanel(netGo.getTellInputList());
        ioPanel.setInputListener(this);
        
        this.command = null;
        this.requested = false;
        this.waiting = false;
        
        initComponents();
        
        okButton.setText(OK);
        declineButton.setText(DECLINE);
        cancelButton.setText(CANCEL);
        closeButton.setText(CLOSE);
        
        matchTypeLabel.setText(MATCH_TYPE);
        myColorLabel.setText(MY_COLOR);
        opponentLabel.setText(OPPONENT);
        
        userInfoButton.setText(INFO);
        
        for(MatchType type : MatchType.values()){
            matchTypeComboBox.addItem(type);
        }
        
        myColorComboBox.addItem(GoColor.BLACK);
        myColorComboBox.addItem(GoColor.WHITE);
        
        
        leftPanel.setLayout(new BorderLayout());
        leftPanel.add(boardSettingPanel, BorderLayout.CENTER);
        rightPanel.setLayout(new BorderLayout());
        rightPanel.add(timeSettingPanel, BorderLayout.CENTER);
        
        inoutPanel.setLayout(new BorderLayout());
        inoutPanel.add(ioPanel, BorderLayout.CENTER);
        
        pack();
        
        matchTypeComboBox.setSelectedItem(MatchType.MATCH);
        
        updateVisibility();
    }
    
    private void setMatchTypeEnabled(boolean b){
        matchTypeLabel.setEnabled(b);
        matchTypeComboBox.setEnabled(b);
    }
    
    private void setMyColorEnabled(boolean b){
        myColorLabel.setEnabled(b);
        myColorComboBox.setEnabled(b);
    }
    
    private void setOpponentEnabled(boolean b){
        opponentLabel.setEnabled(b);
        opponentField.setEnabled(b);
    }
    
    public void addText(String text){
        ioPanel.addText(text);
    }
    
    public void setOpponent(String name){
        opponentField.setText(name);
    }
    
    public String getOpponent(){
        return opponentField.getText();
    }
    
    public void sendHandicap(){
        MatchType matchType = (MatchType)matchTypeComboBox.getSelectedItem();
        GoColor color = (GoColor)myColorComboBox.getSelectedItem();
        int h = boardSettingPanel.getHandicap();
        
        if(color == GoColor.BLACK){
            if(matchType == MatchType.LMATCH || matchType == MatchType.GMATCH || matchType == MatchType.FMATCH){
                netGo.sendCommand("handicap " + h);
            }
        }
    }
    
    public void sendKomi(){
        MatchType matchType = (MatchType)matchTypeComboBox.getSelectedItem();
        GoColor color = (GoColor)myColorComboBox.getSelectedItem();
        double k = boardSettingPanel.getKomi();
        
        if(color == GoColor.BLACK){
            if(matchType == MatchType.LMATCH || matchType == MatchType.GMATCH || matchType == MatchType.FMATCH){
                netGo.sendCommand("komi " + k);
            }
        }
    }
    
    private String getMatchCommand(){
        MatchType matchType = (MatchType)matchTypeComboBox.getSelectedItem();
        String name = opponentField.getText();
        GoColor color = (GoColor)myColorComboBox.getSelectedItem();
        int boardSize = boardSettingPanel.getBoardSize();
        int mainTime = timeSettingPanel.getMainTime();
        int byoYomiTime = timeSettingPanel.getByoYomiTime();
        int kouryoTime = timeSettingPanel.getKouryoTime();
        
        // wing
                /*
                StringBuilder str = new StringBuilder();
                str.append(matchType.getCommand())
                str.append(" ").append(name);
                str.append(" ").append(String.valueOf(mainTime));
                str.append(" ").append(String.valueOf(byoYomiTime));
                str.append(" ").append(String.valueOf(boardSize));
                if(kouryoTime != 0 || matchType != MatchType.LMATCH){
                        str.append(" ").append(String.valueOf(kouryoTime));
                }
                if(matchType != MatchType.EMATCH || matchType != MatchType.FEMATCH || matchType != MatchType.LMATCH){
                        str.append(" ").append(color.toSgfString());
                }
                 */
        
        // nngs
        StringBuilder str = new StringBuilder();
        str.append(matchType.getCommand()).append(" ").append(name);
        str.append(" ").append(color.toSgfString());
        str.append(" ").append(String.valueOf(boardSize));
        str.append(" ").append(String.valueOf(mainTime));
        str.append(" ").append(String.valueOf(byoYomiTime));
        if(kouryoTime != 0 && matchType != MatchType.LMATCH){
            str.append(" ").append(String.valueOf(kouryoTime));
        }
        System.out.println("MatchPanel.getMatchCommand:" + str.toString());
        
        return str.toString();
    }
    
    // "match aaa B 9 1 10"
    public void setMatchCommand(String s){
        command = s;
        
        System.out.println("MatchPanel.setMatchCommand:" + s);
        String[] a = s.split("\\s+");
        
        MatchType matchType = MatchType.get(a[0]);
        String name = a[1];
        GoColor color = GoColor.get(a[2]);
        int boardSize = Integer.parseInt(a[3]);
        int mainTime = Integer.parseInt(a[4]);
        int byoYomiTime = Integer.parseInt(a[5]);
        
        //System.out.println("MatchPanel.setMatchCommand:command:" + matchType);
        //System.out.println("MatchPanel.setMatchCommand:color:" + a[2] + ":" + color);
        
        matchTypeComboBox.setSelectedItem(matchType);
        myColorComboBox.setSelectedItem(color);
        opponentField.setText(name);
        
        boardSettingPanel.setBoardSize(boardSize);
        
        timeSettingPanel.setMainTime(mainTime);
        timeSettingPanel.setByoYomiTime(byoYomiTime);
        
        requested = true;
        waiting = false;
        updateVisibility();
    }
    
    public void updateMatchCommand(String s){
        addText(PARAM_CHANGED);
        setMatchCommand(s);
    }
    
    public void textInputed(Object source, String text) {
        String myName = netGo.myName();
        StringBuilder strDisp = new StringBuilder();
        strDisp.append(myName).append(" : ").append(text);
        ioPanel.addText(strDisp.toString(), InOutPanel.GRAY);
        
        StringBuilder strSend = new StringBuilder();
        strSend.append("tell ").append(opponentField.getText()).append(" ").append(text);
        System.out.println(this.getClass() + ":send:" + strSend.toString() + ":");
        netGo.sendCommand(strSend.toString());
    }
    
    public void receive(Message wm) {
        if(wm instanceof TellMessage){
            TellMessage m = (TellMessage)wm;
            String name = m.getName();
            if(name.equals(opponentField.getText())){
                String text = m.getText();
                
                StringBuilder str = new StringBuilder();
                str.append(name).append(" : ").append(text);
                ioPanel.addText(str.toString());
            }
        }
    }
    
    public void decline(){
        requested = false;
        waiting = false;
        addText("Request is declined.");
        updateVisibility();
    }
    
    private void updatePanelVisibility(){
        if(waiting){
            setMatchTypeEnabled(false);
            setMyColorEnabled(false);
            setOpponentEnabled(false);
            boardSettingPanel.setEnabled(false);
            timeSettingPanel.setEnabled(false);
        }else{
            MatchType matchType = (MatchType)matchTypeComboBox.getSelectedItem();
            GoColor color = (GoColor)myColorComboBox.getSelectedItem();
            
            setMatchTypeEnabled(true);
            if(matchType == MatchType.LMATCH || matchType == MatchType.GMATCH || matchType == MatchType.FMATCH){
                setMyColorEnabled(true);
            }else{
                setMyColorEnabled(false);
            }
            setOpponentEnabled(true);
            
            boardSettingPanel.setBoardSizeEnabled(true);
            if(color == GoColor.BLACK){
                if(matchType == MatchType.LMATCH || matchType == MatchType.GMATCH || matchType == MatchType.FMATCH){
                    boardSettingPanel.setHandicapEnabled(true);
                    boardSettingPanel.setKomiEnabled(true);
                }else{
                    boardSettingPanel.setHandicapEnabled(false);
                    boardSettingPanel.setKomiEnabled(false);
                }
            }else{
                boardSettingPanel.setHandicapEnabled(false);
                boardSettingPanel.setKomiEnabled(false);
            }
            
            timeSettingPanel.setMainTimeEnabled(true);
            timeSettingPanel.setByoYomiTimeEnabled(true);
            if(matchType == matchType.LMATCH){
                timeSettingPanel.setKouryoTimeEnabled(false);
            }else{
                timeSettingPanel.setKouryoTimeEnabled(true);
            }
        }
    }
    
    private void updateVisibility(){
        updatePanelVisibility();
        
        if(waiting){
            okButton.setEnabled(false);
            declineButton.setEnabled(false);
            cancelButton.setEnabled(true);
        }else{
            okButton.setEnabled(true);
            if(requested){
                declineButton.setEnabled(true);
            }else{
                declineButton.setEnabled(false);
            }
            cancelButton.setEnabled(false);
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        leftPanel = new javax.swing.JPanel();
        rightPanel = new javax.swing.JPanel();
        inoutPanel = new javax.swing.JPanel();
        okButton = new javax.swing.JButton();
        declineButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        closeButton = new javax.swing.JButton();
        matchTypeLabel = new javax.swing.JLabel();
        myColorLabel = new javax.swing.JLabel();
        opponentLabel = new javax.swing.JLabel();
        matchTypeComboBox = new javax.swing.JComboBox();
        myColorComboBox = new javax.swing.JComboBox();
        opponentField = new javax.swing.JTextField();
        userInfoButton = new javax.swing.JButton();

        setTitle("Match Setting");
        setLocationByPlatform(true);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                formComponentHidden(evt);
            }
        });

        leftPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout leftPanelLayout = new javax.swing.GroupLayout(leftPanel);
        leftPanel.setLayout(leftPanelLayout);
        leftPanelLayout.setHorizontalGroup(
            leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 213, Short.MAX_VALUE)
        );
        leftPanelLayout.setVerticalGroup(
            leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        rightPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout rightPanelLayout = new javax.swing.GroupLayout(rightPanel);
        rightPanel.setLayout(rightPanelLayout);
        rightPanelLayout.setHorizontalGroup(
            rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 216, Short.MAX_VALUE)
        );
        rightPanelLayout.setVerticalGroup(
            rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        inoutPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout inoutPanelLayout = new javax.swing.GroupLayout(inoutPanel);
        inoutPanel.setLayout(inoutPanelLayout);
        inoutPanelLayout.setHorizontalGroup(
            inoutPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 439, Short.MAX_VALUE)
        );
        inoutPanelLayout.setVerticalGroup(
            inoutPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 156, Short.MAX_VALUE)
        );

        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        declineButton.setText("Decline");
        declineButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                declineButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        closeButton.setText("Close");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        matchTypeLabel.setText("Match Type :");

        myColorLabel.setText("My Color :");

        opponentLabel.setText("Opponent :");

        matchTypeComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                matchTypeComboBoxItemStateChanged(evt);
            }
        });

        myColorComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                myColorComboBoxItemStateChanged(evt);
            }
        });

        userInfoButton.setText("Info");
        userInfoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userInfoButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(inoutPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(leftPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rightPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(opponentLabel)
                            .addComponent(myColorLabel)
                            .addComponent(matchTypeLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(myColorComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(matchTypeComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(opponentField, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(userInfoButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(declineButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(matchTypeLabel)
                    .addComponent(matchTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(myColorLabel)
                    .addComponent(myColorComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(userInfoButton)
                    .addComponent(opponentField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(opponentLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(leftPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rightPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inoutPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(closeButton)
                    .addComponent(cancelButton)
                    .addComponent(declineButton)
                    .addComponent(okButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void myColorComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_myColorComboBoxItemStateChanged
        updatePanelVisibility();
    }//GEN-LAST:event_myColorComboBoxItemStateChanged
    
    private void formComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentHidden
        System.out.println(this.getClass() + " hidden");
        //netGo.getWindow().removeComponentListener(this);
        
        String name = opponentField.getText();
        if(waiting){
            netGo.sendCommand("withdraw");
        }else if(requested){
            netGo.sendCommand("decline " + name);
        }
        
        dispose();
    }//GEN-LAST:event_formComponentHidden
    
    private void matchTypeComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_matchTypeComboBoxItemStateChanged
        updatePanelVisibility();
    }//GEN-LAST:event_matchTypeComboBoxItemStateChanged
    
    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        setVisible(false);
    }//GEN-LAST:event_closeButtonActionPerformed
    
    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        netGo.sendCommand("withdraw");
        
        waiting = false;
        updateVisibility();
    }//GEN-LAST:event_cancelButtonActionPerformed
    
    private void declineButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_declineButtonActionPerformed
        String name = opponentField.getText();
        netGo.sendCommand("decline " + name);
        
        requested = false;
        updateVisibility();
    }//GEN-LAST:event_declineButtonActionPerformed
    
    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        String name = opponentField.getText();
        String matchCommand = getMatchCommand();
        
        netGo.sendCommand(matchCommand);
        
        waiting = true;
        updateVisibility();
    }//GEN-LAST:event_okButtonActionPerformed
    
    private void userInfoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userInfoButtonActionPerformed
        UserInfoWindow window = new UserInfoWindow(opponentField.getText(), netGo);
        window.setVisible(true);
    }//GEN-LAST:event_userInfoButtonActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton closeButton;
    private javax.swing.JButton declineButton;
    private javax.swing.JPanel inoutPanel;
    private javax.swing.JPanel leftPanel;
    private javax.swing.JComboBox matchTypeComboBox;
    private javax.swing.JLabel matchTypeLabel;
    private javax.swing.JComboBox myColorComboBox;
    private javax.swing.JLabel myColorLabel;
    private javax.swing.JButton okButton;
    private javax.swing.JTextField opponentField;
    private javax.swing.JLabel opponentLabel;
    private javax.swing.JPanel rightPanel;
    private javax.swing.JButton userInfoButton;
    // End of variables declaration//GEN-END:variables
}