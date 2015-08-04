The gdx-bridge is an extension for the pathfinding library which acts, as the name implies, as a small bridge which allows
you to skip the creation of your own GridCell's by doing loops and all kind of boring stuff.

For simplicity, the gdx-bridge shares version with the pathfinding library.

This extension provides quick creation of a [NavigationGrid](https://github.com/xaguzman/pathfinding/blob/master/pathfinding/src/main/org/xguzm/pathfinding/grid/NavigationGrid.java).
by doing some small configurations to your tmx files.


__________

## Installing
Since you are using libgdx, you probably are using gradle. Just add it as a dependency to your core project.

>   compile "com.github.xaguzman:pathfinding-gdx-bridge:0.2.6"

If you are using libgdx < 1.5.0 you need to use version 0.2.4 instead

>   compile "com.github.xaguzman:pathfinding-gdx-bridge:0.2.4"

__________

## How to use

This extension contains a new implementation for libgdx's [TiledMapTileLayer](https://github.com/libgdx/libgdx/blob/master/gdx/src/com/badlogic/gdx/maps/tiled/TiledMapTileLayer.java), called
[NavigationTiledMapLayer](https://github.com/xaguzman/pathfinding/blob/master/gdx-bridge/src/main/org/xguzm/pathfinding/gdxbridge/NavigationTiledMapLayer.java), the layer is basically a wrapper
for the pathfinding's NavigationGrid.

To generate this new layer, you need to load your map via the [NavTmxMapLoader](https://github.com/xaguzman/pathfinding/blob/master/gdx-bridge/src/main/org/xguzm/pathfinding/gdxbridge/NavTmxMapLoader.java)
which comes with gdx-bridge.

For the NavTmxMapLoader to work, your tmx map needs to have two things:
1.  You need to have certain tiles in one of your tilesets which has a custom property which specifies whether a tile is walkable or not (by default, this custom property name is "walkable", but you can name it whatever you want).
The value of this custom property can be whatever you want.
2.  Make sure to use a layer exclusively for navigation data, this layer should use your tiles which contain the custom property mentioned above.

## Customizing

After your tmx map is setup with navigation data, you might want to to use different names for your layer / property.
You can configure your NavTmxMapLoader to correctly read your tmx map with your desired values. There are 4 possible
configuration values for the loader, all of them via constructors:

*   navigationLayerName: This tells NavTmxMapLoader what layer to consider for creating the navigation data, every other layer will be parsed by libgdx's default TmxMapLoader. Default value is "navigation".
*   navigationProperty: The name of the property in the tiles which determines wether a tile is walkable or not. Default is "walkable";
*   navigationClosedValue: The value which represents that a tile can not be walked, any other value will be considered as open. When the layer is being loaded,
if the `navigationProperty` is not present, this value is assumed. Default value is "0".
*   handler: A libgdx `FileHandlerResolver` which will be used to load the tmx file. You should rarely need to use anything other than the default here (which is an InternalFileHandleResolver).

**Be aware that the NavTmxMapLoader will automatically make your "navigation" layer invisible.**

If you want further customization, you can always just override the NavTmxMapLoader, or create your own :).

## Example

Assuming you are going to use the default settings, this is what you need to do:
Add a custom property to any tiles in any of your tilesets that you want to make walkable called "walkable", set their value to whatever you want, except for "0" (even empty will work).
Add a layer named "navigation" to your tmx map. In this layer, you should add all your tiles with "walkable" property.
Save your map.

Load your NavigationGrid data:
```java
TiledMap map = new NavTmxMapLoader().load("your/tmx/file.tmx");
NavigationTiledMapLayer navLayer = (NavigationTiledMapLayer) map.getLayer("navigation");

// then use your finder as you usually would
AStarGridFinder finder = //create your finder here.

finder.findPath( x1, y1, x2, y2, navLayer);
```

