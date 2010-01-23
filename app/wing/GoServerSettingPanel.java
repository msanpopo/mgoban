/*
 *  mGoban - GUI for Go
 *  Copyright (C) 2007, 2009, 2010  sanpo
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
import javax.swing.DefaultListModel;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import javax.swing.JOptionPane;
import wing.GoServer;

public class GoServerSettingPanel extends javax.swing.JPanel {
    private static final String EDIT = "編集";
    private static final String ADD = "追加";
    private static final String COPY = "コピー";
    private static final String DELETE = "削除";
    private static final String NAME = "名前：";
    private static final String HOST_NAME = "ホスト名：";
    private static final String PORT = "ポート番号：";
    private static final String LOGIN_NAME = "ログイン名：";
    private static final String PASSWORD = "パスワード：";
    private static final String ENCODING = "文字セット：";
    
    private DefaultListModel model;
	
    public GoServerSettingPanel() {
	
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
		System.out.println(this.getClass() + ":server.cfg.cfg :" + l);
		if (l == null || l.equals(""))
		    break;
		
		model.addElement(new GoServer(l));
	    }
	} catch (Exception ex) {
	    System.out.println(this.getClass() + ":server.cfg.cfg read fail:" + ex);
	}finally{
            if(in != null){
                try {
                    in.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
	
	initComponents();
	
	editButton.setText(EDIT);
        addButton.setText(ADD);
        copyButton.setText(COPY);
        deleteButton.setText(DELETE);
	nameLabel.setText(NAME);
	hostNameLabel.setText(HOST_NAME);
        portLabel.setText(PORT);
        loginNameLabel.setText(LOGIN_NAME);
        passwordLabel.setText(PASSWORD);
        encodingLabel.setText(ENCODING);
        
	
	editNameLabel.setText(NAME);
	editHostNameLabel.setText(HOST_NAME);
        editPortLabel.setText(PORT);
        editLoginNameLabel.setText(LOGIN_NAME);
        editPasswordLabel.setText(PASSWORD);
        editEncodingLabel.setText(ENCODING);
	
	goServerList.setModel(model);
    }
    
        
    private void writeServerList(){
        PrintWriter out = null;
	
        try {
            File userFile = App.getInstance().getServerSettingFile();
            out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(userFile), "UTF-8"));
            int n = model.getSize();
	    int i;
	    for(i = 0; i < n; ++i){
		GoServer gs = (GoServer)(model.getElementAt(i));
		gs.write(out);
	    }
	} catch (Exception ex) {
	    System.err.println(this.getClass() + " : server.cfg write");
	} finally{
            if(out != null){
                out.close();
            }
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        editPanel = new javax.swing.JPanel();
        editNameLabel = new javax.swing.JLabel();
        editHostNameLabel = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();
        hostNameField = new javax.swing.JTextField();
        editPortLabel = new javax.swing.JLabel();
        editLoginNameLabel = new javax.swing.JLabel();
        editPasswordLabel = new javax.swing.JLabel();
        editEncodingLabel = new javax.swing.JLabel();
        portField = new javax.swing.JTextField();
        loginNameField = new javax.swing.JTextField();
        passwordField = new javax.swing.JTextField();
        encodingField = new javax.swing.JTextField();
        editButton = new javax.swing.JButton();
        addButton = new javax.swing.JButton();
        copyButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        goServerList = new javax.swing.JList();
        jSeparator1 = new javax.swing.JSeparator();
        nameLabel = new javax.swing.JLabel();
        hostNameLabel = new javax.swing.JLabel();
        serverNameLabel = new javax.swing.JLabel();
        serverHostNameLabel = new javax.swing.JLabel();
        portLabel = new javax.swing.JLabel();
        loginNameLabel = new javax.swing.JLabel();
        passwordLabel = new javax.swing.JLabel();
        encodingLabel = new javax.swing.JLabel();
        serverPortLabel = new javax.swing.JLabel();
        serverLoginNameLabel = new javax.swing.JLabel();
        serverPasswordLabel = new javax.swing.JLabel();
        serverEncodingLabel = new javax.swing.JLabel();

        editNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        editNameLabel.setText("Name :");

        editHostNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        editHostNameLabel.setText("Host Name :");

        nameField.setPreferredSize(null);

        hostNameField.setPreferredSize(null);

        editPortLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        editPortLabel.setText("Port :");

        editLoginNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        editLoginNameLabel.setText("Login Name :");

        editPasswordLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        editPasswordLabel.setText("Password :");

        editEncodingLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        editEncodingLabel.setText("Encoding :");

        javax.swing.GroupLayout editPanelLayout = new javax.swing.GroupLayout(editPanel);
        editPanel.setLayout(editPanelLayout);
        editPanelLayout.setHorizontalGroup(
            editPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(editPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(editLoginNameLabel)
                    .addComponent(editPortLabel)
                    .addComponent(editHostNameLabel)
                    .addComponent(editNameLabel)
                    .addComponent(editPasswordLabel)
                    .addComponent(editEncodingLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(editPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(encodingField, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
                    .addComponent(passwordField, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
                    .addComponent(loginNameField, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
                    .addComponent(portField, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
                    .addComponent(hostNameField, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
                    .addComponent(nameField, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE))
                .addContainerGap())
        );
        editPanelLayout.setVerticalGroup(
            editPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(editPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(editPanelLayout.createSequentialGroup()
                        .addComponent(editNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editHostNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editPortLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editLoginNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editPasswordLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editEncodingLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE))
                    .addGroup(editPanelLayout.createSequentialGroup()
                        .addComponent(nameField, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hostNameField, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(portField, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(loginNameField, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(passwordField, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(encodingField, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)))
                .addContainerGap())
        );

        editButton.setText("Edit");
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });

        addButton.setText("Add");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        copyButton.setText("Copy");
        copyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyButtonActionPerformed(evt);
            }
        });

        deleteButton.setText("Delete");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        goServerList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                goServerListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(goServerList);

        nameLabel.setText("Name :");

        hostNameLabel.setText("Host Name :");

        portLabel.setText("Port :");

        loginNameLabel.setText("Login Name :");

        passwordLabel.setText("Password :");

        encodingLabel.setText("Encoding :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(deleteButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(editButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(addButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(copyButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(hostNameLabel)
                            .addComponent(nameLabel)
                            .addComponent(portLabel)
                            .addComponent(loginNameLabel)
                            .addComponent(passwordLabel)
                            .addComponent(encodingLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(serverLoginNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                            .addComponent(serverHostNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                            .addComponent(serverNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                            .addComponent(serverPortLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                            .addComponent(serverPasswordLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                            .addComponent(serverEncodingLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(editButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(copyButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteButton))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nameLabel)
                    .addComponent(serverNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(hostNameLabel)
                    .addComponent(serverHostNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(portLabel)
                    .addComponent(serverPortLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(loginNameLabel)
                    .addComponent(serverLoginNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordLabel)
                    .addComponent(serverPasswordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(encodingLabel)
                    .addComponent(serverEncodingLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
	GoServer gs = (GoServer)goServerList.getSelectedValue();
	if(gs == null)
	    return;
	model.removeElement(gs);
	writeServerList();
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void copyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copyButtonActionPerformed
	GoServer gs = (GoServer)goServerList.getSelectedValue();
	if(gs == null)
	    return;
	model.addElement(new GoServer(gs));
	writeServerList();
    }//GEN-LAST:event_copyButtonActionPerformed

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
	model.addElement(new GoServer());
	writeServerList();
    }//GEN-LAST:event_addButtonActionPerformed

    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed
	GoServer gs = (GoServer)goServerList.getSelectedValue();
	nameField.setText(gs.getName());
	hostNameField.setText(gs.getHostName());
        portField.setText(Integer.toString(gs.getPort()));
        loginNameField.setText(gs.getDefaultLoginName());
        passwordField.setText(gs.getDefaultPassword());
        encodingField.setText(gs.getEncoding());
	int retval = JOptionPane.showOptionDialog(this, editPanel, "Go Server Edit", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
	if(retval == JOptionPane.OK_OPTION){
	    gs.setName(nameField.getText());
	    gs.setHostName(hostNameField.getText());
            gs.setPort(Integer.parseInt(portField.getText()));
            gs.setDefaultLoginName(loginNameField.getText());
            gs.setDefaultPassword(passwordField.getText());
            gs.setEncoding(encodingField.getText());
	    writeServerList();
	    goServerList.repaint();
	    
	    serverNameLabel.setText(gs.getName());
            serverHostNameLabel.setText(gs.getHostName());
            serverPortLabel.setText(Integer.toString(gs.getPort()));
	    serverLoginNameLabel.setText(gs.getDefaultLoginName());
            serverPasswordLabel.setText(gs.getDefaultPassword());
            serverEncodingLabel.setText(gs.getEncoding());
	}
    }//GEN-LAST:event_editButtonActionPerformed

    private void goServerListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_goServerListValueChanged
        GoServer gs = (GoServer)goServerList.getSelectedValue();
        if(gs == null){
            return;
        }
        
        serverNameLabel.setText(gs.getName());
        serverHostNameLabel.setText(gs.getHostName());
        serverPortLabel.setText(Integer.toString(gs.getPort()));
        serverLoginNameLabel.setText(gs.getDefaultLoginName());
        serverPasswordLabel.setText(gs.getDefaultPassword());
        serverEncodingLabel.setText(gs.getEncoding());
    }//GEN-LAST:event_goServerListValueChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton copyButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton editButton;
    private javax.swing.JLabel editEncodingLabel;
    private javax.swing.JLabel editHostNameLabel;
    private javax.swing.JLabel editLoginNameLabel;
    private javax.swing.JLabel editNameLabel;
    private javax.swing.JPanel editPanel;
    private javax.swing.JLabel editPasswordLabel;
    private javax.swing.JLabel editPortLabel;
    private javax.swing.JTextField encodingField;
    private javax.swing.JLabel encodingLabel;
    private javax.swing.JList goServerList;
    private javax.swing.JTextField hostNameField;
    private javax.swing.JLabel hostNameLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField loginNameField;
    private javax.swing.JLabel loginNameLabel;
    private javax.swing.JTextField nameField;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField passwordField;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JTextField portField;
    private javax.swing.JLabel portLabel;
    private javax.swing.JLabel serverEncodingLabel;
    private javax.swing.JLabel serverHostNameLabel;
    private javax.swing.JLabel serverLoginNameLabel;
    private javax.swing.JLabel serverNameLabel;
    private javax.swing.JLabel serverPasswordLabel;
    private javax.swing.JLabel serverPortLabel;
    // End of variables declaration//GEN-END:variables
}
