package ee.tmtu.libludum.sound;

import org.lwjgl.util.WaveData;

import java.io.*;
import java.nio.ByteBuffer;

import static org.lwjgl.openal.AL10.*;

public class OggData {

    public ByteBuffer data;
    public int format;
    public int samplerate;

    public OggData(String res) throws IOException {
        this(new File(res));
    }

    public OggData(File file) throws IOException {
        this(new FileInputStream(file));
    }

    public OggData(InputStream is) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        OggInputStream ois = new OggInputStream(is);

        while (!ois.atEnd()) {
            bos.write(ois.read());
        }

        this.format = ois.getChannels() > 1 ? AL_FORMAT_STEREO16 : AL_FORMAT_MONO16;
        this.samplerate = ois.getRate();

        byte[] barr = bos.toByteArray();
        this.data = ByteBuffer.allocateDirect(barr.length);
        this.data.put(barr);
        this.data.rewind();
    }

    public void dispose() {
        this.data.clear();
    }

}
