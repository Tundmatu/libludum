package ee.tmtu.libludum.assets.loaders;

import ee.tmtu.libludum.assets.AssetLoader;
import ee.tmtu.libludum.assets.AssetManager;
import ee.tmtu.libludum.graphics.Font;
import ee.tmtu.libludum.graphics.Glyph;
import ee.tmtu.libludum.graphics.Texture;

import java.io.*;

public class FontLoader implements AssetLoader<Font> {

	@Override
	public Font load(File res) throws IOException {
		final BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(res)));
		int lineHeight, baseLine, pageCount, charCount, charMax = 0, charIndex = 0, pageID = 0;
		Texture[] texturePages;
		Glyph[] glyphs;
		Glyph[] glyphTable;
		String line;
		String info = br.readLine();
		String common = br.readLine();
		lineHeight = property(common, "lineHeight", int.class);
		baseLine = property(common, "base", int.class);
		pageCount = property(common, "pages", int.class);

		texturePages = new Texture[pageCount];
		String pages = br.readLine();
		while(pageID < pageCount) {
            System.out.println(res.getParent());
			texturePages[pageID] = AssetManager.load(res.getParent() + "/" + property(pages, "file", pageID, String.class), Texture.class);
			pageID++;
		}
		String chars = br.readLine();
		charCount = property(chars, "count", int.class);
		glyphs = new Glyph[charCount];
		while((line = br.readLine()) != null) {
			if(line.startsWith("char")) {
				Glyph g = new Glyph();
				g.id = property(line, "id", int.class);
				g.x = property(line, "x", int.class);
				g.y = property(line, "y", int.class);
				g.width = property(line, "width", int.class);
				g.height = property(line, "height", int.class);
				g.xoff = property(line, "xoffset", int.class);
				g.yoff = property(line, "yoffset", int.class);
				g.xadv = property(line, "xadvance", int.class);
				g.page = property(line, "page", int.class);
				glyphs[charIndex] = g;
				charMax = Math.max(charMax, g.id);
				charIndex++;
			}
		}
		glyphTable = new Glyph[charMax+1];
		for(int i = 0; i < charIndex; i++) {
			glyphTable[glyphs[i].id] = glyphs[i];
		}
		Font font = new Font();
		font.glyphs = glyphTable;
		font.pages = texturePages;
		font.base = baseLine;
		font.lineheight = lineHeight;
		return font;
	}

	private static <T> T property(String line, String tag, Class<T> prop) {
		return property(line, tag, 0, prop);
	}

	private static <T> T property(String line, String tag, int recursive, Class<T> prop) {
		int start = line.indexOf(tag);
		int end = line.indexOf(" ", start+tag.length()+1);
		if(end < 0) end = line.length();
		while(recursive > 0) {
			int rstart = line.indexOf(tag, end);
			int rend = line.indexOf(" ", rstart+tag.length()+1);
			if(rstart < 0 || rend < 0) {
				break;
			}
			start += rstart - start;
			end += rend - end;
			recursive--;
		}
		String result = line.substring(start+tag.length()+1, end);
		if(prop == int.class || prop == Integer.class) {
			return (T) new Integer(Integer.parseInt(result));
		}
		if(prop == String.class) {
			return (T) result;
		}

		return (T)line.substring(start, end);
	}

}
