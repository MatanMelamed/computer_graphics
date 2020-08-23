// Matan Melamed 205973613
package Core.Sound;

import java.util.HashMap;

public class SoundManager {
    private static SoundManager instance = new SoundManager();

    private HashMap<Sound, SoundPlayer> soundPlayers;
    private Thread playingThread;
    private SoundPlayer current;

    private SoundManager() {
        soundPlayers = new HashMap<>();
        soundPlayers.put(Sound.Victory, new SoundPlayer("/sound/victory.wav"));
        soundPlayers.put(Sound.Ding, new SoundPlayer("/sound/b.wav"));
        soundPlayers.put(Sound.Begin, new SoundPlayer("/sound/begin.wav"));
        soundPlayers.put(Sound.Defeat, new SoundPlayer("/sound/scream.wav"));
        soundPlayers.put(Sound.Background, new SoundPlayer("/sound/chase_music.wav", true));
    }

    public static void Play(Sound sound) {
        instance.current = instance.soundPlayers.get(sound);
        instance.playingThread = new Thread(() -> instance.soundPlayers.get(sound).Play());
        instance.playingThread.start();
    }

    public static void Stop() {
        if (instance.current != null) {
            instance.current.Stop();
            instance.current = null;
            instance.playingThread.stop();
        }
    }
}
