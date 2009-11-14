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

package wing;

import wing.translator.GamesTranslator;
import wing.translator.InfoTranslator;
import wing.translator.KibitzTranslator;
import wing.translator.MoveTranslator;
import wing.translator.NullTranslator;
import wing.translator.Translator;
import wing.translator.SavedTranslator;
import wing.translator.SayTranslator;
import wing.translator.SgfTranslator;
import wing.translator.ShoutTranslator;
import wing.translator.StatusTranslator;
import wing.translator.StoredTranslator;
import wing.translator.TellTranslator;
import wing.translator.ThistTranslator;
import wing.translator.WhoTranslator;
import wing.translator.YellTranslator;

/**
 * サーバーからくるメッセージの番号リスト
 * <pre>
 * nngs の docs／server-protocol.txt と src/servercodes.h から作った。
 * 欠番があるし、何に使っているのかわからないものも多い。
 * </pre>
 */
public enum ServerCode {
    UNKNOWN(-1, null),       // sanpo が追加
    NONE(0, null),
    PROMPT(1, null),
    BEEP(2, null),
    BOARD(3, null),
    DOWN(4, null),
    ERROR(5, null),
    FILE(6, null),
    GAMES(7, GamesTranslator.class),
    HELP(8, null),
    INFO(9, InfoTranslator.class),
    LAST(10, null),
    KIBITZ(11, KibitzTranslator.class),
    LOAD(12, null),
    LOOK_M(13, null),
    MESSAGE(14, null),
    MOVE(15, MoveTranslator.class),
    OBSERVE(16, null),
    REFRESH(17, null),
    SAVED(18, SavedTranslator.class),      // stored の返事(wing)
    SAY(19, SayTranslator.class),
    SCORE_M(20, null),
    SHOUT(21, ShoutTranslator.class),
    STATUS(22, StatusTranslator.class),
    STORED(23, StoredTranslator.class),     // stored の返事(nngs)
    TELL(24, TellTranslator.class),
    THIST(25, ThistTranslator.class),      // 最近 10 局の対局結果。results コマンドへの返事
    TIME(26, null),
    WHO(27, WhoTranslator.class),
    UNDO(28, null),
    SHOW(29, null),
    TRANS(30, null),
    //
    YELL(32, YellTranslator.class),       // channel の会話
    TEACH(33, null),
    //
    //
    //
    //
    //
    MVERSION(39, null),
    DOT(40, null),
    CLIVRFY(41, null),
    USER(42, null),
    SGF_F(43, SgfTranslator.class);         // wing 専用 ?

    private final int no;
    private final Translator translator;
    
    private ServerCode(int no, Class<?> translatorClass){
        this.no = no;

        if(translatorClass == null){
            this.translator = new NullTranslator();
        }else{
            Translator p = null;
            try {
                p = (Translator) translatorClass.newInstance();
            } catch (IllegalAccessException ex) {
                System.err.println("ServerCode:" + ex);
                ex.printStackTrace();
            } catch (InstantiationException ex) {
                System.err.println("ServerCode:" + ex);
                ex.printStackTrace();
            }
            this.translator = p;
        }
    }

    public static ServerCode get(int no){
        for(ServerCode sc : values()){
            if(sc.no == no){
                return sc;
            }
        }
        System.out.println("ServerCode.get() invalid no.   no:" + no);
        return UNKNOWN;
    }
    
    public int getNo(){
        return no;
    }
    
    public Translator getTranslator(){
        return translator;
    }
    
    @Override
    public String toString(){
        return Integer.toString(no);
    }
}