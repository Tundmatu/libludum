package ee.tmtu.libludum.core;

import java.util.*;

public class CallbackHelper {

    private static final Object MUTEX_LOCK = new Object();

    private static Map<String, Callback> callbacks = new HashMap<>();
    private static List<CallbackTimer> timers = new LinkedList<>();
    private static Thread updateThread;
    private static boolean dead;

    public static Callback get(String id) {
        return CallbackHelper.callbacks.get(id);
    }

    public static void call(Callback callback) {
        callback.onCall();
    }

    public static void call(String id) {
        CallbackHelper.callbacks.get(id).onCall();
    }

    public static void call(Callback callback, float delay) {
        CallbackHelper.timers.add(new CallbackTimer(delay, callback));
    }

    public static void call(String id, float delay) {
        CallbackHelper.timers.add(new CallbackTimer(delay, CallbackHelper.callbacks.get(id)));
    }

    public static void register(String id, Callback callback) {
        CallbackHelper.callbacks.put(id, callback);
    }

    public static void pause() {
        synchronized (CallbackHelper.MUTEX_LOCK) {
            try {
                CallbackHelper.MUTEX_LOCK.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static synchronized void resume() {
        synchronized (CallbackHelper.MUTEX_LOCK) {
            CallbackHelper.MUTEX_LOCK.notify();
        }
    }

    public static void kill() {
        CallbackHelper.dead = true;
    }

    public static void start() {
        if(CallbackHelper.updateThread != null && CallbackHelper.updateThread.isAlive()) {
            try {
                CallbackHelper.updateThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        CallbackHelper.dead = false;
        CallbackHelper.updateThread = new Thread(new Runnable() {
            @Override
            public void run() {
                double accumulator = 0.;
                double currentTime = System.nanoTime() / 1000000000.;
                double frameTime;
                double newTime;
                double dt = 1. / 20.;

                while (!CallbackHelper.dead) {
                    synchronized (CallbackHelper.MUTEX_LOCK) {
                        newTime = System.nanoTime() / 1000000000.;
                        frameTime = newTime - currentTime;
                        currentTime = newTime;
                        accumulator += frameTime;
                        while (accumulator >= dt) {
                            System.out.println(accumulator);
                            CallbackHelper.update((float) dt);
                            accumulator -= dt;
                        }
                    }
                }
            }
        });
        CallbackHelper.updateThread.start();
    }

    private static void update(float delta) {
        Iterator<CallbackTimer> iterator = CallbackHelper.timers.iterator();
        while (iterator.hasNext()) {
            CallbackTimer ct = iterator.next();
            ct.time -= delta;
            if (ct.time <= 0.f) {
                ct.callback.onCall();
                iterator.remove();
            }
        }
    }

    static {
        CallbackHelper.start();
    }

}
