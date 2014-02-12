package org.xguzm.pathfinding.grid.finders;

import java.util.List;

import org.xguzm.pathfinding.finders.AStarFinder;
import org.xguzm.pathfinding.grid.GridCell;
import org.xguzm.pathfinding.grid.NavigationGrid;

public class AStarGridFinder<T extends GridCell> extends AStarFinder<T> {

	public AStarGridFinder(Class<T> clazz) {
		this(clazz, new GridFinderOptions());
	}
	
	public AStarGridFinder(Class<T> clazz, GridFinderOptions options){
		super(clazz, options);
	}
	
	/**
	 * Find and return the path. The resulting collection should never be modified, copy the values instead.
	 * 
	 * @return The path from [startX, startY](exclusive) to [endX, endY] 
	 */
	public List<T> findPath(int startX, int startY, int endX, int endY, NavigationGrid<T> grid) {
		return findPath(grid.getCell(startX, startY), grid.getCell(endX, endY), grid); 	    
	}

}
