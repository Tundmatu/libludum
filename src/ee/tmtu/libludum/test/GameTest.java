package ee.tmtu.libludum.test;

import ee.tmtu.libludum.core.Game;
import ee.tmtu.libludum.core.GameSettings;

public class GameTest extends Game {

    public GameTest(GameSettings settings) {
        super(settings);
    }

    @Override
    public void init() {

    }

    @Override
    public void update() {

    }

    @Override
    public void draw(double lerp) {

    }

    public static void main(String[] args) {
        GameTest game = new GameTest(GameSettings.from("assets/settings.cfg"));
        new Thread(game).start();
    }

}
