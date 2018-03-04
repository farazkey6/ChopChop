package chopchop;

import java.awt.Graphics;

/**
 *
 * @author FaraZ
 */
abstract class ScreenObject {
    
    int x, y;
    
    ScreenManager sm;
    
    public ScreenObject(int x, int y, ScreenManager sm){
        
        this.x = x;
        this.y = y;
        this.sm = sm;
    }
    
    public abstract void draw(Graphics g);
}
