package org.xguzm.pathfinding.grid.heuristics;

import org.xguzm.pathfinding.Heuristic;
import org.xguzm.pathfinding.NavigationNode;
import org.xguzm.pathfinding.grid.GridCell;

public class ChebyshevDistance implements Heuristic{

	@Override
	public float calculate(NavigationNode from, NavigationNode to) {
		GridCell c1 = (GridCell)from, c2 = (GridCell) to;
		
		return calculate(Math.abs(c2.x - c1.x), Math.abs(c2.y - c1.y));
	}
	
	public float calculate(int dx, int dy) {
		return Math.max(dx, dy);
	}

}
