import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AlgoVisualizer {

    // TODO: 创建自己的数据
    private static int DELAY = 1;

    private InsertionSortData data;        // 数据
    private AlgoFrame frame;    // 视图

    public AlgoVisualizer(int sceneWidth, int sceneHeight, int N, boolean showNumber){

        // 初始化数据
        int randomBound = sceneHeight - 15;
        if (randomBound < 1 ) {randomBound = 1;}
        data = new InsertionSortData(N, randomBound);
        InsertionSortData.showNumber = showNumber;

        // 初始化视图
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Inserttion Sort Visualization", sceneWidth, sceneHeight);
            // TODO: 根据情况决定是否加入键盘鼠标事件监听器
            frame.addKeyListener(new AlgoKeyListener());
            frame.addMouseListener(new AlgoMouseListener());
            new Thread(() -> {
                run();
            }).start();
        });
    }

    // 动画逻辑
    private void run()
    {
        setData(0,-1);
        for(int i = 0; i < data.N(); i ++)
        {
            setData(i, i);
            for(int j = i; j >0 && data.get(j) < data.get(j - 1); j--)
            {
                data.swap(j, j -1);
                setData(i + 1,j -1);
            }
        }
        setData(data.N(), -1);
    }

    private void setData(int orderedIndex, int currentIndex)
    {
        data.orderedIndex = orderedIndex;
        data.currentIndex = currentIndex;

        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    // TODO: 根据情况决定是否实现键盘鼠标等交互事件监听器类
    private class AlgoKeyListener extends KeyAdapter{ }
    private class AlgoMouseListener extends MouseAdapter{ }

    public static void main(String[] args) {

        int sceneWidth = 1440;
        int sceneHeight = 800;
        int N = 180;
        boolean showNumber = false;

        // TODO: 根据需要设置其他参数，初始化visualizer
        AlgoVisualizer visualizer = new AlgoVisualizer(sceneWidth, sceneHeight, N, showNumber);
    }
}
