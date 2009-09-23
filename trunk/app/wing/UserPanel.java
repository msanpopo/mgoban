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

package app.wing;

import wing.WingUser;
import go.GoRank;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.Collection;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;
import wing.MessageReceiver;
import wing.message.Message;
import wing.message.WhoMessage;

public class UserPanel extends javax.swing.JPanel implements MessageReceiver, ActionListener {
    private static final String TELL = "話す";
    private static final String MATCH = "対局";
    private static final String INFO = "ユーザー情報";
    private static final String OBSERVE = "観戦";
    
    private static String[] rankArray = {"", "NR", "25k", "24k", "23k", "22k", "21k", "20k", "19k", "18k",
    "17k", "16k", "15k", "14k", "13k", "12k", "11k", "10k", "9k", "8k", "7k", "6k", "5k", "4k", "3k", "2k", "1k",
    "1d", "2d", "3d", "4d", "5d", "6d", "7d", "8d", "9d", "1p", "2p", "3p", "4p", "5p", "6p", "7p", "8p", "9p"};
    
    private NetGo netGo;
    private UserTableModel userModel;
    private TableRowSorter<UserTableModel> sorter;
    
    private DefaultComboBoxModel rankModelLow;
    private DefaultComboBoxModel rankModelHigh;
        
    public UserPanel(NetGo netGo) {
        this.netGo = netGo;
        this.userModel = new UserTableModel();
        
        
        rankModelLow = new DefaultComboBoxModel();
        rankModelHigh = new DefaultComboBoxModel();
        for(String s : rankArray){
            rankModelLow.addElement(new GoRank(s));
            rankModelHigh.addElement(new GoRank(s));
        }
        
        initComponents();
        
        JMenuItem item0 = new JMenuItem(TELL);
        JMenuItem item1 = new JMenuItem(MATCH);
        JMenuItem item2 = new JMenuItem(INFO);
//        JMenuItem item3 = new JMenuItem(OBSERVE);
        item0.addActionListener(this);
        item1.addActionListener(this);
        item2.addActionListener(this);
//        item3.addActionListener(this);
        popupMenu.add(item0);
        popupMenu.add(item1);
        popupMenu.add(item2);
//        popupMenu.add(item3);
        
        rankComboLow.setModel(rankModelLow);
        rankComboHigh.setModel(rankModelHigh);
        
        
        RowFilter<UserTableModel,Integer> rankFilter = new RowFilter<UserTableModel, Integer>() {
            public boolean include(Entry<? extends UserTableModel, ? extends Integer> entry) {
                UserTableModel model = entry.getModel();
                WingUser user = model.getUser(entry.getIdentifier());
                
                GoRank low = (GoRank) rankComboLow.getSelectedItem();
                GoRank high = (GoRank) rankComboHigh.getSelectedItem();
                
                if (low.isUnknown() == false && user.getRank().compareTo(low) < 0) {
                    return false;
                }
                
                if (high.isUnknown() == false && user.getRank().compareTo(high) > 0) {
                    return false;
                }
                
                return true;
            }
        };
        
        sorter = new TableRowSorter<UserTableModel>(userModel);
        sorter.setRowFilter(rankFilter);


        userTable.setModel(userModel);
        userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userTable.setRowSorter(sorter);

        setColumnWidth(userTable, userModel.getColumnWidth());

    }
    
    public void actionPerformed(ActionEvent e) {                                              
        WingUser user = getSelectedPlayer();
        String name = user.getName();
        
        if(name.equals(""))
            return;
        
        if (e.getActionCommand().equals(TELL)) {
            netGo.getWingWindow().selectTellPanel(name);
//        } else if (e.getActionCommand().equals(OBSERVE)) {
//            int gameNo = user.getGameNo();
//            if (gameNo <= 0)
//                return;
////		connection.observe(gameNo);
        } else if (e.getActionCommand().equals(MATCH)) {
            MatchReceiver mr = netGo.getMatchReceiver();
            mr.createMatchWindow(name);
            
        } else if (e.getActionCommand().equals(INFO)) {
            UserInfoWindow window = new UserInfoWindow(user.getName(), netGo);
            window.setVisible(true);
        }
    }           
    
    class MonoCellRenderer implements TableCellRenderer{
        private Font font = new Font(Font.MONOSPACED, Font.PLAIN, 12);
        private TableCellRenderer renderer;
        
        public MonoCellRenderer(TableCellRenderer r){
            renderer = r;
        }
        
