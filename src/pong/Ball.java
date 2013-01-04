package pong;

import java.awt.Color;
import java.awt.Graphics;

public class Ball {
	
	double diameter = 10; //Default: 10
	
	double speed = 0;
	double angle = 0;
	
	double xPos, yPos;
	
	public Ball(int x, int y){
		reset();
	}
	
	public void update(){
		xPos += Math.cos(angle)*speed;
		yPos += Math.sin(angle)*speed;
		
		checkCollisions(GamePanel.leftPaddle);
		checkCollisions(GamePanel.rightPaddle);
		
		//Score on left side of board
		if(xPos <= 0 - diameter ){
			GamePanel.rightPaddle.addScore();
			reset();
		}
		//Score on right side of board
		if (xPos >= GamePanel.width){
			GamePanel.leftPaddle.addScore();
			reset();
		}
		
		//Bounce if contacting top or bottom of window
		if(yPos <= 0 || yPos >= GamePanel.height - (diameter/2.0)){
			angle = -angle;
		}
	}
	
	private void checkCollisions(Paddle paddle){
		if(paddle.side == 1) //LEFT
			if(xPos < paddle.getxPos() + paddle.getWidth())
				if( (yPos < paddle.getyPos() + (paddle.getHeight()/2.0) ) & 
					(yPos > paddle.getyPos() - (paddle.getHeight()/2.0) - diameter )){
					xPos = paddle.getxPos() + paddle.getWidth() + 1;
					angle = Math.PI - angle - (0.01*(yPos - paddle.getyPos()));
					speed += .5;
				}
		if(paddle.side == 2) //RIGHT
			if(xPos > paddle.getxPos())
				if( (yPos < paddle.getyPos() + (paddle.getHeight()/2.0) ) & 
					(yPos > paddle.getyPos() - (paddle.getHeight()/2.0) - diameter )){
					xPos = paddle.getxPos() -  1;
					angle = Math.PI - angle - (0.01*(yPos - paddle.getyPos()));
					speed += .5;
				}
	}
	
	private void reset(){
		xPos = GamePanel.width/2;
		yPos = GamePanel.height/2;
		angle = Math.random()*(Math.PI/2.0) + (3.0*Math.PI/4.0) ;
		angle += (int)(Math.random()*2)*Math.PI;
		//angle = 0;
		speed = 4; //Default: 4
	}
	
	public void draw(Graphics g){
		g.setColor(Color.WHITE);
		g.fillOval((int)(xPos - diameter/2.0), (int)(yPos - diameter/2.0), (int)diameter, (int)diameter);
	}
}
