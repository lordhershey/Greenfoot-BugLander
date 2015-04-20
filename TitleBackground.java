import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Write a description of class TitleBackground here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TitleBackground extends World
{

    /**
     * Constructor for objects of class TitleBackground.
     * 
     */
    public TitleBackground()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(640, 480, 1); 

        prepare();
        Greenfoot.start();
    }

    @Override
    public void act()
    {
        /*
        try
        {
        MouseInfo mi = Greenfoot.getMouseInfo();
        if(mi.getClickCount() > 0)
        {
        Greenfoot.setWorld(new Background());
        }
        }
        catch (Exception e)
        {
        }
         */
        if (Greenfoot.mouseClicked(null))  
        {  
            MouseInfo mouse = Greenfoot.getMouseInfo();

            //find out which one is selected
            List<DifficultySelection>ds =  getObjects(DifficultySelection.class);

            int setting = 0;
            int board = 1;

            for(DifficultySelection d : ds)
            {
                if(d.active)
                {
                    setting = d.selection;
                }
            }

            List<BoardSelection>bs =  getObjects(BoardSelection.class);

            for(BoardSelection b : bs)
            {
                if(b.active)
                {
                    board = b.selection;
                }
            }

            switch(board)
            {
                case 2:
                Greenfoot.setWorld(new VolcanoBackground(setting));
                break;

                default:
                Greenfoot.setWorld(new Background(setting));
                break;
            }
        }
    }

    /**
     * Prepare the world for the start of the program. That is: create the initial
     * objects and add them to the world.
     */
    private void prepare()
    {

        TitlePlaque titleplaque = new TitlePlaque();
        addObject(titleplaque, 299, 199);

        int x = -20;
        int i = 1;
        int y =  /*261*/ 244;
        DifficultySelection difficultyselection = new DifficultySelection();
        addObject(difficultyselection, 72 + x, y);
        difficultyselection.selection = i;
        difficultyselection.setSelected();

        x += 50;
        i++;
        difficultyselection = new DifficultySelection();
        addObject(difficultyselection, 72 + x, y);
        difficultyselection.selection = i;

        x += 50;
        i++;
        difficultyselection = new DifficultySelection();
        addObject(difficultyselection, 72 + x, y);
        difficultyselection.selection = i;

        x += 50;
        i++;
        difficultyselection = new DifficultySelection();
        addObject(difficultyselection, 72 + x, y);
        difficultyselection.selection = i;

        x += 50;
        i++;
        difficultyselection = new DifficultySelection();
        addObject(difficultyselection, 72 + x, y);
        difficultyselection.selection = i;

        MouseFollower mousefollower = new MouseFollower();
        addObject(mousefollower, 72, 267);

        x = -20;
        i = 1;
        y = 426;

        BoardSelection boardselection = new BoardSelection();
        addObject(boardselection, 72 + x, y);
        boardselection.selection = i++; 
        boardselection.setSelected();
        x += 50;

        boardselection = new BoardSelection();
        addObject(boardselection, 72 + x, y);
        boardselection.selection = i++;
        x += 50;
    }
}
