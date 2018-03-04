package chopchop;

import java.awt.Graphics;
import javax.swing.JApplet;

/**
 *
 * @author FaraZ
 */
public class ChopChop extends JApplet {

    final int GAME_WIDTH = 2000;

    ScreenManager sm;
    Refresher refresh;
    Generator generator;
    SoundBank soundbank;

    Player player;

    //enemies
    Alpha alpha;
    Bravo bravo;
    Charlie charlie;
    Delta delta;
    Echo echo;
    Foxtrot foxtrot;

    BlackBox reward;
    Time timer;

    @Override
    public void init() {

        setSize(800, 600);

        sm = new ScreenManager(this);
        generator = new Generator(sm);
        generator.start();

        soundbank = new SoundBank(sm);

        player = new Player(570, 300, 1, 80, MotherChopper.Weapon.pistol, 20, sm);
        sm.addObject(player);
        
        timer = new Time(player.x, player.y, 60, sm);
        sm.addObject(timer);
        Thread time = new Thread (timer);
        time.start();

        alpha = new Alpha(200, 400, 2, 40, MotherChopper.Weapon.pistol, 20, sm);
        sm.addObject(alpha);
        Thread alpha1 = new Thread(alpha);
        alpha1.start();

        bravo = new Bravo(100, 100, 2, 20, MotherChopper.Weapon.pistol, 20, sm);
        sm.addObject(bravo);
        Thread bravo1 = new Thread(bravo);
        bravo1.start();

        charlie = new Charlie(-1000, 0, 2, 60, MotherChopper.Weapon.pistol, 20, sm);
        sm.addObject(charlie);
        Thread charlie1 = new Thread(charlie);
        charlie1.start();

        delta = new Delta(-1000, 40, 2, 30, MotherChopper.Weapon.pistol, 20, sm);
        sm.addObject(delta);
        Thread delta1 = new Thread(delta);
        delta1.start();

        echo = new Echo(-1000, 80, 2, 60, MotherChopper.Weapon.laser, 20, sm);
        sm.addObject(echo);
        Thread echo1 = new Thread(echo);
        echo1.start();

        foxtrot = new Foxtrot(-1000, 120, 2, 9000, MotherChopper.Weapon.shotgun, 50, sm);
        sm.addObject(foxtrot);
        Thread foxtrot1 = new Thread(foxtrot);
        foxtrot1.start();

        setFocusable(true);
        requestFocus();

        refresh = new Refresher(this);
        refresh.start();

    }

    @Override
    public void paint(Graphics g) {

        sm.draw(g);
    }

}
