package org.xguzm.pathfinding.grid.finders;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.xguzm.pathfinding.BHeap;
import org.xguzm.pathfinding.Heuristic;
import org.xguzm.pathfinding.NavigationGraph;
import org.xguzm.pathfinding.PathFinder;
import org.xguzm.pathfinding.Util;
import org.xguzm.pathfinding.grid.GridCell;
import org.xguzm.pathfinding.grid.NavigationGrid;
import org.xguzm.pathfinding.grid.NavigationGridGraph;
import org.xguzm.pathfinding.grid.NavigationGridGraphNode;
import org.xguzm.pathfinding.grid.heuristics.EuclideanDistance;

/**
 * Optimization over A*. This will always use @{link {@link EuclideanDistance}, regardless of the one set in the passed {@link #options}. 
 * This should only be used on {@link NavigationGridGraph}
 * 
 * @author Xavier Guzman
 *
 * @param <T>
 *            any class extending from {@link GridCell}
 */
public class JumpPointFinder<T extends NavigationGridGraphNode> implements PathFinder<T> {
	BHeap<T> openList;
	private GridFinderOptions options;
	int jobId;

	private Heuristic euclideanDist = new EuclideanDistance();

	public JumpPointFinder(Class<T> clazz, GridFinderOptions options) {
		this.options = options;
		openList = new BHeap<T>(new Comparator<T>() {
			@Override
			public int compare(T o1, T o2) {
				if (o1 == null || o2 == null) {
					if (o1 == o2)
						return 0;
					if (o1 == null)
						return -1;
					else
						return 1;

				}
				return (int) (o1.getF() - o2.getF());
			}
		});
	}

	@Override
	public List<T> findPath(T startNode, T endNode, NavigationGraph<T> grid) {
		Util.validateNotNull(startNode, "Start node cannot be null");
		Util.validateNotNull(endNode, "End node cannot be null");

		if (jobId == Integer.MAX_VALUE)
			jobId = 0;
		int job = ++jobId;

		T node;

		startNode.setG(0);
		startNode.setF(0);

		// push the start node into the open list
		openList.clear();
		openList.add(startNode);
		startNode.setParent(null);
		startNode.setOpenedOnJob(job, this.getClass());

		while (openList.size > 0) {

			// pop the position of node which has the minimum 'f' value.
			node = openList.pop();
			node.setClosedOnJob(job, this.getClass());

			// if reached the end position, construct the path and return it
			if (node == endNode) {
				return Util.backtrace(endNode);
			}

			identifySuccesors(node, (NavigationGridGraph<T>) grid, job, startNode, endNode);
		}

		// fail to find the path
		return null;
	}

	/**
	 * Find and return the path. The resulting collection should never be
	 * modified, copy the values instead.
	 * 
	 * @return The path from [startX, startY](exclusive) to [endX, endY]
	 */
	public List<T> findPath(int startX, int startY, int endX, int endY, NavigationGridGraph<T> grid) {
		return findPath(grid.getCell(startX, startY), grid.getCell(endX, endY), grid);
	}

