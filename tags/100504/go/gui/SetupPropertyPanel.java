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

import go.GoColor;
import javax.swing.DefaultComboBoxModel;
import sgf.property.PropertyId;
import sgf.property.SetupProperty;

public class SetupPropertyPanel extends javax.swing.JPanel {

    private static final String NOT_SELECTED = "";
    private static final String BLACK = java.util.ResourceBundle.getBundle("app/resource/Resource").getString("Black");
    private static final String WHITE = java.util.ResourceBundle.getBundle("app/resource/Resource").getString("White");

    private SetupProperty prop;

    public SetupPropertyPanel(SetupProperty prop) {
        this.prop = prop;

        DefaultComboBoxModel model = new DefaultComboBoxModel();
        model.addElement(NOT_SELECTED);
        model.addElement(BLACK);
        model.addElement(WHITE);

        initComponents();

        plComboBox.setModel(model);

        if (prop.hasProperty(PropertyId.PL)) {
            GoColor c = prop.getNextColor();
            if(c == GoColor.BLACK){
                model.setSelectedItem(BLACK);
            }else if(c == GoColor.WHITE){
                model.setSelectedItem(WHITE);
            }
        }
    }

    public void done() {
        String cStr = (String)plComboBox.getSelectedItem();

        if(cStr.equals(BLACK)){
            prop.setNextColor(GoColor.BLACK);
        }else if(cStr.equals(WHITE)){
            prop.setNextColor(GoColor.WHITE);
        }else{
            prop.removeProperty(PropertyId.PL);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        plLabel = new javax.swing.JLabel();
        plComboBox = new javax.swing.JComboBox();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("app/resource/Resource"); // NOI18N
        plLabel.setText(bundle.getString("PlayerToPlay")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(plLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(plComboBox, 0, 116, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(plLabel)
                    .addComponent(plComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox plComboBox;
    private javax.swing.JLabel plLabel;
    // End of variables declaration//GEN-END:variables
}