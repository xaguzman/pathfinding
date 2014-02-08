package org.xguzm.pathfinding.grid.finders;

import org.xguzm.pathfinding.grid.Heuristic;

/**
 * The options for the pathfinding behavior.
 * @author Xavier
 */
public class GridFinderOptions {

	/** Wether diagonal movement is allowed within the grid
	 * </p>
	 * Default value is true
	 */
	public boolean allowDiagonal;
	
	/** When true, diagonal movement requires both neighbors to be open.
	 * When false, diagonal movement can be achieved by having only one open nighbor
	 *  
	 * Example: To go from (1,1) to (2,2) when this is set to true, where (x) denotes a non walkable cell,
	 * the following applies
	 * 
	 * <pre>
	 *                 Valid           Invalid
	 *             +---+---+---+    +---+---+---+
	 *             |   |   |   |    |   | x |   |
	 *             +---+---+---+    +---+---+---+
	 * when True   |   |   |   |    |   |   |   |
	 *             +---+---+---+    +---+---+---+
	 *             |   |   |   |    |   |   |   |
	 *             +---+---+---+    +---+---+---+
	 *  
	 *             
	 *             +---+---+---+    
	 *             |   | x |   |    
	 *             +---+---+---+    
	 * when false  |   |   |   |    none
	 *             +---+---+---+    
	 *             |   |   |   |    
	 *             +---+---+---+    
	 * </pre>
	 * 
	 * If {@link #allowDiagonal} is false, this setting is ignored.
	 * </p>
	 * Default value is true
	 */
	public boolean dontCrossCorners;
	
	/** The {@link Heuristic} to calculate the distance from one node to another 
	 * </p>
	 * Default value is {@link Heuristic#Manhattan}
	 */
	public Heuristic heuristic;
	
	/** When false, (0,0) is located at the bottom left of the grid. When true, (0,0) is located
	 * at the top left of the grid
	 *
	 *</p>
	 * Default value is false
	 */
	public boolean isYDown ;
	
	/**
	 * Constructs a new GridFinderOptions with the default values:
	 * <pre>
	 * {@link #allowDiagonal} = true  
	 * {@link #dontCrossCorners} = true
	 * {@link #heuristic} = {@link Heuristic#Manhattan}
	 * {@link #isYDown} = false
	 */
	public GridFinderOptions(){
		this(true, true, Heuristic.Manhattan, false);
	}
	
	/**
	 * @param {@link #allowDiagonal}  
	 * @param {@link #dontCrossCorners}
	 * @param {@link #heuristic}
	 * @param {@link #isYDown}
	 */
	public GridFinderOptions(boolean allowDiagonals, boolean dontCrossCorners, Heuristic heuristic, boolean isyDown){
		this.allowDiagonal = allowDiagonals;
		this.dontCrossCorners = dontCrossCorners;
		this.heuristic = heuristic;
		this.isYDown = isyDown;
	}

}
