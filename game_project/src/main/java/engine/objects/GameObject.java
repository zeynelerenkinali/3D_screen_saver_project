package engine.objects;

import engine.graphics.Mesh;
import engine.maths.Vector3f;

public class GameObject {
    private Vector3f position, rotation, scale;
    private Mesh mesh;

    public GameObject(Mesh mesh, Vector3f position, Vector3f rotation, Vector3f scale) {
        this.mesh = mesh;
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    public void update(){ // At here we are able to update our object's position,rotation, mesh or scale as much as we can.
        
        position.setZ(position.getZ() -0.05f);
        // scale.add(0.00f, 0.00f, 0.0f);
        //rotation.add(0.9f, 0.09f, 0.0f);
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public Vector3f getScale() {
        return scale;
    }

    public Mesh getMesh() {
        return mesh;
    }
}
