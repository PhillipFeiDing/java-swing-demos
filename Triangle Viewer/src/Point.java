public class Point
{
    private double x;
    private double y;

    public Point(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public double X() {return x;}
    public double Y() {return y;}

    public double distance(Point p)
    {
        return Math.sqrt(Math.pow((x - p.X()),2) + Math.pow((y - p.Y()), 2));
    }
}