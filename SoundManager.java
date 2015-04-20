import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class SoundManager here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SoundManager 
{

    //todo - make a message Queue?
    //Todo turn into an ENUM
    private static boolean SoundOk = false;
    private static GreenfootSound GlassPing[];
    private static GreenfootSound GlassBreak[];
    private static int pingIndex = 0;
    private static int breakIndex = 0;

    static{
        SoundOk = true;
        try{

            GlassBreak = new GreenfootSound[2];
            GlassBreak[0] = new GreenfootSound("glass-break.wav");
            GlassBreak[1] = new GreenfootSound("glass-break.wav");

            GlassPing = new GreenfootSound[5];
            GlassPing[0] = new GreenfootSound("17__anton__glass-a-pp.wav");
            GlassPing[1] = new GreenfootSound("17__anton__glass-a-pp.wav");
            GlassPing[2] = new GreenfootSound("17__anton__glass-a-pp.wav");
            GlassPing[3] = new GreenfootSound("17__anton__glass-a-pp.wav");
            GlassPing[4] = new GreenfootSound("17__anton__glass-a-pp.wav");

            GlassPing[0].play();
        }
        catch(Exception e)
        {
            SoundOk = false;
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void playPing()
    {
        if(!SoundOk)
        {
            return;
        }
        pingIndex = (pingIndex + 1) % GlassPing.length;
        int nextPing = (pingIndex + 1) % GlassPing.length;
        /*
        if(GlassPing[nextPing].isPlaying())
        {
        GlassPing[nextPing].stop();
        }
        GlassPing[pingIndex].play();
         */
        try{
            GlassPing[nextPing].stop();
        }
        catch(Exception e)
        {
        }

        try{
            GlassPing[pingIndex].play();
        }
        catch(Exception e)
        {
        }

    }

    public static void playBreak()
    {
        if(!SoundOk)
        {
            return;
        }

        breakIndex = (breakIndex + 1) % GlassBreak.length;
        int nextBreak = (pingIndex + 1) % GlassBreak.length;
        /*
        if(GlassBreak[nextBreak].isPlaying())
        {
        GlassBreak[nextBreak].stop();
        }
        GlassBreak[breakIndex].play();
         */

        try{
            GlassBreak[nextBreak].stop();
        }
        catch(Exception e)
        {
        }

        try
        {
            GlassBreak[breakIndex].play();
        }
        catch (Exception e)
        {
        }
    }
}
