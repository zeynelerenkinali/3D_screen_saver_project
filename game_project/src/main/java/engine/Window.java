package engine;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;

public class Window 
{
    private int width = 1280, height = 720;
    private String title;
    private long window;
    public int frames;
    public static long time;

    public Window(int width, int height, String title)
    {
        this.width = width;
        this.height = height;
        this.title = title;
    }

    public void create(){
        if(!GLFW.glfwInit()){
            System.err.println("ERROR: Couldn't initialize GLFW");
            return;
        }
        // Settings of screen
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);
        window = GLFW.glfwCreateWindow(width, height, title, 0, 0);
        
        if (window == 0){
            System.err.println("ERROR: Window Couldn't be created.");
            return;
        }
        
        GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        GLFW.glfwSetWindowPos(window, (videoMode.width() - width) / 2, (videoMode.height() - height) / 2);
        GLFW.glfwMakeContextCurrent(window);

        GLFW.glfwShowWindow(window);

        GLFW.glfwSwapInterval(1); // its giving gap between two interwals of swap which is frames
        
        time = System.currentTimeMillis();
    }

    public boolean closed(){
        return GLFW.glfwWindowShouldClose(window);
    }
    
    public void update(){// Gets rid of everything from previous frame
        GLFW.glfwPollEvents();
        frames++;
        if(System.currentTimeMillis() > time + 1000){
            GLFW.glfwSetWindowTitle(window, title + " | FPS: " + frames );
            time = System.currentTimeMillis();
            frames = 0;
        }
    }

    public void swapBuffers(){// Renders necessary things for screen
        GLFW.glfwSwapBuffers(window);
    }
}

