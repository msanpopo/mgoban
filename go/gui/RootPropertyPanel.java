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

import sgf.property.PropertyId;
import sgf.property.RootProperty;

public class RootPropertyPanel extends javax.swing.JPanel {
    private static final String APPLICATION = "アプリケーション";
    private static final String CHARSET = "文字セット";
    private static final String FILEFORMAT = "フォーマット";
    private static final String GAME = "ゲーム種別";
    private static final String STYLE = "表示スタイル";
    private static final String SIZE = "盤サイズ";
    
    private RootProperty prop;
    
    public RootPropertyPanel(RootProperty prop) {
        this.prop = prop;
        
        initComponents();
        
        apLabel.setText(APPLICATION);
        caLabel.setText(CHARSET);
        ffLabel.setText(FILEFORMAT);
        gmLabel.setText(GAME);
        stLabel.setText(STYLE);
        szLabel.setText(SIZE);
        
        apTextField.setText(prop.getApplication());
        caTextField.setText(prop.getCharset());
        if(prop.hasProperty(PropertyId.FF)){
            int ff = prop.getFileFormat();
            ffTextField.setText(Integer.toString(ff));
        }
        if(prop.hasProperty(PropertyId.GM)){
            int gm = prop.getGame();
            gmTextField.setText(Integer.toString(gm));
        }
        if(prop.hasProperty(PropertyId.ST)){
            int st = prop.getStyle();
            stTextField.setText(Integer.toString(st));
        }
        if(prop.hasProperty(PropertyId.SZ)){
            int sz = prop.getBoardSize();
            szTextField.setText(Integer.toString(sz));
        }
    }
    
    public void done(){
        prop.setCharset(caTextField.getText());
        String style = stTextField.getText();
        if(style.isEmpty() == false){
            prop.setStyle(Integer.parseInt(style));
        }else{
            prop.removeProperty(PropertyId.ST);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        gmLabel = new javax.swing.JLabel();
        ffLabel = new javax.swing.JLabel();
        caLabel = new javax.swing.JLabel();
        apLabel = new javax.swing.JLabel();
        stLabel = new javax.swing.JLabel();
        szLabel = new javax.swing.JLabel();
        szTextField = new javax.swing.JTextField();
        stTextField = new javax.swing.JTextField();
        gmTextField = new javax.swing.JTextField();
        ffTextField = new javax.swing.JTextField();
        caTextField = new javax.swing.JTextField();
        apTextField = new javax.swing.JTextField();

        gmLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        gmLabel.setText("Game :");

        ffLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        ffLabel.setText("Fileformat :");

        caLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        caLabel.setText("Charset :");

        apLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        apLabel.setText("Application :");

        stLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        stLabel.setText("Stype :");

        szLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        szLabel.setText("Size :");

        szTextField.setEnabled(false);

        gmTextField.setEnabled(false);

        ffTextField.setEnabled(false);

        apTextField.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(gmLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ffLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(caLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(stLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(szLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(apLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(caTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                    .addComponent(apTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(szTextField, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(stTextField, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(gmTextField, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(ffTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(24, 24, 24))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(apTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(apLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(caLabel)
                    .addComponent(caTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ffLabel)
                    .addComponent(ffTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(gmLabel)
                    .addComponent(gmTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(stLabel)
                    .addComponent(stTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(szLabel)
                    .addComponent(szTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel apLabel;
    private javax.swing.JTextField apTextField;
    private javax.swing.JLabel caLabel;
    private javax.swing.JTextField caTextField;
    private javax.swing.JLabel ffLabel;
    private javax.swing.JTextField ffTextField;
    private javax.swing.JLabel gmLabel;
    private javax.swing.JTextField gmTextField;
    private javax.swing.JLabel stLabel;
    private javax.swing.JTextField stTextField;
    private javax.swing.JLabel szLabel;
    private javax.swing.JTextField szTextField;
    // End of variables declaration//GEN-END:variables
}