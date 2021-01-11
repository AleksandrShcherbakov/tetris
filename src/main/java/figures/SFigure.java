package figures;

public class SFigure extends Figure {
    public SFigure() {
        this.size=3;
        this.figureName="S";
        byte[][] form= new byte[][]{
                {0, 1, 1},
                {1, 1, 0},
                {0, 0, 0}
        };
        this.figureForm=fillForm(form);
    }
}
