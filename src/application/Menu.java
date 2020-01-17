package application;

import java.util.ArrayList;

public class Menu {
	public static final int NOT_A_CHOICE = -1;

	public static final int ALIGN_CENTER = -1;
	public static final int ALIGN_LEFT = -2;
	public static final int ALIGN_RIGHT = -3;

	public static final int SHOW_ALL = 0;

	private int m_posx;
	private int m_posy;
	private ArrayList<String> m_choices;
	private int m_currentChoice;
	private int m_nbToDisplay;
	private int m_offSet;
	private int m_selectedChoice;
	private int m_maxChar;
	private int m_alignment;
	private int m_textSize;
	private int m_codeRessource;

	public Menu(ArrayList<String> choices, int textSize, int nbToDisplay, int posx, int posy, int alignment,
			int codeRessource) {
		m_selectedChoice = NOT_A_CHOICE;
		m_choices = choices;
		m_textSize = textSize;
		m_alignment = alignment;
		m_posx = posx;
		m_posy = posy;
		m_codeRessource = codeRessource;

		if (m_choices == null)
			m_choices = new ArrayList<String>();
		else if (m_choices.size() == 0)
			m_choices.add("error");

		m_maxChar = 0;
		for (int i = 0; i < m_choices.size(); i++)
			if (m_maxChar < m_choices.get(i).length())
				m_maxChar = m_choices.get(i).length();

		if (nbToDisplay > m_choices.size())
			m_nbToDisplay = m_choices.size();
		else if (nbToDisplay <= SHOW_ALL)
			m_nbToDisplay = m_choices.size();
		else
			m_nbToDisplay = nbToDisplay;

		if (posx == ALIGN_CENTER)
			m_posx = (CGraphics.NB_TILE_X - m_maxChar) * CGraphics.TILE_SIZE / 2;
		if (posy == ALIGN_CENTER)
			m_posy = (CGraphics.NB_TILE_Y - m_nbToDisplay) * CGraphics.TILE_SIZE / 2;

		m_choices.trimToSize();
	}

	public ArrayList<Sprite> getSprites() {
		ArrayList<Text> texts = new ArrayList<Text>();
		ArrayList<Sprite> sprites = new ArrayList<Sprite>();
		for (int i = 0; i < m_nbToDisplay; i++) {
			{
				int dif = 0;
				switch (m_alignment) {
				case ALIGN_CENTER:
					dif = (m_maxChar - m_choices.get(i + m_offSet).length()) * CGraphics.TILE_SIZE / 2;
					break;

				case ALIGN_LEFT:
					dif = (m_maxChar - m_choices.get(i + m_offSet).length()) * CGraphics.TILE_SIZE;
					break;

				default:
					break;
				}

				if (i + m_offSet == m_currentChoice)
					texts.add(new Text("<" + m_choices.get(i + m_offSet) + ">", m_posx - m_textSize + dif,
							m_posy + i * m_textSize, m_textSize, m_codeRessource));
				else
					texts.add(new Text(m_choices.get(i + m_offSet), m_posx + dif, m_posy + i * m_textSize, m_textSize,
							m_codeRessource));

			}
		}
		for (int i = 0; i < texts.size(); i++) {
			for (int j = 0; j < texts.get(i).getText().length(); j++) {
				sprites.add(texts.get(i).getSpriteCharAt(j));
			}
		}
		return sprites;
	}

	public void goDown() {
		if (++m_currentChoice >= m_choices.size())
			m_currentChoice--;
		if (m_currentChoice >= m_offSet + m_nbToDisplay)
			m_offSet++;
	}

	public void goUp() {
		if (--m_currentChoice < 0)
			m_currentChoice++;
		if (m_currentChoice < m_offSet)
			m_offSet--;
	}

	public void validChoice() {
		m_selectedChoice = m_currentChoice;
	}

	public int getCurrentChoice() {
		return m_currentChoice;
	}

	public int getSelectedChoice() {
		return m_selectedChoice;
	}

	public void setTextAt(int i, String text) {
		m_choices.set(i, text);
	}

}
