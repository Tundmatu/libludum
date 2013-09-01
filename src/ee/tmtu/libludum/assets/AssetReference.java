package ee.tmtu.libludum.assets;

import java.util.HashMap;
import java.util.Map;

public class AssetReference {

    public Map<String, AssetLoader<?>> extensions;
    public AssetLoader<?> loader;
    public boolean multi;

    public AssetReference() {
        this.multi = true;
        this.extensions = new HashMap<>();
    }

    public AssetReference(AssetLoader<?> loader) {
        this.loader = loader;
        this.multi = false;
    }

    public AssetReference extension(String ext, AssetLoader<?> loader) {
        if (!this.multi) return null;
        this.extensions.put(ext.startsWith(".") ? ext.toLowerCase() : "." + ext.toLowerCase(), loader);
        return this;
    }
}
