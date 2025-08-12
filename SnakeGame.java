
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
    Image head_up_img;
    Image head_down_img;
    Image head_left_img;
    Image head_right_img;
    Image body_vertical_img;
    Image body_horizontal_img;
    Image tail_up_img;
    Image tail_down_img;
    Image tail_right_img;
    Image tail_left_img;
    Image body_up_left_img;
    Image body_up_right_img;
    Image body_down_right_img;
    Image body_down_left_img;
    Image headPositionImg;
    Image tailPositionImg;
    Image bodyPositionImg;
    
    //game logic 
    int velocityX = 0;
    int velocityY = 0;
    int minX = wallThickness;
    int minY = wallThickness;
    int maxX = boardSize - wallThickness ;
    int maxY = boardSize - wallThickness;
    ArrayList<Point> snakeBody = new ArrayList<>();
    boolean keysEnabled = false;
    boolean showScore = true;
    boolean gameOver = true;
    int score = 0;
    Point fruit;
    Timer gameLoop;


    SnakeGame(){
        setPreferredSize(new Dimension(boardSize, boardSize));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        backgroundImg = new ImageIcon(getClass().getResource("/images/background.png")).getImage();

        fruitImg = new ImageIcon(getClass().getResource("/images/fruit.png")).getImage();

        head_up_img = new ImageIcon(getClass().getResource("/images/head_up.png")).getImage();
        head_down_img = new ImageIcon(getClass().getResource("/images/head_down.png")).getImage();
        head_right_img = new ImageIcon(getClass().getResource("/images/head_right.png")).getImage();
        head_left_img = new ImageIcon(getClass().getResource("/images/head_left.png")).getImage();

        body_vertical_img = new ImageIcon(getClass().getResource("/images/body_vertical.png")).getImage();
        body_horizontal_img = new ImageIcon(getClass().getResource("/images/body_horizontal.png")).getImage();

        body_up_right_img = new ImageIcon(getClass().getResource("/images/body_up_right.png")).getImage();
        body_up_left_img = new ImageIcon(getClass().getResource("/images/body_up_left.png")).getImage();
        body_down_right_img = new ImageIcon(getClass().getResource("/images/body_down_right.png")).getImage();
        body_down_left_img = new ImageIcon(getClass().getResource("/images/body_down_left.png")).getImage();

        tail_left_img = new ImageIcon(getClass().getResource("/images/tail_left.png")).getImage();
        tail_right_img = new ImageIcon(getClass().getResource("/images/tail_right.png")).getImage();
        tail_up_img = new ImageIcon(getClass().getResource("/images/tail_up.png")).getImage();
        tail_down_img = new ImageIcon(getClass().getResource("/images/tail_down.png")).getImage();

        //timer
        gameLoop = new Timer(1000/6, this);

        gameStart();
    }

    public void gameStart(){
        snakeBody.add(new Point(20, wallThickness));
        headPositionImg = head_right_img;

        gameLoop.start();

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
        
        if(collision(newHead)){
            gameOver();
            return;
        }
        
        snakeBody.add(0, newHead);

        if(eaten()){
            score++;
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
        showScore = false;
        gameLoop.stop();
    } 
    
    public boolean collision(Point head){

        for(int i = 0; i < snakeBody.size(); i++){
            if(head.equals(snakeBody.get(i)) && i != 0){
                return true;
            }
        }
        if( head.x >= maxX ||
            head.x < minX  ||
            head.y >= maxY ||
            head.y < minY  ){
            
            return true;
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
        Point head = snakeBody.get(0);
        g.drawImage(headPositionImg, head.x, head.y, bodySize, bodySize, null);
        
        //snake body
        for(int i = 1; i < snakeBody.size(); i++){
            Point prev = snakeBody.get(i-1);
            Point current = snakeBody.get(i);
            Point next = (i < snakeBody.size()-1) ? snakeBody.get(i+1) : null;

            if(next == null){
                drawTail(g, prev, current);
            }else{
                drawBody(g, prev, current, next);
            }
        }
        
        //fruit
        g.drawImage(fruitImg, fruit.x, fruit.y, bodySize, bodySize, null);
        
        //score board
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 10));
        if(showScore){
            g.drawString("Score: " + String.valueOf(score), 10, 10);
        }else{
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("Game Over", 120, 200);
            g.drawString("Total Score: " + String.valueOf(score), 120, 240);
        }
        if(gameOver && !keysEnabled){
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("Press Space to Continue", 45, 200);
        }
    }

    public void drawTail(Graphics g, Point prev, Point tail){
        int x = prev.x - tail.x;
        int y = prev.y - tail.y;

        if(x > 0){
            tailPositionImg = tail_right_img;
        }else if(x < 0){
            tailPositionImg = tail_left_img;
        }else if(y < 0){
            tailPositionImg = tail_up_img;
        }else{
            tailPositionImg = tail_down_img;
        }

        g.drawImage(tailPositionImg,tail.x, tail.y, bodySize, bodySize, null);
    }

    public void drawBody(Graphics g, Point prev, Point curr, Point next){
        int xPrev = prev.x - curr.x;
        int yPrev = prev.y - curr.y;
        int xNext = next.x - curr.x;
        int yNext = next.y - curr.y;

        if(xPrev == 0 && xNext == 0){
            bodyPositionImg = body_vertical_img;
        }else if(yPrev == 0 && yNext == 0){
            bodyPositionImg = body_horizontal_img;
        }else{//left         down           up           right
            if((xPrev > 0 && yNext > 0) || (yPrev > 0 && xNext > 0)){
                bodyPositionImg = body_up_right_img;
                //    left         up             down           right
            }else if((xPrev > 0 && yNext < 0) || (yPrev < 0 && xNext > 0)){
                bodyPositionImg = body_down_right_img;
                //    right        up             down         left
            }else if((xPrev < 0 && yNext < 0) || (yPrev < 0 && xNext < 0)){
                bodyPositionImg = body_down_left_img;
            }else{
                bodyPositionImg = body_up_left_img;
            }
        }
        g.drawImage(bodyPositionImg, curr.x, curr.y, bodySize, bodySize, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        
        if(keyCode == KeyEvent.VK_UP && velocityY == 0 && keysEnabled){
            velocityY = -20;
            velocityX = 0;
            headPositionImg = head_up_img;
        }else if(keyCode == KeyEvent.VK_DOWN && velocityY == 0 && keysEnabled){
            velocityY = 20;
            velocityX = 0;
            headPositionImg = head_down_img;
        }else if(keyCode == KeyEvent.VK_LEFT && velocityX == 0 && keysEnabled){
            velocityX = -20;
            velocityY = 0;
            headPositionImg = head_left_img;
        }else if(keyCode == KeyEvent.VK_RIGHT && velocityX == 0 && keysEnabled){
            velocityX = 20;
            velocityY = 0;
            headPositionImg = head_right_img;
        }else if(keyCode == KeyEvent.VK_SPACE && !keysEnabled && !gameOver){
            gameOver = true;
            showScore = true;
            score = 0;
            snakeBody.clear();
            headPositionImg = head_right_img;
            gameStart();
        }else if(keyCode == KeyEvent.VK_SPACE && !keysEnabled && gameOver){
            gameOver = false;
            keysEnabled = true;
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