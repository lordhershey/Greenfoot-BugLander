import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Write a description of class DifficultySelection here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DifficultySelection extends Actor
{
    public static GreenfootImage images[];

    static{
        images = new GreenfootImage[6];
        images[0] = new GreenfootImage("DifficultyNone.png");
        images[1] = new GreenfootImage("Difficulty0.png");
        images[2] = new GreenfootImage("Difficulty1.png");
        images[3] = new GreenfootImage("Difficulty2.png");
        images[4] = new GreenfootImage("Difficulty3.png");
        images[5] = new GreenfootImage("Difficulty4.png");
    
    }

    public int selection = 0;
    public boolean active=false;

    public void set(int i)
    {
        setImage(images[i]);
    }

    public void setBlank()
    {
        set(0);
        active = false;
        setRotation(0);
    }

    public void setSelected()
    {
        active = true;
        set(selection);
        if(selection > 0)
        {
            setRotation(Greenfoot.getRandomNumber(21) - 10);
        }
    }

    public void act() 
    {

        if(active)
        {
            return;
        }
        
        if(getOneIntersectingObject(MouseFollower.class) != null)
        {
            List<DifficultySelection>ds =  getWorld().getObjects(DifficultySelection.class);

            for(DifficultySelection d : ds)
            {
                d.setBlank();
            }

            setSelected();
        }
    }
}
