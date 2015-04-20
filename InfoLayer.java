import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;
import java.awt.Font;

/**
 * Write a description of class InfoLayer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class InfoLayer extends Actor
{
    
    GreenfootImage image = null;
    
    public static int landings = 0;
    public static int bestrun = 0;
    public static int crashes = 0;
    
    Color mycolor = new Color(100,100,200);
    Color myOrange = new Color(0xFF,0x48,0x00);
    Color myGold = new Color(0xFF,0xCC,0x00);
    
    private Font myFont = new Font("TimesRoman", Font.BOLD, 17);
    
    public InfoLayer()
    {
        image = new GreenfootImage(640,480);
        image.setFont(myFont);

        //image.setColor(new Color(10,10,255));
        
        //image.fill();
        
        //image.setTransparency(15);
        
        setImage(image);
        landings = 0;
        bestrun = 0;
        crashes = 0;
    }
    /**
     * Act - do whatever the InfoLayer wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        image.clear();
        
        
        String Landings = "Landings : " + landings;
        
        if (bestrun < landings)
        {
            bestrun = landings;
        }
        
        String BestRun = "Most Landings : " + bestrun;
        
        String Crashes = "Crashes : " + crashes;
        
        image.setColor(mycolor);
        image.drawString(Landings,7,20);
        
        image.setColor(myOrange);
        image.drawString(Crashes,230,20);
        
        image.setColor(myGold);
        image.drawString(BestRun,440,20);
        
    } 
    
    
}
