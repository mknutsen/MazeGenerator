import mknutsen.graphicslibrary.graphicsobject.RectangleGraphicObject;

import java.awt.*;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

/**
 * Created by mknutsen on 4/3/16.
 */
public class Maze extends RectangleGraphicObject {

    private int[][] maze;

    private Hashtable<Integer, Wall> walls;

    /**
     * @param maze
     *         maze to turn into a graphic object
     */
    public Maze(final int[][] maze, int x, int y, int width, int height) {
        super(x, y, width, height, false, null);
        this.maze = maze;
        double rowWidth = width / maze.length, rowHeight = height / maze[0].length;
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
    }

    public final static Maze mazeGenerator(int rows, int columns, int x, int y, int width, int height, int threshold) {
        int[][] maze = new int[rows][columns];
        recursiveMazeGenerator(maze, 0, 0, rows, columns, threshold);
        return new Maze(maze, x, y, width, height);
    }

    private static int[][] recursiveMazeGenerator(int[][] maze, int x1, int y1, int x2, int y2, int threshold) {
        //        System.out.println(String.format("%d, %d -- %d, %d -- %d", x1, y1, x2, y2, threshold));
        if (x2 - x1 > threshold && y2 - y1 > threshold) {
            int x = x1 + (int) (Math.random() * (x2 - x1 - 2)) + 1;
            int y = y1 + (int) (Math.random() * (y2 - y1 - 2)) + 1;
            for (int i = x1; i < x2; i++) {
                maze[i][y] = 1;
            }
            maze[x1 + (int) (Math.random() * (x - x1 - 2)) + 1][y] = 0;
            maze[x + (int) (Math.random() * (x2 - x - 2)) + 1][y] = 0;
            for (int i = y1; i < y2; i++) {
                maze[x][i] = 1;
            }
            maze[x][y1 + (int) (Math.random() * (y - y1 - 2)) + 1] = 0;
            maze[x][y + (int) (Math.random() * (y2 - y - 2)) + 1] = 0;
            recursiveMazeGenerator(maze, x1, y1, x, y, threshold);
            recursiveMazeGenerator(maze, x, y1, x2, y, threshold);
            recursiveMazeGenerator(maze, x1, y, x, y2, threshold);
            recursiveMazeGenerator(maze, x, y, x2, y2, threshold);
        }
        return maze;
    }

    public final static Maze mazeFromTextFile(InputStream mazeStream, int x, int y, int width, int height) {
        int[][] mazeArray;
        Scanner scan = new Scanner(mazeStream);
        ArrayList<String[]> strings = new ArrayList<>();
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
        return new Maze(mazeArray, x, y, width, height);
    }

    @Override
    public final boolean draw(final Graphics gr) {
        walls.forEach((key, value) -> value.draw(gr));
        return true;
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
