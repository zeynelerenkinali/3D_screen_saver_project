package Main;

import org.lwjgl.glfw.GLFW;

import engine.Input;
import engine.Window;

public class Main implements Runnable
{
    public Thread game;
    public Window window;
    public final int WIDTH = 1280, HEIGHT = 720; // 1920 1010
    
        public void start(){
            game = new Thread(this, "game");
            game.start();
        }
    
        public void init(){
            System.out.println("Initializing the Game!");
            window = new Window(WIDTH, HEIGHT, "Game");
            window.create();
    }

    public void run(){
        init();
        while (!window.closed()) { 
            update();
            render();
            if(Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) return;
        }
        window.destroy();
    }
    private void update(){
        //System.out.println("Updating the Game!");
        window.update();
        if(Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) System.out.println("X: " + Input.getMouseX() + " | Y: " + Input.getMouseY());
    }
    
    private void render(){
        //System.out.println("Rendering the Game!");
        window.swapBuffers();
    }
    public static void main(String[] args) {
        new Main().start();
    }
}
