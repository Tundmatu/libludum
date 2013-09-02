package ee.tmtu.libludum.ui;

import ee.tmtu.libludum.assets.AssetManager;
import ee.tmtu.libludum.graphics.Patch9;
import ee.tmtu.libludum.graphics.SpriteBatch;
import ee.tmtu.libludum.graphics.Texture;

import java.util.Iterator;
import java.util.LinkedList;

public class Container extends Component {

    public LinkedList<Component> components;
    public Patch9 background;

    public Container(Margin margin, Padding padding) {
        super(margin, padding);
        this.components = new LinkedList<>();
        this.background = new Patch9(AssetManager.load("./assets/img/idle.png", Texture.class), 5);
        this.drawable = this.background;
    }

    @Override
    public void layout() {
        Iterator<Component> iterator = this.components.iterator();
        Component component;
        int yHeight = 0;
        while (iterator.hasNext()) {
            component = iterator.next();
            component.layout();
            component.x = x + padding.left;
            component.y = y + padding.top + yHeight;
            yHeight += component.height;
            if (yHeight > this.height) this.height = yHeight + padding.bottom + padding.top;
        }
    }

    @Override
    public void update() {
        for (Component component : this.components) {
            component.update();
        }
    }

    @Override
    public void draw(SpriteBatch batch, double lerp) {
        if (!this.invisible) super.draw(batch, lerp);
        for (Component component : this.components) {
            component.draw(batch, lerp);
        }
    }

}
