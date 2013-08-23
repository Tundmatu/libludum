package ee.tmtu.libludum.ui;

import ee.tmtu.libludum.graphics.SpriteBatch;
import ee.tmtu.libludum.ui.event.Event;
import ee.tmtu.libludum.ui.event.KeyEvent;
import ee.tmtu.libludum.ui.event.MouseEvent;

import java.util.Iterator;
import java.util.LinkedList;

public class Root extends Container {

    private Component hover;
    private Component focus;
    private Margin margin;
    private Padding padding;

    public Root(Margin margin, Padding padding) {
        super(margin, padding);
    }

    public void add(Component component) {
        component.root = this;
        this.components.add(component);
    }

    public void addFirst(Component component) {
        component.root = this;
        this.components.addFirst(component);
    }

    public void remove(Component component) {
        component.root = null;
        this.components.remove(component);
    }

    public void clear() {
        this.components.clear();
    }

    public void fire(Event event) {
        if (event instanceof KeyEvent) {
            KeyEvent ke = (KeyEvent) event;

        } else if (event instanceof MouseEvent) {
            MouseEvent me = (MouseEvent) event;
            Iterator<Component> iterator = this.components.iterator();
            Component component;
            boolean hoverset = false;
            while (iterator.hasNext()) {
                component = iterator.next();
                if (me.x > component.x && me.x < component.x + component.width) {
                    if (me.y > component.y && me.y < component.y + component.height) {
                        this.hover = component;
                        component.state = Component.ComponentState.HOVER;
                        hoverset = true;
                        component.onEvent(me);
                        break;
                    }
                }
            }
            if (!hoverset) {
                if(this.hover != null) {
                    this.hover.state = Component.ComponentState.IDLE;
                    this.hover = null;
                }
            }
        }
    }

    public boolean requestFocus(Component component) {
        this.focus = component;
        return true;
    }

}
