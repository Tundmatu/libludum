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


    public void draw(SpriteBatch batch, float x, float y, float rotation, String text) {
        this.draw(batch, x, y, rotation, text, Color.WHITE);
    }

	public void draw(SpriteBatch batch, float x, float y, String text, Orientation orientation) {
		draw(batch, x, y, text, Color.WHITE, orientation);
	}

	public void draw(SpriteBatch batch, float x, float y, String text, ReadableColor color, Orientation orientation) {
		int textWidth = getWidth(text);
		switch (orientation) {
			case LEFT:
				draw(batch, x, y, 0, text, color);
				break;
			case CENTER:
				draw(batch, x - (textWidth / 2), y, 0, text, color);
				break;
			case RIGHT:
				draw(batch, x - textWidth, y, 0, text, color);
				break;
		}
	}

	public void draw(SpriteBatch batch, float x, float y, float rotation, String text, ReadableColor color) {
		batch.setColor(color);
		float lx = x;
        float xCenter = this.getWidth(text);
        float yCenter = this.lineheight / 2;
		for(String s : text.split("\n")) {
			for(int i = 0; i < s.length(); i++) {
				char c = s.charAt(i);
                if(c == '\247') {
                    batch.setColor(new Color(251, 208, 141));
                    continue;
                }
				Glyph g = glyphs[c];
				if(g != null) {
					Texture tex = pages[g.page];
					Vector4f coords = g.getTexCoords(tex);
					batch.draw(tex, lx + g.xoff, y + g.yoff, g.width, g.height, x-lx + xCenter/2, 0, rotation, coords.x, coords.y, coords.z, coords.w);
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
            if(c == '\247') continue;
            Glyph g = glyphs[c];
            if(g != null) {
                x += g.xadv;
            }
        }
        return x;
    }

    public int getHeight(String text) {
        int height = 0;
        for(int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            Glyph g = glyphs[c];
            if(g != null) {
                height = Math.max(height, g.height);
            }
        }
        return height;
    }

	public static enum Orientation {
		LEFT, CENTER, RIGHT
	}

}
