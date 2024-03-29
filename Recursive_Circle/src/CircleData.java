public class CircleData {

    private int startX, startY;
    private int startR;
    private int depth;
    private int step;
    public int deviation;

    public CircleData(int x, int y, int r, int d, int step){
        this.startX = x;
        this.startY = y;
        this.startR = r;
        this.depth = d;
        this.step = step;
        this.deviation = 0;
    }

    public int getStartX() {return startX;}
    public int getStartY() {return startY;}
    public int getStartR() {return startR;}
    public int getDepth() {return depth;}
    public int getStep() {return step;}

    public void incDeviation() {deviation ++;}
}
