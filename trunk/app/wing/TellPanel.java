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

import go.gui.InOutPanel;
import go.gui.InputListener;
import java.awt.BorderLayout;
import javax.swing.JPanel;

public class TellPanel extends JPanel implements InputListener{
    private String name;
    private NetGo netGo;
    private InOutPanel ioPanel;
    
    private String myName;
    
    
    public TellPanel(String name, NetGo netGo) {
        this.name = name;
        this.netGo = netGo;
        
        this.ioPanel = new InOutPanel(netGo.getTellInputList());
        this.ioPanel.setInputListener(this);
        
        this.myName = netGo.myName();
        
        setLayout(new BorderLayout());
        add(ioPanel, BorderLayout.CENTER);
    }
    
    public void addText(String s){
        StringBuilder str = new StringBuilder();
        str.append(name).append(" : ").append(s);
        ioPanel.addText(str.toString());
    }
    
    public void textInputed(Object source, String text) {
        StringBuilder strDisp = new StringBuilder();
        strDisp.append(myName).append(" : ").append(text);
        ioPanel.addText(strDisp.toString(), InOutPanel.GRAY);
        
        StringBuilder strSend = new StringBuilder();
        strSend.append("tell ").append(name).append(" ").append(text);
        System.out.println("TellTab:send:" + strSend.toString() + ":");
        netGo.sendCommand(strSend.toString());
    }
}