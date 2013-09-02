package ee.tmtu.libludum.ui;

import ee.tmtu.libludum.assets.AssetManager;
import ee.tmtu.libludum.graphics.Font;
import ee.tmtu.libludum.graphics.Patch9;
import ee.tmtu.libludum.graphics.SpriteBatch;
import ee.tmtu.libludum.graphics.Texture;
import ee.tmtu.libludum.ui.event.Event;
import ee.tmtu.libludum.ui.event.MouseEvent;
import ee.tmtu.libludum.ui.event.MouseListener;
import org.lwjgl.util.Color;

public class Button extends Component {

    public static final Color idleColor = new Color(190, 190, 190);
    public static final Color hoverColor = new Color(220, 180, 140);
    public static final Color clickColor = new Color(225, 225, 225);

    public Color currentColor;
    public Patch9 idle, hover, click;
    public MouseListener listener;
    public Font font;
    public String title;

    public Button(String title, Font font) {
        super(new Margin(0), new Padding(0));
        this.idle = new Patch9(AssetManager.load("./assets/img/idle.png", Texture.class), 5);
        this.hover = new Patch9(AssetManager.load("./assets/img/hover.png", Texture.class), 5);
        this.click = new Patch9(AssetManager.load("./assets/img/click.png", Texture.class), 5);
        this.drawable = idle;
        this.title = title;
        this.font = font;
        this.currentColor = Button.idleColor;
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof MouseEvent) {
            MouseEvent me = (MouseEvent) event;
            if (listener != null) {
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
        if (this.state != ComponentState.DOWN) {
            this.drawable = this.hover;
            this.currentColor = Button.hoverColor;
            this.state = ComponentState.HOVER;
        }
    }

    @Override
    public void onLeave(MouseEvent event) {
        this.drawable = this.idle;
        this.state = ComponentState.IDLE;
        this.currentColor = Button.idleColor;
    }

    @Override
    public void onDown(MouseEvent event) {
        this.state = ComponentState.DOWN;
        this.drawable = this.click;
        this.currentColor = Button.clickColor;
    }

    @Override
    public void onUp(MouseEvent event) {
        if (this.state == ComponentState.DOWN) {
            this.state = ComponentState.UP;
            if (this.listener != null) {
                this.listener.onMouseEvent(event);
            }
            this.drawable = this.hover;
            this.currentColor = Button.hoverColor;
        }
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void draw(SpriteBatch batch, double lerp) {
        super.draw(batch, lerp);

        this.font.draw(batch, this.x + this.width / 2 + (this.state == ComponentState.DOWN ? 1 : 0), this.y + padding.top - 2 + (this.state == ComponentState.DOWN ? 1 : 0), this.title, this.currentColor, Font.Orientation.CENTER);
    }

    @Override
    public void layout() {
        if (this.root != null) {
            this.width = this.root.width - this.root.padding.left - this.root.padding.right;//this.font.getWidth(this.title) + padding.left + padding.right;
        } else {
            this.width = this.font.getWidth(this.title) + padding.left + padding.right;
        }
        this.height = this.font.lineheight + padding.top + padding.bottom;
    }

}
