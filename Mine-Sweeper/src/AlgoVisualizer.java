import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AlgoVisualizer {

    // TODO: 创建自己的数据
    private static int DELAY = 5;
    private static int blockSide = 32;
    private MineSweeperData data;       // 数据
    private AlgoFrame frame;    // 视图

    public AlgoVisualizer(int N, int M, int mineNumber){

        // 初始化数据
        data = new MineSweeperData(N, M, mineNumber);
        int sceneWidth = M * blockSide;
        int sceneHeight = N * blockSide;

        // 初始化视图
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Welcome", sceneWidth, sceneHeight);
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
        setData(false, -1,-1);
        // TODO: 编写自己的动画逻辑
    }

    private void setData( boolean isLeftClicked, int x, int y){
        if (data.inArea(x,y)) {
            if (isLeftClicked) {
                if (data.isMine(x,y)) {
                    // Game Over
                    data.open[x][y] = true;
                }
                else
                    data.open(x,y);
            }
            else
                data.flags[x][y] = !data.flags[x][y];
        }
        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    // TODO: 根据情况决定是否实现键盘鼠标等交互事件监听器类
    private class AlgoKeyListener extends KeyAdapter{ }
    private class AlgoMouseListener extends MouseAdapter{
        @Override
        public void mouseReleased(MouseEvent event) {
            event.translatePoint(
                    -(int)(frame.getBounds().width - frame.getCanvasWidth()),
                    -(int)(frame.getBounds().height - frame.getCanvasHeight())
            );

            Point pos = event.getPoint();
            int w = frame.getCanvasWidth() / data.M();
            int h = frame.getCanvasHeight() / data.N();

            int x = pos.y / h;
            int y = pos.x / w;

            if (SwingUtilities.isLeftMouseButton(event)){
                setData(true,x,y);
            }
            else
            {
                setData(false,x,y);
            }
        }
    }

    public static void main(String[] args) {

        int sceneWidth = 800;
        int sceneHeight = 800;

        // TODO: 根据需要设置其他参数，初始化visualizer
        int N = 20;
        int M = 20;
        int mineNumber = 50;
        AlgoVisualizer visualizer = new AlgoVisualizer(N, M, mineNumber);
    }
}
