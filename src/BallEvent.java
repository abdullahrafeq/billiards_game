
public class BallEvent {

	public static boolean possibleCollision(Ball ball1, Ball ball2) {	
		
		if (((ball1.getXVel() < 0 && ball2.getXVel() > 0) && ball1.getXPos() < ball2.getXPos())||
				((ball1.getXVel() > 0 && ball2.getXVel() < 0) && ball1.getXPos() > ball2.getXPos())) {
			return false;
		} else if (((ball1.getYVel() < 0 && ball2.getYVel() > 0) && ball1.getYPos() < ball2.getYPos())||
				((ball1.getYVel() > 0 && ball2.getYVel() < 0) && ball1.getYPos() > ball2.getYPos())
				) {
			return false;
		}
		
		return true;
	}

	public static boolean isCollision(Ball ball1, Ball ball2) {
		double dx = ball2.getXPos() - ball1.getXPos();
		double dy = ball2.getYPos() - ball1.getYPos();

		double dx2 = Math.pow(dx, 2);
		double dy2 = Math.pow(dy, 2);

		double d = Math.sqrt(dx2 + dy2);

		if (d < Ball.diameter) {
			return true;										
		}

		return false;
	}

	public static double[] collisionResponse(Ball ball1, Ball ball2) {
		double v1x = ball1.getXVel();
		double v1y = ball1.getYVel();
		double v2x = ball2.getXVel();
		double v2y = ball2.getYVel();

		double x1 = ball1.getXPos();
		double y1 = ball1.getYPos();

		double x2 = ball2.getXPos();
		double y2 = ball2.getYPos();

		double d2 = Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2); 

		double a = ((v1x-v2x)*(x1-x2)+(v1y-v2y)*(y1-y2))/d2;

		double u1x = v1x - a*(x1-x2);
		double u1y = v1y - a*(y1-y2);
		double u2x = v2x - a*(x2-x1);
		double u2y = v2y - a*(y2-y1);

		ball1.setXVel(u1x);
		ball1.setYVel(u1y);
		ball2.setXVel(u2x);
		ball2.setYVel(u2y);

		return new double [] {ball1.getXVel(), ball1.getYVel(), ball2.getXVel(), ball2.getYVel()};
	}

	public static boolean isSideWallCollission(Ball ball, double screenWidth) {
		if ((ball.getXPos() < (37.5+Ball.radius) && ball.getXPos() > 0 )|| (ball.getXPos() > screenWidth-37.5-Ball.radius && ball.getXPos() < 800)) {
			return true;
		}
		return false;
	}

	public static boolean isTopBottomCollission(Ball ball, double screenHeight) {
		if ((ball.getYPos() < (37.5+Ball.radius) && ball.getYPos() > 0) || (ball.getYPos() > screenHeight-37.5-Ball.radius) && ball.getYPos() < 500) {
			return true;
		}
		return false;
	}

	public static double calcTopBottomCollissionVel(Ball ball, double screenHeight) {
		ball.setYVel(-ball.getYVel());

		return ball.getYVel();
	}

	public static double calcSideCollissionVel(Ball ball, double screenWidth) {
		ball.setXVel(-ball.getXVel());

		return ball.getXVel();
	}

	public static boolean isInHole(Ball ball, Hole hole) {
		double dx = ball.getXPos() - hole.getXPos();
		double dy = ball.getYPos() - hole.getYPos();

		double dx2 = Math.pow(dx, 2);
		double dy2 = Math.pow(dy, 2);

		double d = Math.sqrt(dx2 + dy2);
		if (d < Ball.radius+Hole.radius-Ball.radius/2) {
			return true;
		}
		return false;
	}

	public static boolean isCueHitting(Cue cue, Ball ball) {

		double dx = cue.getX2()-ball.getXPos();
		double dy = cue.getY2()-ball.getYPos();

		double dx2 = Math.pow(dx, 2);
		double dy2 = Math.pow(dy, 2);

		double d = Math.sqrt(dx2+dy2);

		if (d < Ball.radius) {
			return true;
		}

		return false;
	}

	public static boolean isStopped(Ball ball) {
		if (ball.getXVel() == 0 && ball.getYVel() == 0) {
			return true;
		}
		return false;
	}

}
