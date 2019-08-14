public class FractalData {

    private int depth;
    private double splitAngle;
    private double splitPosition;

    public FractalData(int depth, double splitAngle, double splitPosition){
        this.depth = depth;
        this.splitAngle = splitAngle;
        this.splitPosition = splitPosition;
    }

    public int getDepth(){
        return depth;
    }

    public double getSplitAngle() {
        return splitAngle;
    }

    public double getSplitPosition() {
        return splitPosition;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }
}
