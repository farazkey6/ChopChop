package chopchop;

import java.awt.Color;
import java.awt.Graphics;
import static java.lang.Thread.sleep;

/**
 *
 * @author FaraZ
 */
public class Delta extends MotherChopper implements Runnable {

    public Delta(int x, int y, int team, int health, Weapon weapon, int damage, ScreenManager sm) {
        super(x, y, team, health, weapon, damage, sm);

        img[0] = sm.parent.getImage(sm.parent.getCodeBase(), "delta0.jpg");
        img[1] = sm.parent.getImage(sm.parent.getCodeBase(), "delta1.jpg");
        velocity = 3;
    }

    @Override
    public void run() {
        try {
            sleep(15000);
        } catch (InterruptedException ex) {
        }
        respawn();

        while (exists) {

            if (!isAlive) {

                while (canMove(FACE.DOWN)) {

                    down();
                    step();
                    try {
                        sleep(1);
                    } catch (InterruptedException ex) {
                    }
                }
                flick();
                respawn();
            }

            if (sm.parent.player.isAlive && sm.parent.player.y > 110) {
                track();
            } else {

                roam();
            }
        }
    }

    public void track() {

        int tempX = sm.parent.player.x;
        int tempY = sm.parent.player.y;

        if (x > tempX) {

            left();
            try {
                sleep(3);
            } catch (InterruptedException ex) {
            }
        }
        if (x < tempX) {

            right();
            try {
                sleep(3);
            } catch (InterruptedException ex) {
            }
        }
        if (y > tempY + 15) {

            up();
            try {
                sleep(5);
            } catch (InterruptedException ex) {
            }
        }
        if (y < tempY + 15) {

            down();
            try {
                sleep(5);
            } catch (InterruptedException ex) {
            }
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
                x = -100;
                face = FACE.RIGHT;
                break;
        }
        y = sm.parent.getHeight() / ((int) (Math.random() * 10 + 5)) + 150;
        isAlive = true;
        health = maxHealth;

        sm.parent.player.income(40);
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
