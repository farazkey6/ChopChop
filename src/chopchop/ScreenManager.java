package chopchop;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

/**
 *
 * @author FaraZ
 */
class ScreenManager {

    ChopChop parent;

    ArrayList<ScreenObject> ol;

    ArrayList<MotherChopper> copters;

    Image BackGround, img, stars, thunder;

    int viewX;

    public ScreenManager(ChopChop parent) {

        this.parent = parent;

        stars = parent.getImage(parent.getCodeBase(), "background.jpg");
        thunder = parent.getImage(parent.getCodeBase(), "thunder.jpg");

        ol = new ArrayList<>();

    }

    public void addObject(ScreenObject so) {

        ol.add(so);
    }

    public void removeObject(ScreenObject so) {

        ol.remove(so);
    }

    public void nuke() {

        ArrayList<MotherChopper> choppers = new ArrayList<>();

        for (int i = 0; i < ol.size(); i++) {

            ScreenObject so = ol.get(i);
            if (so instanceof MotherChopper) {
                choppers.add((MotherChopper) so);
            }

        }

        for (int i = 0; i < choppers.size(); i++) {

            if (!((choppers.get(i) instanceof Player) && (choppers.get(i) instanceof Foxtrot))) {

                choppers.get(i).respawn();
            }
        }
    }

    public void EarthQuake() {

        ArrayList<Cargo> cargos = new ArrayList<>();

        for (int i = 0; i < ol.size(); i++) {

            ScreenObject so = ol.get(i);

            if (so instanceof Cargo) {
                cargos.add((Cargo) so);
            }
        }
        for (int i = 0; i < cargos.size(); i++) {
            
            cargos.get(i).health(100);
        }
    }

