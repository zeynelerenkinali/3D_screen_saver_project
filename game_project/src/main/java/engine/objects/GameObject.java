package engine.objects;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

import engine.graphics.Mesh;
import engine.graphics.Renderer;
import engine.io.Window;
import engine.maths.Vector3f;
import static engine.maths.Vector3f.add;

public class GameObject {
    private Vector3f position, rotation, scale, color;
    private float x = 0.0f, y = 0.0f;  // Initial position of the logo
    private float dx = 0.023f, dy = 0.023f;  // Velocity of the logo in X and Y direction
    private float currentAngle = 0f;
    public Vector3f colors[] =  {new Vector3f(0.0f, 1.0f, 0.0f), new Vector3f(1.0f, 0.0f, 0.0f) , new Vector3f(0.0f, 0.0f, 1.0f)};
    public int color_index = 0;
    private Mesh mesh;
    private Window window;
    private Renderer renderer;
    private float rotationSpeed, deltaTime = 0;
    double previousTime = 0, currentTime = 0;
    
    public GameObject(Mesh mesh, Renderer renderer, Vector3f position, Vector3f rotation, Vector3f scale, Window window) {
        this.mesh = mesh;
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.window = window;
        this.renderer = renderer;
    }

    public void update(){ // At here we are able to update our object's position,rotation, mesh or scale as much as we can.
        currentTime = glfwGetTime();
        deltaTime = (float)(currentTime - previousTime);
        if(!(currentTime >= 3))
        {
            position = add(new Vector3f(position.getX(), position.getY(), position.getZ()), new Vector3f((float) 0.005f,(float) 0.0f,(float) 0.005f));
        }
        //scale = scale.add(new Vector3f(scale.getX(), scale.getY(), scale.getZ()), new Vector3f(0.9f, 0.09f, 0.0f));
        rotation = add(new Vector3f(rotation.getX(), rotation.getY(), rotation.getZ()), new Vector3f(0.0f , 0.0f, -1.0f));
        previousTime = currentTime;
    }


    public void updateMotion(){ // At here we are able to update our object's position,rotation, mesh or scale as much as we can.
        currentTime = glfwGetTime();
        deltaTime = (float)(currentTime - previousTime);
        
        // Basic Rotation
        //rotation = add(new Vector3f(rotation.getX(), rotation.getY(), rotation.getZ()), new Vector3f(0.0f , 1.0f, 0.0f));
        // 2nd Rotation
        x += dx;
        y += dy;
        
        // X and Y limit
        float xLimit = window.getAspect() + 5.99f;
        float yLimit = 4.7650f;
        
        // Check for wall collisions and set random angle accordingly
        boolean collided = false;
        String collisionType = "";

        // Check top boundary collision (y + logoSize > 1.0f)
        if (y + scale.getY() > yLimit) 
        {   
            if (x + scale.getX() > xLimit) 
            { // Top right corner
                collisionType = "corner";
                collided = true;
            } 
            else if (x - scale.getX() < -xLimit) 
            { // Top left corner
                collisionType = "corner";
                collided = true;
            } 
            else 
            { // Top wall
                collisionType = "horizontal";
                collided = true;
            }
        }
        // Check bottom boundary collision (y - logoSize < -1.0f)
        else if (y - scale.getY() < -yLimit) 
        {   
            if (x + scale.getX() > xLimit) 
            { // Bottom right corner
                collided = true;
                collisionType = "corner";
            } 
            else if (x - scale.getX() < -xLimit) 
            { // Bottom left corner
                collided = true;
                collisionType = "corner";
            } 
            else 
            { // Bottom wall
                collided = true;
                collisionType = "horizontal";
            }
        }
        // Check right boundary collision (x + logoSize > 1.0f)
        else if (x + scale.getX() > xLimit) 
        {
            collisionType = "vertical";
            collided = true;
        }
        // Check left boundary collision (x - logoSize < -1.0f)
        else if (x - scale.getX() < -xLimit) 
        {
            collisionType = "vertical";
            collided = true;
        }
        
        if (collided) 
        {
            System.out.println("Collision detected!");
            currentAngle = reflectAngle(currentAngle, collisionType);
            setAngleDirection(currentAngle);
            x = Math.max(Math.min(x, xLimit - scale.getX()), -xLimit + scale.getX());
            y = Math.max(Math.min(y, yLimit - scale.getY()), -yLimit + scale.getY());
            changeColor(new Vector3f((float) Math.random(), (float) Math.random(), (float) Math.random()));
        }   
        
        //System.out.println("Position: (" + x + ", " + y + "), Direction: (" + dx + ", " + dy + "), Collided: " + collided);
        currentAngle = getCurrentAngleFromDirection();
        position.set(position.getX() + dx, position.getY() + dy, position.getZ());
        //rotation.set(rotation.getX() + 0.0f, rotation.getY() + 0.00f, rotation.getZ() + 0.03f);
        previousTime = currentTime;
    }

    private void changeColor(Vector3f newColor) {
        System.out.println("Changing color to: " + newColor);
        renderer.changeColor(newColor);
    }
    

    private float reflectAngle(float currentAngle, String wallType) 
    {
        // Adjust the angle based on which wall is hit
        switch (wallType) {
            case "horizontal":
                // Bounce off top or bottom wall
                return 360f - currentAngle;
            case "vertical":
                // Bounce off left or right wall
                return 180f - currentAngle;
            case "corner":
                // Bounce both walls
                return (currentAngle + 180f) % 360;  // Flip the angle by 180 degrees for corner
            default:
                break;
        }
        
        // If no wall collision, return the current angle
        return currentAngle;
    }

    private void setAngleDirection(float angleDegrees) 
    {
        // Convert angle to radians
        float angle = (float) Math.toRadians(angleDegrees);
        // Speed
        float speed = 0.034f;
        // Update the direction based on the new angle
        dx = (float) Math.cos(angle) * speed;  
        dy = (float) Math.sin(angle) * speed;
    }

    private float getCurrentAngleFromDirection() 
    {
        // Calculate the angle based on dx and dy
        float angleInRadians = (float) Math.atan2(dy, dx);
        // Convert the angle to degrees (atan2 gives radians, convert to degrees)
        float angleInDegrees = (float) Math.toDegrees(angleInRadians);
        
        // Ensure the angle is in the range [0, 360]
        if (angleInDegrees < 0) {
            angleInDegrees += 360;
        }
        
        return angleInDegrees;
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
