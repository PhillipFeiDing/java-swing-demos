public class ShuffleExp {
    private int N;
    private int n, m;

    public ShuffleExp(int N, int n, int m){
        if (N <= 0)
            throw new IllegalArgumentException("Number of trials must be greater than 0!");
        if (n < m)
            throw new IllegalArgumentException("Mine number is larger than the size!");

        this.N = N;
        this.n = n;
        this.m = m;

    }

    public void run(){
        int freq[] = new int[n];

        int arr[] = new int[n];
        for (int i = 0; i < N; i ++) {
            reset(arr);
            shuffle(arr);
            for (int j = 0; j < n; j ++)
                freq[j] += arr[j];
        }

        for (int i = 0; i < n; i ++){
            System.out.println(i + " : " + (double) freq[i] / N);
        }
    }

    private void reset(int[] arr) {
        for (int i = 0; i < m; i ++)
            arr[i] = 1;

        for (int i = m; i < n; i ++)
            arr[i] = 0;
    }

    private void shuffle(int[] arr) {
        for (int i = 0; i < m; i ++){
            int x = (int)(Math.random() * (n - i)) + i;
            swap(arr, i, x);
        }
    }

    private void swap(int[] arr, int x, int y) {
        int temp = arr[x];
        arr[x] = arr[y];
        arr[y] = temp;
    }

    public static void main(String[] args) {
        int N = 10000000;
        int n = 10;
        int m = 5;

        ShuffleExp exp = new ShuffleExp(N, n, m);
        exp.run();
    }
}
