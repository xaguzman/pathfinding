package org.xguzm.pathfinding.tests;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class Launcher {
	public static void main(String[] args) {
		new LwjglApplication(new MapLoadingTest());
	}
}
