package ee.tmtu.libludum.assets;

import com.google.gson.Gson;
import ee.tmtu.libludum.assets.loaders.*;
import ee.tmtu.libludum.core.Logger;
import ee.tmtu.libludum.graphics.Font;
import ee.tmtu.libludum.graphics.Texture;
import ee.tmtu.libludum.sound.Sound;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AssetManager {

    private static final Map<Class<?>, AssetReference> assetLoaders = new HashMap<>();
    private static final Map<String, Object> loadedCache = new HashMap<>();

    public static final Gson gson = new Gson();

    public static <T> T load(String res, Class<T> type) {
        return load(res, type, assetLoaders);
    }

    public static <T> T load(String res, Class<T> type, Map<Class<?>, AssetReference> loaders) {
        File file = new File(res);
        if (loadedCache.containsKey(file.getPath())) {
            Logger.ASSET.log(String.format("Returned cached version of %s.", res));
            return (T) loadedCache.get(file.getPath());
        }
        AssetReference ar = loaders.get(type);
        String ext = res.substring(res.lastIndexOf('.', res.length() - 1));
        AssetLoader al = ar.multi ? ar.extensions.get(ext.toLowerCase()) : ar.loader;
        T ret = null;
        try {
            Logger.ASSET.log(String.format("Caching asset of type %s with path %s.", type.getSimpleName(), res));
            ret = (T) al.load(file);
            loadedCache.put(file.getPath(), ret);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static void register(Class<?> c, AssetReference ar) {
        Logger.ASSET.log(String.format("Registering loader(s) for %s.", c.getSimpleName()));
        assetLoaders.put(c, ar);
    }

    static {
        AssetManager.register(Texture.class, new AssetReference().extension("png", new PngLoader()));
        AssetManager.register(Font.class, new AssetReference(new FontLoader()));
        AssetManager.register(Sound.class, new AssetReference().extension("wav", new WavLoader()).extension("ogg", new OggLoader()));
        AssetManager.register(String[].class, new AssetReference(new LineLoader()));
    }

}
