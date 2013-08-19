package ee.tmtu.libludum.core;

import ee.tmtu.libludum.assets.AssetManager;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class GameSettings {

    public int width, height;
    public String title;
    public boolean vsync;

    public static GameSettings from(String res) {
        try {
            return AssetManager.gson.fromJson(new FileReader(res), GameSettings.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
