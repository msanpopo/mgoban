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
package app;

import app.sgf.SgfActionList;
import app.sgf.SgfGoGame;
import app.sgf.SgfGoWindow;
import app.wing.NetGo;
import go.GoColor;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.InputStream;
import javax.swing.JFrame;
import sgf.GameTree;
import sgf.GoNode;
import sgf.property.GameInfoProperty;
import sound.SoundPlayer;

public class App {
    private static App appData;
    private static final String APP_DIR_LINUX = ".mgoban";
    private static final String APP_DIR_WINDOWS = "mgoban";
    private static final String GTP_SETTING_FILE = "gtpengine.cfg";
    private static final String SERVER_SETTING_FILE = "server.cfg";
    private static final String MGO_SETTING_FILE = "mgoban.cfg";
    private File appDir;
    private File cfgFile;
    private Config config;
    private MainWindow mainWindow;

    public static App getInstance() {
        if (appData == null) {
            appData = new App();
        }

        return appData;
    }

    private App() {
        String appDirTmp;
        String os = System.getProperty("os.name");
        System.out.println("os:" + os);

        if (os.equalsIgnoreCase("linux")) {
            appDirTmp = APP_DIR_LINUX;
        } else if (os.equalsIgnoreCase("windows")) {
            appDirTmp = APP_DIR_WINDOWS;
        } else {
            System.err.println("unknown os.name:" + os);
            appDirTmp = APP_DIR_WINDOWS;
        }

        File homeDir = new File(System.getProperty("user.home"));
        appDir = new File(homeDir, appDirTmp);
        cfgFile = new File(appDir, MGO_SETTING_FILE);

        System.out.println("app dir:" + appDir);
        System.out.println("cfg file:" + cfgFile);

        if (appDir.exists() == false) {
            appDir.mkdir();
        }

        mainWindow = null;
    }

    public File getAppDir() {
        return appDir;
    }

    public InputStream getDeaultGtpSettingStream() {
        return this.getClass().getClassLoader().getResourceAsStream(GTP_SETTING_FILE);
    }

    public InputStream getDeaultServerSettingStream() {
        return this.getClass().getClassLoader().getResourceAsStream(SERVER_SETTING_FILE);
    }

    public File getGtpSettingFile() {
        return new File(appDir, GTP_SETTING_FILE);
    }

    public File getServerSettingFile() {
        return new File(appDir, SERVER_SETTING_FILE);
    }

    private File checkArgs(String[] args) {
        File sgfFile = null;

        for (String s : args) {
            System.out.println("App.checkArgs:" + s);
            if (s.matches(".+\\.sgf$")) {
                sgfFile = new File(s);
            }
        }
        return sgfFile;
    }

    public void start(String[] args) {
        File sgfFile = checkArgs(args);

        config = new Config(cfgFile);

        mainWindow = new MainWindow();
        mainWindow.addComponentListener(new ComponentAdapter() {

            @Override
            public void componentHidden(ComponentEvent e) {
                System.out.println("componentHidden: app");
                config.write();
            }
        });
        /*
        String lookAndFeelClassName = config.getProperty("lookAndFeel", "javax.swing.plaf.metal.MetalLookAndFeel");
        try {
            UIManager.setLookAndFeel(lookAndFeelClassName);
            SwingUtilities.updateComponentTreeUI(ui);
        } catch (Exception ex) {
            System.err.println("Ui UIManager.setLookAndFeel:" + lookAndFeelClassName);
            ex.printStackTrace();
        }
        */
        mainWindow.setVisible(true);
        if (sgfFile != null) {
            startEditor(mainWindow, sgfFile);
        }
    }

    public void soundPlay(SoundType type) {
        SoundPlayer.play(type);
    }

    public void startEditor(JFrame owner, File sgfFile) {
        int boardSize = 19;
        double komi = 6.5;

        GoNode root = GoNode.createRootNode(boardSize);
        
        GameInfoProperty gip = new GameInfoProperty();
        gip.setKomi(komi);
        gip.setPlayerName(GoColor.BLACK, "Black");
        gip.setPlayerName(GoColor.WHITE, "White");
        
        root.setGameInfoProperty(gip);

        SgfGoGame goGame = new SgfGoGame(owner);
        owner.addComponentListener(goGame);

        GameTree tree = goGame.getGameTree();
        tree.setNewTree(root);

        SgfActionList actionList = new SgfActionList(goGame);

        SgfGoWindow sgfGoWindow = new SgfGoWindow(goGame, actionList);

        goGame.setWindow(sgfGoWindow);
        goGame.setOperationPanel(sgfGoWindow);
        goGame.addGoGameListener(sgfGoWindow);
        goGame.addGoGameListener(actionList);
        goGame.connectObject();
        goGame.start();

        if (sgfFile != null) {
            actionList.openAction.doAction(sgfFile);
        }
    }

    public void startEditor(JFrame owner) {
        startEditor(owner, null);
    }
    
    public void startNetGo(JFrame owner){
        NetGo netGo = new NetGo();
        owner.addComponentListener(netGo);
    }
    
    public Config getConfig(){
        return config;
    }
}