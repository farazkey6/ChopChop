package chopchop;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author FaraZ
 */
public class Player extends MotherChopper {

    int bulletsleft, laserleft, rocketsleft, money, life, kills, killstreak;

    int shots0, shots1, shots2, shots3;
    int saved, lost;

    int offset, HUDoffset, gunOffset, shopOffset, Ylimit, Xlimit;

    long lastShot;

    boolean keys[];
    boolean isLaser;
    boolean nukeEnabled;

    boolean rocker = true; //permission to boOm :D
    boolean dead = false;

    enum Upgrades {

        health, damage, live, nuke
    }
    Image minigun, cannon, shotgun, laser, shop;
    Image bullets, rockets, shells, lasers;
    Image nuke;

    public Player(int x, int y, int team, int health, Weapon weapon, int damage, ScreenManager sm) {
        super(x, y, team, health, weapon, damage, sm);

        this.rocketsleft = 1;
        this.bulletsleft = 10;
        this.laserleft = 50;
        this.money = 100;
        this.life = 3;

        shop = sm.parent.getImage(sm.parent.getCodeBase(), "shop.jpg");
        nuke = sm.parent.getImage(sm.parent.getCodeBase(), "nuke.jpg");

        shotgun = sm.parent.getImage(sm.parent.getCodeBase(), "shotgun.jpg");
        minigun = sm.parent.getImage(sm.parent.getCodeBase(), "minigun.jpg");
        cannon = sm.parent.getImage(sm.parent.getCodeBase(), "cannon.jpg");
        laser = sm.parent.getImage(sm.parent.getCodeBase(), "laser.jpg");

        lasers = sm.parent.getImage(sm.parent.getCodeBase(), "lasers.png");
        bullets = sm.parent.getImage(sm.parent.getCodeBase(), "minigun.png");
        rockets = sm.parent.getImage(sm.parent.getCodeBase(), "rocket.png");
        shells = sm.parent.getImage(sm.parent.getCodeBase(), "shells.png");

        img[0] = sm.parent.getImage(sm.parent.getCodeBase(), "player0.jpg");
        img[1] = sm.parent.getImage(sm.parent.getCodeBase(), "player1.jpg");

        keys = new boolean[5];

        Ylimit = 0;
        Xlimit = sm.parent.GAME_WIDTH;

        shots0 = 0;
        shots3 = 0;
        shots2 = 0;
        shots1 = 0;

        saved = 0;
        lost = 0;

        isLaser = false;
        nukeEnabled = false;

        velocity = 10;
        
        sm.parent.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {

                if (e.getKeyCode() == KeyEvent.VK_UP) {

                    if (isAlive) {
                        keys[0] = true;
                    }
                }

                if (e.getKeyCode() == KeyEvent.VK_DOWN) {

                    if (isAlive) {
                        keys[1] = true;
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

                    if (isAlive) {
                        keys[2] = true;
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {

                    if (isAlive) {
                        keys[3] = true;
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {

                    if (!isAlive) {
                        respawn();
                    } else {
                        keys[4] = true;
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_1) {

                    setWeapon(Weapon.pistol);
                }
                if (e.getKeyCode() == KeyEvent.VK_2) {

                    setWeapon(Weapon.shotgun);
                }
                if (e.getKeyCode() == KeyEvent.VK_3) {

                    setWeapon(Weapon.laser);
                }
                if (e.getKeyCode() == KeyEvent.VK_4) {

                    setWeapon(Weapon.rocket);
                }

                if (e.getKeyCode() == KeyEvent.VK_N) {

                    if (nukeEnabled) {
                        nuke();
                    }
                }

                if (e.getKeyCode() == KeyEvent.VK_Q) {

                    EarthQuake();
                }

                if (keys[0]) {
                    up();
                }
                if (keys[1]) {
                    down();
                }
                if (keys[2]) {
                    right();
                }
                if (keys[3]) {
                    left();
                }
                if (keys[4]) {
                    if (System.currentTimeMillis() - lastShot > 100 || isLaser) {
                        shoot();
                        lastShot = System.currentTimeMillis();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

                if (e.getKeyCode() == KeyEvent.VK_UP) {

                    keys[0] = false;
                }

                if (e.getKeyCode() == KeyEvent.VK_DOWN) {

                    keys[1] = false;
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

                    keys[2] = false;
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {

                    keys[3] = false;
                }
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {

                    keys[4] = false;
                }
            }

        });

    }

    @Override
    public void setWeapon(Weapon x) {

        weapon = x;
        if (x == Weapon.laser) {

            isLaser = true;
        } else {

            isLaser = false;
        }

        switch (x) {

            case pistol:
                if (this.y < 80 && money >= 100) {
                    money -= 100;
                    bulletsleft += 50;
                }
                break;
            case shotgun:
                if (this.y < 80 && money >= 300) {
                    money -= 300;
                    bulletsleft += 200;
                }
                break;
            case rocket:
                if (this.y < 80 && money >= 500) {
                    money -= 500;
                    rocketsleft += 10;
                }
                break;
            case laser:
                if (this.y < 80 && money >= 350) {
                    money -= 350;
                    laserleft += 300;
                }
                break;
        }
    }

    public Weapon weapon() {

        return weapon;
    }

    @Override
    public void pistol() {

        if (bulletsleft > 0) {
            sm.parent.soundbank.minigun[sound % 6].stop();
            sound++;
            sm.parent.soundbank.minigun[sound % 6].play();
            Bullet bullet = new Bullet(x + 60, y + 30, team, damage, face, sm, 0);
            sm.addObject(bullet);
            Thread t = new Thread(bullet);
            t.start();
            bulletsleft--;
            shots0++;
        }
    }

    @Override
    public void shotgun() {

        if (bulletsleft > 2) {
            sm.parent.soundbank.shotgun[sound % 6].stop();
            sound++;
            sm.parent.soundbank.shotgun[0].play();
            Bullet b1 = new Bullet(x + 60, y + 30, team, damage, face, sm, 0);
            Bullet b2 = new Bullet(x + 60, y + 25, team, damage, face, sm, 1);
            Bullet b3 = new Bullet(x + 60, y + 35, team, damage, face, sm, 2);

            sm.addObject(b1);
            sm.addObject(b2);
            sm.addObject(b3);

            Thread t1 = new Thread(b1);
            t1.start();
            Thread t2 = new Thread(b2);
            t2.start();
            Thread t3 = new Thread(b3);
            t3.start();
            bulletsleft -= 3;
            shots1++;
        }
    }

    @Override
    public void rocket() {

        if (rocketsleft > 0 && rocker) {
            sm.parent.soundbank.rocket.play();
            Rocket rocket = new Rocket(x + 50, y + 15, team, damage * 20, face, sm);

            sm.addObject(rocket);

            Thread r = new Thread(rocket);
            r.start();
            rocketsleft--;
            shots3++;
            rocker = false;
        }
    }

    @Override
    public void laser() {

        if (laserleft > 0) {

            Laser laser = new Laser(x, y, team, damage / 10, face, sm);

            sm.addObject(laser);

            Thread l = new Thread(laser);
            l.start();
            laserleft--;
            shots2++;
        }
    }

    public void nuke() {

        sm.parent.soundbank.nuke.play();
        sm.nuke();
        nukeEnabled = false;
    }

    public void EarthQuake() {

        sm.EarthQuake();
    }

    public void income(int x) {

        this.money += x;
    }

    @Override
    public void health(int damage) {
        if (isAlive && damage > 0) {
            health -= damage;
        }

        if (health <= 0) {
            isAlive = false;
            flicker = true;
            sm.parent.soundbank.death.play();
        }
    }

    @Override
    public void respawn() {

        if (life > 0) {

            health = maxHealth;
            life--;
            killstreak = 0;
            flicker = false;
            isAlive = true;
        } else {
            GameOver();
        }
    }

    public void upgrade(Upgrades up) {

        switch (up) {

            case health:
                if (maxHealth <= 230) {
                    maxHealth += 50;
                }
                health = maxHealth;
                break;
            case damage:
                damage += 20;
                break;
            case live:
                life += 1;
                break;
            case nuke:
                nukeEnabled = true;
                break;
        }
    }

    public void killStreak() {

        switch (killstreak) { //killstreak rewards

            case 5:
                switch (face) {
                    case RIGHT:
                        BlackBox healthr = new BlackBox(x + 150, -50, sm, Upgrades.health);
                        sm.addObject(healthr);
                        Thread thr = new Thread(healthr);
                        thr.start();
                        break;

                    case LEFT:
                        BlackBox healthl = new BlackBox(x - 150, -50, sm, Upgrades.health);
                        sm.addObject(healthl);
                        Thread thl = new Thread(healthl);
                        thl.start();
                        break;
                }
                break;
            case 10:
                switch (face) {
                    case RIGHT:
                        BlackBox damager = new BlackBox(x + 150, -50, sm, Upgrades.damage);
                        sm.addObject(damager);
                        Thread tdr = new Thread(damager);
                        tdr.start();
                        break;

                    case LEFT:
                        BlackBox damagel = new BlackBox(x - 150, -50, sm, Upgrades.damage);
                        sm.addObject(damagel);
                        Thread tdl = new Thread(damagel);
                        tdl.start();
                        break;
                }
                break;

            case 20:
                switch (face) {
                    case RIGHT:
                        BlackBox lifer = new BlackBox(x + 150, -50, sm, Upgrades.live);
                        sm.addObject(lifer);
                        Thread tlr = new Thread(lifer);
                        tlr.start();
                        break;

                    case LEFT:
                        BlackBox lifel = new BlackBox(x - 150, -50, sm, Upgrades.live);
                        sm.addObject(lifel);
                        Thread tll = new Thread(lifel);
                        tll.start();
                        break;
                }
                break;

            case 50:
                switch (face) {
                    case RIGHT:
                        BlackBox nuker = new BlackBox(x + 150, -50, sm, Upgrades.nuke);
                        sm.addObject(nuker);
                        Thread tNr = new Thread(nuker);
                        tNr.start();
                        break;

                    case LEFT:
                        BlackBox nukel = new BlackBox(x - 150, -50, sm, Upgrades.nuke);
                        sm.addObject(nukel);
                        Thread tNl = new Thread(nukel);
                        tNl.start();
                        break;
                }
                killstreak = 0;
                break;
        }
    }

    public boolean canMove(FACE face) {

        switch (face) {

            case LEFT:
                if (x - velocity <= 0) {
                    return false;
                }
                break;
            case RIGHT:
                if (x + velocity + 60 >= Xlimit) {
                    return false;
                }
                break;
            case UP:
                if (y - velocity <= Ylimit) {
                    return false;
                }
                break;
            case DOWN:
                if (y + velocity + 30 >= sm.parent.getHeight() - 100) {
                    return false;
                }
                break;
        }
        return true;
    }

    public void draw(Graphics g) {

        if (x < sm.parent.GAME_WIDTH / 2) { //HUD
            offset = Math.max(x - 350 + HUDoffset, 10);
        } else {
            offset = Math.min(x - 350 + HUDoffset, sm.parent.GAME_WIDTH - sm.parent.getWidth() + 10);
        }
        if (life == 0) {
            g.setColor(Color.red);                                                     //health bar and nuke icon
        }
        g.drawString(life + "X", offset, 20);
        g.setColor(Color.red);                                                     //health bar
        g.fillRect(offset + 15, 10, maxHealth, 10);
        g.setColor(Color.green);
        g.fillRect(offset + 15, 10, health, 10);

        if (nukeEnabled) {
            g.drawImage(nuke, offset + 20 + maxHealth, 7, sm.parent);
        }
        
        g.setColor(Color.black);
        g.drawString("Money: " + money, offset, 35);
        g.drawString("Bullets: " + bulletsleft + "    Laser: " + laserleft, offset, 50);
        g.drawString("Rockets: " + rocketsleft, offset, 65);

        if (x < 1000) {
            offset = Math.max(x - 100 + shopOffset, 260);
        } else {
            offset = Math.min(x - 100 + shopOffset, sm.parent.GAME_WIDTH - 540);
        }
        if (!sm.parent.foxtrot.begun) {
            g.drawString("$100 = 50x", offset, 25);
            g.drawImage(bullets, offset + 65, 5, sm.parent);

            g.drawString("$300 = 200x", offset + 105, 25);
            g.drawImage(shells, offset + 175, 5, sm.parent);
//        
            g.drawString("$350 = 300x", offset + 105, 60);
            g.drawImage(lasers, offset + 175, 40, sm.parent);

            g.drawString("$500 = 10x", offset, 60);
            g.drawImage(rockets, offset + 65, 40, sm.parent);
        } else {

            g.drawImage(shop, offset + 80, 0, sm.parent);
        }

        if (x < 1000) { //Gun GUI
            offset = Math.max(x + 440 - 150 + gunOffset, sm.parent.getWidth() - 150);
        } else {
            offset = Math.min(x + 440 - 150 + gunOffset, sm.parent.GAME_WIDTH - 150);
        }

        g.setColor(Color.BLUE);
        g.drawLine(offset - 10, 0, offset - 10, 80);

        if (isAlive) {
            switch (face) {

                case RIGHT:
                    g.drawImage(img[0], x, y, sm.parent);
                    break;
                case LEFT:
                    g.drawImage(img[1], x, y, sm.parent);
                    break;
            }
        }

        switch (this.weapon) {

            case pistol:
                g.drawImage(minigun, offset, 0, sm.parent);
                break;
            case rocket:
                g.drawImage(cannon, offset, 0, sm.parent);
                break;
            case shotgun:
                g.drawImage(shotgun, offset, 0, sm.parent);
                break;
            case laser:
                g.drawImage(laser, offset, 0, sm.parent);
                break;
        }
        if (flicker) {

            g.drawImage(flickimg, x, y, sm.parent);
        }

        if (x < 1000) {
            offset = Math.max(x, 360);
        } else {
            offset = Math.min(x, sm.parent.GAME_WIDTH - 440);
        }
        g.setColor(Color.GREEN);
        g.drawRect(offset - 80, sm.parent.getHeight() - 45, 165, 44);
        g.drawOval(offset, sm.parent.getHeight() - 50 + 22, 15, 13);

        if (dead) {

            g.setColor(Color.red);
            g.drawString("Game Over", x, y - 15);
            g.drawString("You Slayed " + kills + " Bad Guys !", x + 60, y + 30);
            g.drawString("And You Fired: ", x + 60, y + 45);
            g.drawString(shots0 + " Bullets", x + 60, y + 60);
            g.drawString(shots1 + " Shells", x + 60, y + 75);
            g.drawString(shots2 + " Plasma", x + 60, y + 90);
            g.drawString(shots3 + " Rockets", x + 60, y + 105);

            g.drawString("You Saved " + saved + " Cargo(s)", x + 60, y);
            g.drawString("You Let " + lost + " Cargo(s) Burn", x + 60, y + 15);
        }

    }

    public void GameOver() {

        exists = false;
        dead = true;
        sm.parent.generator.isAlive = false;
    }

    @Override
    public void drawMap(Graphics g) {

    }
}
