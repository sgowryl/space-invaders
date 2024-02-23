
import java.awt.*;
import javax.swing.*;

import java.awt.image.BufferedImage;

public class SpaceInvaders extends JFrame implements Runnable {

  private static final SpaceInvaders instance; // Singleton instance

  public static int WIDTH = 700;// The width of the frame
  public static int HEIGHT = 500;// The height of the frame
  private int gameSpeed = 100;// Try 500
  AlienArmy army = null;
  Ship ship = null;
  private boolean paused = false;
  private int score = 0;
  Graphics offscreen_high;
  BufferedImage offscreen;
  Image backGroundImage = null;
  Image alienImage = null;

  private SpaceInvaders(String frameTitle) {
    super(frameTitle);
    addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(java.awt.event.WindowEvent windowEvent) {
        System.exit(0);
      }
    });

    backGroundImage = new javax.swing.ImageIcon("back3.jpg").getImage();
    alienImage = new javax.swing.ImageIcon("alien.jpg").getImage();
    ship = new Ship(this);// creating ship
    army = new AlienArmy(ship, this, alienImage);// creating alien army
    addMouseListener(ship);
    addMouseMotionListener(ship);

    offscreen = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    offscreen_high = offscreen.createGraphics();

    setBackground(Color.black);
    setSize(WIDTH, HEIGHT);
    setVisible(true);
    startGame();
  }

  static {
    instance = new SpaceInvaders("Space Invaders");
  }

  public static SpaceInvaders getInstance() {
    return (instance);
  }

  public void pauseGame(boolean state) {
    paused = state;
  }

  public void hitAlienScore() {
    score += 5;// get 5 points if you hit alien
    System.out.println("Current Score = " + score);
  }

  public void shotShip() {
    score -= 20;// loose 20 points if you get shot
    System.out.println("Current Score = " + score);
  }

  public void startGame() {
    Thread thread = new Thread(this);
    thread.start();
  }

  public void paint(Graphics g) {
    offscreen_high.setColor(Color.black);
    offscreen_high.fillRect(0, 0, WIDTH, HEIGHT);
    army.drawArmy(offscreen_high);
    ship.drawShip(offscreen_high);
    g.drawImage(offscreen, 0, 0, this);
  }

  public void moveAliens() {
    army.moveArmy();
  }

  public void run() {
    int c = 0;
    while (true) {
      try {
        Thread.sleep(gameSpeed);
      } catch (InterruptedException ie) {
      }
      if (!paused && c >= 5) {
        moveAliens();
        c = 0;
      }
      repaint();// Update the screen
      c++;

    }
  }

  public AlienArmy getAlienArmy() {
    return army;
  }

  public static void main(String[] args) {
    SpaceInvaders invaders = SpaceInvaders.getInstance();
  }

}