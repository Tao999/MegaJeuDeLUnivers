package application;

import java.awt.event.KeyEvent;
import java.util.LinkedList;


public interface Scene {

	public Scene procc();

	public LinkedList<Sprite> getSpritesToBlast();

	public LinkedList<Ressource> getGfxRessources();
	
	public MapInfo getMapInfo();

	public void clickKey(KeyEvent e);

}
