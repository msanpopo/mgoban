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

import app.sgf.GtpTerminal;
import go.gui.InOutPanel;
import go.gui.InputListener;
import gtp.GtpEngine;
import java.awt.BorderLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class GtpTerminalWindow extends javax.swing.JFrame implements GtpTerminal, InputListener, ComponentListener{
    private static final String BLACK = "黒";
    private static final String WHITE = "白";
    
    private InOutPanel ioPanel1;
    private InOutPanel ioPanel2;
    
    private GtpEngine engine1;
    private GtpEngine engine2;
    
    public GtpTerminalWindow() {
        engine1 = null;
        engine2 = null;
        
        initComponents();
        
        ioPanel1 = new InOutPanel();
        ioPanel2 = new InOutPanel();
        
        ioPanel1.setInputListener(this);
        ioPanel2.setInputListener(this);
        
        topPanel.setLayout(new BorderLayout());
        topPanel.add(ioPanel1, BorderLayout.CENTER);
        
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(ioPanel2, BorderLayout.CENTER);
        
        blackLabel.setText(BLACK);
        whiteLabel.setText(WHITE);
    }
    
    public void setEngine1(GtpEngine e){
        engine1 = e;
    }
    
    public void setEngine2(GtpEngine e){
        engine2 = e;
    }
    
    public void setEngine1Name(String name){
        blackLabel.setText(BLACK + " - " + name);
    }
    
    public void setEngine2Name(String name){
        whiteLabel.setText(WHITE + " - " + name);
    }
    
    public void append(GtpEngine engine, String s) {
        if(engine == engine1){
            ioPanel1.addText(s);
        }else if(engine == engine2){
            ioPanel2.addText(s);
        }else{
            System.err.println("err:GtpTerminalWindow.append: engine??:" + engine);
        }
    }
    
    public void appendError(GtpEngine engine, String s) {
        if(engine == engine1){
            ioPanel1.addText(s, InOutPanel.GRAY);
        }else if(engine == engine2){
            ioPanel2.addText(s, InOutPanel.GRAY);
        }else{
            System.err.println("err:GtpTerminalWindow.appendError: engine??:" + engine);
        }
    }
    
    public void closed(GtpEngine engine){
        if(engine == engine1){
            engine1 = null;
        }else if(engine == engine2){
            engine2 = null;
        }
        
        if(engine1 == null && engine2 == null){
            setVisible(false);
        }
    }
    
    public void textInputed(Object source, String text) {
        if(source != null && source == ioPanel1 && engine1 != null){
            engine1.debugCommand(text);
        }else if(source != null && source == ioPanel2 && engine2 != null){
            engine2.debugCommand(text);
        }else{
            System.err.println("err:GtpTerminalWindow.textInputed: source??:" + source);
        }
    }
    
    public void componentResized(ComponentEvent e) {}
    public void componentMoved(ComponentEvent e) {}
    public void componentShown(ComponentEvent e) {}
    public void componentHidden(ComponentEvent e) {
        setVisible(false);
    }
    
    // <editor-fold defaultstate="collapsed" desc=" Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        jPanel2 = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        panel1 = new javax.swing.JPanel();
        blackLabel = new javax.swing.JLabel();
        topPanel = new javax.swing.JPanel();
        panel2 = new javax.swing.JPanel();
        whiteLabel = new javax.swing.JLabel();
        bottomPanel = new javax.swing.JPanel();

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setTitle("GTP Terminal");
        setLocationByPlatform(true);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                formComponentHidden(evt);
            }
        });

        jSplitPane1.setDividerLocation(200);
        jSplitPane1.setDividerSize(6);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        blackLabel.setText("Black");

        javax.swing.GroupLayout topPanelLayout = new javax.swing.GroupLayout(topPanel);
        topPanel.setLayout(topPanelLayout);
        topPanelLayout.setHorizontalGroup(
            topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 534, Short.MAX_VALUE)
        );
        topPanelLayout.setVerticalGroup(
            topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 178, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(blackLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
            .addComponent(topPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addComponent(blackLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(topPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jSplitPane1.setTopComponent(panel1);

        whiteLabel.setText("White");

        javax.swing.GroupLayout bottomPanelLayout = new javax.swing.GroupLayout(bottomPanel);
        bottomPanel.setLayout(bottomPanelLayout);
        bottomPanelLayout.setHorizontalGroup(
            bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 534, Short.MAX_VALUE)
        );
        bottomPanelLayout.setVerticalGroup(
            bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 206, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bottomPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(whiteLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addComponent(whiteLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bottomPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jSplitPane1.setRightComponent(panel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 536, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void formComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentHidden
        if(engine1 != null){
            engine1.setTerminal(null);
        }
        if(engine2 != null){
            engine2.setTerminal(null);
        }
        dispose();
    }//GEN-LAST:event_formComponentHidden
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel blackLabel;
    private javax.swing.JPanel bottomPanel;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JPanel panel1;
    private javax.swing.JPanel panel2;
    private javax.swing.JPanel topPanel;
    private javax.swing.JLabel whiteLabel;
    // End of variables declaration//GEN-END:variables
}