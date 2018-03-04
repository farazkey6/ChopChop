package chopchop;

import chopchop.MotherChopper.FACE;
import java.awt.Color;
import java.awt.Graphics;
import static java.lang.Thread.sleep;

/**
 *
 * @author FaraZ
 */
public class Laser extends ScreenObject implements Runnable {

    FACE face;
    int damage, team;

    public Laser(int x, int y, int team, int damage, FACE face, ScreenManager sm) {
        super(x, y, sm);

        this.face = face;
        this.damage = damage;
        this.team = team;
    }

    @Override
    public void run() {
        
                try {
                    sleep(30);
                } catch (InterruptedException ex) {
                }
                
        sm.removeObject(this);
    }

    @Override
    public void draw(Graphics g) {
        
        g.setColor(Color.magenta);
        switch(face){
            
            case LEFT:
                g.drawLine(x, y+30, x - 400, y+30);
                break;
            case RIGHT:
                g.drawLine(x+60, y+30, x + 540, y+30);
                break;
        }
    }
}
