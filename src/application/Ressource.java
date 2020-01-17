package application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Ressource {
	private BufferedImage m_ressource;
	private int m_ressourceCode;
	
	public Ressource(int ressourceCode, String ressourcePath){
		m_ressourceCode = ressourceCode;
		try {
			m_ressource = ImageIO.read(new File(ressourcePath));
		} catch (IOException e) {
			m_ressource = new BufferedImage(CGraphics.RESSOURCE_SIZE, CGraphics.RESSOURCE_SIZE, BufferedImage.TYPE_INT_ARGB);
		}
	}
	
	public int getRessourceCode() {
		return m_ressourceCode;
	}
	
	public BufferedImage getRessource() {
		return m_ressource;
	}
}
