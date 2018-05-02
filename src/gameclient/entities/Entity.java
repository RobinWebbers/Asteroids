package gameclient.entities;

import gameclient.graphics.*;
import gameclient.maths.*;

public abstract class Entity {
    
    protected Texture texture;
    protected VertexArray mesh;
    protected Vector3f position;
    protected float angle;
    
    abstract public void render();
    
    abstract public void update();
}
