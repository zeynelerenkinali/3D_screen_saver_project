package engine;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;

public class Window 
{
    private int width = 1280, height = 720;
    private String title;
    private long window;

    public Window(int width, int height, String title)
    {
        this.width = width;
        this.height = height;
        this.title = title;
    }

    public void create(){
        if(!GLFW.glfwInit()){
            System.err.println("Error: Couldn't initialize glfw");
            System.exit(-1);
        }
        // Settings of screen
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);
        window = GLFW.glfwCreateWindow(width, height, title, 0, 0);
        
        if (window == 0){
            System.err.println("Error: Window Couldn't be created.");
            System.exit(-1);
        }
        GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        GLFW.glfwSetWindowPos(window, (videoMode.width() - width) / 2, (videoMode.height() - height) / 2);
    
        GLFW.glfwShowWindow(window);
    }

    public boolean closed(){
        return GLFW.glfwWindowShouldClose(window);
    }
    
    public void update(){// Gets rid of everything from previous frame
        GLFW.glfwPollEvents();
    }

    public void swapBuffers(){// Renders necessary things for screen
        GLFW.glfwSwapBuffers(window);
    }
}

