package ee.tmtu.libludum.ui;

import ee.tmtu.libludum.ui.event.Event;

public abstract class Component {

    public float x, y, width, height;
    public ComponentState state;
    public Orientation orientation;
    public Margin margin;
    public Padding padding;
    public Root root;

    public void onEvent(Event event) {

    }

    public enum ComponentState {
        DOWN, UP, IDLE, HOVER, FOCUS;
    }

}
