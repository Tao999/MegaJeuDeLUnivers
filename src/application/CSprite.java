package application;


public class CSprite {
	private int m_posx;
	private int m_posy;
	private int m_posxInGraphicSet;
	private int m_posyInGraphicSet;
	private int m_tileSetToUse;

	public CSprite(int posx, int posy, int posxInGraphicSet, int posyInGraphicSet, int tileSetToUse) {
		m_posx = posx;
		m_posy = posy;
		m_posxInGraphicSet = posxInGraphicSet;
		m_posyInGraphicSet = posyInGraphicSet;
		m_tileSetToUse = tileSetToUse;
	}

	public int getPosx() {
		return m_posx;
	}

	public int getPosy() {
		return m_posy;
	}

	public int getPosxInGraphicSet() {
		return m_posxInGraphicSet;
	}

	public int getPosyInGraphicSet() {
		return m_posyInGraphicSet;
	}
	
	public int getTileSetToUse() {
		return m_tileSetToUse;
	}
}
