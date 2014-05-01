package org.xguzm.pathfinding;

/**
 * An interface to calculate the distance between two nodes in a {@link NavigationGraph} 
 * 
 * @author Xavier Guzman
 */
public interface Heuristic {
	
	float calculate(NavigationNode from, NavigationNode to);
}
