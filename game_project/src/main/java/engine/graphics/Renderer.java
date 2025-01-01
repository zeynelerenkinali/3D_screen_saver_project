package engine.graphics;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

import engine.io.Window;
import engine.maths.Matrix4f;
import engine.maths.Vector3f;
import engine.objects.Camera;
import engine.objects.GameObject;

public class Renderer {
    private Shader shader;    
    private Window window;
    private Vector3f color;
    
    public Renderer(Window window, Shader shader, Vector3f color){
        this.shader = shader;
        this.window = window;
        this.color = color;
    }

    public void renderMesh(GameObject object, Camera camera){
        GL30.glBindVertexArray(object.getMesh().getVAO());
        GL30.glEnableVertexAttribArray(0);
        GL30.glEnableVertexAttribArray(1);
        GL30.glEnableVertexAttribArray(2);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, object.getMesh().getIBO());
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL13.glBindTexture(GL11.GL_TEXTURE_2D, object.getMesh().getMaterial().getTextureID());
        shader.bind();
        shader.setUniform("model", Matrix4f.transform(object.getPosition(), object.getRotation(), object.getScale()));
        shader.setUniform("projection", window.getProjectionMatrix());
        shader.setUniform("view", Matrix4f.view(camera.getPosition(), camera.getRotation()));
        shader.setUniform("overrideColor", color); 
        shader.setUniform("useTexture", false);  // Disable texture
        GL11.glDrawElements(GL11.GL_TRIANGLES, object.getMesh().getIndices().length, GL11.GL_UNSIGNED_INT, 0);
        shader.unbind();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL30.glDisableVertexAttribArray(0); // Disable the position index
        GL30.glDisableVertexAttribArray(1); // Disable the color index
        GL30.glDisableVertexAttribArray(2); // Disable the textureCoord index
        GL30.glBindVertexArray(0);
    }

    public void changeColor(Vector3f color){
        this.color = color;
    }
}
