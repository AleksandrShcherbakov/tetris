package figures;

public class ZFigure extends Figure {
    public ZFigure() {
        this.size=3;
        this.figureName="Z";
        this.figureForm= new byte[][]{
                {1, 1, 0},
                {0, 1, 1},
                {0, 0, 0}
        };
    }
}
