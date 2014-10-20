package org.xguzm.pathfinding.grid.finders;

import org.xguzm.pathfinding.Heuristic;
import org.xguzm.pathfinding.PathFinderOptions;
import org.xguzm.pathfinding.grid.NavigationGridGraph;
import org.xguzm.pathfinding.grid.heuristics.ManhattanDistance;

/**
 * The options for the pathfinding behavior on a {@link NavigationGridGraph}.
 * 
 * @author Xavier Guzman
 */
public class GridFinderOptions extends PathFinderOptions {

	/** 
	 * Wether diagonal movement is allowed within the grid.
	 * 
	 * <p>
	 * <b>Note:</b> This will be ignored in {@link JumpPointFinder}, as diagonal movement is required for it
	 * <p>
	 * 
	 * Default value is true
	 */
	public boolean allowDiagonal;
	
	/** When true, diagonal movement requires both neighbors to be open.
	 * When false, diagonal movement can be achieved by having only one open nighbor
	 * <p>
	 *  
	 * Example: To go from (1,1) to (2,2) when this is set to true, where (x) denotes a non walkable cell,
	 * the following applies
	 * 
	 * <pre>
	 *                 Valid           Invalid
	 *             +---+---+---+    +---+---+---+
	 *             |   |   | 0 |    |   | x | 0 |
	 *             +---+---+---+    +---+---+---+
	 * when True   |   | 0 |   |    |   | 0 |   |
	 *             +---+---+---+    +---+---+---+
	 *             |   |   |   |    |   |   |   |
	 *             +---+---+---+    +---+---+---+
	 *  
	 *             
	 *             +---+---+---+    
	 *             |   | x | 0 |    
	 *             +---+---+---+    
	 * when false  |   | 0 |   |    none
	 *             +---+---+---+    
	 *             |   |   |   |    
	 *             +---+---+---+    
	 * </pre>
	 * 
	 * If {@link #allowDiagonal} is false, this setting is ignored.
	 * <p>
	 * Default value is true
	 */
	public boolean dontCrossCorners;
	

	/** When false, (0,0) is located at the bottom left of the grid. When true, (0,0) is located
	 * at the top left of the grid
	 *
	 *<p>
	 * Default value is false
	 */
	public boolean isYDown ;
	
	/** The cost of moving one cell over the x or y axis */
	public float orthogonalMovementCost;
	
	/** The cost of moving one cell over both the x and y axis  */
	public float diagonalMovementCost;
	
	/**
	 * Constructs a new GridFinderOptions with the default values:
	 * <pre>
	 * {@link #allowDiagonal} = true  
	 * {@link #dontCrossCorners} = true
	 * {@link #heuristic} = {@link ManhattanDistance}
	 * {@link #isYDown} = false
	 * {@link #orthogonalMovementCost} = 1
	 * {@link #diagonalMovementCost} = 1.4
	 * </pre>
	 */
	public GridFinderOptions(){
		this(true, true, new ManhattanDistance(), false, 1, 1.4f);
	}
	
	public GridFinderOptions(boolean allowDiagonals, boolean dontCrossCorners, Heuristic heuristic, boolean isyDown, float orthogonalMovementCost, float diagonalMovementCost){
		this.allowDiagonal = allowDiagonals;
		this.dontCrossCorners = dontCrossCorners;
		this.heuristic = heuristic;
		this.isYDown = isyDown;
		this.orthogonalMovementCost = orthogonalMovementCost;
		this.diagonalMovementCost = diagonalMovementCost;
	}

}
