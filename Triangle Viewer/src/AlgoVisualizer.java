import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Scanner;

public class AlgoVisualizer {

    // TODO: 创建自己的数据
    private Triangle triangle;        // 数据
    private AlgoFrame frame;    // 视图

    public AlgoVisualizer(int sceneWidth, int sceneHeight, Triangle triangle){

        // 初始化数据
        this.triangle = triangle;
        // TODO: 初始化数据

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
        frame.render(triangle);
        // TODO: 编写自己的动画逻辑
    }

    // TODO: 根据情况决定是否实现键盘鼠标等交互事件监听器类
    private class AlgoKeyListener extends KeyAdapter{ }
    private class AlgoMouseListener extends MouseAdapter{ }

    public static void main(String[] args) {

        int sceneWidth = 800;
        int sceneHeight = 800;

        Scanner in = new Scanner(System.in);
        System.out.println("Follow the instructions bellow to construct a triangle:");
        System.out.print("enter the coordiantes for PointA: ");
        Point pA = new Point(in.nextDouble(), in.nextDouble());
        System.out.print("enter the coordiantes for PointB: ");
        Point pB = new Point(in.nextDouble(), in.nextDouble());
        System.out.print("enter the coordiantes for PointC: ");
        Point pC = new Point(in.nextDouble(), in.nextDouble());
        Triangle triangle = new Triangle(pA, pB, pC);

        triangle.printSides();
        triangle.printAngles();
        System.out.printf("Perimter: %10.2f\n", triangle.getPerimeter());
        System.out.printf("Area    : %10.2f\n", triangle.getArea());

        // TODO: 根据需要设置其他参数，初始化visualizer
        AlgoVisualizer visualizer = new AlgoVisualizer(sceneWidth, sceneHeight, triangle);
    }
}
