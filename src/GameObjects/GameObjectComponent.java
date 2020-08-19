package GameObjects;

import Models.Vector3D;

public abstract class GameObjectComponent implements Comparable<GameObjectComponent> {

    public GameObject parent;
    public boolean isEnabled = true;

    protected int priority = 0;

    public void SetParent(GameObject parent) {
        this.parent = parent;
    }

    public GameObject GetParent() { return parent; }

    public void ParentMoved(float x, float y, float z) {}

    public void Enable() {isEnabled = true;}

    public void Disable() {isEnabled = false;}

    public abstract void Initialize();

    public abstract void Update(float deltaTime);

    public abstract void Render();

    @Override
    public int compareTo(GameObjectComponent other){
        return  other.priority - this.priority;
    }

}