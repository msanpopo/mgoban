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
import java.util.EnumMap;

public class SoundPlayer {
    private EnumMap<SoundType, Sound> map;

    public SoundPlayer(){
        map = new EnumMap<SoundType, Sound>(SoundType.class);
        for(SoundType type : SoundType.values()){
            Sound sound = new Sound(type);
            map.put(type, sound);
        }
    }
    
    public void play(SoundType type){
        Sound sound = map.get(type);
        sound.play();
    }
    
    public static void main(String[] args){
        SoundPlayer player = new SoundPlayer();
        
        player.play(SoundType.MESSAGE);

//        player.play(SoundType.CLICK);
//        player.play(SoundType.TIME);
        
        for(int i = 0; i < 10; ++i){
            System.out.println("play:" + i);
            player.play(SoundType.CLICK);
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}