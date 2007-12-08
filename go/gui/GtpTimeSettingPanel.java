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

import javax.swing.DefaultComboBoxModel;
import javax.swing.SpinnerNumberModel;

public class GtpTimeSettingPanel extends javax.swing.JPanel {
    private static final String NO_TIME_LIMIT = "時間無制限";
    private static final String CANADIAN = "カナダ方式";
    private static final String MAIN_TIME = "持ち時間(分)";
    private static final String BYOYOMI_TIME = "秒読み時間(分)";
    private static final String MOVES = "手数(手)";
    
    private SpinnerNumberModel mainTimeModel;
    private SpinnerNumberModel byoYomiTimeModel;
    private SpinnerNumberModel movesModel;
        
    public GtpTimeSettingPanel() {
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        model.addElement(NO_TIME_LIMIT);
        model.addElement(CANADIAN);
        
        mainTimeModel = new SpinnerNumberModel(1, 0, 100, 1);
        byoYomiTimeModel = new SpinnerNumberModel(10, 0, 100, 1);
        movesModel = new SpinnerNumberModel(25, 0, 100, 1);

        initComponents();
        
        typeComboBox.setModel(model);
        model.setSelectedItem(NO_TIME_LIMIT);
        setEnabled(false);
        
        mainTimeLabel.setText(MAIN_TIME);
        byoYomiTimeLabel.setText(BYOYOMI_TIME);
        movesLabel.setText(MOVES);
        
        mainTimeSpinner.setModel(mainTimeModel);
        byoYomiTimeSpinner.setModel(byoYomiTimeModel);
        movesSpinner.setModel(movesModel);
    }
    
    public int getMainTime(){
        return mainTimeModel.getNumber().intValue();
    }

    public int getByoYomiTime(){
        return byoYomiTimeModel.getNumber().intValue();
    }
    
    public int getByoYomiStones(){
        return movesModel.getNumber().intValue();
    }
    
    public boolean hasTimerSetting(){
        String type = (String)typeComboBox.getSelectedItem();
        if(type.equals(CANADIAN)){
            return true;
        }
        
        return false;
    }
    
    @Override
    public void setEnabled(boolean b){
        setMainTimeEnabled(b);
        setByoYomiTimeEnabled(b);
        setMovesEnabled(b);
    }

    public void setMainTimeEnabled(boolean b){
        mainTimeLabel.setEnabled(b);
        mainTimeSpinner.setEnabled(b);
    }
    
    public void setByoYomiTimeEnabled(boolean b){
        byoYomiTimeLabel.setEnabled(b);
        byoYomiTimeSpinner.setEnabled(b);
    }
    
    public void setMovesEnabled(boolean b){
        movesLabel.setEnabled(b);
        movesSpinner.setEnabled(b);
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        movesLabel = new javax.swing.JLabel();
        movesSpinner = new javax.swing.JSpinner();
        mainTimeLabel = new javax.swing.JLabel();
        mainTimeSpinner = new javax.swing.JSpinner();
        byoYomiTimeLabel = new javax.swing.JLabel();
        byoYomiTimeSpinner = new javax.swing.JSpinner();
        typeComboBox = new javax.swing.JComboBox();
        jSeparator1 = new javax.swing.JSeparator();

        movesLabel.setText("Moves :");

        mainTimeLabel.setText("Main Time :");

        byoYomiTimeLabel.setText("Byo-Yomi Time :");

        typeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        typeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                typeComboBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(typeComboBox, 0, 189, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(byoYomiTimeLabel)
                            .addComponent(movesLabel)
                            .addComponent(mainTimeLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(movesSpinner, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
                            .addComponent(byoYomiTimeSpinner, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
                            .addComponent(mainTimeSpinner, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(typeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mainTimeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mainTimeLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(byoYomiTimeLabel)
                    .addComponent(byoYomiTimeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(movesSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(movesLabel))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void typeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_typeComboBoxActionPerformed
        System.out.println("typeComboBoxCompo");
        String type = (String)typeComboBox.getSelectedItem();
        if(type.equals(NO_TIME_LIMIT)){
            movesModel.setValue(0);
            setEnabled(false);
        }else if(type.equals(CANADIAN)){
            movesModel.setValue(25);
            setEnabled(true);
        }
    }//GEN-LAST:event_typeComboBoxActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel byoYomiTimeLabel;
    private javax.swing.JSpinner byoYomiTimeSpinner;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel mainTimeLabel;
    private javax.swing.JSpinner mainTimeSpinner;
    private javax.swing.JLabel movesLabel;
    private javax.swing.JSpinner movesSpinner;
    private javax.swing.JComboBox typeComboBox;
    // End of variables declaration//GEN-END:variables
}