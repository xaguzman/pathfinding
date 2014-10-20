package org.xguzm.pathfinding.grid.finders;

import java.util.List;

import org.xguzm.pathfinding.NavigationGraph;
import org.xguzm.pathfinding.NavigationNode;
import org.xguzm.pathfinding.finders.ThetaStarFinder;
import org.xguzm.pathfinding.grid.NavigationGridGraph;
import org.xguzm.pathfinding.grid.NavigationGridGraphNode;

public class ThetaStarGridFinder<T extends NavigationGridGraphNode> extends ThetaStarFinder<T>{
	
	public ThetaStarGridFinder(Class<T> clazz){
		this(clazz, new GridFinderOptions());
	}
	
	public ThetaStarGridFinder(Class<T> clazz, GridFinderOptions opt) {
	    super(clazz, opt);
	}
	
	public List<T> findPath(int startX, int startY, int endX, int endY, NavigationGridGraph<T> grid) {
		return findPath(grid.getCell(startX, startY), grid.getCell(endX, endY), grid); 	    
	}
		
	@Override
	protected boolean lineOfSight(NavigationNode from, NavigationNode to, NavigationGraph<T> graph){
		if (from == null || to == null)
			return false;
		
		NavigationGridGraph<T> grid = (NavigationGridGraph<T>)graph;
		NavigationGridGraphNode node = (NavigationGridGraphNode) from, neigh = (NavigationGridGraphNode) to;
		int x1 = node.getX(), y1 = node.getY();
		int x2 = neigh.getX(), y2 = neigh.getY();
		int dx = Math.abs(x1 - x2);
		int dy = Math.abs(y1 - y2);
		int xinc = (x1 < x2) ? 1 : -1;
		int yinc = (y1 < y2) ? 1 : -1;
		
		int error = dx - dy;
		
		for ( int n = dx + dy; n > 0; n--){
			if (!grid.isWalkable(x1, y1)) 
				return false; 
			int e2 = 2*error;
			if ( e2 > -dy){
				error -= dy;
				x1 += xinc;
			}		
			if (e2 < dx ){
				error += dx;
				y1 += yinc;
			}
		}
		
		return true;
		
	}

}