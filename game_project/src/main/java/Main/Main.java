package Main;

import org.lwjgl.glfw.GLFW;

import engine.graphics.Material;
import engine.graphics.Mesh;
import engine.graphics.Renderer;
import engine.graphics.Shader;
import engine.graphics.Vertex;
import engine.io.Input;
import engine.io.Window;
import engine.maths.Vector2f;
import engine.maths.Vector3f;
import engine.objects.GameObject;


public class Main implements Runnable
{
    public Thread game;
    public Window window;
    public Renderer renderer;
    public Shader shader;
    public final int WIDTH = 1280, HEIGHT = 720; // 1920 1010
    
    public Mesh mesh = new Mesh(new Vertex[] {
		    new Vertex(new Vector3f( -0.5f,0.5f, 0.0f), new Vector3f(1.0f, 0.5f, 0.1f), new Vector2f(0.0f, 0.0f)),
			new Vertex(new Vector3f( -0.5f, -0.5f, 0.0f), new Vector3f(0.5f, 0.3f, 1.0f), new Vector2f(0.0f, 1.0f)),
			new Vertex(new Vector3f(0.5f, -0.5f, 0.0f), new Vector3f(1.0f, 0.1f, 0.0f), new Vector2f(1.0f, 1.0f)),
			new Vertex(new Vector3f(0.5f,0.5f, 0.0f), new Vector3f(0.0f, 0.5f, 1.0f), new Vector2f(1.0f, 0.0f))
        }, new int[] {
            0, 1, 2,
            0, 3, 2, 
        }, new Material("game_project/src/main/resources/textures/dvd_video.png"));

        public GameObject object = new GameObject(mesh, new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), new Vector3f(0.25f, 0.25f, 1.0f));

        public void start(){
            game = new Thread(this, "game");
            game.start();
        }
    
        public void init(){
            System.out.println("Initializing the Game!");
            window = new Window(WIDTH, HEIGHT, "Game");
            shader = new Shader("game_project/src/main/resources/shaders/mainVertex.glsl", "game_project/src/main/resources/shaders/mainFragment.glsl");
            renderer = new Renderer(shader);
            window.setBackgroundColor(0.10f, 0.25f, 0.25f);
            window.create();
            mesh.create();
            shader.create();
        }

    public void run(){
        init();
        while (!window.closed() && !Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) { 
            update();
            render();
            if(Input.isKeyDown(GLFW.GLFW_KEY_F11)){ window.setFullScreen(!window.isFullScreen()); Input.setKey(GLFW.GLFW_KEY_F11, false);}
        }
        close();
    }
    private void update(){
        window.update();
        object.update();
        if(Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) System.out.println("X: " + Input.getScrollX() + " | Y: " + Input.getScrollY());
    }
    
    private void render(){
        renderer.renderMesh(object);
        window.swapBuffers();
    }

    private void close(){
        window.destroy();
        mesh.destroy();
        shader.destroy();
    }
    public static void main(String[] args) {
        new Main().start();
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }
}
