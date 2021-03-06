import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.Collections;
import java.util.Stack;

public class Maze {
	int[][] maze;
	int[][] path;
	int[][] movement;
	int R, C;
	int curPosR, curPosC;
	public static final int UP = 0;
	public static final int DOWN = 3;
	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	public static final int[] dirRow = { -1, 0, 0, 1 };
	public static final int[] dirCol = { 0, -1, 1, 0 };

	private boolean isWall(int row, int col, int dir) {
		return (maze[row][col] & (1 << dir)) > 0;
	}

	private void setWall(int row, int col, int dir) {
		maze[row][col] |= (1 << dir);
		maze[row + dirRow[dir]][col + dirCol[dir]] |= (1 << (3 - dir));
	}

	private void unSetWall(int row, int col, int dir) {
		maze[row][col] &= ~(1 << dir); // ~ zneguje vsetky bity
		maze[row + dirRow[dir]][col + dirCol[dir]] &= ~(1 << (3 - dir));
	}

	public void generateMaze(int R, int C) {
		maze = new int[R][C];
		movement = new int[R][C];
		movement[0][0] = 2;
		this.R = R;
		this.C = C;
		Stack<Edge> edges = new Stack<Edge>();
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				maze[i][j] = 15;
				if (i < R - 1) {
					edges.push(new Edge(i, j, DOWN));
				}
				if (j < C - 1) {
					edges.push(new Edge(i, j, RIGHT));
				}
			}
		}
		Collections.shuffle(edges);
		UnionFind uf = new UnionFind(R * C);

		for (Edge e : edges) {
			int v1 = e.row * C + e.col;
			int v2 = (e.row + dirRow[e.dir]) * C + (e.col + dirCol[e.dir]);
			if (uf.find(v1) == uf.find(v2))
				continue;
			unSetWall(e.row, e.col, e.dir);
			uf.union(v1, v2);
		}

	}

	public void draw(Canvas c) {

		BufferStrategy bs = c.getBufferStrategy();
		if (bs == null) {
			c.createBufferStrategy(3);
			return;
		}
		Graphics2D g = (Graphics2D) bs.getDrawGraphics(); // draw here
		g.clearRect(0, 0, c.getWidth(), c.getHeight());
		int w = 30;

		if (path != null) {

			int i = R - 1, j = C - 1;
			g.setColor(Color.ORANGE);
			g.fillRect(0, 0, w, w);
			while (i != 0 || j != 0) {
				g.fillRect(j * w, i * w, w, w);
				int d = path[i][j];
				i += dirRow[d];
				j += dirCol[d];

			}
		}
		g.setStroke(new BasicStroke(3));
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, C * w, R * w);
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {

				if (isWall(i, j, LEFT)) {
					g.drawLine(j * w, i * w, j * w, (i + 1) * w);
				}

				if (isWall(i, j, UP)) {
					g.drawLine(j * w, i * w, (j + 1) * w, i * w);
				}
			}
		}

		g.setColor(Color.blue);
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				if (movement[i][j] == 1) {
					g.setColor(Color.blue);
					g.fillRect(11 + (30 * i), 11 + (30 * j), 8, 8);
				}
				if (movement[i][j] == 2) {
					g.setColor(Color.blue);
					g.fillRect(9 + (30 * i), 9 + (30 * j), 12, 12);
					g.setColor(Color.white);
					g.fillRect(11 + (30 * i), 11 + (30 * j), 8, 8);
				}
			}
		}

		// End Drawing!
		bs.show();
		g.dispose();

	}

	public void pathFinder() {
		path = new int[R][C];
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				path[i][j] = -1;
			}
		}
		pf(0, 0);
	}

	public void clearPath() {
		path = null;
	}

	private boolean pf(int x, int y) {
		if (x == R - 1 && y == C - 1) {
			return true;
		}
		for (int dir = 3; dir >= 0; dir--) {
			if (!isWall(x, y, dir) && path[x + dirRow[dir]][y + dirCol[dir]] == -1) {
				path[x + dirRow[dir]][y + dirCol[dir]] = 3 - dir;
				if (pf(x + dirRow[dir], y + dirCol[dir])) {
					return true;
				}
			}
		}
		return false;
	}

	public void goToStart() {
		movement = new int[R][C];
		movement[0][0] = 2;
		curPosR = 0;
		curPosC = 0;
	}

	public void move(int x, int y) {
		movement[curPosR][curPosC] = 1;
		curPosR = Math.min(Math.max(curPosR += x, 0), R - 1);
		curPosC = Math.min(Math.max(curPosC += y, 0), C - 1);
		movement[curPosR][curPosC] = 2;

	}

}
