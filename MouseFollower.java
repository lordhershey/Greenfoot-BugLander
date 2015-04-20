import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MouseFollower here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MouseFollower extends Actor
{
    static GreenfootImage blank = new GreenfootImage(1,1);
    
    public MouseFollower()
    {
        setImage(blank);
    }
    
    public void act() 
    {
        MouseInfo mi = Greenfoot.getMouseInfo();
        if(mi != null)
        {
            setLocation(mi.getX(),mi.getY());
        }
    }    
}
