package org.xguzm.pathfinding.grid;

import org.xguzm.pathfinding.NavigationGraph;
import org.xguzm.pathfinding.NavigationNode;
import org.xguzm.pathfinding.PathFinder;
import org.xguzm.pathfinding.util.ObjectIntMap;

import java.util.List;


public class GridCell implements NavigationGridGraphNode{
	public int x;
	public int y;
	
	/* for path finders*/
	private float f, g, h;
	private boolean isWalkable;
	private GridCell parent;
	private ObjectIntMap<Class<? extends PathFinder>> closedOnJob = new ObjectIntMap<Class<? extends PathFinder>>();
	private ObjectIntMap<Class<? extends PathFinder>> openedOnJob = new ObjectIntMap<Class<? extends PathFinder>>();

	//for BTree
	private int index;


	public GridCell() {}

	public GridCell(int x, int y) {
		this(x, y, true);
	}

	public GridCell(int x, int y, boolean isWalkable){
		this.y = y;
		this.x = x;
		this.isWalkable = isWalkable;
	}

	public GridCell(boolean isWalkable){
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
		return getClosedOnJob(DummyFinder.class);
	}

	@Override
	public void setClosedOnJob(int closedOnJob) {
		setClosedOnJob(closedOnJob, DummyFinder.class);
	}

	@Override
	public int getOpenedOnJob() {
		return getOpenedOnJob(DummyFinder.class) ;
	}

	@Override
	public void setOpenedOnJob(int openedOnJob) {
		setOpenedOnJob(openedOnJob, DummyFinder.class);
	}

	@Override
	public int getClosedOnJob(Class<? extends PathFinder> clazz) {
		return closedOnJob.get(clazz, 0);
	}

	@Override
	public void setClosedOnJob(int closedOnJob, Class<? extends PathFinder> clazz) {
		this.closedOnJob.put(clazz, closedOnJob);
	}

	@Override
	public int getOpenedOnJob(Class<? extends PathFinder> clazz) {
		return openedOnJob.get(clazz, 0);
	}

	@Override
	public void setOpenedOnJob(int openedOnJob, Class<? extends PathFinder> clazz) {
		this.openedOnJob.put(clazz, openedOnJob);
	}

	@Override
	public String toString() {
		return "[" + x + ", " + y + "]";
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public void setX(int x) {
		this.x = x;
	}

	@Override
	public void setY(int y) {
		this.y = y;
	}

	private static final class DummyFinder<T extends GridCell> implements PathFinder<T> {
		@Override
		public List<T> findPath(T startNode, T endNode, NavigationGraph<T> grid) {
			return null;
		}
	}
}
