package org.xguzm.pathfinding.grid.test;

import java.awt.Color;

import javax.swing.JComponent;

import org.xguzm.pathfinding.grid.GridCell;
import org.xguzm.pathfinding.grid.NavigationGrid;

public class GridRenderer extends JComponent {

	private Color clearColor = Color.WHITE;
	private Color blockColor = Color.BLACK;
	private Color pathColor = Color.GREEN;
	
	NavigationGrid<GridCell> grid;
	
	public GridRenderer(NavigationGrid<GridCell> grid){
		this.grid = grid;
	}
	
	
	
	protected void paintComponent(java.awt.Graphics g) {
		
	}
}
