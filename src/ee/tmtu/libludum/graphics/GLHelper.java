package ee.tmtu.libludum.graphics;

import static org.lwjgl.opengl.GL11.*;

public class GLHelper {

    public static int sizeof(int type) {
        switch (type) {
            case GL_DOUBLE:
                return 8;
            case GL_FLOAT:
                return 4;
            case GL_SHORT:
                return 2;

            default:
                return 0;
        }
    }

}
