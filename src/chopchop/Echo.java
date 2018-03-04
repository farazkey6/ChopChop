package chopchop;

import java.awt.Color;
import java.awt.Graphics;
import static java.lang.Thread.sleep;

/**
 *
 * @author FaraZ
 */
public class Echo extends MotherChopper implements Runnable {

    int jump, fire;
    boolean shot;

    public Echo(int x, int y, int team, int health, Weapon weapon, int damage, ScreenManager sm) {
        super(x, y, team, health, weapon, damage, sm);

        img[0] = sm.parent.getImage(sm.parent.getCodeBase(), "echo0.jpg");
        img[1] = sm.parent.getImage(sm.parent.getCodeBase(), "echo1.jpg");
        velocity = 10;
    }

    @Override
    public void run() {
        try {
            sleep(45000);
        } catch (InterruptedException ex) {
        }
        respawn();

        while (exists) {

            if (!isAlive) {

                while (canMove(FACE.DOWN)) {

                    down();
                    step();
                    try {
                        sleep(10);
                    } catch (InterruptedException ex) {
                    }
                }
                flick();
                respawn();
            }

            if (isAlive) {
                roam();
            }
        }
    }

    public void roam() {

        switch (face) {

            case RIGHT:
                if (canMove(FACE.RIGHT) && isAlive && !shot) {

                    jump = (int) (Math.random() * 20);
                    if (jump == 1) {
                        up();
                    } else if (jump == 0) {
                        down();
                    }
                    step();
                    try {
                        sleep(30);
                    } catch (InterruptedException ex) {
                    }
                    fire = (int) (Math.random() * 100);
                    if (fire == 50) {
                        shoot();
                        shot = true;
                    }
                } else {
                    face = FACE.LEFT;
                    shot = false;
                }

                break;
            case LEFT:
                if (canMove(FACE.LEFT) && isAlive && !shot) {

                    jump = (int) (Math.random() * 30);
                    if (jump == 1) {
                        up();
                    } else if (jump == 0) {
                        down();
                    }
                    step();
                    try {
                        sleep(35);
                    } catch (InterruptedException ex) {
                    }
                    fire = (int) (Math.random() * 100);
                    if (fire == 50) {
                        shoot();
                        shot = true;
                    }
                } else {
                    face = FACE.RIGHT;
                    shot = false;
                }
                break;
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

        sm.parent.player.income(70);
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
