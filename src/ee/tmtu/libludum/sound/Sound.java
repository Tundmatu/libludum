package ee.tmtu.libludum.sound;

import ee.tmtu.libludum.core.Disposable;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import static org.lwjgl.openal.AL10.*;

public class Sound implements Disposable {

    private int buffer;
    private int source;
    private Vector2f position;
    private Vector3f velocity;
    private float pitch;
    private float gain;
    private boolean looping;

    public int getBuffer() {
        return buffer;
    }

    public void setBuffer(int buffer) {
        alSourcei(this.source, AL_BUFFER, this.buffer = buffer);
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(Vector2f position) {
        alSource3f(this.source, AL_POSITION, position.x, position.y, 0);
        this.position = position;
    }

    public Vector3f getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector3f velocity) {
        alSource3f(this.source, AL_VELOCITY, velocity.x, velocity.y, velocity.z);
        this.velocity = velocity;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        alSourcef(this.source, AL_PITCH, this.pitch = pitch);
    }

    public float getGain() {
        return gain;
    }

    public void setGain(float gain) {
        alSourcef(this.source, AL_GAIN, this.gain = gain);
    }

    public boolean isLooping() {
        return looping;
    }

    public void setLooping(boolean looping) {
        alSourcei(this.source, AL_LOOPING, looping ? AL_TRUE : AL_FALSE);
        this.looping = looping;
    }

    public int getState() {
        return alGetInteger(AL_SOURCE_STATE);
    }

    @Override
    public void dispose() {
        alDeleteBuffers(this.buffer);
        alDeleteSources(this.source);
    }

    public static Sound fromBuffer(Sound sound) {
        Sound snd = new Sound();
        snd.setSource(alGenSources());
        snd.setBuffer(sound.buffer);
        snd.setGain(sound.gain);
        snd.setPitch(sound.pitch);
        snd.setVelocity(sound.velocity);
        snd.setPosition(sound.position);
        snd.setLooping(sound.looping);
        return snd;
    }

}