	private void identifySuccesors(T node, NavigationGridGraph<T> graph, int job, T start, T end) {
		List<T> neightbors = getNeighbors(node, graph);

		for (T neighbor : neightbors) {

			// Try to find a node to jump to:
			T jumpPoint = jump(neighbor, node, graph, start, end);

			if (jumpPoint == null || jumpPoint.getClosedOnJob(this.getClass()) == job)
				continue;

			boolean isDiagonalJump = (jumpPoint.getX() != node.getX())
					&& (jumpPoint.getY() != node.getY());
			if (isDiagonalJump && !options.allowDiagonal)
				continue;

			// get the distance between current node and the neighbor and
			// calculate the next g score
			float distance = euclideanDist.calculate(jumpPoint, node);
			float ng = node.getG() + distance;

			if (jumpPoint.getOpenedOnJob(this.getClass()) != job || ng < neighbor.getG()) {
				float prevf = jumpPoint.getF();
				jumpPoint.setG(ng);
				jumpPoint.setH(options.heuristic.calculate(jumpPoint, end));
				jumpPoint.setF(neighbor.getG() + neighbor.getH());
				jumpPoint.setParent(node);

				if (jumpPoint.getOpenedOnJob(this.getClass()) != job) {
					openList.add(jumpPoint);
					jumpPoint.setOpenedOnJob(job, this.getClass());
				} else {
					// the neighbor can be reached with smaller cost.
					// Since its f value has been updated, we have to update its
					// position in the open list
					openList.updateNode(neighbor, neighbor.getF() - prevf);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private List<T> getNeighbors(T node, NavigationGridGraph<T> grid) {
		T parent = (T) node.getParent();

		if (parent != null) {
			int px = parent.getX();
			int py = parent.getY();
			int x = node.getX(), y = node.getY();

			// get the normalized direction of travel
			int dx = clamp(-1, 1, (x - px));
			int dy = clamp(-1, 1, (y - py));
			dy *= options.isYDown ? -1 : 1;

			List<T> neighbors = new ArrayList<T>();
			boolean allowDiagonal = allowedDiagonalMovement(node, dx, dy, grid);

			// search diagonally
			if (dx != 0 && dy != 0) {
				if (grid.isWalkable(x, y + dy)) {
					neighbors.add(grid.getCell(x, y + dy));
				}
				if (grid.isWalkable(x + dx, y)) {
					neighbors.add(grid.getCell(x + dx, y));
				}
				if (grid.isWalkable(x, y + dy) || grid.isWalkable(x + dx, y)) {
					neighbors.add(grid.getCell(x + dx, y + dy));
				}
				if (!grid.isWalkable(x - dx, y) && grid.isWalkable(x, y + dy)) {
					neighbors.add(grid.getCell(x - dx, y + dy));
				}
				if (!grid.isWalkable(x, y - dy) && grid.isWalkable(x + dx, y)) {
					neighbors.add(grid.getCell(x + dx, y - dy));
				}
			} else {// search orthogonally
				if (dx == 0) {// on y
					if (grid.isWalkable(x, y + dy)) {
						neighbors.add(grid.getCell(x, y + dy));

						if (allowDiagonal && !grid.isWalkable(x + 1, y)) {
							neighbors.add(grid.getCell(x + 1, y + dy));
						}
						if (allowDiagonal && !grid.isWalkable(x - 1, y)) {
							neighbors.add(grid.getCell(x - 1, y + dy));
						}
					}

					// In case diagonal moves are forbidden
					if (!allowDiagonal) {
						if (grid.isWalkable(x + 1, y))
							neighbors.add(grid.getCell(x + 1, y));
						if (grid.isWalkable(x - 1, y))
							neighbors.add(grid.getCell(x - 1, y));
					}

				} else { // on x
					if (grid.isWalkable(x + dx, y)) {
						neighbors.add(grid.getCell(x + dx, y));

						if (allowDiagonal && !grid.isWalkable(x, y + 1)) {
							neighbors.add(grid.getCell(x + dx, y + 1));
						}
						if (allowDiagonal && !grid.isWalkable(x, y - 1)) {
							neighbors.add(grid.getCell(x + dx, y - 1));
						}
					}

					// In case diagonal moves are forbidden
					if (!allowDiagonal) {
						if (grid.isWalkable(x, y + 1))
							neighbors.add(grid.getCell(x, y + 1));
						if (grid.isWalkable(x, y - 1))
							neighbors.add(grid.getCell(x, y - 1));
					}
				}
			}
			return neighbors;
		}

		return grid.getNeighbors(node, options);
	}

	private T jump(T node, T parent, NavigationGridGraph<T> grid, T start, T end) {
		int x = node.getX(), y = node.getY();
		int parentX = parent.getX(), parentY = parent.getY();
		int dx = x - parentX;
		int dy = y - parentY;
		dy *= options.isYDown ? -1 : 1;

		if (!grid.isWalkable(x, y)) {
			return null;
		}

		if (x == end.getX() && y == end.getY()) {
			return grid.getCell(x, y);
		}

		boolean allowDiagonal = allowedDiagonalMovement(node, dx, dy, grid);

		// check for forced neighbors diagonally
		if (dx != 0 && dy != 0) {
			if ((grid.isWalkable(x - dx, y + dy) && !grid.isWalkable(x - dx, y))
					|| (grid.isWalkable(x + dx, y - dy) && !grid.isWalkable(x,
							y - dy))) {
				return node;
			}

		} else {
			if (dx != 0) { // moving along x
				if (allowDiagonal) {
					if ((grid.isWalkable(x + dx, y + 1) && !grid.isWalkable(x,
							y + 1))
							|| (grid.isWalkable(x + dx, y - 1) && !grid
									.isWalkable(x, y - 1))) {
						return node;
					}
				}
			} else { // moving along y
				if (allowDiagonal) {
					if ((grid.isWalkable(x + 1, y + dy) && !grid.isWalkable(
							x + 1, y))
							|| (grid.isWalkable(x - 1, y + dy) && !grid
									.isWalkable(x - 1, y))) {
						return node;
					}
				}
			}
		}

		// Recursive horizontal/vertical search
		if (dx != 0 && dy != 0) {
			if (grid.isWalkable(x + dx, y))
				return jump(grid.getCell(x + dx, y), node, grid, start, end);
			if (grid.isWalkable(x, y + dy))
				return jump(grid.getCell(x, y + dy), node, grid, start, end);
		}

		// Attemp to keep going on a straight line
		if (grid.isWalkable(x + dx, y + dy)) {
			T nextJump = jump(grid.getCell(x + dx, y + dy), node, grid, start,
					end);
			if (nextJump != null)
				return nextJump;
		}

		// if cant keep going on a straight line, try a 90 degrees turn
		if (!allowDiagonal) {
			if (dx == 0
					&& (grid.isWalkable(x, y + 1) || grid.isWalkable(x, y - 1)))
				return node;

			// diagonals are forbidden
			if (dy == 0
					&& (grid.isWalkable(x + 1, y) || grid.isWalkable(x - 1, y)))
				return node;
		}

		// couldnt find a jump point
		return null;
	}

	int clamp(int min, int max, int val) {
		if (val < min)
			return min;
		if (val > max)
			return max;
		return val;
	}

	boolean allowedDiagonalMovement(T node, int dx, int dy, NavigationGridGraph<T> grid) {
		if (options.allowDiagonal) {
			if (!options.dontCrossCorners)
				return true;

			if (dx != 0 && dy != 0)
				return true;

			if (dx == 0) {
				return (grid.isWalkable(node.getX() + 1, node.getY()) || grid.isWalkable(
						node.getX() - 1, node.getY()))
						&& grid.isWalkable(node.getX(), node.getY() + dy);
			}

			return (grid.isWalkable(node.getX(), node.getY() + 1) || grid.isWalkable(
					node.getX(), node.getY() - 1))
					&& grid.isWalkable(node.getX() + dx, node.getY());
		}
		return false;
	}

}
