package com.daniel.gamelogic;

import com.daniel.gamelogic.Position;

public class Entity {

	private Position m_Position = null; 
	
	public Entity() {
		m_Position = new Position(0,0,0);
	}

	/*
	void SetLayer(int nLayer) {
		m_Position.SetLayer(nLayer);
	}
	*/
	
	void SetPosition(int x, int y, int layer) {
		m_Position.SetPosition(x, y);
	}
	
	Position GetPosition() {
		return m_Position;
	}
}
