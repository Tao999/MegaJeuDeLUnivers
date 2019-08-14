package application;


public class CText {
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

		if (iTemp >= CGraphics.CHAR_START && iTemp <= CGraphics.CHAR_FINAL) {
			iTemp -= CGraphics.CHAR_START;
			return new CSprite(m_posx + i * CGraphics.TILE_SIZE, m_posy,
					iTemp % CGraphics.FONT_SIZE_SIDE * CGraphics.TILE_SIZE,
					iTemp / CGraphics.FONT_SIZE_SIDE * CGraphics.TILE_SIZE, CGraphics.TILESET_CODE_FONT);
		}

		return new CSprite(m_posx + i * CGraphics.TILE_SIZE, m_posy, CGraphics.CHAR_ERR_X,
				CGraphics.CHAR_ERR_Y, CGraphics.TILESET_CODE_FONT);
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
