
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener{

    int boardSize = 440;
    int bodySize = 20;
    int wallThickness = 20;
    Image backgroundImg;
    Image fruitImg;
    Image snakeBodyImg;
    Image snakeHeadImg;
    String headPosition;
    int minX = wallThickness;
    int minY = wallThickness;
    int maxX = boardSize - wallThickness;
    int maxY = boardSize - wallThickness;


    //game logic 
    int velocityX = 0;
    int velocityY = 0;
    Point fruit;
    ArrayList<Point> snakeBody = new ArrayList<>();
    Timer gameLoop;
    boolean keysEnabled = false;
    boolean paused = true;


    SnakeGame(){
        setPreferredSize(new Dimension(boardSize, boardSize));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        backgroundImg = new ImageIcon(getClass().getResource("/images/background.png")).getImage();
        fruitImg = new ImageIcon(getClass().getResource("/images/fruit.png")).getImage();
        snakeBodyImg = new ImageIcon(getClass().getResource("/images/body.png")).getImage();
        headPosition = "/images/snake_head_right.png";

        //timer
        gameLoop = new Timer(1000/6, this);
        gameLoop.start();

        gameStart();
    }

    public void gameStart(){
        snakeBody.add(new Point(20, 20));

        generateNewFruit();
    }

    public void generateNewFruit(){
        int x = 20 + ((int)(Math.random() * 20)) * 20;
        int y = 20 + ((int)(Math.random() * 20)) * 20;
        
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
        keysEnabled = false;
    } 
    
    public boolean collision(){
        Point head = snakeBody.get(0);
        for(int i = 0; i < snakeBody.size(); i++){
            if(head.equals(snakeBody.get(i)) && i != 0){
                return true;
            }
            else if(head.x >= maxX ||
                    head.x < minX ||
                    head.y >= maxY ||
                    head.y < minY ){
                return true;
            }
        }
        return false;
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        //background
        g.drawImage(backgroundImg, 0, 0, 440, 440, null);
        
        //snake head
        snakeHeadImg = new ImageIcon(getClass().getResource(headPosition)).getImage();
        Point head = snakeBody.get(0);
        g.drawImage(snakeHeadImg, head.x, head.y, bodySize, bodySize, null);

        //snake body
        for(int i = 1; i < snakeBody.size(); i++){
            g.drawImage(snakeBodyImg, snakeBody.get(i).x, snakeBody.get(i).y, bodySize, bodySize, null);
        }
        
        //fruit
        g.drawImage(fruitImg, fruit.x, fruit.y, bodySize, bodySize, null);

        //walls
        // g.setColor(Color.green);
        // g.fillRect(0, 0, boardSize, wallThickness); //upper wall
        // g.fillRect(0, boardSize - wallThickness, boardSize, wallThickness); //lower wall
        // g.fillRect(0, 0, wallThickness, boardSize); //left wall
        // g.fillRect(boardSize - wallThickness, 0, wallThickness, boardSize); //right wall
        //walls not required cuz the background image already contains the design for walls which are 20 px thick
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(velocityX != 0 || velocityY != 0){
            move();
        }
        if(collision()){
            gameOver();
           
        }else{
            repaint();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        
        if(keyCode == KeyEvent.VK_UP && velocityY == 0 && keysEnabled){
            velocityY = -20;
            velocityX = 0;
            headPosition = "/images/snake_head_up.png";
        }else if(keyCode == KeyEvent.VK_DOWN && velocityY == 0 && keysEnabled){
            velocityY = 20;
            velocityX = 0;
            headPosition = "/images/snake_head_down.png";
        }else if(keyCode == KeyEvent.VK_LEFT && velocityX == 0 && keysEnabled){
            velocityX = -20;
            velocityY = 0;
            headPosition = "/images/snake_head_left.png";
        }else if(keyCode == KeyEvent.VK_RIGHT && velocityX == 0 && keysEnabled){
            velocityX = 20;
            velocityY = 0;
            headPosition = "/images/snake_head_right.png";
        }else if(keyCode == KeyEvent.VK_SPACE && !keysEnabled && !paused){
            paused = true;
            snakeBody.clear();
            headPosition = "/images/snake_head_right.png";
            gameStart();
        }else if(keyCode == KeyEvent.VK_SPACE && !keysEnabled && paused){
            keysEnabled = true;
            paused = false;
            velocityX = 20;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}