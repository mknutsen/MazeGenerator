import mknutsen.graphicslibrary.graphicsobject.RectangleGraphicObject;

import java.awt.*;
import java.util.Hashtable;

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

    @Override
    public boolean draw(final Graphics gr) {
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
