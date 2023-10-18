import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.*;

public class GamePlay extends JPanel implements MouseListener, ActionListener, MouseMotionListener{

	private Player p1;
	private Player p2;
	private Ball [] ballList;
	private Hole [] holeList;

	private Image billiardTable;

	private Timer timer;

	private Cue cue;
	private Line line;

	private boolean mouseHeldDown;
	private boolean mouseReleased;
	private boolean cueHit;
	private boolean mouseMoved;
	private boolean isHandBall;
	private boolean drawCue;
	private int numCollisions;
	private ArrayList <Double> arrListXVel = new ArrayList<Double>();
	private ArrayList <Double> arrListYVel = new ArrayList<Double>();
	private ArrayList <String> arrListCollisions = new ArrayList<String>();
	private ArrayList <String> ballsPocketed = new ArrayList<String>();
	private boolean gameStart;
	private String level;
	private boolean isColorNewlySet;
	private int yellowBallCount;
	private int redBallCount;
	private boolean isWinnerSet;

	public GamePlay(String level){	
		//set the level of the game
		if (level.equals("easy")) {
			Ball.fConst = 0.01;
		} else {
			Ball.fConst = 0.03;
		}
		this.isWinnerSet = false;
		this.level = level;
		this.isColorNewlySet = false;
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.setSize(800, 500);
		this.setVisible(true);

		this.p1 = new Player(true);
		this.p2 = new Player(false);

		this.gameStart = true;

		Ball.diameter = 30;
		Hole.radius = 43.1529/2;
		Ball.radius = Ball.diameter/2;
		this.ballList = new Ball[16];
		this.holeList = new Hole[6];

		this.createHoles();
		this.createObjects();
		this.numCollisions = 0;
		GameRules.initGame(this.p1, this.p2);
		this.timer = new Timer(10, this);
		this.timer.start();
		this.cueHit = false;
		this.isHandBall = false;
		this.drawCue = true;
	}

