package pong;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

public abstract class GameState extends JPanel implements Runnable{

	private static final long serialVersionUID = 1L;
	
	private Image dbImage;
	private Graphics dbg;
	private Thread game;
	protected boolean running = false;
	
	public static int fps = 0;
	public static int width, height;
	
	public GameState(){
		
		width = 584;
		height = 363;
		
		setPreferredSize(new Dimension(width, height));
		setFocusable(false);
		this.setVisible(true);
	}
	
	@SuppressWarnings("static-access")
	public void run() {
		while (running) {
			long beforeTime = System.nanoTime();
			gameUpdate();
			gameRender();
			paintScreen();

			double millis = System.nanoTime() - beforeTime;
			millis /= 1000000;

			try {
				if ((int) (16.66666 - millis) > 0)
					game.sleep((int) (16.66666 - millis));
			} catch (InterruptedException e) {
			}

			millis = System.nanoTime() - beforeTime;
			millis /= 1000000;

			fps = (int) (1000 / millis);
		}
	}
	
	public abstract void gameUpdate();

	public abstract void draw(Graphics g);
	
	public abstract void keyPressHandler(KeyEvent e);
	public abstract void keyReleaseHandler(KeyEvent e);
	
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

		dbg.setColor(Color.WHITE);
		dbg.fillRect(0, 0, width, height);

		draw(dbg);
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
