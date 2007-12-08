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

package app.sgf;

import app.App;
import gtp.GtpEngine;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.swing.DefaultListModel;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class GtpEngineSettingPanel extends javax.swing.JPanel {
    private static final String EDIT = "編集";
    private static final String ADD = "追加";
    private static final String COPY = "コピー";
    private static final String DELETE = "削除";
    private static final String NAME = "名前：";
    private static final String COMMAND = "コマンド：";
    private static final String DIR = "作業ディレクトリ：";
    
    private static final String SELECT = "選択";
    private static final String SELECT_COMMAND = "プログラムの選択";
    private static final String SELECT_DIRECTORY = "作業ディレクトリの選択";
    
    private DefaultListModel model;
	
    public GtpEngineSettingPanel() {
	
        model = new DefaultListModel();
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
		if (l == null || l.isEmpty()){
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
		model.addElement(engine);
	    }
	} catch (Exception ex) {
            System.out.println(this.getClass() + ":gtpengine.cfg read fail:" + ex);
        } finally{
            if(in != null){
                try{
                    in.close();
                }catch(IOException e){
                    
                }
            }
        }
	
	initComponents();
	
	editButton.setText(EDIT);
        addButton.setText(ADD);
        copyButton.setText(COPY);
        deleteButton.setText(DELETE);
	nameLabel.setText(NAME);
	commandLabel.setText(COMMAND);
	directoryLabel.setText(DIR);
        
	editNameLabel.setText(NAME);
	editCommandLabel.setText(COMMAND);
        editDirectoryLabel.setText(DIR);
	
        commandSelectButton.setText(SELECT);
        directorySelectButton.setText(SELECT);
        
	gtpEngineList.setModel(model);
    }
    
    private void writeEngineList(){
        PrintWriter out = null;
	try {
            File userFile = App.getInstance().getGtpSettingFile();
            out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(userFile), "UTF-8"));
	    int n = model.getSize();
	    int i;
	    for(i = 0; i < n; ++i){
		GtpEngine gs = (GtpEngine)(model.getElementAt(i));
		gs.write(out);
	    }
	} catch (Exception ex) {
	    System.err.println(this.getClass() + " : gtpengine.cfg write");
	} finally{
            out.close();
        }
    }

    private void showEngineInfo(GtpEngine engien) {
        engineNameLabel.setText(engien.getText());
        engineCommandLabel.setText(engien.getCommand());
        engineDirectoryLabel.setText(engien.getDirectory());
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        editPanel = new javax.swing.JPanel();
        editNameLabel = new javax.swing.JLabel();
        editCommandLabel = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();
        commandField = new javax.swing.JTextField();
        editDirectoryLabel = new javax.swing.JLabel();
        directoryField = new javax.swing.JTextField();
        commandSelectButton = new javax.swing.JButton();
        directorySelectButton = new javax.swing.JButton();
        editButton = new javax.swing.JButton();
        addButton = new javax.swing.JButton();
        copyButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        gtpEngineList = new javax.swing.JList();
        jSeparator1 = new javax.swing.JSeparator();
        nameLabel = new javax.swing.JLabel();
        commandLabel = new javax.swing.JLabel();
        engineNameLabel = new javax.swing.JLabel();
        engineCommandLabel = new javax.swing.JLabel();
        directoryLabel = new javax.swing.JLabel();
        engineDirectoryLabel = new javax.swing.JLabel();

        editNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        editNameLabel.setText("Name :");

        editCommandLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        editCommandLabel.setText("Command :");

        nameField.setPreferredSize(null);

        commandField.setPreferredSize(null);

        editDirectoryLabel.setText("Directory :");

        commandSelectButton.setText("Select");
        commandSelectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                commandSelectButtonActionPerformed(evt);
            }
        });

        directorySelectButton.setText("Select");
        directorySelectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                directorySelectButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout editPanelLayout = new javax.swing.GroupLayout(editPanel);
        editPanel.setLayout(editPanelLayout);
        editPanelLayout.setHorizontalGroup(
            editPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(editPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(editPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(editDirectoryLabel)
                        .addComponent(editNameLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(editCommandLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(editPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nameField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                    .addGroup(editPanelLayout.createSequentialGroup()
                        .addGroup(editPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(directoryField, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
                            .addComponent(commandField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(editPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(commandSelectButton)
                            .addComponent(directorySelectButton))))
                .addContainerGap())
        );
        editPanelLayout.setVerticalGroup(
            editPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(editPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editNameLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(editPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(commandSelectButton)
                    .addComponent(editCommandLabel)
                    .addComponent(commandField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(editPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editDirectoryLabel)
                    .addComponent(directorySelectButton)
                    .addComponent(directoryField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        gtpEngineList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                gtpEngineListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(gtpEngineList);

        nameLabel.setText("Name :");

        commandLabel.setText("Comman :");

        directoryLabel.setText("Directory :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(deleteButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(editButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(addButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(copyButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(commandLabel)
                            .addComponent(nameLabel)
                            .addComponent(directoryLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(engineDirectoryLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(engineNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(engineCommandLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE))))
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
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameLabel)
                    .addComponent(engineNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(commandLabel)
                    .addComponent(engineCommandLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(directoryLabel)
                    .addComponent(engineDirectoryLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
	GtpEngine ge = (GtpEngine)gtpEngineList.getSelectedValue();
	if(ge == null)
	    return;
	model.removeElement(ge);
	writeEngineList();
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void copyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copyButtonActionPerformed
	GtpEngine ge = (GtpEngine)gtpEngineList.getSelectedValue();
	if(ge == null)
	    return;
	model.addElement(new GtpEngine(ge.getText() + " copy", ge.getCommand(), ge.getDirectory()));
	writeEngineList();
    }//GEN-LAST:event_copyButtonActionPerformed

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
	model.addElement(new GtpEngine());
	writeEngineList();
    }//GEN-LAST:event_addButtonActionPerformed

    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed
	GtpEngine ge = (GtpEngine)gtpEngineList.getSelectedValue();
	nameField.setText(ge.getText());
	commandField.setText(ge.getCommand());
        directoryField.setText(ge.getDirectory());
	int retval = JOptionPane.showOptionDialog(this, editPanel, "GTP Engine Edit", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
	if(retval == JOptionPane.OK_OPTION){
	    ge.setText(nameField.getText());
	    ge.setCommand(commandField.getText());
            ge.setDirectory(directoryField.getText());
            
	    writeEngineList();
	    gtpEngineList.repaint();
	    
            showEngineInfo(ge);
	}
    }//GEN-LAST:event_editButtonActionPerformed

    private void gtpEngineListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_gtpEngineListValueChanged
        GtpEngine ge = (GtpEngine)gtpEngineList.getSelectedValue();
        if(ge == null){
            return;
        }
        showEngineInfo(ge);
    }//GEN-LAST:event_gtpEngineListValueChanged

    private void commandSelectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_commandSelectButtonActionPerformed
        File file = getFile(SELECT_COMMAND, JFileChooser.FILES_ONLY);
        if(file != null){
            GtpEngine ge = (GtpEngine)gtpEngineList.getSelectedValue();
            String command = file.getAbsolutePath();
            ge.setCommand(command);
            commandField.setText(command);
        }
    }//GEN-LAST:event_commandSelectButtonActionPerformed

    private void directorySelectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_directorySelectButtonActionPerformed
        File file = getFile(SELECT_DIRECTORY, JFileChooser.DIRECTORIES_ONLY);
        if(file != null){
            GtpEngine ge = (GtpEngine)gtpEngineList.getSelectedValue();
            String directory = file.getAbsolutePath();
            ge.setDirectory(directory);
            directoryField.setText(directory);
        }
    }//GEN-LAST:event_directorySelectButtonActionPerformed
    
    private File getFile(String title, int mode){
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle(title);
        chooser.setFileSelectionMode(mode);
        
        int returnVal = chooser.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
            File file = chooser.getSelectedFile();
            
            return file;
        }else{
            return null;
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JTextField commandField;
    private javax.swing.JLabel commandLabel;
    private javax.swing.JButton commandSelectButton;
    private javax.swing.JButton copyButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JTextField directoryField;
    private javax.swing.JLabel directoryLabel;
    private javax.swing.JButton directorySelectButton;
    private javax.swing.JButton editButton;
    private javax.swing.JLabel editCommandLabel;
    private javax.swing.JLabel editDirectoryLabel;
    private javax.swing.JLabel editNameLabel;
    private javax.swing.JPanel editPanel;
    private javax.swing.JLabel engineCommandLabel;
    private javax.swing.JLabel engineDirectoryLabel;
    private javax.swing.JLabel engineNameLabel;
    private javax.swing.JList gtpEngineList;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField nameField;
    private javax.swing.JLabel nameLabel;
    // End of variables declaration//GEN-END:variables
}