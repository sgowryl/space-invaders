import java.awt.*;

public class AlienShot implements Runnable {
    private int shotSpeed = 10, x = 0, shotHeight = 0;
    boolean shotState = true;
    Ship ship = null;

    public AlienShot(int xVal, int yVal, Ship s) {
        x = xVal;// Set the shot direction
        shotHeight = yVal;
        ship = s;
        Thread thread = new Thread(this);
        thread.start();
    }

    private final boolean moveShot() {

        if (ship.checkShot(x, shotHeight)) {
            System.out.println("An alien shot the ship!");
            ship.hitByAlien();
            shotState = false;
            return true;
        }
        shotHeight -= 2;
        if (shotHeight > SpaceInvaders.HEIGHT) {
            shotState = false;
            return true;
        }

        return false;
    }

    public void drawShot(Graphics g) {
        if (shotState) {
            g.setColor(Color.green);
        } else {
            g.setColor(Color.black);
        }
        g.fillRect(x, shotHeight, 2, 5);
    }

    public boolean getShotState() {
        return shotState;
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(shotSpeed);
            } catch (InterruptedException ie) {
            }

            if (moveShot()) {
                break;
            }

        }
    }

}