package org.xguzm.pathfinding.grid;

import org.xguzm.pathfinding.Indexable;


public class GridCell implements Indexable<GridCell>{
	public int x;
	public int y;
	
	/* for path finders*/
	private float f, g, h;
	private boolean isWalkable;
	public int closedOnJob, openedOnJob;
	private GridCell parent;
	
	//for BTree
	private int index;
	
	public GridCell() {}
	
	public GridCell(int column, int row) {
		this(column, row, true);
	}
	
	public GridCell(int column, int row, boolean isWalkable){
		this.y = row;
		this.x = column;
		this.isWalkable = isWalkable;
	}

	@Override
	public void setIndex(int index) {
		this.index = index;		
	}

	@Override
	public int getIndex() {
		return index;
	}
	
	public boolean isWalkable() {
		return isWalkable;
	}

	public void setWalkable(boolean isWalkable) {
		this.isWalkable = isWalkable;
	}

	public float getF() {
		return f;
	}

	public void setF(float f) {
		this.f = f;
	}

	public float getG() {
		return g;
	}

	public void setG(float g) {
		this.g = g;
	}

	public float getH() {
		return h;
	}

	public void setH(float h) {
		this.h = h;
	}

	public GridCell getParent() {
		return parent;
	}

	public void setParent(GridCell parent) {
		this.parent = parent;
	}

//	/**
//	 * Compares to another navigation node. Comparison is based on their getF() value.
//	 */
//	@Override
//	public int compareTo(GridCell o) {
//		if (o == null)
//			return 1;
//		return (int)(this.getF() - o.getF());
//	}
	
	@Override
	public String toString() {
		return "[" + x + ", " + y + "]";
	}

}
