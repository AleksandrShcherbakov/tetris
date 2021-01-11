package figures;

public class IFigure extends Figure {
    public IFigure() {
        this.size=4;
        this.figureName="I";
        this.figureForm= new byte[][]{
                {0, 1, 0, 0},
                {0, 1, 0, 0},
                {0, 1, 0, 0},
                {0, 1, 0, 0}
        };
    }

    @Override
    public void turnLeft() {
        if (figureForm[0][1]==1){
            figureForm = new byte[][]{
                    {0, 0, 0, 0},
                    {1, 1, 1, 1},
                    {0, 0, 0, 0},
                    {0, 0, 0, 0}
            };
        } else figureForm = new byte[][]{
                {0, 1, 0, 0},
                {0, 1, 0, 0},
                {0, 1, 0, 0},
                {0, 1, 0, 0}
        };
    }
}
