import java.util.ArrayList;

public class CGame {
	public final static int GAME_STATUS_MSK = 0b1111;
	public final static int GAME_STATUS_RUNNING = 0b0001;
	public final static int GAME_STATUS_PAUSE = 0b0010;
	public final static int GAME_STATUS_LOST = 0b0100;
	public final static int GAME_STATUS_STARTING = 0b1000;

	public final static int MAX_SPAWN_ASTEROID = 2;
	public final static int SPAWN_ENEMY_RATE = 11;
	public final static int COUNTER_COLLISION_BUG = 3;
	public final static int NB_SCORE_TO_BLAST = 5;
	public final static int SCORE_POSX = (CGraphics.NB_TILE_X / 2 - NB_SCORE_TO_BLAST / 2) * CGraphics.TILE_SIZE;
	public final static String SCORE_FORMAT = "%0" + NB_SCORE_TO_BLAST + "d";

	public final static String COUNTDOWN_TEXT = "ready?";
	public final static int COUNTDOWN_POSX = CGraphics.TILE_SIZE * (CGraphics.NB_TILE_X - 1) / 2;
	public final static int COUNTDOWN_POSY = CGraphics.TILE_SIZE * (CGraphics.NB_TILE_Y - 1) / 2;
	public final static int COUNTDOWN_TEXT_POSX = (int) (CGraphics.TILE_SIZE * (CGraphics.NB_TILE_X) / 2
			- (COUNTDOWN_TEXT.length() / 2.0) * CGraphics.TILE_SIZE);

	public final static String PAUSE_STRING_TO_BLASE = "pause";
	public final static int PAUSE_POSX = (int) (CGraphics.TILE_SIZE * (CGraphics.NB_TILE_X) / 2
			- (PAUSE_STRING_TO_BLASE.length() / 2.0) * CGraphics.TILE_SIZE);

	public final static int POSX_NUMERIC = 96;
	public final static int POSY_NUMERIC = 32;

	static final String GFXSET_PATH = "gfx/GFXSet.png";
	static final String FONT_PATH = "gfx/font.png";

	private CFlag m_status;
	private CPlayer m_player;
	private ArrayList<CText> m_texts;
	private ArrayList<GameObject> m_gameObjects;
	private int m_count;
	private int m_score;

	CGame() {
		m_score = 0;
		m_status = new CFlag();
		m_texts = new ArrayList<CText>();
		m_player = new CPlayer();
		m_gameObjects = new ArrayList<GameObject>();

		startingNewGame();
	}

	private void startingNewGame() {
		for (int i = m_gameObjects.size() - 1; i >= 0; i--) {
			m_gameObjects.add(new CExplosion(m_gameObjects.get(i).getPosx(), m_gameObjects.get(i).getPosy()));
			m_gameObjects.remove(i);
		}
		m_count = 0;
		m_status.bitClr(GAME_STATUS_MSK);
		m_status.bitSet(GAME_STATUS_STARTING);
		m_player.resetPlayer();
	}

