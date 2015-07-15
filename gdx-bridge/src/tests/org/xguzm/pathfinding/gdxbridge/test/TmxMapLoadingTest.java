package org.xguzm.pathfinding.gdxbridge.test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.resolvers.AbsoluteFileHandleResolver;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.maps.tiled.TiledMap;
import org.junit.Test;
import org.xguzm.pathfinding.gdxbridge.NavTmxMapLoader;
import org.xguzm.pathfinding.gdxbridge.NavigationTiledMapLayer;
import org.xguzm.pathfinding.grid.GridCell;
import org.xguzm.pathfinding.grid.finders.AStarGridFinder;

import java.net.URL;
import java.util.List;
import static org.junit.Assert.*;

/**
 * Created by gdlxguzm on 7/15/2015.
 */
public class TmxMapLoadingTest {

    @Test
    public void TestMapLoading(){
        System.out.println("\nRunning TmxMapLoadingTest.TestMapLoading");

//        String loadFile = "tmx-test.tmx";
        String loadFile = "tiles.png";

        URL url = this.getClass().getResource(loadFile);
//        System.out.println( "");


        MapLoadingGdxApp app = new MapLoadingGdxApp("");
        try {
            new LwjglApplication(app);
            while (!app.loaded) { }
        }catch(Exception ex){
            fail(ex.getMessage());
        }

        int i = 0;
        System.out.println("  Path found in " + app.tmxFile);
        for(GridCell c : app.path){
            System.out.println("    (" + (i++) + ") " + c.toString());
        }

        assertTrue("failed to find path from 0,0 to 9,9", app.path.size() > 0);
    }

    class MapLoadingGdxApp extends ApplicationAdapter{
        String tmxFile;
        List<GridCell> path;
        boolean loaded = false;

        public MapLoadingGdxApp(String file){
            this.tmxFile = file;
        }

        @Override
        public void create() {

            TiledMap map = new NavTmxMapLoader(new AbsoluteFileHandleResolver()).load(tmxFile);
            AStarGridFinder<GridCell> finder = new AStarGridFinder<GridCell>(GridCell.class);

            NavigationTiledMapLayer nav = (NavigationTiledMapLayer)map.getLayers().get("navigation");

            path = finder.findPath(0, 0, 9, 9, nav);

            loaded = true;
        }

        @Override
        public void resize(int width, int height) {
            Gdx.app.exit();
        }
    }
}
