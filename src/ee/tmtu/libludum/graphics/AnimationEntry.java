package ee.tmtu.libludum.graphics;

public class AnimationEntry {

    public float time;
    public Texture texture;

    public AnimationEntry(Texture texture, float time) {
        this.texture = texture;
        this.time = time;
    }

}
