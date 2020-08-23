// Matan Melamed 205973613
package GameObjects.Prefabs;

import GameObjects.Components.RuleCheckerComponent;
import GameObjects.GameObject;

import java.util.function.Supplier;

public class RuleCheckerObject extends GameObject {


    private RuleCheckerComponent checker;

    public RuleCheckerObject(boolean shouldLoop, Supplier<Boolean> rule, Runnable callback) {
        super("rules checker");
        checker = new RuleCheckerComponent(shouldLoop, rule, callback);
        AddComponent(checker);
    }

    public void Reset() {
        checker.Reset();
    }
}
