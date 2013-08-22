package ee.tmtu.libludum.graphics;

import org.lwjgl.util.vector.Vector4f;

public class Glyph {

	public int x, y, width, height, xoff, yoff, xadv, id, page;

	public Vector4f getTexCoords(Texture tex) {
		return new Vector4f((float)x / (float)tex.width, (float)y / (float)tex.height, (float)(x+width) / (float)tex.width, (float)(y+height) / (float)tex.height);
	}

}
