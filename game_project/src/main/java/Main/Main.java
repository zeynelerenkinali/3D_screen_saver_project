package Main;
import engine.Window;

public class Main 
{
    public static void main(String[] args) {
        Window window = new Window(800, 600, "Hello");
        window.create();

        while(!window.closed()){
            window.update();
            System.out.println("hey");
            window.swapBuffers();
        }
    }
}
