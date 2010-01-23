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

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.JMenuItem;

import javax.swing.JPanel;
import wing.GoServer;
import wing.MessageReceiver;
import wing.message.Message;
import wing.message.StatsMessage;
import wing.message.ThistMessage;
import wing.message.SavedMessage;
import wing.message.SgfMessage;
import wing.message.SuggestMessage;

public class UserInfoWindow extends javax.swing.JFrame implements MessageReceiver, ActionListener, ComponentListener{
    private static final String INFO = "情報";
    private static final String SGF = "棋譜";
    
    private static final String RESULTS = "対局履歴";
    private static final String SUGGEST = "適正手合";
    private static final String STORED = "中断対局";
    
    private static final String LOAD = "再開";
    
    private static final String SHOW = "表示";
    private static final String MAIL_ME = "メールで送る";
    
    private static final String GRAPH = "レーティング・グラフ";
    private static final String DOWNLOAD = "ダウンロード";
    
    private static final String PRE_ADDRESS ="http://wing.gr.jp/graph/graph.cgi?lang=J&name1=";
    private static final String POST_ADDRESS = "&start=year&send=%91%97%90M";
    
    private NetGo netGo;
    private String name;
    private GoServer server;
    
    private DefaultListModel storedModel;
    private DefaultListModel sgfModel;
    
    private boolean finished;
    
    private BufferedImage image;
    private JPanel graphPanel;
    
    public UserInfoWindow(String name, NetGo netGo){
        this.netGo = netGo;
        this.name = name;
        this.server = netGo.getServer();
        this.storedModel = new DefaultListModel();
        this.sgfModel = new DefaultListModel();
        this.finished = false;
        
        netGo.getWindow().addComponentListener(this);
        
        initComponents();
        
        tabbedPane.addTab(INFO, infoTab);
        tabbedPane.addTab(SGF, sgfTab);
        
        setTitle(name);
        
        resultsLabel.setText(RESULTS);
        suggestLabel.setText(SUGGEST);
        storedLabel.setText(STORED);
        
        showButton.setText(SHOW);
        
        graphLabel.setText(GRAPH);
        downloadButton.setText(DOWNLOAD);
        
        storedList.setModel(storedModel);
        sgfList.setModel(sgfModel);
        
        JMenuItem storedItem = new JMenuItem(LOAD);
        storedItem.addActionListener(this);
        storedPopupMenu.add(storedItem);
        
        JMenuItem sgfItem = new JMenuItem(MAIL_ME);
        sgfItem.addActionListener(this);
        sgfPopupMenu.add(sgfItem);
        
        image = null;
        graphPanel = new JPanel(){
            @Override
            public void paintComponent(Graphics g){
                super.paintComponents(g);
                
                //windows でユーザーの変更時にグラフがクリアされない。linux だとクリアされる。なぜ？
                // linux でも コンボのプルダウンメニューからならクリアされるのに、手入力してエンターだとクリアされない。(2007/9/15)
                Graphics2D g2 = (Graphics2D)g;
                System.out.println("image:" + image);
                
                if(image == null){
                    return;
                }
                int w = image.getWidth();
                int h = image.getHeight();
                
                int w_panel = graphPanel.getWidth();
                int h_panel = graphPanel.getHeight();
                
                int x = (w_panel - w) / 2;
                int y = (h_panel - h) / 2;
                g2.drawImage(image, null, x, y);
            }
        };
        
        graphPanelBase.setLayout(new BorderLayout());
        graphPanelBase.add(graphPanel, BorderLayout.CENTER);
        
        server.addReceiver(this);
        
        try {
            server.sendCommand("suggest " + name);
            Thread.sleep(300);
            server.sendCommand("results " + name);
            Thread.sleep(300);
            server.sendCommand("stored " + name);
            Thread.sleep(300);
            server.sendCommand("stats " + name);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    
    public void receive(Message wm){
        System.out.println("UserInfoWindow.receive():" + wm.toString());
        if(!finished && wm instanceof ThistMessage){
            // 対局履歴
            ThistMessage m = (ThistMessage)wm;
            
            resultsText.append(m.getResultString());
            
        }else if(!finished && wm instanceof SavedMessage){
            SavedMessage m = (SavedMessage)wm;
            
            for(String s : m.getSavedList()){
                storedModel.addElement(s);
            }
            
        }else if(!finished && wm instanceof StatsMessage){
            StatsMessage m = (StatsMessage)wm;
            
            statsText.append(m.getStatsString());
        
            finished = true;
            
        }else if(!finished && wm instanceof SuggestMessage){
            SuggestMessage m = (SuggestMessage)wm;
            
            suggestText.append(m.getSuggestString());
        }else if(wm instanceof SgfMessage){
            SgfMessage m = (SgfMessage)wm;
            
            sgfModel.clear();
            for(String s : m.getSgfList()){
                sgfModel.addElement(s);
            }
        }
    }
    
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        if (command.equals(LOAD)) {
            String gameString = (String)storedList.getSelectedValue();
            if(gameString != null){
                System.out.println("load :" + gameString);
                server.sendCommand("load " + gameString);
            }
        }else if(command.equals(MAIL_ME)){
            String sgfString = (String)sgfList.getSelectedValue();
            if(sgfString != null){       
                System.out.println("mm :" + sgfString);
                server.sendCommand("mm " + sgfString);
            }
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        storedPopupMenu = new javax.swing.JPopupMenu();
        infoTab = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        statsText = new javax.swing.JTextArea();
        resultsLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        resultsText = new javax.swing.JTextArea();
        storedLabel = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        storedList = new javax.swing.JList();
        suggestLabel = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        suggestText = new javax.swing.JTextArea();
        sgfTab = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        sgfList = new javax.swing.JList();
        showButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        graphLabel = new javax.swing.JLabel();
        downloadButton = new javax.swing.JButton();
        graphPanelBase = new javax.swing.JPanel();
        sgfPopupMenu = new javax.swing.JPopupMenu();
        tabbedPane = new javax.swing.JTabbedPane();

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        statsText.setColumns(20);
        statsText.setEditable(false);
        statsText.setFont(new java.awt.Font("Monospaced", 0, 12));
        statsText.setRows(5);
        jScrollPane1.setViewportView(statsText);

        resultsLabel.setText("Results");

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setMaximumSize(new java.awt.Dimension(32767, 200));

        resultsText.setColumns(20);
        resultsText.setEditable(false);
        resultsText.setFont(new java.awt.Font("Monospaced", 0, 12));
        resultsText.setRows(5);
        resultsText.setMaximumSize(new java.awt.Dimension(2147483647, 243));
        jScrollPane2.setViewportView(resultsText);

        storedLabel.setText("Stored");

        storedList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        storedList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                storedListMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                storedListMouseReleased(evt);
            }
        });
        jScrollPane4.setViewportView(storedList);

