package ee.tmtu.libludum.assets.loaders;

import ee.tmtu.libludum.assets.AssetLoader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class LineLoader implements AssetLoader<String[]> {

    @Override
    public String[] load(File res) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(res));
        String content = "";
        String buf;
        while ((buf = br.readLine()) != null) {
            content += buf + "\n";
        }
        return content.split("\n");
    }

}
