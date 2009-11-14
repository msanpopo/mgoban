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
package go.gui;

import go.GoColor;
import javax.swing.DefaultComboBoxModel;
import sgf.property.MoveAnnotation;
import sgf.property.MoveProperty;
import sgf.property.PropertyId;

public class MovePropertyPanel extends javax.swing.JPanel {

    private MoveProperty prop;

    public MovePropertyPanel(MoveProperty prop) {
        this.prop = prop;

        DefaultComboBoxModel model = new DefaultComboBoxModel();
        for (MoveAnnotation a : MoveAnnotation.values()) {
            model.addElement(a);
        }

        initComponents();

        annotationComboBox.setModel(model);

        moveTextField.setText(prop.getMove().toSgfString());

        if (prop.hasMoveNumber()) {
            mnTextField.setText(Integer.toString(prop.getMoveNumber()));
        }

        if (prop.hasKo()) {
            koCheckBox.setSelected(true);
        }

        if (prop.hasAnnotation()) {
            model.setSelectedItem(prop.getAnnotation());
        }

        if (prop.hasTimeLeft(GoColor.BLACK)) {
            blTextField.setText(Double.toString(prop.getTimeLeft(GoColor.BLACK)));
        }
        
        if (prop.hasStones(GoColor.BLACK)) {
            obTextField.setText(Integer.toString(prop.getStones(GoColor.BLACK)));
        }
        
        if (prop.hasTimeLeft(GoColor.WHITE)) {
            wlTextField.setText(Double.toString(prop.getTimeLeft(GoColor.WHITE)));
        }
        
        if (prop.hasStones(GoColor.WHITE)) {
            owTextField.setText(Integer.toString(prop.getStones(GoColor.WHITE)));
        }
    }

    public void done() {
        String mn = mnTextField.getText();
        if(mn.isEmpty()){
            prop.removeProperty(PropertyId.MN);
        }else{
            prop.setMoveNumber(Integer.parseInt(mn));
        }
        
        prop.setKo(koCheckBox.isSelected());

        prop.setAnnotation((MoveAnnotation)annotationComboBox.getSelectedItem());
        
        String bl = blTextField.getText();
        String ob = obTextField.getText();
        String wl = wlTextField.getText();
        String ow = owTextField.getText();
        
        if(bl.isEmpty()){
            prop.removeProperty(PropertyId.BL);
        }else{
            prop.setTimeLeft(GoColor.BLACK, Double.parseDouble(bl));
        }
        
        if(ob.isEmpty()){
            prop.removeProperty(PropertyId.OB);
        }else{
            prop.setStones(GoColor.BLACK, Integer.parseInt(ob));
        }
        
        if(wl.isEmpty()){
            prop.removeProperty(PropertyId.WL);
        }else{
            prop.setTimeLeft(GoColor.WHITE, Double.parseDouble(wl));
        }
        
        if(ow.isEmpty()){
            prop.removeProperty(PropertyId.OW);
        }else{
            prop.setStones(GoColor.WHITE, Integer.parseInt(ow));
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        blackTimeLeftLabel = new javax.swing.JLabel();
        blTextField = new javax.swing.JTextField();
        blackStonesLabel = new javax.swing.JLabel();
        obTextField = new javax.swing.JTextField();
        moveLabel = new javax.swing.JLabel();
        moveTextField = new javax.swing.JTextField();
        mnTextField = new javax.swing.JTextField();
        mnLabel = new javax.swing.JLabel();
        koLabel = new javax.swing.JLabel();
        koCheckBox = new javax.swing.JCheckBox();
        annotationLabel = new javax.swing.JLabel();
        annotationComboBox = new javax.swing.JComboBox();
        whiteStonesLabel = new javax.swing.JLabel();
        whiteTimeLeftLabel = new javax.swing.JLabel();
        owTextField = new javax.swing.JTextField();
        wlTextField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        blackTimeLeftLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("app/resource/Resource"); // NOI18N
        blackTimeLeftLabel.setText(bundle.getString("propBL")); // NOI18N

        blTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                blTextFieldActionPerformed(evt);
            }
        });

        blackStonesLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        blackStonesLabel.setText(bundle.getString("propOB")); // NOI18N

        moveLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        moveLabel.setText(bundle.getString("ShowMove")); // NOI18N

        moveTextField.setEnabled(false);

        mnLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        mnLabel.setText(bundle.getString("propMN")); // NOI18N

        koLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        koLabel.setText(bundle.getString("propKO")); // NOI18N

        koCheckBox.setText(" ");

        annotationLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        annotationLabel.setText(bundle.getString("MoveAnnotation")); // NOI18N

        whiteStonesLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        whiteStonesLabel.setText(bundle.getString("propOW")); // NOI18N

        whiteTimeLeftLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        whiteTimeLeftLabel.setText(bundle.getString("propWL")); // NOI18N

        jLabel1.setText(":");

        jLabel2.setText(":");

        jLabel3.setText(":");

        jLabel4.setText(":");

        jLabel5.setText(":");

        jLabel6.setText(":");

        jLabel7.setText(":");

        jLabel8.setText(":");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(annotationLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
                    .addComponent(whiteStonesLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
                    .addComponent(whiteTimeLeftLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(blackTimeLeftLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
                    .addComponent(blackStonesLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
                    .addComponent(koLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
                    .addComponent(mnLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
                    .addComponent(moveLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(blTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                    .addComponent(annotationComboBox, 0, 152, Short.MAX_VALUE)
                    .addComponent(obTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                    .addComponent(owTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                    .addComponent(wlTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                    .addComponent(moveTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                    .addComponent(mnTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                    .addComponent(koCheckBox))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                    .addComponent(moveLabel)
                    .addComponent(jLabel1)
                    .addComponent(moveTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mnLabel)
                    .addComponent(jLabel2)
                    .addComponent(mnTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(koCheckBox)
                    .addComponent(koLabel)
                    .addComponent(jLabel3))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(annotationComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(annotationLabel))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(blTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(blackTimeLeftLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(obTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(blackStonesLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(wlTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(whiteTimeLeftLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(owTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(whiteStonesLabel))
                .addGap(22, 22, 22))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void blTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_blTextFieldActionPerformed
    // TODO add your handling code here:
}//GEN-LAST:event_blTextFieldActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox annotationComboBox;
    private javax.swing.JLabel annotationLabel;
    private javax.swing.JTextField blTextField;
    private javax.swing.JLabel blackStonesLabel;
    private javax.swing.JLabel blackTimeLeftLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JCheckBox koCheckBox;
    private javax.swing.JLabel koLabel;
    private javax.swing.JLabel mnLabel;
    private javax.swing.JTextField mnTextField;
    private javax.swing.JLabel moveLabel;
    private javax.swing.JTextField moveTextField;
    private javax.swing.JTextField obTextField;
    private javax.swing.JTextField owTextField;
    private javax.swing.JLabel whiteStonesLabel;
    private javax.swing.JLabel whiteTimeLeftLabel;
    private javax.swing.JTextField wlTextField;
    // End of variables declaration//GEN-END:variables
}