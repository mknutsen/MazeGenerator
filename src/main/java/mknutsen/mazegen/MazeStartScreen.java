package mknutsen.mazegen;

import mknutsen.graphicslibrary.GraphicsComponent;
import mknutsen.graphicslibrary.GraphicsHelperFunctions;
import mknutsen.graphicslibrary.graphicsobject.Button;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by mknutsen on 10/20/16.
 */
public class MazeStartScreen extends GraphicsComponent implements MouseListener {

    private mknutsen.graphicslibrary.graphicsobject.Button neat = new Button(300, 300, 100, 50, "Neat"), random =
            new Button(500, 300, 100, 50, "Random");

    public MazeStartScreen() {
        addMouseListener(this);
    }

    @Override
    public void paint(final Graphics g) {
        super.paint(g);
        GraphicsHelperFunctions.drawCenteredString(g, "Select Neat or Recursive to start. On clicking, a box will " +
                        "appear on the screen. Move your mouse to that box to begin the maze.", 100,
                MazeConfigFile.PIXEL_WIDTH);
        neat.draw(g);
        random.draw(g);
    }

    @Override
    public void takeParameters(final Object[] obj) {
    }

    @Override
    public void mouseClicked(final MouseEvent e) {
        if (neat.isInside(e)) {
            triggerCallback(MazeConfigFile.GenerationAlgorithm.RECURSIVE_NEAT);
        } else if (random.isInside(e)) {
            triggerCallback(MazeConfigFile.GenerationAlgorithm.RECURSIVE_RANDOM);
        }
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

    }
}