package org.xguzm.pathfinding.grid;

/**
 * Encapsulates the way to calculate a distance from one point to another.
 * 
 * @author Xavier Guzman 
 */
public enum Heuristic {
	/**
	 * @return dx + dy
	 */
	Manhattan {
		@Override
		public float calculate(int dx, int dy) {
			return dx + dy;
		}

		@Override
		public float calculate(int x1, int y1, int x2, int y2) {
			return calculate(Math.abs(x2 - x1), Math.abs(y2 - y1));
		}

	},
	
	/**
	 * @return sqrt(dx * dx + dy * dy)
	 */
	Euclidean {
		@Override
		public float calculate(int dx, int dy) {
			return (float) Math.sqrt(dx * dx + dy * dy);
		}

		@Override
		public float calculate(int x1, int y1, int x2, int y2) {
			return calculate(Math.abs(x2 - x1), Math.abs(y2 - y1));
		}

	},
	
	/**
	 * @return max(dx, dy)
	 */
	Chebyshev {		
		@Override
		public float calculate(int dx, int dy) {
			return Math.max(dx, dy);
		}

		@Override
		public float calculate(int x1, int y1, int x2, int y2) {
			return calculate(Math.abs(x2 - x1), Math.abs(y2 - y1));
		}

	};

	/**
	 * @param dx
	 *            - Difference in x.
	 * @param dy
	 *            - Difference in y.
	 * @return the distance determined by the used {@link Heuristic}
	 */
	public abstract float calculate(int dx, int dy);

	/**
	 * @return the distance determined by the used {@link Heuristic}
	 */
	public abstract float calculate(int x1, int y1, int x2, int y2);
}
