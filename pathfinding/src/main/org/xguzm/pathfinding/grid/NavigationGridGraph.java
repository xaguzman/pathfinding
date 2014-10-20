package org.xguzm.pathfinding.grid;

import org.xguzm.pathfinding.NavigationGraph;

/**
 * A {@link NavigationGraph} which is represented as a grid or a table.
 * The nodes are accessible through (x, y) coordinates.
 * 
 * The default implementation is {@link GridCell}.
 * 
 * @author Xavier Guzman
 *
 * @param <T> only classes implementing {@link NavigationGridGraphNode} can be used within this graph
 */
public interface NavigationGridGraph<T extends NavigationGridGraphNode> extends NavigationGraph<T>{
	
	T getCell(int x, int y);
	void setCell(int x, int y, T node);

	/**
	 * Determine wether the given x,y pair is within the bounds of this grid
	 * @param x - The x / column coordinate of the node.
	 * @param y - The y / row coordinate of the node.
	 * @return true if the (x,y) is within the boundaries of this grid
	 */
	boolean contains(int x, int y);


	/**
	 * Set whether the node on the given position is walkable.
	 * 
	 * @param x - The x / column coordinate of the node.
	 * @param y - The y / row coordinate of the node.
	 * @param walkable - Whether the position is walkable.
	 * 
	 * @throws IndexOutOfBoundsException if the coordinate is not inside the grid.
	 */
	void setWalkable(int x, int y, boolean walkable);
	
	/**
	 * Determine whether the node at the given position is walkable.
	 * 
	 * @param x - The x / column coordinate of the node.
	 * @param y - The y / row coordinate of the node.
	 * @return true if the node at [x,y] is walkable, false if it is not walkable (or if [x,y] is not within the grid's limit)
	 */
	boolean isWalkable(int x, int y);
	
	
	T[][] getNodes();
	
	void setNodes(T[][] nodes);
	
	int getWidth();
	
	void setWidth(int width);
	
	int getHeight();
	
	void setHeight(int height);
}
