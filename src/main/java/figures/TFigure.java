package figures;

public class TFigure extends Figure {
    public TFigure() {
        this.size=3;
        this.figureName="T";
        this.figureForm= new byte[][]{
                {0, 1, 0},
                {1, 1, 1},
                {0, 0, 0}
        };
    }
}
