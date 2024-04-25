import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	static final int Width = 500;
	static final int Height = 500;
	static final int Unit_Size = 20;
	static final int No_of_units = (Width * Height) / (Unit_Size * Unit_Size);

	// hold x and y coordinates for body parts of the snake
	final int x[] = new int[No_of_units];
	final int y[] = new int[No_of_units];
	
	// initial length of the snake
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
		this.addKeyListener(new MyKeyAdapter());
		play();
	}	
	
	public void play() {
		addFood();
		running = true;
		
		timer = new Timer(90, this);
        //more the delay slower the snake
		timer.start();	
	}
	
	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		draw(graphics);
	}
	
	public void move() {
		for (int i = length; i > 0; i--) {
			// shift the snake one unit to the desired direction to create a move
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		
		if (direction == 'L') {
			x[0] = x[0] - Unit_Size;
		} else if (direction == 'R') {
			x[0] = x[0] + Unit_Size;
		} else if (direction == 'U') {
			y[0] = y[0] - Unit_Size;
		} else {
			y[0] = y[0] + Unit_Size;
		}	
	}
	
	public void checkFood() {
		if(x[0] == foodX && y[0] == foodY) {
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
			graphics.drawString("Score: " + foodEaten, (Width - metrics.stringWidth("Score: " + foodEaten)) / 2, graphics.getFont().getSize());
		
		} else {
			gameOver(graphics);
		}
	}
	
	public void addFood() {
		foodX = random.nextInt((int)(Width / Unit_Size))*Unit_Size;
		foodY = random.nextInt((int)(Height / Unit_Size))*Unit_Size;
	}
	
	public void checkHit() {
		// check if head run into its body
		for (int i = length; i > 0; i--) {
			if (x[0] == x[i] && y[0] == y[i]) {
				running = false;
			}
		}
		
		// check if head run into walls
		if (x[0] < 0 || x[0] > Width || y[0] < 0 || y[0] > Height) {
			running = false;
		}
		
		if(!running) {
			timer.stop();
		}
	}
	
	public void gameOver(Graphics graphics) {
		graphics.setColor(Color.red);
		graphics.setFont(new Font("Sans serif", Font.ROMAN_BASELINE, 50));
		FontMetrics metrics = getFontMetrics(graphics.getFont());
		graphics.drawString("Game Over", (Width - metrics.stringWidth("Game Over")) / 2, Height / 2);
		
		graphics.setColor(Color.white);
		graphics.setFont(new Font("Sans serif", Font.ROMAN_BASELINE, 25));
		metrics = getFontMetrics(graphics.getFont());
		graphics.drawString("Score: " + foodEaten, (Width - metrics.stringWidth("Score: " + foodEaten)) / 2, graphics.getFont().getSize());

	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (running) {
			move();
			checkFood();
			checkHit();
		}
		repaint();
	}
	
	public class MyKeyAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					if (direction != 'R') {
						direction = 'L';
					}
					break;
					
				case KeyEvent.VK_RIGHT:
					if (direction != 'L') {
						direction = 'R';
					}
					break;
					
				case KeyEvent.VK_UP:
					if (direction != 'D') {
						direction = 'U';
					}
					break;
					
				case KeyEvent.VK_DOWN:
					if (direction != 'U') {
						direction = 'D';
					}
					break;		
			}
		}
	}
}







