	private void createHoles() {
		this.holeList[0] = new Hole(37.5, 37.5);
		this.holeList[1] = new Hole(this.getWidth()-37.5, 37.5);
		this.holeList[2] = new Hole(37.5, this.getHeight()-37.5);
		this.holeList[3] = new Hole(this.getWidth()-37.5, this.getHeight()-37.5);
		this.holeList[4] = new Hole(this.getWidth()/2, 37.5);
		this.holeList[5] = new Hole(this.getWidth()/2, this.getHeight()-37.5);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		//draw billiard table
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2D.drawImage(new ImageIcon("billiardTable.png").getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
		this.drawBalls(g2D);
		this.drawCue(g2D);
		g2D.setFont(new Font(Font.SERIF, Font.PLAIN,  50));
		g2D.setColor(Color.white);
		if (this.isWinnerSet) {
			if (this.p1.isWinner()) {
				g2D.drawString("p1 winner", this.getWidth()/2-200, this.getHeight()/2);
			} else {
				g2D.drawString("p2 winner", this.getWidth()/2-200, this.getHeight()/2);
			}
		} else {			
			if (this.p1.isTurn()) {			
				g2D.drawString("p1 turn color: " + this.p1.getColor(), this.getWidth()/2-200, this.getHeight()/2);
			} else {
				g2D.drawString("p2 turn color: " + this.p2.getColor(), this.getWidth()/2-200, this.getHeight()/2);
			}
		}
	}

	private void drawCue(Graphics2D g2D) {
		if (this.drawCue) {
			g2D.setColor(new Color(128, 85, 0));
			g2D.setStroke(new BasicStroke(this.cue.getHeight()/2));
			g2D.drawLine((int)this.cue.getX1(), (int)this.cue.getY1(), (int)this.cue.getX2(), (int)this.cue.getY2());
			//draw cue line
			if (this.level.equals("easy")) {
				g2D.setStroke(new BasicStroke(1));
				g2D.setColor(Color.white);
				g2D.drawLine((int) this.line.getX1(), (int) this.line.getY1(), (int) this.line.getX2(), (int) this.line.getY2());
				g2D.drawOval((int) this.line.getX2()-Ball.radius, (int) this.line.getY2()-Ball.radius, Ball.diameter, Ball.diameter);
			}
		}
	}

	private void drawBalls(Graphics2D g2D) {
		for (int i = 0; i < this.ballList.length; i++) {
			if (this.ballList[i].getSrc() == "yellow") {					
				g2D.setColor(Color.yellow);
			} else if (this.ballList[i].getSrc() == "red") {
				g2D.setColor(Color.red);
			} else if (this.ballList[i].getSrc() == "black") {
				g2D.setColor(Color.black);
			} else {
				g2D.setColor(Color.white);
			}

			if (this.ballList[i].isNotEaten()) {
				g2D.fillOval((int) this.ballList[i].getXPos()-Ball.radius, (int) this.ballList[i].getYPos()-Ball.radius, Ball.diameter, Ball.diameter);
//				velocity vectors for all the balls
				
//				g2D.setColor(Color.blue);
//				g2D.drawLine((int)(this.ballList[i].getXPos()), (int)(this.ballList[i].getYPos()), (int)(this.ballList[i].getXPos() + this.ballList[i].getXVel()*100), (int)(this.ballList[i].getYPos() + this.ballList[i].getYVel()*100));
			}
		}

	}

	private void createObjects() {
		double x1 = this.getWidth()-225-Ball.radius*3;

		int y1 = this.getHeight()/2;

		double m = 2;

		this.ballList[0] = new Ball(225, y1, 0, 0, "white");

		this.ballList[1] = new Ball(x1, y1, 0, 0, "yellow");

		this.ballList[2] = new Ball(x1+Ball.diameter, y1-Ball.radius-m, 0, 0, "yellow");
		this.ballList[11] = new Ball(x1+Ball.diameter, y1+Ball.radius+m, 0, 0, "red");

		this.ballList[8] = new Ball(x1+Ball.diameter*2, y1, 0, 0, "black");
		this.ballList[12] = new Ball(x1+Ball.diameter*2, y1-Ball.diameter-2*m, 0, 0, "red");
		this.ballList[6] = new Ball(x1+Ball.diameter*2, y1+Ball.diameter+2*m, 0, 0, "yellow");

		this.ballList[9] = new Ball(x1+Ball.diameter*3, y1-Ball.radius-m, 0, 0, "red");
		this.ballList[3] = new Ball(x1+Ball.diameter*3, y1-Ball.radius*3-3*m, 0, 0, "yellow");
		this.ballList[7] = new Ball(x1+Ball.diameter*3, y1+Ball.radius+m, 0, 0, "yellow");
		this.ballList[13] = new Ball(x1+Ball.diameter*3, y1+Ball.radius*3+3*m, 0, 0, "red");

		this.ballList[14] = new Ball(x1+Ball.diameter*4, y1, 0, 0, "red");
		this.ballList[15] = new Ball(x1+Ball.diameter*4, y1-Ball.radius*2-m*2, 0, 0, "red");
		this.ballList[5] = new Ball(x1+Ball.diameter*4, y1-Ball.radius*4-m*4, 0, 0, "yellow");
		this.ballList[4] = new Ball(x1+Ball.diameter*4, y1+Ball.radius*2+m*2, 0, 0, "yellow");
		this.ballList[10] = new Ball(x1+Ball.diameter*4, y1+Ball.radius*4+m*4, 0, 0, "red");

		this.cue = new Cue(500, 20, this.ballList[0].getXPos(), this.ballList[0].getYPos(), this.ballList[0].getXPos(), this.ballList[0].getYPos(), "billiardCue.png", 0);
		this.line = new Line(this.ballList[0].getXPos(), this.ballList[0].getYPos(), this.ballList[0].getXPos(), this.ballList[0].getYPos() );
	}

	private void detectWallCollision() {
		for (int i = 0; i < this.ballList.length; i++) {
			if (this.ballList[i].isNotEaten()) {				
				if (BallEvent.isTopBottomCollission(this.ballList[i], this.getHeight())) {
					double newYVel = BallEvent.calcTopBottomCollissionVel(this.ballList[i], this.getHeight());
					this.ballList[i].changeYPos(newYVel);
				}

				if (BallEvent.isSideWallCollission(this.ballList[i], this.getWidth())) {
					double newXVel = BallEvent.calcSideCollissionVel(this.ballList[i], this.getWidth());
					this.ballList[i].changeXPos(newXVel);
				}

			}
		}
	}

	private void ballEaten() {
		for (int i = 0; i < this.ballList.length; i++) {
			for (int j = 0; j < this.holeList.length; j++) {				
				if (BallEvent.isInHole(this.ballList[i], this.holeList[j]) && this.ballList[i].isNotEaten()) {
					this.ballList[i].setIsNotEaten(false);
					this.ballsPocketed.add(this.ballList[i].getSrc());
					this.ballList[i].setXVel(0);
					this.ballList[i].setYVel(0);
				}
			}
		}
	}
	
	private void totalNumBallsPocketed(String color) {
		for (int i = 0; i < this.ballsPocketed.size(); i++) {
			if (color.equals("yellow")) {
				if (this.ballsPocketed.get(i).equals(color)) {
					this.yellowBallCount++;
				}
			} else {
				if (this.ballsPocketed.get(i).equals(color)) {
					this.redBallCount++;
				}
			}
		}
	}

	private void checkStoppedBalls(int c) {
		if (this.cueHit) {				
			if (this.arrListXVel.size() < 16) {
				this.arrListXVel.add(this.ballList[c].getXVel());
				this.arrListYVel.add(this.ballList[c].getYVel());

			} else if (this.arrListXVel.size() == 16) {
				this.isColorNewlySet = false;
				if (this.arrListXVel.stream().allMatch(n -> n == 0.0) && this.arrListYVel.stream().allMatch(n -> n == 0.0)) {
					if (GameRules.isWinner(this.ballsPocketed)) {
						GameRules.setWinner(this.p1, this.p2, this.ballsPocketed);
						this.isWinnerSet = true;
						this.timer.stop();
					}    
					
					if (!this.isWinnerSet) {						
						this.totalNumBallsPocketed("yellow");
						this.totalNumBallsPocketed("red");
						this.isHandBall = GameRules.isHandBall(this.p1, this.p2, this.ballsPocketed, this.arrListCollisions, this.redBallCount, this.yellowBallCount);
						
						System.out.println("balls collided: " + this.arrListCollisions);
						System.out.println("balls pocketed" + this.ballsPocketed);
						if (this.gameStart && !this.isHandBall) {
							if (!this.ballsPocketed.isEmpty()) {	
								GameRules.setColor(this.p1, this.p2, this.ballsPocketed);
								this.gameStart = false;
								this.isColorNewlySet = true;
							}
						}
						
						if (!this.gameStart) {
							System.out.println("p1 color: " + this.p1.getColor());
							System.out.println("p2 color: " + this.p2.getColor());
						}
						
						if (this.p1.isTurn()) {
							System.out.println("turn before: p1");
						} else {
							System.out.println("turn before: p2");
						}
						
						System.out.println("hand ball: " + this.isHandBall);
						
						if (GameRules.isTurn(this.p1, this.p2, this.ballsPocketed, this.arrListCollisions, this.redBallCount, this.yellowBallCount, this.isColorNewlySet)) {
							GameRules.setTurn(this.p1, this.p2);
						}
						
						if (this.p1.isTurn()) {
							System.out.println("turn after: p1");
						} else {
							System.out.println("turn after: p2");
						}
						
						System.out.println("------------------------------");
						
						
						if (this.p1.getColor() != null && this.p2.getColor() != null) {						
							if ((this.p1.getColor().equals("yellow") && this.yellowBallCount == 7) || (this.p1.getColor().equals("red") && this.redBallCount == 7)) {
								this.p1.setColor("black");
							}
							
							if ((this.p2.getColor().equals("yellow") && this.yellowBallCount == 7) || (this.p2.getColor().equals("red") && this.redBallCount == 7)) {
								this.p2.setColor("black");
							}
						}
						
						this.cue.changeX(0, 0);
						this.cue.changeY(0, 0);
						this.drawCue = true;
						this.cueHit = false;
						this.mouseHeldDown = false;
						this.mouseReleased = false;					
						
						this.arrListCollisions.removeAll(this.arrListCollisions);
						this.ballsPocketed.removeAll(this.ballsPocketed);
					}
				}
				this.arrListXVel.removeAll(this.arrListXVel);
				this.arrListYVel.removeAll(this.arrListYVel);
			}
		}
	}


	private void collisionResponse(int numOfMovingBalls) {
		for (int i = 0; i < numOfMovingBalls; i++) {
			for (int j = i+1; j < numOfMovingBalls; j++) {
				if (this.ballList[i].isNotEaten() && this.ballList[j].isNotEaten()) {
					if (BallEvent.possibleCollision(this.ballList[i], this.ballList[j])) {
						if (BallEvent.isCollision(this.ballList[i], this.ballList[j])) {
							double dx2 = Math.pow(this.ballList[i].getXPos()-this.ballList[j].getXPos(), 2);
							double dy2 = Math.pow(this.ballList[i].getYPos()-this.ballList[j].getYPos(), 2);

							double [] vels = BallEvent.collisionResponse(this.ballList[i], this.ballList[j]);

							this.ballList[i].changeXPos(vels[0]);
							this.ballList[i].changeYPos(vels[1]);

							this.ballList[j].changeXPos(vels[2]);
							this.ballList[j].changeYPos(vels[3]);
							if (i == 0) {							
								this.numCollisions++;	
								this.arrListCollisions.add(this.ballList[j].getSrc());
							}
						}
					}
				}
			}
		}
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (this.isHandBall) {	
			this.isHandBall = false;
			this.drawCue = true;
			this.cueHit = false;
			this.mouseHeldDown = false;
			this.mouseReleased = false;
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		this.mouseHeldDown = true;
		this.mouseReleased = false;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		this.mouseHeldDown = false;
		this.mouseReleased = true;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub		
		int numOfMovingBalls = 16;

		this.collisionResponse(numOfMovingBalls);

		this.ballMovement(numOfMovingBalls);

		this.moveCue();

		this.ballEaten();

		this.detectWallCollision();

		this.repaint();
	}

	private void ballMovement(int numOfMovingBalls) {
		for (int c = 0; c < numOfMovingBalls; c++) {			
			this.ballList[c].setFriction(this.ballList[c].getXVel(), this.ballList[c].getYVel());
			this.ballList[c].setAngle(this.ballList[c].getXVel(), this.ballList[c].getYVel());
			this.ballList[c].setFX(this.ballList[c].getAngle(), this.ballList[c].getFriction());
			this.ballList[c].setFY(this.ballList[c].getAngle(), this.ballList[c].getFriction());

			this.stopBalls(c);

			this.checkStoppedBalls(c);

			this.moveBalls(c);		
		}
	}

	private void moveCue() {
		if (this.mouseHeldDown) {
			this.cue.changeX(-1, Cue.cueVel);
			this.cue.changeY(-1, Cue.cueVel);
			this.cue.setPow(this.ballList[0]);
		} else if (this.mouseReleased) {
			this.cue.changeX(1, this.cue.getPow());
			this.cue.changeY(1, this.cue.getPow());
			if (BallEvent.isCueHitting(this.cue, this.ballList[0])) {
				this.cueHit = true;
				this.drawCue = false;
				this.ballList[0].setXVel(this.cue.getPow()*Math.cos(this.cue.getAngle()));
				this.ballList[0].setYVel(this.cue.getPow()*Math.sin(this.cue.getAngle()));	
			}
		}	
	}

	private void stopBalls(int c) {
		if (this.ballList[c].getFX() > -0.0001 && this.ballList[c].getFX() < 0.0001) {
			this.ballList[c].setXVel(0);
			this.ballList[c].setYVel(0);

		} else {				
			this.ballList[c].changeXVel();
			this.ballList[c].changeYVel();
		}
	}

	private void moveBalls(int c) {
		this.ballList[c].changeXPos(this.ballList[c].getXVel());
		this.ballList[c].changeYPos(this.ballList[c].getYVel());
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		if (this.drawCue) {
			this.cue.setAngle(e.getX(), e.getY(), this.ballList[0].getXPos(), this.ballList[0].getYPos());
			this.cue.setY2(this.ballList[0]);
			this.cue.setX2(this.ballList[0]);
			this.cue.setY1();
			this.cue.setX1();
			
			this.line.setX1(this.ballList[0].getXPos());
			this.line.setY1(this.ballList[0].getYPos());
			this.line.setCoordinates(this.cue.getAngle(), e.getX(), e.getY(), this.ballList[0].getXPos(), this.ballList[0].getYPos());
		}
		if (this.isHandBall) {
			this.drawCue = false;
			this.ballList[0].setIsNotEaten(true);
			this.cue.changeX(0, 0);
			this.cue.changeY(0, 0);
			this.ballList[0].setXPos(e.getX());
			this.ballList[0].setYPos(e.getY());
		}
	}
}
