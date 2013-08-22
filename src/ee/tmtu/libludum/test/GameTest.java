package ee.tmtu.libludum.test;

import ee.tmtu.libludum.assets.AssetManager;
import ee.tmtu.libludum.core.Game;
import ee.tmtu.libludum.core.GameSettings;
import ee.tmtu.libludum.graphics.Font;
import ee.tmtu.libludum.graphics.SpriteBatch;
import ee.tmtu.libludum.sound.Audio;
import ee.tmtu.libludum.sound.Sound;
import ee.tmtu.libludum.ui.Button;
import ee.tmtu.libludum.ui.Margin;
import ee.tmtu.libludum.ui.Padding;
import ee.tmtu.libludum.ui.Root;
import ee.tmtu.libludum.ui.event.MouseEvent;
import ee.tmtu.libludum.ui.event.MouseListener;
import org.lwjgl.input.Mouse;

import static org.lwjgl.opengl.GL11.*;

public class GameTest extends Game {

    public SpriteBatch batch;
    public Sound sound;
    public Root root;
    public Font font;

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
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        batch = new SpriteBatch(250);

        font = AssetManager.load("./assets/fairfax.fnt", Font.class);

        Button button = new Button("Play sound!", font);
        button.listener = new MouseListener() {
            @Override
            public void onMouseEvent(MouseEvent event) {
                Audio.play(sound);
            }
        };

        root = new Root(new Margin(15), new Padding(0));
        root.add(button);
        root.layout();

        sound = AssetManager.load("Gun1.wav", Sound.class);
    }

    @Override
    public void update() {
        MouseEvent me = new MouseEvent();
        while(Mouse.next()) {
            me.x = Mouse.getX();
            me.y = Mouse.getY();
            me.dx = Mouse.getDX();
            me.dy = Mouse.getDY();
            if(Mouse.getEventButtonState()) {
                me.state = MouseEvent.MouseState.DOWN;
            } else {
                me.state = MouseEvent.MouseState.UP;
            }
            root.fire(me);
        }
        if(me.dx != 0 || me.dy != 0) {
            me.state = MouseEvent.MouseState.MOVE;
            root.fire(me);
        }
    }

    @Override
    public void draw(double lerp) {
        glClear(GL_COLOR_BUFFER_BIT);
        glClearColor(0.f, 0.f, 0.f, 1.f);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        /*glTranslatef(-100, -100, 0);
        glColor3f(1.f, 1.f, 1.f);
        glRectf(100, 100, 200, 200);*/
        batch.start();
        root.draw(batch, lerp);
        batch.end();
    }

    public static void main(String[] args) {
        GameTest game = new GameTest(GameSettings.from("assets/settings.cfg"));
        new Thread(game).start();
    }

}
