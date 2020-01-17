package application;

import java.util.ArrayList;

public class Text {
	static final int CHAR_START = '0';
	static final int CHAR_FINAL = 'Z';
	static final int CHAR_DOT = '.';
	static final int CHAR_DOT_X = 96;
	static final int CHAR_DOT_Y = 160;
	static final int CHAR_ERR_X = 0;
	static final int CHAR_ERR_Y = 64;

	private String m_text;
	private int m_posx;
	private int m_posy;
	private int m_size;
	private int m_codeRessource;

	public Text(String text, int posx, int posy, int size, int codeRessource) {
		m_text = text.toUpperCase();
		m_posx = posx;
		m_posy = posy;
		m_size = size;
		m_codeRessource = codeRessource;
	}

	public Sprite getSpriteCharAt(int i) {
		int iTemp = m_text.charAt(i);

		// si le charachtère est entre CHAR_START et CHAR_FINAL
		if (iTemp >= CHAR_START && iTemp <= CHAR_FINAL) {
			iTemp -= CHAR_START;
			return new Sprite(m_posx + i * m_size, m_posy,
					iTemp % CGraphics.GFXSET_SIZE_SIDE * m_size,
					iTemp / CGraphics.GFXSET_SIZE_SIDE * m_size,
					m_size, m_size,
					m_codeRessource);
		}

		// gestion des charactères exeptions
		switch (iTemp) {
		case CHAR_DOT:
			return new Sprite(m_posx + i * CGraphics.TILE_SIZE, m_posy, CHAR_DOT_X, CHAR_DOT_Y,
					m_size, m_size, m_codeRessource);

		default:
			break;
		}

		// si c'est un charactère imprévue, retourne un espace
		return new Sprite(m_posx + i * m_size, m_posy, CHAR_ERR_X, CHAR_ERR_Y,
				m_size, m_size, m_codeRessource);
	}

	public ArrayList<Sprite> getSprites(){
		ArrayList<Sprite> sprites = new ArrayList<Sprite>();
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
	
	public int getSize() {
		return m_size;
	}
	
	public int getCodeRessource() {
		return m_codeRessource;
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
