package org.xguzm.pathfinding;

public interface NavigationNode extends BHeapNode{
	
	/** The Node from which this node is reachable */
	void setParent(NavigationNode parent);
	NavigationNode getParent();
	
	int getClosedOnJob();
	void setClosedOnJob(int job);
	
	int getOpenedOnJob();
	void setOpenedOnJob(int job);
	
	//f
	float getF();
	void setF(float f);

	//g
	float getG();
	void setG(float g);

	
	//h
	/** 
	 * Gets the computed value of the heuristic used to get from this point to the goal node. The
	 * heuristic is determined {@link PathFinderOptions#heuristic} used to navigate the grid
	 */
	float getH();
	
	/** 
	 * Sets the computed value of the heuristic used to get from this point to the goal node. The
	 * heuristic is determined {@link PathFinderOptions#heuristic} used to navigate the grid
	 *  */
	void setH(float h);
	
	boolean isWalkable();
	void setWalkable(boolean walkable);
}
