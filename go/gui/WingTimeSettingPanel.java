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

import javax.swing.SpinnerNumberModel;

public class WingTimeSettingPanel extends javax.swing.JPanel {
    private static final String MAIN_TIME = "持ち時間(分)";
    private static final String BYOYOMI_TIME = "秒読み時間(分)";
    private static final String KOURYO_TIME = "考慮時間(分)";
    
    private SpinnerNumberModel mainTimeModel;
    private SpinnerNumberModel byoYomiTimeModel;
    private SpinnerNumberModel kouryoTimeModel;
        
    public WingTimeSettingPanel() {
        mainTimeModel = new SpinnerNumberModel(1, 0, 100, 1);
        byoYomiTimeModel = new SpinnerNumberModel(10, 0, 100, 1);
        kouryoTimeModel = new SpinnerNumberModel(0, 0, 100, 1);

        initComponents();
        
        mainTimeLabel.setText(MAIN_TIME);
        byoYomiTimeLabel.setText(BYOYOMI_TIME);
        kouryoTimeLabel.setText(KOURYO_TIME);
        
        mainTimeSpinner.setModel(mainTimeModel);
        byoYomiTimeSpinner.setModel(byoYomiTimeModel);
        kouryoTimeSpinner.setModel(kouryoTimeModel);
    }
    
    public int getMainTime(){
        return mainTimeModel.getNumber().intValue();
    }
    
    public void setMainTime(int sec){
        mainTimeModel.setValue(sec);
    }
    
    public int getByoYomiTime(){
        return byoYomiTimeModel.getNumber().intValue();
    }
    
    public void setByoYomiTime(int sec){
        byoYomiTimeModel.setValue(sec);
    }
    public int getKouryoTime(){
        return kouryoTimeModel.getNumber().intValue();
    }
    
    public void setEnabled(boolean b){
        setMainTimeEnabled(b);
        setByoYomiTimeEnabled(b);
        setKouryoTimeEnabled(b);
    }

    public void setMainTimeEnabled(boolean b){
        mainTimeLabel.setEnabled(b);
        mainTimeSpinner.setEnabled(b);
    }
    
    public void setByoYomiTimeEnabled(boolean b){
        byoYomiTimeLabel.setEnabled(b);
        byoYomiTimeSpinner.setEnabled(b);
    }
    
    public void setKouryoTimeEnabled(boolean b){
        kouryoTimeLabel.setEnabled(b);
        kouryoTimeSpinner.setEnabled(b);
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        kouryoTimeLabel = new javax.swing.JLabel();
        kouryoTimeSpinner = new javax.swing.JSpinner();
        mainTimeLabel = new javax.swing.JLabel();
        mainTimeSpinner = new javax.swing.JSpinner();
        byoYomiTimeLabel = new javax.swing.JLabel();
        byoYomiTimeSpinner = new javax.swing.JSpinner();

        kouryoTimeLabel.setText("Kouryo Time :");

        mainTimeLabel.setText("Main Time :");

        byoYomiTimeLabel.setText("Byo-Yomi Time :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(mainTimeLabel)
                    .addComponent(byoYomiTimeLabel)
                    .addComponent(kouryoTimeLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(byoYomiTimeSpinner)
                    .addComponent(kouryoTimeSpinner)
                    .addComponent(mainTimeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mainTimeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mainTimeLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(byoYomiTimeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(byoYomiTimeLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(kouryoTimeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kouryoTimeLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel byoYomiTimeLabel;
    private javax.swing.JSpinner byoYomiTimeSpinner;
    private javax.swing.JLabel kouryoTimeLabel;
    private javax.swing.JSpinner kouryoTimeSpinner;
    private javax.swing.JLabel mainTimeLabel;
    private javax.swing.JSpinner mainTimeSpinner;
    // End of variables declaration//GEN-END:variables
}