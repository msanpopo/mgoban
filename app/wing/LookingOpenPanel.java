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

package app.wing;

/*
 * "set looking on", "set open on", "set open off" をするためのラジオボタン付きパネル
 * 
 *  ボタンを押した後、wing サーバーからの返答を確認してないので、パネルの表示と実際の状態が違ってくることが
 *  あるかも。(あることを確認 2007/12/12 TODO)
 */
public class LookingOpenPanel extends javax.swing.JPanel {
    private static final String MATCH = "対局 : ";
    private static final String LOOKING = "希望";
    private static final String OPEN_ON = "可能";
    private static final String OPEN_OFF = "不可";
    
    private NetGo netGo;
        
    public LookingOpenPanel(NetGo netGo) {
        this.netGo = netGo;
        
        initComponents();
        
        matchLabel.setText(MATCH);
        lookingRadioButton.setText(LOOKING);
        openOnRadioButton.setText(OPEN_ON);
        openOffRadioButton.setText(OPEN_OFF);
        
        connected(false);
    }

    public void connected(boolean bool){
        System.out.println("LookingOpenPanel.setEnabled:" + bool);
        //matchLabel.setEnabled(bool);
        lookingRadioButton.setEnabled(bool);
        openOnRadioButton.setEnabled(bool);
        openOffRadioButton.setEnabled(bool);
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup = new javax.swing.ButtonGroup();
        lookingRadioButton = new javax.swing.JRadioButton();
        openOnRadioButton = new javax.swing.JRadioButton();
        openOffRadioButton = new javax.swing.JRadioButton();
        matchLabel = new javax.swing.JLabel();

        buttonGroup.add(lookingRadioButton);
        lookingRadioButton.setText("Looking");
        lookingRadioButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        lookingRadioButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        lookingRadioButton.setMaximumSize(new java.awt.Dimension(80, 15));
        lookingRadioButton.setMinimumSize(new java.awt.Dimension(80, 15));
        lookingRadioButton.setPreferredSize(new java.awt.Dimension(80, 15));
        lookingRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lookingRadioButtonActionPerformed(evt);
            }
        });

        buttonGroup.add(openOnRadioButton);
        openOnRadioButton.setText("Open On");
        openOnRadioButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        openOnRadioButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        openOnRadioButton.setMaximumSize(new java.awt.Dimension(80, 15));
        openOnRadioButton.setMinimumSize(new java.awt.Dimension(80, 15));
        openOnRadioButton.setPreferredSize(new java.awt.Dimension(80, 15));
        openOnRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openOnRadioButtonActionPerformed(evt);
            }
        });

        buttonGroup.add(openOffRadioButton);
        openOffRadioButton.setSelected(true);
        openOffRadioButton.setText("Open Off");
        openOffRadioButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        openOffRadioButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        openOffRadioButton.setMaximumSize(new java.awt.Dimension(80, 15));
        openOffRadioButton.setMinimumSize(new java.awt.Dimension(80, 15));
        openOffRadioButton.setOpaque(false);
        openOffRadioButton.setPreferredSize(new java.awt.Dimension(80, 15));
        openOffRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openOffRadioButtonActionPerformed(evt);
            }
        });

        matchLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        matchLabel.setText("Match :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(matchLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
                .addGap(6, 6, 6)
                .addComponent(lookingRadioButton, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(openOnRadioButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(openOffRadioButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {openOffRadioButton, openOnRadioButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(lookingRadioButton, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(openOnRadioButton, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(openOffRadioButton, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(matchLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    private void lookingRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lookingRadioButtonActionPerformed
        netGo.sendCommand("set looking on");
    }//GEN-LAST:event_lookingRadioButtonActionPerformed
    
    private void openOnRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openOnRadioButtonActionPerformed
        netGo.sendCommand("set looking off");
        netGo.sendCommand("set open on");
    }//GEN-LAST:event_openOnRadioButtonActionPerformed
    
    private void openOffRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openOffRadioButtonActionPerformed
        netGo.sendCommand("set looking off");
        netGo.sendCommand("set open off");
    }//GEN-LAST:event_openOffRadioButtonActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup;
    private javax.swing.JRadioButton lookingRadioButton;
    private javax.swing.JLabel matchLabel;
    private javax.swing.JRadioButton openOffRadioButton;
    private javax.swing.JRadioButton openOnRadioButton;
    // End of variables declaration//GEN-END:variables
}