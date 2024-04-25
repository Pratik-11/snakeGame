import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.Random;
import java.util.random.*;
import javax.swing.JPanel.*;

public class GamePanel extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;

    // constants bro
    static final int Width = 500;
    static final int Height = 500;
    static final int Unit_Size = 20;
    static final int Number_ofUnits = (Width * Height) / (Unit_Size * Unit_Size);

    final int[] x = new int[Number_ofUnits];
    final int[] y = new int[Number_ofUnits];

    // Snake initial length and all
    int length = 5;
    int foodEaten;
    int foodX;
    int foodY;
    char direction = 'D';
    boolean running = false;
    Random random;
    Timer timer;

    GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(Width, Height));
        this.setBackground(Color.DARK_GRAY);
        this.setFocusable(true);
        this.addKeyListener(new MykeyAdapter());
        play();
    }

    public void play() {
        addFood();
        running = true;

        timer = new Timer(80, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        draw(graphics);
    }

    public void move() {
        for (int i = length; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        if (direction == 'L') {
            x[0] = x[0] - Unit_Size;
        } else if (direction == 'R') {
            x[0] = x[0] + Unit_Size;
        } else if (direction == 'U') {
            x[0] = y[0] - Unit_Size;
        } else {
            y[0] = y[0] + Unit_Size;
        }
    }

    public void checkFood() {
        if (x[0] == foodX && y[0] == foodY) {
            length++;
            foodEaten++;
            addFood();
        }
    }

    public void draw(Graphics graphics) {

        if (running) {
            graphics.setColor(new Color(210, 115, 90));
            graphics.fillOval(foodX, foodY, Unit_Size, Unit_Size);
            graphics.setColor(Color.white);
            graphics.fillRect(x[0], y[0], Unit_Size, Unit_Size);
            for (int i = 1; i < length; i++) {
                graphics.setColor(new Color(40, 200, 150));
                graphics.fillRect(x[i], y[i], Unit_Size, Unit_Size);
            }
            graphics.setColor(Color.white);
            graphics.setFont(new Font("Sans serif", Font.ROMAN_BASELINE, 25));
            FontMetrics metrics = getFontMetrics(graphics.getFont());
            graphics.drawString("Score: " + foodEaten, (Width - metrics.stringWidth("Score: " + foodEaten)) / 2,
                    graphics.getFont().getSize());
        } else {
            gameOver(graphics);
        }
    }

    public void addFood() {
        foodX = random.nextInt((int) (Width / Unit_Size)) * Unit_Size;
        foodY = random.nextInt((int) (Width / Unit_Size)) * Unit_Size;
    }

    public void checkHit() {
        // check if head cuts into its own body

        for (int i = length; i > 0; i--) {
            if (x[0] == x[i] && y[0] == y[i]) {
                running = false;
            }
        }
        // check if head run into the walls

        if (x[0] < 0 || x[0] > Width || y[0] < 0 || y[0] > HEIGHT) {
            running = false;
        }
        if (!running) {
            timer.stop();
        }
    }

    public void gameOver(Graphics graphics) {
        graphics.setColor(Color.red);
        graphics.setFont(new Font("Sans serif", Font.ROMAN_BASELINE, 50));
        FontMetrics metrics = getFontMetrics(graphics.getFont());
        graphics.drawString("Game Over", (Width - metrics.stringWidth("Game Over")) / 2, HEIGHT / 2);
        graphics.setColor(Color.white);
        graphics.setFont(new Font("Sans serif", Font.ROMAN_BASELINE, 25));
        metrics = getFontMetrics(graphics.getFont());
        graphics.drawString("Score: " + foodEaten, (Width - metrics.stringWidth("Score: " + foodEaten)) / 2,graphics.getFont().getSize());
    }

}