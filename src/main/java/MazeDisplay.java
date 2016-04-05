import mknutsen.graphicslibrary.GraphicsComponent;
import mknutsen.graphicslibrary.GraphicsDriver;

import java.awt.*;

/**
 * Created by mknutsen on 4/3/16.
 */
public class MazeDisplay extends GraphicsComponent {

    private Maze maze;

    public MazeDisplay(Maze maze) {
        this.maze = maze;
    }


    public static void main(String[] args) {
        new GraphicsDriver(700, 700, new MazeDisplay(Maze.mazeGenerator(32, 32, 0, 0, 700, 700)));
        //getClass().getResourceAsStream(mazeLoc)
    }

    @Override
    public void paint(final Graphics g) {
        super.paint(g);
        maze.draw(g);
    }

    @Override
    public void takeParameters(final Object[] obj) {
    }
}
