package ee.tmtu.libludum.graphics.opengl;

import java.nio.*;

import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glUnmapBuffer;

public class MappedVertexBuffer {

    private int target;
    private ByteBuffer buffer;

    protected MappedVertexBuffer(int target, ByteBuffer buffer) {
        this.target = target;
        this.buffer = buffer;
    }

    public <T extends Buffer> T buffer() {
        return buffer(null);
    }

    public <T extends Buffer> T buffer(Class<T> c) {
        if (c == null) {
            return (T) buffer.order(ByteOrder.nativeOrder());
        } else if (c.getClass() == FloatBuffer.class.getClass()) {
            return (T) buffer.order(ByteOrder.nativeOrder()).asFloatBuffer();
        } else if (c.getClass() == DoubleBuffer.class.getClass()) {
            return (T) buffer.order(ByteOrder.nativeOrder()).asDoubleBuffer();
        } else if (c.getClass() == IntBuffer.class.getClass()) {
            return (T) buffer.order(ByteOrder.nativeOrder()).asIntBuffer();
        }
        return null;
    }

    public void unmap() {
        glUnmapBuffer(this.target);
        glBindBuffer(this.target, 0);
    }

}
