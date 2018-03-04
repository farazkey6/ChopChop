package chopchop;

import chopchop.MotherChopper.FACE;
import java.awt.Color;
import java.awt.Graphics;
import static java.lang.Thread.sleep;

/**
 *
 * @author FaraZ
 */
public class Rocket extends ScreenObject implements Runnable{

    FACE face;
    int damage, team, velocity;
    
    
    public Rocket(int x, int y, int team, int damage, FACE face, ScreenManager sm){
        
        super(x, y, sm);
        this.face = face;
        this.damage = damage;
        this.team = team;
        
        velocity = 10;
    }
    
    
    @Override
    public void run() {
        
        switch (face){
            
            case LEFT:
                x-=45;
                while (x - sm.parent.player.x >= -600) {
                    x -= velocity;
                    try {
                        sleep(30);
                    } catch (InterruptedException ex) {
                    }
                }
                break;

            case RIGHT:
                while (x - sm.parent.player.x <= 600) {
                    x += velocity;
            try {
                sleep(30);
            } catch (InterruptedException ex) {
            }
                }
            break;
        }
        sm.parent.player.rocker = true;
        sm.removeObject(this);
    }

    @Override
    public void draw(Graphics g) {
        
        g.setColor(Color.red);
        g.drawOval(x, y, 15, 15);
        g.setColor(Color.ORANGE);
        g.drawOval(x, y, 10, 10);
        g.setColor(Color.YELLOW);
        g.drawOval(x, y, 5, 5);
    }
    
}
