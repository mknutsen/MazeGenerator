package mknutsen.mazegen;

/**
 * Created by mknutsen on 4/7/16.
 */
public final class MazeConfigFile {

    public static final int PIXEL_WIDTH = 1000;

    public static final int PIXEL_HEIGHT = 1000;

    public static final int ROW_NUM = 50;

    public static final int COLUMN_NUM = 50;

    public static final GenerationAlgorithm ALG = GenerationAlgorithm.RECURSIVE_NEAT;

    //    public static final
    private MazeConfigFile() {

    }


    public enum GenerationAlgorithm {
        RECURSIVE_RANDOM(), RECURSIVE_NEAT();
    }
    
}
