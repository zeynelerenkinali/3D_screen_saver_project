package engine.objects;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

import engine.graphics.Mesh;
import engine.maths.Vector3f;
import static engine.maths.Vector3f.add;

public class GameObject {
    private Vector3f position, rotation, scale;
    private Mesh mesh;
    private float rotationSpeed, deltaTime = 0;
    double previousTime = 0, currentTime = 0;
    
        public GameObject(Mesh mesh, Vector3f position, Vector3f rotation, Vector3f scale) {
            this.mesh = mesh;
            this.position = position;
            this.rotation = rotation;
            this.scale = scale;
        }
    
        public void update(){ // At here we are able to update our object's position,rotation, mesh or scale as much as we can.
        currentTime = glfwGetTime();
        deltaTime = (float)(currentTime - previousTime);
        if(!(currentTime >= 5))
            position = add(new Vector3f(position.getX(), position.getY(), position.getZ()), new Vector3f((float) 0.009f,(float) 0.009f,(float) 0.009f));
        //scale = scale.add(new Vector3f(scale.getX(), scale.getY(), scale.getZ()), new Vector3f(0.9f, 0.09f, 0.0f));
        rotation = add(new Vector3f(rotation.getX(), rotation.getY(), rotation.getZ()), new Vector3f(0.9f , 0.09f, 0.0f));
        previousTime = currentTime;
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
