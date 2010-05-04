/*
 *  mGoban - GUI for Go
 *  Copyright (C) 2007, 2009, 2010  sanpo
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

/**
 * GtpInputThread や GtpErrorThread がイベントディスパッチスレッドに投げる文字列。
 * <pre>
 * 正常に動いているなら GTP エンジンが標準出力やエラー出力に出力した文字列が入っていて、
 * 異常があったなら、その内容を表す文字列が入っている。
 * 正常か異常かの情報も持っている。
 * </pre>
 */
public class GtpResponse {
    private String response;
    private boolean isError;
    
    public GtpResponse(String response, boolean isError) {
        this.response = response;
        this.isError = isError;
    }
    
    public boolean isError(){
        return isError;
    }
    
    public String getResponse(){
        return response;
    }
    
    @Override
    public String toString(){
        return response;
    }
}