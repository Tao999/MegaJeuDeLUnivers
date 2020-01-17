package application;


public class Sprite {
	private int m_posx;
	private int m_posy;
	private int m_posxInGraphicSet;
	private int m_posyInGraphicSet;
	private int m_sizex;
	private int m_sizey;
	private int m_gfxCodeRessource;

	public Sprite(int posx, int posy, int posxInGraphicSet, int posyInGraphicSet, int sizex, int sizey ,int gfxCodeRessource) {
		m_posx = posx;
		m_posy = posy;
		m_posxInGraphicSet = posxInGraphicSet;
		m_posyInGraphicSet = posyInGraphicSet;
		m_sizex = sizex;
		m_sizey = sizey;
		m_gfxCodeRessource = gfxCodeRessource;
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
	
	public int getSizex() {
		return m_sizex;
	}
	
	public int getSizey() {
		return m_sizey;
	}
	
	public int getGfxRessource() {
		return m_gfxCodeRessource;
	}
}
