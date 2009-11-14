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
package app.wing.action;

import app.wing.LoginPanel;
import app.wing.NetGo;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;

public class ConnectAction extends AbstractAction {
    private static final String OK = "OK";
    private static final String CANCEL = "キャンセル";
    
    private static final String CONNECT = "接続";
    private static final String CONNECT_ICON = "image/stock_connect.png";
    private static final String DISCONNECT_ICON = "image/stock_disconnect.png";
    private NetGo netGo;
    private Icon connectIcon;
    private Icon disconnectIcon;

    public ConnectAction(NetGo netGo) {
        ClassLoader cl = this.getClass().getClassLoader();
        connectIcon = new ImageIcon(cl.getResource(CONNECT_ICON));
        disconnectIcon = new ImageIcon(cl.getResource(DISCONNECT_ICON));

        putValue(Action.NAME, CONNECT);
        putValue(Action.SHORT_DESCRIPTION, CONNECT);
        putValue(Action.SMALL_ICON, disconnectIcon);
        putValue(Action.LARGE_ICON_KEY, disconnectIcon);

        this.netGo = netGo;
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("ConnectAction:" + e);
        JToggleButton button = (JToggleButton) e.getSource();
        if (button.isSelected()) {
            LoginPanel panel = new LoginPanel(netGo);

            String[] options = {OK, CANCEL};
            int retval = JOptionPane.showOptionDialog(netGo.getWindow(), panel, "Login",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, options, options[0]);

            if (retval == 0) {
                if(panel.connect(netGo) == true){
                    putValue(Action.SMALL_ICON, connectIcon);
                    putValue(Action.LARGE_ICON_KEY, connectIcon);
                }
            }
        } else {
            putValue(Action.SMALL_ICON, disconnectIcon);
            putValue(Action.LARGE_ICON_KEY, disconnectIcon);
            netGo.disconnect();
        }
    }
}
