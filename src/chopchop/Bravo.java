package chopchop;

import java.awt.Color;
import java.awt.Graphics;
import static java.lang.Thread.sleep;

/**
 *
 * @author FaraZ
 */
public class Bravo extends MotherChopper implements Runnable {

    public Bravo(int x, int y, int team, int health, Weapon weapon, int damage, ScreenManager sm) {
        super(x, y, team, health, weapon, damage, sm);

        img[0] = sm.parent.getImage(sm.parent.getCodeBase(), "bravo0.png");
        img[1] = sm.parent.getImage(sm.parent.getCodeBase(), "bravo1.png");

        velocity = 10;
    }

    @Override
    public void run() {

        while (exists) {

            if (!isAlive) {

                while (canMove(FACE.DOWN)) {

                    down();
                    step();
                    try {
                        sleep(30);
                    } catch (InterruptedException ex) {
                    }
                }
                flick();
                respawn();
            }
            roam();
        }
    }

    public void respawn() {

        int random = (int) (Math.random() * 2);
        switch (random) {
            case 0:
                x = sm.parent.GAME_WIDTH;
                face = FACE.LEFT;
                break;
            case 1:
                x = 0;
                face = FACE.RIGHT;
                break;
        }
        y = sm.parent.getHeight() / ((int) (Math.random() * 10 + 5)) + 150;
        isAlive = true;
        health = maxHealth;

        sm.parent.player.income(20);
        sm.parent.player.kills++;
        sm.parent.player.killstreak++;
        sm.parent.player.killStreak();
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
