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
	private ArrayList<CBullet> m_bullets;
	private ArrayList<CEnemy> m_enemies;
	private ArrayList<CExplosion> m_explosions;
	private ArrayList<CText> m_texts;
	private int m_count;
	private int m_score;

	CGame() {
		m_score = 0;
		m_status = new CFlag();
		m_bullets = new ArrayList<CBullet>();
		m_enemies = new ArrayList<CEnemy>();
		m_explosions = new ArrayList<CExplosion>();
		m_texts = new ArrayList<CText>();
		m_player = new CPlayer();
		startingNewGame();
	}

	private void startingNewGame() {
		m_count = 0;
		m_status.bitClr(GAME_STATUS_MSK);
		m_status.bitSet(GAME_STATUS_STARTING);
		m_player.resetPlayer();
	}

	private void proccEntities(CFlag kbStatus) {
		// procc joueur
		m_player.procc(kbStatus);

		// procc bullets
		for (int i = 0; i < m_bullets.size() && i >= 0; i++) {
			m_bullets.get(i).procc();
			if (m_bullets.get(i).isOutOfBound())
				m_bullets.remove(i--);
		}

		// procc explosion
		for (int i = 0; i < m_explosions.size(); i++) {
			m_explosions.get(i).procc();
			if (m_explosions.get(i).isDead())
				m_explosions.remove(i--);
		}

		// procc asteroid
		for (int i = 0; i < m_enemies.size() && i >= 0; i++) {
			m_enemies.get(i).procc();

			// collision avec les autres asteroids
			for (int j = i + 1; j < m_enemies.size(); j++) {
				// collision
				if (circlesIsInCollision(m_enemies.get(i).getPosx(), m_enemies.get(i).getPosy(),
						m_enemies.get(j).getPosx(), m_enemies.get(j).getPosy(), CEnemy.RANGE, CEnemy.RANGE)) {
					// change les vecteurs
					int temp;
					temp = m_enemies.get(i).getSpeedx();
					m_enemies.get(i).setSpeedx(m_enemies.get(j).getSpeedx());
					m_enemies.get(j).setSpeedx(temp);

					temp = m_enemies.get(i).getSpeedy();
					m_enemies.get(i).setSpeedy(m_enemies.get(j).getSpeedy());
					m_enemies.get(j).setSpeedy(temp);

					// bouge les asteroides pour éviter les bugs
					double distance = (int) Math.sqrt((m_enemies.get(i).getPosx() - m_enemies.get(j).getPosx())
							* (m_enemies.get(i).getPosx() - m_enemies.get(j).getPosx())
							+ (m_enemies.get(i).getPosy() - m_enemies.get(j).getPosy())
									* (m_enemies.get(i).getPosy() - m_enemies.get(j).getPosy()))
							+ 2;
					double overlap = (distance - CEnemy.RANGE * 2 - COUNTER_COLLISION_BUG) / 2;
					// déplacement asteroid j
					m_enemies.get(i).setPosx((int) (m_enemies.get(i).getPosx()
							- overlap * (m_enemies.get(i).getPosx() - m_enemies.get(j).getPosx()) / distance));
					m_enemies.get(i).setPosy((int) (m_enemies.get(i).getPosy()
							- overlap * (m_enemies.get(i).getPosy() - m_enemies.get(j).getPosy()) / distance));
					// déplacement asteroid j
					m_enemies.get(j).setPosx((int) (m_enemies.get(j).getPosx()
							+ overlap * (m_enemies.get(i).getPosx() - m_enemies.get(j).getPosx()) / distance));
					m_enemies.get(j).setPosy((int) (m_enemies.get(j).getPosy()
							+ overlap * (m_enemies.get(i).getPosy() - m_enemies.get(j).getPosy()) / distance));
				}

			}

			// collision avec le joueur
			if (circlesIsInCollision(m_enemies.get(i).getPosx(), m_enemies.get(i).getPosy(), m_player.getPosx(),
					m_player.getPosy(), CEnemy.RANGE, CPlayer.HITBOX)) {
				m_explosions.add(new CExplosion(m_enemies.get(i).getPosx(), m_enemies.get(i).getPosy()));
				m_enemies.remove(i--);

				m_player.looseHp(CEnemy.DAMAGE);
				if (m_player.getLife() == 0) {
					m_explosions.add(new CExplosion(m_player.getPosx(), m_player.getPosy()));
					m_status.bitSet(GAME_STATUS_LOST);
					startingNewGame();
					for (int k = 0; k < m_enemies.size(); k++) {
						m_explosions.add(new CExplosion(m_enemies.get(k).getPosx(), m_enemies.get(k).getPosy()));
					}
					m_enemies.clear();
					return;
				}
				if (i < 0)
					i = 0;
			}

			if (m_enemies.get(i).isDead()) {
				m_explosions.add(new CExplosion(m_enemies.get(i).getPosx(), m_enemies.get(i).getPosy()));
				m_enemies.remove(i--);
				m_score++;
				if (i < 0)
					i = 0;
			}
			if (m_enemies.get(i).isOutOfBound())
				m_enemies.remove(i--);

		}

		// spawn enemis
		if (m_status.isBitSet(GAME_STATUS_RUNNING) && m_count % SPAWN_ENEMY_RATE == SPAWN_ENEMY_RATE - 1) {
			// spawn des i asteroids
			for (int i = 0; i < MAX_SPAWN_ASTEROID; i++) {
				m_enemies.add(new CEnemy());
			}
		}

		// collision avec les bullets et les asteroids
		for (int i = 0; i < m_bullets.size(); i++) {
			for (int j = 0; j < m_enemies.size() && m_bullets.size() > 0 && i >= 0; j++) {
				if (circlesIsInCollision(m_bullets.get(i).getPosx(), m_bullets.get(i).getPosy(),
						m_enemies.get(j).getPosx(), m_enemies.get(j).getPosy(), CBullet.RANGE, CEnemy.RANGE)) {
					m_enemies.get(j).looseHP(CBullet.DAMAGE);
					m_bullets.remove(i--);

				}
			}
		}

		if (m_status.isBitSet(GAME_STATUS_STARTING))
			return;
		// Tire
		if (m_player.isShooting(kbStatus))
			m_bullets.add(new CBullet(m_player.getPosx(), m_player.getPosy() - CGraphics.TILE_SIZE / 2,
					m_player.getSpeedx(), m_player.getSpeedy(), true));

	}

	public void proccGame(CFlag kbStatus) {
		// pas touche
		m_texts.clear();
		////////

		if (m_status.isBitSet(GAME_STATUS_PAUSE)) {
			// PAUSE
			m_texts.add(
					new CText(PAUSE_STRING_TO_BLASE, PAUSE_POSX, (CGraphics.NB_TILE_Y / 2 - 1) * CGraphics.TILE_SIZE));

		} else if (m_status.isBitSet(GAME_STATUS_RUNNING)) {
			// RUNNING
			proccEntities(kbStatus);

		} else if (m_status.isBitSet(GAME_STATUS_STARTING)) {
			// STARTING
			Integer iTemp = 3 - m_count / 59;
			proccEntities(kbStatus);
			if (iTemp == 0) {
				m_score = 0;
				m_status.bitClr(GAME_STATUS_MSK);
				m_status.bitSet(GAME_STATUS_RUNNING);
			} else {
				// ajout du compte à rebours à l'écran
				m_texts.add(new CText(COUNTDOWN_TEXT, COUNTDOWN_TEXT_POSX, COUNTDOWN_POSY - 32));
				m_texts.add(new CText(iTemp.toString(), COUNTDOWN_POSX, COUNTDOWN_POSY));
			}
		}

		// ajout du score à l'écran (permanant)
		m_texts.add(new CText(String.format(SCORE_FORMAT, m_score), SCORE_POSX, 0));

		// PAS TOUCHE
		m_count++;
		//////
	}

	public CPlayer getPlayer() {
		return m_player;
	}

	public ArrayList<CSprite> getSpritesToBlast() {
		ArrayList<CSprite> m_sprites = new ArrayList<CSprite>();

		// chargement des bullets
		for (int i = 0; i < m_bullets.size(); i++) {
			CBullet tempBullet = m_bullets.get(i);
			m_sprites.add(new CSprite(tempBullet.getPosx(), tempBullet.getPosy(), tempBullet.getPosxInSet(),
					tempBullet.getPosyInSet(), CGraphics.TILESET_CODE_SPRITE));
		}

		// chargement des ennemies
		for (int i = 0; i < m_enemies.size(); i++) {
			CEnemy tempEnemy = m_enemies.get(i);
			m_sprites.add(new CSprite(tempEnemy.getPosx(), tempEnemy.getPosy(), tempEnemy.getPosxInSet(),
					tempEnemy.getPosyInSet(), CGraphics.TILESET_CODE_SPRITE));
		}

		// chargement des explosions
		for (int i = 0; i < m_explosions.size(); i++) {
			CExplosion tempExplosion = m_explosions.get(i);
			m_sprites.add(new CSprite(tempExplosion.getPosx(), tempExplosion.getPosy(), tempExplosion.getPosxInSet(),
					tempExplosion.getPosyInSet(), CGraphics.TILESET_CODE_SPRITE));
		}

		// chargement du perso
		if (m_status.isBitClr(GAME_STATUS_LOST) || m_status.isBitSet(GAME_STATUS_STARTING))
			m_sprites.add(new CSprite(m_player.getPosx(), m_player.getPosy(), m_player.getCharPosxInSet(),
					m_player.getCharPosyInSet(), CGraphics.TILESET_CODE_SPRITE));

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

	public void pressEscape() {
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