public class SelectionSortData
{
    private int[] numbers;
    public int orderedIndex = -1; // [0 .... orderedIndex) is in sequence
    public int currentMinIndex = -1; // the index of the minimum found till now
    public int currentCompareIndex = -1; // the index of the element being compared currently
    public static boolean showNumber;

    public SelectionSortData(int N, int randomBound)
    {
        numbers = new int[N];

        for (int i = 0; i < N; i ++)
            numbers [i] = (int) (Math.random() * (randomBound) + 1);
    }

    public int N() {return numbers.length; }

    public int get(int index)
    {
        if (index < 0 || index >= numbers.length)
            throw new IllegalArgumentException("Invalid index to access Sort Data.");

        return numbers [index];
    }

    public void swap(int i, int j)
    {
        int t = numbers[i];
        numbers[i] = numbers [j];
        numbers[j] = t;
    }
}
