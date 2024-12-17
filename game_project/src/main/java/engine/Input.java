package engine;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;

public class Input {
        private static final boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];
        private static final boolean[] buttons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];
        private static double mouseX, mouseY;
        private static double scrollX, scrollY;
    
        private GLFWKeyCallback keyboard;
        private GLFWCursorPosCallback mouseMove;
        private GLFWMouseButtonCallback mouseButtons;
        private GLFWScrollCallback mouseScroll;

        public Input(){
            keyboard = new GLFWKeyCallback() {
                public void invoke(long window, int key, int scancode, int action, int mods) { 
                    keys[key] = (action != GLFW.GLFW_RELEASE); // GLFW.GLFW_RELEASE means -1 as an integer if its not -1 it means action is one so it will return keys action to true
                }  
            };
            mouseMove = new GLFWCursorPosCallback() {
                public void invoke(long window, double xpos, double ypos) { 
                    mouseX = xpos;
                    mouseY = ypos;
                }  
            };
            mouseButtons = new GLFWMouseButtonCallback() {
                public void invoke(long window, int button, int action, int mods) { 
                    buttons[button] = (action != GLFW.GLFW_RELEASE);
                }  
            };

            mouseScroll = new GLFWScrollCallback(){
                public void invoke(long window, double xoffset, double yoffset) {
                    scrollX += xoffset;
                    scrollY += yoffset;
                }
            };
        }
    
    public static boolean isKeyDown(int key){
        return keys[key];
    }
    public static boolean isButtonDown(int button){
        return buttons[button];
    }

    public void destroy(){
        keyboard.free();
        mouseMove.free();
        mouseButtons.free();
        mouseScroll.free();
    }

    public static double getMouseX() {
        return mouseX;
    }

    public static double getMouseY() {
        return mouseY;
    }

    public static double getScrollX() {
        return scrollX;
    }

    public static double getScrollY() {
        return scrollY;
    }
    public static void setKey(int key, boolean value){
        keys[key] = value;
    }
    public static void setButton(int button, boolean value){
        buttons[button] = value;
    }

    public GLFWKeyCallback getKeyboardCallback() {
        return keyboard;
    }

    public GLFWCursorPosCallback getMouseMoveCallback() {
        return mouseMove;
    }

    public GLFWMouseButtonCallback getMouseButtonsCallback() {
        return mouseButtons;
    }

    public GLFWScrollCallback getMouseScrollCallback() {
        return mouseScroll;
    }
}
