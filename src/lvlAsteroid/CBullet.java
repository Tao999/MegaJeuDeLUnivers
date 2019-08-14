package lvlAsteroid;
import application.CGraphics;
import application.GameObject;

public class CBullet extends GameObject {
	public static final int DEFAULT_SPEED_Y = 7;
	public static final int DAMAGE = 1;
	public static final int RANGE = 7;

	public static final int POSX_IN_SET = 32;
	public static final int POSY_IN_SET = 0;

	private boolean m_isAlly;

	CBullet(int x, int y, boolean isAlly) {
		m_type = TYPE_BULLET;
		m_speedx = 0;
		m_speedy = -DEFAULT_SPEED_Y;
		m_posx = x;
		m_posy = y;
		m_isAlly = isAlly;
	}

	CBullet(int x, int y, int vectSpeedX, int vectSpeedY, boolean isAlly) {
		m_type = TYPE_BULLET;
		m_posx = x;
		m_posy = y;

		// ajout de la vitesse du vaisseau
		m_speedy = vectSpeedY - DEFAULT_SPEED_Y;
		if (m_speedy == 0 && m_speedx == 0)
			m_speedy = -1;

		m_speedx = vectSpeedX;
		m_isAlly = isAlly;
	}

	public boolean isAlly() {
		return m_isAlly;
	}

	@Override
	public void procc() {
		m_posx += m_speedx;
		m_posy += m_speedy;
	}

	@Override
	public boolean isDeadObject() {
		return (m_posx + CGraphics.TILE_SIZE < 0) || (m_posy + CGraphics.TILE_SIZE < 0)
				|| (m_posx > (CGraphics.NB_TILE_X) * CGraphics.TILE_SIZE)
				|| (m_posy > (CGraphics.NB_TILE_Y) * CGraphics.TILE_SIZE) || m_isDeadObject;
	}

	@Override
	public int getPosxInSet() {
		return POSX_IN_SET;
	}

	@Override
	public int getPosyInSet() {
		return POSY_IN_SET;
	}

}
