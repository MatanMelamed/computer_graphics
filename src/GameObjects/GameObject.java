package GameObjects;

import Core.Graphics.Graphics;
import Models.Vector3D;

import java.util.ArrayList;

public class GameObject extends TransformObject {

    private ArrayList<GameObject> children = new ArrayList<>();
    private ArrayList<GameObjectComponent> components = new ArrayList<>();

    public void AddChild(GameObject child) {children.add(child);}

    public void AddComponent(GameObjectComponent component) {
        components.add(component);
        component.SetParent(this);
    }


    public ArrayList<GameObject> GetChildren() {
        return children;
    }

    public ArrayList<GameObjectComponent> GetComponents() {
        return components;
    }

    public void InitializeAll() {
        for (GameObject child : children) {
            child.InitializeAll();
        }
        Initialize();
    }

    public void UpdateAll(float deltaTime) {
        for (GameObject child : children) {
            child.UpdateAll(deltaTime);
        }
        Update(deltaTime);
    }

    public void RenderAll() {
        for (GameObject child : children) {
            child.RenderAll();
        }
        Render();
    }


    private void Initialize() {
        for (GameObjectComponent component : components) {
            component.Initialize();
        }
    }

    private void Update(float deltaTime) {
        for (GameObjectComponent component : components) {
            component.Update(deltaTime);
        }
    }

    private void Render() {
        Vector3D position = GetPosition();

        Graphics.PushMatrix();
        Graphics.Translate((float) position.x, (float) position.y, (float) position.z);
        Graphics.Rotate((float) GetAxisAngleX(), 1, 0, 0);
        Graphics.Rotate((float) GetAxisAngleY(), 0, 1, 0);
        Graphics.Rotate((float) GetAxisAngleZ(), 0, 0, 1);

        for (GameObjectComponent component : components) {
            component.Render();
        }

        Graphics.PopMatrix();
    }


}
