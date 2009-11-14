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

package app;

/**
 * 終了時に保存して、次回起動時に復元する設定項目のリストとそのデフォルト値。
 *
 */
public enum GoConfig {
    
    GO_WINDOW_WIDTH("goWindowWidth", "900"),
    GO_WINDOW_HEIGHT("goWindowHeight", "700"),
    GO_WINDOW_H_DIVIDER_LOCATION("goWindowHDividerLocation", "600"),
    GO_WINDOW_V_DIVIDER_LOCATION("goWindowVDividerLocation", "180");
    
    private final String key;
    private final String defaultValue;

    private GoConfig(String key, String defaultValue){
        this.key = key;
        this.defaultValue = defaultValue;
    }
    
    public String getKey(){
        return key;
    }
    
    public String getDefaultValue(){
        return defaultValue;
    }
}