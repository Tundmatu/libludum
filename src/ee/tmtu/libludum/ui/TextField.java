package ee.tmtu.libludum.ui;

import ee.tmtu.libludum.assets.AssetManager;
import ee.tmtu.libludum.graphics.Font;
import ee.tmtu.libludum.graphics.Patch9;
import ee.tmtu.libludum.graphics.SpriteBatch;
import ee.tmtu.libludum.graphics.Texture;
import ee.tmtu.libludum.ui.event.Event;
import ee.tmtu.libludum.ui.event.KeyEvent;
import ee.tmtu.libludum.ui.event.KeyListener;
import ee.tmtu.libludum.ui.event.MouseEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.Color;
import org.lwjgl.util.ReadableColor;

import java.util.regex.Pattern;

public class TextField extends Component {

    public static final Pattern allowed = Pattern.compile("[a-zA-z0-9-_ ]");

    public static final Color idleColor = new Color(190, 190, 190);
    public static final Color hoverColor = new Color(220, 180, 140);
    public static final Color clickColor = new Color(225, 225, 225);

    public KeyListener listener;
    public Texture blank;
    public Patch9 idle;
    public Color currentColor;
    public Font font;
    public String str;
    public int cursorPulse;
    public boolean cursor;

    public TextField(Font font) {
        super(new Margin(0), new Padding(0));
        this.font = font;
        this.blank = AssetManager.load("./assets/img/blank.png", Texture.class);
        this.idle = new Patch9(AssetManager.load("./assets/img/click.png", Texture.class), 5);
        this.drawable = this.idle;
        this.currentColor = TextField.clickColor;
        this.str = "";
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof KeyEvent) {
            KeyEvent ke = (KeyEvent) event;
            if (this.listener != null) {
                if (this.listener.onKey(ke)) {
                    return;
                }
            }
            if (ke.state == KeyEvent.KeyState.DOWN) {
                switch (ke.key) {
                    case Keyboard.KEY_BACK:
                        if (this.str.length() > 0) {
                            this.str = this.str.substring(0, this.str.length() - 1);
                        }
                        break;

                    default:
                        if (TextField.allowed.matcher("" + ke.ch).matches()) {
                            this.str += ke.ch;
                        }
                        break;
                }
            }
        }
        if (event instanceof MouseEvent) {
            MouseEvent me = (MouseEvent) event;
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

    @Override
    public void onEnter(MouseEvent event) {
        if (this.state != ComponentState.DOWN) {
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
    }

    @Override
    public void onUp(MouseEvent event) {
        if (this.state == ComponentState.DOWN) {
            this.state = ComponentState.UP;
        }
    }

    @Override
    public void update() {
        super.update();

        this.cursorPulse++;
        if (this.cursorPulse > 10) {
            this.cursor = !this.cursor;
            this.cursorPulse = 0;
        }
    }

    @Override
    public void draw(SpriteBatch batch, double lerp) {
        super.draw(batch, lerp);

        this.font.draw(batch, this.x + this.width / 2, this.y + padding.top - 2, this.str, ReadableColor.WHITE, Font.Orientation.CENTER);
        if (this.cursor) {
            batch.draw(this.blank, this.x + this.width / 2 + this.font.getWidth(this.str) / 2 + 4, this.y + this.height / 2 - this.font.lineheight / 2 - 2, 2, this.font.lineheight);
        }
    }

    @Override
    public void layout() {
        this.width = this.root.width - this.root.padding.left - this.root.padding.right;//this.font.getWidth(this.title) + padding.left + padding.right;
        this.height = this.font.lineheight + padding.top + padding.bottom;
    }

}
