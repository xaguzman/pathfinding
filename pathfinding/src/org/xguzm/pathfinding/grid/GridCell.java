package org.xguzm.pathfinding.grid;

import org.xguzm.pathfinding.NavigationNode;


public class GridCell implements NavigationNode{
	public int x;
	public int y;
	
	/* for path finders*/
	private float f, g, h;
	private boolean isWalkable;
	private int closedOnJob, openedOnJob;
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

	@Override
	public float getG() {
		return g;
	}

	@Override
	public void setG(float g) {
		this.g = g;
	}

	@Override
	public float getH() {
		return h;
	}

	@Override
	public void setH(float h) {
		this.h = h;
	}

	@Override
	public NavigationNode getParent() {
		return parent;
	}

	@Override
	public void setParent(NavigationNode parent) {
		this.parent = (GridCell)parent;
	}

	@Override
	public int getClosedOnJob() {
		return closedOnJob;
	}

	@Override
	public void setClosedOnJob(int closedOnJob) {
		this.closedOnJob = closedOnJob;
	}

	@Override
	public int getOpenedOnJob() {
		return openedOnJob;
	}

	@Override
	public void setOpenedOnJob(int openedOnJob) {
		this.openedOnJob = openedOnJob;
	}

	@Override
	public String toString() {
		return "[" + x + ", " + y + "]";
	}
}
