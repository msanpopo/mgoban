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

import javax.swing.DefaultComboBoxModel;
import sgf.property.NoTypeProperty;
import sgf.property.PositionAnnotation;

public class NoTypePropertyPanel extends javax.swing.JPanel {
    
    private static final String HOTSPOT_NOT_SELECTED = "";
    private static final String HOTSPOT_1 = java.util.ResourceBundle.getBundle("app/resource/Resource").getString("HotSpot1");
    private static final String HOTSPOT_2 = java.util.ResourceBundle.getBundle("app/resource/Resource").getString("HotSpot2");
    private NoTypeProperty prop;

    public NoTypePropertyPanel(NoTypeProperty prop) {
        this.prop = prop;

        DefaultComboBoxModel model = new DefaultComboBoxModel();
        for (PositionAnnotation a : PositionAnnotation.values()) {
            model.addElement(a);
        }

        initComponents();

        annotationComboBox.setModel(model);

        hoComboBox.addItem(HOTSPOT_NOT_SELECTED);
        hoComboBox.addItem(HOTSPOT_1);
        hoComboBox.addItem(HOTSPOT_2);

        if (prop.hasNodeName()) {
            nTextField.setText(prop.getNodeName());
        }

        if (prop.hasComment()) {
            cTextArea.setText(prop.getComment());
        }

        if (prop.hasAnnotation()) {
            annotationComboBox.setSelectedItem(prop.getAnnotation());
        }

        if (prop.hasHotspot()) {
            int n = prop.getHotspot();
            if (n == 1) {
                hoComboBox.setSelectedItem(HOTSPOT_1);
            } else if (n == 2) {
                hoComboBox.setSelectedItem(HOTSPOT_2);
            }
        }

        if (prop.hasValue()) {
            vTextField.setText(Double.toString(prop.getValue()));
        }

    }

    public void done() {
        prop.setNodeName(nTextField.getText());
        prop.setComment(cTextArea.getText());
        prop.setAnnotation((PositionAnnotation)annotationComboBox.getSelectedItem());
        
        String ho = (String)hoComboBox.getSelectedItem();
        if(ho.equals(HOTSPOT_1)){
            prop.setHotspot(1);
        }else if(ho.equals(HOTSPOT_2)){
            prop.setHotspot(2);
        }else{
            prop.setHotspot(0);
        }
        
        String v = vTextField.getText();
        if(v.isEmpty()){
            prop.setValue(100000.0);
        }else{
            prop.setValue(Double.parseDouble(v));
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nLabel = new javax.swing.JLabel();
        nTextField = new javax.swing.JTextField();
        cLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        cTextArea = new javax.swing.JTextArea();
        annotationLabel = new javax.swing.JLabel();
        annotationComboBox = new javax.swing.JComboBox();
        vLabel = new javax.swing.JLabel();
        vTextField = new javax.swing.JTextField();
        hoLabel = new javax.swing.JLabel();
        hoComboBox = new javax.swing.JComboBox();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("app/resource/Resource"); // NOI18N
        nLabel.setText(bundle.getString("NodeName")); // NOI18N

        cLabel.setText(bundle.getString("Comment")); // NOI18N

        cTextArea.setColumns(20);
        cTextArea.setRows(5);
        jScrollPane1.setViewportView(cTextArea);

        annotationLabel.setText(bundle.getString("Annotation")); // NOI18N

        vLabel.setText(bundle.getString("EstimatedScore")); // NOI18N

        hoLabel.setText(bundle.getString("HotSpot")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cLabel)
                            .addComponent(nLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(vLabel)
                            .addComponent(hoLabel)
                            .addComponent(annotationLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(vTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(hoComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(annotationComboBox, 0, 155, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nLabel)
                    .addComponent(nTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cLabel)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(annotationLabel)
                    .addComponent(annotationComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(hoLabel)
                    .addComponent(hoComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(vLabel)
                    .addComponent(vTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox annotationComboBox;
    private javax.swing.JLabel annotationLabel;
    private javax.swing.JLabel cLabel;
    private javax.swing.JTextArea cTextArea;
    private javax.swing.JComboBox hoComboBox;
    private javax.swing.JLabel hoLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel nLabel;
    private javax.swing.JTextField nTextField;
    private javax.swing.JLabel vLabel;
    private javax.swing.JTextField vTextField;
    // End of variables declaration//GEN-END:variables
}