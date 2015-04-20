import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Write a description of class LavaRock here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LavaRock extends Actor
{

    public static enum State {FLYING,
        DYING,NAN}

    public static GreenfootImage[] images;

    static{
        images = new GreenfootImage[3];
        images[0] = new GreenfootImage("LavaBall-1.png");
        images[1] = new GreenfootImage("LavaBall-2.png");
        images[2] = new GreenfootImage("LavaBall-3.png");
    }

    State playerState = State.NAN;

    Platform landedon = null;
    v2 pos = new v2();
    public v2 velocity = new v2();
    v2 gravityv = new v2(0.0,700);

    int picindex = 0;

    long picStartTime = System.currentTimeMillis();

    public long timeStart = -1;
    public long timeEnd = -1;
    public int millsPassed = 0;
    public double timeDelta = 0.0;

    int pingIndex = 0;

    public LavaRock()
    {
    }

    public void act() 
    {
        if(timeStart < 0)
        {
            timeStart = System.currentTimeMillis();
        }

        timeEnd = System.currentTimeMillis();

        millsPassed = (int)(timeEnd - timeStart);
        timeDelta = (double)millsPassed/1000.0;

        timeStart = System.currentTimeMillis();

        if(timeDelta > 0.066)
        {
            timeDelta = 0.066;
        }
        
        v2 Acceleration = gravityv;
        long endTime = System.currentTimeMillis();
        if(playerState == State.DYING)
        {
            /*
            velocity = new v2((double)(Greenfoot.getRandomNumber(101) - 50) * 20.0f,(double)(Greenfoot.getRandomNumber(50)) * 20.0f);
            playerState = State.NAN;
            */
           getWorld().removeObject(this);
            return;
        }

        if(playerState == State.NAN)
        {
            //velocity = new v2(0.0,0.0);
            playerState = State.FLYING;
            setImage(images[0]);

            return;
        }

        long picStTime = System.currentTimeMillis();

        velocity = velocity.Add(Acceleration.Scale(timeDelta));

        v2 PlayerDelta = velocity.Scale(timeDelta).Add(Acceleration.Scale(timeDelta * timeDelta * 0.5f));

        //Rocks have less drag
        v2 minusvelocity = new v2(-velocity.x,-velocity.y);
        velocity = velocity.Add(minusvelocity.Scale(0.4 * timeDelta));

        /**********************************************
         * Platform Colissions
         */

        wrappedDouble tMin = new wrappedDouble(1.0f);
        /*
        Get all objects for now - maybe cut down on them if
        there are lots
         */
        List<Platform> Platforms = getWorld().getObjects
            (Platform.class);
        v2 WallNormal = new v2();

        boolean hit = false;
        int side = -1;
        //v2 PlayerDelta = velocity.Scale(timeDelta);
        Platform contact = null;

        v2 Rel = pos;

        long picEndTime = System.currentTimeMillis();

        if((picEndTime - picStartTime) > 199) 
        {
            picStartTime = System.currentTimeMillis();
            picindex = Greenfoot.getRandomNumber(3);
            setImage(images[picindex]);
        }

        int pingcount = 0;

        int taps = 0;

        boolean MakeSparks = false;
        boolean RemoveMe = false;
        
        for(Platform p: Platforms)
        {
            if(p instanceof Ground)
            {
                continue;
            }

            /*Base Box Size for platform 34x 9y each way and 10x 
            and 10y for ball*/
            v2 MinCorner = new v2(p.getX() - 30 - 10, p.getY() - 9 
                    - 10);
            v2 MaxCorner = new v2(p.getX() + 30 + 10, p.getY() + 9 
                    + 10);

            if(MiscUtil.TestWall(MaxCorner.x, Rel.x,  Rel.y,  
                PlayerDelta.x,  PlayerDelta.y,  tMin,  MinCorner.y,  MaxCorner.y))
            {
                WallNormal = new v2(1.0f,0.0f);
                hit = true;
                side = 0;//right
                contact = p;
            }
            if(MiscUtil.TestWall(MinCorner.x, Rel.x,  Rel.y,  
                PlayerDelta.x,  PlayerDelta.y,  tMin,  MinCorner.y,  MaxCorner.y))
            {
                WallNormal = new v2(-1.0f,0.0f);
                hit = true;
                side = 1;//left
                contact = p;
            }
            if(MiscUtil.TestWall(MaxCorner.y, Rel.y,  Rel.x,  
                PlayerDelta.y,  PlayerDelta.x,  tMin,  MinCorner.x,  MaxCorner.x))
            {
                WallNormal = new v2(0.0f,1.0f);
                hit = true;
                side = 2;//bottom
                contact = p;
            }
            if(MiscUtil.TestWall(MinCorner.y, Rel.y,  Rel.x,  
                PlayerDelta.y,  PlayerDelta.x,  tMin,  MinCorner.x,  MaxCorner.x))
            {
                WallNormal = new v2(0.0f,-1.0f);
                hit = true;
                side = 3; //top
                contact = p;
            }

        }

        /**********************************************
         * Player Colission
         */

        {
            wrappedDouble tMinPlayer = new wrappedDouble(1.0f);

            List<PlayerActor> Players = getWorld().getObjects
                (PlayerActor.class);

            boolean playerhit = false;

            PlayerActor playercontact = null;

            for(PlayerActor p: Players)
            {

                v2 MinCornerP = new v2(p.getX() - 19, p.getY() - 19);
                v2 MaxCornerP = new v2(p.getX() + 19, p.getY() + 19);

                if(MiscUtil.TestWall(MaxCornerP.x, Rel.x,  Rel.y,  
                    PlayerDelta.x,  PlayerDelta.y,  tMinPlayer,  MinCornerP.y,  MaxCornerP.y))
                {
                    //WallNormal = new v2(1.0f,0.0f);
                    playerhit = true;
                    //side = 0;//right
                    playercontact = p;

                }
                if(MiscUtil.TestWall(MinCornerP.x, Rel.x,  Rel.y,  
                    PlayerDelta.x,  PlayerDelta.y,  tMinPlayer,  MinCornerP.y,  MaxCornerP.y))
                {
                    //WallNormal = new v2(-1.0f,0.0f);
                    playerhit = true;
                    //side = 1;//left
                    playercontact = p;
                }
                if(MiscUtil.TestWall(MaxCornerP.y, Rel.y,  Rel.x,  
                    PlayerDelta.y,  PlayerDelta.x,  tMinPlayer,  MinCornerP.x,  MaxCornerP.x))
                {
                    //WallNormal = new v2(0.0f,1.0f);
                    playerhit = true;
                    //side = 2;//bottom
                    playercontact = p;
                }
                if(MiscUtil.TestWall(MinCornerP.y, Rel.y,  Rel.x,  
                    PlayerDelta.y,  PlayerDelta.x,  tMinPlayer,  MinCornerP.x,  MaxCornerP.x))
                {
                    //WallNormal = new v2(0.0f,-1.0f);
                    playerhit = true;
                    //side = 3; //top
                    playercontact = p;
                }

            }

            if(playerhit)
            {
                if(hit)
                {
                    if(tMinPlayer.val < tMin.val)
                    {
                        //System.out.println("Player Smashed Before Platform Hit");
                        playercontact.hitWithRock();
                    }
                }
                else
                {
                    //System.out.println("Player Smashed");
                    playercontact.hitWithRock();
                    RemoveMe = true;
                }
            }
        }
        //Ground Test
        if(MiscUtil.TestWall(461.0, Rel.y,  Rel.x,  
            PlayerDelta.y,  PlayerDelta.x,  tMin,  -300,  900))
        {
            WallNormal = new v2(0.0f,-1.0f);
            hit = true;
            side = 3;//bottom
            contact = null;
        }

        pos = pos.Add(PlayerDelta.Scale(tMin.val));

        if(hit)
        {
            switch(side)
            {
                case 3:
                velocity.y = -velocity.y;
                /*top landing*/
                /*
                if(PlayerDelta.Inner(PlayerDelta) > 4)
                {
                System.out.println("Delta Squared " + PlayerDelta.Inner(PlayerDelta));
                }
                 */
                /*
                if(velocity.Inner(velocity) > 2000)
                {
                System.out.println("Velocity Squared " + velocity.Inner(velocity));
                }
                 */

                /*
                minusvelocity = new v2(-velocity.x,-velocity.y);
                velocity = velocity.Add(minusvelocity.Scale(0.9 * timeDelta));
                 */

                //SoundManager.playPing();
                pingcount++;
                int tmpnextIndex = (pingIndex + 1) % 3;
                if(pingcount == 1 && ((velocity.y * velocity.y) > 14000))
                {
                    pingIndex = (pingIndex + 1) % 3;
                    if (taps < 4)
                    {
                        SoundEffect.getPing(pingIndex).play();
                    }
                    taps++;
                }
                else
                {
                    SoundEffect.getPing(tmpnextIndex).stop();
                }

                if(/*velocity.Inner(velocity) < 2000*/  (velocity.y * velocity.y) < 2000 )
                {
                    //counter = 0;
                    //SoundEffect.PING.stop();
                    //SoundEffect.PING2.stop();
                    playerState = State.DYING;
                    setLocation((int)pos.x,(int)pos.y);
                    return;
                }

                if(/*velocity.Inner(velocity) < 2000*/  (velocity.y * velocity.y) > 8000 )
                {
                    MakeSparks = true;
                }
                if((taps > 3) || ((velocity.y * velocity.y) < 9000))
                {
                    velocity.x = 0;
                    velocity.y = 0;
                }

                
                break;
                case 2:
                velocity.y = -velocity.y;
                minusvelocity = new v2(-velocity.x,-velocity.y);
                velocity = velocity.Add(minusvelocity.Scale(0.9 * timeDelta));  
                break;
                default:
                velocity.x = -velocity.x;
                break;
            }

        }

        /**********************************************
         * Platform Colissions
         */

        /*
        if(pos.y > 460.0)
        {
        velocity.y = -velocity.y;
        pos.y = 460.0;
        minusvelocity = new v2(-velocity.x,-velocity.y);
        velocity = velocity.Add(minusvelocity.Scale(0.9 * timeDelta));
        }
         */

        if(pos.x < 10.0)
        {
            velocity.x = -velocity.x;
            pos.x = 10.0;
        }

        //todo remove this - ground is a platform
        if(pos.x > 630.0)
        {
            velocity.x = -velocity.x;
            pos.x = 630.0;

        }

        setLocation((int)pos.x,(int)pos.y);

        if(MakeSparks)
        {
            for(int i = 0 ; i < 3; i++)
            {
                SparksEffect se = new SparksEffect();
                getWorld().addObject(se,getX(),getY()+10);
                se.speed = 1 + Greenfoot.getRandomNumber(5);
                se.setRotation(Greenfoot.getRandomNumber(180) + 180);
            }
        }

        if(RemoveMe)
        {
            
            for(int i = 0 ; i < 13; i++)
            {
                SparksEffect se = new SparksEffect();
                getWorld().addObject(se,getX(),getY());
                se.speed = 1 + Greenfoot.getRandomNumber(5);
                se.setRotation(Greenfoot.getRandomNumber(360));
            }
            SoundEffect.BREAK.playIfNotPlaying();
            getWorld().removeObject(this);
            return;
        }
        
    }    

    @Override
    public void addedToWorld(World world) {
        super.addedToWorld(world);
        //leftw = new LeftWingActor(this);
        //rightw = new RightWingActor(this);
        //world.addObject(leftw,getX() + LeftWingOffsetX,getY() + LeftWingOffsetY);
        //world.addObject(rightw,getX() + RightWingOffsetX,getY() + RightWingOffsetY);
        //leftw.offx = LeftWingOffsetX;
        //leftw.offy = LeftWingOffsetY;
        //rightw.offx = RightWingOffsetX;
        //rightw.offy = RightWingOffsetY;
        pos = new v2((double)getX(),(double)getY());
        velocity = new v2(0.0,0.0);
    }
}
