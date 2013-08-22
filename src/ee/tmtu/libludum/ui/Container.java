package ee.tmtu.libludum.ui;

import ee.tmtu.libludum.graphics.SpriteBatch;

import java.util.Iterator;
import java.util.LinkedList;

public class Container extends Component {

    public LinkedList<Component> components;

    public Container(Margin margin, Padding padding) {
        super(margin, padding);
        this.components = new LinkedList<>();
    }

    @Override
    public void layout() {
        Iterator<Component> iterator = this.components.iterator();
        Component component;
        int yHeight = 0;
        while (iterator.hasNext()) {
            component = iterator.next();
            component.layout();
            component.x = x + margin.left + padding.left;
            component.y = y + margin.top + padding.top + yHeight;
            yHeight += component.y + component.margin.top + component.margin.bottom;
        }
    }

    @Override
    public void update() {
        for(Component component : this.components) {
            component.update();
        }
    }

    @Override
    public void draw(SpriteBatch batch, double lerp) {
        for(Component component : this.components) {
            component.draw(batch, lerp);
        }
    }

}
