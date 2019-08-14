import java.awt.*;

public class Circle
{
    public double x, y;
    private int r;
    public double vx, vy;
    private Color color;
    public boolean isFilled = false;

    public Circle(double x, double y, int r, double vx, double vy, Color color)
    {
        this.x = x;
        this.y = y;
        this.r = r;
        this.vx = vx;
        this.vy = vy;
        this.color = color;
    }
    public int getR() {return r;}
    public Color getColor() {return color;}

    public void move(int minx, int miny, int maxx, int maxy)
    {
        x = x + vx;
        y = y + vy;
        checkCollision(minx, miny, maxx, maxy);
    }

    private void checkCollision(int minx, int miny, int maxx, int maxy)
    {
        if (x - r < minx)  {x = r;        vx = -vx;}
        if (x + r >= maxx) {x = maxx - r; vx = -vx;}
        if (y - r < miny)  {y = r;        vy = -vy;}
        if (y + r >= maxy) {y = maxy - r; vy = -vy;}
    }

    public boolean contain(Point p)
    {
        return (x - p.x) * (x - p.x) + (y - p.y) * (y - p.y) <= r * r;
    }

    public boolean overlap(Circle anotherCircle)
    {
        double distanceBetweenSquared = (x - anotherCircle.x) * (x - anotherCircle.x)
                                        + (y - anotherCircle.y) * (y - anotherCircle.y);
        int sumOfRadiiSquared = (r + anotherCircle.getR()) * (r + anotherCircle.getR());
        return distanceBetweenSquared <= sumOfRadiiSquared;
    }

    public static Color colorGenerator(int i, int numberOfColors)
    {
        int colorType = i % numberOfColors;
        Color color = Color.BLACK;
        switch (colorType)
        {
            case 0: color = Color.BLACK; break;
            case 1: color = Color.RED; break;
            case 2: color = Color.BLUE; break;
            case 3: color = Color.GREEN; break;
            case 4: color = Color.YELLOW; break;
            case 5: color = Color.GRAY; break;
            case 6: color = Color.MAGENTA; break;
            case 7: color = Color.DARK_GRAY; break;
            case 8: color = Color.PINK; break;
            case 9: color = Color.CYAN; break;
            default: break;
        }
        return color;
    }
}
