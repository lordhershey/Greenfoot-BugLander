class v2
{
    public double x;
    public double y;

    public v2()
    {
        x = 0.0;
        y = 0.0;
    }

    public v2(double x,double y)
    {
        this.x = x;
        this.y = y;
    }

    public v2(int x,int y)
    {
        this.x = (double)x;
        this.y = (double)y;
    }

    public v2 Add(v2 b)
    {
        v2 a = new v2(this.x + b.x,this.y + b.y);
        return a;
    }

    public v2 Scale(double b)
    {
        v2 a = new v2(this.x * b,this.y * b);
        return a;
    }

    public v2 Scale(int b)
    {
        v2 a = new v2(this.x * (double)b,this.y * (double)b);
        return a;
    }

    public double Inner(v2 b)
    {
        double a = this.x * b.x + this.y * b.y;
        return(a);
    }

}