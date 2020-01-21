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
    public static int round;

    public AlgoVisualizer(int sceneWidth, int sceneHeight, int N, int R, double maximumMass, int maximumSpeed, int numberOfColors,
                          boolean isFilled, boolean canBeDestroyed, boolean canCollideWithEachOther, int gravity,
                          double bouncingCoefficient, double gravitationCoefficient, boolean showTrack)
    {
        // initialize data
        int maximumN = (sceneWidth / (2 * R)) * (sceneHeight / (2 * R));
        if (N > maximumN) {N = maximumN;}
        circles = new Circle [N];
        Circle.canCollideWithEachOther = canCollideWithEachOther;
        Circle.gravity = gravity;
        Circle.bouncingCoefficient = bouncingCoefficient;
        Circle.gravitationCoefficient = gravitationCoefficient;
        Circle.showTrack = showTrack;
        round = -1;

        for (int i = 0; i < N; i ++)
        {

            double x = (Math.random() * (sceneWidth - 2 * R)) + R;
            double y = (Math.random() * (sceneHeight - 2 * R)) + R;


            double vx = (Math.random() * (maximumSpeed * 2 + 1)) - maximumSpeed;
            double vy = (Math.random() * (maximumSpeed * 2 + 1)) - maximumSpeed;

            int mass = (int) (Math.random() * maximumMass) + 1;

            Color color = Circle.colorGenerator(i, numberOfColors);

            circles[i] = new Circle(x, y, R, mass, vx, vy, color, isFilled, canBeDestroyed);
        }

        circles[0].x = 720;
        circles[0].y = 400;
        circles[0].vx = 0;
        circles[0].vy = 0;
        circles[0].r = 30;
        circles[0].mass = 250000; // sun

        circles[1].x = 720;
        circles[1].y = 50;
        circles[1].vx = -267;
        circles[1].vy = 0;
        circles[1].r = 5;
        circles[1].mass = 250; //earth

        circles[2].x = 720;
        circles[2].y = 40;
        circles[2].vx = -318;
        circles[2].vy = 0;
        circles[2].r = 2;
        circles[2].mass = 0.250; // moon

        circles[3].x = 720;
        circles[3].y = 650;
        circles[3].vx = 315;
        circles[3].vy = 0;
        circles[3].r = 4;
        circles[3].mass = 50; // venum

        circles[4].x = 720;
        circles[4].y = 300;
        circles[4].vx = -500;
        circles[4].vy = 0;
        circles[4].r = 4;
        circles[4].mass = 50; //mercury

        circles[5].x = 225;
        circles[5].y = 400;
        circles[5].vx = 0;
        circles[5].vy = 200;
        circles[5].r = 3;
        circles[5].mass = 150; //mars1

        circles[6].x = 215;
        circles[6].y = 400;
        circles[6].vx = 0;
        circles[6].vy = 250;
        circles[6].r = 3;
        circles[6].mass = 150; //mars2

        circles[7].x = 670;
        circles[7].y = 400;
        circles[7].vx = 0;
        circles[7].vy = 965;
        circles[7].r = 3;
        circles[7].mass = 0.05; // commet        // reduced solar system

        /* circles[0].x = 720;
        circles[0].y = 200;
        circles[0].vx = -1800;
        circles[0].vy = 0;
        circles[0].r = 25;
        circles[0].mass = 5000000; // star 1

        circles[1].x = 720;
        circles[1].y = 600;
        circles[1].vx = 1800;
        circles[1].vy = 0;
        circles[1].r = 25;
        circles[1].mass = 5000000; // star 2

        circles[2].x = 720;
        circles[2].y = 400;
        circles[2].vx = 0;
        circles[2].vy = 0;
        circles[2].r = 25;
        circles[2].mass = 5000000; // star 3   // three-body-problem */

        /* circles[0].x = 720;
        circles[0].y = 200;
        circles[0].vx = -790;
        circles[0].vy = 0;
        circles[0].r = 20;
        circles[0].mass = 5000000; // star 1

        circles[1].x = 720;
        circles[1].y = 600;
        circles[1].vx = 790;
        circles[1].vy = 0;
        circles[1].r = 20;
        circles[1].mass = 5000000; // star 2

        circles[2].x = 720;
        circles[2].y = 700;
        circles[2].vx = 3000;
        circles[2].vy = 0;
        circles[2].r = 5;
        circles[2].mass = 50; // planet */


        // initialize the window
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("-Planetary Model-", sceneWidth, sceneHeight);
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
            /*// stablize the sun in the center
            circles[0].x = 720;
            circles[0].y = 400;
            circles[0].vx = 0;
            circles[0].vy = 0; */

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

                if (Circle.gravitationCoefficient != 0)
                {
                    // change velocity
                    for (int i = 0; i < circles.length; i++) {
                        circles[i].gravitationAttraction(i, circles);
                    }
                }

                // move and collide with walls
                for (Circle circle : circles)
                    {circle.move(0, 0, frame.getCanvasWidth(), frame.getCanvasHeight());}

                // recording the positions of all circles;
                round ++;
                if (round >= Circle.trackLength)
                {
                    round = Circle.trackLength - 1;
                    for (Circle circle: circles)
                    {
                        for (int i = 0; i < round; i++)
                            circle.track[i] = circle.track[i + 1];
                        circle.track[round] = new Position(circle.x,circle.y);
                    }
                }
                else
                {
                    for (Circle circle: circles)
                        circle.track[round] = new Position(circle.x,circle.y);
                }

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
        
        public void keyPressed(KeyEvent event)
        {
            if (event.getKeyChar() == 'w')
            {
                for (Circle circle : circles)
                {
                    circle.vx = circle.vx * 1.05;
                    circle.vy = circle.vy * 1.05;
                }
            }
            if (event.getKeyChar() == 's')
            {
                for (Circle circle : circles)
                {
                    circle.vx = circle.vx * 0.9;
                    circle.vy = circle.vy * 0.9;
                }
            }
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
        int N = 8; // number of particles
        int R = 5; // diameter of particles
        double maximumMass = 10;
        int maximumSpeed = 100; // note that it is a component speed
        int numberOfColors = 10;
        boolean isFilled = true;
        boolean canBeDestroyed = true;
        boolean canCollideWithEachOther = true;
        int gravity = 0; // can be negative
        double bouncingCoefficient = 1.0 ; // needs to be less than one greater than zero
        double gravitationCoefficient = 1;
        boolean showTrack = false;

        AlgoVisualizer visualizer = new AlgoVisualizer(sceneWidth, sceneHeight, N, R, maximumMass, maximumSpeed, numberOfColors
                , isFilled, canBeDestroyed, canCollideWithEachOther, gravity, bouncingCoefficient, gravitationCoefficient, showTrack);
    }
}
