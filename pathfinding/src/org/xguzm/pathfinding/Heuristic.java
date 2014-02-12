package org.xguzm.pathfinding;


public interface Heuristic {
	
	float calculate(NavigationNode from, NavigationNode to);
}
