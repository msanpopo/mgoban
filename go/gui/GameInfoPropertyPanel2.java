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

import sgf.property.GameInfoProperty;
import sgf.property.PropertyId;

public class GameInfoPropertyPanel2 extends javax.swing.JPanel {
    
    private GameInfoProperty prop;

    public GameInfoPropertyPanel2(GameInfoProperty prop) {
        this.prop = prop;
        
        initComponents();
        
        reTextField.setText(prop.getString(PropertyId.RE));
        onTextField.setText(prop.getString(PropertyId.ON));
        gcTextArea.setText(prop.getString(PropertyId.GC));
        soTextField.setText(prop.getString(PropertyId.SO));
        anTextField.setText(prop.getString(PropertyId.AN));
        usTextField.setText(prop.getString(PropertyId.US));
        cpTextField.setText(prop.getString(PropertyId.CP));
    }
    
    public void done(){
        String re = reTextField.getText();
        String on = onTextField.getText();
        String gc = gcTextArea.getText();
        String so = soTextField.getText();
        String an = anTextField.getText();
        String us = usTextField.getText();
        String cp = cpTextField.getText();
        
        prop.setString(PropertyId.RE, re);
        prop.setString(PropertyId.ON, on);
        prop.setString(PropertyId.GC, gc);
        prop.setString(PropertyId.SO, so);
        prop.setString(PropertyId.AN, an);
        prop.setString(PropertyId.US, us);
        prop.setString(PropertyId.CP, cp);
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        miscLabel = new javax.swing.JLabel();
        reLabel = new javax.swing.JLabel();
        soLabel = new javax.swing.JLabel();
        anLabel = new javax.swing.JLabel();
        usLabel = new javax.swing.JLabel();
        cpLabel = new javax.swing.JLabel();
        onLabel = new javax.swing.JLabel();
        gcLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        gcTextArea = new javax.swing.JTextArea();
        onTextField = new javax.swing.JTextField();
        reTextField = new javax.swing.JTextField();
        cpTextField = new javax.swing.JTextField();
        usTextField = new javax.swing.JTextField();
        anTextField = new javax.swing.JTextField();
        soTextField = new javax.swing.JTextField();
        creditsLabel = new javax.swing.JLabel();

        miscLabel.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("app/resource/Resource"); // NOI18N
        miscLabel.setText(bundle.getString("Misc")); // NOI18N

        reLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        reLabel.setText(bundle.getString("Result")); // NOI18N

        soLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        soLabel.setText(bundle.getString("Source")); // NOI18N

        anLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        anLabel.setText(bundle.getString("Annotator")); // NOI18N

        usLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        usLabel.setText(bundle.getString("User")); // NOI18N

        cpLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        cpLabel.setText(bundle.getString("Copyright")); // NOI18N

        onLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        onLabel.setText(bundle.getString("Opening")); // NOI18N

        gcLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        gcLabel.setText(bundle.getString("Comment")); // NOI18N

        gcTextArea.setColumns(20);
        gcTextArea.setRows(3);
        jScrollPane1.setViewportView(gcTextArea);

        creditsLabel.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        creditsLabel.setText(bundle.getString("Credits")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(miscLabel)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(reLabel)
                            .addComponent(onLabel)
                            .addComponent(gcLabel)
                            .addComponent(soLabel)
                            .addComponent(anLabel)
                            .addComponent(usLabel)
                            .addComponent(cpLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
                            .addComponent(cpTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
                            .addComponent(usTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
                            .addComponent(anTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
                            .addComponent(soTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
                            .addComponent(onTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
                            .addComponent(reTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(creditsLabel))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {anLabel, cpLabel, creditsLabel, gcLabel, miscLabel, onLabel, reLabel, soLabel, usLabel});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(miscLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(reTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(reLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(onTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(onLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(gcLabel)
                        .addGap(68, 68, 68)
                        .addComponent(creditsLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
                        .addGap(43, 43, 43)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(soTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(soLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(anTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(anLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(usTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(usLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cpTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cpLabel))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel anLabel;
    private javax.swing.JTextField anTextField;
    private javax.swing.JLabel cpLabel;
    private javax.swing.JTextField cpTextField;
    private javax.swing.JLabel creditsLabel;
    private javax.swing.JLabel gcLabel;
    private javax.swing.JTextArea gcTextArea;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel miscLabel;
    private javax.swing.JLabel onLabel;
    private javax.swing.JTextField onTextField;
    private javax.swing.JLabel reLabel;
    private javax.swing.JTextField reTextField;
    private javax.swing.JLabel soLabel;
    private javax.swing.JTextField soTextField;
    private javax.swing.JLabel usLabel;
    private javax.swing.JTextField usTextField;
    // End of variables declaration//GEN-END:variables
}