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

package app;

import app.App;

import javax.swing.JFrame;

public class MainWindow extends JFrame {
    private static final String NET_GO = "ネット対局";
    private static final String LOCAL_GO = "ローカル碁盤";
    private static final String QUIT = "終了";
    
    public MainWindow() {
        
        initComponents();
        
        netGoButton.setText(NET_GO);
        localGoButton.setText(LOCAL_GO);
        quitButton.setText(QUIT);
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        netGoButton = new javax.swing.JButton();
        quitButton = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        localGoButton = new javax.swing.JButton();

        setTitle("mGo");
        setLocationByPlatform(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                formComponentHidden(evt);
            }
        });

        netGoButton.setText("Net Go");
        netGoButton.setMaximumSize(null);
        netGoButton.setMinimumSize(null);
        netGoButton.setPreferredSize(null);
        netGoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                netGoButtonActionPerformed(evt);
            }
        });

        quitButton.setText("Quit");
        quitButton.setMaximumSize(null);
        quitButton.setMinimumSize(null);
        quitButton.setPreferredSize(null);
        quitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quitButtonActionPerformed(evt);
            }
        });

        localGoButton.setText("Local Go");
        localGoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                localGoButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(quitButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                    .addComponent(netGoButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                    .addComponent(localGoButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(netGoButton, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(localGoButton, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(quitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void localGoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_localGoButtonActionPerformed
        App.getInstance().startEditor(this);
    }//GEN-LAST:event_localGoButtonActionPerformed
        
    private void formComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentHidden
        System.out.println("MainWindow Hidden");

        dispose();
    }//GEN-LAST:event_formComponentHidden
    
    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        System.out.println("MainWindow Closed");
        System.exit(0);
    }//GEN-LAST:event_formWindowClosed
    
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // ウィンドウマネージャーの閉じるボタンが押された
        System.out.println("MainWindow Closeing()");
    }//GEN-LAST:event_formWindowClosing
    
    private void netGoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_netGoButtonActionPerformed
        App.getInstance().startNetGo(this);
    }//GEN-LAST:event_netGoButtonActionPerformed
        
    private void quitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quitButtonActionPerformed
        setVisible(false);
    }//GEN-LAST:event_quitButtonActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JButton localGoButton;
    private javax.swing.JButton netGoButton;
    private javax.swing.JButton quitButton;
    // End of variables declaration//GEN-END:variables
}