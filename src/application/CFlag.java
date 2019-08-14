package application;


public class CFlag {
	private int m_flag;

	public CFlag() {
		m_flag = 0;
	}

	public void bitSet(int msk) {
		m_flag = m_flag | msk;
	}

	public void bitClr(int msk) {
		m_flag = m_flag & ~msk;
	}

	public void bitTgl(int msk) {
		m_flag = m_flag ^ msk;
	}

	public boolean isBitSet(int msk) {
		return (m_flag & msk) == msk;
	}

	public boolean isBitClr(int msk) {
		return (m_flag & msk) != msk;
	}

	public int getMsk(int msk) {
		return m_flag & msk;
	}

	public int getFlag() {
		return m_flag;
	}

}
