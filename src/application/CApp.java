package application;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import simpleScene.SimpleScene;

@SuppressWarnings("serial")
public class CApp extends JFrame implements KeyListener {
	static final String ICON_PATH = "gfx/icon.png";
	static final long PROCC_REVOVERY = 16;

	public static final int KB_UP = 0b0000000001;
	public static final int KB_RIGHT = 0b0000000010;
	public static final int KB_DOWN = 0b0000000100;
	public static final int KB_LEFT = 0b0000001000;
	public static final int KB_Z = 0b0000010000;
	public static final int KB_D = 0b0000100000;
	public static final int KB_S = 0b0001000000;
	public static final int KB_Q = 0b0010000000;
	public static final int KB_ESCAPE = 0b0100000000;
	public static final int KB_SPACE = 0b1000000000;

	private ImageIcon m_icon;
	private static volatile Flag m_kbStatus;
	private static CGraphics m_graphics;
	private Scene m_scene;
	private Thread m_thread;

	private static CApp app;

	CApp() {
		app = this;
		m_icon = new ImageIcon(ICON_PATH);
		setIconImage(m_icon.getImage());
		m_kbStatus = new Flag();
		m_graphics = new CGraphics();
		m_scene = new SimpleScene();
		m_graphics.setGFXSet(m_scene.getGfxRessources());
		setTitle("RSA engine");
		add(m_graphics);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		addKeyListener(this);
		/*System.setProperty("sun.java2d.opengl", "True");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}*/
		pack();

		// THREAD GAME
		m_thread = new Thread() {
			public void run() {
				long timeRegulator;

				Scene sceneChecker = m_scene;

				while (true) {
					timeRegulator = System.currentTimeMillis();
					m_scene = m_scene.procc();
					if (sceneChecker != m_scene) {
						m_graphics.setGFXSet(m_scene.getGfxRessources());
						sceneChecker = m_scene;
					}

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
				}
			};

		};

	}

	public static void setScreenZoom(int zoom) {
		m_graphics.setScreenZoom(zoom);
		app.pack();
	}

	void blast() {
		// blast
		m_graphics.blast(m_scene.getMapInfo(), m_scene.getSpritesToBlast());
	}

	public static Flag getKbStatus() {
		return m_kbStatus;
	}

	public static CGraphics getCGraphics() {
		return m_graphics;
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		m_scene.clickKey(e);

		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP: // UP
			m_kbStatus.bitSet(KB_UP);
			break;

		case KeyEvent.VK_RIGHT: // RIGHT
			m_kbStatus.bitSet(KB_RIGHT);
			break;

		case KeyEvent.VK_DOWN: // DOWN
			m_kbStatus.bitSet(KB_DOWN);
			break;

		case KeyEvent.VK_LEFT: // LEFT
			m_kbStatus.bitSet(KB_LEFT);
			break;

		case KeyEvent.VK_Z: // z
			m_kbStatus.bitSet(KB_Z);
			break;

		case KeyEvent.VK_D: // d
			m_kbStatus.bitSet(KB_D);
			break;

		case KeyEvent.VK_S: // s
			m_kbStatus.bitSet(KB_S);
			break;

		case KeyEvent.VK_Q: // q
			m_kbStatus.bitSet(KB_Q);
			break;

		case KeyEvent.VK_SPACE: // space
			m_kbStatus.bitSet(KB_SPACE);
			break;

		case KeyEvent.VK_ESCAPE:
			m_kbStatus.bitSet(KB_ESCAPE);
			break;

		default:
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP: // UP
			m_kbStatus.bitClr(KB_UP);
			break;

		case KeyEvent.VK_RIGHT: // RIGHT
			m_kbStatus.bitClr(KB_RIGHT);
			break;

		case KeyEvent.VK_DOWN: // DOWN
			m_kbStatus.bitClr(KB_DOWN);
			break;

		case KeyEvent.VK_LEFT: // LEFT
			m_kbStatus.bitClr(KB_LEFT);
			break;

		case KeyEvent.VK_Z: // z
			m_kbStatus.bitClr(KB_Z);
			break;

		case KeyEvent.VK_D: // d
			m_kbStatus.bitClr(KB_D);
			break;

		case KeyEvent.VK_S: // s
			m_kbStatus.bitClr(KB_S);
			break;

		case KeyEvent.VK_Q: // q
			m_kbStatus.bitClr(KB_Q);
			break;

		case KeyEvent.VK_SPACE: // space
			m_kbStatus.bitClr(KB_SPACE);
			break;

		case KeyEvent.VK_ESCAPE:
			m_kbStatus.bitClr(KB_ESCAPE);
			break;

		default:
			break;
		}
	}
	public void start() {
		m_thread.start();
	}
}
