package figures;

public class LFigure extends Figure {
    public LFigure() {
        this.size=3;
        this.figureName="L";
        byte[][]form= new byte[][]{
                {0, 1, 0},
                {0, 1, 0},
                {0, 1, 1}
        };
        this.figureForm=fillFormWithColor(form,super.getColor());
    }
}
