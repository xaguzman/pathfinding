package org.xguzm.pathfinding;

import org.xguzm.pathfinding.util.*;

import java.util.ArrayList;
import java.util.List;

public class Util {

	private static final List<NavigationNode> path = new ArrayList<NavigationNode>(); 
	
	@SuppressWarnings("unchecked")
	public static <T extends NavigationNode> List<T> backtrace(T node){
		path.clear();
		path.add(node);
		T node1 = node;
		while (node1.getParent() != null && node1 != node1.getParent()){
			node1 = (T)node1.getParent();
			path.add(0, node1);
		}
		path.remove(0);
		return (List<T>)path;
	}

	public static void validateNotNull(NavigationNode node, String msg){
		if (node == null){
			throw new PathFindingException(msg, new NullPointerException());
		}
	}
}
