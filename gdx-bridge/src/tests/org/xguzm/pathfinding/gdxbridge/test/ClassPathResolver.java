package org.xguzm.pathfinding.gdxbridge.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;

/**
 * Created by gdlxguzm on 7/27/2015.
 */
public class ClassPathResolver implements FileHandleResolver {
    @Override
    public FileHandle resolve(String fileName) {
        return Gdx.files.classpath(fileName);
    }
}
