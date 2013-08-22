package ee.tmtu.libludum.ui.event;

public class KeyEvent extends Event {

    public boolean shift, alt, meta;
    public KeyState state;
    public int key;

    public enum KeyState {
        DOWN, UP
    }

}
