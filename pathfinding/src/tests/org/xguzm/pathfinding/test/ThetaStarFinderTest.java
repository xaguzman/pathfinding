package org.xguzm.pathfinding.test;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.xguzm.pathfinding.grid.GridCell;
import org.xguzm.pathfinding.grid.NavigationGrid;
import org.xguzm.pathfinding.grid.finders.GridFinderOptions;
import org.xguzm.pathfinding.grid.finders.ThetaStarGridFinder;

public class ThetaStarFinderTest {

	NavigationGrid<GridCell> grid;
	ThetaStarGridFinder<GridCell> finder;
	GridFinderOptions opt;
	
	@Before
	public void init(){
		grid = NavGraphFactory.getGridCellMap();	
		opt = new GridFinderOptions();
		finder = new ThetaStarGridFinder<GridCell>(GridCell.class, opt);
	}
	
	@Test
	public void basicMovementTest() {
		System.out.println("\nRunning ThetaStarFinderTest.basicMovementTest");
		GridCell start = grid.getCell(2, 0), end = grid.getCell(4, 7);
		
		//test orthogonal movement only
		opt.allowDiagonal = false;
		
		List<GridCell> path = finder.findPath(start,  end,  grid);
		assertNotNull(String.format("No path found from %s to %s for orthogonal movement", start, end), path);
				
		//TODO: smarter test...how to make sure path is smooth?
		int i =0 ;
		System.out.println("  Path: no diagonal movement allowed ");
		for(GridCell cell : path){
			System.out.println("    (" + (i++) + ") " + cell);
		}
		
		//test diagonal movement 
		opt.allowDiagonal = true;
		
		path = finder.findPath(start, end, grid);
		assertNotNull(String.format("No path found from %s to %s for diagnoal movement", start, end), path);
		
		//TODO: smarter test...how to make sure path is smooth?
		i =0 ;
		System.out.println("  Diagonal movement allowed ");
		for(GridCell cell : path){
			System.out.println("    (" + (i++) + ") " + cell);
		}
	}

}
