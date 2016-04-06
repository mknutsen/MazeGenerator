package mknutsen.mazegen;

import mknutsen.graphicslibrary.GraphicsComponent;
import mknutsen.graphicslibrary.GraphicsDriver;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * Created by mknutsen on 4/3/16.
 */
public class MazeDisplay extends GraphicsComponent implements MouseMotionListener {

    private final int[] entrance;

    private final int[] exit;
    
    private final Maze maze;

    private boolean started;

    public MazeDisplay(Maze maze) {
        this.maze = maze;
        addMouseMotionListener(this);
        this.entrance = maze.cellToCoordinates(maze.getEntrance()[0], maze.getEntrance()[1]);
        this.exit = maze.cellToCoordinates(maze.getExit()[0], maze.getExit()[1]);
    }


    public static void main(String[] args) {
        new GraphicsDriver(1000, 1000, new MazeDisplay(Maze.mazeGenerator(30, 30, 0, 0, 1000, 1000)),
                new ResultScreen());
        //getClass().getResourceAsStream(mazeLoc)
    }

    @Override
    public void paint(final Graphics g) {
        System.out.println("repaint");
        super.paint(g);
        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.black);
        if (!started) {
            g.drawRect(entrance[1], entrance[0], entrance[3] - entrance[1], entrance[2] - entrance[0]);
        }
        if (started) {
            maze.draw(g);
        }
    }

    @Override
    public void takeParameters(final Object[] obj) {
    }

    @Override
    public void mouseDragged(final MouseEvent e) {

    }

    @Override
    public void mouseMoved(final MouseEvent e) {
        if (!started) {
            if (isMouseInside(e, entrance)) {
                started = true;
                repaint();
            }
        } else {
            if (maze.isWallHere(e)) {
                triggerCallback("failure");

            } else if (isMouseInside(e, exit)) {
                triggerCallback("success");
            }
        }

    }

    private boolean isMouseInside(final MouseEvent e, int[] box) {
        //        System.out.println(e + " " + box);
        return (e.getY() > box[0] && e.getY() < box[2]) && (e.getX() > box[1] && e.getX() < box[3]);
    }
}
