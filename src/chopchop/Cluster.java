package chopchop;

import chopchop.MotherChopper.FACE;
import java.awt.Color;
import java.awt.Graphics;
import static java.lang.Thread.sleep;

/**
 *
 * @author FaraZ
 */
public class Cluster extends ScreenObject implements Runnable {

    FACE face;
    int damage, team, velocity, steps;

    public Cluster(int x, int y, int team, int damage, FACE face, ScreenManager sm) {

        super(x, y, sm);
        this.face = face;
        this.damage = damage;
        this.team = team;

        velocity = 10;
    }

    @Override
    public void run() {

        switch (face) {

            case LEFT:
                x -= 60;
                steps = 0;
                while (x - sm.parent.player.x >= -400 && steps < 17) {
                    x -= velocity;
                    steps++;
                    try {
                        sleep(20);
                    } catch (InterruptedException ex) {
                    }
                }

                Bullet lup = new Bullet(x, y, team, damage, FACE.UP, sm, 3);
                Bullet ldown = new Bullet(x, y, team, damage, FACE.DOWN, sm, 4);

                sm.addObject(lup);
                sm.addObject(ldown);

                Thread lupt = new Thread(lup);
                lupt.start();
                Thread ldownt = new Thread(ldown);
                ldownt.start();
                break;

            case RIGHT:
                steps = 0;
                while (x - sm.parent.player.x <= 400 && steps < 17) {
                    x += velocity;
                    steps++;
                    try {
                        sleep(20);
                    } catch (InterruptedException ex) {
                    }
                }

                Bullet rup = new Bullet(x, y, team, damage, FACE.UP, sm, 3);
                Bullet rdown = new Bullet(x, y, team, damage, FACE.DOWN, sm, 4);

                sm.addObject(rup);
                sm.addObject(rdown);

                Thread rupt = new Thread(rup);
                rupt.start();
                Thread rdownt = new Thread(rdown);
                rdownt.start();
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
