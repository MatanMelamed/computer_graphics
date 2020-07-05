package GameObjects;


public abstract class GameObjectComponent {

    private GameObject parent;

    public void SetParent(GameObject parent) {this.parent = parent;}

    public GameObject GetParent() { return parent; }

    public abstract void Initialize();

    public abstract void Update(float deltaTime);

    public abstract void Render();

}
