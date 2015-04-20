/**
 * Write a description of class MiscUtil here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MiscUtil  
{
    
    public static double Maximum(double a, double b)
    {
        if(a > b)
        {
            return a;
        }

        return b;
    }
    
    
    public static boolean TestWall(double WallX, 
    double RelX, 
    double RelY, 
    double PlayerDeltaX, 
    double PlayerDeltaY, 
    wrappedDouble tMin, 
    double MinY, 
    double MaxY)
    {
        boolean Hit = false;

        double tEpsilon = 0.001f;
        if(PlayerDeltaX != 0.0f)
        {
            double tResult = (WallX - RelX)/PlayerDeltaX;
            double Y = RelY + tResult*PlayerDeltaY;
            if((tResult >= 0.0f) && (tMin.val > tResult))
            {
                if((Y >= MinY) && (Y <= MaxY))
                {
                    tMin.val = Maximum(0.0f,tResult - tEpsilon);
                    Hit = true;
                }
            }
        }
        return(Hit);
    }

}
