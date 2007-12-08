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

import go.gui.InputListener;
import go.gui.InOutPanel;
import java.awt.BorderLayout;

import javax.swing.JPanel;

import wing.MessageReceiver;
import wing.message.Message;

@SuppressWarnings("serial")
public class TerminalPanel extends JPanel implements InputListener, MessageReceiver{
    private NetGo netGo;
    
    private InOutPanel inOutPanel;
    
    public TerminalPanel(NetGo netGo){
        this.netGo = netGo;
        this.netGo.getServer().addReceiver(this);
        
        inOutPanel = new InOutPanel();
        inOutPanel.setInputListener(this);
        
        setLayout(new BorderLayout());
        add(inOutPanel, BorderLayout.CENTER);
    }
    
    public void textInputed(Object source, String text) {
        // System.out.println("textInputed : " + text);
        inOutPanel.addText("##### send command ### :" + text + ":");
        netGo.sendCommand(text);
    }
    
    public void receive(Message wm) {
        for(String s : wm.getMessageCollection()){
            inOutPanel.addText(s);
        }
    }
}