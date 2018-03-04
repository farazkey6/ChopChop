package chopchop;

import chopchop.MotherChopper.FACE;
import java.awt.Color;
import java.awt.Graphics;
import static java.lang.Thread.sleep;

/**
 *
 * @author FaraZ
 */
public class SuperLaser extends ScreenObject implements Runnable {

    FACE face;
    int damage, team;

    public SuperLaser(int x, int y, int team, int damage, FACE face, ScreenManager sm) {
        super(x, y, sm);

        this.face = face;
        this.damage = damage;
        this.team = team;
    }

    @Override
    public void run() {

        try {
            sleep(3000);
        } catch (InterruptedException ex) {
        }

        sm.removeObject(this);
    }

    @Override
    public void draw(Graphics g) {

        g.setColor(Color.cyan);
        switch (face) {

            case LEFT:
                g.fillRect(x - 800, y + 30, 800, 90);
                break;
            case RIGHT:
                g.fillRect(x + 60, y + 30, 800, 90);
                break;
        }
    }
}
