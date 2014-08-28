package org.xguzm.pathfinding.grid.finders;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.xguzm.pathfinding.BHeap;
import org.xguzm.pathfinding.NavigationGraph;
import org.xguzm.pathfinding.PathFinder;
import org.xguzm.pathfinding.Util;
import org.xguzm.pathfinding.grid.NavigationGridGraph;
import org.xguzm.pathfinding.grid.NavigationGridGraphNode;

public class ThetaStarGridFinder<T extends NavigationGridGraphNode> implements PathFinder<T>{

	private GridFinderOptions defaultOptions;
	BHeap<T> openList;
	public int jobId;
		
	public ThetaStarGridFinder(Class<T> clazz, GridFinderOptions opt) {
	    this.defaultOptions = opt ;
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
		});
	}
	
	public List<T> findPath(int startX, int startY, int endX, int endY, NavigationGridGraph<T> grid) {
		return findPath(grid.getCell(startX, startY), grid.getCell(endX, endY), grid); 	    
	}
	
	public List<T> findPath(T startNode, T endNode, NavigationGraph<T> graph) {
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
	    startNode.setOpenedOnJob( job );
	    
	    while (openList.size > 0) {
	    	
	        // pop the position of node which has the minimum 'f' value.
	        node = openList.pop();
	        node.setClosedOnJob(job);
	        

	        // if reached the end position, construct the path and return it
	        if (node == endNode) {
	            return Util.backtrace(endNode);
	        }

	        // get neighbors of the current node
	        neighbors.clear();
	        neighbors.addAll( graph.getNeighbors(node, defaultOptions)) ;
	        for (int i = 0, l = neighbors.size(); i < l; ++i) {
	            neighbor = neighbors.get(i);

	            if (neighbor.getClosedOnJob() == job || !graph.isWalkable(neighbor)) {
	                continue;
	            }

	            // get the distance between current node and the neighbor and calculate the next g score
	            ng = node.getG() + graph.getMovementCost(node, neighbor, defaultOptions);

	            // check if the neighbor has not been inspected yet, or can be reached with smaller cost from the current node
	            if (neighbor.getOpenedOnJob() != job || ng < neighbor.getG()) {
	            	float prevf = neighbor.getF();
	                neighbor.setG(ng);

	                neighbor.setH(defaultOptions.heuristic.calculate(neighbor, endNode));
	                neighbor.setF( neighbor.getG() + neighbor.getH());
	                neighbor.setParent(node);

	                if (neighbor.getOpenedOnJob() != job) {
	                    openList.add(neighbor);
	                    neighbor.setOpenedOnJob(job);
	                } else {
	                    // the neighbor can be reached with smaller cost.
	                    // Since its f value has been updated, we have to update its position in the open list
	                    openList.updateNode(neighbor, node.getF() - prevf);
	                }
	            }
	        } 
	    } 

	    // fail to find the path
	    return null;
	}
	
	private void setVertext(NavigationGridGraphNode node){
		
	}
	
	private boolean lineOfSight(NavigationGridGraphNode node, NavigationGridGraphNode neighbor, NavigationGridGraph<T> graph){
		int x1 = node.getX(), y1 = node.getY();
		int x2 = neighbor.getX(), y2 = neighbor.getY();
		int dx = Math.abs(x1 - x2);
		int dy = Math.abs(y1 - y2);
		int xinc = (x1 < x2) ? 1 : -1;
		int yinc = (y1 < y2) ? 1 : -1;
		
		int error = dx - dy;
		
		for ( int n = dx + dy; n > 0; n--){
			if (!graph.isWalkable(x1, y1)) 
				return false; 
			int e2 = 2*error;
			if ( e2 > -dy){
				error -= dy;
				x1 += xinc;
			}		
			if (e2 < dx ){
				error += dx;
				y1 += yinc;
			}
		}
		
		return true;
		
	}

}