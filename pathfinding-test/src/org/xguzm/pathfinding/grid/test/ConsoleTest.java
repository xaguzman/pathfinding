package org.xguzm.pathfinding.grid.test;

import java.util.List;

import org.xguzm.pathfinding.grid.GridCell;
import org.xguzm.pathfinding.grid.NavigationGrid;
import org.xguzm.pathfinding.grid.finders.GridFinderOptions;
import org.xguzm.pathfinding.grid.finders.JumpPointFinder;
import org.xguzm.pathfinding.grid.heuristics.EuclideanDistance;


public class ConsoleTest {

	public static void main(String[] args) throws InstantiationException, IllegalAccessException{
	
		int[][] cells = {
			{1, 1, 1, 1},
			{0, 0, 0, 1},
			{0, 0, 0, 1},
			{1, 1, 0, 1}
		};
	
		for (int i = cells[0].length - 1; i >= 0; i--){
			for (int j = 0; j < cells.length; j++){
				System.out.print(cells[j][i] + ",");
			}
			System.out.println("");
		}
		System.out.println("");
		
		GridFinderOptions options = new GridFinderOptions(true, true, new EuclideanDistance(), false, 1, 1);
		
		NavigationGrid<GridCell> grid= new NavigationGrid<GridCell>(cells.length, cells[0].length, GridCell.class, cells );
		//AStarGridFinder<GridCell> finder = new AStarGridFinder<GridCell>(GridCell.class, options);
		JumpPointFinder<GridCell> finder = new JumpPointFinder<GridCell>(GridCell.class, options);
		
		List<GridCell> path = finder.findPath(1, 0, 3, 2,  grid);
		for(GridCell c : path){
			System.out.println(c);
		}
		System.out.println("");
		
		GridCell[][] nodes = {
				{new GridCell(0, 0, false), new GridCell(0, 1, false), new GridCell(0, 2, false)},
				{new GridCell(1, 0), new GridCell(1, 1, false), new GridCell(1, 2, false)},
				{new GridCell(2, 0), new GridCell(2, 1), new GridCell(2, 2)},
				{new GridCell(3, 0, false), new GridCell(3, 1, false), new GridCell(3, 2)}	
		};
		
		NavigationGrid<GridCell> grid2 = new NavigationGrid<GridCell>(nodes.length, nodes[0].length, nodes );
		
		for (int i = nodes[0].length - 1; i >= 0; i--){
			for (int j = 0; j < nodes.length; j++){
				System.out.print(nodes[j][i].isWalkable() + ",");
			}
			System.out.println("");
		}
		System.out.println("");
		
		path = finder.findPath(1, 0, 3, 2,  grid2);
		for(GridCell c : path){
			System.out.println(c);
		}
	}
}
