package ee.tmtu.libludum.core.callback;

public class CallbackTimer {

    public float time;
    public Callback callback;

    public CallbackTimer(float time, Callback callback) {
        this.time = time;
        this.callback = callback;
    }

}
