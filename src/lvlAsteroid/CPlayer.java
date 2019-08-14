package lvlAsteroid;
import application.CApp;
import application.CGraphics;
import application.GameObject;

public class CPlayer extends GameObject {

	static final int PLAYER_SPEED = 3;
	static final int PLAYER_SHOT_COOLDOWN = 9;
	static final int PLAYER_LIFE_MAX = CGraphics.NB_TILE_X;
	static final int RANGE = 10;

	public static final int CHAR_POSX_IN_SET = 0;
	public static final int CHAR_POSY_IN_SET = 0;

	public static final int LIFE_POSX_IN_SET = 32;
	public static final int LIFE_POSY_IN_SET = 32;

	private int m_shotCoolDown;
	private int m_life;

	CPlayer() {
		m_type = TYPE_PLAYER;
		resetPlayer();
	}

	public void resetPlayer() {
		m_posx = CGraphics.NB_TILE_X / 2 * CGraphics.TILE_SIZE;
		m_posy = CGraphics.TILE_SIZE * (CGraphics.NB_TILE_Y - 2);
		m_speedx = 0;
		m_speedy = 0;
		m_life = PLAYER_LIFE_MAX;
	}

	public boolean canShoot() {
		if (m_shotCoolDown == 0 && CApp.getKbStatus().isBitSet(CApp.KB_SPACE)) {
			m_shotCoolDown = PLAYER_SHOT_COOLDOWN;
			return true;
		}
		return false;
	}

	@Override
	public void procc() {
		// cooldown fire
		if (--m_shotCoolDown < 0)
			m_shotCoolDown = 0;

		m_speedx = 0;
		m_speedy = 0;
		// set la vitesse au max
		if ((CApp.getKbStatus().isBitSet(CApp.KB_UP) || (CApp.getKbStatus().isBitSet(CApp.KB_Z)))
				&& (CApp.getKbStatus().isBitClr(CApp.KB_DOWN) && CApp.getKbStatus().isBitClr(CApp.KB_S)))
			m_speedy = -PLAYER_SPEED;
		if ((CApp.getKbStatus().isBitSet(CApp.KB_RIGHT) || (CApp.getKbStatus().isBitSet(CApp.KB_D)))
				&& (CApp.getKbStatus().isBitClr(CApp.KB_LEFT) && CApp.getKbStatus().isBitClr(CApp.KB_Q)))
			m_speedx = PLAYER_SPEED;
		if ((CApp.getKbStatus().isBitSet(CApp.KB_DOWN) || (CApp.getKbStatus().isBitSet(CApp.KB_S)))
				&& (CApp.getKbStatus().isBitClr(CApp.KB_UP) && CApp.getKbStatus().isBitClr(CApp.KB_Z)))
			m_speedy = PLAYER_SPEED;
		if ((CApp.getKbStatus().isBitSet(CApp.KB_LEFT) || (CApp.getKbStatus().isBitSet(CApp.KB_Q)))
				&& (CApp.getKbStatus().isBitClr(CApp.KB_RIGHT) && CApp.getKbStatus().isBitClr(CApp.KB_D)))
			m_speedx = -PLAYER_SPEED;

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

	}

	public void looseHp(int damageTaken) {
		m_life -= damageTaken;
	}

	public int getLife() {
		return m_life;
	}

	@Override
	public int getPosxInSet() {
		return CHAR_POSX_IN_SET;
	}

	@Override
	public int getPosyInSet() {
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

	@Override
	public boolean isDeadObject() {
		return m_life == 0;
	}
}
