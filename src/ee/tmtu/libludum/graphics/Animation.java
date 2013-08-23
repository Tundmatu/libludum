package ee.tmtu.libludum.graphics;

import java.util.ArrayList;

public class Animation {

    private ArrayList<AnimationFrame> frames;
    private int index;
    private float time;

    public Animation() {
        this(0);
    }

    public Animation(int size) {
        this.frames = new ArrayList<>(size);
        this.index = 0;
        this.time = 0.f;
    }

    public void add(AnimationFrame frame) {
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

    public AnimationFrame frame() {
        return this.frames.get(this.index);
    }

}
