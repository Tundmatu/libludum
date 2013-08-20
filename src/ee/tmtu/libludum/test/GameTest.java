package ee.tmtu.libludum.test;

import ee.tmtu.libludum.assets.AssetManager;
import ee.tmtu.libludum.core.Game;
import ee.tmtu.libludum.core.GameSettings;
import ee.tmtu.libludum.sound.Audio;
import ee.tmtu.libludum.sound.Sound;

import static org.lwjgl.opengl.GL11.*;

public class GameTest extends Game {

    public GameTest(GameSettings settings) {
        super(settings);
    }

    @Override
    public void init() {
        System.out.println("init");
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0.f, this.settings.width, this.settings.height, 0.f, 0.f, 1.f);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        sound = AssetManager.load("Gun1.wav", Sound.class);
        sound.setLooping(true);
        Audio.play(sound);
    }
    Sound sound;

    int i = 0;
    @Override
    public void update() {
        i++;
        if(i > 75) {
            Audio.stop(sound);
        }
    }

    @Override
    public void draw(double lerp) {
        glClear(GL_COLOR_BUFFER_BIT);
        glClearColor(0.f, 0.f, 0.f, 1.f);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        glTranslatef(-100, -100, 0);
        glColor3f(1.f, 1.f, 1.f);
        glRectf(100, 100, 200, 200);
    }

    public static void main(String[] args) {
        GameTest game = new GameTest(GameSettings.from("assets/settings.cfg"));
        new Thread(game).start();
    }

}
