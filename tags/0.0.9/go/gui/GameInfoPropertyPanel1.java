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

import sgf.property.GameInfoProperty;
import sgf.property.PropertyId;

public class GameInfoPropertyPanel1 extends javax.swing.JPanel {
    private static final String BLACK_PLAYER = "黒";
    private static final String WHITE_PLAYER = "白";
    private static final String RULE = "ルール";
    private static final String GAME = "対局";
    
    private static final String NAME = "名前";
    private static final String RANK = "ランク";
    private static final String TEAM = "チーム";
    private static final String HANDICAP = "置き石";
    private static final String KOMI = "コミ";
    private static final String TIME_LIMIT = "持ち時間";
    private static final String OVERTIME = "時間方式";
    private static final String GAME_NAME = "対局名";
    private static final String PLACE = "場所";
    private static final String DATE = "日時";
    private static final String EVENT = "イベント名";
    private static final String ROUND = "ラウンド";
    
    private GameInfoProperty prop;
    
    public GameInfoPropertyPanel1(GameInfoProperty prop) {
        this.prop = prop;
        
        initComponents();
        
        blackPlayerLabel.setText(BLACK_PLAYER);
        whitePlayerLabel.setText(WHITE_PLAYER);
        ruleLabel.setText(RULE);
        gameLabel.setText(GAME);
        pbLabel.setText(NAME);
        brLabel.setText(RANK);
        btLabel.setText(TEAM);
        pwLabel.setText(NAME);
        wrLabel.setText(RANK);
        wtLabel.setText(TEAM);
        ruLabel.setText(RULE);
        haLabel.setText(HANDICAP);
        kmLabel.setText(KOMI);
        tmLabel.setText(TIME_LIMIT);
        otLabel.setText(OVERTIME);
        gnLabel.setText(GAME_NAME);
        pcLabel.setText(PLACE);
        dtLabel.setText(DATE);
        evLabel.setText(EVENT);
        roLabel.setText(ROUND);
        
        pbTextField.setText(prop.getString(PropertyId.PB));
        brTextField.setText(prop.getString(PropertyId.BR));
        btTextField.setText(prop.getString(PropertyId.BT));
        pwTextField.setText(prop.getString(PropertyId.PW));
        wrTextField.setText(prop.getString(PropertyId.WR));
        wtTextField.setText(prop.getString(PropertyId.WT));
        
        ruTextField.setText(prop.getString(PropertyId.RU));
        
        haTextField.setText(prop.getString(PropertyId.HA));
        kmTextField.setText(prop.getString(PropertyId.KM));
        tmTextField.setText(prop.getString(PropertyId.TM));
        
        otTextField.setText(prop.getString(PropertyId.OT));
        
        gnTextField.setText(prop.getString(PropertyId.GN));
        pcTextField.setText(prop.getString(PropertyId.PC));
        dtTextField.setText(prop.getString(PropertyId.DT));
        evTextField.setText(prop.getString(PropertyId.EV));
        roTextField.setText(prop.getString(PropertyId.RO));
        


    }
    
