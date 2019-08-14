import java.awt.*;

public class Circle
{
    public double x, y, xNew, yNew;
    private int r;
    public double vx, vy, vxNew, vyNew, vxIntermediate, vyIntermediate;
    private Color color;
    public boolean isFilled;
    public boolean canBeDestroyed;
    public boolean stillExists;
    public static boolean canCollideWithEachOther;
    public static int gravity;
    public static double bouncingCoefficient;
    public static double harmonicCoefficient;

    public Circle(double x, double y, int r, double vx, double vy, Color color, boolean isFilled, boolean canBeDestroyed)
    {
        this.x = x;
        this.y = y;
        this.r = r;
        this.vx = vx;
        this.vy = vy;
        this.color = color;
        this.isFilled = isFilled;
        this.canBeDestroyed = canBeDestroyed;
        this.stillExists= true;
    }

    public int getR() {return r;}
    public Color getColor() {return color;}

    public void move(int minx, int miny, int maxx, int maxy)
    {
        x = x + 0.1 * vx;
        y = y + 0.1 * vy;
        checkCollision(minx, miny, maxx, maxy);
        if (y - r >= miny && y + r < maxy) {vy = vy + gravity;}
        if (Circle.harmonicCoefficient != 0)
        {
            vx = vx - (Circle.harmonicCoefficient * distance(x, maxx / 2));
            vy = vy - (Circle.harmonicCoefficient * distance(y, maxy / 2));
        }
    }

    private void checkCollision(int minx, int miny, int maxx, int maxy)
    {
        if (x - r < minx && vx < 0)  { /*x = r;*/         vx = -vx * Circle.bouncingCoefficient;}
        if (x + r >= maxx && vx > 0) { /*x = maxx - r; */ vx = -vx * Circle.bouncingCoefficient;}
        if (y - r < miny && vy < 0)  { vy = -vy * Circle.bouncingCoefficient; /*y = r;*/}
        if (y + r >= maxy && vy > 0) { vy = -vy * Circle.bouncingCoefficient; /*y = maxy - r;*/}
    }

    public boolean contain(Point p)
    {
        return (x - p.x) * (x - p.x) + (y - p.y) * (y - p.y) <= r * r;
    }

    public boolean collideWith(Circle anotherCircle)
    {
        // check the velocities
        boolean collisionOccurs = false;
        double v1x = vxIntermediate; double v2x = anotherCircle.vx;
        double v1y = vyIntermediate; double v2y = anotherCircle.vy;
        if (x > anotherCircle.x) {v1x = anotherCircle.vx; v2x = vxIntermediate;}
        if (y > anotherCircle.y) {v1y = anotherCircle.vy; v2y = vyIntermediate;}
        if ((v1x - v2x > 0) || (v1y - v2y > 0)) {collisionOccurs = true;}

        // check the distance
        double distanceBetweenSquared = (x - anotherCircle.x) * (x - anotherCircle.x)
                + (y - anotherCircle.y) * (y - anotherCircle.y);
        double sumOfRadiiSquared = Math.pow(r + anotherCircle.getR(), 2);

        return (collisionOccurs) && (distanceBetweenSquared <= sumOfRadiiSquared);
    }

    public void ballBallCollision(int i, Circle[] circles) // i is indexThisBall; j is indexAnotherBall
    {
        vxNew =vx; vyNew = vy;
        vxIntermediate = vx; vyIntermediate = vy;
        xNew = x; yNew = y;

        for (int j =0; j < circles.length; j++)
        {
            if ((i != j) && (circles[i].collideWith(circles[j]))  && (circles[j].stillExists)
                    && (circles[i].stillExists))
            {
                double x2 = circles[j].x; double y2= circles[j].y; double x1 = x; double y1 = y;
                double v1x = vx; double v1y = vy; double v2x = circles[j].vx; double v2y = circles[j].vy;

                int amendment = 0;
                if ((Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)) == 0)  {amendment ++;}
                vxIntermediate = (v1x * Math.pow(y2 - y1, 2)
                                 + v2x * Math.pow(x2 - x1, 2)
                                 + (v2y - v1y) * (x2 - x1) * (y2 - y1))
                                  /
                                 ((Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)) + amendment);

                vyIntermediate = (v1y * Math.pow(x2 - x1, 2)
                                 + v2y * Math.pow(y2 - y1, 2)
                                    + (v2x - v1x) * (x2 - x1) * (y2 - y1))
                                  /
                                  ((Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2))+ amendment);
            }
        }
        vxNew = vxIntermediate;
        vyNew = vyIntermediate;
    }

    public double distance(double x, int center)
    {
        return x - center;
    }

    public static Color colorGenerator(int i, int numberOfColors)
    {
        int colorType = i % numberOfColors;
        Color color = Color.BLACK;
        switch (colorType)
        {
            case 0: color = Color.BLUE; break;
            case 1: color = Color.RED; break;
            case 2: color = Color.BLACK; break;
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
