
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener{

    int boardSize = 400;
    int bodySize = 20;


    //game logic 
    int velocityX = 0;
    int velocityY = 0;
    Point fruit;
    ArrayList<Point> snakeBody = new ArrayList<>();
    Timer gameLoop;


    SnakeGame(){
        setPreferredSize(new Dimension(boardSize, boardSize));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        //timer
        gameLoop = new Timer(1000/6, this);
        gameLoop.start();

        startGame();

    }

    public void startGame(){
        snakeBody.add(new Point(0,0));

        generateNewFruit();
    }

    public void generateNewFruit(){
        int x = ((int)(Math.random() * 20)) * 20;
        int y = ((int)(Math.random() * 20)) * 20;
        
        fruit = new Point(x, y);
    }
    
    public void move(){
        Point head = snakeBody.get(0);

        Point newHead = new Point(head.x + velocityX, head.y + velocityY);
        snakeBody.add(0, newHead);

        if(eaten()){
            generateNewFruit();
        }else{
            snakeBody.remove(snakeBody.size()-1);
        }
    }
    
    public boolean eaten(){
        return snakeBody.get(0).equals(fruit);
    }

    public void gameOver(){
        velocityX = 0;
        velocityY = 0;
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public boolean collision(){
        Point head = snakeBody.get(0);
        for(int i = 1; i < snakeBody.size(); i++){
            if(head.equals(snakeBody.get(i))){
                return true;
            }
        }
        return false;
    }

    public void draw(Graphics g){

        g.setColor(Color.WHITE);
        for(Point p : snakeBody){
            g.fillRect(p.x, p.y, bodySize, bodySize);
        }

        g.setColor(Color.RED);
        g.fillRect(fruit.x, fruit.y, bodySize, bodySize);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        if(collision()){
            gameOver();
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if(keyCode == KeyEvent.VK_UP && velocityY == 0){
            velocityY = -20;
            velocityX = 0;
        }else if(keyCode == KeyEvent.VK_DOWN && velocityY == 0){
            velocityY = 20;
            velocityX = 0;
        }else if(keyCode == KeyEvent.VK_LEFT && velocityX == 0){
            velocityX = -20;
            velocityY = 0;
        }else if(keyCode == KeyEvent.VK_RIGHT && velocityX == 0){
            velocityX = 20;
            velocityY = 0;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}