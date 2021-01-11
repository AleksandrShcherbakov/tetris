import figures.*;
import org.w3c.dom.css.Rect;

import java.awt.*;
import java.util.Map;
import java.util.Random;

public class Helper {



    public static void printMstrix(int[][]matrix){
        for (int i=0;i<matrix.length;i++){
            for (int j=0;j<matrix[0].length;j++){
                System.out.print(matrix[i][j]);
            }
            System.out.println("");
        }
    }

    public static Figure getRandomFigure(){
        Random random = new Random();
        int number = random.nextInt(6);
        switch (number){
            case 0:
                return new IFigure();
            case 1:
                return new JFigure();
            case 2:
                return new LFigure();
            case 3:
                return new RectFigure();
            case 4:
                return new SFigure();
            case 5:
                return new TFigure();
            case 6:
                return new ZFigure();
        }
        return new ZFigure();
    }


}
