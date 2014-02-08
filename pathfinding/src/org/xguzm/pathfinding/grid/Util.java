package org.xguzm.pathfinding.grid;

import java.util.ArrayList;
import java.util.List;

public class Util {

	private static final List<GridCell> path = new ArrayList<GridCell>(); 
	
	@SuppressWarnings("unchecked")
	public static <T extends GridCell> List<T> backtrace(T node){
		path.clear();
		path.add(node);
		GridCell node1 = node;
		while (node1.getParent() != null){
			node1 = node1.getParent();
			path.add(0, node1);
		}
		path.remove(0);
		return (List<T>)path;
	}
}
