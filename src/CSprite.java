
public class CSprite {
	private int m_posx;
	private int m_posy;
	private int m_posxInSet;
	private int m_posyInSet;

	public CSprite(int posx, int posy, int posxInSet, int posyInSet) {
		m_posx = posx;
		m_posy = posy;
		m_posxInSet = posxInSet;
		m_posyInSet = posyInSet;
	}

	public int getPosx() {
		return m_posx;
	}

	public int getPosy() {
		return m_posy;
	}

	public int getPosxInSet() {
		return m_posxInSet;
	}

	public int getPosyInSet() {
		return m_posyInSet;
	}
}
