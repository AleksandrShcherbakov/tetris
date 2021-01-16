package figures;

public class IFigure extends Figure {
    public IFigure() {
        this.size=4;
        this.figureName="I";
        byte[][] form= new byte[][]{
                {0, 1, 0, 0},
                {0, 1, 0, 0},
                {0, 1, 0, 0},
                {0, 1, 0, 0}
        };
        this.figureForm=fillFormWithColor(form, super.getColor());
    }

    @Override
    public void turnLeft() {
        if (figureForm[0][1].getIsCube()==1){
            byte[][]temp = new byte[][]{
                    {0, 0, 0, 0},
                    {1, 1, 1, 1},
                    {0, 0, 0, 0},
                    {0, 0, 0, 0}
            };
            this.figureForm=fillFormWithColor(temp, super.getColor());

        } else {
            byte[][] temp = new byte[][]{
                    {0, 1, 0, 0},
                    {0, 1, 0, 0},
                    {0, 1, 0, 0},
                    {0, 1, 0, 0}
            };
            this.figureForm=fillFormWithColor(temp, super.getColor());
        }
    }
}