    public void done(){
        prop.setString(PropertyId.PB, pbTextField.getText());
        prop.setString(PropertyId.BR, brTextField.getText());
        prop.setString(PropertyId.BT, btTextField.getText());
        prop.setString(PropertyId.PW, pwTextField.getText());
        prop.setString(PropertyId.WR, wrTextField.getText());
        prop.setString(PropertyId.WT, wtTextField.getText());
        
        prop.setString(PropertyId.RU, ruTextField.getText());
        
        prop.setString(PropertyId.HA, haTextField.getText());
        prop.setString(PropertyId.KM, kmTextField.getText());
        prop.setString(PropertyId.TM, tmTextField.getText());
        
        prop.setString(PropertyId.OT, otTextField.getText());
        
        prop.setString(PropertyId.GN, gnTextField.getText());
        prop.setString(PropertyId.PC, pcTextField.getText());
        prop.setString(PropertyId.DT, dtTextField.getText());
        prop.setString(PropertyId.EV, evTextField.getText());
        prop.setString(PropertyId.RO, roTextField.getText());
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        blackPlayerLabel = new javax.swing.JLabel();
        btLabel = new javax.swing.JLabel();
        pbLabel = new javax.swing.JLabel();
        brLabel = new javax.swing.JLabel();
        btTextField = new javax.swing.JTextField();
        pbTextField = new javax.swing.JTextField();
        brTextField = new javax.swing.JTextField();
        ruleLabel = new javax.swing.JLabel();
        haLabel = new javax.swing.JLabel();
        ruLabel = new javax.swing.JLabel();
        kmLabel = new javax.swing.JLabel();
        tmLabel = new javax.swing.JLabel();
        otLabel = new javax.swing.JLabel();
        haTextField = new javax.swing.JTextField();
        ruTextField = new javax.swing.JTextField();
        gameLabel = new javax.swing.JLabel();
        whitePlayerLabel = new javax.swing.JLabel();
        wtLabel = new javax.swing.JLabel();
        wrLabel = new javax.swing.JLabel();
        gnLabel = new javax.swing.JLabel();
        pcLabel = new javax.swing.JLabel();
        dtLabel = new javax.swing.JLabel();
        evLabel = new javax.swing.JLabel();
        roLabel = new javax.swing.JLabel();
        pwLabel = new javax.swing.JLabel();
        roTextField = new javax.swing.JTextField();
        evTextField = new javax.swing.JTextField();
        dtTextField = new javax.swing.JTextField();
        pcTextField = new javax.swing.JTextField();
        gnTextField = new javax.swing.JTextField();
        wrTextField = new javax.swing.JTextField();
        pwTextField = new javax.swing.JTextField();
        wtTextField = new javax.swing.JTextField();
        kmTextField = new javax.swing.JTextField();
        tmTextField = new javax.swing.JTextField();
        otTextField = new javax.swing.JTextField();

        blackPlayerLabel.setFont(new java.awt.Font("Dialog", 1, 14));
        blackPlayerLabel.setText("Black Player");

        btLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        btLabel.setText("Team :");

        pbLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        pbLabel.setText("Name :");

        brLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        brLabel.setText("Rank :");

        ruleLabel.setFont(new java.awt.Font("Dialog", 1, 14));
        ruleLabel.setText("Rule");

        haLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        haLabel.setText("Handicap :");

        ruLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        ruLabel.setText("Rule :");

        kmLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        kmLabel.setText("Komi :");

        tmLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        tmLabel.setText("Time limit :");

        otLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        otLabel.setText("Overtime :");

        gameLabel.setFont(new java.awt.Font("Dialog", 1, 14));
        gameLabel.setText("Game");

        whitePlayerLabel.setFont(new java.awt.Font("Dialog", 1, 14));
        whitePlayerLabel.setText("White Player");

        wtLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        wtLabel.setText("Team :");

        wrLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        wrLabel.setText("Rank :");

        gnLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        gnLabel.setText("Game name :");

        pcLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        pcLabel.setText("Place :");

        dtLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        dtLabel.setText("Date :");

        evLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        evLabel.setText("Event :");

        roLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        roLabel.setText("Round :");

        pwLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        pwLabel.setText("Name :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ruleLabel)
                    .addComponent(blackPlayerLabel)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(ruLabel)
                                    .addComponent(haLabel)
                                    .addComponent(kmLabel)
                                    .addComponent(tmLabel)
                                    .addComponent(otLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(tmTextField, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(kmTextField, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(haTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(ruTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                                    .addComponent(otTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(pbLabel)
                                    .addComponent(brLabel)
                                    .addComponent(btLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btTextField)
                                    .addComponent(brTextField)
                                    .addComponent(pbTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE))))
                        .addGap(17, 17, 17)))
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pwLabel)
                            .addComponent(wrLabel)
                            .addComponent(wtLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(pwTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                            .addComponent(wrTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                            .addComponent(wtTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(pcLabel)
                            .addComponent(dtLabel)
                            .addComponent(evLabel)
                            .addComponent(roLabel)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(gameLabel)
                                .addComponent(whitePlayerLabel))
                            .addComponent(gnLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(evTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                            .addComponent(dtTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                            .addComponent(pcTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                            .addComponent(roTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                            .addComponent(gnTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE))))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {blackPlayerLabel, brLabel, btLabel, haLabel, kmLabel, otLabel, pbLabel, ruLabel, ruleLabel, tmLabel});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {dtLabel, evLabel, gameLabel, gnLabel, pcLabel, pwLabel, roLabel, whitePlayerLabel, wrLabel, wtLabel});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(whitePlayerLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pwTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(wrTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(wtTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(blackPlayerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(pbTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pbLabel)
                            .addComponent(pwLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(brLabel)
                            .addComponent(brTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(wrLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btLabel)
                            .addComponent(btTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(wtLabel))))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(gameLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(gnTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(gnLabel)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(pcTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(pcLabel))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dtTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dtLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(evTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(evLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(roLabel)
                            .addComponent(roTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(ruleLabel)
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(ruLabel)
                                    .addComponent(ruTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(haLabel)
                                    .addComponent(haTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(kmLabel)
                            .addComponent(kmTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tmLabel)
                            .addComponent(tmTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(otLabel)
                            .addComponent(otTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel blackPlayerLabel;
    private javax.swing.JLabel brLabel;
    private javax.swing.JTextField brTextField;
    private javax.swing.JLabel btLabel;
    private javax.swing.JTextField btTextField;
    private javax.swing.JLabel dtLabel;
    private javax.swing.JTextField dtTextField;
    private javax.swing.JLabel evLabel;
    private javax.swing.JTextField evTextField;
    private javax.swing.JLabel gameLabel;
    private javax.swing.JLabel gnLabel;
    private javax.swing.JTextField gnTextField;
    private javax.swing.JLabel haLabel;
    private javax.swing.JTextField haTextField;
    private javax.swing.JLabel kmLabel;
    private javax.swing.JTextField kmTextField;
    private javax.swing.JLabel otLabel;
    private javax.swing.JTextField otTextField;
    private javax.swing.JLabel pbLabel;
    private javax.swing.JTextField pbTextField;
    private javax.swing.JLabel pcLabel;
    private javax.swing.JTextField pcTextField;
    private javax.swing.JLabel pwLabel;
    private javax.swing.JTextField pwTextField;
    private javax.swing.JLabel roLabel;
    private javax.swing.JTextField roTextField;
    private javax.swing.JLabel ruLabel;
    private javax.swing.JTextField ruTextField;
    private javax.swing.JLabel ruleLabel;
    private javax.swing.JLabel tmLabel;
    private javax.swing.JTextField tmTextField;
    private javax.swing.JLabel whitePlayerLabel;
    private javax.swing.JLabel wrLabel;
    private javax.swing.JTextField wrTextField;
    private javax.swing.JLabel wtLabel;
    private javax.swing.JTextField wtTextField;
    // End of variables declaration//GEN-END:variables
}