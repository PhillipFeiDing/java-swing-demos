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
            AlgoVisHelper.setStrokeWidth(g2d, 1);
            // set background
            AlgoVisHelper.setBackGround(g2d, 0, 0, canvasWidth, canvasHeight);

            // show Track;
            if (Circle.showTrack)
            {
                // int boundary = AlgoVisualizer.round; if (boundary >= Circle.precesion) {boundary = Circle.precesion - 1;}
                for (Circle circle: circles)
                {
                    if (circle.stillExists) {
                        AlgoVisHelper.setColor(g2d, circle.getColor());
                        for (int i = 0; i < AlgoVisualizer.round - 1; i+= Circle.precesion) {
                            AlgoVisHelper.showTrack(g2d, circle.track[i], circle.track[i + 1]);
                        }
                    }
                }
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
