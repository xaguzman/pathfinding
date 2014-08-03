package org.xguzm.pathfinding.tests;

import java.util.List;

import org.xguzm.pathfinding.gdxbridge.NavTmxMapLoader;
import org.xguzm.pathfinding.gdxbridge.NavigationTiledMapLayer;
import org.xguzm.pathfinding.grid.GridCell;
import org.xguzm.pathfinding.grid.finders.AStarGridFinder;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;

public class MapLoadingTest extends ApplicationAdapter{

	String tmxFile = "assets/tmx-test.tmx";
	
	@Override
	public void create() {
		TiledMap map = new NavTmxMapLoader().load(tmxFile);
		AStarGridFinder<GridCell> finder = new AStarGridFinder<GridCell>(GridCell.class);
		
		NavigationTiledMapLayer nav = (NavigationTiledMapLayer)map.getLayers().get("navigation");
		
		List<GridCell> path = finder.findPath(0, 0, 9, 9, nav);
		
		for(GridCell c : path){
			Gdx.app.log("Map Loading Test - path: ", c.toString());
		}
		
		if(path.size() == 0)
			Gdx.app.log("Map Loading Test - result: ", "failed to find path from 0,0 to 9,9");
		else
			Gdx.app.log("Map Loading Test - result: ", "success getting path from 0,0 to 9,9");
	}
	
	@Override
	public void render() {
		Gdx.app.exit();
	}
}
