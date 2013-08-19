package ee.tmtu.libludum.core;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public abstract class Game implements Runnable {

    private static final int TICKS_PER_SECOND = 20;
    private static final double DT = 1. / TICKS_PER_SECOND;

    private boolean quit;

    public GameSettings settings;

    public Game(GameSettings settings) {
        this.settings = settings;
    }

    public abstract void init();

    public abstract void update();

    public abstract void draw(double lerp);

    public void shutdown() {
        this.quit = true;
    }

    @Override
    public void run() {
        try {
            Display.setDisplayMode(new DisplayMode(this.settings.width, this.settings.height));
            Display.setTitle(this.settings.title);
            Display.setVSyncEnabled(this.settings.vsync);
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }

        this.init();

        double accumulator = 0.;
        double currentTime = System.currentTimeMillis() / 1000.;
        double frameTime;
        double newTime;

        while(!this.quit && !Display.isCloseRequested()) {
            newTime = System.currentTimeMillis() / 1000.;
            frameTime = newTime - currentTime;
            currentTime = newTime;
            accumulator += frameTime;
            while (accumulator >= Game.DT) {
                this.update();

                accumulator -= Game.DT;
            }
            this.draw(accumulator / Game.DT);
            Display.update();
        }
        Display.destroy();
        System.exit(0);
    }

}
