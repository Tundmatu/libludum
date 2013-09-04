package ee.tmtu.libludum.core.game;

import ee.tmtu.libludum.assets.AssetManager;
import ee.tmtu.libludum.core.logger.Logger;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Mouse;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.IntBuffer;

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

    public Cursor getCursor() {
        return Mouse.getNativeCursor();
    }

    public void setCursor(Cursor cursor) {
        if(Mouse.getNativeCursor() != null && !Mouse.getNativeCursor().equals(cursor)) {
            try {
                Mouse.setNativeCursor(cursor);
            } catch (LWJGLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        try {
            Logger.CORE.log(String.format("Initializing OpenGL ('%s', %s, %s).", this.settings.title, this.settings.width, this.settings.height));
            Display.setDisplayMode(new DisplayMode(this.settings.width, this.settings.height));
            Display.setTitle(this.settings.title);
            Display.setVSyncEnabled(this.settings.vsync);
            Display.create();
            Logger.CORE.log("Initializing OpenAL.");
            AL.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }

        this.init();

        double accumulator = 0.;
        double currentTime = System.currentTimeMillis() / 1000.;
        double frameTime;
        double newTime;

        while (!this.quit && !Display.isCloseRequested()) {
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
        AL.destroy();
        Display.destroy();
        System.exit(0);
    }

}
