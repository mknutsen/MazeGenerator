package mknutsen.mazegen;

import mknutsen.graphicslibrary.GraphicsComponent;
import mknutsen.graphicslibrary.GraphicsDriver;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Created by mknutsen on 4/3/16.
 */
public class MazeDisplay extends GraphicsComponent implements MouseMotionListener, MouseListener {

    private int[] entrance;

    private int[] exit;

    private Maze maze;

    private long startTime;

    private boolean started;

    public MazeDisplay(Maze maze) {
        this();
        this.maze = maze;
        this.entrance = maze.cellToCoordinates(maze.getEntrance()[0], maze.getEntrance()[1]);
        this.exit = maze.cellToCoordinates(maze.getExit()[0], maze.getExit()[1]);
    }

    public MazeDisplay() {
        maze = null;
        addMouseMotionListener(this);

        addMouseListener(this);

    }


    public static void main(String[] args) {
        new GraphicsDriver(MazeConfigFile.PIXEL_WIDTH + 300, MazeConfigFile.PIXEL_HEIGHT, new MazeStartScreen(),
                new MazeDisplay(), new ResultScreen());
        //getClass().getResourceAsStream(mazeLoc)
    }

    @Override
    public void paint(final Graphics g) {
        super.paint(g);
        if (maze != null) {
            g.setColor(Color.white);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.black);
            if (!started) {
                g.drawRect(entrance[1], entrance[0], entrance[3] - entrance[1], entrance[2] - entrance[0]);
            }
            if (started) {
                maze.draw(g);
                g.drawString((System.currentTimeMillis() - startTime) / 1000 + "", 1000, 100);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            repaint();
        }
    }

    @Override
    public void takeParameters(final Object[] obj) {
        if (obj[0] instanceof MazeConfigFile.GenerationAlgorithm) {
            MazeConfigFile.ALG = (MazeConfigFile.GenerationAlgorithm) obj[0];
        }
        maze = MazeGeneration.recursiveRandomMazeGenerator(MazeConfigFile.COLUMN_NUM, MazeConfigFile.ROW_NUM, 0, 0,
                MazeConfigFile.PIXEL_WIDTH, MazeConfigFile.PIXEL_HEIGHT);

        this.entrance = maze.cellToCoordinates(maze.getEntrance()[0], maze.getEntrance()[1]);
        this.exit = maze.cellToCoordinates(maze.getExit()[0], maze.getExit()[1]);
        repaint();

    }

    @Override
    public void mouseDragged(final MouseEvent e) {

    }


    @Override
    public void mouseMoved(final MouseEvent e) {
        if (!started) {
            if (isMouseInside(e, entrance)) {
                started = true;
                startTime = System.currentTimeMillis();
                repaint();
            }
        } else {
            if (maze.isWallHere(e)) {
                triggerCallback("failure", System.currentTimeMillis() - startTime);

            } else if (isMouseInside(e, exit)) {
                triggerCallback("success", System.currentTimeMillis() - startTime);
            } else if (e.getX() > MazeConfigFile.PIXEL_WIDTH) {
                triggerCallback("failure", System.currentTimeMillis() - startTime);
            }
        }

    }

    private boolean isMouseInside(final MouseEvent e, int[] box) {
        //        System.out.println(e + " " + box);
        return (e.getY() > box[0] && e.getY() < box[2]) && (e.getX() > box[1] && e.getX() < box[3]);
    }

    @Override
    public void mouseClicked(final MouseEvent e) {

    }

    @Override
    public void mousePressed(final MouseEvent e) {

    }

    @Override
    public void mouseReleased(final MouseEvent e) {

    }

    @Override
    public void mouseEntered(final MouseEvent e) {

    }

    @Override
    public void mouseExited(final MouseEvent e) {
        if (started) {
            triggerCallback("failure", System.currentTimeMillis() - startTime);
        }
    }
}
