// Matan Melamed 205973613
package GameObjects.Components;

import GameObjects.GameObjectComponent;

import java.util.function.Supplier;

public class RuleCheckerComponent extends GameObjectComponent {

    private boolean isOnce;
    private boolean hasTriggered;
    private Supplier<Boolean> checker;
    private Runnable callback;

    public RuleCheckerComponent(boolean isOnce, Supplier<Boolean> rules, Runnable callback) {
        this.isOnce = isOnce;
        this.checker = rules;
        this.callback = callback;
        this.hasTriggered = false;
    }

    public void Reset() {
        hasTriggered = false;
    }

    @Override
    public void Initialize() {

    }

    @Override
    public void Update(float deltaTime) {
        if (hasTriggered && isOnce) return;

        if (checker.get()) {
            callback.run();
            hasTriggered = true;
        }
    }

    @Override
    public void Render() {

    }
}
