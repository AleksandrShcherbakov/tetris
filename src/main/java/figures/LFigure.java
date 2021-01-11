package figures;

public class LFigure extends Figure {
    public LFigure() {
        this.size=3;
        this.figureName="L";
        this.figureForm= new byte[][]{
                {0, 1, 0},
                {0, 1, 0},
                {0, 1, 1}
        };
    }
}
