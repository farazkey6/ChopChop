
package chopchop;

import javax.swing.JApplet;

public class Refresher extends Thread {
    
    private JApplet applet;
    
    public Refresher(JApplet applet) {
        
        this.applet = applet;
    }
    
    public void run() {
        
        while(true) {
            
            applet.repaint();
            
            try {
                sleep(30);
            } catch (InterruptedException ex) {}
        }
    }
}
