package ee.tmtu.libludum.ui;

import ee.tmtu.libludum.graphics.Font;
import ee.tmtu.libludum.graphics.SpriteBatch;
import ee.tmtu.libludum.ui.event.Event;
import ee.tmtu.libludum.ui.event.MouseEvent;
import ee.tmtu.libludum.ui.event.MouseListener;

public class Button extends Component {

    public MouseListener listener;
    public Font font;
    public String title;

    public Button(String title, Font font) {
        super(new Margin(0), new Padding(0));
        this.title = title;
        this.font = font;
    }

    @Override
    public void onEvent(Event event) {
        if(event instanceof MouseEvent) {
            MouseEvent me = (MouseEvent)event;
            if(listener != null) {
                listener.onMouseEvent(me);
            }
        }
        System.out.println(event);
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void draw(SpriteBatch batch, double lerp) {
        super.draw(batch, lerp);

        this.font.draw(batch, this.x, this.y, this.title);
    }

    @Override
    public void layout() {
        this.width = this.font.getWidth(this.title) + padding.left + padding.right;
        this.height = this.font.lineheight + padding.top + padding.bottom;
    }

}
