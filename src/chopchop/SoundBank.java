package chopchop;

import java.applet.AudioClip;

/**
 *
 * @author FaraZ
 */
public class SoundBank extends Thread {

    AudioClip[] minigun, shotgun, laser;
    AudioClip rocket;

    AudioClip eagle, death, fall, stab, thunder, beep, comet, nuke;
    ScreenManager sm;

    public SoundBank(ScreenManager sm) {

        this.sm = sm;

        minigun = new AudioClip[6];
        shotgun = new AudioClip[6];
        laser = new AudioClip[6];

        for (int i = 0; i < 6; i++) {
            minigun[i] = sm.parent.getAudioClip(sm.parent.getCodeBase(), "bullet" + i + ".wav");
            shotgun[i] = sm.parent.getAudioClip(sm.parent.getCodeBase(), "gunshot" + i + ".wav");
        }

        rocket = sm.parent.getAudioClip(sm.parent.getCodeBase(), "rocket.wav");

        eagle = sm.parent.getAudioClip(sm.parent.getCodeBase(), "eagle.wav");
        death = sm.parent.getAudioClip(sm.parent.getCodeBase(), "death.wav");
        fall = sm.parent.getAudioClip(sm.parent.getCodeBase(), "fall.wav");
        stab = sm.parent.getAudioClip(sm.parent.getCodeBase(), "stab.wav");
        beep = sm.parent.getAudioClip(sm.parent.getCodeBase(), "beep.wav");
        comet = sm.parent.getAudioClip(sm.parent.getCodeBase(), "comet.wav");
        nuke = sm.parent.getAudioClip(sm.parent.getCodeBase(), "nuke.wav");
        thunder = sm.parent.getAudioClip(sm.parent.getCodeBase(), "thunder.wav");

    }
}
