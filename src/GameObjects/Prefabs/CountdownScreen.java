package GameObjects.Prefabs;

import GameObjects.Components.TimerComponent;
import GameObjects.GameObject;

public class Countdown extends GameObject {

    TimerComponent timer;

    public Countdown() {
        timer = new TimerComponent(this::TimeUp, 1);
        AddComponent(timer);
    }

    public void TimeUp() {

    }
}
