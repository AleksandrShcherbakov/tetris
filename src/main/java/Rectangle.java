import figures.Cube;
import figures.Figure;
import figures.IFigure;
import figures.TFigure;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Rectangle extends JPanel {
    private static Thread thread;
    private static Random random = new Random();
    private boolean isLeft = false;
    private boolean isRight = false;
    private boolean isUp = false;
    private boolean isDown = false;
    private static int fieldX=400, fieldY=800;
    private static int figureWidth=40;
    private static int figureHrigth=40;

    private static int startSpeedOfDown=20;
    public static int speedOfDown = startSpeedOfDown;
    private static int level= 1;

    private static Figure currentFigure= Helper.getRandomFigure();
    private static Figure nextFigure=Helper.getRandomFigure();

    private static Object pauseLock = new Object();

    private static int score =0;

    private static Cube[][] gameField;
    static {
        gameField = new Cube[fieldY/figureHrigth+1][fieldX/figureWidth];
        for(int i=0;i<fieldX/figureWidth;i++) {
            for (int j=0; j<fieldY/figureHrigth;j++){
                gameField[j][i]=new Cube(0,null);
            }
            gameField[fieldY/figureHrigth][i]=new Cube(1,Color.BLACK);
        }
        //Helper.printMstrix(gameField);
    }
    private Cube[][] figureField;
    private Cube [][] previousFigure = new Cube[fieldY/figureHrigth+1][fieldX/figureWidth];

    private static boolean fieldIsEmpty=true;

    private static String labelText = "Счет: "+score;
    static JLabel label = new JLabel(labelText);


    Queue<Integer> queue = new ArrayDeque<Integer>();

    int lastCommand;

    static boolean isGameOver;

    static JFrame frame;
    static JPanel pannel;
    static JButton button;

    public static void main(String[] args) {
        frame = new JFrame();
        frame.setSize(700,900);
        frame.setLayout(new BorderLayout());

        pannel=new Rectangle(frame);
        pannel.setSize(fieldX+600, fieldY+500);
        pannel.setLayout(null);


        frame.add(pannel,BorderLayout.CENTER);

        button = new JButton("restart");
        button.setSize(100,30);
        button.setLocation(fieldX+50,0);
        pannel.add(button);
        AbstractAction buttonPressed = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                currentFigure=Helper.getRandomFigure();
                currentPositionX = fieldX/2+figureWidth*(currentFigure.getSize()%2)/2;
                currentPositionY = figureHrigth+ figureHrigth*(currentFigure.getSize()%2)/2;
                gameField = new Cube[fieldY/figureHrigth+1][fieldX/figureWidth];
                for(int i=0;i<fieldX/figureWidth;i++) {
                    for (int j=0; j<fieldY/figureHrigth;j++){
                        gameField[j][i]=new Cube(0,null);
                    }
                    gameField[fieldY/figureHrigth][i]=new Cube(1,Color.BLACK);
                }
                score=0;
                speedOfDown=20;
                labelText="Счет: "+score;
                label.setText(labelText);
                synchronized (pauseLock) {
                    pauseLock.notifyAll();
                }
            }
        };
        button.addActionListener(buttonPressed);
        button.setFocusable(false);

        label.setSize(100,30);
        label.setLocation(fieldX+50,30);
        pannel.add(label);

        frame.setFocusable(true);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }


    public Rectangle(final JFrame frame){

        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_LEFT || e.getKeyCode()==KeyEvent.VK_RIGHT || e.getKeyCode()==KeyEvent.VK_UP){
                    queue.add(e.getKeyCode());
                }
                if (e.getKeyCode()==KeyEvent.VK_DOWN) {
                    isDown=true;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_DOWN) {
                    isDown=false;
                }
                else {
                    if (e.getKeyChar() == 'r') {
                        currentFigure = Helper.getRandomFigure();
                        currentPositionX = fieldX / 2 + figureWidth * (currentFigure.getSize() % 2) / 2;
                        currentPositionY = figureHrigth + figureHrigth * (currentFigure.getSize() % 2) / 2;
                        gameField = new Cube[fieldY / figureHrigth + 1][fieldX / figureWidth];
                        for (int i = 0; i < fieldX / figureWidth; i++) {
                            gameField[fieldY / figureHrigth][i].setIsCube(1);
                        }
                    }
                    //queue.add(e.getKeyCode());
                }
            }
        });



        thread = new MoveThread(this);
        pauseLock=((MoveThread) thread).pauseLock;
        thread.start();
    }

    private static int currentPositionX = fieldX/2+figureWidth*(currentFigure.getSize()%2)/2;
    private static int currentPositionY = figureHrigth+ figureHrigth*(currentFigure.getSize()%2)/2;

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //g.drawRect(0,0,500,500);
        g.setColor(Color.black);
        g.drawRect(0,0,fieldX,fieldY);

            for (int i=0; i<gameField[0].length; i++){
                for (int j=0; j<gameField.length-1; j++){
                    if (gameField[j][i]!=null && gameField[j][i].getIsCube()!=0){
                        g.setColor(gameField[j][i].getColor());
                        g.fillRect(i*figureWidth,j*figureHrigth,figureWidth,figureHrigth);
                        g.setColor(Color.black);
                        g.drawRect(i*figureWidth,j*figureHrigth,figureWidth,figureHrigth);
                    }
                }
            }

        for (int i=0; i<currentFigure.getSize(); i++){
            for (int j=0; j<currentFigure.getSize(); j++){
                if (currentFigure.getFigureForm()[j][i].getIsCube()!=0){
                    g.setColor(currentFigure.getFigureForm()[j][i].getColor());
                    g.fillRect((currentPositionX-currentFigure.getSize()*figureWidth/2)+i*figureWidth,(currentPositionY-(currentFigure.getSize()%2)*(figureHrigth/2)+j*figureHrigth),figureWidth,figureHrigth);
                    g.setColor(Color.black);
                    g.drawRect((currentPositionX-currentFigure.getSize()*figureWidth/2)+i*figureWidth,(currentPositionY-(currentFigure.getSize()%2)*(figureHrigth/2)+j*figureHrigth),figureWidth,figureHrigth);

                }
            }
        }

        for (int i=0; i<nextFigure.getSize(); i++){
            for (int j=0; j<nextFigure.getSize(); j++){
                if (nextFigure.getFigureForm()[j][i].getIsCube()!=0){
                    g.setColor(nextFigure.getFigureForm()[j][i].getColor());
                    g.fillRect((fieldX+50)+i*figureWidth,(100+j*figureHrigth),figureWidth,figureHrigth);
                    g.setColor(Color.black);
                    g.drawRect((fieldX+50)+i*figureWidth,(100+j*figureHrigth),figureWidth,figureHrigth);
                }
            }
        }

        //currentFigure.printFigure();
        //g.fillRect(currentPositionX,currentPositionY,figureWidth,figureHrigth);
    }

    int leftColumn=0;
    int rightColumn=0;

    public void animate() {
        lastCommand=0;
        if (queue.size()>0) {
            int key = queue.remove();
            lastCommand=key;
            queue.clear();
            if (key == KeyEvent.VK_LEFT) isLeft = true;
            if (key == KeyEvent.VK_RIGHT) isRight = true;
            if (key == KeyEvent.VK_UP) isUp = true;
            //if (key == KeyEvent.VK_DOWN) isDown = true;

            if (isLeft) {
                if ((currentPositionX-currentFigure.getSize()*figureWidth/2)>=figureWidth) {
                    currentPositionX = currentPositionX - figureWidth;
                    boolean isPossibleToMove = isPossibleToMove();
                    if (!isPossibleToMove){
                        currentPositionX = currentPositionX + figureWidth;
                    }
                    if (rightColumn>0) {
                        rightColumn--;
                    }
                } else {
                    boolean possibleLeft=true;
                    for (int j=0; j<currentFigure.getSize();j++){
                        if (currentFigure.getFigureForm()[j][leftColumn].getIsCube()!=0){
                            possibleLeft=false;
                            break;
                        }
                    }
                    if (possibleLeft){
                        currentPositionX = currentPositionX - figureWidth;
                        leftColumn++;
                        boolean isPossibleToMove = isPossibleToMove();
                        if (!isPossibleToMove){
                            currentPositionX = currentPositionX + figureWidth;
                            leftColumn--;
                        }
                    }
                }
                lastCommand=0;
            }
            if (isRight) {
                if ((currentPositionX+currentFigure.getSize()*figureWidth/2)<=fieldX-figureWidth) {
                    currentPositionX = currentPositionX + figureWidth;
                    boolean isPossibleToMove = isPossibleToMove();
                    if (!isPossibleToMove){
                        currentPositionX = currentPositionX - figureWidth;
                        queue.clear();
                    }
                    if (leftColumn>0) {
                        leftColumn--;
                    }
                } else {
                    boolean possibleRight=true;
                    for (int j=0; j<currentFigure.getSize();j++){
                        if (currentFigure.getFigureForm()[j][currentFigure.getSize()-1-rightColumn].getIsCube()!=0){
                            possibleRight=false;
                            break;
                        }
                    }
                    if (possibleRight){
                        currentPositionX = currentPositionX + figureWidth;
                        rightColumn++;
                        boolean isPossibleToMove = isPossibleToMove();
                        if (!isPossibleToMove){
                            currentPositionX = currentPositionX - figureWidth;
                            rightColumn--;
                        }

                    }
                }
                lastCommand=0;
            }
            if (isUp) {
                Cube[][]temp=currentFigure.getFigureForm();
                currentFigure.turnLeft();
                boolean isPossibleToMove = isPossibleToMove();
                for (int i=0; i<currentFigure.getSize(); i++){
                    for (int j=0; j<currentFigure.getSize(); j++){
                        if (currentFigure.getFigureForm()[i][j].getIsCube()!=0){
                            if ((currentPositionX-figureWidth)<=0 || (currentPositionX+figureWidth)>=fieldX || !isPossibleToMove){
                                currentFigure.setFigureForm(temp);
                                break;
                            }
                        }
                    }
                }
            }

            isLeft = false;
            isRight = false;
            isUp = false;
            //isDown = false;
        }
        this.repaint();
    }

    private boolean possibleToMoveDown() {

        int currentX = currentPositionX / figureWidth;
        int currenyY = currentPositionY / figureHrigth + currentFigure.getSize() / 2;

        int startX = currentX - currentFigure.getSize() / 2;
        int startY = currenyY - currentFigure.getSize() / 2;



        fillFigureField();

        boolean isPossibleToMoveDown = isPossibleToMove();
        //Helper.printMstrix(figureField);

        if (!isPossibleToMoveDown) {
            Cube[][] temp = new Cube[fieldY / figureHrigth+1][fieldX / figureWidth];
            for(int i=0;i<fieldX/figureWidth;i++) {
                for (int j=0; j<fieldY/figureHrigth;j++){
                    temp[j][i]=new Cube(0,null);
                }
                temp[fieldY/figureHrigth][i]=new Cube(1,Color.BLACK);
            }
            Cube[][] figure;
            if (lastCommand==KeyEvent.VK_LEFT || lastCommand==KeyEvent.VK_RIGHT){
                figure = figureField;
            } else {
                figure = previousFigure;
            }
            for (int i = 0; i < fieldX / figureWidth; i++) {
                for (int j = 0; j <= fieldY / figureHrigth; j++) {
                    if (gameField[j][i].getIsCube() == 1 || figure[j][i].getIsCube() == 1 ) {
                        if (gameField[j][i].getIsCube() == 1){
                            temp[j][i]= gameField[j][i];
                        }else {
                            temp[j][i] = figure[j][i];
                        }
                    } else {
                        temp[j][i]=new Cube(0, null);
                    }
                }
            }
            gameField = temp;
            return isPossibleToMoveDown;
        }
        previousFigure=figureField;
        return isPossibleToMoveDown;
    }


    private class MoveThread extends Thread{
        Rectangle rectangle;
        private final Object pauseLock = new Object();

        public MoveThread(Rectangle rectangle) {
            super("MoveThread");
            this.rectangle = rectangle;
        }

        public void run(){
            int downTime=0;

            while(true) {
                level=score/1000+1;
                speedOfDown=startSpeedOfDown/level;
                rectangle.animate();
                try {
                    Thread.sleep(50);
                    if (isDown || downTime==speedOfDown) {
                        fillFigureField();
                        previousFigure=figureField;
                        currentPositionY = currentPositionY + figureHrigth;
                        downTime = 0;
                        if (!possibleToMoveDown()){
                            checkFullRow();
                            if (checkGameOver()){
                                label.setText("Game Over");
                                synchronized (pauseLock){pauseLock.wait();}
                            }
                            currentFigure=nextFigure;
                            nextFigure=Helper.getRandomFigure();
                            rightColumn=0;
                            leftColumn=0;
                            currentPositionX = fieldX/2+figureWidth*(currentFigure.getSize()%2)/2;
                            currentPositionY = figureHrigth+ figureHrigth*(currentFigure.getSize()%2)/2;
                        }
                    }
                    downTime++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean checkGameOver() {
        for (int i = 0; i < fieldX / figureWidth; i++) {
            if (gameField[1][i].getIsCube() == 1) {
                return true;
            }
        }
        return false;
    }

    private boolean checkFullRow() {
        boolean isFull=false;

        List<Integer>rows=new ArrayList<Integer>();
        for (int j = 0; j < fieldY / figureHrigth; j++) {
            int yes=1;
            for (int i = 0; i < fieldX / figureWidth; i++) {
                yes = yes*gameField[j][i].getIsCube();
            }
            if (yes==1){
                rows.add(j);
            }
        }
        if (rows.size()>0){
            for (int m : rows){
                Cube[][]temp = new Cube[fieldY/figureHrigth+1][fieldX/figureWidth];
                for(int i=0;i<fieldX/figureWidth;i++) {
                    for (int j=0; j<fieldY/figureHrigth;j++){
                        temp[j][i]=new Cube(0,null);
                    }
                    temp[fieldY/figureHrigth][i]=new Cube(1,Color.BLACK);
                }
                for (int k=0;k<m;k++){
                    for (int s = 0; s < fieldX / figureWidth;s++){
                        temp[k+1][s]=gameField[k][s];
                    }
                }
                for (int k=m+1;k<fieldY / figureHrigth;k++){
                    for (int s = 0; s < fieldX / figureWidth;s++){
                        temp[k][s]=gameField[k][s];
                    }
                }
                gameField=temp;
            }
            score=score+100*rows.size()*rows.size();
            labelText="Счет: "+score;
            label.setText(labelText);
        }
        return isFull;
    }

    private void fillFigureField() {
        int currentX = currentPositionX / figureWidth;
        int currenyY = currentPositionY / figureHrigth + currentFigure.getSize() / 2;

        int startX = currentX - currentFigure.getSize() / 2;
        int startY = currenyY - currentFigure.getSize() / 2;

        figureField = new Cube[fieldY / figureHrigth+1][fieldX / figureWidth];

        for (int i=0; i<fieldX/figureWidth; i++){
            for(int j=0; j<fieldY/figureHrigth+1; j++){
                figureField[j][i]=new Cube(0, null);
            }
        }

        for (int i = startX; i < startX + currentFigure.getSize(); i++) {
            for (int j = startY; j < startY + currentFigure.getSize(); j++) {
                if (i >= 0 && i < fieldX / figureWidth && j > 0 && j <= fieldY / figureHrigth) {
                    figureField[j][i] = currentFigure.getFigureForm()[j - startY][i - startX];
                }
            }
        }
    }

    private boolean isPossibleToMove(){
        int currentX = currentPositionX / figureWidth;
        int currenyY = currentPositionY / figureHrigth + currentFigure.getSize() / 2;

        int startX = currentX - currentFigure.getSize() / 2;
        int startY = currenyY - currentFigure.getSize() / 2;

        boolean isPossibleToMove = true;

        fillFigureField();

        for (int i = startX; i < startX + currentFigure.getSize(); i++) {
            for (int j = startY; j < startY + currentFigure.getSize(); j++) {
                if (i >= 0 && i < fieldX / figureWidth && j > 0 && j < fieldY / figureHrigth+1) {
                    if (figureField[j][i].getIsCube() * gameField[j][i].getIsCube() == 1) {
                        isPossibleToMove = false;
                        break;
                    }
                }
            }
        }
        return isPossibleToMove;
    }
}
