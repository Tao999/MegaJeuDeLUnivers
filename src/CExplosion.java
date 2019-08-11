
public class CExplosion {
	public static final int NB_STEP = 6;
	public static final int FRAME_BY_STEP = 2;

	public static final int POSX_IN_SET = 96;
	public static final int POSY_IN_SET = 0;

	private int m_count;
	private int m_posx;
	private int m_posy;

	CExplosion(int posx, int posy) {
		m_posx = posx;
		m_posy = posy;
		m_count = 0;

	}

	public void procc() {
		m_count++;
	}

	public boolean isDead() {
		return NB_STEP * FRAME_BY_STEP <= m_count;
	}

	public int getPosx() {
		return m_posx;
	}

	public int getPosy() {
		return m_posy;
	}

	public int getPosxInSet() {
		return (POSX_IN_SET + ((m_count / FRAME_BY_STEP % NB_STEP) * CGraphics.TILE_SIZE))
				% (CGraphics.GFXSET_SIZE_SIDE * CGraphics.TILE_SIZE);
	}

	public int getPosyInSet() {
		if (m_count / FRAME_BY_STEP <= 4)
			return POSY_IN_SET;
		else
			return POSY_IN_SET + CGraphics.TILE_SIZE;
	}

}
