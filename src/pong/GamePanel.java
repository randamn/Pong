package pong;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {

	private Image dbImage;
	private Graphics dbg;

	public static int width, height;

	private Thread game;
	private boolean running = false;
	private double fps = 0;
	
	public static Ball ball;
	public static Ball ball2;
	
	public static Paddle computer;
	public static Paddle human;

	private static final long serialVersionUID = 1L;

	public GamePanel(int w, int h) {
		width = w;
		height = h;
		
		ball = new Ball(width/2, height/2);
		
		computer = new Computer(1);
		human = new Human(2);
		
		addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e){
            	switch(e.getKeyCode()){
            	case KeyEvent.VK_UP:
            		((Human) human).keyUp();
            		break;
            	case KeyEvent.VK_DOWN:
            		((Human) human).keyDown();
            		break;
            	}
            }
            public void keyReleased(KeyEvent e){
            	((Human) human).keyRelease();
            }
            public void keyTyped(KeyEvent e){
            }
        });

		setPreferredSize(new Dimension(width, height));
		//setLocation(20, 20);
		setFocusable(true);
		requestFocus();
		
	}

	@SuppressWarnings("static-access")
	@Override
	public void run() {
		while (running) {
			long beforeTime = System.nanoTime();
			gameUpdate();
			gameRender();
			paintScreen();
						
			double millis = System.nanoTime() - beforeTime;
			millis /= 1000000;
			
			try {
				if((int)(16.66666-millis) > 0)
					game.sleep((int)(16.66666-millis));
			} catch (InterruptedException e) {}
			
			millis = System.nanoTime() - beforeTime;
			millis /= 1000000;
			
			fps = (int)(1000/millis);
		}
	}

	private void gameUpdate() {
		ball.update();
		computer.update();
		human.update();
	}

	private void gameRender() {
		if (dbImage == null) { // Create the buffer
			dbImage = createImage(width, height);
			if (dbImage == null) {
				System.err.println("dbImage is still null!");
				return;
			} else {
				dbg = dbImage.getGraphics();
			}
		}
		// Clear the screen
		dbg.setColor(Color.WHITE);
		dbg.fillRect(0, 0, width, height);
		// Draw Game elements
		draw(dbg);
	}

	private void draw(Graphics g) {	
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		
		char[] fpsArray = Integer.toString((int)fps).toCharArray();
		g.setColor(Color.WHITE);
		g.drawChars(fpsArray, 0, fpsArray.length, 20, 20);
		
		g.drawString(Integer.toString(computer.getScore()), 150, 40);
		g.drawString(Integer.toString(human.getScore()), 450, 40);
		
		g.drawRect(width/2, 0, 1, height);

		ball.draw(g);
		computer.draw(g);
		human.draw(g);
	}

	private void paintScreen() {
		Graphics g;
		try {
			g = this.getGraphics();
			if (dbImage != null && g != null) {
				g.drawImage(dbImage, 0, 0, null);
			}
			Toolkit.getDefaultToolkit().sync(); // For some operating systems
			g.dispose();
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	public void addNotify() {
		super.addNotify();
		startGame();
	}

	private void startGame() {
		if (game == null || !running) {
			game = new Thread(this);
			game.start();
			running = true;
		}
	}
}