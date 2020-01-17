package application;

import java.awt.Point;
import java.awt.Rectangle;

public class MapInfo {
	private int[][] m_map;
	private Rectangle m_rect;
	private Point m_tileDimension;
	private Point m_offSet;
	private Point[] m_idInfo;

	public MapInfo(int[][] map, Rectangle rect, Point tileDimension, Point offset, Point[] idInfo) {
		m_map = map;
		m_rect = rect;
		m_tileDimension = tileDimension;
		m_offSet = offset;
		m_idInfo = idInfo;
	}
	
	public int[][] getMap(){
		return m_map;
	}
	
	public Rectangle getRectToBlast() {
		return m_rect;
	}
	
	public Point getTileDimension() {
		return m_tileDimension;
	}
	
	public Point getOffset() {
		return m_offSet;
	}
	
	public Point[] getIdInfo() {
		return m_idInfo;
	}
}
