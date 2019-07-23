import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class CApp extends JFrame implements KeyListener {
	static final String ICON_PATH = "gfx/ship.png";
	
	private ImageIcon m_icon;
	private CFlag m_kbStatus;
	private CGraphics m_graphics;
	private CGame m_game;
	private Thread m_thread;

	CApp() {
		m_icon = new ImageIcon(ICON_PATH);
		setIconImage(m_icon.getImage());
		m_kbStatus = new CFlag();
		m_graphics = new CGraphics();
		m_game = new CGame();
		setTitle("Mega Jeu de l'univers");
		add(m_graphics);
		pack();
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		addKeyListener(this);

		// THREAD GAME
		m_thread = new Thread() {
			public void run() {
				while (true) {
					//if(m_game.isRunning())
						m_game.proccGame(m_kbStatus);

					// affichage
					blast();

					// WAIT
					try {

						Thread.sleep(16);

					} catch (InterruptedException ex) {

					}
				}
			};

		};

		m_thread.start();

	}

	void blast() {
		// blast
		m_graphics.blastSprites(m_game.getSpritesToBlast());
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
			m_game.pressEscape();

		switch (e.getKeyCode()) {
		case KeyEvent.VK_Z: // UP
			m_kbStatus.bitSet(CPlayer.KB_PLAYER_UP);
			break;

		case KeyEvent.VK_D: // RIGHT
			m_kbStatus.bitSet(CPlayer.KB_PLAYER_RIGHT);
			break;

		case KeyEvent.VK_S: // DOWN
			m_kbStatus.bitSet(CPlayer.KB_PLAYER_DOWN);
			break;

		case KeyEvent.VK_Q: // LEFT
			m_kbStatus.bitSet(CPlayer.KB_PLAYER_LEFT);
			break;

		case KeyEvent.VK_SPACE: // SHOT
			m_kbStatus.bitSet(CPlayer.KB_PLAYER_SHOT);
			break;

		default:
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_Z: // UP
			m_kbStatus.bitClr(CPlayer.KB_PLAYER_UP);
			break;

		case KeyEvent.VK_D: // RIGHT
			m_kbStatus.bitClr(CPlayer.KB_PLAYER_RIGHT);
			break;

		case KeyEvent.VK_S: // DOWN
			m_kbStatus.bitClr(CPlayer.KB_PLAYER_DOWN);
			break;

		case KeyEvent.VK_Q: // LEFT
			m_kbStatus.bitClr(CPlayer.KB_PLAYER_LEFT);
			break;

		case KeyEvent.VK_SPACE: // SHOT
			m_kbStatus.bitClr(CPlayer.KB_PLAYER_SHOT);
			break;

		default:
			break;
		}
	}
}
