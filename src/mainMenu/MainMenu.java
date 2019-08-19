package mainMenu;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import application.CScene;
import application.CSprite;
import application.CText;
import application.ConMenu;
import lvlAsteroid.LvlAsteroid;

public class MainMenu extends CScene {
	static final String FONT_PATH = "gfx/font/pen.png";
	static final String BKG_PATH = "gfx/levels/mainMenu/mainMenu.png";

	static final int MENU_PLAY_GAME = 0;
	static final int MENU_HOW_TO_PLAY = 1;
	static final int MENU_SETTINGS = 2;
	static final int MENU_QUIT_GAME = 3;

	private ConMenu m_menu;

	public MainMenu() {
		ArrayList<String> choices = new ArrayList<String>();
		choices.add("play");
		choices.add("controls");
		choices.add("settings");
		choices.add("quit");
		m_menu = new ConMenu(choices, ConMenu.SHOW_ALL, ConMenu.ALIGN_CENTER, ConMenu.ALIGN_CENTER,
				ConMenu.ALIGN_CENTER);
	}

	public ArrayList<CSprite> getSpritesToBlast() {
		return m_menu.getSprites();
	}

	@Override
	public void clickKey(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_DOWN:
			m_menu.goDown();
			break;

		case KeyEvent.VK_UP:
			m_menu.goUp();
			break;
			
		case KeyEvent.VK_S:
			m_menu.goDown();
			break;

		case KeyEvent.VK_Z:
			m_menu.goUp();
			break;

		case KeyEvent.VK_ENTER:
			m_menu.validChoice();
			break;

		default:
			break;
		}
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
	public CScene procc() {
		switch (m_menu.getSelectedChoice()) {
		case MENU_PLAY_GAME:
			return new LvlAsteroid();

		case MENU_HOW_TO_PLAY:
			return new MenuControls();

		case MENU_SETTINGS:
			return new SettingsMenu();

		case MENU_QUIT_GAME:
			System.exit(0);
			break;

		default:
			break;
		}
		return this;
	}

}
