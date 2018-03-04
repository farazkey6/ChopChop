package chopchop;

import java.awt.Color;
import java.awt.Graphics;
import static java.lang.Thread.sleep;

/**
 *
 * @author FaraZ
 */
public class Alpha extends MotherChopper implements Runnable {

    public Alpha(int x, int y, int team, int health, Weapon weapon, int damage, ScreenManager sm) {
        super(x, y, team, health, weapon, damage, sm);

        img[0] = sm.parent.getImage(sm.parent.getCodeBase(), "alpha0.png");
        img[1] = sm.parent.getImage(sm.parent.getCodeBase(), "alpha1.png");
        velocity = 10;
    }

    @Override
    public void run() {

        while (exists) {

            if (!isAlive) {

                boolean i = true;
                while (canMove(FACE.DOWN)) {

                    if (i) {
                        down();
                        down();
                        right();
                        try {
                            sleep(35);
                        } catch (InterruptedException ex) {
                        }
                        i = false;
                    } else if (!i) {
                        down();
                        down();
                        left();
                        try {
                            sleep(35);
                        } catch (InterruptedException ex) {
                        }
                        i = true;
                    }
                }
                flick();
                respawn();
            }
            roam();
        }
    }

    public void draw(Graphics g) {

        g.setColor(Color.red);                                                     //health bar
        g.fillRect(x, y + 30, maxHealth, 1);
        g.setColor(Color.green);
        g.fillRect(x, y + 30, health, 1);

        switch (face) {

            case RIGHT:
                g.drawImage(img[0], x, y, sm.parent);
                break;
            case LEFT:
                g.drawImage(img[1], x, y, sm.parent);
                break;
        }
        if (flicker) {

            g.drawImage(flickimg, x, y, sm.parent);
        }
    }

}
