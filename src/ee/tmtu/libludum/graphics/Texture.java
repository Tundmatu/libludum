package ee.tmtu.libludum.graphics;

import ee.tmtu.libludum.core.Disposable;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;

public class Texture implements Bindable, Disposable {

    public int width, height;
    public int target;
    public int id;

    public Texture(int target, int width, int height, ByteBuffer buffer) {
        this.target = target;
        this.width = width;
        this.height = height;
        this.id = glGenTextures();
        glBindTexture(this.target, this.id);
        glTexImage2D(this.target, 0, GL_RGBA8, this.width, this.height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
    }

    public Texture(int target, int width, int height, FillMode fm) {
        this.target = target;
        this.width = width;
        this.height = height;
        this.id = glGenTextures();
        glBindTexture(this.target, this.id);
        switch (fm) {
            case RESERVE_NULL:
                glTexImage2D(this.target, 0, GL_RGBA8, this.width, this.height, 0, GL_RGBA, GL_UNSIGNED_BYTE, (ByteBuffer) null);
                break;
            case RESERVE_MEMORY:
                glTexImage2D(this.target, 0, GL_RGBA8, this.width, this.height, 0, GL_RGBA, GL_UNSIGNED_BYTE, BufferUtils.createByteBuffer(width * height * 4));
                break;
        }
    }

    public void filter(int filter) {
        this.filter(filter, filter);
    }

    public void filter(int min, int mag) {
        this.min(min);
        this.mag(mag);
    }

    public void min(int filter) {
        glTexParameteri(this.target, GL_TEXTURE_MIN_FILTER, filter);
    }

    public void mag(int filter) {
        glTexParameteri(this.target, GL_TEXTURE_MAG_FILTER, filter);
    }

    public void wrap(int wrap) {
        this.wrap(wrap, wrap);
    }

    public void wrap(int wraps, int wrapt) {
        this.wraps(wraps);
        this.wrapt(wrapt);
    }

    public void wraps(int wrap) {
        glTexParameteri(this.target, GL_TEXTURE_WRAP_S, wrap);
    }

    public void wrapt(int wrap) {
        glTexParameteri(this.target, GL_TEXTURE_WRAP_T, wrap);
    }

    @Override
    public void bind() {
        glBindTexture(this.target, this.id);
    }

    @Override
    public void unbind() {
        glBindTexture(this.target, 0);
    }

    @Override
    public void dispose() {
        glDeleteTextures(this.id);
    }

    @Override
    public boolean equals(Object rhs) {
        return (rhs instanceof Texture) && this.id == ((Texture) rhs).id;
    }

    public enum FillMode {
        RESERVE_NULL, RESERVE_MEMORY;
    }

}
