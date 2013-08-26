package ee.tmtu.libludum.core;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenAccessor;
import org.lwjgl.util.Color;

public class ColorAccessor implements TweenAccessor<Color> {

    public static final int RGBA = 0;
    public static final int R = 1;
    public static final int G = 2;
    public static final int B = 3;
    public static final int A = 4;

    @Override
    public int getValues(Color color, int type, float[] floats) {
        switch (type) {
            case RGBA:
                floats[0] = (float)color.getRed() / 255.f;
                floats[1] = (float)color.getGreen() / 255.f;
                floats[2] = (float)color.getBlue() / 255.f;
                floats[3] = (float)color.getAlpha() / 255.f;
                return 4;
            case R:
                floats[0] = (float)color.getRed() / 255.f;
                return 1;
            case G:
                floats[0] = (float)color.getGreen() / 255.f;
                return 1;
            case B:
                floats[0] = (float)color.getBlue() / 255.f;
                return 1;
            case A:
                floats[0] = (float)color.getAlpha() / 255.f;
                return 1;
            default:
                return 0;
        }
    }

    @Override
    public void setValues(Color color, int type, float[] floats) {
        switch (type) {
            case RGBA:
                color.set((int)(floats[0]*255.f), (int)(floats[1]*255.f), (int)(floats[2]*255.f), (int)(floats[3]*255.f));
                break;
            case R:
                color.setRed((int)(floats[0]*255.f));
                break;
            case G:
                color.setGreen((int)(floats[0]*255.f));
                break;
            case B:
                color.setBlue((int)(floats[0]*255.f));
                break;
            case A:
                color.setAlpha((int)(floats[0]*255.f));
                break;
        }
    }

}
