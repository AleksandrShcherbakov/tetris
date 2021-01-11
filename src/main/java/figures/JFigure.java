package figures;

public class JFigure extends Figure {
    public JFigure() {
        this.size=3;
        this.figureName="J";
        this.figureForm= new byte[][]{
                {0, 1, 0},
                {0, 1, 0},
                {1, 1, 0}
        };
    }
}
