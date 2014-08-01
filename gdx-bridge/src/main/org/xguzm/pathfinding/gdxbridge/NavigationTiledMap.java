package org.xguzm.pathfinding.gdxbridge;

import org.xguzm.pathfinding.grid.GridCell;
import org.xguzm.pathfinding.grid.NavigationGrid;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class NavigationTiledMap extends NavigationGrid<GridCell> {

	protected TiledMap tiledMap;
	protected String navigationLayerName = "navigation";
	protected String navigationProperty = "walkable";
	protected String navigationClosedValue = "0";

	public NavigationTiledMap(TiledMap tiledMap){
		this.tiledMap = tiledMap;
		height = tiledMap.getProperties().get("height", Integer.class);
		width = tiledMap.getProperties().get("width", Integer.class);
	}
	
	public NavigationTiledMap(String tmxFilePath){
		this(new TmxMapLoader().load(tmxFilePath));
	}
	
	public void loadNavigationInfo(){
		loadNavigationInfo(navigationLayerName, navigationProperty, navigationClosedValue);
		
	}
	
	public void loadNavigationInfo(String navigationLayerName, String navigationProperty, String navigationClosedValue){
		TiledMapTileLayer dataLayer = (TiledMapTileLayer) tiledMap.getLayers().get(navigationLayerName);
		
		nodes = new GridCell[width][height];
		dataLayer.setVisible(false);
		
		for (int row = 0; row < height; row++)
			for (int col = 0; col < width; col++){
				Cell cell = dataLayer.getCell(col, row);
				
				GridCell t  = new GridCell(col, row, false);
				nodes[col][row] = t;
				
				if ( cell != null){
					MapProperties prop = cell.getTile().getProperties();
					String walkableProp = prop.get(navigationProperty, navigationClosedValue, String.class);
					
					t.setWalkable( !walkableProp.equals(navigationClosedValue) );
				}
			}
	}
	
}
