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
import app.sgf.GtpEngineSettingPanel;
import go.GoGame;
import go.gui.GtpTimeSettingPanel;
import gtp.GtpEngine;
import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;

public class GameSettingPanel extends javax.swing.JPanel {
    private static final String CLOSE = "閉じる";
    
    private static final String BLACK = "黒";
    private static final String WHITE = "白";
    
    private static final String HUMAN = "人間";
    private static final String COMPUTER = "コンピュータ";
    
    private static final String ENGINE_SETTING = "エンジンの設定";
    
    private GoGame goGame;
    
    private GtpTimeSettingPanel timeSettingPanel;
    
    private DefaultComboBoxModel blackComboModel;
    private DefaultComboBoxModel whiteComboModel;
    
    public GameSettingPanel(GoGame goGame) {
        this.goGame = goGame;
        timeSettingPanel = new GtpTimeSettingPanel();
        
        initComponents();
        
        TitledBorder blackBorder = (TitledBorder) blackPanel.getBorder();
        TitledBorder whiteBorder = (TitledBorder) whitePanel.getBorder();
        blackBorder.setTitle(BLACK);
        whiteBorder.setTitle(WHITE);
        radioBlackHuman.setText(HUMAN);
        radioBlackCom.setText(COMPUTER);
        radioWhiteHuman.setText(HUMAN);
        radioWhiteCom.setText(COMPUTER);
        
        engineSettingButton.setText(ENGINE_SETTING);
        
        leftPanel.setLayout(new BorderLayout());
        leftPanel.add(timeSettingPanel, BorderLayout.CENTER);
        
        radioBlackHuman.setSelected(true);
        radioWhiteCom.setSelected(true);
        
        updateModel();
    }
    
    private void updateModel(){
        blackComboModel = new DefaultComboBoxModel();
        whiteComboModel = new DefaultComboBoxModel();
        
        BufferedReader in = null;
        try{
            InputStream defaultStream = App.getInstance().getDeaultGtpSettingStream();
            File userFile = App.getInstance().getGtpSettingFile();
            
            if(userFile.exists() == true){
                in = new BufferedReader(new InputStreamReader(new FileInputStream(userFile), "UTF-8"));
            }else{
                in = new BufferedReader(new InputStreamReader(defaultStream, "UTF-8"));
            }
            
            while (true) {
                String l = in.readLine();
                System.out.println(this.getClass() + ":gtpengine.cfg :" + l);
                if (l == null || l.equals("")){
                    break;
                }
                
                String[] a = l.split(",");
                GtpEngine engine = null;
                
                if(a.length == 2){
                    engine = new GtpEngine(a[0], a[1], "");
                }else if(a.length == 3){
                    engine = new GtpEngine(a[0], a[1], a[2]);
                }else{
                    System.err.println("err:GtpEngineSettingPanel: a.lenght:" + a.length);
                }
                
                blackComboModel.addElement(engine);
                whiteComboModel.addElement(new GtpEngine(engine));
            }
        } catch (Exception ex) {
            System.err.println(this.getClass() + ":gtpengine.cfg read fail:" + ex);
        } finally{
            if(in != null){
                try{
                    in.close();
                }catch(IOException e){
                    
                }
            }
        }
        
        blackComboBox.setModel(blackComboModel);
        whiteComboBox.setModel(whiteComboModel);
    }
    
    public boolean blackIsEngine(){
        if(radioBlackCom.isSelected()){
            return true;
        }else{
            return false;
        }
    }
    
    public boolean whiteIsEngine(){
        if(radioWhiteCom.isSelected()){
            return true;
        }else{
            return false;
        }
    }
    
    public GtpEngine getBlackEngien(){
        if(blackIsEngine()){
        return (GtpEngine)blackComboBox.getSelectedItem();
        }else{
            return null;
        }
    }
    
    public GtpEngine getWhiteEngien(){
        if(whiteIsEngine()){
        return (GtpEngine)whiteComboBox.getSelectedItem();
        }else{
            return null;
        }
    }
    
    public boolean hasTimerSetting(){
        return timeSettingPanel.hasTimerSetting();
    }
    
    public int getMainTime(){
        return timeSettingPanel.getMainTime();
    }
    
    public int getByoYomiTime(){
        return timeSettingPanel.getByoYomiTime();
    }
    
    public int getByoYomiStones(){
        return timeSettingPanel.getByoYomiStones();
    }
    
