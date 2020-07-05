package GameObjects.Components;

import Core.Collision.Collider;
import Core.Collision.IntersectData;
import GameObjects.GameObjectComponent;

public class ColliderComponent extends GameObjectComponent {

    private Collider collider;
//    int list;

    public ColliderComponent(Collider collider) {
        this.collider = collider;
    }

    public Collider GetCollider() {
        return collider;
    }

    @Override
    public void Initialize() {
//        list = Graphics.make_ball();
    }

    @Override
    public void Update(float deltaTime) {
        //GetParent().GetPosition()
        collider.SetPosition(GetParent().GetPosition());
    }

    @Override
    public void Render() {
//        if (collider.GetType()== ColliderType.BS){
////            Graphics.CallList(list);
//        }
    }

    public void CollisionHandle(IntersectData data, ColliderComponent other) {
        System.out.println("collision in " + GetParent().GetPosition());
    }
}
