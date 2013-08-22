package ee.tmtu.libludum.ui.event;

public class MouseEvent extends Event {

    public MouseState state;
    public int btn, x, y, dx, dy;

    public enum MouseState {
        DOWN, UP, MOVE, NULL
    }

}
