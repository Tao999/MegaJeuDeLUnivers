package application;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;

@SuppressWarnings("serial")
public class CGraphics extends JPanel {
	// les valeurs de zooms sont multipliers par 10 pour éviter les bug de calculs
	// due à java
	public static final int MAX_ZOOM = 20;
	public static final int MIN_ZOOM = 10;

	static final int START_ZOOM = 20;
	public static final int TILE_SIZE = 32;
	public static final int NB_TILE_X = 13;
	public static final int NB_TILE_Y = 15;

	public static final int SCREEN_SIZE_X = NB_TILE_X * TILE_SIZE;
	public static final int SCREEN_SIZE_Y = NB_TILE_Y * TILE_SIZE;

	public static final int GFXSET_SIZE_SIDE = 8;
	public static final int RESSOURCE_SIZE = GFXSET_SIZE_SIDE * TILE_SIZE;

	public static final int MAP_CODE = -1;

	private BufferedImage buf;
	private HashMap<Integer, BufferedImage> m_gfxRessources;

	private int m_screenZoom;
	private double m_screenSizeX;
	private double m_screenSizeY;

	CGraphics() {
		m_screenZoom = START_ZOOM;
		m_screenSizeX = SCREEN_SIZE_X * m_screenZoom / 10.0;
		m_screenSizeY = SCREEN_SIZE_Y * m_screenZoom / 10.0;
		setPreferredSize(new Dimension((int) m_screenSizeX, (int) m_screenSizeY));
		buf = new BufferedImage((int) m_screenSizeX, (int) m_screenSizeY, BufferedImage.TYPE_INT_RGB);
	}

	public void setGFXSet(LinkedList<Ressource> gfxRessources) {
		HashMap<Integer, BufferedImage> gfxrsc = new HashMap<Integer, BufferedImage>();
		ListIterator<Ressource> it = gfxRessources.listIterator();
		while (it.hasNext()) {
			Ressource r = it.next();
			gfxrsc.put((Integer) r.getRessourceCode(), r.getRessource());
		}
		m_gfxRessources = gfxrsc;
	}

	public void blast(MapInfo mi, LinkedList<Sprite> sprites) {
		Graphics g = buf.getGraphics();
		// chargement de la carte
		int[][] map = mi.getMap();
		Rectangle r = mi.getRectToBlast();
		Point tileDim = mi.getTileDimension();
		Point offSet = mi.getOffset();
		Point[] idI = mi.getIdInfo();
		
		for (int x = 0; x < r.width; x++) {
			for (int y = 0; y < r.height; y++) {
				g.drawImage(
						m_gfxRessources.get(MAP_CODE).getSubimage(idI[map[y+r.y][r.x+x]].x, idI[map[y+r.y][r.x+x]].y, tileDim.x, tileDim.y),
						(int) ((x * tileDim.x+offSet.x) *m_screenZoom / 10.0),
						(int) ((y * tileDim.y+offSet.y) *m_screenZoom / 10.0),
						(int) (tileDim.x * m_screenZoom / 10.0),
						(int) (tileDim.y * m_screenZoom / 10.0), this);
			}
		}

		// chargement des sprites
		ListIterator<Sprite> it = sprites.listIterator();
		while (it.hasNext()) {
			Sprite tempSprite = it.next();
			g.drawImage(
					m_gfxRessources.get(tempSprite.getGfxRessource()).getSubimage(tempSprite.getPosxInGraphicSet(),
							tempSprite.getPosyInGraphicSet(), tempSprite.getSizex(), tempSprite.getSizey()),
					(int) (tempSprite.getPosx() * m_screenZoom / 10.0),
					(int) (tempSprite.getPosy() * m_screenZoom / 10.0),
					(int) (tempSprite.getSizex() * m_screenZoom / 10.0),
					(int) (tempSprite.getSizey() * m_screenZoom / 10.0), this);
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
		m_screenSizeX = NB_TILE_X * TILE_SIZE * m_screenZoom / 10.0;
		m_screenSizeY = NB_TILE_Y * TILE_SIZE * m_screenZoom / 10.0;
		setPreferredSize(new Dimension((int) m_screenSizeX, (int) m_screenSizeY));
		buf = new BufferedImage((int) m_screenSizeX, (int) m_screenSizeY, BufferedImage.TYPE_INT_RGB);
	}

}
