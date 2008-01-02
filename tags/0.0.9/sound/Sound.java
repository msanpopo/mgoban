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
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound{

    private SoundType type;
    private Clip clip;
    
    public Sound(SoundType type) {
        this.type = type;
        this.clip = null;
        
        String resource = type.getResource();

        InputStream stream = this.getClass().getClassLoader().getResourceAsStream(resource);
        AudioInputStream audioStream = null;

        try {
            audioStream = AudioSystem.getAudioInputStream(stream);

            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);

            clip = (Clip) AudioSystem.getLine(info);
            clip.open(audioStream);

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (UnsupportedAudioFileException ex) {
            ex.printStackTrace();
        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
        } finally {
            if (audioStream != null) {
                try {
                    audioStream.close();
                } catch (IOException ex) {
                    System.err.println("err:Sound: audioStream.close:" + ex);
                }
            }
        }
    }

    public void play() {
//        System.out.println("Sound.play:" + clip.isRunning());
        Thread thread = new PlayThread();
        thread.start();
    }

    class PlayThread extends Thread{
        public PlayThread(){
        }
        
        @Override
        public void run(){
            synchronized(clip){
                clip.stop();
                clip.setFramePosition(0);
                clip.start();
            }
        }
    }
}