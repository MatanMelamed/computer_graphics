// Matan Melamed 205973613
package GameObjects.Components;

import GameObjects.GameObjectComponent;

public class TimeLooperComponent extends GameObjectComponent {

    private float turningOffTime;
    private float turningOnTime;
    private float peakOnTime;
    private float peakOffTime;

    private float currentTime;
    private boolean currentlyOn;
    private boolean currentlyPeak;

    public float relativeValue = 1;

    public TimeLooperComponent(float turningOffTime, float turningOnTime, float peakOnTime, float peakOffTime) {
        this.turningOffTime = turningOffTime * 1000f;
        this.turningOnTime = turningOnTime * 1000f;
        this.peakOnTime = peakOnTime * 1000f;
        this.peakOffTime = peakOffTime * 1000f;
        Reset();

    }

    public void Reset() {
        this.currentTime = 0;
        this.currentlyPeak = true;
        this.currentlyOn = false;
    }

    @Override
    public void Initialize() {}

    @Override
    public void Update(float deltaTime) {
        currentTime -= deltaTime;

        boolean timeUp = currentTime <= 0;
        if (!currentlyPeak && currentlyOn && timeUp) {
            currentTime = peakOnTime;
            currentlyPeak = true;
        } else if (currentlyPeak && currentlyOn && timeUp) {
            currentTime = turningOffTime;
            currentlyOn = false;
            currentlyPeak = false;
        } else if (!currentlyPeak && !currentlyOn & timeUp) {
            currentTime = peakOffTime;
            currentlyPeak = true;
        } else if (currentlyPeak && !currentlyOn && timeUp) {
            currentTime = turningOnTime;
            currentlyOn = true;
            currentlyPeak = false;
        }

        relativeValue = currentlyPeak
                ? (currentlyOn ? 1f : 0f)
                : (currentlyOn ? 1f - (currentTime / turningOffTime) : currentTime / turningOffTime);
    }

    @Override
    public void Render() {}
}
