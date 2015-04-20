import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.image.*;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.lang.Math;

public class FireBall extends Actor
{
    static GreenfootImage[] images=null;

    int imageIndex = 7;

    static{
        images = new GreenfootImage[8];
        images[0] = new GreenfootImage("ball.png");

        BufferedImage master = images[0].getAwtImage();
        int rad = 15;
        for(int i = 1; i < 8; i++)
        {
            rad -= 1;
            images[i] = new GreenfootImage(rad,rad);
            BufferedImage bi = images[i].getAwtImage();
            Graphics2D g = (Graphics2D)bi.getGraphics();          
            g.drawImage(master,0,0,rad,rad,null);
        }
    }

    public static enum State {FLYING,
        DYING,NAN}

    State playerState = State.NAN;

    Platform landedon = null;
    v2 pos = new v2();
    v2 velocity = new v2();
    v2 gravityv = new v2(0.0,100);

    int picindex = 0;

    long picStartTime = System.currentTimeMillis();

    public long timeStart = -1;
    public long timeEnd = -1;
    public int millsPassed = 0;
    public double timeDelta = 0.0;

    int pingIndex = 0;

    public FireBall()
    {

    }

    public void act()
    {
        if(timeStart < 0)
        {
            timeStart = System.currentTimeMillis();
        }

        imageIndex = (int)(-velocity.y / 50);
        if (imageIndex > 7)
        {
            imageIndex = 7;
        }
        
        if(imageIndex < 0)
        {
            imageIndex = 0;
        }
        //System.out.println("Image Index " + imageIndex);
        timeEnd = System.currentTimeMillis();

        millsPassed = (int)(timeEnd - timeStart);
        timeDelta = (double)millsPassed/1000.0;

        if(timeDelta > 0.066)
        {
            timeDelta = 0.066;
        }
        
        timeStart = System.currentTimeMillis();

        v2 Acceleration = gravityv;
        long endTime = System.currentTimeMillis();

        long picStTime = System.currentTimeMillis();

        velocity = velocity.Add(Acceleration.Scale(timeDelta));

        v2 PlayerDelta = velocity.Scale(timeDelta).Add(Acceleration.Scale(timeDelta * timeDelta * 0.5f));

        //Rocks have less drag
        v2 minusvelocity = new v2(-velocity.x,-velocity.y);
        velocity = velocity.Add(minusvelocity.Scale(0.95 * timeDelta));

        pos = pos.Add(PlayerDelta.Scale(1.0f));

        setImage(images[imageIndex]);
        
        //put smoke in old location
        Smoke smoke = new Smoke();
        getWorld().addObject(smoke,getX(),getY());
        smoke = new Smoke();
        getWorld().addObject(smoke,getX() + Greenfoot.getRandomNumber(15) - 7,getY() + Greenfoot.getRandomNumber(15) - 7);
        
        setLocation((int)pos.x,(int)pos.y);

        if(velocity.y > 30)
        {
            int x = getX();
            int y = getY();
            LavaRock lr = new LavaRock();
            getWorld().addObject(lr,x,y);
            getWorld().removeObject(this);
            lr.velocity = velocity;
            return;
        }
    }

    @Override
    public void addedToWorld(World world) {
        super.addedToWorld(world);
        pos = new v2((double)getX(),(double)getY());
        velocity = new v2((double)(Greenfoot.getRandomNumber(51)-25) * 13.f,-480.f);
    }
}
