package ee.tmtu.libludum.graphics;

import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

public class VertexBuffer extends GLBuffer {

    private VertexAttribute[] attributes;
    private int stride;
    private int size;

    public VertexBuffer(int usage, int size, VertexAttribute... attributes) {
        super(GL_ARRAY_BUFFER);
        this.attributes = attributes;
        this.size = size;
        for (VertexAttribute va : this.attributes) {
            stride += va.len * GLHelper.sizeof(va.type);
        }
        glBindBuffer(this.target, this.id);
        glBufferData(this.target, BufferUtils.createByteBuffer(size), usage);
        glBindBuffer(this.target, 0);
    }

    public MappedVertexBuffer map(int access) {
        glBindBuffer(this.target, this.id);
        return new MappedVertexBuffer(this.target, glMapBuffer(this.target, access, null));
    }

    public void unmap() {
        glUnmapBuffer(this.target);
        glBindBuffer(this.target, 0);
    }

    public void draw(int mode, int elements) {
        int offset = 0;
        glBindBuffer(this.target, this.id);
        for (VertexAttribute va : this.attributes) {
            va.enable(this.stride, offset);
            offset += va.len * GLHelper.sizeof(va.type);
        }
        glDrawArrays(mode, 0, elements);
        for (VertexAttribute va : this.attributes) {
            va.disable();
        }
        glBindBuffer(this.target, 0);
    }

    @Override
    public String toString() {
        return String.format("Vertex Buffer Object [size=%s, stride=%s]", this.size, this.stride);
    }

}
