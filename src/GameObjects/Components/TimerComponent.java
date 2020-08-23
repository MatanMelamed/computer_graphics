// Matan Melamed 205973613
package GameObjects.Components;

import GameObjects.GameObjectComponent;

public class TimerComponent extends GameObjectComponent {

    private Runnable timeUpCallback;
    private float currentTime;
    private float lastTimeToMeassure;
    private boolean shouldRun;

    public TimerComponent(Runnable timeUpCallback, float currentTime) {
        this.timeUpCallback = timeUpCallback;
        this.shouldRun = false;
        SetTime(currentTime);
    }

    public TimerComponent() { this(null, 0); }

    public void SetCallback(Runnable newCallback) {this.timeUpCallback = newCallback;}

    public void SetTime(float time) {lastTimeToMeassure = time * 1000f;}

    public void Start() {
        currentTime = lastTimeToMeassure;
        shouldRun = true;
    }

    @Override
    public void Initialize() {}

    public boolean IsRunning() {return currentTime != 0;}

    @Override
    public void Update(float deltaTime) {
        if (!shouldRun) return;

        if (currentTime <= 0) {
            shouldRun = false;
            currentTime = 0;
            timeUpCallback.run();
        } else {
            currentTime -= deltaTime;
        }
    }

    @Override
    public void Render() {}
}
