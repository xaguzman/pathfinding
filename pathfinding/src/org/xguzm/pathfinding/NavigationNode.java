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
	public float getF();
	public void setF(float f);

	//g
	public float getG();
	public void setG(float g);

	
	//h
	/** 
	 * Gets the computed value of the heuristic used to get from this point to the goal node. The
	 * heuristic is determined {@link PathFinderOptions#heuristic} used to navigate the grid
	 */
	public float getH();
	
	/** 
	 * Sets the computed value of the heuristic used to get from this point to the goal node. The
	 * heuristic is determined {@link PathFinderOptions#heuristic} used to navigate the grid
	 *  */
	public void setH(float h);
}