        public Component getTableCellRendererComponent(JTable table, Object val, boolean isSelected, boolean hasFocus, int row, int col) {
            JLabel l = (JLabel)renderer.getTableCellRendererComponent(table, val, isSelected, hasFocus, row, col);
            l.setFont(font);
            return l;
        }
    }
    
    private void setColumnWidth(JTable table, int[] width) {
        TableColumnModel columnModel = table.getColumnModel();
        
        for (int i = 0; i < columnModel.getColumnCount(); ++i) {
            TableColumn column = columnModel.getColumn(i);
            column.setPreferredWidth(width[i]);
            column.setCellRenderer(new MonoCellRenderer(table.getDefaultRenderer(Object.class)));
        }
    }
    
    public void refresh() {
        netGo.getServer().addReceiver(this);
        
        userModel.clear();
        netGo.sendCommand("who");
    }
    
    public void receive(Message wm) {
        if(WhoMessage.class.isInstance(wm)){
            WhoMessage m = (WhoMessage)wm;
            
            Collection<WingUser> userCollection = m.getUserCollection();
            
            userModel.setUser(userCollection);
            
            sorter.sort();
            
            netGo.getServer().removeReceiver(this);
        }
    }
    
    private WingUser getSelectedPlayer() {
        ListSelectionModel lsm = userTable.getSelectionModel();
        int i = lsm.getAnchorSelectionIndex();
        
        return (WingUser)userTable.getValueAt(i, 0);
    }
    
    public void connected(boolean bool){
        refreshButton.setEnabled(bool);
    }
    
    // <editor-fold defaultstate="collapsed" desc=" Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        popupMenu = new javax.swing.JPopupMenu();
        jToolBar1 = new javax.swing.JToolBar();
        refreshButton = new javax.swing.JButton();
        jToolBar2 = new javax.swing.JToolBar();
        rankComboLow = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        rankComboHigh = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        userTable = new javax.swing.JTable();

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        refreshButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/view-refresh.png")));
        refreshButton.setBorderPainted(false);
        refreshButton.setFocusPainted(false);
        refreshButton.setOpaque(false);
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        jToolBar1.add(refreshButton);

        jToolBar2.setFloatable(false);
        jToolBar2.setBorderPainted(false);
        jToolBar2.setOpaque(false);
        rankComboLow.setMaximumSize(new java.awt.Dimension(65, 22));
        rankComboLow.setMinimumSize(new java.awt.Dimension(65, 22));
        rankComboLow.setOpaque(false);
        rankComboLow.setPreferredSize(new java.awt.Dimension(65, 22));
        jToolBar2.add(rankComboLow);

        jLabel1.setText(" - ");
        jToolBar2.add(jLabel1);

        rankComboHigh.setMaximumSize(new java.awt.Dimension(65, 22));
        rankComboHigh.setMinimumSize(new java.awt.Dimension(65, 22));
        rankComboHigh.setOpaque(false);
        rankComboHigh.setPreferredSize(new java.awt.Dimension(65, 22));
        jToolBar2.add(rankComboHigh);

        jToolBar1.add(jToolBar2);

        userTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        userTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        userTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                userTableMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                userTableMouseReleased(evt);
            }
        });

        jScrollPane1.setViewportView(userTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void userTableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userTableMouseReleased
        checkPopup(evt);
    }//GEN-LAST:event_userTableMouseReleased

    private void userTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userTableMousePressed
        checkPopup(evt);
    }//GEN-LAST:event_userTableMousePressed
    
    private void checkPopup(MouseEvent e) {
        JTable table = (JTable) e.getSource();
        int row = table.rowAtPoint(e.getPoint());
        int modelRow = userTable.convertRowIndexToModel(row);
        WingUser user = userModel.getUser(modelRow);
        System.out.println("mouse pressed :" + row + " user:" + user.getDebugString());
        
        if (e.isPopupTrigger()) {
            ListSelectionModel lsm = table.getSelectionModel();
            if (lsm.isSelectedIndex(row) == false) {
                lsm.setSelectionInterval(row, row);
            }

            popupMenu.show(e.getComponent(), e.getX(), e.getY());
        }
    }

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        refresh();
    }//GEN-LAST:event_refreshButtonActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JPopupMenu popupMenu;
    private javax.swing.JComboBox rankComboHigh;
    private javax.swing.JComboBox rankComboLow;
    private javax.swing.JButton refreshButton;
    private javax.swing.JTable userTable;
    // End of variables declaration//GEN-END:variables
}