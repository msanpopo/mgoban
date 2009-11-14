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
package app.wing;

import app.App;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;
import wing.GoServer;

public class LoginPanel extends javax.swing.JPanel {

    private static final String ACCOUNT_LIST = "アカウント一覧";
    private static final String LOGIN_NAME = "ログイン名";
    private static final String PASSWORD = "パスワード";
    private static final String CLOSE = "閉じる";
    private static final String EDIT_SETTING = "サーバーの設定";
    private NetGo netGo;
    private DefaultListModel model;

    public LoginPanel(NetGo netGo) {
        this.netGo = netGo;

        initComponents();

        updateModel();

        TitledBorder border = (TitledBorder) accountListPanel.getBorder();
        border.setTitle(ACCOUNT_LIST);

        loginNameLabel.setText(LOGIN_NAME);
        passwordLabel.setText(PASSWORD);

        editSettingButton.setText(EDIT_SETTING);
    }

    private void updateModel() {
        model = new DefaultListModel();

        BufferedReader in = null;
        try {
            InputStream defaultStream = App.getInstance().getDeaultServerSettingStream();
            File userFile = App.getInstance().getServerSettingFile();

            if (userFile.exists() == true) {
                in = new BufferedReader(new InputStreamReader(new FileInputStream(userFile), "UTF-8"));
            } else {
                in = new BufferedReader(new InputStreamReader(defaultStream, "UTF-8"));
            }

            while (true) {
                String l = in.readLine();

                if (l == null || l.equals("")) {
                    break;
                }

                model.addElement(new GoServer(l));
            }
        } catch (Exception ex) {
            System.out.println(this.getClass() + ":server.cfg read fail:" + ex);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

        accountList.setModel(model);
    }

    public boolean connect(NetGo netGo) {
        GoServer gs = (GoServer) accountList.getSelectedValue();
        
        return netGo.connect(gs, loginNameField.getText(), new String(passwordField.getPassword()));
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        accountListPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        accountList = new javax.swing.JList();
        editSettingButton = new javax.swing.JButton();
        passwordLabel = new javax.swing.JLabel();
        loginNameLabel = new javax.swing.JLabel();
        passwordField = new javax.swing.JPasswordField();
        loginNameField = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();

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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editSettingButton)
                .addContainerGap())
        );

        passwordLabel.setText("Password :");

        loginNameLabel.setText("Login Name :");
        loginNameLabel.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                    .addComponent(accountListPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(passwordLabel)
                            .addComponent(loginNameLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(passwordField, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                            .addComponent(loginNameField, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
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
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    private void accountListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_accountListValueChanged
        // System.out.println("LoginDialog valueChanged : " + evt);

        GoServer gs = (GoServer) accountList.getSelectedValue();
        if (gs == null) {
            return;
        }

        loginNameField.setText(gs.getDefaultLoginName());
        passwordField.setText(gs.getDefaultPassword());
    }//GEN-LAST:event_accountListValueChanged

    private void editSettingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editSettingButtonActionPerformed
        GoServerSettingPanel panel = new GoServerSettingPanel();

        String[] options = {CLOSE};
        int retval = JOptionPane.showOptionDialog(this, panel, "Server Setting",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
        updateModel();
    }//GEN-LAST:event_editSettingButtonActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList accountList;
    private javax.swing.JPanel accountListPanel;
    private javax.swing.JButton editSettingButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField loginNameField;
    private javax.swing.JLabel loginNameLabel;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JLabel passwordLabel;
    // End of variables declaration//GEN-END:variables
}
