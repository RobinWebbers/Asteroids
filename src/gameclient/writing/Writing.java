package gameclient.writing;

import gameclient.graphics.*;
import gameclient.maths.*;

public class Writing {
    
    private static Texture texture;
    private static Shader shader;
    private static VertexArray mesh;
    private static final int CHARS_IN_WIDTH = 16;
    private static final int CHARS_IN_HEIGHT = 16;
    private static final float WIDTH_PER_HEIGHT = 1.0f;
    
    private static final int COORDINATES_PER_CHAR = 12;
    private static final int INDICES_PER_CHAR = 6;
    private static final int TEXTURECOORDINATES_PER_CHAR = 8;
    
    public static final int TOP_LEFT = 0;
    public static final int TOP_RIGHT = 1;
    public static final int BOTTOM_LEFT = 2;
    public static final int BOTTOM_RIGHT = 3;
    
    
    private Writing(){}
    
    public static void initWriting(){
        Writing.setShader("shaders/background.vert", "shaders/background.frag");
        Writing.setBitmap("resources/font.png");
        Writing.setMesh();
    }
    
    public static void setBitmap(String path){
        texture = new Texture(path);
    }
    
    public static void setShader(String vertex, String frag){
        shader = new Shader(vertex, frag);
        shader.enable();
        shader.setUniformMat4f("projection_matrix", Matrix4f.orthographic(-16.0f, 16.0f, -9.0f, 9.0f, -1.0f, 1.0f));
        shader.setUniform1i("tex", 1);
        shader.disable();
    }
    
    public static void setMesh(){
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
        mesh = new VertexArray(vertices, indices, tcs);
    }
    
    private static void changeMesh(String text, Vector3f position, float verticalSize, int startingPoint){
        float horizontalSize = verticalSize / WIDTH_PER_HEIGHT;
        switch(startingPoint){
            case TOP_LEFT:
                break;
            case TOP_RIGHT:
                position.x -= text.length() * horizontalSize;
                break;
            case BOTTOM_LEFT:
                position.y += verticalSize;
                break;
            case BOTTOM_RIGHT:
                position.x -= text.length() * horizontalSize;
                position.y += verticalSize;
                break;
            default:
                throw new RuntimeException("Invalid StartingPoint");
        }
        
        float[] vertices = new float[text.length() * COORDINATES_PER_CHAR];
        for(int i = 0; i < vertices.length; i+=COORDINATES_PER_CHAR ){
            //bottomleft vertex
            vertices[i]      = position.x + horizontalSize * i/COORDINATES_PER_CHAR;
            vertices[i + 1]  = position.y - verticalSize;
            vertices[i + 2]  = position.z;
            
            //topleft vertex
            vertices[i + 3]  = position.x + horizontalSize * i/COORDINATES_PER_CHAR;
            vertices[i + 4]  = position.y;
            vertices[i + 5]  = position.z;
            
            //topright vertex
            vertices[i + 6]  = position.x + horizontalSize * (1 + (i/COORDINATES_PER_CHAR));
            vertices[i + 7]  = position.y;
            vertices[i + 8]  = position.z;
            
            //bottomright vertex
            vertices[i + 9]  = position.x + horizontalSize * (1 + (i/COORDINATES_PER_CHAR));
            vertices[i + 10] = position.y - verticalSize;
            vertices[i + 11] = position.z;
        }
        
        byte[] indices = new byte[text.length() * INDICES_PER_CHAR];
        int count = 0;
        for(int i = 0; i < indices.length; i+=INDICES_PER_CHAR){
            //topleft triangle
            indices[i]     = (byte)count;
            indices[i + 1] = (byte)(count + 1);
            indices[i + 2] = (byte)(count + 2);
            
            //bottomright triangle
            indices[i + 3] = (byte)count;
            indices[i + 4] = (byte)(count + 2);
            indices[i + 5] = (byte)(count + 3);
            count+=4;
        }
        
        float[] tcs = new float[text.length() * TEXTURECOORDINATES_PER_CHAR];
        for(int i = 0; i < tcs.length; i+=TEXTURECOORDINATES_PER_CHAR){
            int character = text.charAt(i/TEXTURECOORDINATES_PER_CHAR);
            int x = character%CHARS_IN_WIDTH;
            int y = character/CHARS_IN_WIDTH;
            
            //bottomleft texture coordinates
            tcs[i]     = x * 1.0f/CHARS_IN_WIDTH;
            tcs[i + 1] = (y + 1) * 1.0f/CHARS_IN_HEIGHT;
            
            //topleft texture coordinates
            tcs[i + 2] = x * 1.0f/CHARS_IN_WIDTH;
            tcs[i + 3] = y * 1.0f/CHARS_IN_HEIGHT;
            
            //topright texture coordinates
            tcs[i + 4] = (x + 1) * 1.0f/CHARS_IN_WIDTH;
            tcs[i + 5] = y * 1.0f/CHARS_IN_HEIGHT;
            
            //bottomright texture coordinates
            tcs[i + 6] = (x + 1) * 1.0f/CHARS_IN_WIDTH;
            tcs[i + 7] = (y + 1) * 1.0f/CHARS_IN_HEIGHT;
        }
        mesh.changeBuffers(vertices, indices, tcs);
    }
    
    
    
    public static void drawText(String text, Vector3f position,float verticalSize, int startingPoint){
        changeMesh(text, position, verticalSize, startingPoint);
        
        texture.bind();
        shader.enable();
        mesh.bind();
        mesh.draw();
        mesh.unbind();
        shader.disable();
        texture.unbind();
    }
}