    // <editor-fold defaultstate="collapsed" desc=" Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bulackButtonGroup = new javax.swing.ButtonGroup();
        whiteButtonGroup = new javax.swing.ButtonGroup();
        blackPanel = new javax.swing.JPanel();
        radioBlackHuman = new javax.swing.JRadioButton();
        radioBlackCom = new javax.swing.JRadioButton();
        blackComboBox = new javax.swing.JComboBox();
        leftPanel = new javax.swing.JPanel();
        whitePanel = new javax.swing.JPanel();
        radioWhiteHuman = new javax.swing.JRadioButton();
        radioWhiteCom = new javax.swing.JRadioButton();
        whiteComboBox = new javax.swing.JComboBox();
        engineSettingButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();

        blackPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Black"));
        bulackButtonGroup.add(radioBlackHuman);
        radioBlackHuman.setText("Human");
        radioBlackHuman.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        radioBlackHuman.setMargin(new java.awt.Insets(0, 0, 0, 0));

        bulackButtonGroup.add(radioBlackCom);
        radioBlackCom.setText("Computer :");
        radioBlackCom.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        radioBlackCom.setMargin(new java.awt.Insets(0, 0, 0, 0));

        javax.swing.GroupLayout blackPanelLayout = new javax.swing.GroupLayout(blackPanel);
        blackPanel.setLayout(blackPanelLayout);
        blackPanelLayout.setHorizontalGroup(
            blackPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(blackPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(blackPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(blackPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(radioBlackCom)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(blackComboBox, 0, 270, Short.MAX_VALUE))
                    .addComponent(radioBlackHuman))
                .addContainerGap())
        );
        blackPanelLayout.setVerticalGroup(
            blackPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, blackPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(radioBlackHuman)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(blackPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radioBlackCom)
                    .addComponent(blackComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        leftPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        javax.swing.GroupLayout leftPanelLayout = new javax.swing.GroupLayout(leftPanel);
        leftPanel.setLayout(leftPanelLayout);
        leftPanelLayout.setHorizontalGroup(
            leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 192, Short.MAX_VALUE)
        );
        leftPanelLayout.setVerticalGroup(
            leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 115, Short.MAX_VALUE)
        );

        whitePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("White"));
        whiteButtonGroup.add(radioWhiteHuman);
        radioWhiteHuman.setText("Human");
        radioWhiteHuman.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        radioWhiteHuman.setMargin(new java.awt.Insets(0, 0, 0, 0));

        whiteButtonGroup.add(radioWhiteCom);
        radioWhiteCom.setText("Computer :");
        radioWhiteCom.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        radioWhiteCom.setMargin(new java.awt.Insets(0, 0, 0, 0));

        javax.swing.GroupLayout whitePanelLayout = new javax.swing.GroupLayout(whitePanel);
        whitePanel.setLayout(whitePanelLayout);
        whitePanelLayout.setHorizontalGroup(
            whitePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(whitePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(whitePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(whitePanelLayout.createSequentialGroup()
                        .addComponent(radioWhiteCom)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(whiteComboBox, 0, 270, Short.MAX_VALUE))
                    .addComponent(radioWhiteHuman))
                .addContainerGap())
        );
        whitePanelLayout.setVerticalGroup(
            whitePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, whitePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(radioWhiteHuman)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(whitePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radioWhiteCom, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(whiteComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        engineSettingButton.setText("Engine Setting");
        engineSettingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                engineSettingButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGap(272, 272, 272)
                            .addComponent(engineSettingButton))
                        .addComponent(leftPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(blackPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(whitePanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(blackPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(whitePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(engineSettingButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(leftPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    
    private void engineSettingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_engineSettingButtonActionPerformed
        GtpEngineSettingPanel panel = new GtpEngineSettingPanel();
        
        String[] options = {CLOSE};
        int retval = JOptionPane.showOptionDialog(goGame.getWindow(), panel, "Engine Setting",
                                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                                    null, options, options[0]);
    }//GEN-LAST:event_engineSettingButtonActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox blackComboBox;
    private javax.swing.JPanel blackPanel;
    private javax.swing.ButtonGroup bulackButtonGroup;
    private javax.swing.JButton engineSettingButton;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel leftPanel;
    private javax.swing.JRadioButton radioBlackCom;
    private javax.swing.JRadioButton radioBlackHuman;
    private javax.swing.JRadioButton radioWhiteCom;
    private javax.swing.JRadioButton radioWhiteHuman;
    private javax.swing.ButtonGroup whiteButtonGroup;
    private javax.swing.JComboBox whiteComboBox;
    private javax.swing.JPanel whitePanel;
    // End of variables declaration//GEN-END:variables
}