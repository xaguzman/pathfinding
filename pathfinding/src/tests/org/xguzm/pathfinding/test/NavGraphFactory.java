package org.xguzm.pathfinding.test;

import org.xguzm.pathfinding.grid.GridCell;
import org.xguzm.pathfinding.grid.NavigationGrid;

public class NavGraphFactory {

	private static // 0 means closed, 1 means open, 2 is marker for start, 3 is marker for goal
	int[][] navCells = new int[][] { 
		{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },	// 8
		{ 0, 0, 0, 0, 3, 1, 0, 0, 0, 0 },
		{ 0, 0, 0, 0, 1, 1, 1, 0, 0, 0 },
		{ 0, 0, 0, 0, 0, 1, 1, 0, 0, 0 },
		{ 0, 0, 0, 1, 1, 0, 1, 1, 0, 0 },   // 4
		{ 0, 0, 0, 1, 1, 1, 1, 1, 0, 0 },
		{ 0, 0, 0, 1, 1, 1, 1, 1, 0, 0 },
		{ 0, 0, 0, 1, 1, 0, 0, 0, 0, 0 },
		{ 0, 0, 2, 1, 1, 0, 0, 0, 0, 0 }    //0
		//0				 5			 9
	};
	
	public static NavigationGrid<GridCell> getGridCellMap() {
		GridCell[][] cells = new GridCell[navCells[0].length][navCells.length];
	

		for (int y = navCells.length - 1; y >= 0; y--) 
			for (int x = 0; x < navCells[0].length; x++){
				int invY = navCells.length - 1 - y;
				GridCell cell = new GridCell(x, invY, navCells[y][x] > 0);
				cells[x][invY] = cell;
			}

		return new NavigationGrid<GridCell>(cells, false);

	}
	
	public static NavigationGrid<GridCell> getAutoAssignedGridCellMap() {
		GridCell[][] cells = new GridCell[navCells[0].length][navCells.length];
	

		for (int y = navCells.length - 1; y >= 0; y--) 
			for (int x = 0; x < navCells[0].length; x++){
				int invY = navCells.length - 1 - y;
				GridCell cell = new GridCell(navCells[y][x] > 0);
				cells[x][invY] = cell;
			}

		return new NavigationGrid<GridCell>(cells, true);

	}
}
