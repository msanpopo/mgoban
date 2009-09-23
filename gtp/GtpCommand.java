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

package gtp;

public enum GtpCommand {
    // ここから Required Commands
    PROTOCOL_VERSION("protocol_version"),
    NAME("name"),
    VERSION("version"),
    KNOWN_COMMAND("known_command"),     // TODO
    LIST_COMMAND("list_commands"),      // TODO
    QUIT("quit"),
    BOARDSIZE("boardsize"),
    CLEAR_BOARD("clear_board"),
    KOMI("komi"),
    PLAY("play"),
    GENMOVE("genmove"),
    // ここまで Required Commands

    // その他の gtp の仕様に載ってるコマンド
    
    // TODO mGoban の作りが下のコマンドがあることを前提にしているのは問題。
    // 例えば MoGo は final_score コマンドを持たない。（final_status_list は持つ）
    
    // FIXED_HANDICAP は３子の置き石の位置がおかしいので使わないことにする
    // 例えば世間では右下に石がくるが、gnugo は左上にくる。
    FIXED_HANDICAP("fixed_handicap"),   // 使ってはいけない
    PLACE_FREE_HANDICAP("place_free_handicap"),     // TODO
    SET_FREE_HANDICAP("set_free_handicap"), 
    UNDO("undo"),
    TIME_SETTINGS("time_settings"),         // TODO
    TIME_LEFT("time_left"),                 // TODO
    FINAL_SCORE("final_score"),
    FINAL_STATUS_LIST("final_status_list"),
    LOADSGF("loadsgf"),                     // TODO
    REG_GENMOVE("reg_genmove"),             // TODO 
    SHOWBOARD("showboard"),

    // mGoban 専用で追加
    UNKNOWN("");
    
    
    private final String command;
    
    private GtpCommand(String command){
	this.command = command;
    }
    
    @Override
    public String toString(){
	return command;
    }
}