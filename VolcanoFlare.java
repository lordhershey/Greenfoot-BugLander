import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class VolcanoFlare here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class VolcanoFlare extends Actor
{
    static GreenfootImage[] images;
    static
    {
        images = new GreenfootImage[6];

        images[0] = new GreenfootImage("flare-1.png");
        images[1] = new GreenfootImage("flare-2.png");
        images[2] = new GreenfootImage("flare-3.png");
        images[3] = new GreenfootImage("flare-4.png");
        images[4] = new GreenfootImage("flare-5.png");
        images[5] = new GreenfootImage("flare-6.png");
    }

    int index = 0;
    int step = 0;

    long startTime = System.currentTimeMillis();

    long totaldiff = 0;

    int smallpause = 20 + Greenfoot.getRandomNumber(50);
    int bigpause = 1000 + Greenfoot.getRandomNumber(2000);

    public void act() 
    {

        long endTime = System.currentTimeMillis();

        if((endTime - startTime) > smallpause)
        {            
            index = (index + 1) % 3;
            totaldiff += (endTime - startTime);
            startTime = endTime;
        }
        else
        {
            return;
        }

        if(totaldiff > bigpause)
        {
            smallpause = 20 + Greenfoot.getRandomNumber(50);
            step = (step + 1) % 3;
            totaldiff = 0;
            bigpause = 1000 + Greenfoot.getRandomNumber(2000);

            if(step < 1)
            {
                FireBall fb = new FireBall();
                getWorld().addObject(fb,getX(),getY());
            }
        }

        setImage(images[step + index]);
    }    
}