        suggestLabel.setText("Suggest");

        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        suggestText.setColumns(20);
        suggestText.setEditable(false);
        suggestText.setFont(new java.awt.Font("Monospaced", 0, 12));
        suggestText.setRows(5);
        jScrollPane3.setViewportView(suggestText);

        javax.swing.GroupLayout infoTabLayout = new javax.swing.GroupLayout(infoTab);
        infoTab.setLayout(infoTabLayout);
        infoTabLayout.setHorizontalGroup(
            infoTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, infoTabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(infoTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 557, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 557, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, infoTabLayout.createSequentialGroup()
                        .addGroup(infoTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(storedLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(infoTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(suggestLabel)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)))
                    .addComponent(resultsLabel, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap())
        );
        infoTabLayout.setVerticalGroup(
            infoTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, infoTabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(resultsLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(infoTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(suggestLabel)
                    .addComponent(storedLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(infoTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane4)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE))
                .addContainerGap())
        );

        sgfList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        sgfList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        sgfList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                sgfListMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                sgfListMouseReleased(evt);
            }
        });
        jScrollPane5.setViewportView(sgfList);

        showButton.setText("Show");
        showButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showButtonActionPerformed(evt);
            }
        });

        graphLabel.setText("Graph");

        downloadButton.setText("Download");
        downloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downloadButtonActionPerformed(evt);
            }
        });

        graphPanelBase.setBackground(new java.awt.Color(255, 255, 255));
        graphPanelBase.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        graphPanelBase.setMinimumSize(new java.awt.Dimension(0, 350));
        graphPanelBase.setPreferredSize(new java.awt.Dimension(0, 350));

        javax.swing.GroupLayout graphPanelBaseLayout = new javax.swing.GroupLayout(graphPanelBase);
        graphPanelBase.setLayout(graphPanelBaseLayout);
        graphPanelBaseLayout.setHorizontalGroup(
            graphPanelBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 579, Short.MAX_VALUE)
        );
        graphPanelBaseLayout.setVerticalGroup(
            graphPanelBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 295, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout sgfTabLayout = new javax.swing.GroupLayout(sgfTab);
        sgfTab.setLayout(sgfTabLayout);
        sgfTabLayout.setHorizontalGroup(
            sgfTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sgfTabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(sgfTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(graphPanelBase, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 583, Short.MAX_VALUE)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 583, Short.MAX_VALUE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 583, Short.MAX_VALUE)
                    .addComponent(showButton, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, sgfTabLayout.createSequentialGroup()
                        .addComponent(graphLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(downloadButton)))
                .addContainerGap())
        );
        sgfTabLayout.setVerticalGroup(
            sgfTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sgfTabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(showButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(sgfTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(graphLabel)
                    .addComponent(downloadButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(graphPanelBase, javax.swing.GroupLayout.PREFERRED_SIZE, 299, Short.MAX_VALUE)
                .addContainerGap())
        );

        setLocationByPlatform(true);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                formComponentHidden(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 605, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 747, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void sgfListMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sgfListMouseReleased
        String sgfString = (String)sgfList.getSelectedValue();
        if(sgfString == null){
            return;
        }
        checkPopup(evt);
    }//GEN-LAST:event_sgfListMouseReleased
    
    private void sgfListMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sgfListMousePressed
        String sgfString = (String)sgfList.getSelectedValue();
        if(sgfString == null){
            return;
        }
        checkPopup(evt);
    }//GEN-LAST:event_sgfListMousePressed
    
    private void showButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showButtonActionPerformed
        server.sendCommand("sgf " + name);
    }//GEN-LAST:event_showButtonActionPerformed
    
    private void storedListMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_storedListMouseReleased
        String gameString = (String) storedList.getSelectedValue();
        if (gameString == null) {
            return;
        }
        checkPopup(evt);
    }//GEN-LAST:event_storedListMouseReleased
    
    private void storedListMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_storedListMousePressed
        String gameString = (String) storedList.getSelectedValue();
        if (gameString == null) {
            return;
        }
        checkPopup(evt);
    }//GEN-LAST:event_storedListMousePressed
    
    private void checkPopup(MouseEvent e) {
        if (e.isPopupTrigger()) {
            if(e.getSource() == storedList){
                storedPopupMenu.show(e.getComponent(), e.getX(), e.getY());
            }else if(e.getSource() == sgfList){
                sgfPopupMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }
    
    public void componentResized(ComponentEvent e) {}
    public void componentMoved(ComponentEvent e) {}
    public void componentShown(ComponentEvent e) {}
    public void componentHidden(ComponentEvent e) {
        setVisible(false);
    }
    
    private void formComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentHidden
        System.out.println("UserInfoWindow:formComponentHidden");
        server.removeReceiver(this);
        dispose();
    }//GEN-LAST:event_formComponentHidden

    private void downloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downloadButtonActionPerformed
        if(image != null){
            return;
        }

        URL url;

        try {
            url = new URL(PRE_ADDRESS + name + POST_ADDRESS);
            System.out.println("url:" + url);
        } catch (MalformedURLException ex) {
            System.err.println("UserInfoWindow:URL()" + ex);
            ex.printStackTrace();
            return;
        }
        
        try {
            image = ImageIO.read(url);
        } catch (IOException ex) {
            System.err.println("KgsGraphPanel:" + url);
            return;
        }
        graphPanel.repaint();
}//GEN-LAST:event_downloadButtonActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton downloadButton;
    private javax.swing.JLabel graphLabel;
    private javax.swing.JPanel graphPanelBase;
    private javax.swing.JPanel infoTab;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel resultsLabel;
    private javax.swing.JTextArea resultsText;
    private javax.swing.JList sgfList;
    private javax.swing.JPopupMenu sgfPopupMenu;
    private javax.swing.JPanel sgfTab;
    private javax.swing.JButton showButton;
    private javax.swing.JTextArea statsText;
    private javax.swing.JLabel storedLabel;
    private javax.swing.JList storedList;
    private javax.swing.JPopupMenu storedPopupMenu;
    private javax.swing.JLabel suggestLabel;
    private javax.swing.JTextArea suggestText;
    private javax.swing.JTabbedPane tabbedPane;
    // End of variables declaration//GEN-END:variables
}