
public class Cue {

	private int width;
	private int height;
	private String src;
	private double angle;
	public static double cueVel = 3;
	private double pow;
	private double powX;
	private double powY;

	private double x1;
	private double y1;
	private double x2;
	private double y2;

	public Cue(int width, int height, double x1, double y1, double x2, double y2, String src, double angle) {
		this.width = width;
		this.height = height;
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.src = src;
		this.angle = angle;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public double getAngle() {
		return this.angle;
	}

	public void setAngle(double mouseX, double mouseY, double ballX, double ballY) {
		if (mouseX != ballX) {					
			if (mouseX < ballX) {
				this.angle = Math.atan((mouseY-ballY)/(mouseX-ballX));				
			} else {
				this.angle = Math.PI+Math.atan((mouseY-ballY)/(mouseX-ballX));				
			}
		} else {
			if (mouseY < ballY) {
				this.angle = Math.PI/2;
			} else if (mouseY > ballY) {
				this.angle = Math.PI*3/2;
			}
		}
	}

	public void setX1() {
		this.x1 = this.x2 - this.width*Math.cos(this.angle);
	}

	public void setY1() {
		this.y1 = this.y2 - this.width*Math.sin(this.angle);
	}

	public void setX2(Ball ball) {
		this.x2 = ball.getXPos() - Math.cos(this.angle)*(20+Ball.radius);
	}

	public void setY2(Ball ball) {
		this.y2 = ball.getYPos() - Math.sin(this.angle)*(20+Ball.radius);
	}

	public double getX1() {
		return this.x1;
	}

	public double getY1() {
		return this.y1;
	}

	public double getX2() {
		return this.x2;
	}

	public double getY2() {
		return this.y2;
	}

	public void changeX(int direction, double vel) {
		this.x1 += direction*Math.cos(this.angle)*vel;
		this.x2 += direction*Math.cos(this.angle)*vel;
	}

	public void changeY(int direction, double vel) {
		this.y1 += direction*Math.sin(this.angle)*vel;
		this.y2 += direction*Math.sin(this.angle)*vel;
	}
	
	public void setPow(Ball ball) {
		double dx2 = Math.pow(ball.getXPos()-this.x2, 2);
		double dy2 = Math.pow(ball.getYPos()-this.y2, 2);
		double d = Math.sqrt(dx2+dy2);
		this.pow = d/10;
	}
	
	public double getPow() {
		return this.pow;
	}
}
