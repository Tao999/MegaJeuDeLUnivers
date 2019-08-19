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

	static final int CHAR_ERR_X = 0;
	static final int CHAR_ERR_Y = 64;
	
	//les valeurs de zooms sont multipliers par 10 pour éviter les bug de calculs due à java
	public static final int MAX_ZOOM = 20;
	public static final int MIN_ZOOM = 10;

	static final int  START_ZOOM = 20;
	public static final int TILE_SIZE = 32;
	public static final int NB_TILE_X = 13;
	public static final int NB_TILE_Y = 15;
	public static final int GFXSET_SIZE_SIDE = 8;
	public static final int FONT_SIZE_SIDE = 8;

	private BufferedImage buf;
	private BufferedImage m_GFXSet;
	private BufferedImage m_font;
	private BufferedImage m_bkg;

	private int m_screenZoom;
	private double m_screenSizeX;
	private double m_screenSizeY;

	CGraphics() {
		m_screenZoom = START_ZOOM;
		m_screenSizeX = NB_TILE_X * TILE_SIZE * m_screenZoom/10.0;
		m_screenSizeY = NB_TILE_Y * TILE_SIZE * m_screenZoom/10.0;
		setPreferredSize(new Dimension((int) m_screenSizeX, (int) m_screenSizeY));
		buf = new BufferedImage((int) m_screenSizeX, (int) m_screenSizeY, BufferedImage.TYPE_INT_RGB);
	}

	public void setGFXSet(String gfxPath, String fontPath, String bkgPath) {
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
			g.drawImage(m_bkg, 0, 0, (int) (m_bkg.getWidth() * m_screenZoom/10.0), (int) (m_bkg.getHeight() * m_screenZoom/10.0),
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
							(int) (tempSprite.getPosx() * m_screenZoom/10.0), (int) (tempSprite.getPosy() * m_screenZoom/10.0),
							(int) (TILE_SIZE * m_screenZoom/10.0), (int) (TILE_SIZE * m_screenZoom/10.0), this);
				else if (tempSprite.getTileSetToUse() == TILESET_CODE_FONT)
					g.drawImage(
							m_font.getSubimage(tempSprite.getPosxInGraphicSet(), tempSprite.getPosyInGraphicSet(),
									TILE_SIZE, TILE_SIZE),
							(int) (tempSprite.getPosx() * m_screenZoom/10.0), (int) (tempSprite.getPosy() * m_screenZoom/10.0),
							(int) (TILE_SIZE * m_screenZoom/10.0), (int) (TILE_SIZE * m_screenZoom/10.0), this);
			}
		}
		// call blast
		paint(getGraphics());
	}

	public void paint(Graphics g) {
		// blast
		g.drawImage(buf, 0, 0, this);
	}

	public int getScreenZoom() {
		return m_screenZoom;
	}
	
	public void setScreenZoom(int zoom) {
		m_screenZoom = zoom;
		m_screenSizeX = NB_TILE_X * TILE_SIZE * m_screenZoom/10.0;
		m_screenSizeY = NB_TILE_Y * TILE_SIZE * m_screenZoom/10.0;
		setPreferredSize(new Dimension((int) m_screenSizeX, (int) m_screenSizeY));
		buf = new BufferedImage((int) m_screenSizeX, (int) m_screenSizeY, BufferedImage.TYPE_INT_RGB);
	}

}
