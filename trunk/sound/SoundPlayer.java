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

package sound;

import app.SoundType;

public class SoundPlayer {
    
    public static void play(SoundType type) {
        Sound sound = new Sound(type.getResource());
        sound.play();
    }

    public static void main(String[] args){
        
        for(int i = 0; i < 20; ++i){
            play(SoundType.MESSAGE);
            try {
                Thread.sleep(150);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}