package ee.tmtu.libludum.graphics.opengl;

import ee.tmtu.libludum.core.Bindable;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;

public class VertexArray implements Bindable {

    private FloatBuffer data;
    private VertexAttribute[] attributes;
    private int stride = 0;
    private int len = 0;
    private int size;

    public VertexArray(int size, VertexAttribute... attributes) {
        this.attributes = attributes;
        for (VertexAttribute va : this.attributes) {
            len += va.len;
            stride += va.len * GLHelper.sizeof(va.type);
        }
        this.size = size * len;
        this.data = BufferUtils.createFloatBuffer(this.size);
    }

    public FloatBuffer buffer() {
        return this.data;
    }

    public void draw(int type, int elements) {
        glDrawArrays(type, 0, elements);
    }

    public void bind() {
        int offset = 0;
        for (VertexAttribute va : this.attributes) {
            this.data.position(offset);
            glEnableClientState(va.id);
            switch (va.id) {
                case GL_VERTEX_ARRAY:
                    glVertexPointer(va.len, stride, this.data);
                    break;
                case GL_COLOR_ARRAY:
                    glColorPointer(va.len, stride, this.data);
                    break;
                case GL_TEXTURE_COORD_ARRAY:
                    glTexCoordPointer(va.len, stride, this.data);
                    break;
            }
            offset += va.len;
        }
    }

    public void unbind() {
        for (VertexAttribute va : this.attributes) {
            va.disable();
        }
    }

    @Override
    public String toString() {
        return String.format("Vertex Array [size=%s, stride=%s]", this.size, this.stride);
    }
}
