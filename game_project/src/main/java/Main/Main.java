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
            window.setBackgroundColor(0.10f, 0.25f, 0.25f);
            window.create();
        }

    public void run(){
        init();
        while (!window.closed() && !Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) { 
            update();
            render();
            if(Input.isKeyDown(GLFW.GLFW_KEY_F11)){ window.setFullScreen(!window.isFullScreen()); Input.setKey(GLFW.GLFW_KEY_F11, false);
            }
        }
        window.destroy();
    }
    private void update(){
        window.update();
        if(Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) System.out.println("X: " + Input.getScrollX() + " | Y: " + Input.getScrollY());
    }
    
    private void render(){
        window.swapBuffers();
    }
    public static void main(String[] args) {
        new Main().start();
    }
}
