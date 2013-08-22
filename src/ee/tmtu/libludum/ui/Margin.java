package ee.tmtu.libludum.ui;

public class Margin {

    public int top, left, right, bottom;

    public Margin(int padding) {
        this(padding, padding, padding, padding);
    }

    public Margin(int top_bottom, int left_right) {
        this(top_bottom, left_right, left_right, top_bottom);
    }

    public Margin(int top, int left, int right, int bottom) {
        this.top = top;
        this.left = left;
        this.right = right;
        this.bottom = bottom;
    }

}
