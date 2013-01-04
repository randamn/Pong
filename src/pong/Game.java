package pong;

import java.awt.FlowLayout;

import javax.swing.JFrame;

public class Game extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	private GamePanel game;
	private Menu menu;
	
	public Game(){
		
		setSize(600, 400);
		setTitle("Pong");
		FlowLayout layout = new FlowLayout();
		layout.setHgap(0);
		layout.setVgap(0);
		setLayout(layout);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setLocationRelativeTo(null); // Center in screen
       
        menu = new Menu(this);
        add(menu);
        setVisible(true);
	}
	
	public void startGame(char p1, char p2){
		game = new GamePanel(p1, p2);
		menu.setVisible(false);
		remove(menu);

		game.setVisible(true);
		add(game);
		
	}
	
	public static void main(String[] args) {
		new Game();
	}

}
