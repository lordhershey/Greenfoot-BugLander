import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Background here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Background extends World
{

    /**
     * Constructor for objects of class Background.
     * 
     */

    public static long startTime = System.currentTimeMillis();
    public static long endTime = System.currentTimeMillis();
    public static int millsPassed = 0;
    public static double timeDelta = 0.0;

    public Platform platform[] = new Platform[6];
    int platformNum = -1;

    //Default to practice
    public int setting = 0;

    public Background()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(640, 480, 1,false); 
        setPaintOrder(PlayerActor.class,LeftWingActor.class,RightWingActor.class,InfoLayer.class,Platform.class);

        setActOrder(PlayerActor.class,LeftWingActor.class,RightWingActor.class);
        prepare();
        setting = 0;
        PlayerActor.setting = setting;

    }

    public Background(boolean doPrepare)
    {
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(640, 480, 1,false); 
        if(doPrepare)
        {
            setPaintOrder(PlayerActor.class,LeftWingActor.class,RightWingActor.class,InfoLayer.class,Platform.class);
            prepare();
        }
        setActOrder(PlayerActor.class,LeftWingActor.class,RightWingActor.class);
        setting = 0;
        PlayerActor.setting = setting;
    }

    public Background(int setting)
    {
        this();

        this.setting = setting;
        PlayerActor.setting = setting;

        System.out.println("Difficulty Setting set to " + setting);
    }

    public void activateNewPlatform()
    {
        int newNum = platformNum;
        while(newNum == platformNum)
        {
            newNum = Greenfoot.getRandomNumber(6);
        }
        platformNum = newNum;
        platform[platformNum].activate();
    }

    /**
     * Prepare the world for the start of the program. That is: create the initial
     * objects and add them to the world.
     */
    private void prepare()
    {
        /*
        PlayerActor playeractor = new PlayerActor();
        addObject(playeractor, 277, 220);
        RightWingActor rightwingactor = new RightWingActor();
        addObject(rightwingactor, 289, 223);
        LeftWingActor leftwingactor = new LeftWingActor();
        addObject(leftwingactor, 271, 219);
        leftwingactor.setLocation(272, 215);
        rightwingactor.setLocation(282, 215);
         */
        platform[0] = new Platform();
        addObject(platform[0], 140, 269);

        platform[1] = new Platform();
        addObject(platform[1], 467, 238);

        platform[2] = new Platform();
        addObject(platform[2], 336, 379);

        platform[3] = new Platform();
        addObject(platform[3], 156, 405);

        platform[4] = new Platform();
        addObject(platform[4], 521, 354);

        platform[5] = new Platform();
        addObject(platform[5], 289, 205);

        platformNum = Greenfoot.getRandomNumber(5);
        platform[platformNum].activate();

        Cloud cloud = new Cloud();
        addObject(cloud, 125, 101);

        Cloud cloud2 = new Cloud();
        addObject(cloud2, 521, 194);

        Cloud cloud3 = new Cloud();
        addObject(cloud3, 205, 275);

        PlayerActor playeractor = new PlayerActor();
        addObject(playeractor, 295, 185);

        Ground ground = new Ground();
        addObject(ground, 320, 489);

        startTime = System.currentTimeMillis();
        endTime = System.currentTimeMillis();

        InfoLayer infolayer = new InfoLayer();
        addObject(infolayer, 320, 240);
    }

    public void act()
    {
        //pull this into the player class
        startTime = endTime;
        endTime = System.currentTimeMillis();
        millsPassed = (int)(endTime - startTime);
        timeDelta = (double)millsPassed/1000.0;
    }

    public void started()
    {
        System.out.println("Started");
        startTime = System.currentTimeMillis();
        endTime = System.currentTimeMillis();
    }

    public void stopped()
    {
        System.out.println("Stopped");
    }
}
