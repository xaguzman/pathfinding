package org.xguzm.pathfinding.grid;

import java.util.ArrayList;
import java.util.List;

import org.xguzm.pathfinding.NavigationGraph;
import org.xguzm.pathfinding.NavigationNode;
import org.xguzm.pathfinding.PathFinderOptions;
import org.xguzm.pathfinding.grid.finders.GridFinderOptions;

/**
 * A {@link NavigationGraph} which is represented as a grid or a table.
 * The nodes are accessible through (x, y) coordinates. 
 * 
 * @author Xavier Guzman
 *
 * @param <T> only classes extending {@link GridCell} can be used within this graph
 */
public class NavigationGrid<T extends NavigationGridGraphNode> implements NavigationGridGraph<T> {
	protected int width;
	protected int height;
	private List<T> neighbors = new ArrayList<T>();
	
	/** The nodes contained in the grid. They are stored as Grid[x][y] */
	protected T[][] nodes;
	

	/**
	 * Creates an grid with no nodes.
	 * If this constructor is used, make sure to call {@link NavigationGrid#setNodes(NavigationGridGraphNode[][])} before trying to make
	 * use of the grid cells.
	 */ 
	public NavigationGrid(){ 
		this(null); 
	}
	

	/** 
	 * Constructs the grid by using the given nodes. 
	
	 * This is the old way to biuld the grid, which now just calls {@link #NavigationGrid(T[][] nodes, boolean)} with autoAssignXY = false.
	 * Kept only for compatibility with older vesions.
	 * 
	 * @param nodes the ready-to-use nodes
	 * */
	@Deprecated	
	public NavigationGrid(T[][] nodes){
		this(nodes, false);
	}
	
	/** Constructs the grid by using the given nodes. If autoAssignXY == true, the grid will automatically assign x, y values, under
	 * the assumption that nodes[0][0] is the lower left corner of the grid. 
	 * 
	 * <pre>
	 *   -----------------------
	 *  |  0,2	|       |  2,2  |
	 *   -----------------------
	 *  |      |       |       |
	 *   -----------------------
	 *  |  0,0	|       |  2,0  |
	 *   -----------------------
	 *  
	 * </pre>
	 * 
	 * 
	 * @param nodes the nodes which compose the graph. Can be null if you plan to set the nodes later
	 * @param autoAssignXY wetherTo automatically assign coordinates to your nodes 
	 * */
	public NavigationGrid(T[][] nodes, boolean autoAssignXY){
		setNodes(nodes, autoAssignXY);		
	}
		
	@Override
	public T getCell(int x, int y) {
	    return this.contains(x, y)  ? this.nodes[x][y] : null; 
	}
	
	@Override
	public void setCell(int x, int y, T cell){
		if ( this.contains(x, y) )
			nodes[x][y] = cell;
	}

	/**
	 * Determine whether the node at the given position is walkable.
	 * 
	 * @param x - The x / column coordinate of the node.
	 * @param y - The y / row coordinate of the node.
	 * @return true if the node at [x,y] is walkable, false if it is not walkable (or if [x,y] is not within the grid's limit)
	 */
	public boolean isWalkable(int x, int y) {
	    return this.contains(x, y) && this.nodes[x][y].isWalkable();
	};


	/**
	 * Determine wether the given x,y pair is within the bounds of this grid
	 * @param x - The x / column coordinate of the node.
	 * @param y - The y / row coordinate of the node.
	 * @return true if the (x,y) is within the boundaries of this grid
	 */
	public boolean contains(int x, int y) {
	    return (x >= 0 && x < this.width) && (y >= 0 && y < this.height);
	};


	/**
	 * Set whether the node on the given position is walkable.
	 * 
	 * @param x - The x / column coordinate of the node.
	 * @param y - The y / row coordinate of the node.
	 * @param walkable - Whether the position is walkable.
	 * 
	 * @throws IndexOutOfBoundsException if the coordinate is not inside the grid.
	 */
	public void setWalkable(int x, int y, boolean walkable) {
	    this.nodes[x][y].setWalkable(walkable);
	};
	

	@Override
	public List<T> getNeighbors(T cell) {
		return null;
	}

