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

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import go.gui.InputListener;

public class InOutPanel extends javax.swing.JPanel implements ActionListener {

    private static final String SEND = "送信";
    
    public static final String REGULAR = "regular";
    public static final String RED = "red";
    public static final String GRAY = "gray";
    public static final String ITALIC = "italic";
    public static final String BOLD = "bold";
    public static final String SMALL = "small";
    public static final String LARGE = "large";
    private StyledDocument doc;
    private InputListener listener;
    private InputList list;

    public InOutPanel(){
        init(new InputList());
    }
    
    public InOutPanel(InputList list) {
        init(list);
    }

    private void init(InputList list) {
        listener = null;

        initComponents();

        doc = outputTextPane.getStyledDocument();
        addStylesToDocument(doc);

        sendButton.setText(SEND);

        this.list = list;
    }
        
    public void setInputList(InputList list){
        if(list == null){
            this.list = new InputList();
        }else{
            this.list = list;
        }
    }
    
    public void actionPerformed(ActionEvent e) {
        String text = e.getActionCommand();
        //System.out.println("popupMenuSelected:" + text);
        inputField.setText(text);
    }

    public void setInputListener(InputListener l) {
        listener = l;
    }

    public void addText(String text) {
        addText(text, REGULAR);
    }

    public void addText(String text, String style) {
        StringBuilder str = new StringBuilder();
        str.append(text).append("\n");
        try {
            // System.out.println("InOutPanel:" + style + ":" + text);
            doc.insertString(doc.getLength(), str.toString(), doc.getStyle(style));
        } catch (BadLocationException e) {
            System.err.println("error:addTextRed : " + text + " :" + e);
        }
    }

    public void clearText() {
        try {
            doc.remove(0, doc.getLength());
        } catch (BadLocationException e) {
            System.err.println("error:clearText : " + e);
        }
    }

    private void addStylesToDocument(StyledDocument doc) {
        Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
        StyleConstants.setFontFamily(def, "Monospaced"/*SansSerif"*/);

        Style regular = doc.addStyle(REGULAR, def);

        Style s = doc.addStyle(RED, regular);
        StyleConstants.setForeground(s, Color.RED);

        s = doc.addStyle(GRAY, regular);
        StyleConstants.setForeground(s, Color.GRAY);

        s = doc.addStyle(ITALIC, regular);
        StyleConstants.setItalic(s, true);

        s = doc.addStyle(BOLD, regular);
        StyleConstants.setBold(s, true);

        s = doc.addStyle(SMALL, regular);
        StyleConstants.setFontSize(s, 10);

        s = doc.addStyle(LARGE, regular);
        StyleConstants.setFontSize(s, 16);

    }

    private void inputFieldActivated() {
        String text = inputField.getText();

        if (listener != null) {
            listener.textInputed(this, inputField.getText());
        }

        inputField.setText("");
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popupMenu = new javax.swing.JPopupMenu();
        jScrollPane1 = new javax.swing.JScrollPane();
        outputTextPane = new javax.swing.JTextPane();
        jToolBar1 = new javax.swing.JToolBar();
        inputField = new javax.swing.JTextField();
        sendButton = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(200, 100));

        outputTextPane.setEditable(false);
        outputTextPane.setFont(new java.awt.Font("Monospaced", 0, 12));
        jScrollPane1.setViewportView(outputTextPane);

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        inputField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                inputFieldMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                inputFieldMouseReleased(evt);
            }
        });
        inputField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputFieldActionPerformed(evt);
            }
        });
        jToolBar1.add(inputField);

        sendButton.setText("Send");
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(sendButton);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
        inputFieldActivated();
    }//GEN-LAST:event_sendButtonActionPerformed

    private void inputFieldMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inputFieldMousePressed
        checkPopup(evt);
    }//GEN-LAST:event_inputFieldMousePressed

    private void checkPopup(MouseEvent e) {
        if (e.isPopupTrigger()) {
            popupMenu.removeAll();
            JMenuItem emptyItem = new JMenuItem("");
            emptyItem.addActionListener(this);
            popupMenu.add(emptyItem);

            for (String s : list.getCollection()) {
                JMenuItem item = new JMenuItem(s);
                item.addActionListener(this);
                popupMenu.add(item);
            }
            popupMenu.show(e.getComponent(), e.getX(), e.getY());
        }
    }

        
    private void inputFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputFieldActionPerformed
        inputFieldActivated();
    }//GEN-LAST:event_inputFieldActionPerformed

    private void inputFieldMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inputFieldMouseReleased
        checkPopup(evt);
    }//GEN-LAST:event_inputFieldMouseReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField inputField;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextPane outputTextPane;
    private javax.swing.JPopupMenu popupMenu;
    private javax.swing.JButton sendButton;
    // End of variables declaration//GEN-END:variables
}