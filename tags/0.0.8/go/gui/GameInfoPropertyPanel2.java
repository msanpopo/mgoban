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

import sgf.property.GameInfoProperty;
import sgf.property.PropertyId;

public class GameInfoPropertyPanel2 extends javax.swing.JPanel {
    private static final String MISC = "いろいろ";
    private static final String CREDITS = "クレジット";
    private static final String RESULT = "結果";
    private static final String OPENING = "布石";
    private static final String GAME_COMMENT = "コメント";
    private static final String SOURCE = "出典";
    private static final String ANNOTATOR = "注釈者";
    private static final String USER = "入力者";
    private static final String COPYRIGHT = "コピーライト";
    
    private GameInfoProperty prop;

    public GameInfoPropertyPanel2(GameInfoProperty prop) {
        this.prop = prop;
        
        initComponents();
        
        miscLabel.setText(MISC);
        creditsLabel.setText(CREDITS);
        reLabel.setText(RESULT);
        onLabel.setText(OPENING);
        gcLabel.setText(GAME_COMMENT);
        soLabel.setText(SOURCE);
        anLabel.setText(ANNOTATOR);
        usLabel.setText(USER);
        cpLabel.setText(COPYRIGHT);
        
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

        miscLabel.setFont(new java.awt.Font("Dialog", 1, 14));
        miscLabel.setText("Misc");

        reLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        reLabel.setText("Result :");

        soLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        soLabel.setText("Source :");

        anLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        anLabel.setText("Annotator :");

        usLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        usLabel.setText("User :");

        cpLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        cpLabel.setText("Copyright :");

        onLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        onLabel.setText("Opening :");

        gcLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        gcLabel.setText("Comment :");

        gcTextArea.setColumns(20);
        gcTextArea.setRows(3);
        jScrollPane1.setViewportView(gcTextArea);

        creditsLabel.setFont(new java.awt.Font("Dialog", 1, 14));
        creditsLabel.setText("Credits");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(miscLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(reLabel))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(onLabel))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(gcLabel))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(soLabel))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(anLabel))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(usLabel))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(cpLabel)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                            .addComponent(cpTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                            .addComponent(usTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                            .addComponent(anTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                            .addComponent(soTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                            .addComponent(onTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                            .addComponent(reTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(creditsLabel)))
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