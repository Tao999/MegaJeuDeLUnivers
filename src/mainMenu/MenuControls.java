package mainMenu;

import java.awt.event.KeyEvent;

import application.CFlag;
import application.CScene;

public class MenuControls extends CScene{
	static final String BKG_PATH = "gfx/levels/mainMenu/controls.png";
	
	static final int NEED_QUIT = 0b1;

	private CFlag m_status;
	
	public MenuControls() {
		m_status = new CFlag();
	}
	
	@Override
	public CScene procc() {
		if(m_status.isBitSet(NEED_QUIT))
			return new MainMenu();
		return this;
	}
	
	@Override
	public String getBkgPath() {
		return BKG_PATH;
	}
	
	@Override
	public void clickKey(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_ESCAPE)
			m_status.bitSet(NEED_QUIT);
	}
}
