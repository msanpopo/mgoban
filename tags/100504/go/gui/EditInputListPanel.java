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

package go.gui;

import javax.swing.DefaultListModel;

public class EditInputListPanel extends javax.swing.JPanel {
    private static final String ADD = "追加";
    private static final String REPLACE = "変更";
    private static final String DELETE = "削除";
    
    private DefaultListModel model;
    
    private InputList list;
    
    public EditInputListPanel(InputList list) {
        this.list = list;
        
        initComponents();
        
        addButton.setText(ADD);
        replaceButton.setText(REPLACE);
        deleteButton.setText(DELETE);
        
        model = new DefaultListModel();
        
        for(String s : list.getCollection()){
            model.addElement(s);
        }
        
        textList.setModel(model);
    }
    
    public void write(){
        list.clear();
        
        int n = model.getSize();
        for(int i = 0; i < n; ++i){
            String text = (String)model.get(i);
            list.add(text);
        }
        list.write();
    }
    
    private void addText(String text) {
        model.addElement(text);
        write();
    }
    
    private void replaceText(int index, String text){
        model.remove(index);
        model.add(index, text);
        textList.setSelectedIndex(index);
        write();
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        textList = new javax.swing.JList();
        addButton = new javax.swing.JButton();
        replaceButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        textField = new javax.swing.JTextField();

        textList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                textListValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(textList);

        addButton.setText("Add");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        replaceButton.setText("Replace");
        replaceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                replaceButtonActionPerformed(evt);
            }
        });

        deleteButton.setText("Delete");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        textField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(replaceButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteButton))
                    .addComponent(textField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {addButton, deleteButton, replaceButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(deleteButton)
                    .addComponent(replaceButton)
                    .addComponent(addButton))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        String text = textField.getText();
        if(text.isEmpty() == false){
            addText(text);
            textField.setText("");
        }
}//GEN-LAST:event_addButtonActionPerformed

    private void replaceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_replaceButtonActionPerformed
        String newText = textField.getText();
        if(newText.isEmpty() == false){
            int index = textList.getSelectedIndex();
            if(index != -1){
                replaceText(index, newText);
                textField.setText("");
            }
        }
}//GEN-LAST:event_replaceButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        String text = (String)textList.getSelectedValue();
	if(text != null){
            model.removeElement(text);
            textField.setText("");
            write();
        }
}//GEN-LAST:event_deleteButtonActionPerformed

    private void textListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_textListValueChanged
        String text = (String)textList.getSelectedValue();
	if(text != null){
            textField.setText(text);
        }
    }//GEN-LAST:event_textListValueChanged

    private void textFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldActionPerformed
        String text = textField.getText();
        if(text.isEmpty() == false){
            int index = textList.getSelectedIndex();
            if(index == -1){
                addText(text);
            }else{
                replaceText(index, text);
            }
            textField.setText("");
        }
    }//GEN-LAST:event_textFieldActionPerformed
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton replaceButton;
    private javax.swing.JTextField textField;
    private javax.swing.JList textList;
    // End of variables declaration//GEN-END:variables
}