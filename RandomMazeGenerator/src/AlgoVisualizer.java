import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.Stack;

public class AlgoVisualizer {

    // TODO: 创建自己的数据
    private static int DELAY = 4;
    private static int blockSide = 7;
    private static final int d[][] = {{-1, 0},{0, 1},{1, 0},{0, -1}};

    private MazeData data;        // 数据
    private AlgoFrame frame;    // 视图



    public AlgoVisualizer(int N, int M)
    {
        // 初始化数据
        data = new MazeData(N, M);
        int sceneHeight = data.N() * blockSide;
        int sceneWidth = data.M() * blockSide;

        // 初始化视图
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Random Maze Generator Visualization", sceneWidth, sceneHeight);
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
        setData(-1,-1);

        RandomQueue<Position> queue = new RandomQueue<Position>();
        Position first = new Position(data.getEntranceX(), data.getEntranceY() + 1);
        queue.add(first);
        data.visited[first.getX()][first.getY()] = true;
        data.openMist(first.getX(), first.getY());

        while (queue.size() != 0)
        {
            Position curPos = queue.remove();

            for (int i = 0; i < 4; i++) {
                int newX = curPos.getX() + d[i][0] * 2;
                int newY = curPos.getY() + d[i][1] * 2;
                if (data.inArea(newX, newY) && !data.visited[newX][newY]) {
                    queue.add(new Position(newX, newY));
                    data.visited[newX][newY] = true;
                    data.openMist(newX, newY);
                    setData(curPos.getX() + d[i][0], curPos.getY() + d [i][1]);
                }
            }
        }

        setData(-1,-1);

    }


    private void setData(int x, int y)
    {
        if (data.inArea(x,y))
           data.maze [x][y] = MazeData.Road;

        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    // TODO: 根据情况决定是否实现键盘鼠标等交互事件监听器类
    private class AlgoKeyListener extends KeyAdapter
    {
        @Override
        public void keyReleased(KeyEvent event)
        {
            if (event.getKeyChar() == 'w')
            {
                if (DELAY % 2 == 0) {DELAY = DELAY / 2;}
                if (DELAY < 1) {DELAY = 1;}
            }
            if (event.getKeyChar() == 's')
            {
                if (DELAY * 2 <= 1024) DELAY = DELAY * 2;
            }
        }
    }

    private class AlgoMouseListener extends MouseAdapter{ }

    public static void main(String[] args)
    {
        int N = 113;
        int M = 205;

        AlgoVisualizer vis = new AlgoVisualizer(N, M);
    }
}
