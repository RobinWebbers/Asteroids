package gameclient;

import gameclient.entities.*;
import gameclient.graphics.*;
import gameclient.writing.*;
import gameclient.utils.*;
import gameclient.input.*;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;


import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.system.MemoryUtil.*;



public class GameClient implements Runnable {
    
    Player spaceship;
    Background background;
    
    private Thread thread;
    
    private long window;
    
    private void start(){
        thread = new Thread(this, "game");
        thread.start();
    }
    
    private void setupWindow(){
        // Setup an error callback. The default implementation
	// will print the error message in System.err.
	GLFWErrorCallback.createPrint(System.err).set();
        
        // Initialize GLFW
        if (!glfwInit()){
            throw new IllegalStateException("Unable to initialize GLFW");
        }
        
	glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
	glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
        
        GLFWVidMode vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        if(vidmode == null){
            throw new RuntimeException("Failed to get video mode of primary monitor");
        }
        
        window = glfwCreateWindow(vidmode.width(), vidmode.height(), "Test_game", glfwGetPrimaryMonitor(), NULL);
        if(window == NULL){
            throw new RuntimeException("Failed to create the GLFW window");
        }
        
        GLFWImage image = GLFWImage.malloc();
        GLFWImage.Buffer imagebf = GLFWImage.malloc(1);
        ImageBuffer resource = ImageBuffer.load_image("resources/spaceship.png");
        image.set(resource.get_width(), resource.get_heigh(), resource.get_image());
        imagebf.put(0, image);
        glfwSetWindowIcon(window, imagebf);
        
        GLFW.glfwMakeContextCurrent(window);
        glfwSetKeyCallback(window, new Input());
        
        glfwShowWindow(window);
        
        GL.createCapabilities();
        glEnable(GL_DEPTH_TEST);
        
    }
    
    private void initializeEntities(){
        glActiveTexture(GL_TEXTURE1);
        Background.setShader("shaders/background.vert", "shaders/background.frag");
        background = new Background();
        
        
        Player.setShader("shaders/spaceship.vert", "shaders/spaceship.frag");
        spaceship = new Player();
        
        Writing.initWriting();
        
        
        
        Timer.setFpsCap(0);
    }
    
    private void update(){
        glfwPollEvents();
        background.update();
        spaceship.update();
    }
    
    private void render(){
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        background.render();
        spaceship.render();
        Timer.displayFps();
        
        glfwSwapBuffers(window);
    }
    
    private void closeWindow(){
        // Free the window callbacks and destroy the window
	glfwFreeCallbacks(window);
	glfwDestroyWindow(window);
        
        // Terminate GLFW and free the error callback
	glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
    
    @Override
    public void run(){
        
        setupWindow();
        
        initializeEntities();
        Timer.startTimer();
        while(!glfwWindowShouldClose(window)){
            if(Timer.nextFrame()){
                
                update();
                render();
            }
        }
        
        closeWindow();
    }
    
    public static void main(String[] args) {
        new GameClient().start();
    }
}