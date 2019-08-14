import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AlgoVisualizer {

    public static final int DELAY = 200;
    private int maxDepth;

    private FractalData data;        // 数据
    private AlgoFrame frame;    // 视图

    public AlgoVisualizer(int maxDepth){

        // 初始化数据
        data = new FractalData(maxDepth);
        this.maxDepth = maxDepth;

        int sceneWidth = (int) Math.pow(2, maxDepth);
        int sceneHeight = (int) Math.pow(2, maxDepth);

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

        int maxDepth = 9;

        // TODO: 根据需要设置其他参数，初始化visualizer
        AlgoVisualizer visualizer = new AlgoVisualizer(maxDepth);
    }
}
