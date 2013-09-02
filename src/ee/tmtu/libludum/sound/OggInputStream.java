package ee.tmtu.libludum.sound;

import com.jcraft.jogg.Packet;
import com.jcraft.jogg.Page;
import com.jcraft.jogg.StreamState;
import com.jcraft.jogg.SyncState;
import com.jcraft.jorbis.Block;
import com.jcraft.jorbis.Comment;
import com.jcraft.jorbis.DspState;
import com.jcraft.jorbis.Info;
import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class OggInputStream extends InputStream implements AudioInputStream {
    private byte[] buffer;
    private int bytes = 0;
    private boolean bigEndian = ByteOrder.nativeOrder().equals(ByteOrder.BIG_ENDIAN);
    private boolean endOfBitStream = true;
    private boolean inited = false;
    private int convsize = 16384;
    private byte[] convbuffer = new byte[this.convsize];
    private InputStream input;
    private Info oggInfo = new Info();
    private boolean endOfStream;
    private SyncState syncState = new SyncState();
    private StreamState streamState = new StreamState();
    private Page page = new Page();
    private Packet packet = new Packet();
    private Comment comment = new Comment();
    private DspState dspState = new DspState();
    private Block vorbisBlock = new Block(this.dspState);
    private int readIndex;
    private ByteBuffer pcmBuffer = BufferUtils.createByteBuffer(2048000);
    private int total;

    public OggInputStream(InputStream input) throws IOException {
        this.input = input;
        this.total = input.available();

        init();
    }

    public int getLength() {
        return this.total;
    }

    public int getChannels() {
        return this.oggInfo.channels;
    }

    public int getRate() {
        return this.oggInfo.rate;
    }

    private void init() throws IOException {
        initVorbis();
        readPCM();
    }

    public int available() {
        return this.endOfStream ? 0 : 1;
    }

    private void initVorbis() {
        this.syncState.init();
    }

    private boolean getPageAndPacket() {
        int index = this.syncState.buffer(4096);

        this.buffer = this.syncState.data;
        if (this.buffer == null) {
            this.endOfStream = true;
            return false;
        }
        try {
            this.bytes = this.input.read(this.buffer, index, 4096);
        } catch (Exception e) {
            e.printStackTrace();
            this.endOfStream = true;
            return false;
        }
        this.syncState.wrote(this.bytes);

        if (this.syncState.pageout(this.page) != 1) {
            if (this.bytes < 4096) {
                return false;
            }

            this.endOfStream = true;
            return false;
        }

        this.streamState.init(this.page.serialno());

        this.oggInfo.init();
        this.comment.init();
        if (this.streamState.pagein(this.page) < 0) {
            this.endOfStream = true;
            return false;
        }

        if (this.streamState.packetout(this.packet) != 1) {
            this.endOfStream = true;
            return false;
        }

        if (this.oggInfo.synthesis_headerin(this.comment, this.packet) < 0) {
            this.endOfStream = true;
            return false;
        }

        int i = 0;
        while (i < 2) {
            while (i < 2) {
                int result = this.syncState.pageout(this.page);
                if (result == 0) {
                    break;
                }

                if (result == 1) {
                    this.streamState.pagein(this.page);

                    while (i < 2) {
                        result = this.streamState.packetout(this.packet);
                        if (result == 0)
                            break;
                        if (result == -1) {
                            this.endOfStream = true;
                            return false;
                        }

                        this.oggInfo.synthesis_headerin(this.comment, this.packet);
                        i++;
                    }
                }
            }

            index = this.syncState.buffer(4096);
            this.buffer = this.syncState.data;
            try {
                this.bytes = this.input.read(this.buffer, index, 4096);
            } catch (Exception e) {
                this.endOfStream = true;
                return false;
            }
            if ((this.bytes == 0) && (i < 2)) {
                this.endOfStream = true;
                return false;
            }
            this.syncState.wrote(this.bytes);
        }

        this.convsize = (4096 / this.oggInfo.channels);

        this.dspState.synthesis_init(this.oggInfo);
        this.vorbisBlock.init(this.dspState);

        return true;
    }

    private void readPCM() throws IOException {
        boolean wrote = false;
        while (true) {
            if (this.endOfBitStream) {
                if (!getPageAndPacket()) {
                    break;
                }
                this.endOfBitStream = false;
            }

            if (!this.inited) {
                this.inited = true;
                return;
            }

            float[][][] _pcm = new float[1][][];
            int[] _index = new int[this.oggInfo.channels];

            while (!this.endOfBitStream) {
                while (!this.endOfBitStream) {
                    int result = this.syncState.pageout(this.page);

                    if (result == 0) {
                        break;
                    }
                    if (result != -1) {
                        this.streamState.pagein(this.page);
                        while (true) {
                            result = this.streamState.packetout(this.packet);

                            if (result == 0)
                                break;
                            if (result != -1) {
                                if (this.vorbisBlock.synthesis(this.packet) == 0)
                                    this.dspState.synthesis_blockin(this.vorbisBlock);
                                int samples;
                                while ((samples = this.dspState.synthesis_pcmout(_pcm, _index)) > 0) {
                                    float[][] pcm = _pcm[0];

                                    int bout = samples < this.convsize ? samples : this.convsize;

                                    for (int i = 0; i < this.oggInfo.channels; i++) {
                                        int ptr = i * 2;

                                        int mono = _index[i];
                                        for (int j = 0; j < bout; j++) {
                                            int val = (int) (pcm[i][(mono + j)] * 32767.0D);

                                            if (val > 32767) {
                                                val = 32767;
                                            }
                                            if (val < -32768) {
                                                val = -32768;
                                            }
                                            if (val < 0) {
                                                val |= 32768;
                                            }
                                            if (this.bigEndian) {
                                                this.convbuffer[ptr] = ((byte) (val >>> 8));
                                                this.convbuffer[(ptr + 1)] = ((byte) val);
                                            } else {
                                                this.convbuffer[ptr] = ((byte) val);
                                                this.convbuffer[(ptr + 1)] = ((byte) (val >>> 8));
                                            }
                                            ptr += 2 * this.oggInfo.channels;
                                        }
                                    }

                                    int bytesToWrite = 2 * this.oggInfo.channels * bout;
                                    if (bytesToWrite < this.pcmBuffer.remaining()) {
                                        this.pcmBuffer.put(this.convbuffer, 0, bytesToWrite);
                                    }

                                    wrote = true;
                                    this.dspState.synthesis_read(bout);
                                }
                            }

                        }

                        if (this.page.eos() != 0) {
                            this.endOfBitStream = true;
                        }

                        if ((!this.endOfBitStream) && (wrote)) {
                            return;
                        }
                    }
                }

                if (!this.endOfBitStream) {
                    this.bytes = 0;
                    int index = this.syncState.buffer(4096);
                    if (index >= 0) {
                        this.buffer = this.syncState.data;
                        try {
                            this.bytes = this.input.read(this.buffer, index, 4096);
                        } catch (Exception e) {
                            this.endOfStream = true;
                            return;
                        }
                    } else {
                        this.bytes = 0;
                    }
                    this.syncState.wrote(this.bytes);
                    if (this.bytes == 0) {
                        this.endOfBitStream = true;
                    }

                }

            }

            this.streamState.clear();

            this.vorbisBlock.clear();
            this.dspState.clear();
            this.oggInfo.clear();
        }

        this.syncState.clear();
        this.endOfStream = true;
    }

    public int read() throws IOException {
        if (this.readIndex >= this.pcmBuffer.position()) {
            this.pcmBuffer.clear();
            readPCM();
            this.readIndex = 0;
        }
        if (this.readIndex >= this.pcmBuffer.position()) {
            return -1;
        }

        int value = this.pcmBuffer.get(this.readIndex);
        if (value < 0) {
            value = 256 + value;
        }
        this.readIndex += 1;

        return value;
    }

    public boolean EOS() {
        return (this.endOfStream) && (this.readIndex >= this.pcmBuffer.position());
    }

    public int read(byte[] b, int off, int len) throws IOException {
        for (int i = 0; i < len; i++) {
            try {
                int value = read();
                if (value >= 0) {
                    b[i] = ((byte) value);
                } else {
                    if (i == 0) {
                        return -1;
                    }
                    return i;
                }
            } catch (IOException e) {
                return i;
            }
        }

        return len;
    }

    public int read(byte[] b) throws IOException {
        return read(b, 0, b.length);
    }

    public void close() throws IOException {

    }

}