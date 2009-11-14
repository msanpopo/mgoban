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

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;

import wing.MessageReceiver;
import wing.message.CreatingMatchMessage;
import wing.message.DeclineMatchMessage;
import wing.message.MatchRequestMessage;
import wing.message.Message;

public class MatchReceiver implements MessageReceiver, ComponentListener{
    private NetGo netGo;
    
    private ArrayList<MatchSettingDialog> matchSettingDialogList;
    
    public MatchReceiver(NetGo netGo){
        this.netGo = netGo;
        this.matchSettingDialogList = new ArrayList<MatchSettingDialog>();
    }
    
    public void createMatchWindow(String name){
        MatchSettingDialog msw = new MatchSettingDialog(netGo);
        msw.setOpponent(name);
        msw.addComponentListener(this);
        msw.setVisible(true);
        matchSettingDialogList.add(msw);
    }
    
    public void receive(Message wm) {
        if(wm instanceof MatchRequestMessage){
            MatchRequestMessage m = (MatchRequestMessage)wm;
            
            String command = m.getAcceptString();
            System.out.println("MatchReceiver: match request detected:" + command);
            
            String name = m.getName();
            MatchSettingDialog w = findMatchSettingDialog(name);
            if(w == null){
                System.out.println("MatchReceiver: match request detected: new request");
                
                w = new MatchSettingDialog(netGo);
                w.addComponentListener(this);
                w.setVisible(true);
                
                matchSettingDialogList.add(w);
                
                w.setMatchCommand(command); // w.setVisible でダイアログ内のコマンドが初期化されるので、その後でコマンドをセットする
            }else{
                System.out.println("MatchReceiver: match request detected:not new request");
                
                w.updateMatchCommand(command);
            }

        }else if(wm instanceof DeclineMatchMessage){
            DeclineMatchMessage m = (DeclineMatchMessage)wm;
            
            System.out.println("MatchReceiver: match declined");
            String name = m.getName();
            MatchSettingDialog w = findMatchSettingDialog(name);
            if(w == null){
                System.err.println("MatchReceiver: decline received??:" + name);
            }else{
                w.decline();
            }
            
        }else if(wm instanceof CreatingMatchMessage){
            CreatingMatchMessage m = (CreatingMatchMessage)wm;
            
            System.out.println("MatchReceiver: match creating");
            String name = m.getName();
            MatchSettingDialog w = findMatchSettingDialog(name);
            if(w == null){
                System.err.println("MatchReceiver: creating received??:" + name);
            }else{
                w.sendHandicap();
                w.sendKomi();
                w.setVisible(false);
            }
        }
    }
    
    private MatchSettingDialog findMatchSettingDialog(String name){
        System.out.println("MatchReceiver:findMatchSettingWindow:" + name + ":");
        for(MatchSettingDialog w : matchSettingDialogList){
            System.out.println("MatchReceiver:findMatchSettingWindow:loop:" + w.getOpponent() + ":");
            if(w.getOpponent().equals(name)){
                
                return w;
            }
        }
        return null;
    }
    
    // ComponentListener
    public void componentHidden(ComponentEvent e) {
        System.out.println("MatchSettingDialog hidden");
        MatchSettingDialog msw = (MatchSettingDialog)e.getSource();
        matchSettingDialogList.remove(msw);
    }
    public void componentMoved(ComponentEvent e) {}
    public void componentResized(ComponentEvent e) {}
    public void componentShown(ComponentEvent e) {}
}