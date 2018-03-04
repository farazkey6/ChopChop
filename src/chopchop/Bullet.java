package chopchop;

import chopchop.MotherChopper.FACE;
import java.awt.Color;
import java.awt.Graphics;
import static java.lang.Thread.sleep;

/**
 *
 * @author FaraZ
 */
public class Bullet extends ScreenObject implements Runnable {

    FACE face;
    int damage, team, velocity;
    int way;

    public Bullet(int x, int y, int team, int damage, FACE face, ScreenManager sm, int way) {

        super(x, y, sm);
        this.face = face;
        this.damage = damage;
        this.team = team;
        this.way = way;

        velocity = 10;
    }

    @Override
    public void run() {

        switch (way) {
            case 0:
                switch (face) {

                    case LEFT:
                        x -= 60;
                        while (x - sm.parent.player.x >= -600) {
                            x -= velocity;
                            try {
                                sleep(10);
                            } catch (InterruptedException ex) {
                            }
                        }
                        sm.removeObject(this);
                        break;

                    case RIGHT:
                        while (x - sm.parent.player.x <= 600) {
                            x += velocity;
                            try {
                                sleep(10);
                            } catch (InterruptedException ex) {
                            }
                        }
                        sm.removeObject(this);
                        break;
                }
            case 1:
                switch (face) {

                    case LEFT:
                        x -= 60;
                        while (x - sm.parent.player.x >= -600 && y + velocity > 0) {
                            x -= velocity;
                            y -= velocity / 4;
                            try {
                                sleep(10);
                            } catch (InterruptedException ex) {
                            }
                        }
                        sm.removeObject(this);
                        break;

                    case RIGHT:
                        while (x - sm.parent.player.x <= 600 && y + velocity > 0) {
                            x += velocity;
                            y -= velocity / 4;
                            try {
                                sleep(10);
                            } catch (InterruptedException ex) {
                            }
                        }
                        sm.removeObject(this);
                        break;
                }
            case 2:
                switch (face) {

                    case LEFT:
                        x -= 60;
                        while (x - sm.parent.player.x >= -600 && y + velocity < sm.parent.getHeight()) {
                            x -= velocity;
                            y += velocity / 4;
                            try {
                                sleep(10);
                            } catch (InterruptedException ex) {
                            }
                        }
                        sm.removeObject(this);
                        break;

                    case RIGHT:
                        while (x - sm.parent.player.x <= 600 && y + velocity < sm.parent.getHeight()) {
                            x += velocity;
                            y += velocity / 4;
                            try {
                                sleep(10);
                            } catch (InterruptedException ex) {
                            }
                        }
                        sm.removeObject(this);
                        break;
                }
            case 3:
                while (y > 0) {
                    y -= velocity;
                    try {
                        sleep(10);
                    } catch (InterruptedException ex) {
                    }
                }
                sm.removeObject(this);
                break;
            case 4:
                while (y < sm.parent.getHeight()) {
                    y += velocity;
                    try {
                        sleep(10);
                    } catch (InterruptedException ex) {
                    }
                }
                sm.removeObject(this);
                break;
        }
        sm.removeObject(this);
    }

    @Override
    public void draw(Graphics g) {

        g.setColor(Color.red);
        g.drawOval(x, y, 5, 5);
    }

}