    public void conflict() {

        ArrayList<Bullet> bullets = new ArrayList<>();
        ArrayList<Rocket> rockets = new ArrayList<>();
        ArrayList<Laser> lasers = new ArrayList<>();
        ArrayList<SuperLaser> supers = new ArrayList<>();
        ArrayList<MotherChopper> choppers = new ArrayList<>();
        ArrayList<BlackBox> rewards = new ArrayList<>();
        ArrayList<Cargo> cargos = new ArrayList<>();

        for (int i = 0; i < ol.size(); i++) {

            ScreenObject so = ol.get(i);

            if (so instanceof Bullet) {
                bullets.add((Bullet) so);
            }

            if (so instanceof Rocket) {
                rockets.add((Rocket) so);
            }

            if (so instanceof Laser) {
                lasers.add((Laser) so);
            }

            if (so instanceof SuperLaser) {
                supers.add((SuperLaser) so);
            }

            if (so instanceof MotherChopper) {
                choppers.add((MotherChopper) so);
            }

            if (so instanceof BlackBox) {
                rewards.add((BlackBox) so);
            }

            if (so instanceof Cargo) {
                cargos.add((Cargo) so);
            }

        }

        for (int j = 0; j < choppers.size(); j++) {

            if (choppers.get(j) instanceof Foxtrot) {

                MotherChopper chopper = choppers.get(j);

                for (int i = 0; i < bullets.size(); i++) {

                    Bullet bullet = bullets.get(i);
                    int blltx = bullet.x + 2;    // 2 is for precision
                    int bllty = bullet.y + 2;

                    if (blltx >= chopper.x && blltx <= chopper.x + 300 && bllty >= chopper.y && bllty <= chopper.y + 150) {
                        chopper.health(bullet.damage);
                        removeObject(bullet);
                    }
                }

                for (int m = 0; m < rockets.size(); m++) {

                    Rocket rocket = rockets.get(m);
                    int rocketX = 0, rocketY = 0;

                    switch (rocket.face) {

                        case RIGHT:
                            rocketX = rocket.x + 15;
                            rocketY = rocket.y + 7;
                            break;

                        case LEFT:
                            rocketX = rocket.x;
                            rocketY = rocket.y + 7;
                            break;

                        default:
                            break;
                    }

                    if (rocketX >= chopper.x && rocketX <= chopper.x + 300 && rocketY >= chopper.y && rocketY <= chopper.y + 150) {
                        chopper.health(rocket.damage);
                        removeObject(rocket);
                        parent.player.rocker = true;
                    }
                }
            }

            if (choppers.get(j) instanceof Player) { //chopper collision & reward collecting

                for (int c = 0; c < rewards.size(); c++) {

                    if ((choppers.get(j).x >= rewards.get(c).x && choppers.get(j).x <= rewards.get(c).x + 40 //upper left
                            && choppers.get(j).y >= rewards.get(c).y && choppers.get(j).y <= rewards.get(c).y + 20)
                            || (choppers.get(j).x + 60 >= rewards.get(c).x && choppers.get(j).x + 60 <= rewards.get(c).x + 40 //upper right
                            && choppers.get(j).y >= rewards.get(c).y && choppers.get(j).y <= rewards.get(c).y + 20)
                            || (choppers.get(j).x >= rewards.get(c).x && choppers.get(j).x <= rewards.get(c).x + 40 //below left
                            && choppers.get(j).y + 30 >= rewards.get(c).y && choppers.get(j).y + 30 <= rewards.get(c).y + 20)
                            || (choppers.get(j).x + 60 >= rewards.get(c).x && choppers.get(j).x + 60 <= rewards.get(c).x + 40 //below right
                            && choppers.get(j).y + 30 >= rewards.get(c).y && choppers.get(j).y + 30 <= rewards.get(c).y + 20)) {

                        parent.player.upgrade(rewards.get(c).upgrade);
                        removeObject(rewards.get(c));
                    }
                }

                for (int k = 0; k < choppers.size(); k++) {

                    if ((choppers.get(j).x > choppers.get(k).x && choppers.get(j).x < choppers.get(k).x + 60 //upper left
                            && choppers.get(j).y > choppers.get(k).y && choppers.get(j).y < choppers.get(k).y + 30)
                            || (choppers.get(j).x + 60 > choppers.get(k).x && choppers.get(j).x + 60 < choppers.get(k).x + 60 //upper right
                            && choppers.get(j).y > choppers.get(k).y && choppers.get(j).y < choppers.get(k).y + 30)
                            || (choppers.get(j).x > choppers.get(k).x && choppers.get(j).x < choppers.get(k).x + 60 //below left
                            && choppers.get(j).y + 30 > choppers.get(k).y && choppers.get(j).y + 30 < choppers.get(k).y + 30)
                            || (choppers.get(j).x + 60 > choppers.get(k).x && choppers.get(j).x + 60 < choppers.get(k).x + 60 //below right
                            && choppers.get(j).y + 30 > choppers.get(k).y && choppers.get(j).y + 30 < choppers.get(k).y + 30)) {
                        int tempHealth = choppers.get(j).health;
                        choppers.get(j).health(choppers.get(k).health);
                        choppers.get(k).health(tempHealth);

                    }
                }

                for (int t = 0; t < supers.size(); t++) {

                    SuperLaser lazer = supers.get(t);

                    if (choppers.get(j).y + 30 > lazer.y && choppers.get(j).y < lazer.y + 90) {

                        parent.player.health(999);
                    }
                }
            }

            MotherChopper chopper = choppers.get(j);

            for (int i = 0; i < bullets.size(); i++) {

                Bullet bullet = bullets.get(i);
                int blltx = bullet.x + 2;    // 2 is for precision
                int bllty = bullet.y + 2;

                if (blltx >= chopper.x && blltx <= chopper.x + 60 && bllty >= chopper.y && bllty <= chopper.y + 30) {
                    chopper.health(bullet.damage);
                    removeObject(bullet);
                }
            }

            for (int k = 0; k < rockets.size(); k++) {

                Rocket rocket = rockets.get(k);
                int rocketX = 0, rocketY = 0;

                switch (rocket.face) {

                    case RIGHT:
                        rocketX = rocket.x + 15;
                        rocketY = rocket.y + 7;
                        break;

                    case LEFT:
                        rocketX = rocket.x;
                        rocketY = rocket.y + 7;
                        break;

                    default:
                        break;
                }

                if (rocketX >= chopper.x && rocketX <= chopper.x + 60 && rocketY >= chopper.y && rocketY <= chopper.y + 30) {
                    chopper.health(rocket.damage);
                    removeObject(rocket);
                    parent.player.rocker = true;
                }
            }
            for (int x = 0; x < lasers.size(); x++) {

                Laser laser = lasers.get(x);
                int X = 0, Y = 0;

                switch (laser.face) {

                    case RIGHT:
                        X = laser.x + 10;
                        Y = laser.y + 25;
                        if (X <= chopper.x && Y >= chopper.y && Y <= chopper.y + 30) {
                            chopper.health(laser.damage);
                        }
                        break;

                    case LEFT:
                        X = laser.x - 50;
                        Y = laser.y;
                        if (X >= chopper.x && Y >= chopper.y && Y <= chopper.y + 30) {
                            chopper.health(laser.damage);
                        }
                        break;

                    default:
                        break;
                }
            }
        }

        for (int k = 0; k < cargos.size(); k++) {

            Cargo go = cargos.get(k);

            for (int i = 0; i < bullets.size(); i++) {

                Bullet bullet = bullets.get(i);
                int blltx = bullet.x + 2;    // 2 is for precision
                int bllty = bullet.y + 2;

                if (blltx >= go.x && blltx <= go.x + go.length && bllty >= go.y && bllty <= go.y + 30) {
                    go.health(bullet.damage);
                    removeObject(bullet);
                }
            }

            for (int m = 0; m < rockets.size(); m++) {

                Rocket rocket = rockets.get(m);
                int rocketX = 0, rocketY = 0;

                switch (rocket.face) {

                    case RIGHT:
                        rocketX = rocket.x + 15;
                        rocketY = rocket.y + 7;
                        break;

                    case LEFT:
                        rocketX = rocket.x;
                        rocketY = rocket.y + 7;
                        break;

                    default:
                        break;
                }

                if (rocketX >= go.x && rocketX <= go.x + go.length && rocketY >= go.y && rocketY <= go.y + 30) {
                    go.health(rocket.damage);
                    removeObject(rocket);
                    parent.player.rocker = true;
                }
            }
            for (int n = 0; n < lasers.size(); n++) {

                Laser laser = lasers.get(n);
                int X = 0, Y = 0;

                switch (laser.face) {

                    case RIGHT:
                        X = laser.x + 10;
                        Y = laser.y + 25;
                        if (X <= go.x && Y >= go.y && Y <= go.y + 30) {
                            go.health(laser.damage);
                        }
                        break;

                    case LEFT:
                        X = laser.x - 50;
                        Y = laser.y;
                        if (X >= go.x && Y >= go.y && Y <= go.y + 30) {
                            go.health(laser.damage);
                        }
                        break;

                    default:
                        break;
                }
            }
        }
    }

