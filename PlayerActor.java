import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.lang.*;
import java.util.*;
import javax.sound.sampled.*;
import java.net.URL;
import java.io.File;
import java.io.IOException;

/**********************************************************************
 * 
 * Main Class
 */

public class PlayerActor extends PointUpActor
{

    public static int setting = 0;

    public static enum State {
        FLYING,
        DYING,FALLING,NAN}

    State playerState = State.NAN;

    int LeftWingOffsetX = -5;
    int LeftWingOffsetY = -5;

    int RightWingOffsetX = 7;
    int RightWingOffsetY = -5;

    LeftWingActor leftw = null;
    RightWingActor rightw = null;

    long startTime = 0;

    int counter = 0;

    public static GreenfootImage cracking[] = new GreenfootImage[10];
    public static GreenfootImage body = null;

    public boolean UseDarkerBody = false;

    Platform landedon = null;

    v2 pos = new v2();

    v2 velocity = new v2();

    v2 gravityv = new v2(0.0,700);

    v2 rightv = new v2(-280.0,-450.0);
    v2 leftv = new v2(280.0,-450.0);

    public PlayerActor()
    {

        body = new GreenfootImage("bubble.png");

        for(int i = 0; i < cracking.length ; i++)
        {
            cracking[i] = new GreenfootImage("bubble-crack" + (i+1) + ".png");
        }

        playerState = State.NAN;
        velocity = new v2(0.0,0.0);
        UseDarkerBody = false;
        //breakspeed = 20;
    }

    public void ToggleDarkerBody()
    {
        UseDarkerBody = !UseDarkerBody;
        if(UseDarkerBody)
        {
            body = new GreenfootImage("bubble-2.png");
        }
        else
        {
            body = new GreenfootImage("bubble.png");
        }
        setImage(body);
    }

    private boolean ShouldBreakOnLanding()
    {

        double velocitySq = velocity.Inner(velocity);
        double velocityYSq = velocity.y * velocity.y;

        if((velocityYSq < 300) || (safetycounter > 0))
        {
            //sliding is not breaking
            return(false);
        }

        switch (setting)
        {

            case 5:
            //Not Fair Setting
            if(velocityYSq > 5100 || velocitySq > 8500)
            {
                return (true);
            }
            break;

            case 4:
            //Hard Setting
            if(velocityYSq > 11000 || velocitySq > 170000)
            {
                return (true);
            }
            break;

            case 3:
            //Notmal setting
            if(velocityYSq > 26000 || velocitySq > 40000)
            {
                return (true);
            }
            break;

            case 2:
            //Easy Setting
            if(velocityYSq > 66000)
            {
                return(true);
            }
            break;

            case 1:
            default:
            //practice mode never breaks;
            break;
        }

        return(false);
    }

    int safetycounter = 10;

    int pingcount = 0;

    int taps = 0;

    public long timeStart = -1;
    public long timeEnd = -1;
    public int millsPassed = 0;
    public double timeDelta = 0.0;

    int pingIndex = 0;

