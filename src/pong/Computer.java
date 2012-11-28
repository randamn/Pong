package pong;

public class Computer extends Paddle{
	
	int direction = 1;
	
	public Computer(int side){
		this.setyPos(GamePanel.height/2);
		
		
		this.side = side; //LEFT
		if(this.side == 1)
			this.setxPos(10);
		else
			this.setxPos(GamePanel.width - 20);
	}

	@Override
	public void update() {
		/*if(GamePanel.ball.xPos < GamePanel.width/2){
			if( this.getyPos() > GamePanel.height ||// - (this.getHeight()/2) || 
				this.getyPos() > GamePanel.ball.yPos)// - (this.getHeight()/2) )
				direction = -1;
			if(	this.getyPos() < this.getHeight()/2 ||
				this.getyPos() < GamePanel.ball.yPos - this.getHeight()/2)
				direction = 1;
		}
		else
			direction = 0;
		
		if(direction == 1)
			this.moveDown();
		if(direction == -1)
			this.moveUp();*/
		if(this.side == 1)
			if(GamePanel.ball.xPos < GamePanel.width/2){
				if(this.getyPos() - GamePanel.ball.yPos > this.getSpeed())
					this.setyPos(this.getyPos() - this.getSpeed());
				else if(this.getyPos() - GamePanel.ball.yPos < -this.getSpeed())
					this.setyPos(this.getyPos() + this.getSpeed());
				else
					this.setyPos(this.getyPos() - (this.getyPos() - GamePanel.ball.yPos));
				
			}
		if(this.side == 2)
			if(GamePanel.ball.xPos > GamePanel.width/2){
				if(this.getyPos() - GamePanel.ball.yPos > this.getSpeed())
					this.setyPos(this.getyPos() - this.getSpeed());
				else if(this.getyPos() - GamePanel.ball.yPos < -this.getSpeed())
					this.setyPos(this.getyPos() + this.getSpeed());
				else
					this.setyPos(this.getyPos() - (this.getyPos() - GamePanel.ball.yPos));
				
			}
	}

}
