import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.Stroke;

public class AlgoVisHelper
{
    private AlgoVisHelper(){}
        public static void setStrokeWidth(Graphics2D g2d, int w)
        {
            int strokeWidth = w;
            g2d.setStroke(new BasicStroke(strokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        }

        public static void strokeCircle(Graphics2D g2d, int x, int y, int r)
        {
            Ellipse2D circle = new Ellipse2D.Double(x-r, y-r, 2*r, 2*r);
            g2d.draw(circle);
        }

        public static void setColor(Graphics2D g2d, Color color)
        {
            g2d.setColor(color);
        }

        public static void fillCircle(Graphics2D g2d, int x, int y, int r)
        {
            Ellipse2D circle = new Ellipse2D.Double(x-r, y-r, 2*r, 2*r);
            g2d.fill(circle);
        }

        public static void setBackGround(Graphics2D g2d, int x, int y, int width, int height)
        {
            Rectangle2D rectangle = new Rectangle2D.Double(x, y, width, height);
            g2d.setColor(Color.BLACK);
            g2d.fill(rectangle);
        }

        public static void showTrack(Graphics2D g2d, Position pre, Position cur)
        {
            g2d.drawLine((int)pre.getX(), (int)pre.getY(), (int)cur.getX(), (int)cur.getY());
        }

        public static void pause(int t)
        {
            try
            {
                Thread.sleep(t);
            }
            catch (InterruptedException e)
            {
                System.out.println("Error in sleeping.");
            };

        }


}