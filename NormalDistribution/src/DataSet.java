public class DataSet{
    private int[] population;

    public DataSet(int n, int low, int high){
        population = new int[n];
        int round = (int) (n / (double) (high - low + 1));
        for (int i = 0; i < n; i ++){
            population[i] = (int) transform(low + (double) i / round);
        }
    }

    public double transform(double original){
        return Math.log10(original) * 50;}

    public int n() {return population.length;}
    public double mean(){
        int sum = 0;
        for (int val : population)
            sum += val;
        return (double) sum / n();
    }
    public double stDev(){
        double mean = mean();
        double squareSum = 0;
        for (int val : population)
            squareSum += Math.pow(val - mean, 2);
        return Math.sqrt(squareSum / n());
    }

    public void swap(int i, int j){
        int temp = population[i];
        population[i] = population[j];
        population[j] = temp;
    }
    public void shuffle(){
        for (int i = 0; i < n(); i ++)
            swap(i, (int) (Math.random() * (n() - i)) + i);
    }
    public int[] generateRandomSample(int sampleSize){
        int[] result = new int[sampleSize];
        shuffle();
        for (int i = 0; i < sampleSize; i ++)
            result[i] = population[i];
        return result;
    }
}