import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AlgoVisualizer {

    public static final int DELAY = 400;
    private int maxDepth;

    private FractalData data;        // 数据
    private AlgoFrame frame;    // 视图

    public AlgoVisualizer(int sceneWidth, int sceneHeight, int maxDepth, double splitAngle, double splitPosition){

        // 初始化数据
        data = new FractalData(maxDepth, splitAngle, splitPosition);
        this.maxDepth = maxDepth;

        // 初始化视图
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Fractal Basics Visualizer", sceneWidth, sceneHeight);
            // TODO: 根据情况决定是否加入键盘鼠标事件监听器
            frame.addKeyListener(new AlgoKeyListener());
            frame.addMouseListener(new AlgoMouseListener());
            new Thread(() -> {
                run();
            }).start();
        });
    }

    // 动画逻辑
    private void run(){
        int depth = 1;
        int step = 1;
        while (true) {
            setData(depth);
            AlgoVisHelper.pause(DELAY);

            if (depth >= maxDepth || depth <= 0)
                step *= -1;
            depth += step;
        }
    }

    public void setData(int depth){
        if (depth >=0)
            data.setDepth(depth);

        frame.render(data);
        AlgoVisHelper.pause(DELAY);

    }

    // TODO: 根据情况决定是否实现键盘鼠标等交互事件监听器类
    private class AlgoKeyListener extends KeyAdapter{ }
    private class AlgoMouseListener extends MouseAdapter{ }

    public static void main(String[] args) {

        int maxDepth = 10;
        double splitAngle = 60.0;
        double splitPosition = 1.0 / 3.0;
        int sceneWidth = 800;
        int sceneHeight = 800;

        // TODO: 根据需要设置其他参数，初始化visualizer
        AlgoVisualizer visualizer = new AlgoVisualizer(sceneWidth, sceneHeight, maxDepth, splitAngle, splitPosition);
    }
}
