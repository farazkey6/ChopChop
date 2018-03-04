package chopchop;

import chopchop.Player.Upgrades;
import java.awt.Graphics;
import java.awt.Image;
import static java.lang.Thread.sleep;

/**
 *
 * @author FaraZ
 */
public class BlackBox extends ScreenObject implements Runnable {

    Upgrades upgrade;
    int velocity;
    Image life, health, damage, radio, wing;
    boolean eagle;

    int shadoX;

    public BlackBox(int x, int y, ScreenManager sm, Upgrades upgrade) {
        super(x, y, sm);

        this.velocity = 5;
        this.upgrade = upgrade;
        wing = sm.parent.getImage(sm.parent.getCodeBase(), "wing.jpg");
        life = sm.parent.getImage(sm.parent.getCodeBase(), "lifeup.jpg");
        health = sm.parent.getImage(sm.parent.getCodeBase(), "healthup.jpg");
        damage = sm.parent.getImage(sm.parent.getCodeBase(), "damageup.png");
        radio = sm.parent.getImage(sm.parent.getCodeBase(), "radio.jpg");
    }

    @Override
    public void draw(Graphics g) {

        if (eagle) {

            g.drawImage(wing, shadoX, sm.parent.getHeight() - 100, sm.parent);
        }
        switch (upgrade) {

            case live:
                g.drawImage(life, x, y, sm.parent);
                break;
            case health:
                g.drawImage(health, x, y, sm.parent);
                break;
            case damage:
                g.drawImage(damage, x, y, sm.parent);
                break;
            case nuke:
                g.drawImage(radio, x, y, sm.parent);
                break;
        }
    }

    public void upgrade(Upgrades upgrade) {

        sm.parent.player.upgrade(upgrade);
    }

    @Override
    public void run() {

        sm.parent.soundbank.eagle.play();

        switch (sm.parent.player.face) {
            case RIGHT:
                shadoX = -100;
                while (shadoX < sm.parent.GAME_WIDTH) {

                    eagle = true;
                    shadoX += 13;
                    try {
                        sleep(3);
                    } catch (InterruptedException ex) {
                    }
                }
                break;
            case LEFT:
                shadoX = sm.parent.GAME_WIDTH + 100;
                while (shadoX > 0) {

                    eagle = true;
                    shadoX -= 13;
                    try {
                        sleep(3);
                    } catch (InterruptedException ex) {
                    }
                }
                break;
        }
        eagle = false;

        while (y + 4 * velocity < sm.parent.getHeight() - 150) {

            y += velocity;
            try {
                sleep(40);
            } catch (InterruptedException ex) {
            }
        }
    }

}
