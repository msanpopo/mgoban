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

import go.GoColor;
import go.GoVertex;
import go.Handicap;
import java.util.ArrayList;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;
import sgf.GoNode;
import sgf.property.GameInfoProperty;
import sgf.property.SetupProperty;

public class NewGameTreePanel extends javax.swing.JPanel {
    private SpinnerListModel boardSizeModel;
    private SpinnerListModel handicapModel;
    private SpinnerNumberModel komiModel;
    
    public NewGameTreePanel() {
        int[] boardSizeArray = {9, 13, 19};
        ArrayList<Integer> boardSizeList = arrayToList(boardSizeArray);
        
        int[] handicapArray = {0, 2, 3, 4, 5, 6, 7, 8, 9};
        ArrayList<Integer> handicapList = arrayToList(handicapArray);
        
        boardSizeModel = new SpinnerListModel(boardSizeList);
        handicapModel = new SpinnerListModel(handicapList);
        komiModel = new SpinnerNumberModel(6.5, -100.0, 100.0, 0.5);
        
        initComponents();

        boardSizeSpinner.setModel(boardSizeModel);
        handicapSpinner.setModel(handicapModel);
        komiSpinner.setModel(komiModel);
        
        boardSizeSpinner.setValue(new Integer(19));
        
        handicapCheckBox.setSelected(true);
        komiCheckBox.setSelected(true);
    }
    
    private ArrayList<Integer> arrayToList(int[] a){
        ArrayList<Integer> list = new ArrayList<Integer>();
        
        for(int i : a){
            list.add(new Integer(i));
        }
        
        return list;
    }
    
    private int getBoardSize(){
        Integer boardSize = (Integer)boardSizeModel.getValue();
        return boardSize.intValue();
    }
    
    private int getHandicap(){
        int handicap = (Integer)handicapModel.getValue();
        return handicap;
    }
    
    private double getKomi(){
        return komiModel.getNumber().doubleValue();
    }
    
    private boolean handicapEnabled(){
        return handicapCheckBox.isSelected();
    }
    
    private boolean komiEnabled(){
        return komiCheckBox.isSelected();
    }
    
    public GoNode getRootNode(){
        int boardSize = getBoardSize();
        int handicap = getHandicap();
        double komi = getKomi();
        
        GoNode root = GoNode.createRootNode(boardSize);

        if(handicapEnabled() || komiEnabled()){
            GameInfoProperty gip = new GameInfoProperty();
            root.setGameInfoProperty(gip);
            
            if(handicapEnabled() && handicap != 0){
                gip.setHandicap(handicap);
                
                SetupProperty sp = new SetupProperty();
                root.setSetupProperty(sp);
                
                GoVertex[] fixedHandicap = Handicap.getVertex(boardSize, handicap);
                for (GoVertex v : fixedHandicap) {
                    sp.addBlack(v);
                }
            
            }
            if(komiEnabled()){
                gip.setKomi(komi);
            }

            gip.setPlayerName(GoColor.BLACK, "Black");
            gip.setPlayerName(GoColor.WHITE, "White");
        }

        return root;
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        boardSizeLabel = new javax.swing.JLabel();
        komiLabel = new javax.swing.JLabel();
        handicapLabel = new javax.swing.JLabel();
        komiSpinner = new javax.swing.JSpinner();
        boardSizeSpinner = new javax.swing.JSpinner();
        handicapSpinner = new javax.swing.JSpinner();
        handicapCheckBox = new javax.swing.JCheckBox();
        komiCheckBox = new javax.swing.JCheckBox();
        jSeparator1 = new javax.swing.JSeparator();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("app/resource/Resource"); // NOI18N
        boardSizeLabel.setText(bundle.getString("BoardSize")); // NOI18N

        komiLabel.setText(bundle.getString("Komi")); // NOI18N

        handicapLabel.setText(bundle.getString("Handicap")); // NOI18N

        handicapSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                handicapSpinnerStateChanged(evt);
            }
        });

        handicapCheckBox.setText(" ");
        handicapCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        handicapCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handicapCheckBoxActionPerformed(evt);
            }
        });

        komiCheckBox.setText(" ");
        komiCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        komiCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                komiCheckBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(boardSizeLabel)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(komiCheckBox)
                                    .addComponent(handicapCheckBox))
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(handicapLabel)
                                    .addComponent(komiLabel))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(komiSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(handicapSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(boardSizeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                    .addComponent(boardSizeLabel)
                    .addComponent(boardSizeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(handicapLabel)
                    .addComponent(handicapCheckBox)
                    .addComponent(handicapSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(komiSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(komiLabel)
                    .addComponent(komiCheckBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    
    private void handicapSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_handicapSpinnerStateChanged
        int handicap = (Integer)handicapModel.getValue();
        if(handicap == 0){
            komiModel.setValue(6.5);
        }else{
            komiModel.setValue(0.5);
        }
    }//GEN-LAST:event_handicapSpinnerStateChanged
    
    private void komiCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_komiCheckBoxActionPerformed
        boolean isSelected = komiCheckBox.isSelected();
        komiLabel.setEnabled(isSelected);
        komiSpinner.setEnabled(isSelected);
    }//GEN-LAST:event_komiCheckBoxActionPerformed
    
    private void handicapCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_handicapCheckBoxActionPerformed
        boolean isSelected = handicapCheckBox.isSelected();
        handicapLabel.setEnabled(isSelected);
        handicapSpinner.setEnabled(isSelected);
    }//GEN-LAST:event_handicapCheckBoxActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel boardSizeLabel;
    private javax.swing.JSpinner boardSizeSpinner;
    private javax.swing.JCheckBox handicapCheckBox;
    private javax.swing.JLabel handicapLabel;
    private javax.swing.JSpinner handicapSpinner;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JCheckBox komiCheckBox;
    private javax.swing.JLabel komiLabel;
    private javax.swing.JSpinner komiSpinner;
    // End of variables declaration//GEN-END:variables
}