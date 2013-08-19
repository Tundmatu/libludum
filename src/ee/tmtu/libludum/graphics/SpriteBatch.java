package ee.tmtu.libludum.graphics;

import static org.lwjgl.opengl.GL11.*;

public class SpriteBatch {

    private static final int VERTICES_PER_QUAD = 6;

    private VertexArray vertexArray;
    private Sprite currentSprite;

    private boolean drawing;
    private int maxIndex;
    private int index;

    public SpriteBatch(int size) {
        this.vertexArray = new VertexArray(size * VERTICES_PER_QUAD, VertexAttribute.XY, VertexAttribute.RGBA, VertexAttribute.UV);

        this.maxIndex = size * VERTICES_PER_QUAD;
    }

    public void start() {
        if(this.drawing) {
            throw new IllegalStateException("SpriteBatch.end() must be called before attempting to start new batch.");
        }

        this.drawing = true;
        this.index = 0;
        this.currentSprite = null;
    }

    public void end() {
        if(!this.drawing) {
            throw new IllegalStateException("SpriteBatch.start() must be called before attempting to end batch.");
        }

        this.flush();
        this.drawing = false;
    }

    public void flush() {
        if(this.index > 0) {
            this.vertexArray.buffer().flip();
            this.render();
            this.index = 0;
            this.vertexArray.buffer().clear();
        }
    }

    public void render() {
        if(this.currentSprite.getTexture() != null) {
            glBindTexture(GL_TEXTURE_2D, this.currentSprite.getTexture().id);
        }

        this.vertexArray.bind();
        this.vertexArray.draw(GL_TRIANGLES, this.index);
        this.vertexArray.unbind();
    }

    public void draw(Sprite sprite) {
        if(!this.drawing) {
            throw new IllegalStateException("SpriteBatch.start() must be called before attempting to draw.");
        }

        if(this.currentSprite == null) {
            this.currentSprite = sprite;
        }

        if(!this.currentSprite.getTexture().equals(sprite.getTexture()) || this.index > this.maxIndex) {
            this.flush();
            this.currentSprite = sprite;
        }

        float x1,y1, x2,y2, x3,y3, x4,y4;

        if (sprite.getRotation() != 0) {
            float scaleX = sprite.getWidth()/2;//width/tex.getWidth();
            float scaleY = sprite.getHeight()/2;//height/tex.getHeight();

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

            x1 = sprite.getX() - sprite.getWidth()/2 + (cos * p1x - sin * p1y) + cx;
            y1 = sprite.getY() - sprite.getHeight()/2+ (sin * p1x + cos * p1y) + cy;
            x2 = sprite.getX() - sprite.getWidth()/2+ (cos * p2x - sin * p2y) + cx;
            y2 = sprite.getY() - sprite.getHeight()/2+ (sin * p2x + cos * p2y) + cy;
            x3 = sprite.getX() - sprite.getWidth()/2+ (cos * p3x - sin * p3y) + cx;
            y3 = sprite.getY() - sprite.getHeight()/2+ (sin * p3x + cos * p3y) + cy;
            x4 = sprite.getX() - sprite.getWidth()/2+ (cos * p4x - sin * p4y) + cx;
            y4 = sprite.getY() - sprite.getHeight()/2+ (sin * p4x + cos * p4y) + cy;
        } else {
            x1 = sprite.getX() - sprite.getWidth()/2;
            y1 = sprite.getY() - sprite.getHeight()/2;

            x2 = sprite.getX() + sprite.getWidth()/2;
            y2 = sprite.getY() - sprite.getHeight()/2;

            x3 = sprite.getX() + sprite.getWidth()/2;
            y3 = sprite.getY() + sprite.getHeight()/2;

            x4 = sprite.getX() - sprite.getWidth()/2;
            y4 = sprite.getY() + sprite.getHeight()/2;
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
