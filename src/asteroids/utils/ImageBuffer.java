package asteroids.utils;

import java.nio.*;
import org.lwjgl.stb.*;
import org.lwjgl.system.*;

public class ImageBuffer {
    public ByteBuffer get_image() {
        return image;
    }

    public int get_width() {
        return width;
    }

    public int get_heigh() {
        return heigh;
    }

    private ByteBuffer image;
    private int width, heigh;

    private ImageBuffer(int width, int heigh, ByteBuffer image) {
        this.image = image;
        this.heigh = heigh;
        this.width = width;
    }
    
    public static ImageBuffer load_image(String path) {
        ByteBuffer image;
        int width, heigh;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer comp = stack.mallocInt(1);
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);

            image = STBImage.stbi_load(path, w, h, comp, 4);
            if (image == null) {
                throw new RuntimeException("Could not load image resources.");
            }
            width = w.get();
            heigh = h.get();
        }
        return new ImageBuffer(width, heigh, image);
    }
}
