package ee.tmtu.libludum.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

public abstract class GLBuffer implements Bindable, Disposable {

    public int target;
    public int id;

    public GLBuffer(int target) {
        this(target, glGenBuffers());
    }

    public GLBuffer(int target, int id) {
        this.target = target;
        this.id = id;
    }

    @Override
    public void bind() {
        glBindBuffer(this.target, this.id);
    }

    @Override
    public void unbind() {
        glBindBuffer(this.target, 0);
    }

    @Override
    public void dispose() {
        glDeleteBuffers(this.id);
    }

}
