package figures;

import java.awt.*;

public class Cube {
    private int isCube;
    private Color color;

    public Cube(int isCube, Color color) {
        this.isCube = isCube;
        this.color = color;
    }

    public int getIsCube() {
        return isCube;
    }

    public Color getColor() {
        return color;
    }

    public void setIsCube(int isCube) {
        this.isCube = isCube;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
