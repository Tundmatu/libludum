package ee.tmtu.libludum.graphics;

import org.lwjgl.util.Color;
import org.lwjgl.util.ReadableColor;

import static org.lwjgl.opengl.GL11.*;

public class SpriteBatch {

    private static final int VERTICES_PER_QUAD = 6;

    private VertexArray vertexArray;
    private Texture currentTexture;

    private ReadableColor color;
    private boolean drawing;
    private int maxIndex;
    private int index;

    public SpriteBatch(int size) {
        this.vertexArray = new VertexArray(size * VERTICES_PER_QUAD, VertexAttribute.XY, VertexAttribute.RGBA, VertexAttribute.UV);

        this.maxIndex = size * VERTICES_PER_QUAD;
    }

    public void start() {
        if (this.drawing) {
            throw new IllegalStateException("SpriteBatch.end() must be called before attempting to start new batch.");
        }

        this.drawing = true;
        this.index = 0;
        this.currentTexture = null;
    }

    public void end() {
        if (!this.drawing) {
            throw new IllegalStateException("SpriteBatch.start() must be called before attempting to end batch.");
        }

        this.flush();
        this.drawing = false;
    }

    public void flush() {
        if (this.index > 0) {
            this.vertexArray.buffer().flip();
            this.render();
            this.index = 0;
            this.vertexArray.buffer().clear();
        }
    }

    public void render() {
        if (this.currentTexture != null) {
            glBindTexture(GL_TEXTURE_2D, this.currentTexture.id);
        }

        this.vertexArray.bind();
        this.vertexArray.draw(GL_TRIANGLES, this.index);
        this.vertexArray.unbind();
    }

    public ReadableColor getColor() {
        return color;
    }

    public void setColor(ReadableColor color) {
        this.color = color;
    }

    public void drawRegion(Texture tex, float srcX, float srcY, float srcWidth, float srcHeight, float dstX, float dstY) {
        drawRegion(tex, srcX, srcY, srcWidth, srcHeight, dstX, dstY, srcWidth, srcHeight);
    }

    public void drawRegion(Texture tex, float srcX, float srcY, float srcWidth, float srcHeight, float dstX, float dstY, float dstWidth, float dstHeight) {
        float u = srcX / tex.width;
        float v = srcY / tex.height;
        float u2 = (srcX + srcWidth) / tex.width;
        float v2 = (srcY + srcHeight) / tex.height;
        draw(tex, dstX, dstY, dstWidth, dstHeight, u, v, u2, v2);
    }

    public void draw(Texture tex, float x, float y, float width, float height, float originX, float originY, float rotationRadians, float u, float v, float u2, float v2) {
        if (this.currentTexture == null) {
            this.currentTexture = tex;
        }

        if (!this.currentTexture.equals(tex) || this.index > this.maxIndex) {
            this.flush();
            this.currentTexture = tex;
        }

        final float r = color.getRed() / 255.f;
        final float g = color.getGreen() / 255.f;
        final float b = color.getBlue() / 255.f;
        final float a = color.getAlpha() / 255.f;


        float x1, y1, x2, y2, x3, y3, x4, y4;

        if (rotationRadians != 0) {
            float scaleX = 1f;//width/tex.getWidth();
            float scaleY = 1f;//height/tex.getHeight();

            float cx = originX * scaleX;
            float cy = originY * scaleY;

            float p1x = -cx;
            float p1y = -cy;
            float p2x = width - cx;
            float p2y = -cy;
            float p3x = width - cx;
            float p3y = height - cy;
            float p4x = -cx;
            float p4y = height - cy;

            final float cos = (float) Math.cos(rotationRadians);
            final float sin = (float) Math.sin(rotationRadians);

            x1 = x + (cos * p1x - sin * p1y) + cx; // TOP LEFT
            y1 = y + (sin * p1x + cos * p1y) + cy;
            x2 = x + (cos * p2x - sin * p2y) + cx; // TOP RIGHT
            y2 = y + (sin * p2x + cos * p2y) + cy;
            x3 = x + (cos * p3x - sin * p3y) + cx; // BOTTOM RIGHT
            y3 = y + (sin * p3x + cos * p3y) + cy;
            x4 = x + (cos * p4x - sin * p4y) + cx; // BOTTOM LEFT
            y4 = y + (sin * p4x + cos * p4y) + cy;
        } else {
            x1 = x;
            y1 = y;

            x2 = x + width;
            y2 = y;

            x3 = x + width;
            y3 = y + height;

            x4 = x;
            y4 = y + height;
        }

        // top left, top right, bottom left
        vertex(x1, y1, r, g, b, a, u, v);
        vertex(x2, y2, r, g, b, a, u2, v);
        vertex(x4, y4, r, g, b, a, u, v2);

        // top right, bottom right, bottom left
        vertex(x2, y2, r, g, b, a, u2, v);
        vertex(x3, y3, r, g, b, a, u2, v2);
        vertex(x4, y4, r, g, b, a, u, v2);
    }

