package ee.tmtu.libludum.graphics;

import ee.tmtu.libludum.ui.Drawable;
import org.lwjgl.util.vector.Vector2f;

public class Patch9 implements Drawable {

    public Texture texture;
    public Vector2f startCorner;
    public Vector2f endCorner;
    public float xInsetStart, xInsetEnd;
    public float yInsetStart, yInsetEnd;
    public int inset;

    public Patch9(Texture texture, int inset) {
        this(texture, 0, 0, texture.width, texture.height, inset);
    }

    public Patch9(Texture texture, int xStart, int yStart, int xEnd, int yEnd, int inset) {
        this.texture = texture;
        this.inset = inset;
        this.startCorner = new Vector2f(xStart, yStart);
        this.endCorner = new Vector2f(xEnd, yEnd);
        this.xInsetStart = this.inset;
        this.xInsetEnd = this.texture.width - this.inset;
        this.yInsetStart = this.inset;
        this.yInsetEnd = this.texture.height - this.inset;
    }

    @Override
    public void draw(SpriteBatch batch, float x, float y, float width, float height) {
        // Static regions
        batch.drawRegion(this.texture, this.startCorner.x, this.startCorner.y, xInsetStart, yInsetStart, x, y);
        batch.drawRegion(this.texture, this.startCorner.x, this.yInsetEnd, xInsetStart, this.inset, x, y + height - (this.inset));
        batch.drawRegion(this.texture, this.xInsetEnd, this.startCorner.y, this.inset, this.inset, x + width - inset, y);
        batch.drawRegion(this.texture, this.xInsetEnd, this.yInsetEnd, this.inset, this.inset, x + width - inset, y + height - (this.inset));

        // Dynamic Regions
        batch.drawRegion(this.texture, this.startCorner.x, this.yInsetStart, this.xInsetStart, inset, x, y + yInsetStart, this.xInsetStart, height - inset * 2);
        batch.drawRegion(this.texture, this.inset, this.startCorner.y, this.inset, this.inset, x + this.inset, y, width - this.inset * 2, this.inset);
        batch.drawRegion(this.texture, this.inset, this.yInsetEnd, this.inset, this.inset, x + this.inset, y + height - inset, width - this.inset * 2, this.inset);
        batch.drawRegion(this.texture, this.xInsetEnd, this.yInsetStart, this.inset, this.inset, x + width - inset, y + yInsetStart, this.xInsetStart, height - inset * 2);

        // Middle piece
        batch.drawRegion(this.texture, this.inset, this.inset, this.inset, this.inset, x + inset, y + inset, width - inset * 2, height - inset * 2);

    }

}
