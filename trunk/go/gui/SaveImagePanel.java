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

import go.GoGame;
import go.board.GoBoard;
import go.board.gui.BoardImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.SpinnerNumberModel;
import sgf.GameTree;

public class SaveImagePanel extends javax.swing.JPanel {
    private static final int DEFAULT_WIDTH = 420;
    
    private static final String IMAGE_WIDTH = "幅";
    private static final String RANGE = "範囲";
    private static final String HORIZONTAL = "横";
    private static final String VERTICAL = "縦";
    private static final String FILENAME = "保存先";
    private static final String DIALOG_TITLE = "保存先の選択";
    private static final String SELECT = "選択";
    private GoGame goGame;
    private GameTree tree;
    private GoBoard board;
    private BoardImage image;

    private ImageIcon icon;
    
    private SpinnerNumberModel x0Model;
    private SpinnerNumberModel x1Model;
    private SpinnerNumberModel y0Model;
    private SpinnerNumberModel y1Model;
    
    public SaveImagePanel(GoGame goGame) {

        this.goGame = goGame;
        this.tree = goGame.getGameTree();
        this.board = goGame.getBoard();
        this.image = new BoardImage(board, DEFAULT_WIDTH);
        this.image.setShowLastMove(false);
        //String abc = "ABCDEFGHJKLMNOPQRSTUVWXYZ";
        
        int n = goGame.getGameTree().getBoardSize() + 1;
        
        x0Model = new SpinnerNumberModel(0, 0, n, 1);
        x1Model = new SpinnerNumberModel(n, 0, n, 1);
        y0Model = new SpinnerNumberModel(0, 0, n, 1);
        y1Model = new SpinnerNumberModel(n, 0, n, 1);
        
        initComponents();
        
        widthLabel.setText(IMAGE_WIDTH);
        rangeLabel.setText(RANGE);
        horizontalLabel.setText(HORIZONTAL);
        verticalLabel.setText(VERTICAL);
        filenameLabel.setText(FILENAME);
        selectButton.setText(SELECT);
        
        x0Spinner.setModel(x0Model);
        x1Spinner.setModel(x1Model);
        y0Spinner.setModel(y0Model);
        y1Spinner.setModel(y1Model);
        
        widthTextField.setText(String.valueOf(DEFAULT_WIDTH));
        
        icon = new ImageIcon(image.getImage());
        imageLabel.setIcon(icon);
        imageLabel.repaint();
    }

    public void save() {
        String filename = filenameTextField.getText();
        if (filename.isEmpty() == true) {
            return;
        }

        if(image.getImage() == null){
            return;
        }
        
        if (filename.matches(".+\\.png$") == false) {
            filename = filename + ".png";
            System.out.println("file new name:" + filename);
        }

        File file = new File(filename);
        
        System.out.println("w:" + image.getImage().getWidth());
        try {
            ImageIO.write(image.getImage(), "png", file);
        } catch (IOException ex) {
            System.err.println("SaveImagePanel.save:" + ex);
        }
    }

