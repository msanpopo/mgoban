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

import java.util.ArrayList;

import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;

public class BoardSettingPanel extends javax.swing.JPanel {
    private static final String BOARDSIZE = "盤サイズ";
    private static final String HANDICAP = "置き石";
    private static final String KOMI = "コミ";
    
    private SpinnerListModel boardSizeModel;
    private SpinnerListModel handicapModel;
    private SpinnerNumberModel komiModel;

    public BoardSettingPanel() {
        int[] boardSizeArray = {9, 13, 19};
        ArrayList<Integer> boardSizeList = arrayToList(boardSizeArray);
        
        int[] handicapArray = {0, 2, 3, 4, 5, 6, 7, 8, 9};
        ArrayList<Integer> handicapList = arrayToList(handicapArray);
        
        boardSizeModel = new SpinnerListModel(boardSizeList);
        handicapModel = new SpinnerListModel(handicapList);
        komiModel = new SpinnerNumberModel(6.5, -100.0, 100.0, 0.5);
        
        initComponents();
        
        boardSizeLabel.setText(BOARDSIZE);
        handicapLabel.setText(HANDICAP);
        komiLabel.setText(KOMI);
        
        boardSizeSpinner.setModel(boardSizeModel);
        handicapSpinner.setModel(handicapModel);
        komiSpinner.setModel(komiModel);
        
        boardSizeSpinner.setValue(new Integer(19));
    }
    
    private ArrayList<Integer> arrayToList(int[] a){
        ArrayList<Integer> list = new ArrayList<Integer>();
        
        for(int i : a){
            list.add(new Integer(i));
        }
        
        return list;
    }
    
    public int getBoardSize(){
        Integer boardSize = (Integer)boardSizeModel.getValue();
        return boardSize.intValue();
    }
    
    public void setBoardSize(int n){
        boardSizeModel.setValue(n);
    }
    
    public int getHandicap(){
        int handicap = (Integer)handicapModel.getValue();
        return handicap;
    }
    
    public double getKomi(){
        return komiModel.getNumber().doubleValue();
    }
    
    @Override
    public void setEnabled(boolean b){
        setBoardSizeEnabled(b);
        setHandicapEnabled(b);
        setKomiEnabled(b);
    }
    
    public void setBoardSizeEnabled(boolean b){
        boardSizeLabel.setEnabled(b);
        boardSizeSpinner.setEnabled(b);
    }
    
    public void setHandicapEnabled(boolean b){
        handicapLabel.setEnabled(b);
        handicapSpinner.setEnabled(b);
    }
    
    public void setKomiEnabled(boolean b){
        komiLabel.setEnabled(b);
        komiSpinner.setEnabled(b);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        boardSizeLabel = new javax.swing.JLabel();
        komiLabel = new javax.swing.JLabel();
        handicapLabel = new javax.swing.JLabel();
        komiSpinner = new javax.swing.JSpinner();
        boardSizeSpinner = new javax.swing.JSpinner();
        handicapSpinner = new javax.swing.JSpinner();

        boardSizeLabel.setText("Board Size :");

        komiLabel.setText("Komi :");

        handicapLabel.setText("Handicap :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(boardSizeLabel)
                    .addComponent(komiLabel)
                    .addComponent(handicapLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(boardSizeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(handicapSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(komiSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(boardSizeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boardSizeLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(handicapSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(handicapLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(komiLabel)
                    .addComponent(komiSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel boardSizeLabel;
    private javax.swing.JSpinner boardSizeSpinner;
    private javax.swing.JLabel handicapLabel;
    private javax.swing.JSpinner handicapSpinner;
    private javax.swing.JLabel komiLabel;
    private javax.swing.JSpinner komiSpinner;
    // End of variables declaration//GEN-END:variables
}