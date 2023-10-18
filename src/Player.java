
public class Player {
	
	private boolean isTurn;
	private int ballsPocketed;
	private String color;
	private boolean isWinner;
	private boolean isHandBall;
	

	public Player(boolean isTurn) {
		this.isTurn = isTurn;
		this.isHandBall = false;
	}
	
	public boolean isHandBall() {
		return isHandBall;
	}
	
	public void setHandBall(boolean isHandBall) {
		this.isHandBall = isHandBall;
	}
	
	public boolean isWinner() {
		return isWinner;
	}
	
	public void setWinner(boolean isWinner) {
		this.isWinner = isWinner;
	}

	public boolean isTurn() {
		return isTurn;
	}

	public void setTurn(boolean isTurn) {
		this.isTurn = isTurn;
	}

	public int getBallsPocketed() {
		return ballsPocketed;
	}

	public void setBallsPocketed(int ballsPocketed) {
		this.ballsPocketed = ballsPocketed;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
}
