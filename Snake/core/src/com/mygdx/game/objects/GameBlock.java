package com.mygdx.game.objects;

import com.badlogic.gdx.math.Rectangle;

public class GameBlock 
{
	private Rectangle rectangle;
	private BlockType type; //okresla rodzaj bloku
	private int previousRow;
	private int previousColumn;
	
	public GameBlock(Rectangle rectangle, BlockType type, int previousRow, int previousColumn) 
	{
		this.rectangle = rectangle;
		this.type = type;
		this.previousRow = previousRow;
		this.previousColumn = previousColumn;
	}
	
	public GameBlock(Rectangle rectangle, BlockType type) 
	{
		this.rectangle = rectangle;
		this.type = type;
	}
	
	public Rectangle getRectangle() {
		return rectangle;
	}
	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
	}
	public BlockType getType() {
		return type;
	}
	public void setType(BlockType type) {
		this.type = type;
	}

	public int getPreviousRow() {
		return previousRow;
	}

	public void setPreviousRow(int previousRow) {
		this.previousRow = previousRow;
	}

	public int getPreviousColumn() {
		return previousColumn;
	}

	public void setPreviousColumn(int previousColumn) {
		this.previousColumn = previousColumn;
	}
	 
	
}
