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
package app.wing;

import app.App;
import app.wing.GameObjectManager;
import app.wing.NetGo;
import app.SoundType;
import go.gui.EditInputListPanel;
import app.wing.GameTableModel;
import app.wing.TellPanel;
import app.wing.TerminalPanel;
import app.wing.LookingOpenPanel;
import go.gui.TabPanel;
import app.wing.UserPanel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.Collection;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;
import wing.Game;
import wing.MessageReceiver;
import wing.message.DeclareMessage;
import wing.message.GamesMessage;
import wing.message.Message;
import wing.message.TellMessage;

public class WingWindow extends javax.swing.JFrame implements ActionListener, MessageReceiver {

    private static final String OK = "OK";
    private static final String FILE = "ファイル";
    private static final String CLOSE = "閉じる";
    private static final String TOOL = "ツール";
    private static final String EDIT_TEXT = "定形文の編集";
    private static final String OPEN_GAME = "開く";
    private static final String DECLARE = "勝ちを宣言";
    private static final String DISMISS = "無勝負を宣言";
    private static final String DO_NOTHING = "何もしない";
    private static final String TERMINAL = "端末";
    private static final String TELL = "通常";
    private static final String SAY = "対局";
    private static final String KIBITZ = "観戦";
    private NetGo netGo;
    private LookingOpenPanel lookingOpenPanel;
    private GameTableModel gameModel;
    private TableRowSorter<GameTableModel> gameSorter;
    private TabPanel tabPanel;
    private UserPanel userPanel;
    private boolean refreshing;
    
    public WingWindow(NetGo netGo) {
        this.netGo = netGo;

        this.lookingOpenPanel = new LookingOpenPanel(netGo);
        this.gameModel = new GameTableModel();
        this.tabPanel = new TabPanel();
        this.userPanel = new UserPanel(netGo);

        this.refreshing = false;
        
        this.tabPanel.addTab(TERMINAL, new TerminalPanel(netGo), true);

        initComponents();

        fileMenu.setText(FILE);
        toolMenu.setText(TOOL);
        editTextMenuItem.setText(EDIT_TEXT);

        lookingOpenPanelBase.setLayout(new BorderLayout());
        lookingOpenPanelBase.add(new LookingOpenPanel(netGo), BorderLayout.CENTER);

        JMenuItem itemOpen = new JMenuItem(OPEN_GAME);
        itemOpen.addActionListener(this);
        gamePopupMenu.add(itemOpen);

        gameSorter = new TableRowSorter<GameTableModel>(gameModel);
//        Comparator<Game> comparator = new Comparator<Game>() {
//            public int compare(Game game1, Game game2) {
//                return game1.compareTo(game2);
//            }
//        };
//        gameSorter.setComparator(1, comparator);
        gameTable.setModel(gameModel);
        gameTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        gameTable.setRowSorter(gameSorter);

        setColumnWidth(gameTable, gameModel.getColumnWidth());

        userPanelBase.setLayout(new BorderLayout());
        userPanelBase.add(userPanel, BorderLayout.CENTER);

        tabPanelBase.setLayout(new BorderLayout());
        tabPanelBase.add(tabPanel, BorderLayout.CENTER);

        JMenuItem itemClose = new JMenuItem(CLOSE);

        itemClose.addActionListener(this);

        fileMenu.add(itemClose);

        netGo.getServer().addReceiver(this);

    }

    class MonoCellRenderer implements TableCellRenderer {

        private Font font = new Font(Font.MONOSPACED, Font.PLAIN, 12);
        private TableCellRenderer renderer;

        public MonoCellRenderer(TableCellRenderer r) {
            renderer = r;
        }

        public Component getTableCellRendererComponent(JTable table, Object val, boolean isSelected, boolean hasFocus, int row, int col) {
            JLabel l = (JLabel) renderer.getTableCellRendererComponent(table, val, isSelected, hasFocus, row, col);
            l.setFont(font);
            return l;
        }
    }

