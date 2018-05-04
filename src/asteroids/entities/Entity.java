package asteroids.entities;

import asteroids.maths.Vector3f;
import asteroids.graphics.Texture;
import asteroids.graphics.VertexArray;

public abstract class Entity {
    
    protected Texture texture;
    protected VertexArray mesh;
    protected Vector3f position;
    protected float angle;
    
    abstract public void render();
    
    abstract public void update();
}
