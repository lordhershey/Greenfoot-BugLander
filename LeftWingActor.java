import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class LeftWingActor here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LeftWingActor extends Actor
{
    PlayerActor parent = null;
    public int offx;
    public int offy;
    public boolean connected = true;
    int counter = 0;
    public boolean flapping = false;
    
    int rotation = 0;
    
    public LeftWingActor(PlayerActor parent)
    {
        this.parent = parent;
        connected = true;
        flapping = false;
    }
    /**
     * Act - do whatever the LeftWingActor wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        // Add your action code here.
        counter = (counter + 1) % 5;
        if(connected)
        {
            rotation = 0;
            setLocation(parent.getX() + offx,parent.getY() + offy);
        
            if(Greenfoot.isKeyDown("a"))
            {
                flapping = true;
            }
            else
            {
                counter = 0;
                flapping = false;
            }
        }
        else
        {
            //flutter away
            move(1);
            rotation += Greenfoot.getRandomNumber(31) - 15;
        }
        
        setRotation(-counter * 15 + rotation);
    }    
}
