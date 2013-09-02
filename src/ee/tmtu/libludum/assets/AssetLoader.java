package ee.tmtu.libludum.assets;

import java.io.File;
import java.io.IOException;

public interface AssetLoader<T> {

    public T load(File res) throws IOException;

}
