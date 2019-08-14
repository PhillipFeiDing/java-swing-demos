import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AlgoVisualizer {

    // TODO: 创建自己的数据
    private Data data;        // 数据
    private AlgoFrame frame;    // 视图

    public AlgoVisualizer(int sceneWidth, int sceneHeight, Data data){

        // 初始化数据
        // TODO: 初始化数据
        this.data = data;

        // 初始化视图
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Sampling Distribution", sceneWidth, sceneHeight);
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
            frame.render(data);
        // TODO: 编写自己的动画逻辑
    }

    // TODO: 根据情况决定是否实现键盘鼠标等交互事件监听器类
    private class AlgoKeyListener extends KeyAdapter{ }
    private class AlgoMouseListener extends MouseAdapter{ }

    public static void main(String[] args) {

        int sceneWidth = 1440;
        int sceneHeight = 855;

        int N = 100; int low = 1; int high = 100;
        DataSet population = new DataSet(N, low, high);
        Sample samples = new Sample();
        int n = 20; int repetition = 100000;
        for (int i = 0; i < repetition; i ++)
            samples.addSample(population.generateRandomSample(n));
        int[] dist = samples.getDistribution(low, high);
        Data data = new Data(dist, population.mean(), population.stDev(), samples.mean(), samples.stDev());
        // TODO: 根据需要设置其他参数，初始化visualizer
        AlgoVisualizer visualizer = new AlgoVisualizer(sceneWidth, sceneHeight, data);
    }
}
