package ee.tmtu.libludum.graphics;

import ee.tmtu.libludum.core.Disposable;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

public class FrameBuffer extends GLBuffer implements Bindable, Disposable {

    public Texture texture;
    public RenderBuffer depth;

    public int width, height;
    public int status;
    public int id;

    public FrameBuffer(int width, int height) {
        super(GL_FRAMEBUFFER);
        this.width = width;
        this.height = height;

        this.texture = new Texture(GL_TEXTURE_2D, this.width, this.height, Texture.FillMode.RESERVE_NULL);
        this.texture.filter(GL_NEAREST);
        this.texture.wrap(GL_CLAMP);

        this.id = glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, this.id);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, this.texture.id, 0);

        this.depth = new RenderBuffer(this.width, this.height);

        this.status = glCheckFramebufferStatus(GL_FRAMEBUFFER);
        glBindRenderbuffer(GL_RENDERBUFFER, 0);
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    @Override
    public void bind() {
        glBindFramebuffer(GL_FRAMEBUFFER, this.id);
    }

    @Override
    public void unbind() {
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    @Override
    public void dispose() {
        glDeleteFramebuffers(this.id);
        this.texture.dispose();
        this.depth.dispose();
    }

}
