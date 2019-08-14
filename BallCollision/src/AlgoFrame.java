import javax.swing.*;
import java.awt.*;

public class AlgoFrame extends JFrame
{
    private int canvasWidth;
    private int canvasHeight;

    public AlgoFrame(String title, int canvasWidth, int canvasHeight)
    {
        super(title);
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;

        AlgoCanvas canvas = new AlgoCanvas();
        setContentPane(canvas);
        pack();

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);
    }

    public AlgoFrame(String title)
    {
        this(title, 1024, 768);
    }
    public int getCanvasWidth() {return canvasWidth;}
    public int getCanvasHeight() {return canvasHeight;}

    private Circle[] circles;
    public void render(Circle[] circles)
    {
        this.circles = circles;
        repaint();
    }

    private class AlgoCanvas extends JPanel
    {
        public AlgoCanvas()
        {
            super(true);
        }

        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D)g;

            // Anti-Aliased
            RenderingHints hints = new RenderingHints(
                                        RenderingHints.KEY_ANTIALIASING,
                                        RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.addRenderingHints(hints);

            // Draw
            AlgoVisHelper.setStrokeWidth(g2d, 2);

            if (Circle.harmonicCoefficient != 0)
            {
                AlgoVisHelper.setColor(g2d, Color.ORANGE);
                AlgoVisHelper.fillCircle(g2d, canvasWidth / 2, canvasHeight / 2, 50);
                AlgoVisHelper.setColor(g2d, Color.BLACK);
                AlgoVisHelper.strokeCircle(g2d, canvasWidth / 2, canvasHeight / 2, 25);
            }

            for(Circle circle: circles)
                {
                    AlgoVisHelper.setColor(g2d, circle.getColor());
                    if (circle.stillExists)
                    {
                        if (!circle.isFilled)
                        AlgoVisHelper.strokeCircle(g2d, (int) circle.x, (int) circle.y, circle.getR());
                        else
                        AlgoVisHelper.fillCircle(g2d, (int) circle.x, (int) circle.y, circle.getR());
                    }
                }
        }

        @Override
        public Dimension getPreferredSize()
        {
            return new Dimension(canvasWidth, canvasHeight);
        }
    }
}
