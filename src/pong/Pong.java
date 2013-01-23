package pong;

import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

public class Pong extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	GameState state;
	
	public Pong(){
		
		setSize(600, 400);
		setTitle("Pong");
		FlowLayout layout = new FlowLayout();
		layout.setHgap(0);
		layout.setVgap(0);
		setLayout(layout);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setLocationRelativeTo(null); // Center in screen
        
        state = new Menu(this);
        
        this.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				state.keyPressHandler(e);
			}
			public void keyReleased(KeyEvent e) {
				state.keyReleaseHandler(e);
			}

			public void keyTyped(KeyEvent e) {
			}
		});
        add(state);
        setVisible(true);
	}
	
	public void startGame(char p1, char p2){
		state = new Game(p1, p2);
	}
	
	public static void main(String[] args) {
		new Pong();
	}

}
