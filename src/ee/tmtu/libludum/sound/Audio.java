package ee.tmtu.libludum.sound;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.nio.FloatBuffer;

import static org.lwjgl.openal.AL10.*;

public class Audio {

    private static Vector2f position;
    private static Vector3f velocity;
    private static FloatBuffer orientation = BufferUtils.createFloatBuffer(6);

    public static void play(Sound sound) {
        alSourcePlay(sound.getSource());
    }

    public static void pause(Sound sound) {
        alSourcePause(sound.getSource());
    }

    public static void rewind(Sound sound) {
        alSourceRewind(sound.getSource());
    }

    public static void stop(Sound sound) {
        alSourceStop(sound.getSource());
    }

    public static FloatBuffer getOrientation() {
        return orientation;
    }

    public static void setOrientation(float x, float y, float z, float x1, float y1, float z1) {
        Audio.orientation.clear();
        Audio.orientation.put(x).put(y).put(z);
        Audio.orientation.put(x1).put(y1).put(z1);
        Audio.orientation.flip();
        alListener(AL_ORIENTATION, Audio.orientation);
    }

    public static Vector3f getVelocity() {
        return velocity;
    }

    public static void setVelocity(Vector3f velocity) {
        alListener3f(AL_VELOCITY, velocity.x, velocity.y, velocity.z);
        Audio.velocity = velocity;
    }

    public static Vector2f getPosition() {
        return position;
    }

    public static void setPosition(Vector2f position) {
        alListener3f(AL_POSITION, position.x, position.y, 0.f);
        Audio.position = position;
    }

    static {
        Audio.setPosition(new Vector2f(0.f, 0.f));
        Audio.setVelocity(new Vector3f(0.f, 0.f, 0.f));
        Audio.setOrientation(0.0f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f);
    }

}
