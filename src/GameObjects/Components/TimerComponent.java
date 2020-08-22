package GameObjects.Components;

import GameObjects.GameObjectComponent;

public class Timer extends GameObjectComponent {

    Runnable timeUpCallback;
    float currentTime;
    float lastTimeToMeassure;
    boolean shouldRun;

    public Timer(Runnable timeUpCallback, float currentTime) {
        this.timeUpCallback = timeUpCallback;
        this.shouldRun = false;
        SetTime(currentTime);
    }

    public Timer() { this(null, 0); }

    public void SetCallback(Runnable newCallback) {this.timeUpCallback = newCallback;}

    public void SetTime(float time) {lastTimeToMeassure = currentTime = time * 1000f;}

    public void Start() {shouldRun = true;}

    public void Reset() {currentTime = lastTimeToMeassure; Start();}

    @Override
    public void Initialize() {

    }

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
    public void Render() {

    }
}
