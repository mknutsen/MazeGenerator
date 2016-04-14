package mknutsen.mazegen;

import mknutsen.graphicslibrary.graphicsobject.RectangleGraphicObject;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Hashtable;

/**
 * Created by mknutsen on 4/3/16.
 */
public class Maze extends RectangleGraphicObject {


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
        if (row > maze.length || row < 0 || col > maze[0].length || col < 0) {
            return false;
        } else {
            return maze[row][col] == 1;
        }
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
