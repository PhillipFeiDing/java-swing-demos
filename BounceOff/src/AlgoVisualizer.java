import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AlgoVisualizer
{
    private Circle[] circles;
    private AlgoFrame frame;
    private boolean isAnimated = false;

    public AlgoVisualizer(int sceneWidth, int sceneHeight, int N, int R, int maximumSpeed, int numberOfColors)
    {
        // initialize data
        int maximumN = (sceneWidth / (2 * R)) * (sceneHeight / (2 * R));
        if (N > maximumN) {N = maximumN;}
        circles = new Circle [N];

        for (int i = 0; i < N; i ++)
        {

            double x = (Math.random() * (sceneWidth - 2 * R)) + R;
            double y = (Math.random() * (sceneHeight - 2 * R)) + R;

            double vx = (Math.random() * (maximumSpeed * 2 + 1)) - maximumSpeed;
            double vy = (Math.random() * (maximumSpeed * 2 + 1)) - maximumSpeed;

            Color color = Circle.colorGenerator(i, numberOfColors);

            circles[i] = new Circle(x, y, R, vx, vy, color);
        }

        // initialize the window
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("-Catch That Ball-", sceneWidth, sceneHeight);
            frame.addKeyListener(new AlgoKeyListener());
            frame.addMouseListener(new AlgoMouseListener());
            new Thread(() -> {
                run();
            }).start();
        });
    }

    // the logic of animation
    private void run()
    {
        while (true)
        {
            // draw curent status
            frame.render(circles);
            AlgoVisHelper.pause(20);

            // renew status
            if (isAnimated)
                for (Circle circle : circles)
                    circle.move(0, 0, frame.getCanvasWidth(), frame.getCanvasHeight());
        }
    }

    private class AlgoKeyListener extends KeyAdapter
    {
        @Override
        public void keyReleased(KeyEvent event)
        {
            if (event.getKeyChar() == ' ')
                isAnimated = !isAnimated;
        }
    }

    private class AlgoMouseListener extends MouseAdapter
    {
        @Override
        public void mousePressed(MouseEvent event)
        {
            event.translatePoint(0,
                    -(frame.getBounds().height - frame.getCanvasHeight()));
            // System.out.println(event.getPoint());
            for (Circle circle: circles)
                if (circle.contain(event.getPoint()))
                    circle.isFilled = !circle.isFilled;
        }
    }

    public static void main(String[] args)
    {
        int sceneWidth = 1440;
        int sceneHeight = 800;
        int N = 20; // number of particles
        int R = 50; // diameter of particles
        int maximumSpeed = 5; // note that it is a component speed
        int numberOfColors = 10;

        AlgoVisualizer visualizer = new AlgoVisualizer(sceneWidth, sceneHeight, N, R, maximumSpeed, numberOfColors);
    }
}