	private void proccEntities() {
		if (m_status.isBitSet(GAME_STATUS_RUNNING) && m_count % SPAWN_ENEMY_RATE == SPAWN_ENEMY_RATE - 1) {
			// spawn des i asteroids for (int
			for (int i = 0; i < MAX_SPAWN_ASTEROID; i++) {
				m_gameObjects.add(new CEnemy());
			}
		}

		// le joueur tire
		if (m_player.canShoot() && m_status.isBitSet(GAME_STATUS_RUNNING))
			m_gameObjects.add(new CBullet(m_player.getPosx(), m_player.getPosy() - CGraphics.TILE_SIZE / 2,
					m_player.getSpeedx(), m_player.getSpeedy(), true));

		// collision en relation avec les ennemies
		for (int i = 0; i < m_gameObjects.size(); i++) {
			GameObject tempObj = m_gameObjects.get(i);

			if (!tempObj.isDeadObject() && tempObj.getType() == GameObject.TYPE_ENEMY) {
				// on s'occupe des collision que si l'objet n'est pas mort
				// et si l'obj scanné est un ennemie...
				CEnemy tempEnemy = (CEnemy) tempObj;
				// collision de l'enemies avec le joueur
				if (circlesIsInCollision(tempEnemy.getPosx(), tempEnemy.getPosy(), m_player.getPosx(),
						m_player.getPosy(), CEnemy.RANGE, CPlayer.HITBOX)) {
					tempEnemy.markAsDeadObject();
					m_player.looseHp(CEnemy.DAMAGE);
				}

				// collision avec le reste des entités
				for (int j = i + 1; j < m_gameObjects.size(); j++) {
					GameObject tempObj2 = m_gameObjects.get(j);
					if (!tempObj2.isDeadObject()) {
						// on ne traite l'objet que si il est vivant
						switch (tempObj2.getType()) {

						// l'enemie touche une balle...
						case GameObject.TYPE_BULLET:
							CBullet tempBullet = (CBullet) tempObj2;
							if (circlesIsInCollision(tempEnemy.getPosx(), tempEnemy.getPosy(), tempBullet.getPosx(),
									tempBullet.getPosy(), CEnemy.RANGE, CBullet.RANGE)) {
								tempEnemy.looseHP(CEnemy.DAMAGE);
								tempBullet.markAsDeadObject();
								if (tempEnemy.isDeadObject())
									m_score++;
							}
							break;

						// l'enemie touche un autre enemie...
						case GameObject.TYPE_ENEMY:
							CEnemy tempEnemy2 = (CEnemy) tempObj2;
							if (circlesIsInCollision(tempEnemy.getPosx(), tempEnemy.getPosy(), tempEnemy2.getPosx(),
									tempEnemy2.getPosy(), CEnemy.RANGE, CEnemy.RANGE)) {
								// on échange les vecteurs
								int speedx = tempEnemy.getSpeedx();
								int speedy = tempEnemy.getSpeedy();

								tempEnemy.setSpeedx(tempEnemy2.getSpeedx());
								tempEnemy.setSpeedy(tempEnemy2.getSpeedy());

								tempEnemy2.setSpeedx(speedx);
								tempEnemy2.setSpeedy(speedy);
								// bouge les asteroides pour éviter les bugs
								double distance = (int) Math.sqrt((tempEnemy.getPosx() - tempEnemy2.getPosx())
										* (tempEnemy.getPosx() - tempEnemy2.getPosx())
										+ (tempEnemy.getPosy() - tempEnemy2.getPosy())
												* (tempEnemy.getPosy() - tempEnemy2.getPosy()))
										+ 2;
								double overlap = (distance - CEnemy.RANGE * 2 - COUNTER_COLLISION_BUG) / 2;
								// déplacement de tempEnemy
								tempEnemy.setPosx((int) (tempEnemy.getPosx()
										- overlap * (tempEnemy.getPosx() - tempEnemy2.getPosx()) / distance));
								tempEnemy.setPosy((int) (tempEnemy.getPosy()
										- overlap * (tempEnemy.getPosy() - tempEnemy2.getPosy()) / distance));
								// déplacement de tempEnemy2
								tempEnemy2.setPosx((int) (tempEnemy2.getPosx()
										+ overlap * (tempEnemy.getPosx() - tempEnemy2.getPosx()) / distance));
								tempEnemy2.setPosy((int) (tempEnemy2.getPosy()
										+ overlap * (tempEnemy.getPosy() - tempEnemy2.getPosy()) / distance));
							}
							break;

						default:
							break;
						}
					}
				}
			}

		}

		// mort du joueur
		if (m_player.isDeadObject()) {
			m_gameObjects.add(new CExplosion(m_player.getPosx(), m_player.getPosy()));
			startingNewGame();
		}

		// procc des entités
		for (int i = 0; i < m_gameObjects.size(); i++)
			m_gameObjects.get(i).procc();
		m_player.procc();
		m_count++;
		// on retir tous les objets morts
		for (int i = 0; i < m_gameObjects.size(); i++) {
			if (m_gameObjects.get(i).isDeadObject()) {
				if (m_gameObjects.get(i).getType() == GameObject.TYPE_ENEMY) {
					m_gameObjects.add(new CExplosion(m_gameObjects.get(i).getPosx(), m_gameObjects.get(i).getPosy()));
				}
				m_gameObjects.remove(i--);
				if (m_gameObjects.size() == 0)// si il n'y a plus d'objet, on part
					return;
				if (i < 0)
					i = 0;
			}
		}
	}

