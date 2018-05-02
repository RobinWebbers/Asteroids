package gameclient.entities;

import gameclient.maths.Vector3f;
import gameclient.maths.Matrix4f;
import gameclient.graphics.*;
import gameclient.input.*;
import gameclient.Timer;

import static org.lwjgl.glfw.GLFW.*;

public class Player extends Entity {
    
    private static Shader shader;
    private static final float SIZE = 2.0f;
    private static final float SPEED = 0.00000002f;
    private static final float ROTATIONAL_SPEED = 0.0000003f;
    
    public Player(){
        if(shader == null){
            throw new RuntimeException("Cannot instantiate object without shader");
        }
        
        float[] vertices = new float[] {
            -SIZE/ 2.0f, -SIZE/ 2.0f, -0.1f,
            -SIZE/ 2.0f,  SIZE/ 2.0f, -0.1f,
             SIZE/ 2.0f,  SIZE/ 2.0f, -0.1f,
             SIZE/ 2.0f, -SIZE/ 2.0f, -0.1f
        };
        
        byte[] indices = new byte[]{
             0, 1, 2,
             0, 2, 3
        };
        
        float[] tcs = new float[]{
             0, 1,
             0, 0,
             1, 0,
             1, 1
        };
        
        position = new Vector3f();
        mesh = new VertexArray(vertices, indices, tcs);
        texture = new Texture("resources/spaceship.png");
    }
    
    public static void setShader(String vertex, String frag){
        shader = new Shader(vertex, frag);
        shader.enable();
        shader.setUniformMat4f("projection_matrix", Matrix4f.orthographic(-16.0f, 16.0f, -9.0f, 9.0f, -1.0f, 1.0f));
        shader.setUniform1i("tex", 1);
        shader.disable();
    }
    
    @Override
    public void update(){
        if(Input.keys[GLFW_KEY_UP]){
            float r = (float) Math.toRadians(angle);
            float cos = (float) Math.cos(r);
            float sin = (float) Math.sin(r);
            
            
            position.x -= sin * SPEED * Timer.getDeltaTime();
            position.y += cos * SPEED * Timer.getDeltaTime();
        }
        if(Input.keys[GLFW_KEY_DOWN]){
            float r = (float) Math.toRadians(angle);
            float cos = (float) Math.cos(r);
            float sin = (float) Math.sin(r);
            
            position.x += sin * SPEED * Timer.getDeltaTime();
            position.y -= cos * SPEED * Timer.getDeltaTime();
            
        }
        if(Input.keys[GLFW_KEY_LEFT]){
            angle += ROTATIONAL_SPEED * Timer.getDeltaTime();
        }
        if(Input.keys[GLFW_KEY_RIGHT]){
            angle -= ROTATIONAL_SPEED * Timer.getDeltaTime();
        }
    }
    
    @Override
    public void render(){
        texture.bind();
        shader.enable();
        shader.setUniformMat4f("model_matrix", Matrix4f.translate(position));
        shader.setUniformMat4f("rotation_matrix", Matrix4f.rotate(angle));
        mesh.bind();
        mesh.draw();
        mesh.unbind();
        shader.disable();
        texture.unbind();
    }
}
