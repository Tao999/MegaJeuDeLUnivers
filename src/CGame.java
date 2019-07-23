import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class CGame {
	public final static int GAME_STATUS_MSK = 0b11;
	public final static int RUNNING = 0b01;
	public final static int PAUSE = 0b10;
	public final static int LOST = 0b11;

	public final static int MAX_SPAWN_ASTEROID = 2;
	public final static int SPAWN_SMOKE_RATE = 4;
	public final static int SPAWN_ENEMY_RATE = 8;
	public final static int COUNTER_COLLISION_BUG = 3;
	public final static int NB_SCORE_TO_BLAST = 3;

	static final String NUMERICS_PATH = "gfx/numerics.png";

	private CFlag m_status;
	private CPlayer m_player;
	private ArrayList<CBullet> m_bullets;
	private ArrayList<CSmoke> m_smokes;
	private ArrayList<CEnemy> m_enemies;
	private ArrayList<CExplosion> m_explosions;
	private int m_count;
	private int m_score;
	private BufferedImage m_numericsImg;

	CGame() {
		m_status = new CFlag();
		m_status.bitSet(RUNNING);
		m_count = 0;
		m_bullets = new ArrayList<CBullet>();
		m_smokes = new ArrayList<CSmoke>();
		m_enemies = new ArrayList<CEnemy>();
		m_explosions = new ArrayList<CExplosion>();

		m_player = new CPlayer();

		try {
			m_numericsImg = ImageIO.read(new File(NUMERICS_PATH));
		} catch (IOException e) {
			m_numericsImg = new BufferedImage(CGraphics.TILE_SIZE, CGraphics.TILE_SIZE, BufferedImage.TYPE_INT_RGB);
		}
	}

	public void proccGame(CFlag kbStatus) {
		
		for (int i = 0; i < m_smokes.size() && i >= 0; i++) {
			m_smokes.get(i).procc();
			if (m_smokes.get(i).isOutOfBound())
				m_smokes.remove(i--);
		}

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
					double overlap = (distance - CGraphics.TILE_SIZE - COUNTER_COLLISION_BUG) / 2;
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
					m_status.bitSet(LOST);
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
		if (m_status.getMsk(GAME_STATUS_MSK) != RUNNING) {
			for (int i = 0; i < m_enemies.size(); i++) {
				m_explosions.add(new CExplosion(m_enemies.get(i).getPosx(), m_enemies.get(i).getPosy()));
			}
			m_enemies.clear();
			return;
		}

		// fumée
		if (m_count % SPAWN_SMOKE_RATE == 0)
			m_smokes.add(new CSmoke(m_player.getPosx(), m_player.getPosy() + CGraphics.TILE_SIZE / 3));

		// spawn enemis
		if (m_count % SPAWN_ENEMY_RATE == 0) {
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

		// Tire
		if (m_player.isShooting(kbStatus))
			m_bullets.add(new CBullet(m_player.getPosx(), m_player.getPosy() - CGraphics.TILE_SIZE / 2,
					m_player.getSpeedx(), m_player.getSpeedy(), true));

		// PAS TOUCHE
		m_count++;
	}

	public CPlayer getPlayer() {
		return m_player;
	}

	public ArrayList<CSprite> getSpritesToBlast() {
		ArrayList<CSprite> m_sprites = new ArrayList<CSprite>();
		// chargement de la fumé
		for (int i = 0; i < m_smokes.size(); i++) {
			CSmoke tempSmoke = m_smokes.get(i);
			m_sprites.add(new CSprite(tempSmoke.getImg(), tempSmoke.getPosx(), tempSmoke.getPosy()));
		}

		// chargement des bullets
		for (int i = 0; i < m_bullets.size(); i++) {
			CBullet tempBullet = m_bullets.get(i);
			m_sprites.add(new CSprite(tempBullet.getImg(), tempBullet.getPosx(), tempBullet.getPosy()));
		}

		// chargement des ennemies
		for (int i = 0; i < m_enemies.size(); i++) {
			CEnemy tempEnemy = m_enemies.get(i);
			m_sprites.add(new CSprite(tempEnemy.getImg(), tempEnemy.getPosx(), tempEnemy.getPosy()));
		}

		// chargement des explosions
		for (int i = 0; i < m_explosions.size(); i++) {
			CExplosion tempExplosion = m_explosions.get(i);
			m_sprites.add(new CSprite(tempExplosion.getImg(), tempExplosion.getPosx(), tempExplosion.getPosy()));
		}

		// chargement du perso
		if (m_status.getMsk(GAME_STATUS_MSK) == RUNNING)
			m_sprites.add(new CSprite(m_player.getImg(), m_player.getPosx(), m_player.getPosy()));

		// chargement de la barre de vie
		for (int i = 0; i < CPlayer.PLAYER_LIFE_MAX; i++) {
			m_sprites.add(new CSprite(m_player.getLifePoint(m_player.getLife() / (i + 1) >= 1), i * CGraphics.TILE_SIZE,
					(CGraphics.NB_TILE_Y - 1) * CGraphics.TILE_SIZE));
		}

		// chargement du score
		for (int i = 0; i < NB_SCORE_TO_BLAST; i++) {
			m_sprites.add(new CSprite(getNumericScore((int) (m_score / Math.pow(10, i))),
					(int) (CGraphics.NB_TILE_X / 2 - 2) * CGraphics.TILE_SIZE
							+ (NB_SCORE_TO_BLAST - i) * CGraphics.TILE_SIZE,
					0));
		}

		return m_sprites;
	}

	public boolean isRunning() {
		if (m_status.getMsk(GAME_STATUS_MSK) == RUNNING)
			return true;
		return false;
	}

	public void pressEscape() {
		if (m_status.getMsk(GAME_STATUS_MSK) == RUNNING) {
			m_player.setSpeedx(0);
			m_player.setSpeedy(0);
			m_status.bitClr(GAME_STATUS_MSK);
			m_status.bitSet(PAUSE);
		} else if (m_status.getMsk(GAME_STATUS_MSK) == PAUSE) {
			m_status.bitClr(GAME_STATUS_MSK);
			m_status.bitSet(RUNNING);
		}
	}

	public boolean circlesIsInCollision(int ax, int ay, int bx, int by, int aRadius, int bRadius) {
		if (((ax - bx) * (ax - bx)) + ((ay - by) * (ay - by)) <= (aRadius + bRadius) * (aRadius + bRadius))
			return true;
		return false;
	}

	public BufferedImage getNumericScore(int i) {
		return m_numericsImg.getSubimage(i % 10 * CGraphics.TILE_SIZE, 0, CGraphics.TILE_SIZE, CGraphics.TILE_SIZE);
	}
}