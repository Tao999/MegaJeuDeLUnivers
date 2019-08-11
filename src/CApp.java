import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class CApp extends JFrame implements KeyListener {
	static final String ICON_PATH = "gfx/ship.png";
	static final long PROCC_REVOVERY = 16;

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
		m_graphics.setGFXSet(CGame.GFXSET_PATH);
		setTitle("Mega Jeu de l'univers | ?? fps");
		add(m_graphics);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		addKeyListener(this);
		System.setProperty("sun.java2d.opengl", "True");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pack();

		// THREAD GAME
		m_thread = new Thread() {
			public void run() {
				long timeRegulator;
				long fpsCheck=0;
				int fpsCount=0;
				int latencySum=0;
				
				while (true) {
					
					fpsCheck = timeRegulator = System.currentTimeMillis();
					// if(m_game.isRunning())
					m_game.proccGame(m_kbStatus);

					// affichage
					blast();

					// WAIT
					timeRegulator = System.currentTimeMillis() - timeRegulator;
					if (timeRegulator > PROCC_REVOVERY)
						timeRegulator = 0;
					else
						timeRegulator = PROCC_REVOVERY - timeRegulator;

					try {

						Thread.sleep(timeRegulator);

					} catch (InterruptedException ex) {

					}
					
					fpsCheck = System.currentTimeMillis()-fpsCheck;
					latencySum+=fpsCheck;
					if(fpsCount++ == 30) {
						fpsCount=0;
						setTitle("Mega Jeu de l'univers | " + (int)(1000/(latencySum/30.0)) + " fps");
						latencySum=0;
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
