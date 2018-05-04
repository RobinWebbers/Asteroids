package asteroids.graphics;

import asteroids.utils.ShaderUtils;
import asteroids.maths.Vector3f;
import asteroids.maths.Matrix4f;
import java.util.*;
import static org.lwjgl.opengl.GL20.*;


public class Shader {
    
    public static final int VERTEX_ATTRIBUTE = 0;
    public static final int TEXTURE_COORDINATES_ATTRIBUTE = 1;
    
    private final int ID;
    private final Map<String, Integer> uniformCache;
    
    public Shader(String vertex, String fragment){
        uniformCache = new HashMap();
        ID = ShaderUtils.load(vertex, fragment);
    }
    
    public int getUniform(String name){
        if(uniformCache.containsKey(name)){
            return uniformCache.get(name);
        }
        int result = glGetUniformLocation(ID, name);
        if(result == -1){
            throw new RuntimeException("Could not find uniform variable '" + name + "'.");
        }else{
            uniformCache.put(name, result);
        }
        return result;
    }
    
    public void setUniform1i(String name, int x){
        glUniform1i(getUniform(name), x);
    }
    
    public void setUniform1f(String name, int x){
        glUniform1f(getUniform(name), x);
    }
    
    public void setUniform2f(String name, float x, float y){
        glUniform2f(getUniform(name), x, y);
    }
    
    public void setUniform3f(String name, Vector3f vector){
        glUniform3f(getUniform(name), vector.x, vector.y, vector.z);
    }
    
    public void setUniformMat4f(String name, Matrix4f matrix){
        glUniformMatrix4fv(getUniform(name), false, matrix.toFloatBuffer());
    }
    
    public void enable(){
        glUseProgram(ID);
    }
    
    public void disable(){
        glUseProgram(0);
    }
}