    private void createNewImage() {
        String widthString = widthTextField.getText();
        int length = Integer.parseInt(widthString);
        int x0 = x0Model.getNumber().intValue();
        int x1 = x1Model.getNumber().intValue();
        int y0 = y0Model.getNumber().intValue();
        int y1 = y1Model.getNumber().intValue();
        
        image.setLength(length);
        image.setRange(x0, y0, x1, y1);
        icon.setImage(image.getImage());
        imageLabel.setIcon(icon);
        imageLabel.repaint();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        imageScrollPane = new javax.swing.JScrollPane();
        imageLabel = new javax.swing.JLabel();
        widthLabel = new javax.swing.JLabel();
        filenameLabel = new javax.swing.JLabel();
        widthTextField = new javax.swing.JTextField();
        filenameTextField = new javax.swing.JTextField();
        selectButton = new javax.swing.JButton();
        rangeLabel = new javax.swing.JLabel();
        horizontalLabel = new javax.swing.JLabel();
        verticalLabel = new javax.swing.JLabel();
        x0Spinner = new javax.swing.JSpinner();
        y0Spinner = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        x1Spinner = new javax.swing.JSpinner();
        y1Spinner = new javax.swing.JSpinner();

        imageScrollPane.setMaximumSize(new java.awt.Dimension(500, 500));
        imageScrollPane.setMinimumSize(new java.awt.Dimension(500, 500));
        imageScrollPane.setPreferredSize(new java.awt.Dimension(500, 500));

        imageLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imageScrollPane.setViewportView(imageLabel);

        widthLabel.setText("Width");

        filenameLabel.setText("Filename");

        widthTextField.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        widthTextField.setText("420");
        widthTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                widthTextFieldActionPerformed(evt);
            }
        });

        selectButton.setText("Select");
        selectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectButtonActionPerformed(evt);
            }
        });

        rangeLabel.setText("Range");

        horizontalLabel.setText("Horizontal");

        verticalLabel.setText("Vertical");

        x0Spinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                x0SpinnerStateChanged(evt);
            }
        });

        y0Spinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                y0SpinnerStateChanged(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("-");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("-");

        x1Spinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                x1SpinnerStateChanged(evt);
            }
        });

        y1Spinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                y1SpinnerStateChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(rangeLabel)
                    .addComponent(widthLabel)
                    .addComponent(filenameLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(widthTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(verticalLabel)
                                    .addComponent(horizontalLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(y0Spinner)
                                    .addComponent(x0Spinner, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(y1Spinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(x1Spinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(filenameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(selectButton)))
                .addContainerGap())
            .addComponent(imageScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {x0Spinner, x1Spinner, y0Spinner, y1Spinner});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(imageScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(widthLabel)
                    .addComponent(widthTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(horizontalLabel)
                    .addComponent(rangeLabel)
                    .addComponent(x0Spinner, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(x1Spinner, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(verticalLabel)
                    .addComponent(y0Spinner, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(y1Spinner, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(filenameLabel)
                    .addComponent(selectButton)
                    .addComponent(filenameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    private void widthTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_widthTextFieldActionPerformed
        String widthString = widthTextField.getText();
        if (widthString.isEmpty()) {
            widthTextField.setText(String.valueOf(DEFAULT_WIDTH));
        }
        createNewImage();
    }//GEN-LAST:event_widthTextFieldActionPerformed

    private void selectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectButtonActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle(DIALOG_TITLE);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int returnVal = chooser.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
            File file = chooser.getSelectedFile();
            if (file != null) {
                String command = file.getAbsolutePath();
                filenameTextField.setText(command);
            }
        }                                          
        

    }//GEN-LAST:event_selectButtonActionPerformed

    private void x0SpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_x0SpinnerStateChanged
        int x0 = x0Model.getNumber().intValue();
        int x1 = x1Model.getNumber().intValue();
        if(x0 > x1){
            x1Model.setValue(x0);
        }
        createNewImage();
    }//GEN-LAST:event_x0SpinnerStateChanged

    private void x1SpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_x1SpinnerStateChanged
        int x0 = x0Model.getNumber().intValue();
        int x1 = x1Model.getNumber().intValue();
        if(x1 < x0){
            x0Model.setValue(x1);
        }
        createNewImage();
    }//GEN-LAST:event_x1SpinnerStateChanged

    private void y0SpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_y0SpinnerStateChanged
        int y0 = y0Model.getNumber().intValue();
        int y1 = y1Model.getNumber().intValue();
        if(y0 > y1){
            y1Model.setValue(y0);
        }
        createNewImage();
    }//GEN-LAST:event_y0SpinnerStateChanged

    private void y1SpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_y1SpinnerStateChanged
        int y0 = y0Model.getNumber().intValue();
        int y1 = y1Model.getNumber().intValue();
        if(y1 < y0){
            y0Model.setValue(y1);
        }
        createNewImage();
    }//GEN-LAST:event_y1SpinnerStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel filenameLabel;
    private javax.swing.JTextField filenameTextField;
    private javax.swing.JLabel horizontalLabel;
    private javax.swing.JLabel imageLabel;
    private javax.swing.JScrollPane imageScrollPane;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel rangeLabel;
    private javax.swing.JButton selectButton;
    private javax.swing.JLabel verticalLabel;
    private javax.swing.JLabel widthLabel;
    private javax.swing.JTextField widthTextField;
    private javax.swing.JSpinner x0Spinner;
    private javax.swing.JSpinner x1Spinner;
    private javax.swing.JSpinner y0Spinner;
    private javax.swing.JSpinner y1Spinner;
    // End of variables declaration//GEN-END:variables
}