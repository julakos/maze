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
	int R, C;
	int posX = 1, posY = 1;
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

	public void move(int x, int y, Canvas c) {
		BufferStrategy bs = c.getBufferStrategy();
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		
		g.setColor(Color.blue);
		g.drawRect(11 + (30 * (posX + x)), 11 + (30 * (posY + y)), 19 + (30 * (posX + x)), 19 + (30 * (posY + y)));
		posX = posX + x;
		posY = posY + y;

		bs.show();
		g.dispose();
	}

}
