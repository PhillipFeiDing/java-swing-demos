public class Data {
    private int[] dist;
    private double population_mean, population_stDev, sample_mean, sample_stDev;
    public static final String mu_url = "images/mu.png";
    public static final String sigma_url = "images/sigma.png";

    public Data(int[] dist, double population_mean, double population_stDev, double sample_mean, double sample_stDev){
        this.dist = dist;
        this.population_mean = population_mean;
        this.population_stDev = population_stDev;
        this.sample_mean = sample_mean;
        this.sample_stDev = sample_stDev;
    }

    public int[] dist() {return dist;}
    public double population_mean() {return population_mean;}
    public double population_stDev() {return population_stDev;}
    public double sample_mean() {return sample_mean;}
    public double sample_stDev() {return sample_stDev;}
}