	private void proccPause() {
		m_texts.add(new CText(PAUSE_STRING_TO_BLASE, PAUSE_POSX, (CGraphics.NB_TILE_Y / 2 - 1) * CGraphics.TILE_SIZE));
	}

	private void proccStarting() {
		Integer iTemp = 3 - m_count / 59;
		proccEntities();
		if (iTemp == 0) {
			m_score = 0;
			m_status.bitClr(GAME_STATUS_MSK);
			m_status.bitSet(GAME_STATUS_RUNNING);
		} else {
			// ajout du compte à rebours à l'écran
			m_texts.add(new CText(COUNTDOWN_TEXT, COUNTDOWN_TEXT_POSX, COUNTDOWN_POSY - 32));
			m_texts.add(new CText(iTemp.toString(), COUNTDOWN_POSX, COUNTDOWN_POSY));
		}
		// m_count++;
	}

	public void proccGame() {
		// pas touche
		m_texts.clear();
		////////

		if (m_status.isBitSet(GAME_STATUS_PAUSE)) {
			// PAUSE
			proccPause();

		} else if (m_status.isBitSet(GAME_STATUS_RUNNING)) {
			// RUNNING
			proccEntities();

		} else if (m_status.isBitSet(GAME_STATUS_STARTING)) {
			// STARTING
			proccStarting();
		}

		// ajout du score à l'écran (permanant)
		m_texts.add(new CText(String.format(SCORE_FORMAT, m_score), SCORE_POSX, 0));

	}

	public CPlayer getPlayer() {
		return m_player;
	}

	public ArrayList<CSprite> getSpritesToBlast() {
		ArrayList<CSprite> m_sprites = new ArrayList<CSprite>();
		for (int i = 0; i < m_gameObjects.size(); i++) {
			GameObject tempObj = m_gameObjects.get(i);
			m_sprites.add(new CSprite(tempObj.getPosx(), tempObj.getPosy(), tempObj.getPosxInSet(),
					tempObj.getPosyInSet(), CGraphics.TILESET_CODE_SPRITE));
		}

		// chargement du perso
		if (m_status.isBitClr(GAME_STATUS_LOST) || m_status.isBitSet(GAME_STATUS_STARTING))
			m_sprites.add(new CSprite(m_player.getPosx(), m_player.getPosy(), m_player.getPosxInSet(),
					m_player.getPosyInSet(), CGraphics.TILESET_CODE_SPRITE));

		// chargement de la barre de vie
		for (int i = 0; i < CPlayer.PLAYER_LIFE_MAX; i++) {
			m_sprites.add(new CSprite(i * CGraphics.TILE_SIZE, (CGraphics.NB_TILE_Y - 1) * CGraphics.TILE_SIZE,
					m_player.getLifePosxInSet(m_player.getLife() / (i + 1) >= 1), m_player.getLifePosyInSet(),
					CGraphics.TILESET_CODE_SPRITE));
		}

		// chargement des texts
		for (int i = 0; i < m_texts.size(); i++) {
			for (int j = 0; j < m_texts.get(i).getText().length(); j++) {
				m_sprites.add(m_texts.get(i).getSpriteCharAt(j));
			}
		}

		return m_sprites;
	}

	public void clickEscape() {
		if (m_status.isBitClr(GAME_STATUS_PAUSE)) {
			m_player.setSpeedx(0);
			m_player.setSpeedy(0);
			m_status.bitSet(GAME_STATUS_PAUSE);
		} else if (m_status.isBitSet(GAME_STATUS_PAUSE)) {
			m_status.bitClr(GAME_STATUS_PAUSE);
		}
	}

	public boolean circlesIsInCollision(int ax, int ay, int bx, int by, int aRadius, int bRadius) {
		if (((ax - bx) * (ax - bx)) + ((ay - by) * (ay - by)) <= (aRadius + bRadius) * (aRadius + bRadius))
			return true;
		return false;
	}
}