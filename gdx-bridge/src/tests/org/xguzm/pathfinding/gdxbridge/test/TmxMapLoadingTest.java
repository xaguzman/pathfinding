package org.xguzm.pathfinding.gdxbridge.test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.maps.tiled.TiledMap;
import org.xguzm.pathfinding.gdxbridge.NavTmxMapLoader;
import org.xguzm.pathfinding.gdxbridge.NavigationTiledMapLayer;
import org.xguzm.pathfinding.grid.GridCell;
import org.xguzm.pathfinding.grid.finders.AStarGridFinder;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by gdlxguzm on 7/15/2015.
 */
public class TmxMapLoadingTest {


    public void TestMapLoading(){
        System.out.println("\nRunning TmxMapLoadingTest.TestMapLoading");

        String loadFile = "tmx-test.tmx";
        MapLoadingGdxApp app = new MapLoadingGdxApp(loadFile);

        try{
            new LwjglApplication(app);
            while (!app.loaded){}
        }catch(Exception ex){
            fail(ex.getMessage());
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

            TiledMap map = new NavTmxMapLoader(new ClassPathResolver()).load(tmxFile);
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
