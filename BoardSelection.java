import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Write a description of class BoardSelection here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BoardSelection extends Actor
{
       public static GreenfootImage images[];

    static{
        images = new GreenfootImage[3];
        images[0] = new GreenfootImage("DifficultyNone.png");
        images[1] = new GreenfootImage("NormalLevel.png");
        images[2] = new GreenfootImage("VolcanoLevel.png");
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
            List<BoardSelection>ds =  getWorld().getObjects(BoardSelection.class);

            for(BoardSelection d : ds)
            {
                d.setBlank();
            }

            setSelected();
        }
    }  
}
