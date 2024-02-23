import java.awt.*;
import java.util.*;

public class AlienArmy {

    // The army has 3 rows of aliens
    protected final Alien rowOne[] = new Alien[10];
    protected final Alien rowTwo[] = new Alien[10];
    protected final Alien rowThree[] = new Alien[10];
    private boolean movingRight = true;
    private final Ship ship;
    private final SpaceInvaders spaceInvaders;
    // A container to store details of the current alien shots
    protected final Vector<AlienShot> alienShots = new Vector<>();
    private Image alienImage = null;

    public AlienArmy(Ship s, SpaceInvaders si, Image ai) {
        ship = s;
        spaceInvaders = si;
        alienImage = ai;
        createArmy();
        setStartingPositions();
    }

    private final void createArmy() {
        for (int i = 0; i < 10; i++) {
            rowOne[i] = new Alien(alienImage, spaceInvaders);
            rowTwo[i] = new Alien(alienImage, spaceInvaders);
            rowThree[i] = new Alien(alienImage, spaceInvaders);
        }
    }

    private void setStartingPositions() {
        int rHeight = 50;
        int lStart = 50;

        for (int row = 0; row < 3; row++) {
            for (int i = 0; i < 10; i++) {
                switch (row) {
                    case 0:
                        rowOne[i].setPosition(lStart, rHeight);
                        break;
                    case 1:
                        rowTwo[i].setPosition(lStart, rHeight);
                        break;
                    case 2:
                        rowThree[i].setPosition(lStart, rHeight);
                        break;
                }
                lStart += 40;
            }
            rHeight += 50;
            lStart = 50;
        }
    }

    public void moveArmy() {
        if (movingRight) {
            moveRight();
        } else {
            moveLeft();
        }
        startRandomAlienFiring();
    }

    private void moveAlienRowRight(Alien[] row) {
        for (int i = 0; i < 10; i++) {
            row[i].setPosition(row[i].getXPos() + 15, row[i].getYPos());
        }
    }

    private void moveAlienRowLeft(Alien[] row) {
        for (int i = 0; i < 10; i++) {
            row[i].setPosition(row[i].getXPos() - 15, row[i].getYPos());
        }
    }

    private void moveAlienRowDown(Alien[] row) {
        for (int i = 0; i < 10; i++) {
            row[i].setPosition(row[i].getXPos(), row[i].getYPos() + 15);
        }
    }

    private void moveRight() {
        if (checkEdgeCollision(rowOne) || checkEdgeCollision(rowTwo) || checkEdgeCollision(rowThree)) {
            handleEdgeCollision();
            return;
        }
        moveAlienRowRight(rowOne);
        moveAlienRowRight(rowTwo);
        moveAlienRowRight(rowThree);
    }

    private void moveLeft() {
        if (checkEdgeCollision(rowOne) || checkEdgeCollision(rowTwo) || checkEdgeCollision(rowThree)) {
            handleEdgeCollision();
            return;
        }

        moveAlienRowLeft(rowOne);
        moveAlienRowLeft(rowTwo);
        moveAlienRowLeft(rowThree);
    }

    private boolean checkEdgeCollision(Alien[] row) {
        for (int i = 9; i >= 0; i--) {
            if (!row[i].hasBeenHit() && row[i].getXPos() > (SpaceInvaders.WIDTH - Alien.ALIEN_WIDTH - 15)) {
                System.out.println("hitt");
                return true;
            }
        }
        return false;
    }

    private void handleEdgeCollision() {
        movingRight = !movingRight;
        moveAlienRowDown(rowOne);
        moveAlienRowDown(rowTwo);
        moveAlienRowDown(rowThree);
    }

    private void startRandomAlienFiring() {
        Random generator = new Random();
        int rnd1 = generator.nextInt(10);
        int rnd2 = generator.nextInt(10);
        int rnd3 = generator.nextInt(10);

        startAlienFiring(rowOne, rnd1);
        startAlienFiring(rowTwo, rnd2);
        startAlienFiring(rowThree, rnd3);
    }

    private void startAlienFiring(Alien[] row, int rnd) {
        if (!row[rnd].hasBeenHit()) {
            AlienShot as = new AlienShot(row[rnd].getXPos() + (int) (Alien.ALIEN_WIDTH / 2), row[rnd].getYPos(), ship);
            alienShots.addElement(as);
        }
    }

    /**
     *
     */
    public void drawArmy(Graphics g) {
        // Draw the first row
        for (int i = 0; i < 10; i++) {
            rowOne[i].drawAlien(g);// Draw the first row
            rowTwo[i].drawAlien(g);// Draw the second row
            rowThree[i].drawAlien(g);// Draw the third row
        }
        // Now we need to draw any of the shots the aliens have fired
        Vector<AlienShot> tmp = new Vector<>();
        for (int i = 0; i < alienShots.size(); i++) {
            AlienShot as = alienShots.elementAt(i);
            // Need to remove old shots at this point!
            if (as.getShotState()) {
                // This is NOT an old shot
                tmp.addElement(as);
            }

            as.drawShot(g);

        }
        alienShots.clear();
        alienShots.addAll(tmp);
    }

    /**
     * This is where the collision detection takes place
     */
    public boolean checkShot(int x, int y) {
        for (int i = 0; i < 10; i++) {
            if (rowOne[i].hitAlien(x, y)) {
                spaceInvaders.hitAlienScore();
                return true;
            }
            if (rowTwo[i].hitAlien(x, y)) {
                spaceInvaders.hitAlienScore();
                return true;
            }
            if (rowThree[i].hitAlien(x, y)) {
                spaceInvaders.hitAlienScore();
                return true;
            }
        }
        return false;
    }
}
