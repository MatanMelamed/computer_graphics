// Matan Melamed 205973613
package GameObjects;

import Core.Graphics.Graphics;
import Models.Axis;
import Models.Vector3D;

import java.util.ArrayList;
import java.util.Collections;


public class GameObject extends TransformObject {

    private ArrayList<GameObject> children = new ArrayList<>();
    private ArrayList<GameObjectComponent> components = new ArrayList<>();
    private boolean isEnabled = true;

    protected boolean isDebug = false;

    public String name;


    public GameObject() {
        this("object");
    }

    public GameObject(String name) {
        this.name = name;
    }

    public boolean IsEnabled() {return isEnabled;}

    public void Enable() {
        isEnabled = true;
        for (GameObjectComponent component : components) {
            component.Enable();
        }
        for (GameObject child : children) {
            child.Enable();
        }
    }

    public void Disable() {
        isEnabled = false;
        for (GameObjectComponent component : components) {
            component.Disable();
        }
        for (GameObject child : children) {
            child.Disable();
        }
    }

    public void MoveToZero() {
        Vector3D current = GetPosition();
        Move(-current.x, -current.y, -current.z);
    }

    public void ResetRotation() {
        float x = (float) this.GetAxisAngleX();
        float y = (float) this.GetAxisAngleY();
//        float z = (float) this.GetAxisAngleZ();
        Rotate(Axis.Y, -y);
        Rotate(Axis.X, -x);
    }

    @Override
    public void Move(float x, float y, float z) {
        for (int i = 0; i < children.size(); i++) {
            children.get(i).Move(x, y, z);
        }
        super.Move(x, y, z);

        for (int i = 0; i < components.size(); i++) {
            components.get(i).ParentMoved(x, y, z);
        }
    }

    public void Move(Vector3D delta) {
        Move(delta.x, delta.y, delta.z);
    }

    @Override
    public void Rotate(Axis axis, double angle) {
        super.Rotate(axis, angle);
    }

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
        Initialize();
        for (GameObject child : children) {
            child.InitializeAll();
        }
    }

    public void UpdateAll(float deltaTime) {
        if (!isEnabled) return;
        Update(deltaTime);
        for (GameObject child : children) {
            child.UpdateAll(deltaTime);
        }
    }

    public void RenderAll() {
        if (!isEnabled) return;
        Render();
        for (GameObject child : children) {
            child.RenderAll();
        }
    }


    protected void Initialize() {
        Collections.sort(components);
        for (GameObjectComponent component : components) {
            component.Initialize();
        }
    }

    protected void Update(float deltaTime) {
        for (GameObjectComponent component : components) {
            component.Update(deltaTime);
        }
    }

    protected void Render() {
        Vector3D position = GetPosition();

        Graphics.PushMatrix();
        Graphics.SetMaterial(Graphics.DEF_MATERIAL);
        Graphics.Translate(position.x, position.y, position.z);
        Graphics.Rotate((float) GetAxisAngleY(), 0, 1, 0);
        Graphics.Rotate((float) GetAxisAngleX(), 1, 0, 0);
        Graphics.Rotate((float) GetAxisAngleZ(), 0, 0, 1);

        for (GameObjectComponent component : components) {
            if (component.isEnabled) {
                component.Render();
            }
        }

        Graphics.PopMatrix();
    }

    public void PrintAll(int level) {
        System.out.println("\t".repeat(level) + name);
        for (GameObject child : children) {
            child.PrintAll(level + 1);
        }
    }

}
