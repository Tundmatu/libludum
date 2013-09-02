package ee.tmtu.libludum.assets.loaders;

import de.matthiasmann.twl.utils.PNGDecoder;
import ee.tmtu.libludum.assets.AssetLoader;
import ee.tmtu.libludum.graphics.Texture;
import org.lwjgl.BufferUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;

public class PngLoader implements AssetLoader<Texture> {

    @Override
    public Texture load(File res) throws IOException {
        PNGDecoder decoder = new PNGDecoder(new FileInputStream(res));
        ByteBuffer buffer = BufferUtils.createByteBuffer(decoder.getWidth() * decoder.getHeight() * 4);
        decoder.decode(buffer, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);
        buffer.flip();
        Texture texture = new Texture(GL_TEXTURE_2D, decoder.getWidth(), decoder.getHeight(), buffer);
        texture.filter(GL_NEAREST);
        texture.wrap(GL_CLAMP);
        return texture;
    }

}
