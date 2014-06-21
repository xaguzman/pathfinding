package org.xguzm.pathfinding;

import java.util.List;

public interface PathFinder<T extends NavigationNode> {
	/**
	 * Finds. The resulting collection should never be modified, copy the values instead.
	 * 
	 * @param startNode 
	 * @param endNode 
	 * @return The path from start(exclusive) to end(inclusive) or null, if no path was found.
	 */
	public List<T> findPath(T startNode, T endNode, NavigationGraph<T> grid);
}
