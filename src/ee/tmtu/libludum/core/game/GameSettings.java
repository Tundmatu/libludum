package ee.tmtu.libludum.core.game;

import ee.tmtu.libludum.assets.AssetManager;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GameSettings {

    public int width, height;
    public String title;
    public boolean vsync, mute;
    public float volume;

    public void save(String res) {
        try {
            FileWriter fw = new FileWriter(res);
            fw.write(AssetManager.gson.toJson(this));
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GameSettings from(String res) {
        try {
            return AssetManager.gson.fromJson(new FileReader(res), GameSettings.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
