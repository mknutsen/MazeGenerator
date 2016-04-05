import mknutsen.graphicslibrary.graphicsobject.RectangleGraphicObject;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by mknutsen on 4/3/16.
 */
public class Maze extends RectangleGraphicObject {

    private static Random rand;

    static {
        rand = new Random();
    }

    private final int[] entrance;

    private final int[] exit;

    private final int x;

    private final int y;

    private final int rowWidth;

    private final int rowHeight;

    private int[][] maze;

    private Hashtable<Integer, Wall> walls;

    /**
     * @param maze
     *         maze to turn into a graphic object
     */
    public Maze(final int[][] maze, int x, int y, int width, int height, int[] entrance, int[] exit) {
        super(x, y, width, height, false, null);
        this.x = x;
        this.y = y;
        this.maze = maze;
        this.rowWidth = width / maze.length;
        this.rowHeight = height / maze[0].length;
        walls = new Hashtable<>();
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (maze[i][j] == 1) {
                    walls.put                                  (i * 100 + j,
                            new Wall((int) (x + j * rowHeight), (int) (y + i * rowWidth), (int) rowHeight,
                                    (int) rowWidth, Color.BLUE));
                }
            }
        }
        this.entrance = entrance;
        this.exit = exit;
    }

    public final static Maze mazeGenerator(int rows, int columns, int x, int y, int width, int height) {
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
            entrance[0] = 0;
            entrance[1] = rand.nextInt(rows - 2) + 1;
        } else {
            entrance[0] = rand.nextInt(columns - 2) + 1;
            entrance[1] = 0;
        }
        if (Math.random() > .5) {
            exit[0] = 0;
            exit[1] = rand.nextInt(rows - 2) + 1;
        } else {
            exit[0] = rand.nextInt(columns - 2) + 1;
            exit[1] = 0;
        }
        maze[entrance[0]][entrance[1]] = 0;
        maze[exit[0]][exit[1]] = 0;
        recursiveMazeGenerator(maze, 1, 1, rows - 1, columns - 1);
        return new Maze(maze, x, y, width, height, entrance, exit);
    }

    private static int[][] recursiveMazeGenerator(int[][] maze, int x1, int y1, int x2, int y2) {
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
            recursiveMazeGenerator(maze, x1, y1, x, y2);
            recursiveMazeGenerator(maze, x + 1, y1, x2, y2);
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
            recursiveMazeGenerator(maze, x1, y1, x2, y);
            recursiveMazeGenerator(maze, x1, y + 1, x2, y2);

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

    public int[] cellToCoordinates(int row, int col) {
        int[] coordinates = new int[4];
        coordinates[0] = (x + row * rowHeight);
        coordinates[1] = (y + col * rowWidth);
        coordinates[2] = coordinates[0] + rowHeight;
        coordinates[3] = coordinates[1] + rowWidth;
        return coordinates;
    }

    @Override
    public final boolean draw(final Graphics gr) {
        walls.forEach((key, value) -> value.draw(gr));
        return true;
    }

    public int[] getEntrance() {
        return entrance;
    }

    public int[] getExit() {
        return exit;
    }

    public boolean isWallHere(final MouseEvent e) {
        int col = (e.getX() - y) / rowHeight;
        int row = (e.getY() - x) / rowWidth;
        //        System.out.println(maze[row][col] == 1);
        return maze[row][col] == 1;
    }

    private static class Wall extends RectangleGraphicObject {

        private int length, width;

        private Color color;

        /**
         * @param x
         *         x coordinate for top left
         * @param y
         *         y coordinate for top left
         * @param length
         *         length (x)
         * @param width
         *         width (y)
         * @param color
         *         color
         */
        public Wall(final int x, final int y, final int length, final int width, final Color color) {
            super(x, y, length, width, false, null);
            this.length = length;
            this.width = width;
            this.color = color;
        }

        @Override
        public boolean draw(final Graphics gr) {
            gr.setColor(color);
            gr.fillRect(getX(), getY(), length, width);
            gr.setColor(Color.black);
            gr.drawRect(getX(), getY(), length, width);
            return true;
        }
    }
}
