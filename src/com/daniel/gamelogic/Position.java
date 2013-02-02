package com.daniel.gamelogic;

public class Position {

	private int m_nX = 0;
	private int m_nY = 0;
	private int m_nLayer = 0;
	
	public Position(int nX, int nY, int nLayer) {
		SetPosition(nX, nY);
		m_nLayer = nLayer;
	}
	
	int GetX() {
		return m_nX;
	}
	
	int GetY() {
		return m_nY;
	}
	
	int GetLayer() {
		return m_nLayer;
	}
	
	void SetPosition(int x, int y) {
		m_nX = x;
		m_nY = y;
	}
	
	/*
	void SetLayer(int x, int y) {
		m_nX = x;
		m_nY = y;
		m_nLayer = layer;
	}
	*/	
}
