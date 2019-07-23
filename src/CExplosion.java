import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class CExplosion {
	static final String NAME_PATH = "gfx/explosion.png";
	public static final int NB_STEP = 6;
	public static final int FRAME_BY_STEP = 2;

	private int m_count;
	private int m_posx;
	private int m_posy;
	private BufferedImage m_img;

	CExplosion(int posx, int posy) {
		m_posx = posx;
		m_posy = posy;
		m_count = 0;
		try {
			m_img = ImageIO.read(new File(NAME_PATH));
		} catch (IOException e) {
			m_img = new BufferedImage(CGraphics.TILE_SIZE, CGraphics.TILE_SIZE, BufferedImage.TYPE_INT_RGB);
		}
	}

	public void procc() {
		m_count++;
	}

	public boolean isDead() {
		return NB_STEP * FRAME_BY_STEP <= m_count;
	}

	public int getPosx() {
		return m_posx;
	}

	public int getPosy() {
		return m_posy;
	}

	public BufferedImage getImg() {
		return m_img.getSubimage((m_count / FRAME_BY_STEP % NB_STEP)*CGraphics.TILE_SIZE, 0, CGraphics.TILE_SIZE, CGraphics.TILE_SIZE);
	}
}
