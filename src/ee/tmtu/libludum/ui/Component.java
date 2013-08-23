package ee.tmtu.libludum.ui;

import ee.tmtu.libludum.graphics.SpriteBatch;
import ee.tmtu.libludum.ui.event.Event;
import ee.tmtu.libludum.ui.event.MouseEvent;

public abstract class Component {

    public float x, y, width, height;
    public Drawable drawable;
    public ComponentState state;
    public Orientation orientation;
    public Margin margin;
    public Padding padding;
    public Root root;

    public Component(Margin margin, Padding padding) {
        this.margin = margin;
        this.padding = padding;
    }

    public void onEvent(Event event) {

    }

    public void onEnter(MouseEvent event) {

    }

    public void onLeave(MouseEvent event) {

    }

    public void onDown(MouseEvent event) {

    }

    public void onMove(MouseEvent event) {

    }

    public void onUp(MouseEvent event) {

    }

    public void update() {

    }

    public boolean isInside(int x, int y) {
        return x > this.x && x < this.x + this.width && y > this.y && y < this.y + this.height;
    }

    public void draw(SpriteBatch batch, double lerp) {
        if(this.drawable != null) {
            this.drawable.draw(batch, this.x, this.y, this.width, this.height);
        }
    }

    public void layout() {

    }

    public enum ComponentState {
        DOWN, UP, IDLE, HOVER, FOCUS;
    }

}
