package org.xguzm.pathfinding.grid.finders;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import org.xguzm.pathfinding.grid.BHeap;
import org.xguzm.pathfinding.grid.GridCell;
import org.xguzm.pathfinding.grid.NavigationGrid;
import org.xguzm.pathfinding.grid.Util;

public class AStarFinder<T extends GridCell> implements GridFinder<T>{

	private GridFinderOptions opt;
	BHeap<T> openList;
	HashSet<T> closedList;
	public int jobId;

	public AStarFinder(Class<T> clazz) {
		this (clazz, new GridFinderOptions());
	}
	
	public AStarFinder(Class<T> clazz, GridFinderOptions opt) {
	    this.opt = opt ;
	    openList = new BHeap<T>(new Comparator<T>() {
				@Override
		    	public int compare(T o1, T o2) {
		    		if (o1 == null || o2 == null){
		    			if (o1 == o2)
		    				return 0;
		    			if (o1 == null) 
		    				return -1;
		    			else
		    				return 1;
		    			
		    		}
		    		return (int)(o1.getF() - o2.getF());
		    	}
		}, clazz);
	    closedList = new HashSet<T>();
	}

	/**
	 * Find and return the path. The resulting collection should never be modified, copy the values instead.
	 * The reason is caching of the results in memory to avoid memory allocation
	 * @return The path, excluding the start point and including the end point, or null, if no path was found.
	 */
	public List<T> findPath(T startNode, T endNode, NavigationGrid<T> grid) {
		if (jobId == Integer.MAX_VALUE)
			jobId = 0;
		int job = ++jobId;
		
	    T node, neighbor;
        List<T> neighbors = new ArrayList<T>();
        float ng;
        
	    startNode.setG(0);
	    startNode.setF(0);

	    // push the start node into the open list
	    openList.clear();
	    openList.add(startNode);
	    startNode.setParent(null);
	    startNode.openedOnJob = job;
	    int r, c;
	    
	    while (openList.size > 0) {
	    	
	        // pop the position of node which has the minimum 'f' value.
	        node = openList.pop();
	        node.closedOnJob = job;
	        

	        // if reached the end position, construct the path and return it
	        if (node == endNode) {
	            return Util.backtrace(endNode);
	        }

	        // get neighbors of the current node
	        neighbors.clear();
	        neighbors.addAll( grid.getNeighbors(node, opt)) ;
	        for (int i = 0, l = neighbors.size(); i < l; ++i) {
	            neighbor = neighbors.get(i);

	            if (neighbor.closedOnJob == job || !grid.isWalkable(neighbor.x , neighbor.y)) {
	                continue;
	            }

	            c = neighbor.x;
	            r = neighbor.y;

	            // get the distance between current node and the neighbor
	            // and calculate the next g score
	            ng = node.getG() + ((c == node.x || r == node.y ) ? 1 : 1.4f);

	            // check if the neighbor has not been inspected yet, or
	            // can be reached with smaller cost from the current node
	            if (neighbor.openedOnJob != job || ng < neighbor.getG()) {
	            	float prevf = neighbor.getF();
	                neighbor.setG(ng);
	                neighbor.setH(opt.heuristic.calculate( c, r, endNode.x, endNode.y));
	                neighbor.setF( neighbor.getG() + neighbor.getH());
	                neighbor.setParent(node);

	                if (neighbor.openedOnJob != job) {
	                    openList.add(neighbor);
	                    neighbor.openedOnJob = job;
	                } else {
	                    // the neighbor can be reached with smaller cost.
	                    // Since its f value has been updated, we have to
	                    // update its position in the open list
	                    openList.updateNode(neighbor, node.getF() - prevf);
	                }
	            }
	        } // end for each neighbor
	    } // end while not open list empty

	    // fail to find the path
	    return null;
	}
	
	/**
	 * Find and return the path. The resulting collection should never be modified, copy the values instead.
	 * The reason is caching of the results in memory to avoid memory allocation
	 * @return The path, including the end point
	 */
	public List<T> findPath(int startX, int startY, int endX, int endY, NavigationGrid<T> grid) {
		return findPath(grid.getCell(startX, startY), grid.getCell(endX, endY), grid); 	    
	}

}
