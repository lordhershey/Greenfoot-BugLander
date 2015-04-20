import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.lang.*;
import java.util.*;
import javax.sound.sampled.*;
import java.net.URL;
import java.io.File;
import java.io.IOException;

public enum SoundEffect {
    PING("sounds/17__anton__glass-a-pp.wav"),

    PING2("sounds/17__anton__glass-a-pp.wav"),

    PING3("sounds/17__anton__glass-a-pp.wav"),
    
    //PING4("sounds/17__anton__glass-a-pp.wav"),
    
    //PING5("sounds/17__anton__glass-a-pp.wav"),
    
    BREAK("sounds/glass-break.wav"),

    BUZZ("sounds/bumblebee.wav"),
    BELL("sounds/small_ring.wav"),
    FAIL("sounds/price-is-right-fail.wav");

    // Nested class for specifying volume
    public static enum Volume {
        MUTE, LOW, MEDIUM, HIGH
    }

    public static SoundEffect getPing(int i)
    {
        SoundEffect ping;
        switch(i)
        {
            //case 4:
            //return PING5;
            //case 3:
            //return PING4;
            case 2:
            return PING3;
            case 1:
            return PING2;
            case 0:
            default:
            return PING;
        }
 
    }
    
    public static Volume volume = Volume.LOW;

    // Each sound effect has its own clip, loaded with its own sound file.
    private Clip clip;

    // Constructor to construct each element of the enum with its own sound file.
    SoundEffect(String soundFileName) {

        try {
            // Use URL (instead of File) to read from disk and JAR.
            URL url = this.getClass().getClassLoader().getResource(soundFileName);
            // Set up an audio input stream piped from the sound file.
            AudioInputStream audioInputStream = null;
            if(null == url)
            {
                File f = new File(soundFileName);
                audioInputStream = AudioSystem.getAudioInputStream(f); 
            }
            else
            {
                audioInputStream = AudioSystem.getAudioInputStream(url); 
            }
            // Get a clip resource.
            clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioInputStream);

        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    // Play or Re-play the sound e/ffect from the beginning, by rewinding.
    public void play() {
        stop();
        if (volume != Volume.MUTE) {
            //if (clip.isRunning())
            //    clip.stop();   // Stop the player if it is still running
            //clip.setFramePosition(0);
            clip.start();     // Start playing
        }
    }

    public void pause()
    {
        if (clip.isRunning())
            clip.stop();
    }
    
    public void stop()
    {
        if (clip.isRunning())
            clip.stop();

        clip.setFramePosition(0);
    }

    public void playIfNotPlaying()
    {
        if (volume != Volume.MUTE) {
            if (clip.isRunning())
                return;   // Stop the player if it is still running
            clip.setFramePosition(0); // rewind to the beginning
            clip.start();     // Start playing
        }
    }

    // Optional static method to pre-load all the sound files.
    static void init() {
        values(); // calls the constructor for all the elements
    }
}