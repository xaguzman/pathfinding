package org.xguzm.pathfinding.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.xguzm.pathfinding.Util;
import org.xguzm.pathfinding.grid.GridCell;

public class UtilTest {

	@Test
	public void testBacktrace() {
		
		ArrayList<GridCell> path = new ArrayList<GridCell>();
		
		path.add(new GridCell());
		for (int i = 1; i < 5; i++){
			GridCell cell = new GridCell(i, i);
			cell.setParent(path.get(i - 1));
			path.add(cell);
		}
		
		List<GridCell> backwardPath = Util.backtrace(path.get(path.size() - 1));
		
		//since initial cell is excluded, removed from original path
		path.remove(0);
		
		assertEquals("Reversed path size does not match" , path.size(), backwardPath.size() );
		for(int i = 0 ; i < path.size(); i++){
			assertSame("Reversed path node is changed", path.get(i), backwardPath.get(i));
		}
	}

}
