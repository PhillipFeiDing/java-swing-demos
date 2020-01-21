import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AlgoVisualizer
{
    public Circle[] circles;
    private AlgoFrame frame;
    private boolean isAnimated = false;

    public AlgoVisualizer(int sceneWidth, int sceneHeight, int N, int R, int maximumSpeed, int numberOfColors, boolean isFilled,
                            boolean canBeDestroyed, boolean canCollideWithEachOther, int gravity, double bouncingCoefficient,
                           double harmonicCoefficient)
    {
        // initialize data
        int maximumN = (sceneWidth / (2 * R)) * (sceneHeight / (2 * R));
        if (N > maximumN) {N = maximumN;}
        circles = new Circle [N];
        Circle.canCollideWithEachOther = canCollideWithEachOther;
        Circle.gravity = gravity;
        Circle.bouncingCoefficient = bouncingCoefficient;
        Circle.harmonicCoefficient = harmonicCoefficient;

        for (int i = 0; i < N; i ++)
        {
            boolean pass = true;
            double x = 0.0; double y = 0.0;

            // x = (Math.random() * (sceneWidth - 2 * R)) + R;
            // y = (Math.random() * (sceneHeight - 2 * R)) + R;
            if (i % 2 == 0) {x = (Math.random() * (sceneWidth / 2 - R)) + R;}
            else  {x = (Math.random() * (sceneWidth / 2 - R)) + R + sceneWidth / 2;}
            y = (Math.random() * (sceneHeight - 2 * R)) + R;

            double vx = (Math.random() * (maximumSpeed * 2 + 1)) - maximumSpeed;
            double vy = (Math.random() * (maximumSpeed * 2 + 1)) - maximumSpeed;

            Color color = Circle.colorGenerator(i, numberOfColors);

            circles[i] = new Circle(x, y, R, vx, vy, color, isFilled, canBeDestroyed);
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
        double energy = 0;
        while (true)
        {
            // draw curent status
            frame.render(circles);
            AlgoVisHelper.pause(20);

            // renew status
            if (isAnimated)
            {
                if (Circle.canCollideWithEachOther)
                {

                        // collide with each other
                        for (int i = 0; i < circles.length; i++) {
                            circles[i].ballBallCollision(i, circles);
                        }
                        // copy the new data to current data
                        for (int i = 0; i < circles.length; i++) {
                            circles[i].vx = circles[i].vxNew;
                            circles[i].vy = circles[i].vyNew;
                            circles[i].x = circles[i].xNew;
                            circles[i].y = circles[i].yNew;
                        }
                }

                // calculating the sum of energy of existing balls
                double newEnergy = 0;
                for (Circle circle : circles)
                {if (circle.stillExists) {newEnergy = newEnergy + 0.5 * ((Math.pow(circle.vx, 2)) + (Math.pow(circle.vy, 2))); }}
                // System.out.println(newEnergy);
                // if (Math.abs(energy - newEnergy) > 10) {isAnimated = false;}
                energy = newEnergy;

                // move and collide with walls
                for (Circle circle : circles)
                    {circle.move(0, 0, frame.getCanvasWidth(), frame.getCanvasHeight());}

            }
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
                if (circle.contain(event.getPoint()) && (isAnimated))
                {
                    circle.isFilled = !circle.isFilled;
                    if (circle.canBeDestroyed) {circle.stillExists = false;}
                }
        }
    }

    public static void main(String[] args)
    {
        int sceneWidth = 1440;
        int sceneHeight = 800;
        int N = 10; // number of particles
        int R = 70; // diameter of particles
        int maximumSpeed = 200; // note that it is a component speed
        int numberOfColors = 10;
        boolean isFilled = true;
        boolean canBeDestroyed = true;
        boolean canCollideWithEachOther = true;
        int gravity = 0; // can be negative
        double bouncingCoefficient = 1.0 ; // needs to be less than one greater than zero
        boolean simpleHarmonicCenter = false;
        double harmonicCoefficient = 0;

        AlgoVisualizer visualizer = new AlgoVisualizer(sceneWidth, sceneHeight, N, R, maximumSpeed, numberOfColors
                , isFilled, canBeDestroyed, canCollideWithEachOther, gravity, bouncingCoefficient, harmonicCoefficient);
    }
}