    public void hitWithRock()
    {
        if(playerState == State.FLYING)
        {
            playerState = State.FALLING;
            leftw.connected = false;
            rightw.connected = false;
            taps = 0;
            pingIndex = -1;

        }
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

        boolean thrustL = leftw.flapping;
        boolean thrustR = rightw.flapping;

        //Copy Global Time Delta
        //double timeDelta = Background.timeDelta;

        if(timeDelta > 0.066)
        {
            timeDelta = 0.066;
        }
        
        v2 Acceleration = gravityv;

        long endTime = System.currentTimeMillis();

        if(playerState == State.DYING)
        {
            safetycounter = 10;
            if(counter < 1)
            {
                leftw.connected = false;
                rightw.connected = false;
                startTime = 0;
                //SoundManager.playBreak();
                //SoundEffect.PING.stop();
                //SoundEffect.PING2.stop();
                SoundEffect.BUZZ.stop();
            }

            if(counter == 1)
            {
                //SoundManager.playBreak();
                SoundEffect.BREAK.playIfNotPlaying();
            }

            if((endTime - startTime) > 100)
            {
                counter++;
                startTime = endTime;
            }

            int index = counter;
            if(index >= cracking.length)
                index = cracking.length-1;

            setImage(cracking[index]);

            if(counter > 30)
            {
                playerState = State.NAN;
                InfoLayer.landings = 0;
                InfoLayer.crashes++;
                //leftw.connected = true;
                //rightw.connected = true;

            }
            return;
        }

        if(playerState == State.NAN)
        {
            velocity = new v2(0.0,0.0);
            playerState = State.FLYING;
            setImage(body);
            if(leftw == null)
                leftw = new LeftWingActor(this);
            if(rightw == null)
                rightw = new RightWingActor(this);

            leftw.connected = true;
            rightw.connected = true;

            safetycounter = 12;

            return;
        }

        boolean flapping = false;

        if(playerState == State.FLYING)
        {

            if(safetycounter > 0)
            {
                safetycounter--;
                return;
            }

            if(thrustL)
            {
                Acceleration = Acceleration.Add(leftv);
                flapping = true;
            }

            if(thrustR)
            {
                Acceleration = Acceleration.Add(rightv);
                flapping = true;
            }

        }
        if(flapping)
        {
            SoundEffect.BUZZ.playIfNotPlaying();
        }
        else
        {
            SoundEffect.BUZZ.stop();
        }

        //System.out.println(timeDelta);
        /*
        if(timeDelta > 0.09)
        {
        timeDelta = 0.09;
        }
         */

        velocity = velocity.Add(Acceleration.Scale(timeDelta));

        //pos = pos.Add(velocity.Scale(timeDelta));
        v2 PlayerDelta = velocity.Scale(timeDelta).Add(Acceleration.Scale(timeDelta * timeDelta * 0.5f));
        //pos = pos.Add(PlayerDelta);

        //Faking Drag
        v2 minusvelocity = new v2(-velocity.x,-velocity.y);
        velocity = velocity.Add(minusvelocity.Scale(0.7 * timeDelta));

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

        for(Platform p: Platforms)
        {
            if(p instanceof Ground)
            {
                continue;
            }

            /*All ground pieces here - we take X + Y radius of 
            platform and add radius
            of the flier
             */

            //Todo remove hard coded things 
            //double MinX = p.getX() - 33 - 10;
            //double MinY = p.getY() - 8 - 10;
            //double MaxX = p.getX() + 33 + 10;
            //double MaxY = p.getY() + 8 + 10;

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
                if(velocity.Inner(velocity) > 2000)
                {
                    System.out.println("Velocity Squared " + velocity.Inner(velocity));
                }

                if((playerState == State.FLYING) && ShouldBreakOnLanding())
                {
                    /*
                    counter = 0;
                    playerState = State.DYING;
                    setLocation((int)pos.x,(int)pos.y);
                    return;
                     */
                    playerState = State.FALLING;
                    leftw.connected = false;
                    rightw.connected = false;
                    taps = 0;
                    pingIndex = -1;
                }

                //credit the landing even if it is a crash
                if(/*PlayerDelta.Inner(PlayerDelta) < 16.0f*/ velocity.Inner(velocity) < 2000)
                {
                    if((null != contact) && (contact.isActive()))
                    {
                        contact.landedOnMe();
                        world.activateNewPlatform();
                        if(playerState == State.FLYING)
                        {
                            SoundEffect.BELL.play();
                            InfoLayer.landings++;
                        }
                        else
                        {
                            SoundEffect.FAIL.play();
                        }
                    }

                }
                else
                {
                    minusvelocity = new v2(-velocity.x,-velocity.y);
                    velocity = velocity.Add(minusvelocity.Scale(0.9 * timeDelta));
                }

                if(playerState == State.FALLING)
                {
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
                        counter = 0;
                        //SoundEffect.PING.stop();
                        //SoundEffect.PING2.stop();
                        playerState = State.DYING;
                        setLocation((int)pos.x,(int)pos.y);
                        return;
                    }

                    if((taps > 3) || ((velocity.y * velocity.y) < 9000))
                    {
                        velocity.x = 0;
                        velocity.y = 0;
                    }

                }     

                //No Adjustment - take 2 wings to get off of surface
                //if((/*(PlayerDelta.y * PlayerDelta.y) < 5*/ ((velocity.y * velocity.y)) < 100) && (thrustL || thrustR))
                //{

                //    velocity.y -= 10;
                //}

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

        if(side != 3) 
        {
            pingcount = 0;
        }
        /*
        v2 minusvelocity = new v2(-velocity.x,-velocity.y);
        velocity = velocity.Add(minusvelocity.Scale(0.80 * 
        timeDelta));
         */

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
    } 

    @Override
    public void addedToWorld(World world) {
        super.addedToWorld(world);
        leftw = new LeftWingActor(this);
        rightw = new RightWingActor(this);
        world.addObject(leftw,getX() + LeftWingOffsetX,getY() + LeftWingOffsetY);
        world.addObject(rightw,getX() + RightWingOffsetX,getY() + RightWingOffsetY);
        leftw.offx = LeftWingOffsetX;
        leftw.offy = LeftWingOffsetY;
        rightw.offx = RightWingOffsetX;
        rightw.offy = RightWingOffsetY;
        pos = new v2((double)getX(),(double)getY());
        velocity = new v2(0.0,0.0);
    }
}
