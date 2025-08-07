import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
        int boardWidth = 440;
        int boardHeight = 440;

        JFrame frame = new JFrame("Snake Game");
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SnakeGame snake_game = new SnakeGame();
        frame.add(snake_game);
        frame.pack();
        snake_game.requestFocus();
  
        frame.setVisible(true);
    }
}
