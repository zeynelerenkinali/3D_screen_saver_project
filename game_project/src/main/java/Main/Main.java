package Main;

import engine.Window;

public class Main implements Runnable
{
    public Thread game;
    public static Window window;
    public static final int WIDTH = 1280, HEIGHT = 720; // 1920 1010
    
        public void start(){
            game = new Thread(this, "game");
            game.start();
        }
    
        public static void init(){
            System.out.println("Initializing the Game!");
            window = new Window(WIDTH, HEIGHT, "Game");
            window.create();
    }

    public void run(){
        init();
        while (!window.closed()) { 
            update();
            render();   
        }
    }
    private void update(){
        //System.out.println("Updating the Game!");
        window.update();
    }
    
    private void render(){
        //System.out.println("Rendering the Game!");
        window.swapBuffers();
    }
    public static void main(String[] args) {
        new Main().start();
    }
}
