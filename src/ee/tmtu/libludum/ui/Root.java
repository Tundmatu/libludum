package ee.tmtu.libludum.ui;

import ee.tmtu.libludum.ui.event.Event;
import ee.tmtu.libludum.ui.event.KeyEvent;
import ee.tmtu.libludum.ui.event.MouseEvent;

import java.util.Iterator;

public class Root extends Container {

    private Component hover;
    private Component focus;

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
                if (component.isInside(me.x, me.y)) {
                    if(this.hover != component) {
                        if(this.hover != null) this.hover.onLeave(me);
                        component.onEnter(me);
                    }
                    this.hover = component;
                    component.onMove(me);
                    hoverset = true;
                    component.onEvent(me);
                    break;
                }
            }
            if (!hoverset) {
                if(this.hover != null) {
                    this.hover.onLeave(me);
                    //this.hover.state = Component.ComponentState.IDLE;
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
