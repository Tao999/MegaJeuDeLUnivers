
public class CEnemy {
	public static final int DEFAULT_SPEED_Y = 5;
	public static final int SPEED_MOL = 2;
	public static final int RANGE = CGraphics.TILE_SIZE / 2;
	public static final int DEFAULT_LIFE = 3;
	public static final int DAMAGE = 1;

	public static final int POSX_IN_SET = 64;
	public static final int POSY_IN_SET = 0;

	private int m_posx;
	private int m_posy;
	private int m_speedx;
	private int m_speedy;
	private int m_life;

	CEnemy() {
		m_posy = -CGraphics.TILE_SIZE;
		m_posx = (int) (Math.random() * (CGraphics.NB_TILE_X - 1) * CGraphics.TILE_SIZE);
		m_speedx = (int) ((Math.random() * (SPEED_MOL * 2)) - SPEED_MOL);
		m_speedy = (DEFAULT_SPEED_Y - (int) (Math.random() * SPEED_MOL));
		m_life = DEFAULT_LIFE;
		if (m_speedy <= 0)
			m_speedy = 1;

	}

	CEnemy(int posx) {
		m_posy = -CGraphics.TILE_SIZE;
		m_posx = posx;
		m_speedx = (int) ((Math.random() * (SPEED_MOL * 2)) - SPEED_MOL);
		m_speedy = (DEFAULT_SPEED_Y - (int) (Math.random() * SPEED_MOL));
		m_life = DEFAULT_LIFE;
		if (m_speedy <= 0)
			m_speedy = 1;

	}

	public void procc() {
		m_posx += m_speedx;
		m_posy += m_speedy;
		if (m_posx > CGraphics.NB_TILE_X * CGraphics.TILE_SIZE)
			m_posx = -CGraphics.TILE_SIZE;
		if (m_posx < -CGraphics.TILE_SIZE)
			m_posx = CGraphics.NB_TILE_X * CGraphics.TILE_SIZE;
	}

	public boolean isDead() {
		return m_life <= 0;
	}

	public boolean isOutOfBound() {
		return (m_posy > (CGraphics.NB_TILE_Y) * CGraphics.TILE_SIZE || m_posy < -CGraphics.TILE_SIZE);
	}

	public void looseHP(int x) {
		m_life -= x;
		m_speedy -= 1;
	}

	public int getPosx() {
		return m_posx;
	}

	public int getPosy() {
		return m_posy;
	}

	public int getSpeedx() {
		return m_speedx;
	}

	public int getSpeedy() {
		return m_speedy;
	}

	public int getLife() {
		return m_life;
	}

	public void setSpeedx(int x) {
		m_speedx = x;
	}

	public void setSpeedy(int y) {
		m_speedy = y;
	}

	public void setPosx(int x) {
		m_posx = x;
	}

	public void setPosy(int y) {
		m_posy = y;
	}

	public int getPosxInSet() {
		return POSX_IN_SET;
	}

	public int getPosyInSet() {
		return POSY_IN_SET;
	}

}
