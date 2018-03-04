package chopchop;

/**
 *
 * @author FaraZ
 */
public class Generator extends Thread{
    
    ScreenManager sm;
    boolean isAlive;
    
    public Generator(ScreenManager sm){
        
        this.sm = sm;
        isAlive = true;
    }
    
    public void run(){
        
        while(isAlive){
            
            Cargo C = new Cargo(sm.parent.GAME_WIDTH, 450, 90, sm);
            sm.addObject(C);
            Thread t = new Thread(C);
            t.start();
            try {
                sleep(10000);
            } catch (InterruptedException ex) {
            }
        }
    }
}
