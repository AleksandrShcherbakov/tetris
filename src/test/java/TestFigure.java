import figures.TFigure;
import org.junit.jupiter.api.Test;

public class TestFigure {
    @Test
    public void testFigure(){
        TFigure tFigure = new TFigure();
        tFigure.printFigure();
        tFigure.turnRight();
        tFigure.printFigure();
        tFigure.turnLeft();
        tFigure.printFigure();
    }
}
