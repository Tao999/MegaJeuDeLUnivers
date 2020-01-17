package simpleScene;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

import application.CGraphics;
import application.Flag;
//import application.GameObject;
import application.MapInfo;
import application.Ressource;
import application.Scene;
import application.Sprite;

public class SimpleScene implements Scene {
	public static final int FLAG_APP_RUNING = 0b01;
	public static final int FLAG_APP_PAUSING = 0b10;

	// public static final int BKG_RESSOURCE_NAME = 0;
	public static final String BKG_RESSOURCE_PATH = "gfx/levels/asteroid/tileSet2.png";

	public static final int TILE_SET_RESSOURCE_NAME = 1;
	public static final String TILE_SET_RESSOURCE_PATH = "gfx/levels/asteroid/tileSet.png";

	public static final int TEXT_RESSOURCE_NAME = 2;
	public static final String TEXT_RESSOURCE_PATH = "gfx/font/pen.png";

	private Player m_player;
	private Flag m_status;
	private int[][] m_map;
	// private LinkedList<GameObject> m_gameObjects;

	public SimpleScene() {
		m_map = new int[][] {
			{ 6, 7, 7 , 7, 7, 7, 8},
			{ 9, 1, 0 , 0, 0, 0, 11},
			{ 9, 1, 0 , 0, 0, 0, 11},
			{ 9, 1, 0 , 0, 0, 0, 11},
			{ 9, 1, 0 , 0, 0, 0, 11},
			{ 12, 13, 13, 13, 13, 13, 14},
			};
		m_player = new Player();
		m_status = new Flag();
		// m_gameObjects = new LinkedList<GameObject>();
		m_status.bitSet(FLAG_APP_RUNING);
	}

	@Override
	public Scene procc() {
		if (m_status.isBitSet(FLAG_APP_RUNING)) {
			// l'application est en marche
			m_player.procc();
		} else if (m_status.isBitSet(FLAG_APP_PAUSING)) {
			// l'application est en pause
		}
		return this;
	}

	@Override
	public LinkedList<Sprite> getSpritesToBlast() {
		LinkedList<Sprite> sprites = new LinkedList<Sprite>();
		// chargement du bkg
		// sprites.add(new Sprite(0, 0, 0, 0, CGraphics.SCREEN_SIZE_X,
		// CGraphics.SCREEN_SIZE_Y, BKG_RESSOURCE_NAME));

		// chargement du joueur
		sprites.add(new Sprite(m_player.getPosx(), m_player.getPosy(), m_player.getPosxInSet(), m_player.getPosyInSet(),
				Player.SPRITE_SIZE, Player.SPRITE_SIZE, TILE_SET_RESSOURCE_NAME));

		return sprites;
	}

	@Override
	public LinkedList<Ressource> getGfxRessources() {
		LinkedList<Ressource> ressources = new LinkedList<Ressource>();
		ressources.add(new Ressource(CGraphics.MAP_CODE, BKG_RESSOURCE_PATH));
		ressources.add(new Ressource(TILE_SET_RESSOURCE_NAME, TILE_SET_RESSOURCE_PATH));
		ressources.add(new Ressource(TEXT_RESSOURCE_NAME, TEXT_RESSOURCE_PATH));
		return ressources;
	}

	@Override
	public MapInfo getMapInfo() {
		Point[] p = new Point[] {
				new Point(0, 0),
				new Point(32, 0),
				new Point(64, 0),
				new Point(0, 32),
				new Point(32, 32),
				new Point(64, 32),
				new Point(0, 64),
				new Point(32, 64),
				new Point(64, 64),
				new Point(0, 96),
				new Point(32, 96),
				new Point(64, 96),
				new Point(0, 128),
				new Point(32, 128),
				new Point(64, 128),
				};
		return new MapInfo(m_map, new Rectangle(0, 0, 7, 6), new Point(32, 32), new Point(0, 0), p);
	}

	@Override
	public void clickKey(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
