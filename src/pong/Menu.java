package pong;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

public class Menu extends JPanel implements Runnable {

	private static final long serialVersionUID = 1L;
	
	public static int width, height;
	
	private Image dbImage;
	private Graphics dbg;
	private Thread menu;
	private boolean running = false;
	
	private MenuOption[] options;
	private int choice = 0;
	
	private class MenuOption{
		public String name;
		public char p1, p2;
		
		public MenuOption(String name, char p1, char p2){
			this.name = name;
			this.p1 = p1;
			this.p2 = p2;
		}
	}
	
	public Menu(final Game game){
		
		width = 584;
		height = 363;
		
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_SPACE){
					stopGame();
					setFocusable(false);
					game.startGame(options[choice].p1, options[choice].p2);
					System.out.println(options[choice].p1 + " " + options[choice].p2);
					
					System.out.println("game start initiated");
					
				}
				if(e.getKeyCode() == KeyEvent.VK_UP){
					if(choice > 0)
						choice--;
				}
				if(e.getKeyCode() == KeyEvent.VK_DOWN){
					if(choice < 3)
						choice++;
				}
			}
			public void keyReleased(KeyEvent e) {
			}

			public void keyTyped(KeyEvent e) {
			}
		});
		
		options = new MenuOption[4];
		options[0] = new MenuOption("Human 		- 	Human", 'h', 'h');
		options[1] = new MenuOption("Human 	  	- 	Computer", 'h', 'c');
		options[2] = new MenuOption("Computer	- 	Human", 'c', 'h');
		options[3] = new MenuOption("Computer 	- 	Computer", 'c', 'c');
		
		run();
		
		setPreferredSize(new Dimension(width, height));
		setFocusable(true);
		requestFocus();
	}
	
	public void run(){
		System.out.println("werp");
		while(running){
			gameUpdate();
			gameRender();
			paintScreen();
		}
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
	
	public void draw(Graphics g){
		g.setColor(Color.BLACK);
		for(int i = 0; i < 4; i++){
			g.drawString(options[i].name, 20, 120 + (20*i));
		}
		g.drawOval(12, 113 + (20 * choice), 4, 4);
		
		
	}
	
	public void addNotify() {
		super.addNotify();
		startGame();
	}
	
	private void startGame() {
		if (menu == null || !running) {
			menu = new Thread(this);
			menu.start();
			running = true;
		}
	}
	
	private void stopGame() {
		running = false;
		
	}
	
	public void gameUpdate(){
		
	}

}
