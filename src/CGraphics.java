import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import java.util.ArrayList;

public class CGraphics extends JPanel {
	static final int SCREEN_ZOOM = 2;
	static final int TILE_SIZE = 32;
	static final int NB_TILE_X = 13;
	static final int NB_TILE_Y = 15;
	static final int SCREEN_SIZE_X = NB_TILE_X * TILE_SIZE * SCREEN_ZOOM;
	static final int SCREEN_SIZE_Y = NB_TILE_Y * TILE_SIZE * SCREEN_ZOOM;
	public static final int GFXSET_SIZE_SIDE = 8;

	private BufferedImage buf;
	private BufferedImage m_GFXSet;

	CGraphics() {
		setPreferredSize(new Dimension(SCREEN_SIZE_X, SCREEN_SIZE_Y));
		buf = new BufferedImage(SCREEN_SIZE_X, SCREEN_SIZE_Y, BufferedImage.TYPE_INT_RGB);
	}

	public void setGFXSet(String path) {
		try {
			m_GFXSet = ImageIO.read(new File(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			m_GFXSet = new BufferedImage(CGraphics.GFXSET_SIZE_SIDE * CGraphics.TILE_SIZE,
					CGraphics.GFXSET_SIZE_SIDE * CGraphics.TILE_SIZE, BufferedImage.TYPE_INT_RGB);
		}
	}

	public void blastSprites(ArrayList<CSprite> sprites) {
		if (sprites == null)
			return;
		Graphics g = buf.getGraphics();

		// chargement du background
		BufferedImage bkg = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
		try {
			bkg = ImageIO.read(new File("gfx/background.png"));
		} catch (IOException e) {

			e.printStackTrace();
		}

		g.drawImage(bkg, 0, 0, bkg.getWidth() * SCREEN_ZOOM, bkg.getHeight() * SCREEN_ZOOM, this);

		// chargements de tout les sprites
		CSprite tempSprite;
		for (int i = 0; i < sprites.size(); i++) {
			tempSprite = sprites.get(i);
			g.drawImage(
					m_GFXSet.getSubimage(tempSprite.getPosxInSet(), tempSprite.getPosyInSet(), TILE_SIZE, TILE_SIZE),
					tempSprite.getPosx() * SCREEN_ZOOM, tempSprite.getPosy() * SCREEN_ZOOM, TILE_SIZE * SCREEN_ZOOM,
					TILE_SIZE * SCREEN_ZOOM, this);

		}

		// call blast
		paint(getGraphics());
	}

	public void paint(Graphics g) {
		// blast
		g.drawImage(buf, 0, 0, this);
	}

}
