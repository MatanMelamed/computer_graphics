package GameObjects.Components;

import GameObjects.GameObjectComponent;

import java.util.function.Supplier;

public class RuleChecker extends GameObjectComponent {

    private boolean isOnce;
    private boolean hasTriggered;
    private Supplier<Boolean> checker;
    private Runnable callback;

    public RuleChecker(boolean isOnce, Supplier<Boolean> checker, Runnable callback) {
        this.isOnce = isOnce;
        this.checker = checker;
        this.callback = callback;
        this.hasTriggered = false;
    }

    public RuleChecker(Supplier<Boolean> checker, Runnable callback) {
        this(true, checker, callback);
    }

    public void Reset() {
        hasTriggered = false;
    }

    @Override
    public void Initialize() {

    }

    @Override
    public void Update(float deltaTime) {
        if (!(isOnce && hasTriggered)) {
            if (checker.get()) {
                callback.run();
                hasTriggered = true;
            }
        }
    }

    @Override
    public void Render() {

    }
}
