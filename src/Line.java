
public class Line {

	private double x1;
	private double y1;
	private double x2;
	private double y2;

	public Line(double x1, double y1, double x2, double y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	public double getX1() {
		return this.x1;
	}

	public void setX1(double x1) {
		this.x1 = x1;
	}

	public double getY1() {
		return this.y1;
	}

	public void setY1(double y1) {
		this.y1 = y1;
	}

	public double getX2() {
		return this.x2;
	}

	public void setCoordinates(double angle, double mouseX, double mouseY, double ballX, double ballY) {
		if (mouseX < ballX && mouseY > ballY) {
			this.y2 = 37.5+Ball.radius;
			this.x2 = this.x1 + (this.y2-this.y1)/Math.tan(angle);
			if (this.x2 > 800-37.5-Ball.radius) {
				this.x2 = 800-37.5-Ball.radius;
				this.y2 = this.y1 + (this.x2-this.x1)*Math.tan(angle);
			}
		} else if (mouseX < ballX && mouseY < ballY) {
			this.y2 = 500-37.5-Ball.radius;
			this.x2 = this.x1 + (this.y2-this.y1)/Math.tan(angle);
			if (this.x2 > 800-37.5-Ball.radius) {
				this.x2 = 800-37.5-Ball.radius;
				this.y2 = this.y1 + (this.x2-this.x1)*Math.tan(angle);
			}
		} else if (mouseX == ballX && mouseY < ballY) {
			this.x2 = ballX;
			this.y2 = 500-37.5-Ball.radius;
		} else if (mouseX > ballX && mouseY < ballY) {
			this.y2 = 500-37.5-Ball.radius;
			this.x2 = this.x1 + (this.y2-this.y1)/Math.tan(angle);
			if (this.x2 < 37.5) {
				this.x2 = 37.5+Ball.radius;
				this.y2 = this.y1 + (this.x2-this.x1)*Math.tan(angle);
			}
		} else if (mouseX > ballX && mouseY > ballY) {
			this.y2 = 37.5+Ball.radius;
			this.x2 = this.x1 + (this.y2-this.y1)/Math.tan(angle);
			if (this.x2 < 37.5) {
				this.x2 = 37.5+Ball.radius;
				this.y2 = this.y1 + (this.x2-this.x1)*Math.tan(angle);
			}
		}
	}

	public double getY2() {
		return this.y2;
	}


}
