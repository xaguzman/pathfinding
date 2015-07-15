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
		System.out.println("\nRunning AStarFinderTest.testMovementPath");
		GridCell start = grid.getCell(2, 0), end = grid.getCell(4, 7);
		
		//test orthogonal movement only
		opt.allowDiagonal = false;
		
		List<GridCell> path = finder.findPath(start,  end,  grid);
		assertNotNull(String.format("No path found from %s to %s for orthogonal movement", start, end), path);
		
		ManhattanDistance heuristic = new ManhattanDistance();
		
		System.out.println("  Path: no diagonal movement allowed ");
		for(int i = 1; i < path.size(); i++){
			GridCell current = path.get(i);
			GridCell prev = path.get(i-1);
			
			System.out.println("    Path1: (" + (i) + ") " + current);
			
			//the distance should not be different than one, otherwise, a diagonal movement occured
			float dst = heuristic.calculate(current, prev);
			
			assertTrue("Found diagonal movement during orthogonal-only movement test", dst == 1);
		}
		
		
		//test diagonal movement 
		opt.allowDiagonal = true;
		
		path = finder.findPath(start, end, grid);
		assertNotNull(String.format("No path found from %s to %s for diagnoal movement", start, end), path);
		
		int diagonalCount = 0;
		System.out.println("\n  Diagonal movement allowed: ");
		for(int i = 1; i < path.size(); i++){
			GridCell current = path.get(i);
			GridCell prev = path.get(i-1);
			
			//the distance should be greater than one, otherwise, no diagonal movement occured
			float dst = heuristic.calculate(current, prev);
			System.out.println("    Path2: (" + (i) + ") " + current);
			
			if (dst > 1) diagonalCount++;
		}
		
		assertTrue("No diagonal movement during diagonal movement test", diagonalCount > 0);
	}
	
	@Test
	public void autoAssignXYMapTest(){
		System.out.println("\nRunning AStarFinderTest.autoAssignXYMapTest");
		NavigationGrid<GridCell> grid = NavGraphFactory.getAutoAssignedGridCellMap();
		GridCell c = grid.getCell(3, 1);
		
		assertTrue("GridCell at Grid(3,2) didn't have it's x and y auto assigned correctly", c.x == 3 && c.y == 1);
		
		GridCell start = grid.getCell(2, 0), end = grid.getCell(4, 7);
		
		//test orthogonal movement only
		opt.allowDiagonal = false;
		
		List<GridCell> path = finder.findPath(start,  end,  grid);
		assertNotNull(String.format("No path found from %s to %s for orthogonal movement", start, end), path);
	}
}
