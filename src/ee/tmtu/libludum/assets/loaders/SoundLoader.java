package ee.tmtu.libludum.assets.loaders;

import ee.tmtu.libludum.assets.AssetLoader;
import ee.tmtu.libludum.sound.Sound;
import org.lwjgl.util.WaveData;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.io.File;
import java.io.IOException;

import static org.lwjgl.openal.AL10.*;

public class SoundLoader implements AssetLoader<Sound> {

    @Override
    public Sound load(File res) throws IOException {
        WaveData wav = WaveData.create(res.toURI().toURL());
        int buffer = alGenBuffers();
        int source = alGenSources();
        alBufferData(buffer, wav.format, wav.data, wav.samplerate);
        wav.dispose();

        Sound sound = new Sound();
        sound.setSource(source);
        sound.setBuffer(buffer);
        sound.setPosition(new Vector2f(0.f, 0.f));
        sound.setVelocity(new Vector3f(0.f, 0.f, 0.f));
        sound.setGain(1.f);
        sound.setPitch(1.f);

        System.out.println(alGetError());
        return sound;
    }

}
