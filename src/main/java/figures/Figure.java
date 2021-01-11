package figures;

public class Figure {

    int size;

    String figureName;

    byte[][] figureForm;

    public void turnRight() {
        int SIDE = size;
        byte[][] rezult = new byte[SIDE][SIDE];

        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                rezult[i][j] = figureForm[SIDE - j - 1][i];
            }
        }
        figureForm=rezult;
    }

    public void turnLeft() {
        int SIDE = size;
        byte[][] rezult = new byte[SIDE][SIDE];

        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                rezult[i][j] = figureForm[j][SIDE - i - 1];
            }
        }
        figureForm=rezult;
    }

    public void printFigure(){
        for (int i=0;i<figureForm.length;i++){
            for (int j=0;j<figureForm.length;j++){
                System.out.print(figureForm[i][j]);
            }
            System.out.println("");
        }
    }

    public int getSize() {
        return size;
    }

    public String getFigureName() {
        return figureName;
    }

    public byte[][] getFigureForm() {
        return figureForm;
    }

    public void setFigureForm(byte[][] figureForm) {
        this.figureForm = figureForm;
    }
}
