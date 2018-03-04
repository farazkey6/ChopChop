package chopchop;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import static java.lang.Thread.sleep;

/**
 *
 * @author FaraZ
 */
public class Cargo extends ScreenObject implements Runnable {

    int health, maxHealth, doodle, velocity;
    int offsetX, offsetY;
    int length;

    enum Condition {

        great, good, bad
    }

    Condition condition;

    boolean flicker, isAlive, alert;

    Image[] img;
    Image flickimg;

    public Cargo(int x, int y, int health, ScreenManager sm) {
        super(x, y, sm);

        this.health = health;
        maxHealth = health;
        this.x = x;
        this.y = y;

        img = new Image[3];

        condition = Condition.great;

        img[0] = sm.parent.getImage(sm.parent.getCodeBase(), "cargo0.jpg");
        img[1] = sm.parent.getImage(sm.parent.getCodeBase(), "cargo1.jpg");
        img[2] = sm.parent.getImage(sm.parent.getCodeBase(), "cargo2.jpg");
        flickimg = sm.parent.getImage(sm.parent.getCodeBase(), "flick.jpg");

        flicker = false;
        isAlive = true;
        velocity = 10;
        length = 300;

    }

    public void health(int damage) {
        if (isAlive && damage > 0) {
            alert = true;
            health -= damage;
        }

        if (health <= maxHealth * 2 / 3 && health > maxHealth / 3) {

            condition = Condition.good;
            length = 200;
        } else if (health <= maxHealth / 3) {

            condition = Condition.bad;
            length = 100;
        }

        if (health <= 0) {
            isAlive = false;
            sm.parent.player.money = Math.max(0, sm.parent.player.money - maxHealth);
            sm.parent.player.lost++;
            sm.removeObject(this);
        }
    }

    public void alert() {

        alert = true;
        try {
            sleep(50);
        } catch (InterruptedException ex) {
        }
    }

    public void roam() {

        if (x > -300) {

            x -= velocity;
        }
    }

    @Override
    public void run() {

        while (isAlive && x > -300) {

            roam();
            try {
                sleep(100);
            } catch (InterruptedException ex) {
            }
            alert = false;
        }
        if (x == -300) {
            sm.parent.player.saved++;
        }
        sm.removeObject(this);
    }

    @Override
    public void draw(Graphics g) {

        switch (condition) {

            case great:
                g.drawImage(img[0], x, y, sm.parent);
                doodle = 15;
                break;
            case good:
                g.drawImage(img[1], x, y, sm.parent);
                doodle = 10;
                break;
            case bad:
                g.drawImage(img[2], x, y, sm.parent);
                doodle = 5;
                break;
        }

        drawMap(g);
    }

    public void drawMap(Graphics g) {

        offsetX = (x - sm.parent.player.x) / 10;
        offsetY = y / 10;

        if (x - sm.parent.player.x > -800 && x - sm.parent.player.x < 800) {

            g.setColor(Color.BLUE);
            g.fillRect(sm.parent.player.offset + offsetX, sm.parent.getHeight() - 50 + offsetY, doodle, 5);
        }

        if (alert) {

            g.setColor(Color.red);
            g.fillRect(sm.parent.player.offset + offsetX, sm.parent.getHeight() - 50 + offsetY, doodle, 5);

        }
    }
}
