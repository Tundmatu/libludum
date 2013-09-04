package ee.tmtu.libludum.sound;

import ee.tmtu.libludum.core.Disposable;
import ee.tmtu.libludum.core.logger.Logger;

import java.io.*;
import java.nio.ByteBuffer;

import static org.lwjgl.openal.AL10.AL_FORMAT_MONO16;
import static org.lwjgl.openal.AL10.AL_FORMAT_STEREO16;

public class OggData implements Disposable {

    public ByteBuffer data;
    public int format;
    public int samplerate;

    public static OggData create(String res) throws IOException {
        return OggData.create(new File(res));
    }

    public static OggData create(File file) throws IOException {
        return OggData.create(new FileInputStream(file));
    }

    public static OggData create(InputStream is) throws IOException {
        OggData ogg = new OggData();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        OggInputStream ois = new OggInputStream(is);

        while (!ois.EOS()) {
            bos.write(ois.read());
        }

        ogg.format = ois.getChannels() > 1 ? AL_FORMAT_STEREO16 : AL_FORMAT_MONO16;
        ogg.samplerate = ois.getRate();

        byte[] barr = bos.toByteArray();
        ogg.data = ByteBuffer.allocateDirect(barr.length);
        ogg.data.put(barr);
        ogg.data.rewind();
        return ogg;
    }

    @Override
    public void dispose() {
        Logger.SND.log(String.format("Disposing OggData (%s).", this.data.limit()));
        this.data.clear();
    }

}
