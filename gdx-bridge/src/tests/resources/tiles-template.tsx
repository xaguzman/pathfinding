<?xml version="1.0" encoding="UTF-8"?>
<tileset name="tiles" tilewidth="32" tileheight="32">
 <image source="tiles.png" width="96" height="96"/>
 <tile id="0">
  <properties>
   <property name="type" value="spawn"/>
   <property name="walkable" value="1"/>
  </properties>
 </tile>
 <tile id="1">
  <properties>
   <property name="walkable" value="1"/>
  </properties>
 </tile>
 <tile id="2">
  <properties>
   <property name="type" value="archer"/>
   <property name="dir" value="right" />
  </properties>
 </tile>
 <tile id="3">
  <properties>
   <property name="type" value="goal"/>
   <property name="walkable" value="1"/>
  </properties>
 </tile>
 <tile id="4">
  <properties>
   <property name="type" value="archer"/>
   <property name="dir" value="left" />
  </properties>
 </tile>
</tileset>
