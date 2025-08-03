
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener{

    int boardHeight = 400;
    int boardWidth = 400;

    //snake
    int snakeX = 0;
    int snakeY = 0;
    int snakeWidth = 20;
    int snakeHeight = 20;
    class Snake{
        int x = snakeX;
        int y = snakeY;
        int width = snakeWidth;
        int height = snakeHeight;
    }


    //fruit
    int fruitX = 200;
    int fruitY = 200;
    int fruitWidth = 20;
    int fruitHeight = 20;
    class Fruit{
        int x = fruitX;
        int y = fruitY;
        int width = fruitWidth;
        int height = fruitHeight;
    }

    //game logic
    Snake snake;
    Fruit fruit;
    int velocityX = 0;
    int velocityY = 0;
    Timer gameLoop;

    SnakeGame(){
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        //snake
        snake = new Snake();

        //fruit
        fruit = new Fruit();

        //timer
        gameLoop = new Timer(1000/6, this);
        gameLoop.start();

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        g.setColor(Color.WHITE);
        g.fillRect(snake.x, snake.y, snake.width, snake.height);

        g.setColor(Color.RED);
        g.fillRect(fruit.x, fruit.y, fruit.width, fruit.height);

        if(eat()){
            g.setColor(Color.WHITE);
            g.fillRect(snake.x, snake.y, snake.width, snake.height);
            System.out.print("body created");
        }
    }

    public void move(){
        snake.x += velocityX;
        snake.y += velocityY;
        if(eat()){
            fruit.x = ((int)(Math.random() * 20)) * 20;
            fruit.y = ((int)(Math.random() * 20)) * 20;
        }
    }

    public boolean eat(){
        return  snake.x == fruit.x && 
                snake.y == fruit.y;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if(keyCode == KeyEvent.VK_UP){
            velocityY = -20;
            velocityX = 0;
        }else if(keyCode == KeyEvent.VK_DOWN){
            velocityY = 20;
            velocityX = 0;
        }else if(keyCode == KeyEvent.VK_LEFT){
            velocityX = -20;
            velocityY = 0;
        }else if(keyCode == KeyEvent.VK_RIGHT){
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