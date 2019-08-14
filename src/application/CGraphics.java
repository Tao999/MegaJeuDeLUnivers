package application;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class CGraphics extends JPanel {
	public static final int TILESET_CODE_SPRITE = 0;
	public static final int TILESET_CODE_FONT = 1;

	public static final int CHAR_START = '0';
	public static final int CHAR_FINAL = 'Z';
	public static final int CHAR_ERR_X = 0;
	public static final int CHAR_ERR_Y = 64;
	public static final int SCREEN_ZOOM = 2;
	public static final int TILE_SIZE = 32;
	public static final int NB_TILE_X = 13;
	public static final int NB_TILE_Y = 15;
	public static final int SCREEN_SIZE_X = NB_TILE_X * TILE_SIZE * SCREEN_ZOOM;
	public static final int SCREEN_SIZE_Y = NB_TILE_Y * TILE_SIZE * SCREEN_ZOOM;
	public static final int GFXSET_SIZE_SIDE = 8;
	public static final int FONT_SIZE_SIDE = 8;

	private BufferedImage buf;
	private BufferedImage m_GFXSet;
	private BufferedImage m_font;
	private BufferedImage m_bkg;

	CGraphics() {
		setPreferredSize(new Dimension(SCREEN_SIZE_X, SCREEN_SIZE_Y));
		buf = new BufferedImage(SCREEN_SIZE_X, SCREEN_SIZE_Y, BufferedImage.TYPE_INT_RGB);
	}

	public void setGFXSet(String gfxPath, String fontPath, String bkgPath) {
		System.out.println("changement des sets graphiques");
		// chargement des gfx
		if (gfxPath != null)
			try {
				m_GFXSet = ImageIO.read(new File(gfxPath));
			} catch (IOException e) {
				m_GFXSet = new BufferedImage(CGraphics.GFXSET_SIZE_SIDE * CGraphics.TILE_SIZE,
						CGraphics.GFXSET_SIZE_SIDE * CGraphics.TILE_SIZE, BufferedImage.TYPE_INT_RGB);
			}
		else
			m_GFXSet = new BufferedImage(CGraphics.GFXSET_SIZE_SIDE * CGraphics.TILE_SIZE,
					CGraphics.GFXSET_SIZE_SIDE * CGraphics.TILE_SIZE, BufferedImage.TYPE_INT_RGB);

		// chargement du font
		if (fontPath != null)
			try {
				m_font = ImageIO.read(new File(fontPath));
			} catch (IOException e) {
				m_font = new BufferedImage(CGraphics.GFXSET_SIZE_SIDE * CGraphics.TILE_SIZE,
						CGraphics.GFXSET_SIZE_SIDE * CGraphics.TILE_SIZE, BufferedImage.TYPE_INT_RGB);
			}
		else
			m_font = new BufferedImage(CGraphics.GFXSET_SIZE_SIDE * CGraphics.TILE_SIZE,
					CGraphics.GFXSET_SIZE_SIDE * CGraphics.TILE_SIZE, BufferedImage.TYPE_INT_RGB);

		// chargement du background
		if (bkgPath != null)
			try {
				m_bkg = ImageIO.read(new File(bkgPath));
			} catch (IOException e) {
				m_bkg = new BufferedImage(CGraphics.NB_TILE_X * CGraphics.TILE_SIZE,
						CGraphics.NB_TILE_Y * CGraphics.TILE_SIZE, BufferedImage.TYPE_INT_RGB);
			}
		else
			m_bkg = new BufferedImage(CGraphics.NB_TILE_X * CGraphics.TILE_SIZE,
					CGraphics.NB_TILE_Y * CGraphics.TILE_SIZE, BufferedImage.TYPE_INT_RGB);
	}

	public void blastSprites(ArrayList<CSprite> sprites) {
		Graphics g = buf.getGraphics();

		// chargement du background
		if (m_bkg != null)
			g.drawImage(m_bkg, 0, 0, m_bkg.getWidth() * SCREEN_ZOOM, m_bkg.getHeight() * SCREEN_ZOOM,
					this);

		// chargements de tout les sprites
		if (sprites != null) {
			CSprite tempSprite;
			for (int i = 0; i < sprites.size(); i++) {
				tempSprite = sprites.get(i);
				if (tempSprite.getTileSetToUse() == TILESET_CODE_SPRITE)
					g.drawImage(
							m_GFXSet.getSubimage(tempSprite.getPosxInGraphicSet(), tempSprite.getPosyInGraphicSet(),
									TILE_SIZE, TILE_SIZE),
							tempSprite.getPosx() * SCREEN_ZOOM, tempSprite.getPosy() * SCREEN_ZOOM,
							TILE_SIZE * SCREEN_ZOOM, TILE_SIZE * SCREEN_ZOOM, this);
				else if (tempSprite.getTileSetToUse() == TILESET_CODE_FONT)
					g.drawImage(
							m_font.getSubimage(tempSprite.getPosxInGraphicSet(), tempSprite.getPosyInGraphicSet(),
									TILE_SIZE, TILE_SIZE),
							tempSprite.getPosx() * SCREEN_ZOOM, tempSprite.getPosy() * SCREEN_ZOOM,
							TILE_SIZE * SCREEN_ZOOM, TILE_SIZE * SCREEN_ZOOM, this);
			}
		}
		// call blast
		paint(getGraphics());
	}

	public void paint(Graphics g) {
		// blast
		g.drawImage(buf, 0, 0, this);
	}

}
