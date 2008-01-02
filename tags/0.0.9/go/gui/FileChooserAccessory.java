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

package go.gui;

import go.board.gui.PreviewPanel;
import java.awt.BorderLayout;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

public class FileChooserAccessory extends javax.swing.JPanel{
    private JPanel previewPanel;
    
    public FileChooserAccessory(JFileChooser fs) {
        previewPanel = new PreviewPanel(fs);
        
        initComponents();
        
        previewPanelBase.setLayout(new BorderLayout());
        previewPanelBase.add(previewPanel, BorderLayout.CENTER);
    }

    public String getCharset(){
        return (String)charsetComboBox.getSelectedItem();
    }
   
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        charsetLabel = new javax.swing.JLabel();
        previewPanelBase = new javax.swing.JPanel();
        charsetComboBox = new javax.swing.JComboBox();

        charsetLabel.setText("Charset");

        previewPanelBase.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        previewPanelBase.setMaximumSize(new java.awt.Dimension(240, 240));
        previewPanelBase.setMinimumSize(new java.awt.Dimension(240, 240));
        previewPanelBase.setPreferredSize(new java.awt.Dimension(240, 240));

        javax.swing.GroupLayout previewPanelBaseLayout = new javax.swing.GroupLayout(previewPanelBase);
        previewPanelBase.setLayout(previewPanelBaseLayout);
        previewPanelBaseLayout.setHorizontalGroup(
            previewPanelBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 236, Short.MAX_VALUE)
        );
        previewPanelBaseLayout.setVerticalGroup(
            previewPanelBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 236, Short.MAX_VALUE)
        );

        charsetComboBox.setEditable(true);
        charsetComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "UTF-8", "SJIS", "GB2312" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(previewPanelBase, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(charsetLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(charsetComboBox, 0, 184, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(previewPanelBase, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(charsetLabel)
                    .addComponent(charsetComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox charsetComboBox;
    private javax.swing.JLabel charsetLabel;
    private javax.swing.JPanel previewPanelBase;
    // End of variables declaration//GEN-END:variables
}