package figures;

public class SFigure extends Figure {
    public SFigure() {
        this.size=3;
        this.figureName="S";
        this.figureForm= new byte[][]{
                {0, 1, 1},
                {1, 1, 0},
                {0, 0, 0}
        };
    }
}
