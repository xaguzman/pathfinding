package org.xguzm.pathfinding;


public abstract class PathFinderOptions {
	/** The {@link Heuristic} to calculate the distance from one node to another 
	 * </p>
	 * Default value is {@link Heuristic#Manhattan}
	 */
	public Heuristic heuristic;
}
