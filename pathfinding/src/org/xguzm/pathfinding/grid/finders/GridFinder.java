package org.xguzm.pathfinding.grid.finders;

import java.util.List;

import org.xguzm.pathfinding.grid.GridCell;
import org.xguzm.pathfinding.grid.NavigationGrid;

public interface GridFinder<T extends GridCell>{
	List<T> findPath(T startNode, T endNode, NavigationGrid<T> grid);
	List<T> findPath(int startX, int startY, int endX, int endY, NavigationGrid<T> grid) ;
}
