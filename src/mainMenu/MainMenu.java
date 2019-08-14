package mainMenu;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import application.CScene;
import application.CSprite;
import application.CText;
import application.ContextualMenu;
import lvlAsteroid.LvlAsteroid;

public class MainMenu extends CScene {
	static final String FONT_PATH = "gfx/font/pen.png";
	static final String BKG_PATH = "gfx/levels/mainMenu/mainMenu.png";

	public static final String PLAY_OPTION = "play";
	public static final String HOW_TO_PLAY_OPTION = "controls";
	public static final String QUIT_OPTION = "quit";

	public static final int PLAY_GAME = 0;
	public static final int HOW_TO_PLAY = 1;
	public static final int QUIT_GAME = 2;

	private ContextualMenu m_menu;

	public MainMenu() {
		ArrayList<String> choices = new ArrayList<String>();
		choices.add(PLAY_OPTION);
		choices.add(HOW_TO_PLAY_OPTION);
		choices.add(QUIT_OPTION);
		m_menu = new ContextualMenu(choices, ContextualMenu.SHOW_ALL, ContextualMenu.ALIGN_CENTER,
				ContextualMenu.ALIGN_CENTER, ContextualMenu.ALIGN_CENTER);
	}

	public ArrayList<CSprite> getSpritesToBlast() {
		ArrayList<CSprite> sprites = new ArrayList<CSprite>();
		ArrayList<CText> texts = m_menu.getTexts();
		for (int i = 0; i < texts.size(); i++)
			for (int j = 0; j < texts.get(i).getText().length(); j++)
				sprites.add(texts.get(i).getSpriteCharAt(j));
		return sprites;
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
		switch (m_menu.getChoice()) {
		case PLAY_GAME:
			return new LvlAsteroid();

		case HOW_TO_PLAY:
			return new MenuControls();

		case QUIT_GAME:
			System.exit(0);
			break;

		default:
			break;
		}
		return this;
	}

}
