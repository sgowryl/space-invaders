
import java.awt.event.*;
import java.awt.*;

public class Ship implements MouseListener, MouseMotionListener {

    public static int S_HEIGHT = 20;
    public static int S_WIDTH = 10;
    private int x, heightPosition = 0;
    SpaceInvaders spaceInvaders = null;
    Shot shot = null;
    boolean hitState = false;

    public Ship(SpaceInvaders si) {
        spaceInvaders = si;
        x = (int) ((SpaceInvaders.WIDTH / 2) + (S_WIDTH / 2));
        heightPosition = SpaceInvaders.HEIGHT - S_HEIGHT - 20;
    }

    public void mouseMoved(MouseEvent me) {
        int newX = me.getX();
        if (newX > (SpaceInvaders.WIDTH - S_WIDTH - 10)) {// SETTING BOUNDARY
            x = SpaceInvaders.WIDTH - S_WIDTH - 10;
        } else {
            x = newX;// NEWX
        }
    }

    public void mouseDragged(MouseEvent me) {

    }

    public void mouseEntered(MouseEvent me) {
        spaceInvaders.pauseGame(false);
    }

    public void mouseExited(MouseEvent me) {
        spaceInvaders.pauseGame(true);
    }

    public void mouseReleased(MouseEvent me) {

    }

    public void mousePressed(MouseEvent me) {

    }

    public void mouseClicked(MouseEvent me) {
        AlienArmy army = spaceInvaders.getAlienArmy();
        shot = new Shot(x + (int) (S_WIDTH / 2), heightPosition, army);
    }

    public void drawShip(Graphics g) {
        g.setColor(Color.yellow);
        g.fillRect(x, heightPosition, S_WIDTH, S_HEIGHT);
        if ((shot != null) && (shot.getShotState())) {
            shot.drawShot(g);
        }
    }

    public final boolean checkShot(int xShot, int yShot) {// CHECKING IF HIT BY A SHOT

        if ((xShot >= x) && (xShot <= (x + S_WIDTH))) {
            if ((yShot >= heightPosition) && (yShot <= (heightPosition + S_HEIGHT))) {
                // The ship is hit
                hitState = true;
                return true;
            }
        }
        return false;
    }

    public void hitByAlien() {
        spaceInvaders.shotShip();
    }

}