import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.RenderingHints;

import javax.swing.*;

public class AlgoFrame extends JFrame{

    private int canvasWidth;
    private int canvasHeight;

    public AlgoFrame(String title, int canvasWidth, int canvasHeight){

        super(title);

        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;

        AlgoCanvas canvas = new AlgoCanvas();
        setContentPane(canvas);
        pack();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        setVisible(true);
    }

    public AlgoFrame(String title){

        this(title, 1024, 768);
    }

    public int getCanvasWidth(){return canvasWidth;}
    public int getCanvasHeight(){return canvasHeight;}

    // TODO: 设置自己的数据
    private Data data;
    public void render(Data data){
        this.data = data;
        repaint();
    }

    private class AlgoCanvas extends JPanel{

        public AlgoCanvas(){
            // 双缓存
            super(true);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D)g;

            // 抗锯齿
            RenderingHints hints = new RenderingHints(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.addRenderingHints(hints);

            // 具体绘制
            // TODO： 绘制自己的数据data

            //画背景
            AlgoVisHelper.setColor(g2d, AlgoVisHelper.White);
            AlgoVisHelper.fillRectangle(g2d,0,0,canvasWidth, canvasHeight);

            // 画标准差背景
            int n = data.dist().length;
            int width = (int) ((double) canvasWidth / n); int height;


            /*
            //一个标准差
            AlgoVisHelper.setColor(g2d, AlgoVisHelper.Green);
            AlgoVisHelper.fillRectangle(g2d, (int) (data.sample_mean() * width - data.sample_stDev() * width) - 1, canvasHeight - height,
                    (int) (data.sample_stDev() * width * 2) + 2, height);
            //两个标准差
            AlgoVisHelper.setColor(g2d, AlgoVisHelper.LightGreen);
            AlgoVisHelper.fillRectangle(g2d, (int) (data.sample_mean() * width - data.sample_stDev() * 2 * width), canvasHeight - height,
                    (int) (data.sample_stDev() * width) + 1, height);
            AlgoVisHelper.fillRectangle(g2d, (int) (data.sample_mean() * width + data.sample_stDev() * width), canvasHeight - height,
                    (int) (data.sample_stDev() * width) + 1, height);
            //三个标准差
            AlgoVisHelper.setColor(g2d, AlgoVisHelper.LighterGreen);
            AlgoVisHelper.fillRectangle(g2d, (int) (data.sample_mean() * width - data.sample_stDev() * 3 * width), canvasHeight - height,
                    (int) (data.sample_stDev() * width) + 1, height);
            AlgoVisHelper.fillRectangle(g2d, (int) (data.sample_mean() * width + data.sample_stDev() * 2 * width), canvasHeight - height,
                    (int) (data.sample_stDev() * width) + 1, height);
            */

            // 画样本分布
            AlgoVisHelper.setStrokeWidth(g2d,1);
            for (int i = 0; i < n; i ++){
                height = data.dist()[i];
                System.out.println("[" + (i + 1) + ", " + (i + 2) + ") : " + height);
                AlgoVisHelper.setColor(g2d, AlgoVisHelper.White);
                AlgoVisHelper.fillRectangle(g2d, (i + 1) * width, canvasHeight - height / 20, width, height/ 20);
                AlgoVisHelper.setColor(g2d, AlgoVisHelper.Black);
                AlgoVisHelper.strokeRectangle(g2d, (i + 1) * width, canvasHeight - height / 20, width, height / 20);
            }

            // 画线
            AlgoVisHelper.setStrokeWidth(g2d,3);
            AlgoVisHelper.setColor(g2d, AlgoVisHelper.Black);
            AlgoVisHelper.drawLine(g2d, data.sample_mean() * width, canvasHeight - Sample.getMaxCount(data.dist()) / 20 - 40,
                    data.sample_mean() * width, canvasHeight); //中竖线
            height = Sample.getMaxCount(data.dist()) / 20;
            AlgoVisHelper.drawLine(g2d,(int) (data.sample_mean() * width - data.sample_stDev() * width), canvasHeight - height - 40,
                    (int) (data.sample_mean() * width + data.sample_stDev() * width), canvasHeight - height - 40); //中横线
            AlgoVisHelper.drawLine(g2d,(int) (data.sample_mean() * width - data.sample_stDev() * width), canvasHeight - height - 60,
                    (int) (data.sample_mean() * width - data.sample_stDev() * width), canvasHeight - height - 20); //左
            AlgoVisHelper.drawLine(g2d,(int) (data.sample_mean() * width + data.sample_stDev() * width), canvasHeight - height - 60,
                    (int) (data.sample_mean() * width + data.sample_stDev() * width), canvasHeight - height - 20); //右

            // 标
            AlgoVisHelper.putImage(g2d, (int)((data.sample_mean()) * width) - 12,
                    (canvasHeight - Sample.getMaxCount(data.dist()) / 20 - 19 - 40),
                    Data.mu_url);
            AlgoVisHelper.putImage(g2d, (int)(((data.sample_mean()) - data.sample_stDev() / 2) * width) - 12,
                    (canvasHeight - Sample.getMaxCount(data.dist()) / 20 - 35),
                    Data.sigma_url);
            AlgoVisHelper.putImage(g2d, (int)(((data.sample_mean()) + data.sample_stDev() / 2) * width) - 12,
                    (canvasHeight - Sample.getMaxCount(data.dist()) / 20 - 35),
                    Data.sigma_url);

            //IO输出
            System.out.println("Population mean: " + data.population_mean());
            System.out.println("Sample mean: " + data.sample_mean());
            System.out.println("Population standard deviation: " + data.population_stDev());
            System.out.println("Sample standard deviation: " + data.sample_stDev());

        }

        @Override
        public Dimension getPreferredSize(){
            return new Dimension(canvasWidth, canvasHeight);
        }
    }
}

