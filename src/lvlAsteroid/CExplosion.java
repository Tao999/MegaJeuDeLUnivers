package lvlAsteroid;
import application.CGraphics;
import application.GameObject;

public class CExplosion extends GameObject{
	public static final int NB_STEP = 6;
	public static final int FRAME_BY_STEP = 2;

	public static final int POSX_IN_SET = 96;
	public static final int POSY_IN_SET = 0;

	private int m_count;

	CExplosion(int posx, int posy) {
		m_type = TYPE_EXPLOSION;
		m_posx = posx;
		m_posy = posy;
		m_count = 0;

	}

	@Override
	public void procc() {
		m_count++;
	}

	@Override
	public boolean isDeadObject() {
		return NB_STEP * FRAME_BY_STEP <= m_count|| m_isDeadObject;
	}

	@Override
	public int getPosxInSet() {
		return (POSX_IN_SET + ((m_count / FRAME_BY_STEP % NB_STEP) * CGraphics.TILE_SIZE))
				% (CGraphics.GFXSET_SIZE_SIDE * CGraphics.TILE_SIZE);
	}

	@Override
	public int getPosyInSet() {
		if (m_count / FRAME_BY_STEP <= 4)
			return POSY_IN_SET;
		else
			return POSY_IN_SET + CGraphics.TILE_SIZE;
	}

}
