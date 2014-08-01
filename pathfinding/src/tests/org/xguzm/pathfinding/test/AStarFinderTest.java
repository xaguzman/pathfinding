package org.xguzm.pathfinding.test;

import java.util.List;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.xguzm.pathfinding.finders.AStarFinder;
import org.xguzm.pathfinding.grid.GridCell;
import org.xguzm.pathfinding.grid.NavigationGrid;
import org.xguzm.pathfinding.grid.finders.GridFinderOptions;
import org.xguzm.pathfinding.grid.heuristics.ManhattanDistance;


public class AStarFinderTest {

	NavigationGrid<GridCell> grid;
	AStarFinder<GridCell> finder;
	GridFinderOptions opt;
	
	@Before
	public void init(){
		grid = NavGraphFactory.getGridCellMap();	
		opt = new GridFinderOptions();
		finder = new AStarFinder<GridCell>(GridCell.class, opt);
	}
	
	@Test
	public void testMovementPath() {
		GridCell start = grid.getCell(2, 0), end = grid.getCell(4, 7);
		
		//test orthogonal movement only
		opt.allowDiagonal = false;
		
		List<GridCell> path = finder.findPath(start,  end,  grid);
		assertNotNull(String.format("No path found from %s to %s for orthogonal movement", start, end), path);
		
		ManhattanDistance heuristic = new ManhattanDistance();
		
		for(int i = 1; i < path.size(); i++){
			GridCell current = path.get(i);
			GridCell prev = path.get(i-1);
			
			//the distance should not be greater than one, otherwise, a diagonal movement occured
			float dst = heuristic.calculate(current, prev);
			
			assertTrue("Found diagonal movement during orthogonal-only movement test", dst == 1);
		}
		
		
		//test diagonal movement 
		opt.allowDiagonal = true;
		
		path = finder.findPath(start, end, grid);
		assertNotNull(String.format("No path found from %s to %s for diagnoal movement", start, end), path);
		
		int diagonalCount = 0;
		for(int i = 1; i < path.size(); i++){
			GridCell current = path.get(i);
			GridCell prev = path.get(i-1);
			
			//the distance should not be greater than one, otherwise, a diagonal movement occured
			float dst = heuristic.calculate(current, prev);
			
			if (dst == 1) diagonalCount++;
		}
		
		assertTrue("No diagonal movement during diagonal movement test", diagonalCount > 0);
	}
}
