import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

public class AlgoVisualizer {

    // TODO: 创建自己的数据
    private static int DELAY = 40;
    private int[] money;        // 数据
    private AlgoFrame frame;    // 视图
    private boolean isAnimated = false;
    private int round = 10; // 模拟轮数
    private int members = 100; // 一共多少人
    private int moneyForEveryone = 100; // 每个人多少钱
    private int minimumMoneyGiver = -10000; // 送钱人至少有多少钱
    private int minimumMoneyReceiver = -10000; // 收钱人至少有多少钱

    public AlgoVisualizer(int sceneWidth, int sceneHeight){

        // 初始化数据
        money = new int [members];
        for (int i = 0; i < money.length; i ++)
            money[i] = moneyForEveryone;

        // 初始化视图
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Money Exchange Distribution", sceneWidth, sceneHeight);
            // TODO: 根据情况决定是否加入键盘鼠标事件监听器
            frame.addKeyListener(new AlgoKeyListener());
            // frame.addMouseListener(new AlgoMouseListener());
            new Thread(() -> {
                run();
            }).start();
        });
    }

    // 动画逻辑
    private void run(){

        while (true)
        {
            Arrays.sort(money);
            frame.render(money);
            AlgoVisHelper.pause(DELAY);

            if (isAnimated)
            for (int k = 0; k < round; k++) // 多少轮显示一次
            {
                for (int i = 0; i < money.length; i++)
                {
                     if (money [i] >= minimumMoneyGiver) // 给钱人最少要有多少钱
                     {
                         int j = (int) (Math.random() * money.length);
                         if (money [j] >= minimumMoneyReceiver) // 收钱人最少要有多少钱
                         {
                             money[i] = money[i] - 1;
                             money[j] = money[j] + 1;
                         }
                     }
                }
            }
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
            if (event.getKeyChar() == 'w')
            {
                round = round * 10;
                if (round > 10000) {round = round / 10;}
            }
            if (event.getKeyChar() == 's')
            {
                round = round / 10;
                if (round < 1) {round = 1;}
            }
            if (event.getKeyChar() == 'r')
            {
                isAnimated = false;
                for (int i = 0; i < money.length; i ++)
                    money[i] = moneyForEveryone;
            }
        }
    }

    private class AlgoMouseListener extends MouseAdapter{ }

    public static void main(String[] args) {

        int sceneWidth = 1000;
        int sceneHeight = 800;

        AlgoVisualizer visualizer = new AlgoVisualizer(sceneWidth, sceneHeight);
    }
}
