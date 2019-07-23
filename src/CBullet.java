import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class CBullet {
	static final String NAME_PATH = "gfx/bullet.png";
	public static final int DEFAULT_SPEED_Y = 7;
	public static final int DAMAGE = 9;
	public static final int RANGE = 10 / 2;

	private int m_posx;
	private int m_posy;
	private int m_speedx;
	private int m_speedy;
	private boolean m_ally;
	private BufferedImage m_img;

	CBullet(int x, int y, boolean isAlly) {
		m_speedx = 0;
		m_speedy = -DEFAULT_SPEED_Y;
		m_posx = x;
		m_posy = y;
		m_ally = isAlly;
		try {
			m_img = ImageIO.read(new File(NAME_PATH));
		} catch (IOException e) {
			m_img = new BufferedImage(CGraphics.TILE_SIZE, CGraphics.TILE_SIZE, BufferedImage.TYPE_INT_RGB);
		}
	}

	CBullet(int x, int y, int vectSpeedX, int vectSpeedY, boolean isAlly) {
		m_posx = x;
		m_posy = y;

		// ajout de la vitesse du vaisseau
		m_speedy = vectSpeedY - DEFAULT_SPEED_Y;
		if (m_speedy == 0 && m_speedx == 0)
			m_speedy = -1;

		m_speedx = vectSpeedX;
		m_ally = isAlly;

		try {
			m_img = ImageIO.read(new File(NAME_PATH));
		} catch (IOException e) {
			m_img = null;
		}
	}

	public boolean isAlly() {
		return m_ally;
	}

	public void procc() {
		m_posx += m_speedx;
		m_posy += m_speedy;
	}

	public boolean isOutOfBound() {
		return (m_posx + CGraphics.TILE_SIZE < 0) || (m_posy + CGraphics.TILE_SIZE < 0)
				|| (m_posx > (CGraphics.NB_TILE_X) * CGraphics.TILE_SIZE)
				|| (m_posy > (CGraphics.NB_TILE_Y) * CGraphics.TILE_SIZE);
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
