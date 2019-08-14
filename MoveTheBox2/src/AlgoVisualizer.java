import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Scanner;

public class AlgoVisualizer {

    private static int blockSide = 80;
    private static int DELAY = 500;

    // TODO: 创建自己的数据
    private GameData data;        // 数据
    private AlgoFrame frame;    // 视图

    public AlgoVisualizer(String filename){

        // 初始化数据
        // TODO: 初始化数据
        data = new GameData(filename);
        int sceneWidth = data.M() * blockSide;
        int sceneHeight = data.N() * blockSide;

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

        setData();
        // TODO: 编写自己的动画逻辑

        if (data.solve())
            System.out.println("The game has a solution.");
        else
            System.out.println("The game does not have a solution.");

        Scanner in = new Scanner(System.in);
        while (!data.ifShowBoardWins()){
            int x1 = in.nextInt();
            int y1 = in.nextInt();
            int x2 = in.nextInt();
            int y2 = in.nextInt();
            data.swapShowBoard(x1,y1,x2,y2);
            setData();
            // match & drop
            do {
                setData();
                data.dropShowBoard();
                setData();
            } while (data.matchShowBoard());
        }
        setData();
        System.out.println("You won!");

    }

    private void setData() {
        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    // TODO: 根据情况决定是否实现键盘鼠标等交互事件监听器类
    private class AlgoKeyListener extends KeyAdapter{ }
    private class AlgoMouseListener extends MouseAdapter{ }

    public static void main(String[] args) {

        String filename = "level/london_03.txt";

        // TODO: 根据需要设置其他参数，初始化visualizer
        AlgoVisualizer visualizer = new AlgoVisualizer(filename);
    }
}
