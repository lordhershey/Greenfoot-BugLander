import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class VolcanoBackground here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class VolcanoBackground extends Background
{

    /**
     * Constructor for objects of class VolcanoBackground.
     * 
     */
    public VolcanoBackground()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(false);
        prepare();

        setPaintOrder(LavaRock.class,
            PlayerActor.class,
            LeftWingActor.class,
            RightWingActor.class,
            InfoLayer.class,
            SparksEffect.class,
            Platform.class,
            Cloud.class,
            VolcanoFront.class,
            FireBall.class,
            Smoke.class,
            VolcanoFlare.class,
            VolcanoBack.class,
            BackCloud.class);

            //Default to normal mode
        this.setting = 3;
        PlayerActor.setting = 3;
    }

    public VolcanoBackground(int setting)
    {
        this();

        this.setting = setting;
        PlayerActor.setting = setting;
        System.out.println("Difficulty Setting set to " + setting);
    }

    private void prepare()
    {

        ClearCloud cloud = new ClearCloud();
        addObject(cloud, 125, 101);

        ClearCloud cloud2 = new ClearCloud();
        addObject(cloud2, 521, 194);

        BackCloud backcloud = new BackCloud();
        addObject(backcloud, 205, 275);

        VolcanoGround ground = new VolcanoGround();
        addObject(ground, 320, 489);

        startTime = System.currentTimeMillis();
        endTime = System.currentTimeMillis();

        
        InfoLayer infolayer = new InfoLayer();
        addObject(infolayer, 320, 240);
         

        VolcanoBack volcanoback = new VolcanoBack();
        addObject(volcanoback, 320, 300);

        VolcanoFront volcanofront = new VolcanoFront();
        addObject(volcanofront, 320, 300);

        VolcanoFlare volcanoflare = new VolcanoFlare();
        addObject(volcanoflare, 316, 316);
        
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
         

        
        PlayerActor playeractor = new PlayerActor();
        playeractor.ToggleDarkerBody();
        addObject(playeractor, 295, 185);
         
/*
        Platform platform = new Platform();
        addObject(platform, 109, 133);
        platform.setLocation(143, 147);
        Platform platform2 = new Platform();
        addObject(platform2, 491, 214);
        platform2.setLocation(488, 211);
        Platform platform3 = new Platform();
        addObject(platform3, 403, 318);
        Platform platform4 = new Platform();
        addObject(platform4, 562, 393);
        Platform platform5 = new Platform();
        addObject(platform5, 228, 264);
        Platform platform6 = new Platform();
        addObject(platform6, 79, 382);
        Platform platform7 = new Platform();
        addObject(platform7, 312, 419);
        platform3.setLocation(409, 297);
        platform2.setLocation(506, 206);
        platform.setLocation(125, 147);
        platform7.setLocation(318, 377);
        platform5.setLocation(205, 261);
        platform.setLocation(108, 163);
        platform6.setLocation(57, 379);
        platform3.setLocation(420, 293);
        platform7.setLocation(316, 376);

        
        this.platform = new Platform[7];
        this.platform[0] = platform;
        this.platform[1] = platform2;
        this.platform[2] = platform3;
        this.platform[3] = platform4;
        this.platform[4] = platform5;
        this.platform[5] = platform6;
        this.platform[6] = platform7;

        int platformNum = Greenfoot.getRandomNumber(6);
        if(platformNum == 4)
        {
            platformNum++;
        }
        this.platform[platformNum].activate();
  */      
       
    }
}