    public void draw(Texture tex, float x, float y, float width, float height, float u, float v,
                     float u2, float v2) {
        draw(tex, x, y, width, height, x, y, 0f, u, v, u2, v2);
    }

    public void draw(Sprite sprite) {
        if (!this.drawing) {
            throw new IllegalStateException("SpriteBatch.start() must be called before attempting to draw.");
        }

        if (this.currentTexture == null) {
            this.currentTexture = sprite.getTexture();
        }

        if (!this.currentTexture.equals(sprite.getTexture()) || this.index > this.maxIndex) {
            this.flush();
            this.currentTexture = sprite.getTexture();
        }

        float x1, y1, x2, y2, x3, y3, x4, y4;

        if (sprite.getRotation() != 0) {
            float scaleX = sprite.getWidth() / 2;//width/tex.getWidth();
            float scaleY = sprite.getHeight() / 2;//height/tex.getHeight();

            float cx = scaleX;
            float cy = scaleY;

            float p1x = -cx;
            float p1y = -cy;
            float p2x = sprite.getWidth() - cx;
            float p2y = -cy;
            float p3x = sprite.getWidth() - cx;
            float p3y = sprite.getHeight() - cy;
            float p4x = -cx;
            float p4y = sprite.getHeight() - cy;

            final float cos = (float) Math.cos(sprite.getRotation());
            final float sin = (float) Math.sin(sprite.getRotation());

            x1 = sprite.getX() - sprite.getWidth() / 2 + (cos * p1x - sin * p1y) + cx;
            y1 = sprite.getY() - sprite.getHeight() / 2 + (sin * p1x + cos * p1y) + cy;
            x2 = sprite.getX() - sprite.getWidth() / 2 + (cos * p2x - sin * p2y) + cx;
            y2 = sprite.getY() - sprite.getHeight() / 2 + (sin * p2x + cos * p2y) + cy;
            x3 = sprite.getX() - sprite.getWidth() / 2 + (cos * p3x - sin * p3y) + cx;
            y3 = sprite.getY() - sprite.getHeight() / 2 + (sin * p3x + cos * p3y) + cy;
            x4 = sprite.getX() - sprite.getWidth() / 2 + (cos * p4x - sin * p4y) + cx;
            y4 = sprite.getY() - sprite.getHeight() / 2 + (sin * p4x + cos * p4y) + cy;
        } else {
            x1 = sprite.getX() - sprite.getWidth() / 2;
            y1 = sprite.getY() - sprite.getHeight() / 2;

            x2 = sprite.getX() + sprite.getWidth() / 2;
            y2 = sprite.getY() - sprite.getHeight() / 2;

            x3 = sprite.getX() + sprite.getWidth() / 2;
            y3 = sprite.getY() + sprite.getHeight() / 2;

            x4 = sprite.getX() - sprite.getWidth() / 2;
            y4 = sprite.getY() + sprite.getHeight() / 2;
        }

        vertex(x1, y1, sprite.getRed(), sprite.getGreen(), sprite.getBlue(), sprite.getAlpha(), sprite.getU(), sprite.getV());
        vertex(x2, y2, sprite.getRed(), sprite.getGreen(), sprite.getBlue(), sprite.getAlpha(), sprite.getU2(), sprite.getV());
        vertex(x4, y4, sprite.getRed(), sprite.getGreen(), sprite.getBlue(), sprite.getAlpha(), sprite.getU(), sprite.getV2());

        vertex(x2, y2, sprite.getRed(), sprite.getGreen(), sprite.getBlue(), sprite.getAlpha(), sprite.getU2(), sprite.getV());
        vertex(x3, y3, sprite.getRed(), sprite.getGreen(), sprite.getBlue(), sprite.getAlpha(), sprite.getU2(), sprite.getV2());
        vertex(x4, y4, sprite.getRed(), sprite.getGreen(), sprite.getBlue(), sprite.getAlpha(), sprite.getU(), sprite.getV2());
    }

    public void vertex(float x, float y, float r, float g, float b, float a, float u, float v) {
        this.vertexArray.buffer().put(x).put(y).put(r).put(g).put(b).put(a).put(u).put(v);
        this.index++;
    }

}
