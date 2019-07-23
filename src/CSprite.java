import java.awt.image.BufferedImage;


public class CSprite {
	private BufferedImage m_img;
	private int m_posx;
	private int m_posy;

	public CSprite() {
		m_img = null;
		m_posx = 0;
		m_posy = 0;
	}

	public CSprite(BufferedImage img, int posx, int posy) {
		m_img = img;
		m_posx = posx;
		m_posy = posy;
	}
	
	public BufferedImage getImg() {
		return m_img;
	}

	public int getPosx() {
		return m_posx;
	}

	public int getPosy() {
		return m_posy;
	}
}
