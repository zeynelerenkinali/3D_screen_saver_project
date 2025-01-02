package Main;

import org.lwjgl.glfw.GLFW;

import engine.graphics.Mesh;
import engine.graphics.Renderer;
import engine.graphics.Shader;
import engine.io.Input;
import engine.io.ModelLoader;
import engine.io.Window;
import engine.maths.Vector3f;
import engine.objects.Camera;
import engine.objects.GameObject;


public class Main implements Runnable
{
    public Thread game;
    public Window window;
    public Renderer[] renderers = new Renderer[4];
    public Shader shader;
    public Vector3f colors[] =  {new Vector3f(0.0f, 1.0f, 0.0f), new Vector3f(1.0f, 0.0f, 0.0f) , new Vector3f(0.0f, 0.0f, 1.0f)};
    public int color_index = 0, object_index = 0,renderer_index = 0;
    public Vector3f camPosition = new Vector3f(0, 0, 1), camRotation = new Vector3f(0, 0, 0);
    public boolean multi_object = false;
    public final int WIDTH = 1920, HEIGHT = 1080; // 1920 1010
    
    public Mesh[] mesh = {
        ModelLoader.loadModel("game_project/src/main/resources/models/DVDVIDEO.obj", "game_project/src/main/resources/textures/green_color.png"),
        ModelLoader.loadModel("game_project/src/main/resources/models/Cube.obj", "game_project/src/main/resources/textures/redstone_lamp.png"),
        ModelLoader.loadModel("game_project/src/main/resources/models/ETU.obj", "game_project/src/main/resources/textures/redstone_lamp.png")};

    public GameObject[] objects = {
        new GameObject(mesh[0], renderers[0], new Vector3f(0.0f, 0.0f, -5.0f), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(1.0f, 1.0f, 1.0f), window),
        new GameObject(mesh[1], renderers[1], new Vector3f(0.0f, 0.0f, -5.0f), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(1.0f, 1.0f, 1.0f), window),
        new GameObject(mesh[2], renderers[2], new Vector3f(0.0f, 0.0f, -5.0f), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(1.0f, 1.0f, 1.0f), window)};

    public Camera camera = new Camera(camPosition, camRotation);
        
        public void start(){
            game = new Thread(this, "game");
            game.start();
        }
    
        public void init(){
            System.out.println("Initializing the Game!");
            window = new Window(WIDTH, HEIGHT, "OpenGL Project");
            shader = new Shader("game_project/src/main/resources/shaders/mainVertex.glsl", "game_project/src/main/resources/shaders/mainFragment.glsl");
            for(int i = 0; i < objects.length; i++){
                renderers[i] = new Renderer(window ,shader, colors[color_index]);
            }
            //window.setBackgroundColor(0.196078f, 0.6f, 0.8f);
            window.setBackgroundColor(0.0f, 0.0f, 0.0f);
            window.create();
            for (Mesh mesh1 : mesh) {
                mesh1.create();
            }
            shader.create();
            for(int i = 0; i < objects.length; i++){
                objects[i] = new GameObject(mesh[i], renderers[i], new Vector3f(0.0f, 0.0f, -5.0f), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(1.0f, 1.0f, 1.0f), window);
            }
            window.setFullScreen(!window.isFullScreen());
        }

    public void run(){
        init();
        while (!window.closed() && !Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) { 
            update();
            render();
            if(Input.isKeyDown(GLFW.GLFW_KEY_F11)){ window.setFullScreen(!window.isFullScreen()); Input.setKey(GLFW.GLFW_KEY_F11, false);}
            if(Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) window.mouseState(true);
            if(Input.isKeyDown(GLFW.GLFW_KEY_C)){camera.switchCamera(); Input.setKey(GLFW.GLFW_KEY_C, false);}
            if(Input.isKeyDown(GLFW.GLFW_KEY_T)){
                object_index += 1;
                object_index %= 3;
                renderer_index += 1;
                renderer_index %= 3;
                Input.setKey(GLFW.GLFW_KEY_T, false);
            }
            if(Input.isKeyDown(GLFW.GLFW_KEY_M)){
                multi_object = !multi_object; 
                Input.setKey(GLFW.GLFW_KEY_M, false);}
        }
        close();
    }
    private void update(){
        window.update();
        if(camera.getCameraMode()) camera.updateStatic(camPosition, camRotation);
        else camera.update();
        if(multi_object)
        {
            for (GameObject object : objects) {
                object.updateMotion();
            }
        }
        else objects[object_index].updateMotion();
    }
    
    private void render(){
        if(multi_object)
        {
            renderers[0].renderMesh(objects[0], camera);
            renderers[1].renderMesh(objects[1], camera);
            renderers[2].renderMesh(objects[2], camera);
        }
        else{
            renderers[renderer_index].renderMesh(objects[object_index], camera);
        } 
        System.out.println(object_index);
        window.swapBuffers();
    }

    private void close(){
        window.destroy();
        mesh[object_index].destroy();
        shader.destroy();
    }
    public static void main(String[] args) {
        new Main().start();
    }
}