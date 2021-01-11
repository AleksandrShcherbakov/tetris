package figures;

public class ZFigure extends Figure {
    public ZFigure() {
        this.size=3;
        this.figureName="Z";
        byte[][]form= new byte[][]{
                {1, 1, 0},
                {0, 1, 1},
                {0, 0, 0}
        };
        this.figureForm=fillForm(form);
    }
}
