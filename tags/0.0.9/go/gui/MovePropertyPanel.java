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

import go.GoColor;
import javax.swing.DefaultComboBoxModel;
import sgf.property.MoveAnnotation;
import sgf.property.MoveProperty;
import sgf.property.PropertyId;

public class MovePropertyPanel extends javax.swing.JPanel {

    private static final String MOVE = "着手";
    private static final String MOVE_NUMBER = "番号";
    private static final String KO = "ルール無視";
    private static final String ANNOTATION = "評価";
    private static final String BLACK = "黒";
    private static final String WHITE = "白";
    private static final String TIME_LEFT = "残り時間";
    private static final String STONES = "残り回数";
    private static final String NOT_SELECTED = "";
    private static final String GOOD = "";
    private static final String VERY_GOOD = "";
    private static final String BAD = "悪手";
    private static final String VERY_BAD = "大悪手";
    private static final String DOUBTFUL = "疑問手";
    private static final String INTERESTING = "";
    private MoveProperty prop;

    public MovePropertyPanel(MoveProperty prop) {
        this.prop = prop;

        DefaultComboBoxModel model = new DefaultComboBoxModel();
        for (MoveAnnotation a : MoveAnnotation.values()) {
            model.addElement(a);
        }

        initComponents();

        moveLabel.setText(MOVE);
        mnLabel.setText(MOVE_NUMBER);
        koLabel.setText(KO);
        annotationLabel.setText(ANNOTATION);
        blackTimeLeftLabel.setText(BLACK + " " + TIME_LEFT);
        blackStonesLabel.setText(BLACK + " " + STONES);
        whiteTimeLeftLabel.setText(WHITE + " " + TIME_LEFT);
        whiteStonesLabel.setText(WHITE + " " + STONES);

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

        blackTimeLeftLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        blackTimeLeftLabel.setText("Black Time Left:");

        blTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                blTextFieldActionPerformed(evt);
            }
        });

        blackStonesLabel.setText("Black Moves left:");

        moveLabel.setText("Move:");

        moveTextField.setEnabled(false);

        mnLabel.setText("Move Number:");

        koLabel.setText("Ko:");

        koCheckBox.setText(" ");

        annotationLabel.setText("Annotation:");

        whiteStonesLabel.setText("White Moves left:");

        whiteTimeLeftLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        whiteTimeLeftLabel.setText("White Time Left:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(whiteStonesLabel)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGap(57, 57, 57)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(annotationLabel)
                                .addComponent(koLabel)
                                .addComponent(mnLabel)
                                .addComponent(moveLabel)))
                        .addGroup(layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(whiteTimeLeftLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(blackStonesLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(blackTimeLeftLabel)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(owTextField)
                    .addComponent(wlTextField)
                    .addComponent(obTextField)
                    .addComponent(blTextField)
                    .addComponent(mnTextField)
                    .addComponent(koCheckBox)
                    .addComponent(annotationComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(moveTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(moveLabel)
                    .addComponent(moveTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mnLabel)
                    .addComponent(mnTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(koLabel)
                    .addComponent(koCheckBox))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(annotationLabel)
                    .addComponent(annotationComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(blTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(blackTimeLeftLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(blackStonesLabel)
                    .addComponent(obTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(whiteTimeLeftLabel)
                    .addComponent(wlTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(whiteStonesLabel)
                    .addComponent(owTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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