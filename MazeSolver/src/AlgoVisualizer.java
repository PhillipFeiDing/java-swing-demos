import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AlgoVisualizer {

    // TODO: 创建自己的数据
    private static int DELAY = 16;
    private static int blockSide = 7;

    private MazeData data;        // 数据
    private AlgoFrame frame;    // 视图

    private static final int d[][] = {{-1, 0},{0, 1},{1, 0},{0, -1}};

    public AlgoVisualizer(String mazeFile)
    {

        // 初始化数据
        data = new MazeData(mazeFile);

        int sceneHeight = data.N() * blockSide;
        int sceneWidth = data.M() * blockSide;

        // 初始化视图
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Maze Solver Visualization", sceneWidth, sceneHeight);
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
        setData(-1, -1,false);

        if (!go(data.getEntranceX(), data.getEntranceY()))
            System.out.println("The maze has No solution!");

        setData(-1, -1,false);
    }

    // solve the maze with starting position (x,y), and if it's solve, return true, otherwise, false
    private boolean go(int x, int y)
    {
        if (!data.inArea(x, y))
            throw new IllegalArgumentException("x, y are out of index in go function!");

        data.visited[x][y] = true;

        setData(x, y, true);

        if (x == data.getExitX() && y == data.getExitY())
            return true;

        for (int i = 0; i < 4; i++) {
            int newX = x + d[i][0];
            int newY = y + d[i][1];
            if (data.inArea(newX, newY) && data.getMaze(newX, newY) == MazeData.Road
                    && !data.visited[newX][newY])
                if (go(newX, newY)) return true;
        }
        setData(x, y, false);

        return false;
    }

    private void setData(int x, int y, boolean isPath)
    {
        if (data.inArea(x,y))
        { data.path [x][y] = isPath;}

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
        String mazeFile = "maze_101_101.txt";
        AlgoVisualizer vis = new AlgoVisualizer(mazeFile);
    }
}
