package org.xguzm.pathfinding;

import java.util.List;

import org.xguzm.pathfinding.grid.NavigationGridGraph;
import org.xguzm.pathfinding.grid.NavigationGridGraphNode;

/**
 * A collection of {@link NavigationNode}.
 * 
 * @author Xavier Guzman
 *
 * @param <T> the type implementing {@link NavigationNode}
 */
public interface NavigationGraph<T extends NavigationNode> {
	/**
	 * @param node the node to find the neighbors for
	 * @return a list with all the adjacent nodes for the passed node, using the default options
	 */
	List<T> getNeighbors(T node);
	
	/**
	 * @param node the node to find the neighbors for
	 * @return a list containing the adjacent nodes for the passed node, using the passed options
	 */
	List<T> getNeighbors(T node, PathFinderOptions opt);
	
	/** Determines the movement cost for moving from node1 to node2, with the given options */
	float getMovementCost(T node1, T node2, PathFinderOptions opt);
	
	
	boolean isWalkable(T node );
	
	boolean lineOfSight(NavigationNode from, NavigationNode to);
	
}
