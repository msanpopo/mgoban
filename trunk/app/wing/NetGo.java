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

import go.gui.InputList;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JFrame;

import wing.GoServer;

public class NetGo implements ComponentListener {

    private static final String TELL_FILE = "tell.cfg";
    private static final String SAY_FILE = "say.cfg";
    private static final String KIBITZ_FILE = "kibitz.cfg";
    private GoServer server;
    private WingWindow window;
    private GameObjectManager gameObjectManager;
    private MatchReceiver matchReceiver;
    private InputList tellList;
    private InputList sayList;
    private InputList kibitzList;

    public NetGo() {
        server = null;

        window = new WingWindow(this);
        window.setVisible(true);
        window.addComponentListener(this);

        gameObjectManager = new GameObjectManager(this);
        matchReceiver = new MatchReceiver(this);

        tellList = new InputList(TELL_FILE);
        sayList = new InputList(SAY_FILE);
        kibitzList = new InputList(KIBITZ_FILE);
    }

    public InputList getTellInputList() {
        return tellList;
    }

    public InputList getSayInputList() {
        return sayList;
    }

    public InputList getKibitzInputList() {
        return kibitzList;
    }

    public String myName() {
        return server.getLoginName();
    }

    public GoServer getServer() {
        return server;
    }

    public WingWindow getWingWindow() {
        return window;
    }

    public JFrame getWindow() {
        return window;
    }

    public GameObjectManager getGameObjectManager() {
        return gameObjectManager;
    }

    public MatchReceiver getMatchReceiver() {
        return matchReceiver;
    }

    public boolean connect(GoServer server, String loginName, String password) {
        this.server = server;
        this.server.addReceiver(gameObjectManager);
        this.server.addReceiver(matchReceiver);
        this.server.addReceiver(window);

        boolean connected = server.connect(loginName, password);
        window.connected(connected);
        
        if(connected){
            sendCommand("set looking off");
            sendCommand("set open off");
        }
        
        return connected;
    }

    public void disconnect() {
        if (server != null) {
            server.close();
        }
        
        window.connected(false);
    }

    public void sendCommand(String command) {
        if (server != null) {
            server.sendCommand(command);
        }
    }

    public void close() {
        System.out.println("NetGo.close():");
        
        disconnect();

        window.dispose();
    }

    // ComponentListener
    public void componentHidden(ComponentEvent e) {
        System.out.println("NetGo:componentHidden:main window Hidden");

        close();
    }

    public void componentMoved(ComponentEvent e) {}
    public void componentResized(ComponentEvent e) {}
    public void componentShown(ComponentEvent e) {}
}