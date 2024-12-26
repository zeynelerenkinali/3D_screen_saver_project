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
import engine.objects.Camera;
import engine.objects.GameObject;


public class Main implements Runnable
{
    public Thread game;
    public Window window;
    public Renderer renderer;
    public Shader shader;
    public final int WIDTH = 1280, HEIGHT = 720; // 1920 1010
    
    public Mesh mesh = new Mesh(new Vertex[] {
        //Back face
        new Vertex(new Vector3f(-0.5f,  0.5f, -0.5f),new Vector3f(1.0f,  0.5f, 0.1f), new Vector2f(0.0f, 0.0f)),
        new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f),new Vector3f(1.0f,  0.5f, 0.1f), new Vector2f(0.0f, 1.0f)),
        new Vertex(new Vector3f( 0.5f, -0.5f, -0.5f),new Vector3f(1.0f,  0.5f, 0.1f), new Vector2f(1.0f, 1.0f)),
        new Vertex(new Vector3f( 0.5f,  0.5f, -0.5f),new Vector3f(1.0f,  0.5f, 0.1f), new Vector2f(1.0f, 0.0f)),
        
        //Front face
        new Vertex(new Vector3f(-0.5f,  0.5f,  0.5f),new Vector3f(1.0f,  0.5f, 0.1f), new Vector2f(0.0f, 0.0f)),
        new Vertex(new Vector3f(-0.5f, -0.5f,  0.5f),new Vector3f(1.0f,  0.5f, 0.1f), new Vector2f(0.0f, 1.0f)),
        new Vertex(new Vector3f( 0.5f, -0.5f,  0.5f),new Vector3f(1.0f,  0.5f, 0.1f), new Vector2f(1.0f, 1.0f)),
        new Vertex(new Vector3f( 0.5f,  0.5f,  0.5f),new Vector3f(1.0f,  0.5f, 0.1f), new Vector2f(1.0f, 0.0f)),
        
        //Right face
        new Vertex(new Vector3f( 0.5f,  0.5f, -0.5f),new Vector3f(1.0f,  0.5f, 0.1f), new Vector2f(0.0f, 0.0f)),
        new Vertex(new Vector3f( 0.5f, -0.5f, -0.5f),new Vector3f(1.0f,  0.5f, 0.1f), new Vector2f(0.0f, 1.0f)),
        new Vertex(new Vector3f( 0.5f, -0.5f,  0.5f),new Vector3f(1.0f,  0.5f, 0.1f), new Vector2f(1.0f, 1.0f)),
        new Vertex(new Vector3f( 0.5f,  0.5f,  0.5f),new Vector3f(1.0f,  0.5f, 0.1f), new Vector2f(1.0f, 0.0f)),
        
        //Left face
        new Vertex(new Vector3f(-0.5f,  0.5f, -0.5f),new Vector3f(1.0f,  0.5f, 0.1f), new Vector2f(0.0f, 0.0f)),
        new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f),new Vector3f(1.0f,  0.5f, 0.1f), new Vector2f(0.0f, 1.0f)),
        new Vertex(new Vector3f(-0.5f, -0.5f,  0.5f),new Vector3f(1.0f,  0.5f, 0.1f), new Vector2f(1.0f, 1.0f)),
        new Vertex(new Vector3f(-0.5f,  0.5f,  0.5f),new Vector3f(1.0f,  0.5f, 0.1f), new Vector2f(1.0f, 0.0f)),
        
        //Top face
        new Vertex(new Vector3f(-0.5f,  0.5f,  0.5f),new Vector3f(1.0f,  0.5f, 0.1f), new Vector2f(0.0f, 0.0f)),
        new Vertex(new Vector3f(-0.5f,  0.5f, -0.5f),new Vector3f(1.0f,  0.5f, 0.1f), new Vector2f(0.0f, 1.0f)),
        new Vertex(new Vector3f( 0.5f,  0.5f, -0.5f),new Vector3f(1.0f,  0.5f, 0.1f), new Vector2f(1.0f, 1.0f)),
        new Vertex(new Vector3f( 0.5f,  0.5f,  0.5f),new Vector3f(1.0f,  0.5f, 0.1f), new Vector2f(1.0f, 0.0f)),
        
        //Bottom face
        new Vertex(new Vector3f(-0.5f, -0.5f,  0.5f),new Vector3f(1.0f,  0.5f, 0.1f), new Vector2f(0.0f, 0.0f)),
        new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f),new Vector3f(1.0f,  0.5f, 0.1f), new Vector2f(0.0f, 1.0f)),
        new Vertex(new Vector3f( 0.5f, -0.5f, -0.5f),new Vector3f(1.0f,  0.5f, 0.1f), new Vector2f(1.0f, 1.0f)),
        new Vertex(new Vector3f( 0.5f, -0.5f,  0.5f),new Vector3f(1.0f,  0.5f, 0.1f), new Vector2f(1.0f, 0.0f)),
    }, new int[] {
            //Back face
            0, 1, 3,	
            3, 1, 2,	
            
            //Front face
            4, 5, 7,
            7, 5, 6,
            
            //Right face
            8, 9, 11,
            11, 9, 10,
            
            //Left face
            12, 13, 15,
            15, 13, 14,
            
            //Top face
            16, 17, 19,
            19, 17, 18,
            
            //Bottom face
            20, 21, 23,
            23, 21, 22
    }, new Material("game_project/src/main/resources/textures/dvd_video.png"));

        public GameObject object = new GameObject(mesh, new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), new Vector3f(1.0f, 1.0f, 1.0f));

        public Camera camera = new Camera(new Vector3f(0, 0, 1), new Vector3f(0, 0, 0));
        
        public void start(){
            game = new Thread(this, "game");
            game.start();
        }
    
        public void init(){
            System.out.println("Initializing the Game!");
            window = new Window(WIDTH, HEIGHT, "Game");
            shader = new Shader("game_project/src/main/resources/shaders/mainVertex.glsl", "game_project/src/main/resources/shaders/mainFragment.glsl");
            renderer = new Renderer(window ,shader);
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
            if(Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) window.mouseState(true);
        }
        close();
    }
    private void update(){
        window.update();
        camera.update();
        //object.update();
    }
    
    private void render(){
        renderer.renderMesh(object, camera);
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
}
