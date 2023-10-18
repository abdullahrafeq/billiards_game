import java.util.*;

public class GameRules {

	public static void initGame(Player p1, Player p2) {
		p1.setTurn(true);
		p2.setTurn(false);
	}

	public static void setTurn(Player p1, Player p2) {
		if (p1.isTurn()) {
			p1.setTurn(false);
			p2.setTurn(true);
		} else {
			p1.setTurn(true);
			p2.setTurn(false);
		}
	}

	public static void setHandBall(Player p1, Player p2) {
		if (p1.isHandBall()) {
			p1.setHandBall(false);
			p2.setHandBall(true);
		} else {
			p1.setHandBall(true);
			p2.setHandBall(false);
		}
	}

	public static void setWinner(Player p1, Player p2, ArrayList<String> ballsPocketed) {
		if (p1.isTurn()) {	
			if (p1.getColor() == null) {
				p1.setWinner(false);
				p2.setWinner(true);
				System.out.println("p2 winner");
			} else {				
				if (p1.getColor().equals("black")) {
					if (ballsPocketed.contains("white")) {
						p1.setWinner(false);
						p2.setWinner(true);
						System.out.println("p2 winner");
					} else {					
						p1.setWinner(true);
						p2.setWinner(false);
						System.out.println("p1 winner");
					}
				} else {
					p1.setWinner(false);
					p2.setWinner(true);
					System.out.println("p2 winner");
				}
			}
		} else {
			if (p2.getColor() == null) {
				p2.setWinner(false);
				p1.setWinner(true);
				System.out.println("p1 winner");
			} else {				
				if (p2.getColor().equals("black")) {				
					if (ballsPocketed.contains("white")) {
						p2.setWinner(false);
						p1.setWinner(true);
						System.out.println("p1 winner");
					} else {					
						p2.setWinner(true);
						p1.setWinner(false);
						System.out.println("p2 winner");
					}
				} else {
					p2.setWinner(false);
					p1.setWinner(true);
					System.out.println("p1 winner");
				}
			}
		}
	}

	public static boolean isWinner(ArrayList<String> ballsPocketed) {
		if (ballsPocketed.contains("black")) {
			return true;
		}
		return false;
	}

	public static boolean isTurn(Player p1, Player p2, ArrayList<String> ballsPocketed, ArrayList<String> arrListCollisions, int redBallCount, int yellowBallCount, boolean isColorNewlySet) {
		if (isColorNewlySet) {
			return false;
		} else {			
			if (isHandBall(p1, p2, ballsPocketed, arrListCollisions, redBallCount, yellowBallCount)) {
				return true;
			} 
			if (
					(p1.isTurn() && !ballsPocketed.contains(p1.getColor()))
					||(p2.isTurn() && !ballsPocketed.contains(p2.getColor()))
					) {
				return true;
			}
		}
		return false;
	}

	public static boolean isHandBall(Player p1, Player p2, ArrayList<String> ballsPocketed, ArrayList<String> arrListCollisions, int redBallCount, int yellowBallCount) {
		if (p1.isTurn()) {
			if (ballsPocketed.contains("white")){
				setHandBall(p1, p2);
				return true;
			} else if (arrListCollisions.isEmpty()) {
				setHandBall(p1, p2);
				return true;
			} else if (arrListCollisions.get(0).equals("black") && !p1.getColor().equals("black")) {	
				setHandBall(p1, p2);
				return true;
			}
			else {
				if (!arrListCollisions.get(0).equals(p1.getColor())) {
					if (p1.getColor() != null) {	
						setHandBall(p1, p2);
						return true;
					}
				}
			}
		} else {
			if (ballsPocketed.contains("white")){
				setHandBall(p1, p2);
				return true;
			} else if (arrListCollisions.isEmpty()) {
				setHandBall(p1, p2);
				return true;
			} else if (arrListCollisions.get(0).equals("black") && !p2.getColor().equals("black")) {
				setHandBall(p1, p2);
				return true;

			} else {
				if (!arrListCollisions.get(0).equals(p2.getColor())) {
					if (p2.getColor() != null) {						
						setHandBall(p1, p2);
						return true;
					}
				}
			}
		}
		return false;
	}

	public static void setColor(Player p1, Player p2, ArrayList<String> ballsPocketed) {
		if (p1.isTurn()) {
			if (ballsPocketed.get(0) == "yellow") {
				p1.setColor("yellow");
				p2.setColor("red");
			} else if (ballsPocketed.get(0) == "red") {
				p1.setColor("red");
				p2.setColor("yellow");
			}
		} else {
			if (ballsPocketed.get(0) == "yellow") {
				p2.setColor("yellow");
				p1.setColor("red");
			} else if (ballsPocketed.get(0) == "red") {
				p2.setColor("red");
				p1.setColor("yellow");
			}
		}
	}

}
