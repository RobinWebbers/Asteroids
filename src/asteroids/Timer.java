package asteroids;

import asteroids.writing.Writing;
import asteroids.maths.Vector3f;


public class Timer {
    
    final private static int SECOND_IN_NANOS = 1000000000;
    
    private static int nanosPerFrame;
    private static int fps;
    
    //frame counter
    private static int frames;
    
    //timer used to determine whether 1 second has passed
    private static long fpsTimer;
    
    //last time nextFrame() returned true
    private static long lastFrameTime;
    
    //start of the first nextFrame() call
    public static long startTime;
    
    //time between last frame and current frame
    private static long deltaTime;
    
    
    private Timer(){}
    
    public static void startTimer(){
        fpsTimer = System.nanoTime();
        startTime = fpsTimer;
    }
    
    public static void setFpsCap(int fpsCap){
        if(fpsCap <= 0){
            nanosPerFrame = 0;
        }else{
            nanosPerFrame = SECOND_IN_NANOS/fpsCap;
        }
    }
    
    public static boolean nextFrame(){
        boolean result = false;
        long currentTime = System.nanoTime();
        
        if(currentTime - lastFrameTime >= nanosPerFrame){
            deltaTime = currentTime - lastFrameTime;
            lastFrameTime = currentTime;
            result = true; 
            frames++;
        }
        if(currentTime - fpsTimer > SECOND_IN_NANOS){
            fpsTimer += SECOND_IN_NANOS;
            fps = frames;
            frames = 0;
            System.out.println(fps); 
        }
        return result;
    }
    
    public static long getDeltaTime(){
        return deltaTime;
    }
    
    public static void displayFps(){
        Writing.drawText(fps + " fps", new Vector3f(16.0f, 9.0f, -1.0f), 0.5f, Writing.TOP_RIGHT);
        //System.out.println(fps); 
    }
}