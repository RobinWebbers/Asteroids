package gameclient.entities;

import gameclient.graphics.*;
import gameclient.maths.*;

public class Background extends Entity {
    
    private static Shader shader;
    
    public Background(){
        if(shader == null){
            throw new RuntimeException("Cannot instantiate object without shader");
        }
        
        float[] vertices = new float[] {
            -16.0f, -9.0f, 0.0f,
            -16.0f,  9.0f, 0.0f,
             16.0f,  9.0f, 0.0f,
             16.0f, -9.0f, 0.0f
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
        texture = new Texture("resources/space.jpg");
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
        
    }
    
    @Override
    public void render(){
        texture.bind();
        shader.enable();
        mesh.bind();
        mesh.draw();
        mesh.unbind();
        shader.disable();
        texture.unbind();
    }
}