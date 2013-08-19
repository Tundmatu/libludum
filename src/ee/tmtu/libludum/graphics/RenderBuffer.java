package ee.tmtu.libludum.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL30.*;

public class RenderBuffer extends GLBuffer implements Bindable, Disposable {

    public int width, height;

    public RenderBuffer(int width, int height) {
        super(GL_RENDERBUFFER);
        this.id = glGenRenderbuffers();
        this.width = width;
        this.height = height;
        glBindRenderbuffer(GL_RENDERBUFFER, this.id);
        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT32F, this.width, this.height);
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, this.id);
    }

    @Override
    public void bind() {
        glBindRenderbuffer(GL_RENDERBUFFER, this.id);
    }

    @Override
    public void unbind() {
        glBindRenderbuffer(GL_RENDERBUFFER, 0);
    }

    @Override
    public void dispose() {
        glDeleteRenderbuffers(this.id);
    }

}
