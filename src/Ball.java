
public class Ball {

	private double xPos; 
	private double yPos; 
	private double xVel; 
	private double yVel;
	private double angle;

	public static int diameter;
	public static int radius;
	public static double fConst;
	public double friction;
	public double fx;
	public double fy;
	private String src;
	private boolean isNotEaten;

	public Ball(double xPos, double yPos, double xVel, double yVel, String src) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.xVel = xVel;
		this.yVel = yVel;
		this.src = src;
		this.isNotEaten = true;
	}

	public double getXPos() {
		return xPos;
	}

	public void setXPos(double xPos) {
		this.xPos = xPos;
	}

	public void changeXPos(double xVel) {
		this.xPos += xVel;
	}

	public double getYPos() {
		return yPos;
	}

	public void setYPos(double yPos) {
		this.yPos = yPos;
	}

	public void changeYPos(double yVel) {
		this.yPos += yVel;
	}

	public double getXVel() {
		return xVel;
	}

	public void setFriction(double xVel, double yVel) {
		this.friction = Math.sqrt(Math.pow(xVel, 2)+Math.pow(yVel, 2))*fConst;
	}
	
	public double getFriction() {
		return this.friction;
	}

	public void setFX(double angle, double friction) {
		this.fx = Math.cos(angle)*friction;		
	}
	
	public void changeXVel() {
		this.xVel -= this.fx;
	}

	public void setFY(double angle, double friction) {		
		this.fy = Math.sin(angle)*friction;
	}
	
	public double getFX() {
		return this.fx;
	}
	
	public double getFY() {
		return this.fy;
	}
	
	public void changeYVel() {
		this.yVel += this.fy;
	}

	public void setXVel(double xVel) {
		this.xVel = xVel;
	}

	public double getYVel() {
		return yVel;
	}

	public void setYVel(double yVel) {
		this.yVel = yVel;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public boolean isNotEaten() {
		return this.isNotEaten;
	}

	public void setIsNotEaten(boolean isEaten) {
		this.isNotEaten = isEaten;
	}
	
	public void setAngle(double xVel, double yVel) {
		if (xVel > 0 && yVel < 0) {
			this.angle = -Math.atan(yVel/xVel);
		} else if (xVel < 0 && yVel < 0) {
			this.angle = Math.PI-Math.atan(yVel/xVel);
		} else if (xVel < 0 && yVel > 0) {
			this.angle = Math.PI-Math.atan(yVel/xVel);
		} else if (xVel > 0 && yVel > 0) {
			this.angle = 2*Math.PI-Math.atan(yVel/xVel);
		} else if (xVel > 0 && yVel == 0) {
			this.angle = 0;
		}  else if (xVel < 0 && yVel == 0) {
			this.angle = Math.PI;
		}
	}
	
	public double getAngle() {
		return this.angle;
	}

}
