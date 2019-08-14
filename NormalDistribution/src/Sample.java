import java.util.ArrayList;
public class Sample{
    private ArrayList<int[]> samples;

    public Sample(){
        samples = new ArrayList<int[]>();
    }

    public void addSample(int[] sample){
        samples.add(sample);
    }

    public double mean(int[] sample){
        int sum = 0;
        for (int val : sample)
            sum += val;
        return (double) sum / sample.length;
    }

    public double mean(){
        double sum = 0;
        for (int i = 0; i < samples.size(); i ++)
            sum += mean(samples.get(i));
        return sum / samples.size();
    }

    public double stDev(){
        double mean = mean();
        double squareSum = 0;
        for (int i = 0; i < samples.size(); i ++)
            squareSum += Math.pow((mean(samples.get(i)) - mean), 2);
        return Math.sqrt(squareSum / (samples.size() - 1));
    }

    public int[] getDistribution(int low, int high){
        int[] result = new int[high - low + 1];
        for (int i = 0; i < samples.size(); i ++) {
            int mean = (int) mean(samples.get(i));
            if (mean >= low && mean < high)
                result[mean - low] ++;
        }
        return result;
    }

    public static int getMaxCount(int[] dist){
        int maxCount = dist[0];
        for (int i = 1; i < dist.length; i ++){
            if (maxCount < dist[i]){
                maxCount = dist[i];
            }
        }
        return maxCount;
    }

    // tester method, never used;
    public void printAllSamples(){
        for (int i = 0; i < samples.size(); i ++){
            int[] target = samples.get(i);
            for (int val : target)
                System.out.print(val + " ");
            System.out.println();
        }
    }
}