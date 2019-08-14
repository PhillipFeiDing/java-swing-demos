import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class MazeData
{
    public static final char Road = ' ';
    public static final char Wall = '#';

    private int N, M;
    public char [] [] maze;
    public boolean [][] inMist;
    private int entranceX, entranceY;
    private int exitX, exitY;

    public boolean [][] visited;
    public boolean [][] path;
    public boolean [][] result;

    public MazeData(int N, int M)
    {
        if (N % 2 ==0 || M % 2 == 0)
            throw new IllegalArgumentException("Our maze Generator Algorithm requres the width and height of the maze that are odds");

        this.N = N;
        this.M = M;

        maze = new char[N][M];
        visited = new boolean [N][M];
        inMist = new boolean [N][M];
        path = new boolean [N][M];

        for (int i =0; i < N; i ++)
            for (int j = 0; j < M; j ++)
            {
                if (i % 2 == 1 && j % 2 == 1)
                    maze[i][j] = Road;
                else
                    maze[i][j] = Wall;

                visited[i][j] =false;
                inMist[i][j] = true;
                path[i][j] = false;

            }

        entranceX = N - 2;
        entranceY = 0;
        exitX = 1; // N - 2;
        exitY = M - 1;

        maze[entranceX][entranceY] = Road;
        maze[exitX][exitY] = Road;

    }

    /* public MazeData(String filename)
    {
        if (filename == null)
            throw new IllegalArgumentException("Filename can not be null!");

        Scanner scanner = null;
        try
        {
            File file = new File (filename);
            if (!file.exists())
                throw new IllegalArgumentException("File " + filename + " doesn't exist!");

            FileInputStream fis = new FileInputStream(file);
            scanner = new Scanner(new BufferedInputStream(fis), "UTF-8");

            // scan the first row
            String nmline = scanner.nextLine();
            String [] nm = nmline.trim().split("\\s+");

            N = Integer.parseInt(nm[0]);
            M = Integer.parseInt(nm[1]);

            maze = new char [N][M];
            visited = new boolean [N][M];
            path = new boolean [N][M];
            result = new boolean [N][M];

            // scan the subsequent N lines
            for (int i = 0; i < N; i ++)
            {
                String line = scanner.nextLine();

                // ensure that there are M chars in every line
                if (line.length() != M)
                    throw new IllegalArgumentException("Maze file " + filename + " does not keep " + M + " chars in a line!");

                for (int j = 0; j < M; j ++)
                {
                    maze[i][j] = line.charAt(j);
                    visited [i][j] = false;
                    path [i][j] = false;
                    result [i][j] = false;
                }
            }
        }
        catch (IOException e) {e.printStackTrace();}
        finally
        {
            if (scanner != null)
                scanner.close();
        }

        entranceX = 1;
        entranceY = 0;
        exitX = N -2;
        exitY = M -1;
    } */

    public int N() {return N;}
    public int M() {return M;}
    public int getEntranceX() {return entranceX;}
    public int getEntranceY() {return entranceY;}
    public int getExitX() {return exitX;}
    public int getExitY() {return exitY;}

    public char getMaze(int i, int j)
    {
        if (! inArea(i, j))
            throw new IllegalArgumentException("i or j is out of index in getMaze!");
        return maze[i] [j];
    }

    public boolean inArea(int x, int y)
    {
        return (x >= 0 && x < N && y >= 0 && y < M);
    }

    public void openMist (int x, int y)
    {
        if (!inArea(x,y))
            throw new IllegalArgumentException("x or y is out of index in openMist function!");

        for (int i = x - 1; i <= x + 1; i++)
            for (int j = y - 1; j <= y +1; j++)
                if (inArea(i, j))
                    inMist[i][j] = false;

        return;
    }

    public void resetVisit()
    {
        for (int i = 0; i < N; i ++)
            for (int j = 0; j < M; j ++)
            {
                visited [i][j] = false;
            }
        return;
    }

    public void print()
    {
        System.out.println(N + " " + M);
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < M; j++)
                System.out.print(maze[i][j]);
            System.out.println();
        }
        return;
    }
}
