package mknutsen.mazegen;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by mknutsen on 4/5/16.
 */
public final class MazeGeneration {

    private static Random rand;

    static {
        rand = new Random();
    }

    private MazeGeneration() {
    }

    public final static Maze recursiveRandomMazeGenerator(int rows, int columns, int x, int y, int width, int height) {
        int[][] maze = new int[rows][columns];
        for (int i = 0; i < maze.length; i++) {
            maze[i][0] = 1;
            maze[i][maze[0].length - 1] = 1;
        }
        for (int i = 1; i < maze[0].length - 1; i++) {
            maze[0][i] = 1;
            maze[maze.length - 1][i] = 1;
        }
        int[] entrance = new int[2];
        int[] exit = new int[2];
        if (Math.random() > .5) {
            entrance[0] = Math.random() > .5 ? 0 : rows - 1;
            entrance[1] = rand.nextInt(rows - 2) + 1;
        } else {
            entrance[0] = rand.nextInt(columns - 2) + 1;
            entrance[1] = Math.random() > .5 ? 0 : columns - 1;
        }
        if (Math.random() > .5) {
            exit[0] = Math.random() > .5 ? 0 : rows - 1;
            ;
            exit[1] = rand.nextInt(rows - 2) + 1;
        } else {
            exit[0] = rand.nextInt(columns - 2) + 1;
            exit[1] = Math.random() > .5 ? 0 : columns - 1;
        }
        maze[entrance[0]][entrance[1]] = 0;
        maze[exit[0]][exit[1]] = 0;
        switch (MazeConfigFile.ALG) {
            case RECURSIVE_NEAT:
                recursiveMazeGenerator(maze, 1, 1, rows - 1, columns - 1, true);
                break;
            case RECURSIVE_RANDOM:
                recursiveRandomMazeGenerator(maze, 1, 1, rows - 1, columns - 1);
                break;
        }

        return new Maze(maze, x, y, width, height, entrance, exit);
    }

    private static int[][] recursiveRandomMazeGenerator(int[][] maze, int x1, int y1, int x2, int y2) {
        //        for (int i = 0; i < maze.length; i++) {
        //            for (int j = 0; j < maze[0].length; j++) {
        //                System.out.print((maze[i][j] == 0 ? " " : "O") + " ");
        //            }
        //            System.out.println("");
        //        }
        //        System.out.println(x1 + " " + x2 + " " + y1 + " " + y2);
        //        Scanner scan = new Scanner(System.in);
        //        scan.nextLine();

        double horizontalOrVertical = rand.nextDouble();
        if (y2 - y1 <= 2 || x2 - x1 <= 2) {
            return maze;
        } else if (horizontalOrVertical > .5) {
            int x = x1 + rand.nextInt(x2 - x1 - 2) + 1;
            //System.out.println("-----------------------------------------------");
            //            System.out.println(x + ", ");
            for (int y = y1; y < y2; y++) {
                maze[x][y] = 1;
            }
            int randomDoor = (int) (y1 + (y2 - y1) * Math.random());
            if (maze[x][y1 - 1] == 0) {
                randomDoor = y1;
            } else if (maze[x][y2] == 0) {
                randomDoor = y2 - 1;
            }
            maze[x][randomDoor] = 0;
            recursiveRandomMazeGenerator(maze, x1, y1, x, y2);
            recursiveRandomMazeGenerator(maze, x + 1, y1, x2, y2);
        } else { // vertical wall
            int y = y1 + rand.nextInt(y2 - y1 - 2) + 1;
            //            System.out.println(", " + y);
            for (int x = x1; x < x2; x++) {
                maze[x][y] = 1;
            }
            int randomDoor = (int) (x1 + (x2 - x1) * Math.random());
            if (maze[x1 - 1][y] == 0) {
                randomDoor = x1;
            } else if (maze[x2][y] == 0) {
                randomDoor = x2 - 1;
            }
            maze[randomDoor][y] = 0;
            recursiveRandomMazeGenerator(maze, x1, y1, x2, y);
            recursiveRandomMazeGenerator(maze, x1, y + 1, x2, y2);

        }
        return maze;
    }

    private static int[][] recursiveMazeGenerator(int[][] maze, int x1, int y1, int x2, int y2, boolean vertical) {
        //        for (int i = 0; i < maze.length; i++) {
        //            for (int j = 0; j < maze[0].length; j++) {
        //                System.out.print((maze[i][j] == 0 ? " " : "O") + " ");
        //            }
        //            System.out.println("");
        //        }
        //        System.out.println(x1 + " " + x2 + " " + y1 + " " + y2);
        //        Scanner scan = new Scanner(System.in);
        //        scan.nextLine();

        double horizontalOrVertical = rand.nextDouble();
        if (y2 - y1 <= 2 || x2 - x1 <= 2) {
            return maze;
        } else if (vertical) {
            int x = x1 + (x2 - x1) / 2;
            //System.out.println("-----------------------------------------------");
            //            System.out.println(x + ", ");
            for (int y = y1; y < y2; y++) {
                maze[x][y] = 1;
            }
            int randomDoor = (int) (y1 + (y2 - y1) * Math.random());
            if (maze[x][y1 - 1] == 0) {
                randomDoor = y1;
            } else if (maze[x][y2] == 0) {
                randomDoor = y2 - 1;
            }
            maze[x][randomDoor] = 0;
            recursiveMazeGenerator(maze, x1, y1, x, y2, false);
            recursiveMazeGenerator(maze, x + 1, y1, x2, y2, false);
        } else { // vertical wall
            int y = y1 + (y2 - y1) / 2;
            //            System.out.println(", " + y);
            for (int x = x1; x < x2; x++) {
                maze[x][y] = 1;
            }
            int randomDoor = (int) (x1 + (x2 - x1) * Math.random());
            if (maze[x1 - 1][y] == 0) {
                randomDoor = x1;
            } else if (maze[x2][y] == 0) {
                randomDoor = x2 - 1;
            }
            maze[randomDoor][y] = 0;
            recursiveMazeGenerator(maze, x1, y1, x2, y, true);
            recursiveMazeGenerator(maze, x1, y + 1, x2, y2, true);

        }
        return maze;
    }

    public final static Maze mazeFromTextFile(InputStream mazeStream, int x, int y, int width, int height) {
        int[][] mazeArray;
        Scanner scan = new Scanner(mazeStream);
        ArrayList<String[]> strings = new ArrayList<>();
        int[] entrance = {scan.nextInt(), scan.nextInt()};
        int[] exit = {scan.nextInt(), scan.nextInt()};
        while (scan.hasNext()) {
            String[] items = scan.nextLine().split(" ");
            strings.add(items);
        }
        mazeArray = new int[strings.size()][strings.get(0).length];
        for (int i = 0; i < mazeArray.length; i++) {
            for (int j = 0; j < mazeArray[0].length; j++) {
                mazeArray[i][j] = Integer.parseInt(strings.get(i)[j]);
            }
        }

        scan.close();
        return new Maze(mazeArray, x, y, width, height, entrance, exit);
    }
}
