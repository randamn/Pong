package pong;

import java.awt.Color;
import java.awt.Graphics;

public class Ball {
	
	int diameter = 10;
	
	double speed = 4;
	double angle = 0;
	
	double xPos, yPos;
	
	public Ball(int x, int y){
		reset();
	}
	
	public void update(){
		xPos += Math.cos(angle)*speed;
		yPos += Math.sin(angle)*speed;
		if(xPos <= 0 ){
			GamePanel.human.addScore();
			reset();
		}
		if (xPos >= GamePanel.width - diameter){
			GamePanel.computer.addScore();
			reset();
		}
		if(yPos <= 0 || yPos >= GamePanel.height - diameter){
			angle = -angle;
		}
		
		checkCollisions(GamePanel.computer);
		checkCollisions(GamePanel.human);
	}
	
	private void checkCollisions(Paddle paddle){
		if(paddle.side == 1) //LEFT
			if(xPos < paddle.getxPos() + paddle.getWidth())
				if(Math.abs(yPos - paddle.getyPos()) < paddle.getHeight()/2){
					angle = Math.PI - angle - (0.01*(yPos - paddle.getyPos()));
					speed += .5;
				}
		if(paddle.side == 2) //RIGHT
			if(xPos > paddle.getxPos() - paddle.getWidth())
				if(Math.abs(yPos - paddle.getyPos()) < paddle.getHeight()/2){
					angle = Math.PI - angle - (0.01*(yPos - paddle.getyPos()));
					speed += .5;
				}
	}
	
	private void reset(){
		xPos = GamePanel.width/2;
		yPos = GamePanel.height/2;
		angle = Math.random()*(Math.PI/2.0) + (3.0*Math.PI/4.0) ;
		angle += (int)(Math.random()*2)*Math.PI;
		speed = 4;
	}
	
	public void draw(Graphics g){
		g.setColor(Color.WHITE);
		g.fillOval((int)xPos, (int)yPos, diameter, diameter);
	}
}
