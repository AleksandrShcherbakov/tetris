package figures;

public class RectFigure extends Figure {
    public RectFigure() {
        this.size=2;
        this.figureName="R";
        byte[][]form= new byte[][]{
                {1, 1},
                {1, 1}
        };
        this.figureForm=fillFormWithColor(form,super.getColor());
    }
}
