import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.image.*;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.lang.Math;

/**
 * Write a description of class Smoke here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Smoke extends Actor
{
    static GreenfootImage image[];
    public static int spawnIdx = -1;

    int index=0;
    int coloridx = 0;

    static
    {
        image = new GreenfootImage[9];

        image [0] = new GreenfootImage("smoke0.png");

        int rad = 19;
        for(int i = 1 ; i < 9 ; i++)
        {
            rad -= 2;

            BufferedImage master = image[0].getAwtImage();
            image[i] = new GreenfootImage(rad,rad);
            BufferedImage bi = image[i].getAwtImage();
            Graphics2D g = (Graphics2D)bi.getGraphics();          
            g.drawImage(master,0,0,rad,rad,null);

        }
    }

    public Smoke()
    {

        setImage(image[index]);
    }

    int idxcounter = 0;

    public void act() 
    {
        if(index > 8)
        {
            getWorld().removeObject(this);
            return;
        }

        setImage(image[index]);
        idxcounter++;
        if(idxcounter > 3)
        {
            index++;
            idxcounter = 0;
        }
    }    
}