    public void locate() {

        if (!parent.foxtrot.begun) {
            if (parent.player.x > parent.GAME_WIDTH / 2) {
                viewX = Math.min(parent.GAME_WIDTH, parent.player.x + 440) - parent.getWidth();
            } else {
                viewX = Math.max(0, parent.player.x - 360);
            }
        } else {

            if (parent.player.x > parent.GAME_WIDTH / 2) {
                viewX = Math.min(parent.GAME_WIDTH - 800, parent.player.x);
            } else {
                viewX = Math.max(0, parent.player.x);
            }
        }
    }

    public void draw(Graphics g) {

        copters = new ArrayList<>();
        conflict();
        locate();
        Image offImg = parent.createImage(parent.GAME_WIDTH, parent.getHeight());
        Graphics offg = offImg.getGraphics();
        offg.drawImage(BackGround, 0, 0, parent.getWidth(), parent.getHeight(), parent);
        offg.translate(-viewX, 0);
        if (!parent.foxtrot.begun) {
            offg.drawImage(stars, 0, 0, parent);
        } else {
            offg.drawImage(thunder, 0, 0, parent);
        }

        for (int i = 0; i < ol.size(); i++) {

            try {
                ol.get(i).draw(offg);
            } catch (NullPointerException e) {

            }
            if (ol.get(i) instanceof MotherChopper) {

                copters.add((MotherChopper) ol.get(i));
            }
        }
        for (int j = 0; j < copters.size(); j++) {

            copters.get(j).drawMap(offg);
        }
        offg.setColor(Color.blue);
        offg.drawLine(0, 80, parent.GAME_WIDTH, 80);
        g.drawImage(offImg, 0, 0, parent);

    }
}
