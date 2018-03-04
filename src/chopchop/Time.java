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
public class Time extends ScreenObject implements Runnable {

    int timer;

    public Time(int x, int y, int time, ScreenManager sm) {
        super(x, y, sm);

        this.timer = time;
    }

    @Override
    public void draw(Graphics g) {

        if (timer > 10) {
            g.setColor(Color.DARK_GRAY);
        } else if (timer > 0) {
            g.setColor(Color.red);
            g.drawString("Shop Will Be Closed Soon", sm.parent.player.x, sm.parent.player.y - 25);
        }
        if (timer > 0) {
            g.drawString("Time Left: " + timer, sm.parent.player.x, sm.parent.player.y - 50);
        }
    }

    @Override
    public void run() {

        while (true) {
            
            if (!sm.parent.player.isAlive){
                try {
                    sleep(500);
                } catch (InterruptedException ex) {
                }
            }
            else if (timer > -1 && sm.parent.player.exists) {
                if (timer < 10) {
                    sm.parent.soundbank.beep.play();
                }
                timer--;
                try {
                    sleep(1000);
                } catch (InterruptedException ex) {
                }
            }
        }
    }

}
