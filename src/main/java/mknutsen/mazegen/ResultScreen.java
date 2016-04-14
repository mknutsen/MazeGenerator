package mknutsen.mazegen;

import mknutsen.graphicslibrary.GraphicsComponent;

import java.awt.*;

/**
 * Created by mknutsen on 4/5/16.
 */
public class ResultScreen extends GraphicsComponent {
    
    private String message;

    private long time;

    @Override
    public void paint(final Graphics g) {
        super.paint(g);
        g.drawString(message + " " + time / 1000, 100, 100);
    }

    @Override
    public void takeParameters(final Object[] obj) {
        this.message = (String) obj[0];
        this.time = (long) obj[1];
    }
}
