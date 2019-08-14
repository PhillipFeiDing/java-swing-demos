import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

public class AlgoVisualizer {

    private static int DELAY = 500;

    private MonteCarloPiData data;
    private AlgoFrame frame;    // 视图
    private int N;
    private boolean isAnimated = true;

    public AlgoVisualizer(int sceneWidth, int sceneHeight, int N){

        if (sceneWidth != sceneHeight)
            throw new IllegalArgumentException("This demo must be run in a square window!");

        // 初始化数据
        this.N = N;

        Circle circle = new Circle(sceneWidth / 2, sceneHeight/2, sceneWidth/2);
        data = new MonteCarloPiData(circle);

        // 初始化视图
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Get Pi with Monte Carlo", sceneWidth, sceneHeight);
            // TODO: 根据情况决定是否加入键盘鼠标事件监听器
            frame.addKeyListener(new AlgoKeyListener());
            /* frame.addMouseListener(new AlgoMouseListener()); */
            new Thread(() -> {
                run();
            }).start();
        });
    }

    // 动画逻辑
    private void run(){
        for (int i = 0; i < N; i ++)
        {
            if (i % 500 == 0)
            {
                frame.render(data);
                AlgoVisHelper.pause(DELAY);
                System.out.println(data.estimatePi());
            }

            int x = 0; int y = 0;
            x = (int) (Math.random() * frame.getCanvasWidth());
            y = (int) (Math.random() * frame.getCanvasHeight());
            if (isAnimated) {data.addPoint(new Point(x, y));}
        }
    }

    // TODO: 根据情况决定是否实现键盘鼠标等交互事件监听器类
    private class AlgoKeyListener extends KeyAdapter
    {
        @Override
        public void keyReleased(KeyEvent event)
        {
            if (event.getKeyChar() == ' ')
                isAnimated = !isAnimated;

        }
    }
    private class AlgoMouseListener extends MouseAdapter{ }

    public static void main(String[] args) {

        int sceneWidth = 800;
        int sceneHeight = 800;
        int N = 100000;

        AlgoVisualizer visualizer = new AlgoVisualizer(sceneWidth, sceneHeight, N);
    }
}
