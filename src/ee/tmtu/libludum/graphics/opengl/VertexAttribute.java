package ee.tmtu.libludum.graphics.opengl;

import static org.lwjgl.opengl.GL11.*;

public class VertexAttribute {

    public static final VertexAttribute UV = new VertexAttribute(GL_TEXTURE_COORD_ARRAY, 2, GL_FLOAT);
    public static final VertexAttribute XY = new VertexAttribute(GL_VERTEX_ARRAY, 2, GL_FLOAT);
    public static final VertexAttribute XYZ = new VertexAttribute(GL_VERTEX_ARRAY, 3, GL_FLOAT);
    public static final VertexAttribute RGB = new VertexAttribute(GL_COLOR_ARRAY, 3, GL_FLOAT);
    public static final VertexAttribute RGBA = new VertexAttribute(GL_COLOR_ARRAY, 4, GL_FLOAT);

    public int id;
    public int len;
    public int type;

    public VertexAttribute(int id, int len, int type) {
        this.id = id;
        this.len = len;
        this.type = type;
    }

    public void enable(int stride, int offset) {
        glEnableClientState(this.id);
        switch (id) {
            case GL_TEXTURE_COORD_ARRAY:
                glTexCoordPointer(this.len, this.type, stride, offset);
                break;
            case GL_VERTEX_ARRAY:
                glVertexPointer(this.len, this.type, stride, offset);
                break;
            case GL_COLOR_ARRAY:
                glColorPointer(this.len, this.type, stride, offset);
                break;
        }
    }

    public void disable() {
        glDisableClientState(this.id);
    }

}
