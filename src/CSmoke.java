import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class CSmoke {
	static final String NAME_PATH = "gfx/smoke.png";
	static final int SPEED = 1;
	private int m_posx;
	private int m_posy;
	private int m_speedx;
	private int m_speedy;
	private BufferedImage m_img;

	CSmoke(int x, int y) {
		m_posx = x;
		m_posy = y;
		try {
			m_img = ImageIO.read(new File(NAME_PATH));
		} catch (IOException e) {
			m_img = new BufferedImage(CGraphics.TILE_SIZE, CGraphics.TILE_SIZE, BufferedImage.TYPE_INT_RGB);
		}
	}

	public void procc() {
		m_posy += SPEED;
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
