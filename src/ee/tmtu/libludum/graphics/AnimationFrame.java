package ee.tmtu.libludum.graphics;

import ee.tmtu.libludum.graphics.opengl.Texture;
import org.lwjgl.util.vector.Vector4f;

public class AnimationFrame {

    public float time;
    public Vector4f coords;
    public Texture texture;

    public AnimationFrame(Texture texture, float time) {
        this.texture = texture;
        this.time = time;
    }

    public AnimationFrame(Texture texture, float time, Vector4f coords) {
        this.texture = texture;
        this.time = time;
        this.coords = coords;
    }

}
