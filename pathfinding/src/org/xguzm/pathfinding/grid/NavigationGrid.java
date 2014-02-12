package org.xguzm.pathfinding.grid;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.xguzm.pathfinding.NavigationGraph;
import org.xguzm.pathfinding.PathFinderOptions;
import org.xguzm.pathfinding.grid.finders.GridFinderOptions;

public class NavigationGrid<T extends GridCell> implements NavigationGraph<T> {
	protected int width;
	protected int height;
	private List<T> neighbors = new ArrayList<T>();
	//private GridFinderOptions defaultOptions = new GridFinderOptions();
	
	/** The nodes contained in the grid. They are stored as Grid[x][y] */
	protected T[][] nodes;

	//public NavigationGrid(Class<T> clazz){}
	
	public NavigationGrid(int width, int height, Class<T> clazz) throws InstantiationException, IllegalAccessException {
		this(width, height, clazz, null);
	}
	
	/**
	 * The Grid class, which serves as the encapsulation of the layout of the nodes.
	 * @param width Number of columns of the grid.
	 * @param height Number of rows of the grid.
	 * @param matrix - A matrix( columns, rows) representing  the walkable status of the nodes(0 for walkable, everything else
	 * for unwalkable).
	 *     If the matrix is not supplied, all the nodes will be walkable.  
	 * @throws IllegalAccessException 
	 * @throws InstantiationException */
	public NavigationGrid(int width, int height, Class<T> clazz, int[][] matrix ) throws InstantiationException, IllegalAccessException {
	    this.width = width;
	    this.height = height;
	    this.nodes = buildNodes(width, height, clazz, matrix);
	}

	public NavigationGrid(int width, int height, T[][] nodes){
		this.width = width;
		this.height = height;
		this.nodes = nodes;
	}

	/**
	 * Build and return the nodes.
	 * @param columns the width (in cells) of this grid
	 * @param rows the height (in cells) of this grid
	 * @param matrix - A matrix representing the walkable status of the nodes. 0 is walkable, any other
	 * value will be unwalkable
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@SuppressWarnings("unchecked")
	private T[][] buildNodes(int columns, int rows, Class<T> clazz, int[][] matrix) throws InstantiationException, IllegalAccessException {
		if (matrix == null) {
	        return nodes;
	    }
		
		if (matrix.length != columns || matrix[0].length != rows) {
	        throw new IllegalArgumentException("Matrix size does not fit");
	    }
		
		T[][]nodes = (T[][])Array.newInstance(clazz, columns, rows);
		
		int i, j;
	    for (i = 0; i < columns; ++i) {
	        for (j = 0; j < rows; ++j) {
	            nodes[i][j] = clazz.newInstance();
	            nodes[i][j].x = i;
	            nodes[i][j].y = j;
	            nodes[i][j].setWalkable(matrix[i][j] == 0);
	        }
	    }
	    return nodes;
	};


	public T getCell(int x, int y) {
	    return this.contains(x, y)  ? this.nodes[x][y] : null; 
	};


	/**
	 * Determine whether the node at the given position is walkable.
	 * (Also returns false if the position is outside the grid.)
	 * @param x - The x / column coordinate of the node.
	 * @param y - The y / row coordinate of the node.
	 * @return true if the node at (x,y) is walkable
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
	 * @param options
	 */
	@Override
	public List<T> getNeighbors(T node, PathFinderOptions opt) {
		GridFinderOptions options = (GridFinderOptions) opt;
		boolean allowDiagonal = options.allowDiagonal;
		boolean dontCrossCorners = options.dontCrossCorners;
		int yDir = options.isYDown ?  -1 : 1;
	    int x = node.x, y = node.y;
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
}
