import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class TitlePlaque here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TitlePlaque extends Actor
{
    /**
     * Act - do whatever the TitlePlaque wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        // Add your action code here.
        /*
        try
        {
            MouseInfo mi = Greenfoot.getMouseInfo();
            if(mi.getClickCount() > 0)
            {
                Greenfoot.setWorld(new Background());
            }
        }
        catch (Exception e)
        {
        }
        */
       if (Greenfoot.mouseClicked(null))  
       {  
           MouseInfo mouse = Greenfoot.getMouseInfo();
           Greenfoot.setWorld(new Background());
       }
    }    
}
