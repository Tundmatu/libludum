package ee.tmtu.libludum.graphics;

import java.util.ArrayList;

public class SpriteAnimation {

    private ArrayList<SpriteAnimationEntry> frames;
    private int index;
    private float time;

    public SpriteAnimation() {
        this(0);
    }

    public SpriteAnimation(int size) {
        this.frames = new ArrayList<>(size);
        this.index = 0;
        this.time = 0.f;
    }

    public void add(SpriteAnimationEntry frame) {
        this.frames.add(frame);
    }

    public void clear() {
        this.frames.clear();
    }

    public void update(float dt) {
        this.time += dt;
        if (this.time > this.frames.get(this.index).time) {
            this.index++;
            if (this.index > this.frames.size() - 1) {
                this.index = 0;
            }
            this.time -= this.frames.get(this.index).time;
        }
    }

    public Sprite frame() {
        return this.frames.get(this.index).sprite;
    }

}
