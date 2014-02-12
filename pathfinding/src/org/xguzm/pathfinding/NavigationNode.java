package org.xguzm.pathfinding;

public interface NavigationNode extends BHeapNode<NavigationNode>{
	
	/** The Node from which this node is reachable */
	void setParent(NavigationNode parent);
	NavigationNode getParent();
	
	int getClosedOnJob();
	void setClosedOnJob(int job);
	
	int getOpenedOnJob();
	void setOpenedOnJob(int job);
	
	public float getF();
	public void setF(float f);

	public float getMovementCost();
	public void setMovementCost(float g);

	/** 
	 * Gets the computed value of the heuristic used to get from this point to the goal node. The
	 * heuristic is determined {@link PathFinderOptions#heuristic} used to navigate the grid
	 */
	public float getHeuristic();
	
	/** 
	 * Sets the computed value of the heuristic used to get from this point to the goal node. The
	 * heuristic is determined {@link PathFinderOptions#heuristic} used to navigate the grid
	 *  */
	public void setHeuristic(float h);
}
