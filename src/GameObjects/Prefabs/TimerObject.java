// Matan Melamed 205973613
package GameObjects.Prefabs;

import GameObjects.Components.TimerComponent;
import GameObjects.GameObject;

public class TimerObject extends GameObject {

    private TimerComponent timer;

    public TimerObject() {
        this(null, 0);
    }

    public TimerObject(Runnable r, float seconds) {
        super("timer object");
        timer = new TimerComponent(r, seconds);
        AddComponent(timer);
    }

    public void SetTimer(Runnable r, float seconds) {
        timer.SetCallback(r);
        timer.SetTime(seconds);
    }

    public void Start() {
        timer.Start();
    }

    public boolean IsRunning(){return timer.IsRunning();}
}
