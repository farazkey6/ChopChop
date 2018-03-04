package chopchop;

import java.awt.Color;
import java.awt.Graphics;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author FaraZ
 */
public class Foxtrot extends MotherChopper implements Runnable {

    boolean begun, blackout;

    int tempY;

    public Foxtrot(int x, int y, int team, int health, Weapon weapon, int damage, ScreenManager sm) {
        super(x, y, team, health, weapon, damage, sm);

        img[0] = sm.parent.getImage(sm.parent.getCodeBase(), "boss0.jpg");
        img[1] = sm.parent.getImage(sm.parent.getCodeBase(), "boss1.jpg");

        begun = false;
        blackout = false;
        velocity = 5;
    }

    @Override
    public void run() {
        try {
            sleep(60000);
        } catch (InterruptedException ex) {
        }
        while (sm.parent.timer.timer > 0) {
            try {
                sleep(500);
            } catch (InterruptedException ex) {
            }
        }
        if (sm.parent.player.exists) {
            begun = true;

            sm.parent.generator.isAlive = false;
            sm.parent.echo.exists = false;
            sm.parent.charlie.exists = false;
            sm.parent.delta.exists = false;
            sm.parent.alpha.exists = false;
            sm.parent.bravo.exists = false;
            
            sm.removeObject(sm.parent.bravo);
            sm.removeObject(sm.parent.alpha);
            sm.removeObject(sm.parent.delta);
            sm.removeObject(sm.parent.echo);
            sm.removeObject(sm.parent.charlie);

            sm.parent.player.HUDoffset += 360;
            sm.parent.player.gunOffset += 360;
            sm.parent.player.shopOffset += 360;

            sm.parent.soundbank.thunder.loop();

            respawn();

            sm.parent.player.Ylimit = 80;

        }

        while (exists) {

            sm.parent.player.Xlimit = x;

            if (!isAlive) {

                boolean i = true;
                sm.parent.player.Xlimit = sm.parent.GAME_WIDTH;
                while (canMove(FACE.DOWN)) {

                    if (i) {
                        down();
                        down();
                        right();
                        try {
                            sleep(5);
                        } catch (InterruptedException ex) {
                        }
                        i = false;
                    } else if (!i) {
                        down();
                        down();
                        left();
                        try {
                            sleep(5);
                        } catch (InterruptedException ex) {
                        }
                        i = true;
                    }
                }
                flick();
                sm.parent.player.isAlive = false;
                sm.parent.player.GameOver();
            }

            if (sm.parent.player.isAlive) {
                track();
            }
        }
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
                if (y - velocity <= 80) {
                    return false;
                }
                break;
            case DOWN:
                if (y + velocity + 30 >= sm.parent.getHeight() - 250) {
                    return false;
                }
                break;
        }
        return true;
    }

    @Override
    public void shotgun() {

        sm.parent.soundbank.shotgun[sound % 6].play();
        sound++;
        Bullet b1 = new Bullet(x, y + 70, team, damage, face, sm, 0);
        Bullet b2 = new Bullet(x, y + 35, team, damage, face, sm, 1);
        Bullet b3 = new Bullet(x, y + 105, team, damage, face, sm, 2);

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

    private void engage() {

        if (health > maxHealth / 2) {
            if (y < 300) {

                while (canMove(FACE.DOWN) && isAlive) {

                    shoot();
                    down();
                    try {
                        sleep(150);
                    } catch (InterruptedException ex) {
                    }
                }
            } else {

                while (canMove(FACE.UP) && isAlive) {

                    shoot();
                    up();
                    try {
                        sleep(150);
                    } catch (InterruptedException ex) {
                    }
                }
            }
        } else if (health <= maxHealth / 2) {

            tempY = sm.parent.player.y;

            while (sm.parent.player.y > y + 75 && canMove(FACE.DOWN)) {

                down();
                try {
                    sleep(100);
                } catch (InterruptedException ex) {
                }
            }
            while (sm.parent.player.y < y + 75 && canMove(FACE.UP)) {

                up();
                try {
                    sleep(100);
                } catch (InterruptedException ex) {
                }
            }

            sm.parent.soundbank.comet.play();
            try {
                sleep(1500);
            } catch (InterruptedException ex) {
            }
            laser();
            try {
                sleep(3000);
            } catch (InterruptedException ex) {
            }
        }
    }

    public void laser() {

        SuperLaser laser = new SuperLaser(x, y, team, damage, face, sm);
        sm.addObject(laser);
        Thread l = new Thread(laser);
        l.start();
    }

    public void track() {

        int tempX = sm.parent.player.x;

        if (x - tempX >= - 300 && x - tempX <= 500) {

            if (!begun) {

                try {
                    sleep(1000);
                } catch (InterruptedException ex) {
                }
                blackout = true;
                try {
                    sleep(1000);
                } catch (InterruptedException ex) {
                }
                begun = true;
                blackout = false;
                try {
                    sleep(500);
                } catch (InterruptedException ex) {
                }
            }
            engage();
            try {
                sleep(5000);
            } catch (InterruptedException ex) {
            }
        } else {

            if (x > tempX + 360) {

                left();
                try {
                    sleep(50);
                } catch (InterruptedException ex) {
                }
            }
            if (x < tempX - 300) {

                right();
                try {
                    sleep(50);
                } catch (InterruptedException ex) {
                }
            }
        }
    }

    public void respawn() {

        x = sm.parent.GAME_WIDTH;
        face = FACE.LEFT;

        y = 130;
        isAlive = true;
        health = maxHealth;

        sm.parent.player.income(1000);
        sm.parent.player.kills++;
        sm.parent.player.killstreak++;
        sm.parent.player.killStreak();
    }

    public void draw(Graphics g) {

        g.setColor(Color.red);                                                     //health bar
        g.fillRect(x, y - 10, maxHealth / 30, 10);
        g.setColor(Color.green);
        g.fillRect(x, y - 10, health / 30, 10);

        if (!begun) {
            g.drawImage(img[0], x, y, sm.parent);
        } else {
            g.drawImage(img[1], x, y, sm.parent);
        }

        if (blackout) {

            g.setColor(Color.BLACK);
            g.fillRect(0, 0, sm.parent.GAME_WIDTH, sm.parent.getHeight());
        }

        if (flicker) {

            g.drawImage(flickimg, x, y, 300, 150, sm.parent);
        }
    }

    public void drawMap(Graphics g) {

        if (x - sm.parent.player.x > -800 && x - sm.parent.player.x < 800) {

            offsetX = (x - sm.parent.player.x) / 10;
            offsetY = y / 10;
            g.setColor(Color.RED);
            g.fillRect(sm.parent.player.offset + offsetX, sm.parent.getHeight() - 50 + offsetY, 15, 15);
        }
    }

}
