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
package wing;

import java.io.DataOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/*
 * telnet コマンドをフィルターする（らしい）。
 * 0-9, 12 を 0 に置き換えたりしているが、なぜそうしているかはわからない。
 * ほぼ jago からもらってきたまま。
 */
public class TelnetStream extends FilterInputStream {

    private InputStream input;
    private DataOutputStream out;

    public TelnetStream(InputStream i, DataOutputStream o) {
        super(i);
        input = i;
        out = o;
    }

    @Override
    public int read() throws IOException {
        while (true) {
            int c = input.read();

            if (c == -1) { // ストリームの終わり
                // System.out.println("############## TelnetStream : c:" + c);
                return c;
            } else if (c >= 0 && c <= 255) { // telnet で扱う文字範囲
                if (c == 255) { // telnet コマンドのはじまり
                    int command = input.read();

                    System.out.println("############## TelnetStream : command received:" + command);

                    if (command == 253) { // do の要求に wont をかえす
                        c = input.read(); // 要求の内容
                        out.write(255);
                        out.write(252);
                        out.write(c);
                        System.out.println("############## TelnetStream : command received:do:" + c);
                    } else if (command == 246) { // ayt の要求に nop をかえす
                        out.write(255);
                        out.write(241);
                    }

                    if (c == -1) {
                        System.out.println("############## TelnetStream : c == -1:");
                        return c;
                    }
                    continue;
                } else if (c >= 0 && c <= 9) {
                    System.out.println("TelnetStream : drop ##:" + c);
                    continue;
                } else if (c == 11 || c == 12) {
                    System.out.println("TelnetStream : drop ##:" + c);
                    continue;
                }
                // System.out.println("############## TelnetStream : c:" + c);
                return c;
            } else { // telnet で扱う文字範囲外
                System.out.println("TelnetStream c < 0 or c > 255 :c:" + c);
            }
        }
    }

    // InputStreamReader からは read() でなくこちらが呼ばれる。
    @Override
    public int read(byte b[], int off, int len) throws IOException {
        int i = 0;
        int c;

        c = read();
        if (c == -1) {
            return -1;
        } else {
            b[off + i] = (byte) c;
            ++i;
        }

        while (input.available() > 0 && i < len && c != -1) {
            c = read();
            // System.out.print(" " + (int)c);
            b[off + i] = (byte) c;
            ++i;
        }

        return i;
    }
}
