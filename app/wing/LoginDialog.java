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
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;

import wing.GoServer;
import app.MainWindow;
import app.wing.NetGo;
import app.wing.GoServerSettingPanel;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

@SuppressWarnings("serial")
public class LoginDialog extends JFrame {
    private static final String ACCOUNT_LIST = "アカウント一覧";
    private static final String LOGIN_NAME = "ログイン名";
    private static final String PASSWORD = "パスワード";
    private static final String LOGIN = "接続";
    private static final String CLOSE = "閉じる";
    private static final String EDIT_SETTING = "サーバーの設定";
    
    private MainWindow mainWindow;
    private DefaultListModel model;
    
    public LoginDialog(MainWindow window) {
        mainWindow = window;
        
        initComponents();
        
        updateModel();
        
        TitledBorder border = (TitledBorder)accountListPanel.getBorder();
        border.setTitle(ACCOUNT_LIST);
         
        loginNameLabel.setText(LOGIN_NAME);
        passwordLabel.setText(PASSWORD);
        
        loginButton.setText(LOGIN);
        closeButton.setText(CLOSE);
        
        editSettingButton.setText(EDIT_SETTING);
    }
    
    private void updateModel(){
        model = new DefaultListModel();
                
	BufferedReader in = null;
        try{
            InputStream defaultStream = App.getInstance().getDeaultServerSettingStream();
            File userFile = App.getInstance().getServerSettingFile();
            
            if(userFile.exists() == true){
                in = new BufferedReader(new InputStreamReader(new FileInputStream(userFile), "UTF-8"));
            }else{
                in = new BufferedReader(new InputStreamReader(defaultStream, "UTF-8"));
            }
            
            while (true) {
                String l = in.readLine();
                
                if (l == null || l.equals(""))
                    break;
                
                model.addElement(new GoServer(l));
            }
        } catch (Exception ex) {
            System.out.println(this.getClass() + ":server.cfg read fail:" + ex);
        }finally{
            if(in != null){
                try {
                    in.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        
        accountList.setModel(model);
    }
    
    private void connect(GoServer gs, String loginName, String pass){
        if(gs == null){
            return;
        }
        
        NetGo netGo = new NetGo(gs);
        mainWindow.addComponentListener(netGo);
        
        if (netGo.connect(loginName, pass)){

        }else{
            System.err.println(this.getClass() + " :connect() fail:" + gs.toString());
        }
        dispose();
    }

    // <editor-fold defaultstate="collapsed" desc=" Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        closeButton = new javax.swing.JButton();
        loginButton = new javax.swing.JButton();
        loginNameLabel = new javax.swing.JLabel();
        passwordLabel = new javax.swing.JLabel();
        loginNameField = new javax.swing.JTextField();
        accountListPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        accountList = new javax.swing.JList();
        editSettingButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        passwordField = new javax.swing.JPasswordField();

        setTitle("Login");
        setLocationByPlatform(true);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                formComponentHidden(evt);
            }
        });

        closeButton.setText("Close");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActionPerformed(evt);
            }
        });

        loginButton.setText("Login");
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActionPerformed(evt);
            }
        });

        loginNameLabel.setText("Login Name :");
        loginNameLabel.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        passwordLabel.setText("Password :");

        accountListPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Account List"));
        accountList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        accountList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                accountListValueChanged(evt);
            }
        });

        jScrollPane1.setViewportView(accountList);

        editSettingButton.setText("Edit Setting");
        editSettingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editSettingButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout accountListPanelLayout = new javax.swing.GroupLayout(accountListPanel);
        accountListPanel.setLayout(accountListPanelLayout);
        accountListPanelLayout.setHorizontalGroup(
            accountListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(accountListPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(accountListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                    .addComponent(editSettingButton, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        accountListPanelLayout.setVerticalGroup(
            accountListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, accountListPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editSettingButton)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(accountListPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(passwordLabel)
                            .addComponent(loginNameLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(passwordField, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                            .addComponent(loginNameField, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(loginButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(accountListPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(loginNameLabel)
                    .addComponent(loginNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordLabel)
                    .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(closeButton)
                    .addComponent(loginButton))
                .addContainerGap())
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void editSettingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editSettingButtonActionPerformed
        GoServerSettingPanel panel = new GoServerSettingPanel();
        
        String[] options = {CLOSE};
        int retval = JOptionPane.showOptionDialog(this, panel, "Server Setting",
                                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                                    null, options, options[0]);
        updateModel();
    }//GEN-LAST:event_editSettingButtonActionPerformed

    private void formComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentHidden
	dispose();
    }//GEN-LAST:event_formComponentHidden

    private void accountListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_accountListValueChanged
        // System.out.println("LoginDialog valueChanged : " + evt);
        
        GoServer gs = (GoServer)accountList.getSelectedValue();
        if(gs == null){
            return;
        }
        
        loginNameField.setText(gs.getDefaultLoginName());
        passwordField.setText(gs.getDefaultPassword());
    }//GEN-LAST:event_accountListValueChanged

    private void ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActionPerformed
        Object src = evt.getSource();
        
        GoServer gs = (GoServer)accountList.getSelectedValue();
        
        if(src == loginButton){
            connect(gs, loginNameField.getText(), new String(passwordField.getPassword()));
        }else if(src == closeButton){
            setVisible(false);
        }else{
        }
        
    }//GEN-LAST:event_ActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList accountList;
    private javax.swing.JPanel accountListPanel;
    private javax.swing.JButton closeButton;
    private javax.swing.JButton editSettingButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton loginButton;
    private javax.swing.JTextField loginNameField;
    private javax.swing.JLabel loginNameLabel;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JLabel passwordLabel;
    // End of variables declaration//GEN-END:variables
}