// Matan Melamed 205973613
package com.matan.studies.computergraphics.GameObjects.Prefabs;

import com.matan.studies.computergraphics.GameObjects.Components.RuleCheckerComponent;
import com.matan.studies.computergraphics.GameObjects.GameObject;

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
