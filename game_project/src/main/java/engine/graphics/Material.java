package engine.graphics;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

public class Material {
    private String path;
    private int textureID;
    private float width, height;
    
    public Material(String path) {
        this.path = path;
    }
    
    public void create() {
        // Load the texture using stb_image
        try (MemoryStack stack = MemoryStack.stackPush()) {
            // Allocate memory for the image width, height, and number of channels (RGBA)
            IntBuffer widthBuffer = stack.mallocInt(1);
            IntBuffer heightBuffer = stack.mallocInt(1);
            IntBuffer compBuffer = stack.mallocInt(1);

            // Flip vertically to match OpenGL's coordinates
            STBImage.stbi_set_flip_vertically_on_load(true);

            // Load image
            ByteBuffer image = STBImage.stbi_load(path, widthBuffer, heightBuffer, compBuffer, 4);
            if (image == null) {
                System.err.println("Can't find texture at " + path + ": " + STBImage.stbi_failure_reason());
                return;
            }

            // Retrieve width and height
            width = widthBuffer.get();
            height = heightBuffer.get();

            // Enable blending for transparency
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

            // Generate a new OpenGL texture
            textureID = GL11.glGenTextures();
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);

            // Set texture parameters
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

            // Upload texture data to the GPU
            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, (int) width, (int) height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, image);

            // Free the image memory once uploaded to the GPU
            STBImage.stbi_image_free(image);
        }
    }
    
    public void destroy() {
        GL13.glDeleteTextures(textureID);
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public int getTextureID() {
        return textureID;
    }
}