	/**
	 * Get the neighbors of the given node.
	 *
	 *<pre>
	 *     offsets      diagonalOffsets:
	 *  +---+---+---+    +---+---+---+
	 *  |   | 0 |   |    | 4 |   | 5 |
	 *  +---+---+---+    +---+---+---+
	 *  | 3 |   | 1 |    |   |   |   |
	 *  +---+---+---+    +---+---+---+
	 *  |   | 2 |   |    | 6 |   | 7 |
	 *  +---+---+---+    +---+---+---+
	 * </pre>
	 * 
	 * @param node
	 * @param opt
	 */
	@Override
	public List<T> getNeighbors(T node, PathFinderOptions opt) {
		GridFinderOptions options = (GridFinderOptions) opt;
		boolean allowDiagonal = options.allowDiagonal;
		boolean dontCrossCorners = options.dontCrossCorners;
		int yDir = options.isYDown ?  -1 : 1;
	    int x = node.getX(), y = node.getY();
	    neighbors.clear();
        boolean s0 = false, d0 = false, s1 = false, d1 = false,
        		s2 = false, d2 = false, s3 = false, d3 = false;

	    // up
	    if (isWalkable(x, y + yDir)) {
	        neighbors.add(nodes[x][y  + yDir]);
	        s0 = true;
	    }
	    // right
	    if (isWalkable(x+1, y)) {
	        neighbors.add(nodes[x + 1][y]);
	        s1 = true;
	    }
	    // down
	    if (isWalkable(x, y - yDir)) {
	        neighbors.add(nodes[x][y - yDir]);
	        s2 = true;
	    }
	    // left
	    if (isWalkable(x - 1, y)) {
	        neighbors.add(nodes[x - 1][y]);
	        s3 = true;
	    }
	    
	    if (!allowDiagonal) {
	        return neighbors;
	    }

	    if (dontCrossCorners) {
	        d0 = s3 && s0;
	        d1 = s0 && s1;
	        d2 = s1 && s2;
	        d3 = s2 && s3;
	    } else {
	        d0 = s3 || s0;
	        d1 = s0 || s1;
	        d2 = s1 || s2;
	        d3 = s2 || s3;
	    }

	    // up left
	    if (d0 && this.isWalkable(x - 1, y + yDir)) {
	        neighbors.add(nodes[x-1][y + yDir]);
	    }
	    // up right
	    if (d1 && this.isWalkable(x + 1, y + yDir)) {
	        neighbors.add(nodes[x + 1][y + yDir]);
	    }
	    // down right
	    if (d2 && this.isWalkable(x + 1, y - yDir)) {
	        neighbors.add(nodes[x + 1][y - yDir]);
	    }
	    // down left
	    if (d3 && this.isWalkable(x - 1, y - yDir)) {
	        neighbors.add(nodes[x - 1][y - yDir]);
	    }

	    return neighbors;
	}

	@Override
	public float getMovementCost(T node1, T node2, PathFinderOptions opt) {
		
		if (node1 == node2)
			return 0;
		
		GridFinderOptions options = (GridFinderOptions)opt;
		GridCell cell1 = (GridCell) node1, cell2 = (GridCell) node2;
		return cell1.x == cell2.x || cell1.y == cell2.y  ? 
				options.orthogonalMovementCost : options.diagonalMovementCost;
	}
	
	@Override
	public boolean isWalkable(T node) {
		GridCell c = (GridCell)node;
		return isWalkable(c.x, c.y);
	};
	
	public T[][] getNodes(){
		return nodes;
	}
	
	public void setNodes(T[][] nodes){
		setNodes(nodes, true);
	}
	
	/** Sets the grid nodes. If autoAssignXY == true, the grid will automatically assign x, y values, under
	 * the assumption that nodes[0][0] is the lower left corner of the grid. 
	 * 
	 * <pre>
	 *   -----------------------
	 *  |  0,2	|       |  2,2  |
	 *   -----------------------
	 *  |      |       |       |
	 *   -----------------------
	 *  |  0,0	|       |  2,0  |
	 *   -----------------------
	 * </pre>
	 * 
	 * @param nodes the nodes which compose the graph. Can be null if you plan to set the nodes later
	 * @param autoAssignXY wetherTo automatically assign coordinates to your nodes 
	 * */
	public void setNodes(T[][] nodes, boolean autoAssignXY){
		if (nodes != null ){
			this.width = nodes.length;
			this.height = nodes[0].length;
			
			if (autoAssignXY){
				for(int x = 0; x < width; x++){
					for(int y = 0; y < height; y++){
						nodes[x][y].setX(x); 
						nodes[x][y].setY(y);
					}
				}
			}
		}else{
			this.width = 0;
			this.height = 0;
		}
		
		this.nodes = nodes;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public void setWidth(int width) {
		this.width = width;
		
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public void setHeight(int height) {
		this.height = height;
	}
	
	@Override
	public boolean lineOfSight(NavigationNode from, NavigationNode to){
		if (from == null || to == null)
			return false;
		
		NavigationGridGraphNode node = (NavigationGridGraphNode) from, neigh = (NavigationGridGraphNode) to;
		int x1 = node.getX(), y1 = node.getY();
		int x2 = neigh.getX(), y2 = neigh.getY();
		int dx = Math.abs(x1 - x2);
		int dy = Math.abs(y1 - y2);
		int xinc = (x1 < x2) ? 1 : -1;
		int yinc = (y1 < y2) ? 1 : -1;
		
		int error = dx - dy;
		
		for ( int n = dx + dy; n > 0; n--){
			if (!isWalkable(x1, y1)) 
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
