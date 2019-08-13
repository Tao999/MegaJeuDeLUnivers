
public class GameObject {
	static final int TYPE_PLAYER = 0;
	static final int TYPE_ENEMY = 1;
	static final int TYPE_BULLET = 2;
	static final int TYPE_EXPLOSION = 3;
	static final int TYPE_NONE = 4;

	protected int m_posx;
	protected int m_posy;
	protected int m_type;
	protected int m_speedx;
	protected int m_speedy;
	protected boolean m_isDeadObject;

	public GameObject() {
		m_type = TYPE_NONE;
		m_posx = 0;
		m_posy = 0;
		m_speedx = 0;
		m_speedy = 0;
		m_isDeadObject = false;
	}

	public void procc() {

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

	public int getType() {
		return m_type;
	}

	public void setPosx(int posx) {
		m_posx = posx;
	}

	public void setPosy(int posy) {
		m_posy = posy;
	}

	public void setSpeedx(int speedx) {
		m_speedx = speedx;
	}

	public void setSpeedy(int speedy) {
		m_speedy = speedy;
	}

	public int getPosxInSet() {
		return 0;
	}

	public int getPosyInSet() {
		return 0;
	}

	public boolean isDeadObject() {
		return m_isDeadObject;
	}

	public void markAsDeadObject() {
		m_isDeadObject = true;
	}
}
