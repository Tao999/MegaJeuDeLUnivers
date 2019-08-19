package application;

import java.util.ArrayList;

public class CText {
	static final int CHAR_START = '0';
	static final int CHAR_FINAL = 'Z';
	static final int CHAR_DOT = '.';
	static final int CHAR_DOT_X = 96;
	static final int CHAR_DOT_Y = 160;

	private String m_text;
	private int m_posx;
	private int m_posy;

	public CText(String text, int posx, int posy) {
		m_text = text.toUpperCase();
		m_posx = posx;
		m_posy = posy;
	}

	public CSprite getSpriteCharAt(int i) {
		int iTemp = m_text.charAt(i);

		// si le charachtère est entre CHAR_START et CHAR_FINAL
		if (iTemp >= CHAR_START && iTemp <= CHAR_FINAL) {
			iTemp -= CHAR_START;
			return new CSprite(m_posx + i * CGraphics.TILE_SIZE, m_posy,
					iTemp % CGraphics.FONT_SIZE_SIDE * CGraphics.TILE_SIZE,
					iTemp / CGraphics.FONT_SIZE_SIDE * CGraphics.TILE_SIZE, CGraphics.TILESET_CODE_FONT);
		}

		// gestion des charactères exeptions
		switch (iTemp) {
		case CHAR_DOT:
			return new CSprite(m_posx + i * CGraphics.TILE_SIZE, m_posy, CHAR_DOT_X, CHAR_DOT_Y,
					CGraphics.TILESET_CODE_FONT);

		default:
			break;
		}

		// si c'est un charactère imprévue, retourne un espace
		return new CSprite(m_posx + i * CGraphics.TILE_SIZE, m_posy, CGraphics.CHAR_ERR_X, CGraphics.CHAR_ERR_Y,
				CGraphics.TILESET_CODE_FONT);
	}

	public ArrayList<CSprite> getSprites(){
		ArrayList<CSprite> sprites = new ArrayList<CSprite>();
		for(int i=0; i< m_text.length(); i++) 
			sprites.add(getSpriteCharAt(i));
		return sprites;
	}
	
	public String getText() {
		return m_text;
	}

	public int getPosx() {
		return m_posx;
	}

	public int getPosy() {
		return m_posy;
	}

	public void setText(String text) {
		m_text = text;
	}

	public void setPosx(int posx) {
		m_posx = posx;
	}

	public void setPosy(int posy) {
		m_posy = posy;
	}
}
