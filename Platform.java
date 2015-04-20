import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Platform here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Platform extends Actor
{
    public static GreenfootImage image[] = new GreenfootImage[2];
    boolean active = false;
    
    public Platform()
    {
        image[0] = new GreenfootImage("Platform1.png");
        image[1] = new GreenfootImage("Platform1-active.png");
    }
    
    public void landedOnMe()
    {
        if(active)
        {
            active = false;
            setImage(image[0]);
        }
    }
    
    public void activate()
    {
        active = true;
        setImage(image[1]);
    }
    
    public boolean isActive()
    {
        return (active);
    }
    
    
    /**
     * Act - do whatever the Platform wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        // Add your action code here.
        
    }    
}
