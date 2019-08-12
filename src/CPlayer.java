
public class CPlayer {
	static final int KB_PLAYER_UP = 0b00001;
	static final int KB_PLAYER_RIGHT = 0b00010;
	static final int KB_PLAYER_DOWN = 0b00100;
	static final int KB_PLAYER_LEFT = 0b01000;
	static final int KB_PLAYER_SHOT = 0b10000;
	static final int KB_PLAYER_MASK = 0b11111;

	static final int PLAYER_SPEED = 3;
	static final int PLAYER_STRAFE_SPEED = (int) Math.ceil((double) Math.sqrt(2) / (double) 2 * (double) PLAYER_SPEED);
	// static final int PLAYER_FRICTION = 5;
	static final int PLAYER_SHOT_COOLDOWN = 9;
	static final int PLAYER_LIFE_MAX = 1;
	static final int HITBOX = 10;

	public static final int CHAR_POSX_IN_SET = 0;
	public static final int CHAR_POSY_IN_SET = 0;

	public static final int LIFE_POSX_IN_SET = 32;
	public static final int LIFE_POSY_IN_SET = 32;

	private int m_posx;
	private int m_posy;
	private int m_speedx;
	private int m_speedy;
	private int m_shotCoolDown;
	private int m_life;

	CPlayer() {
		resetPlayer();
	}

	public void resetPlayer() {
		m_posx = CGraphics.NB_TILE_X / 2 * CGraphics.TILE_SIZE;
		m_posy = CGraphics.TILE_SIZE * (CGraphics.NB_TILE_Y - 2);
		m_speedx = 0;
		m_speedy = 0;
		m_life = PLAYER_LIFE_MAX;
	}

	public boolean isShooting(CFlag kbStatus) {
		if (m_shotCoolDown == 0 && kbStatus.isBitSet(KB_PLAYER_SHOT)) {
			m_shotCoolDown = PLAYER_SHOT_COOLDOWN;
			return true;
		}
		return false;
	}

	// fait bouger le perso
	public void procc(CFlag kbStatus) {
		// cooldown fire
		if (--m_shotCoolDown < 0)
			m_shotCoolDown = 0;

		m_speedx=0;
		m_speedy=0;
		// set la vitesse au max
		if (kbStatus.isBitSet(KB_PLAYER_UP) && kbStatus.isBitClr(KB_PLAYER_DOWN))
			m_speedy = -PLAYER_SPEED;
		if (kbStatus.isBitSet(KB_PLAYER_RIGHT) && kbStatus.isBitClr(KB_PLAYER_LEFT))
			m_speedx = PLAYER_SPEED;
		if (kbStatus.isBitSet(KB_PLAYER_DOWN) && kbStatus.isBitClr(KB_PLAYER_UP))
			m_speedy = PLAYER_SPEED;
		if (kbStatus.isBitSet(KB_PLAYER_LEFT) && kbStatus.isBitClr(KB_PLAYER_RIGHT))
			m_speedx = -PLAYER_SPEED;

		// gestion du strafe
		if (kbStatus.isBitSet(KB_PLAYER_UP) && kbStatus.isBitSet(KB_PLAYER_RIGHT) && kbStatus.isBitClr(KB_PLAYER_DOWN)
				&& kbStatus.isBitClr(KB_PLAYER_LEFT)) {
			m_speedx = PLAYER_STRAFE_SPEED;
			m_speedy = -PLAYER_STRAFE_SPEED;
		}
		if (kbStatus.isBitSet(KB_PLAYER_UP) && kbStatus.isBitSet(KB_PLAYER_LEFT) && kbStatus.isBitClr(KB_PLAYER_DOWN)
				&& kbStatus.isBitClr(KB_PLAYER_RIGHT)) {
			m_speedx = -PLAYER_STRAFE_SPEED;
			m_speedy = -PLAYER_STRAFE_SPEED;
		}
		if (kbStatus.isBitSet(KB_PLAYER_DOWN) && kbStatus.isBitSet(KB_PLAYER_RIGHT) && kbStatus.isBitClr(KB_PLAYER_UP)
				&& kbStatus.isBitClr(KB_PLAYER_LEFT)) {
			m_speedx = PLAYER_STRAFE_SPEED;
			m_speedy = PLAYER_STRAFE_SPEED;
		}
		if (kbStatus.isBitSet(KB_PLAYER_DOWN) && kbStatus.isBitSet(KB_PLAYER_LEFT) && kbStatus.isBitClr(KB_PLAYER_UP)
				&& kbStatus.isBitClr(KB_PLAYER_RIGHT)) {
			m_speedx = -PLAYER_STRAFE_SPEED;
			m_speedy = PLAYER_STRAFE_SPEED;
		}

		// mouvement
		m_posx += m_speedx;
		m_posy += m_speedy;

		// collisions limite
		if (m_posx < 0)
			m_posx = 0;
		if (m_posy < CGraphics.TILE_SIZE)
			m_posy = CGraphics.TILE_SIZE;
		if (m_posx > (CGraphics.NB_TILE_X - 1) * CGraphics.TILE_SIZE)
			m_posx = (CGraphics.NB_TILE_X - 1) * CGraphics.TILE_SIZE;
		if (m_posy > (CGraphics.NB_TILE_Y - 2) * CGraphics.TILE_SIZE)
			m_posy = (CGraphics.NB_TILE_Y - 2) * CGraphics.TILE_SIZE;

		// gestion des speed
		if (kbStatus.isBitClr(KB_PLAYER_DOWN) && kbStatus.isBitClr(KB_PLAYER_UP))
			m_speedy = 0;
		if (kbStatus.isBitClr(KB_PLAYER_LEFT) && kbStatus.isBitClr(KB_PLAYER_RIGHT))
			m_speedx = 0;

	}

	public void looseHp(int damageTaken) {
		m_life -= damageTaken;
	}

	public void setPosy(int y) {
		m_posy = y;
	}

	public void setPosx(int x) {
		m_posy = x;
	}

	public void setSpeedx(int x) {
		m_speedx = x;
	}

	public void setSpeedy(int y) {
		m_speedy = y;
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

	public int getCharPosxInSet() {
		return CHAR_POSX_IN_SET;
	}

	public int getCharPosyInSet() {
		return CHAR_POSY_IN_SET;
	}

	public int getLifePosxInSet(boolean isContainHP) {
		if (isContainHP)
			return LIFE_POSX_IN_SET;
		return LIFE_POSX_IN_SET + CGraphics.TILE_SIZE;
	}

	public int getLifePosyInSet() {
		return LIFE_POSY_IN_SET;
	}
}
