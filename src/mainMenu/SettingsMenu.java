package mainMenu;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import application.CApp;
import application.CFlag;
import application.CGraphics;
import application.CScene;
import application.CSprite;
import application.ConMenu;

public class SettingsMenu extends CScene {
	static final String FONT_PATH = "gfx/font/pen.png";
	static final String BKG_PATH = "gfx/levels/mainMenu/mainMenu.png";

	static final int MENU_CHANGE_ZOOM = 0;
	static final int MENU_QUIT = 1;
	static final int STATUS_QUIT = 0b1;

	private ConMenu m_menu;
	private CFlag m_status;

	public SettingsMenu() {
		m_status = new CFlag();
		ArrayList<String> choices = new ArrayList<String>();
		choices.add("zoom x" + CApp.getCGraphics().getScreenZoom() / 10.0);
		choices.add("quit");
		m_menu = new ConMenu(choices, ConMenu.SHOW_ALL, ConMenu.ALIGN_CENTER, ConMenu.ALIGN_CENTER,
				ConMenu.ALIGN_CENTER);
	}

	@Override
	public CScene procc() {
		if (m_status.isBitSet(STATUS_QUIT) || m_menu.getSelectedChoice() == MENU_QUIT)
			return new MainMenu();
		m_menu.setTextAt(MENU_CHANGE_ZOOM, "zoom x" + CApp.getCGraphics().getScreenZoom() / 10.0);
		return this;
	}

	@Override
	public ArrayList<CSprite> getSpritesToBlast() {
		return m_menu.getSprites();
	}

	@Override
	public String getSpritePath() {
		return null;
	}

	@Override
	public String getFontPath() {
		return FONT_PATH;
	}

	@Override
	public String getBkgPath() {
		return BKG_PATH;
	}

	@Override
	public void clickKey(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_RIGHT:
			if (CApp.getCGraphics().getScreenZoom() < CGraphics.MAX_ZOOM
					&& m_menu.getCurrentChoice() == MENU_CHANGE_ZOOM)
				CApp.setScreenZoom((int) (CApp.getCGraphics().getScreenZoom() + 1));
			break;

		case KeyEvent.VK_LEFT:
			if (CApp.getCGraphics().getScreenZoom() > CGraphics.MIN_ZOOM
					&& m_menu.getCurrentChoice() == MENU_CHANGE_ZOOM)
				CApp.setScreenZoom((int) (CApp.getCGraphics().getScreenZoom() - 1));
			break;
			
		case KeyEvent.VK_D:
			if (CApp.getCGraphics().getScreenZoom() < CGraphics.MAX_ZOOM
					&& m_menu.getCurrentChoice() == MENU_CHANGE_ZOOM)
				CApp.setScreenZoom((int) (CApp.getCGraphics().getScreenZoom() + 1));
			break;

		case KeyEvent.VK_Q:
			if (CApp.getCGraphics().getScreenZoom() > CGraphics.MIN_ZOOM
					&& m_menu.getCurrentChoice() == MENU_CHANGE_ZOOM)
				CApp.setScreenZoom((int) (CApp.getCGraphics().getScreenZoom() - 1));
			break;

		case KeyEvent.VK_ENTER:
			m_menu.validChoice();
			break;

		case KeyEvent.VK_ESCAPE:
			m_status.bitSet(STATUS_QUIT);
			break;

		case KeyEvent.VK_DOWN:
			m_menu.goDown();
			break;

		case KeyEvent.VK_S:
			m_menu.goDown();
			break;

		case KeyEvent.VK_UP:
			m_menu.goUp();
			break;

		case KeyEvent.VK_Z:
			m_menu.goUp();
			break;

		default:
			break;
		}

	}
}