    private void setColumnWidth(JTable table, int[] width) {
        TableColumnModel columnModel = table.getColumnModel();

        for (int i = 0; i < columnModel.getColumnCount(); ++i) {
            TableColumn column = columnModel.getColumn(i);
            column.setPreferredWidth(width[i]);
            if (i > 0) {	// 超その場しのぎ。要修正？
                column.setCellRenderer(new MonoCellRenderer(table.getDefaultRenderer(Object.class)));
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("WingWindow actionPerformed : " + e.getActionCommand());

        if (e.getActionCommand().equals(CLOSE)) {
            setVisible(false);
        } else if (e.getActionCommand().equals(OPEN_GAME)) {
            ListSelectionModel lsm = gameTable.getSelectionModel();
            int index = lsm.getAnchorSelectionIndex();
            if (index == -1) {
                return;
            }
            
            int modelRow = gameTable.convertRowIndexToModel(index);
            Game game = gameModel.getGame(modelRow);
            if (game == null) {
                return;
            }

            System.out.println("selected : " + game);

            GameObjectManager gom = netGo.getGameObjectManager();
            gom.createNewGameObject(game);

        }
    }

    public void receive(Message wm) {
        if (wm instanceof DeclareMessage) {
            DeclareMessage m = (DeclareMessage) wm;

            showDeclareDialog(m.getMessage(), m.hasDeclare());
        } else if (wm instanceof TellMessage) {
            TellMessage m = (TellMessage) wm;
            String name = m.getName();

            Component c = tabPanel.getComponent(name);
            TellPanel p = null;
            if (c == null) {
                p = addTellPanel(name);
            } else {
                if (c instanceof TellPanel) {
                    p = (TellPanel) c;
                } else {
                    System.err.println("WingWindow.receive:" + wm.toString());
                    return;
                }
            }
            p.addText(m.getText());
            tabPanel.tabColorChange(p);

            App.getInstance().soundPlay(SoundType.MESSAGE);
        } else if (wm instanceof GamesMessage) {
            if (refreshing == true) {
                GamesMessage m = (GamesMessage) wm;

                Collection<Game> gameSet = m.getGameCollection();

                gameModel.setGame(gameSet);

                refreshing = false;
            }
        }
    }

    public TellPanel addTellPanel(String name) {
        TellPanel tellPanel = new TellPanel(name, netGo);

        tabPanel.addTab(name, tellPanel, false);

        return tellPanel;
    }

    public void selectTellPanel(String name) {
        Component c = tabPanel.getComponent(name);
        TellPanel p = null;
        if (c == null) {
            p = addTellPanel(name);
        } else {
            if (c instanceof TellPanel) {
                p = (TellPanel) c;
            } else {
                System.err.println("WingWindow.selectTellPanel:" + name);
                return;
            }
        }
        tabPanel.select(p);
    }

    private void showDeclareDialog(String msg, boolean declare) {
        String[] options;

        if (declare) {
            options = new String[3];
            options[0] = DECLARE;
            options[1] = DISMISS;
            options[2] = DO_NOTHING;
        } else {
            options = new String[2];
            options[0] = DISMISS;
            options[1] = DO_NOTHING;
        }

        int retval = JOptionPane.showOptionDialog(this, msg, "", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, null);
        String retStr = options[retval];
        System.out.println("###########################retval:" + retStr);

        if (retStr.equals(DECLARE)) {
            netGo.sendCommand("declare");
        } else if (retStr.equals(DISMISS)) {
            netGo.sendCommand("dismiss");
        } else {
            // 何もしない
        }
    }

    public void refreshGames() {
        refreshing = true;
        gameModel.clear();
        netGo.sendCommand("games");
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        gamePopupMenu = new javax.swing.JPopupMenu();
        jSplitPane1 = new javax.swing.JSplitPane();
        userPanelBase = new javax.swing.JPanel();
        jSplitPane2 = new javax.swing.JSplitPane();
        tabPanelBase = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        gameRefreshButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        gameTable = new javax.swing.JTable();
        lookingOpenPanelBase = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        toolMenu = new javax.swing.JMenu();
        editTextMenuItem = new javax.swing.JMenuItem();

        setTitle("Net Go");
        setLocationByPlatform(true);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                formComponentHidden(evt);
            }
        });

        jSplitPane1.setBorder(null);
        jSplitPane1.setDividerLocation(600);
        jSplitPane1.setDividerSize(6);
        jSplitPane1.setPreferredSize(new java.awt.Dimension(900, 600));

        userPanelBase.setMaximumSize(new java.awt.Dimension(200, 32767));
        userPanelBase.setMinimumSize(new java.awt.Dimension(200, 100));
        userPanelBase.setPreferredSize(new java.awt.Dimension(200, 100));

