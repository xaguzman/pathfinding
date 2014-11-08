package org.xguzm.pathfinding.gdxbridge;

import java.util.List;

import org.xguzm.pathfinding.NavigationNode;
import org.xguzm.pathfinding.PathFinderOptions;
import org.xguzm.pathfinding.grid.GridCell;
import org.xguzm.pathfinding.grid.NavigationGrid;
import org.xguzm.pathfinding.grid.NavigationGridGraph;

import com.badlogic.gdx.maps.MapLayer;

public class NavigationTiledMapLayer extends MapLayer implements NavigationGridGraph<GridCell>{
	
	NavigationGrid<GridCell> navGrid;
		
	public NavigationTiledMapLayer() {
		this(null);
	}
	
	public NavigationTiledMapLayer(GridCell[][] nodes){
		navGrid = new NavigationGrid<GridCell>(nodes);
	}
		
	@Override
	public void setCell(int x, int y, GridCell node) {
		navGrid.setCell(x, y, node);
	}

	@Override
	public List<GridCell> getNeighbors(GridCell node) {
		return navGrid.getNeighbors(node);
	}

	@Override
	public List<GridCell> getNeighbors(GridCell node, PathFinderOptions opt) {
		return navGrid.getNeighbors(node, opt);
	}

	@Override
	public float getMovementCost(GridCell node1, GridCell node2, PathFinderOptions opt) {
		return navGrid.getMovementCost(node1, node2, opt);
	}

	@Override
	public boolean isWalkable(GridCell node) {
		return navGrid.isWalkable(node);
	}

	@Override
	public GridCell getCell(int x, int y) {
		return navGrid.getCell(x, y);
	}

	@Override
	public boolean contains(int x, int y) {
		return navGrid.contains(x, y);
	}

	@Override
	public void setWalkable(int x, int y, boolean walkable) {
		navGrid.setWalkable(x, y, walkable);
	}

	@Override
	public boolean isWalkable(int x, int y) {
		return navGrid.isWalkable(x, y);
	}

	@Override
	public GridCell[][] getNodes() {
		return navGrid.getNodes();
	}

	@Override
	public void setNodes(GridCell[][] nodes) {
		navGrid.setNodes(nodes);
	}

	@Override
	public int getWidth() {
		return navGrid.getWidth();
	}

	@Override
	public void setWidth(int width) {
		navGrid.setWidth(width);
	}

	@Override
	public int getHeight() {
		return navGrid.getHeight();
	}

	@Override
	public void setHeight(int height) {
		navGrid.setHeight(height);
	}

	@Override
	public boolean lineOfSight(NavigationNode from, NavigationNode to) {
		return navGrid.lineOfSight(from, to);
	}

}
