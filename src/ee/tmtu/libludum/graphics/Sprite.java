package ee.tmtu.libludum.graphics;

import ee.tmtu.libludum.ui.Drawable;

public class Sprite implements Drawable {

    private Texture texture;

    private float x, y, width, height;
    private float r, g, b, a;
    private float u, v, u2, v2;
    private float rotation;

    public Sprite(Texture handle) {
        this.texture = handle;
        this.width = handle.width;
        this.height = handle.height;
        this.r = 1.f;
        this.g = 1.f;
        this.b = 1.f;
        this.a = 1.f;
        this.u = 0.f;
        this.v = 0.f;
        this.u2 = 1.f;
        this.v2 = 1.f;
    }

    public Sprite(Texture handle, float x, float y, float width, float height) {
        this.texture = handle;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.r = 1.f;
        this.g = 1.f;
        this.b = 1.f;
        this.a = 1.f;
        this.u = 0.f;
        this.v = 0.f;
        this.u2 = 1.f;
        this.v2 = 1.f;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getRed() {
        return r;
    }

    public void setRed(float r) {
        this.r = r;
    }

    public float getGreen() {
        return g;
    }

    public void setGreen(float g) {
        this.g = g;
    }

    public float getBlue() {
        return b;
    }

    public void setBlue(float b) {
        this.b = b;
    }

    public float getAlpha() {
        return a;
    }

    public void setAlpha(float a) {
        this.a = a;
    }

    public void setRGBA(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public void setTextureRegion(int x, int y, int width, int height) {
        this.u = (float) x / (float) this.texture.width;
        this.v = (float) y / (float) this.texture.height;
        this.u2 = (float) (x + width) / (float) this.texture.width;
        this.v2 = (float) (y + height) / (float) this.texture.height;
    }

    public float getU() {
        return u;
    }

    public void setU(float u) {
        this.u = u;
    }

    public float getV() {
        return v;
    }

    public void setV(float v) {
        this.v = v;
    }

    public float getU2() {
        return u2;
    }

    public void setU2(float u2) {
        this.u2 = u2;
    }

    public float getV2() {
        return v2;
    }

    public void setV2(float v2) {
        this.v2 = v2;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    @Override
    public void draw(SpriteBatch batch, float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        batch.draw(this);
    }
}
