import mknutsen.graphicslibrary.GraphicsComponent;
import mknutsen.graphicslibrary.GraphicsDriver;

import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by mknutsen on 4/3/16.
 */
public class MazeDisplay extends GraphicsComponent {

    private int[][] mazeArray;

    private Maze maze;

    public MazeDisplay(String mazeLoc) {

        Scanner scan = new Scanner(getClass().getResourceAsStream(mazeLoc));
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
        maze = new Maze(mazeArray, 0, 0, 700, 700);

    }


    public static void main(String[] args) {
        new GraphicsDriver(700, 700, new MazeDisplay("graph.txt"));
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
