import java.util.Random;

public class UnionFind {
	int[] boss;
	Random r = new Random();

	public UnionFind(int n) {
		boss = new int[n];
		for (int i = 0; i < n; i++) {
			boss[i] = i;
		}
	}

	public int find(int i) {
		if (i == boss[i]) {
			return boss[i];
		} else {
			boss[i] = find(boss[i]);
			return boss[i];
		}
	}

	public void union(int i, int j) {
		i = find(i);
		j = find(j);
		if (i == j) {
			return;
		}
		if (r.nextInt(2) == 0) {
			boss[i] = j;
		} else {
			boss[j] = i;
		}

	}

}
