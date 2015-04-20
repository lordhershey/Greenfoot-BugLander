import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Cloud here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Cloud extends PointUpActor
{
    /**
     * Act - do whatever the Cloud wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    int speed = 1;
    long startTime = 0;
    int pauseTime = 50;
    
    public Cloud()
    {
        pauseTime = 20 + Greenfoot.getRandomNumber(50);
    }
    
    public void act() 
    {
        // Add your action code here.
        
        long endTime = System.currentTimeMillis();
        
        if(endTime - startTime < pauseTime)
        {
            return;
        }
        
        startTime = endTime;
        
        int x = getX();
        int y = getY();
        
        x += speed;
        int width = getImage().getWidth()/2;
        
        if(x >= (world.getWidth() + width + 10))
        {
            x = -(width * 3/2);
            y = 101 + Greenfoot.getRandomNumber(175);
            pauseTime = 20 + Greenfoot.getRandomNumber(50);
        }
        setLocation(x,y);
    }    
}
