package figures;

public class JFigure extends Figure {
    public JFigure() {
        this.size=3;
        this.figureName="J";
        byte[][] form= new byte[][]{
                {0, 1, 0},
                {0, 1, 0},
                {1, 1, 0}
        };
        this.figureForm=fillFormWithColor(form,super.getColor());
    }
}
