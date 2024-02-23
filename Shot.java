import java.awt.*;

/**
 *
 */
public class Shot implements Runnable {
    private int shotSpeed = 10;
    private int x = 0;
    private int shotHeight = 0;
    boolean shotState = true;
    AlienArmy alienArmy = null;

    public Shot(int xVal, int yVal, AlienArmy aa) {
        x = xVal; // Set the shot direction
        shotHeight = yVal;
        alienArmy = aa;
        Thread thread = new Thread(this);
        thread.start();
    }

    private final boolean moveShot() {
        if (alienArmy.checkShot(x, shotHeight)) {
            // We hit something!
            System.out.println("We shot an alien!");
            shotState = false;
            return true;
        }

        shotHeight -= 2;
        if (shotHeight < 0) {
            shotState = false;
            return true;
        }

        return false;
    }

    public void drawShot(Graphics g) {
        if (shotState) {
            g.setColor(Color.white);
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
                // Ignore this exception
            }
            if (moveShot()) {
                break;
            }

        }
    }
}
