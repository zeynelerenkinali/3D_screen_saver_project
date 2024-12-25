package engine.io;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import engine.maths.Matrix4f;
import engine.maths.Vector3f;

public class Window 
{
    private int width = 1280, height = 720;
    private final String title;
    private long window;
    public int frames;
    public static long time;
    public Input input;
    private Vector3f background = new Vector3f(0, 0, 0);
    private GLFWWindowSizeCallback sizeCallback;
    private boolean isResized;
    private boolean isFullScreen;
    private int[] windowPosX = new int[1], windowPosY = new int[1];
    private Matrix4f projection;
    private float fov = 70.0f;
    private float aspect = (float) width / (float) height;

    public Window(int width, int height, String title){
        this.width = width;
        this.height = height;
        this.title = title;
        projection = Matrix4f.projection(fov, aspect, 0.1f, 1000.0f);
    }

    public void create(){
        if(!GLFW.glfwInit()){
            System.err.println("ERROR: Couldn't initialize GLFW");
            return;
        }

        input = new Input();
        // Settings of screen
        window = GLFW.glfwCreateWindow(width, height, title, isFullScreen ? GLFW.glfwGetPrimaryMonitor() : 0, 0);
        
        if (window == 0){
            System.err.println("ERROR: Window Couldn't be created.");
            return; 
        }
        
        GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        windowPosX[0] = (videoMode.width() - width) / 2;
        windowPosY[0] = (videoMode.height() - height) / 2;
        GLFW.glfwSetWindowPos(window, windowPosX[0], windowPosY[0]);
        GLFW.glfwMakeContextCurrent(window);
        GL.createCapabilities();
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        createCallbacks();

        GLFW.glfwShowWindow(window);

        GLFW.glfwSwapInterval(2); // its giving gap between two interwals of swap which is frames
        
        time = System.currentTimeMillis();
    }

    private void createCallbacks(){

        sizeCallback = new GLFWWindowSizeCallback(){
            public void invoke(long window, int w, int h) {
                width = w;
                height = h;
                isResized = true;
            }  
        };
        GLFW.glfwSetKeyCallback(window, input.getKeyboardCallback());
        GLFW.glfwSetCursorPosCallback(window, input.getMouseMoveCallback());
        GLFW.glfwSetMouseButtonCallback(window, input.getMouseButtonsCallback());
        GLFW.glfwSetScrollCallback(window, input.getMouseScrollCallback());
        GLFW.glfwSetWindowSizeCallback(window, sizeCallback);
    }
    
    public void update(){// Gets rid of everything from previous frame
        // basically sets new sizes of screen
        if(isResized){
            GL11.glViewport(0, 0, width, height);
            isResized = false;
        }
        //basically clears the screen
        GL11.glClearColor(background.getX(), background.getY(), background.getY(), 1.0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GLFW.glfwPollEvents();
        frames++;
        if(System.currentTimeMillis() > time + 1000){
            GLFW.glfwSetWindowTitle(window, title + " | FPS: " + frames );
            time = System.currentTimeMillis();
            frames = 0;
        }
    }

    public boolean closed(){
        return GLFW.glfwWindowShouldClose(window);
    }

    public void swapBuffers(){// Renders necessary things for screen
        GLFW.glfwSwapBuffers(window);
    }

    public void destroy(){
        input.destroy();
        sizeCallback.free();
        GLFW.glfwWindowShouldClose(window);
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
    }

    public void setBackgroundColor(float r, float g, float b){
        background.set(r, g, b);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getTitle() {
        return title;
    }

    public long getWindow() {
        return window;
    }

    public boolean isResized() {
        return isResized;
    }

    public boolean isFullScreen() {
        return isFullScreen;
    }

    public void setFullScreen(boolean isFullScreen) {
        this.isFullScreen = isFullScreen;
        isResized = true;
        if (isFullScreen){
            GLFW.glfwGetWindowPos(window, windowPosX, windowPosY);
            GLFW.glfwSetWindowMonitor(window, GLFW.glfwGetPrimaryMonitor(), 0, 0, width, height, 0);
        } else{
            GLFW.glfwSetWindowMonitor(window, 0, windowPosX[0], windowPosY[0], width, height, 0); // the logic on these one's "windowPosX[0], windowPosY[0]" instead of setting zero the posiitons of screen zero no matter what we set previous posiiotn.
        }
    }

    public Matrix4f getProjectionMatrix() {
        return projection;
    }
    
}