        javax.swing.GroupLayout userPanelBaseLayout = new javax.swing.GroupLayout(userPanelBase);
        userPanelBase.setLayout(userPanelBaseLayout);
        userPanelBaseLayout.setHorizontalGroup(
            userPanelBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 274, Short.MAX_VALUE)
        );
        userPanelBaseLayout.setVerticalGroup(
            userPanelBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 680, Short.MAX_VALUE)
        );

        jSplitPane1.setRightComponent(userPanelBase);

        jSplitPane2.setBorder(null);
        jSplitPane2.setDividerLocation(420);
        jSplitPane2.setDividerSize(6);
        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        javax.swing.GroupLayout tabPanelBaseLayout = new javax.swing.GroupLayout(tabPanelBase);
        tabPanelBase.setLayout(tabPanelBaseLayout);
        tabPanelBaseLayout.setHorizontalGroup(
            tabPanelBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );
        tabPanelBaseLayout.setVerticalGroup(
            tabPanelBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 254, Short.MAX_VALUE)
        );

        jSplitPane2.setRightComponent(tabPanelBase);

        jToolBar1.setFloatable(false);

        gameRefreshButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/view-refresh.png"))); // NOI18N
        gameRefreshButton.setBorderPainted(false);
        gameRefreshButton.setFocusPainted(false);
        gameRefreshButton.setOpaque(false);
        gameRefreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gameRefreshButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(gameRefreshButton);

        gameTable.setModel(new javax.swing.table.DefaultTableModel(
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
        gameTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        gameTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                gameTableMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                gameTableMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(gameTable);

        javax.swing.GroupLayout lookingOpenPanelBaseLayout = new javax.swing.GroupLayout(lookingOpenPanelBase);
        lookingOpenPanelBase.setLayout(lookingOpenPanelBaseLayout);
        lookingOpenPanelBaseLayout.setHorizontalGroup(
            lookingOpenPanelBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 360, Short.MAX_VALUE)
        );
        lookingOpenPanelBaseLayout.setVerticalGroup(
            lookingOpenPanelBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 32, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(lookingOpenPanelBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 208, Short.MAX_VALUE)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lookingOpenPanelBase, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE))
        );

        jSplitPane2.setLeftComponent(jPanel1);

        jSplitPane1.setLeftComponent(jSplitPane2);

        fileMenu.setText("File");
        jMenuBar1.add(fileMenu);

        toolMenu.setText("Tool");

        editTextMenuItem.setText("Edit template");
        editTextMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editTextMenuItemActionPerformed(evt);
            }
        });
        toolMenu.add(editTextMenuItem);

        jMenuBar1.add(toolMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 880, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void gameTableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_gameTableMouseReleased
        checkPopup(evt);
    }//GEN-LAST:event_gameTableMouseReleased

    private void gameTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_gameTableMousePressed
        checkPopup(evt);
    }//GEN-LAST:event_gameTableMousePressed

    private void gameRefreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gameRefreshButtonActionPerformed
        refreshGames();
    }//GEN-LAST:event_gameRefreshButtonActionPerformed

    private void checkPopup(MouseEvent e) {
        JTable table = (JTable) e.getSource();
        int row = table.rowAtPoint(e.getPoint());
        int modelRow = gameTable.convertRowIndexToModel(row);
        Game game = gameModel.getGame(modelRow);
        
        System.out.println("mouse pressed :" + row + " game:" + game.toString() + ":" + modelRow);

        if (e.isPopupTrigger()) {
            ListSelectionModel lsm = table.getSelectionModel();
            if (lsm.isSelectedIndex(row) == false) {
                lsm.setSelectionInterval(row, row);
            }

            gamePopupMenu.show(e.getComponent(), e.getX(), e.getY());
        }
    }
    private void formComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentHidden
        dispose();
    }//GEN-LAST:event_formComponentHidden

    private void editTextMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editTextMenuItemActionPerformed
        EditInputListPanel tellPanel = new EditInputListPanel(netGo.getTellInputList());
        EditInputListPanel sayPanel = new EditInputListPanel(netGo.getSayInputList());
        EditInputListPanel kibitzPanel = new EditInputListPanel(netGo.getKibitzInputList());

        JTabbedPane pane = new JTabbedPane();
        pane.add(TELL, tellPanel);
        pane.add(SAY, sayPanel);
        pane.add(KIBITZ, kibitzPanel);

        String[] options = {OK};
        int retval = JOptionPane.showOptionDialog(null, pane, "Edit Text",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);

        tellPanel.write();
        sayPanel.write();
        kibitzPanel.write();
}//GEN-LAST:event_editTextMenuItemActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem editTextMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JPopupMenu gamePopupMenu;
    private javax.swing.JButton gameRefreshButton;
    private javax.swing.JTable gameTable;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JPanel lookingOpenPanelBase;
    private javax.swing.JPanel tabPanelBase;
    private javax.swing.JMenu toolMenu;
    private javax.swing.JPanel userPanelBase;
    // End of variables declaration//GEN-END:variables
}