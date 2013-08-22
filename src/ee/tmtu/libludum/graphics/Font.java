package ee.tmtu.libludum.graphics;

import org.lwjgl.util.Color;
import org.lwjgl.util.ReadableColor;
import org.lwjgl.util.vector.Vector4f;

public class Font {

	public Texture[] pages;
	public Glyph[] glyphs;
	public int lineheight;
	public int base;

	public void draw(SpriteBatch batch, float x, float y, String text) {
		this.draw(batch, x, y, text, Orientation.LEFT);
	}

	public void draw(SpriteBatch batch, float x, float y, String text, Orientation orientation) {
		draw(batch, x, y, text, Color.WHITE, orientation);
	}

	public void draw(SpriteBatch batch, float x, float y, String text, ReadableColor color, Orientation orientation) {
		int textWidth = getWidth(text);
		switch (orientation) {
			case LEFT:
				draw(batch, x, y, text, color);
				break;
			case CENTER:
				draw(batch, x - (textWidth / 2), y, text, color);
				break;
			case RIGHT:
				draw(batch, x - textWidth, y, text, color);
				break;
		}
	}

	public void draw(SpriteBatch batch, float x, float y, String text, ReadableColor color) {
		batch.setColor(color);
		float lx = x;
		for(String s : text.split("\n")) {
			for(int i = 0; i < s.length(); i++) {
				char c = s.charAt(i);
				Glyph g = glyphs[c];
				if(g != null) {
					Texture tex = pages[g.page];
					Vector4f coords = g.getTexCoords(tex);
					batch.draw(tex, lx + g.xoff, y + g.yoff, g.width, g.height, coords.x, coords.y, coords.z, coords.w);
					lx += g.xadv;
				}
			}
			lx = x;
			y += lineheight;
		}
		batch.setColor(Color.WHITE);
	}

	public int getWidth(String text) {
		int x = 0;
		for(int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			Glyph g = glyphs[c];
			if(g != null) {
				x += g.xadv;
			}
		}
		return x;
	}

	public static enum Orientation {
		LEFT, CENTER, RIGHT
	}

}
