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

import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound implements LineListener{
    private String resource;

    private Clip clip;
    
    public Sound(String resource){
        this.resource = resource;
    }
    
    public void play(){
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream(resource);
        
        AudioInputStream audioStream = null;

        try {
            audioStream = AudioSystem.getAudioInputStream(stream);
            
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (UnsupportedAudioFileException ex) {
            ex.printStackTrace();
        }
        
        AudioFormat format = audioStream.getFormat();
        DataLine.Info info = new DataLine.Info(Clip.class, format);
        
        try {
            clip = (Clip) AudioSystem.getLine(info);
            clip.addLineListener(this);
            
        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
        }
        
        try {
            clip.open(audioStream);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
        }

        // System.out.println("Sound.play:" + clip.isRunning());
        
        clip.setFramePosition(0);
        clip.start();
        
    }
    
    public void update(LineEvent event) {
        //System.out.println("Sound.update:type:" +  event.getType());
        if(event.getType() == LineEvent.Type.STOP){
            clip.close();
        }
    }
}