package ee.tmtu.libludum.ui;

import ee.tmtu.libludum.ui.event.Event;
import ee.tmtu.libludum.ui.event.KeyEvent;
import ee.tmtu.libludum.ui.event.MouseEvent;

import java.util.Iterator;
import java.util.LinkedList;

public class Root {

    private LinkedList<Component> components;
    private Component hover;
    private Component focus;
    private Margin margin;
    private Padding padding;

    public Root(Margin margin, Padding padding) {
        this.margin = margin;
        this.padding = padding;
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
                this.hover.state = Component.ComponentState.IDLE;
                this.hover = null;
            }
        }
    }

    public boolean requestFocus(Component component) {
        this.focus = component;
        return true;
    }

    public void layout() {
        Iterator<Component> iterator = this.components.iterator();
        Component component;
        int yHeight = 0;
        while (iterator.hasNext()) {
            component = iterator.next();
            component.x = margin.left + padding.left;
            component.y = margin.top + padding.top + yHeight;
            yHeight += component.y + component.margin.top + component.margin.bottom;
        }
    }

}
