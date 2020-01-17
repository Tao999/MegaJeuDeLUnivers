package simpleScene;

import application.CApp;
import application.CGraphics;
import application.GameObject;

public class Player implements GameObject {
	public static final int SPRITE_SIZE = 32;

	public static final int SPEED = 3;

	private int x;
	private int y;
	private int m_count;

	Player() {
		x = CGraphics.NB_TILE_X / 2 * CGraphics.TILE_SIZE;
		y = CGraphics.TILE_SIZE * (CGraphics.NB_TILE_Y - 2);
		m_count = 0;
	}

	@Override
	public void procc() {
		//regarde les touches de déplacement
		if ((CApp.getKbStatus().isBitSet(CApp.KB_UP) || (CApp.getKbStatus().isBitSet(CApp.KB_Z)))
				&& (CApp.getKbStatus().isBitClr(CApp.KB_DOWN) && CApp.getKbStatus().isBitClr(CApp.KB_S)))
			y -= SPEED;
		if ((CApp.getKbStatus().isBitSet(CApp.KB_RIGHT) || (CApp.getKbStatus().isBitSet(CApp.KB_D)))
				&& (CApp.getKbStatus().isBitClr(CApp.KB_LEFT) && CApp.getKbStatus().isBitClr(CApp.KB_Q)))
			x += SPEED;
		if ((CApp.getKbStatus().isBitSet(CApp.KB_DOWN) || (CApp.getKbStatus().isBitSet(CApp.KB_S)))
				&& (CApp.getKbStatus().isBitClr(CApp.KB_UP) && CApp.getKbStatus().isBitClr(CApp.KB_Z)))
			y += SPEED;
		if ((CApp.getKbStatus().isBitSet(CApp.KB_LEFT) || (CApp.getKbStatus().isBitSet(CApp.KB_Q)))
				&& (CApp.getKbStatus().isBitClr(CApp.KB_RIGHT) && CApp.getKbStatus().isBitClr(CApp.KB_D)))
			x -= SPEED;

		// collisions limite
		if (x < 0)
			x = 0;
		if (y < CGraphics.TILE_SIZE)
			y = CGraphics.TILE_SIZE;
		if (x > (CGraphics.NB_TILE_X - 1) * CGraphics.TILE_SIZE)
			x = (CGraphics.NB_TILE_X - 1) * CGraphics.TILE_SIZE;
		if (y > (CGraphics.NB_TILE_Y - 2) * CGraphics.TILE_SIZE)
			y = (CGraphics.NB_TILE_Y - 2) * CGraphics.TILE_SIZE;
		m_count++;
	}

	@Override
	public int getPosx() {
		return x;
	}

	@Override
	public int getPosy() {
		return y;
	}

	@Override
	public int getPosxInSet() {
		return SPRITE_SIZE * (m_count / 4 % 2);
	}

	@Override
	public int getPosyInSet() {
		return 0;
	}

	@Override
	public boolean isDeadObject() {
		return false;
	}

}
