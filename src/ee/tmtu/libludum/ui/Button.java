package ee.tmtu.libludum.ui;

import ee.tmtu.libludum.assets.AssetManager;
import ee.tmtu.libludum.graphics.Font;
import ee.tmtu.libludum.graphics.Patch9;
import ee.tmtu.libludum.graphics.SpriteBatch;
import ee.tmtu.libludum.graphics.Texture;
import ee.tmtu.libludum.ui.event.Event;
import ee.tmtu.libludum.ui.event.MouseEvent;
import ee.tmtu.libludum.ui.event.MouseListener;

public class Button extends Component {

    public Patch9 idle, hover, click;
    public MouseListener listener;
    public Font font;
    public String title;

    public Button(String title, Font font) {
        super(new Margin(0), new Padding(0));
        this.idle = new Patch9(AssetManager.load("./assets/idle.png", Texture.class), 5);
        this.hover = new Patch9(AssetManager.load("./assets/hover.png", Texture.class), 5);
        this.click = new Patch9(AssetManager.load("./assets/click.png", Texture.class), 5);
        this.drawable = idle;
        this.title = title;
        this.font = font;
    }

    @Override
    public void onEvent(Event event) {
        if(event instanceof MouseEvent) {
            MouseEvent me = (MouseEvent)event;
            if(listener != null) {
                switch (me.state) {
                    case DOWN:
                        this.onDown(me);
                        break;
                    case UP:
                        this.onUp(me);
                        break;
                    case MOVE:
                        break;
                }
            }
        }
    }

    @Override
    public void onEnter(MouseEvent event) {
        if(this.state != ComponentState.DOWN) {
            this.drawable = this.hover;
            this.state = ComponentState.HOVER;
        }
    }

    @Override
    public void onLeave(MouseEvent event) {
        this.drawable = this.idle;
        this.state = ComponentState.IDLE;
    }

    @Override
    public void onDown(MouseEvent event) {
        this.state = ComponentState.DOWN;
        this.drawable = this.click;
    }

    @Override
    public void onUp(MouseEvent event) {
        if(this.state == ComponentState.DOWN) {
            this.state = ComponentState.UP;
            if(this.listener != null) {
                this.listener.onMouseEvent(event);
            }
            this.drawable = this.hover;
        }
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void draw(SpriteBatch batch, double lerp) {
        super.draw(batch, lerp);

        this.font.draw(batch, this.x + this.width / 2 + (this.state == ComponentState.DOWN ? 1 : 0), this.y + padding.top - 2 + (this.state == ComponentState.DOWN ? 1 : 0), this.title, Font.Orientation.CENTER);
    }

    @Override
    public void layout() {
        this.width = this.font.getWidth(this.title) + padding.left + padding.right;
        this.height = this.font.lineheight + padding.top + padding.bottom;
    }

}
