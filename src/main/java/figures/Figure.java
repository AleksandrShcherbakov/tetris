package figures;

import java.awt.*;
import java.util.Random;


public class Figure {

    private static Random randomColor = new Random();

    int size;

    String figureName;

    Cube[][] figureForm;

    private Color color;

    public Figure() {
        color= getRandomColor();
    }

    public void turnRight() {
        int SIDE = size;
        Cube[][] rezult = new Cube[SIDE][SIDE];

        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                rezult[i][j] = figureForm[SIDE - j - 1][i];
            }
        }
        figureForm=rezult;
    }

    public void turnLeft() {
        int SIDE = size;
        Cube[][] rezult = new Cube[SIDE][SIDE];

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

    public Cube[][] getFigureForm() {
        return figureForm;
    }

    public void setFigureForm(Cube[][] figureForm) {
        this.figureForm = figureForm;
    }

    public Color getColor() {
        return color;
    }

    private static Color getRandomColor(){
        int r = randomColor.nextInt(256);
        int g = randomColor.nextInt(256);
        int b = randomColor.nextInt(256);

        return new Color(r, g, b);
    }

    static Cube[][] fillForm(byte[][] form){
        Cube emptyCube = new Cube(0,null);
        Cube fillCube = new Cube(1, getRandomColor());
        Cube[][]cube = new Cube[form.length][form.length];
        for (int i=0; i<form.length;i++){
            for (int j=0; j<form.length; j++){
                if (form[j][i]==0){
                    cube[j][i]=emptyCube;
                } else {
                    cube[j][i]=fillCube;
                }
            }
        }
        return cube;
    }
}
