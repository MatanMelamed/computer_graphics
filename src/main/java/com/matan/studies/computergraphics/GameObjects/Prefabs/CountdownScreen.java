// Matan Melamed 205973613
package com.matan.studies.computergraphics.GameObjects.Prefabs;

import com.matan.studies.computergraphics.Core.Sound.Sound;
import com.matan.studies.computergraphics.Core.Sound.SoundManager;
import com.matan.studies.computergraphics.GameObjects.Components.TimerComponent;
import com.matan.studies.computergraphics.GameObjects.GameObject;
import com.matan.studies.computergraphics.Models.Axis;

import java.util.ArrayList;

public class CountdownScreen extends GameObject {

    private ArrayList<PlateObject> screenNumbers;
    private TimerComponent timer;
    private int count;
    private Runnable onFinishCallback;
    private boolean isRunning = false;

    public CountdownScreen(Runnable onFinishCallback) {
        super("count down screen object");
        this.onFinishCallback = onFinishCallback;
        timer = new TimerComponent(this::TimeUp, 1f);
        AddComponent(timer);

        screenNumbers = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            PlateObject newScreen = new PlateObject(String.valueOf(i), i + ".jpg", 1, 1, 16, 9);
            newScreen.Rotate(Axis.X, -90);
            newScreen.Move(0, 2000, 0);
            newScreen.Disable();
            screenNumbers.add(newScreen);
            AddChild(newScreen);
        }
    }

    public boolean IsRunning() {
        return isRunning;
    }

    public void Start() {
        isRunning = true;
        count = screenNumbers.size() - 1;
        TimeUp();
        timer.Start();
    }

    private void TimeUp() {
        SoundManager.Play(Sound.Ding);
        if (count < screenNumbers.size() - 1) {
            screenNumbers.get(count + 1).Disable();
        }
        screenNumbers.get(count).Enable();
        count--;

        if (count >= 0) {
            timer.Start();
        } else {
            onFinishCallback.run();
            isRunning = false;
        }
    }
}
