package chopchop;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import static java.lang.Thread.sleep;

/**
 *
 * @author FaraZ
 */
public abstract class MotherChopper extends ScreenObject {

    int health, maxHealth, damage, team;

    int offsetX, offsetY;

    int sound;

    boolean flicker, tracked, exists;

    protected enum Weapon {

        pistol, shotgun, laser, rocket
    };
    int velocity;

    enum FACE {

        UP, DOWN, RIGHT, LEFT
    };

    FACE face;
    Weapon weapon;

    boolean isAlive;

    Image[] img;
    Image flickimg;

    public MotherChopper(int x, int y, int team, int health, Weapon weapon, int damage, ScreenManager sm) {

        super(x, y, sm);
        this.health = health;
        this.damage = damage;
        this.team = team;

        maxHealth = health;

        flickimg = sm.parent.getImage(sm.parent.getCodeBase(), "flick.jpg");

        flicker = false;

        sound = 0;

        setWeapon(weapon);

        face = FACE.RIGHT;

        isAlive = true;
        exists = true;

        img = new Image[2];

    }

    public void setWeapon(Weapon x) {

        weapon = x;
    }

    public void health(int damage) {
        if (isAlive && damage > 0) {
            health -= damage;
        }

        if (health <= 0) {
            isAlive = false;
            sm.parent.soundbank.fall.play();
        }
    }

    public void roam() {

        int jump;

        switch (face) {

            case RIGHT:
                if (canMove(FACE.RIGHT) && isAlive) {

                    jump = (int) (Math.random() * 30);
                    if (jump == 1) {
                        up();
                    } else if (jump == 0) {
                        down();
                    }
                    step();
                    try {
                        sleep(45);
                    } catch (InterruptedException ex) {
                    }
                } else {
                    face = FACE.LEFT;
                }
                break;
            case LEFT:
                if (canMove(FACE.LEFT) && isAlive) {

                    jump = (int) (Math.random() * 30);
                    if (jump == 1) {
                        up();
                    } else if (jump == 0) {
                        down();
                    }
                    step();
                    try {
                        sleep(45);
                    } catch (InterruptedException ex) {
                    }
                } else {
                    face = FACE.RIGHT;
                }
                break;
        }
    }

    public void respawn() {

        sm.parent.soundbank.stab.play();
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

        sm.parent.player.income(10);
        sm.parent.player.kills++;
        sm.parent.player.killstreak++;
        sm.parent.player.killStreak();
    }

    public void flick() {

        sm.parent.soundbank.stab.play();
        flicker = true;
        try {
            sleep(50);
        } catch (InterruptedException ex) {
        }

        flicker = false;
    }

    public boolean canMove(FACE face) {

        switch (face) {

            case LEFT:
                if (x - velocity <= 0) {
                    return false;
                }
                break;
            case RIGHT:
                if (x + velocity + 60 >= sm.parent.GAME_WIDTH) {
                    return false;
                }
                break;
            case UP:
                if (y - velocity <= 70) {
                    return false;
                }
                break;
            case DOWN:
                if (y + velocity + 30 >= sm.parent.getHeight() - 100) {
                    return false;
                }
                break;
        }
        return true;
    }

    public void pistol() {

        sm.parent.soundbank.minigun[sound % 6].play();
        sound++;
        Bullet bullet = new Bullet(x + 60, y + 30, team, damage, face, sm, 0);
        sm.addObject(bullet);
        Thread t = new Thread(bullet);
        t.start();

    }

    public void shotgun() {

        sm.parent.soundbank.shotgun[sound % 6].play();
        sound++;
        Bullet b1 = new Bullet(x + 60, y + 30, team, damage, face, sm, 0);
        Bullet b2 = new Bullet(x + 60, y + 25, team, damage, face, sm, 1);
        Bullet b3 = new Bullet(x + 60, y + 35, team, damage, face, sm, 2);

        sm.addObject(b1);
        sm.addObject(b2);
        sm.addObject(b3);

        Thread t1 = new Thread(b1);
        t1.start();
        Thread t2 = new Thread(b2);
        t2.start();
        Thread t3 = new Thread(b3);
        t3.start();

    }

    public void laser() { //original name is cluster

        sm.parent.soundbank.minigun[sound % 6].play();
        sound++;
        Cluster cluster = new Cluster(x + 60, y + 30, team, damage, face, sm);
        sm.addObject(cluster);
        Thread c = new Thread(cluster);
        c.start();

    }

    public void rocket() {

        sm.parent.soundbank.rocket.play();
        Rocket rocket = new Rocket(x + 60, y + 15, team, damage * 10, face, sm);

        sm.addObject(rocket);

        Thread r = new Thread(rocket);
        r.start();

    }

    public void shoot() {

        if (isAlive) {
            switch (weapon) {

                case pistol:
                    pistol();
                    break;

                case shotgun:
                    shotgun();
                    break;

                case rocket:
                    rocket();
                    break;

                case laser:
                    laser();
                    break;
            }
        }
    }

    public void up() {

        if (canMove(FACE.UP)) {
            y -= velocity;
        }
    }

    public void down() {

        if (canMove(FACE.DOWN)) {
            y += velocity;
        }
    }

    public void left() {

        face = FACE.LEFT;

        if (canMove(FACE.LEFT)) {
            x -= velocity;
        }
    }

    public void right() {

        face = FACE.RIGHT;

        if (canMove(FACE.RIGHT)) {
            x += velocity;
        }
    }

    public void step() {

        switch (face) {

            case RIGHT:
                right();
                break;

            case LEFT:
                left();
                break;
        }
    }

    public void draw(Graphics g) {

        switch (face) {

            case RIGHT:
                g.drawImage(img[0], x, y, sm.parent);
                break;
            case LEFT:
                g.drawImage(img[1], x, y, sm.parent);
                break;
        }

        drawMap(g);
    }

    public void drawMap(Graphics g) {

        if (x - sm.parent.player.x > -800 && x - sm.parent.player.x < 800) {

            offsetX = (x - sm.parent.player.x) / 10;
            offsetY = y / 10;
            g.setColor(Color.BLACK);
            g.fillRect(sm.parent.player.offset + offsetX, sm.parent.getHeight() - 50 + offsetY, 5, 5);
        }
    }
}
