
import java.awt.*;

interface AlienShotHandler {
    void handleShot(Alien alien, int x, int y);
}

class AlienShotEventHandler implements AlienShotHandler {

    public void handleShot(Alien alien, int x, int y) {
        if (alien.hitAlien(x, y)) {
            alien.hitByShot();
        }
    }
}

public class Alien {

    public static int ALIEN_HEIGHT = 25;
    public static int ALIEN_WIDTH = 15;

    private int leftPosition = 0;
    private int heightPosition = 0;

    private boolean hitState = false;

    private Image alienImage = null;
    private final AlienShotHandler shotHandler;
    protected SpaceInvaders spaceInvaders = null;

    public Alien(Image ai, SpaceInvaders si) {
        alienImage = ai;
        spaceInvaders = si;
        shotHandler = new AlienShotEventHandler();
    }

    // Constructor to accept custom AlienShotHandler
    protected Alien(Image ai, SpaceInvaders si, AlienShotHandler handler) {
        alienImage = ai;
        spaceInvaders = si;
        shotHandler = handler;
    }

    public void handleShotEvent(int x, int y) {
        shotHandler.handleShot(this, x, y);
    }

    public void hitByShot() {
        hitState = true;
    }

    public boolean hasBeenHit() {
        return hitState;
    }

    public boolean hitAlien(int x, int y) {
        if (hitState) {
            return false;
        }

        if ((x >= leftPosition) && (x <= (leftPosition + ALIEN_WIDTH))) {
            if ((y >= heightPosition) && (y <= (heightPosition + ALIEN_HEIGHT))) {
                hitState = true;
                return true;
            }
        }
        return false;
    }

    public void drawAlien(Graphics g) {
        if (!hitState) {
            g.setColor(Color.red);
            g.fillRect(leftPosition, heightPosition, ALIEN_WIDTH, ALIEN_HEIGHT);
        }
    }

    public void setPosition(int x, int y) {
        leftPosition = x;
        heightPosition = y;
    }

    public int getXPos() {
        return leftPosition;
    }

    public int getYPos() {
        return heightPosition;
    }

}
