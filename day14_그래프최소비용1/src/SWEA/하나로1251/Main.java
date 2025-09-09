package SWEA.하나로1251;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
	static class Edge implements Comparable<Edge> {
		int from;
		int to;
		long cost;

		public Edge(int from, int to, long cost) {
			this.from = from;
			this.to = to;
			this.cost = cost;
		}

		@Override
		public int compareTo(Edge o) {
			return Long.compare(this.cost, o.cost);
		}

	}

	// 대표자 배열
	static int[] p;
	// 섬좌표 배열
	static int[][] land;
	// Edge 배열
	static List<Edge> list;
	// 개수
	static int N;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();
		for (int tc = 1; tc <= T; tc++) {
			N = sc.nextInt();
			list = new ArrayList<>();
			land = new int[N][2];
			for (int i = 0; i < N; i++)
				land[i][0] = sc.nextInt();
			for (int i = 0; i < N; i++)
				land[i][1] = sc.nextInt();
			double E = sc.nextDouble();
			// 초기화

			// 각 비용을 계산해서 간선 리스트에 넣기
			for (int i = 0; i < N; i++) {
				for (int j = i + 1; j < N; j++) {
					long diffX = Math.abs(land[i][0] - land[j][0]);
					long diffY = Math.abs(land[i][1] - land[j][1]);

					long cost = diffX * diffX + diffY * diffY;

					list.add(new Edge(i, j, cost));

				}
			}
			// 리스트 크기만큼 대표자 크기 초기화
			p = new int[N + 1];
			initP();
			Collections.sort(list);
			long ans = 0;
			// 간선을 N-1까지 잇기
			for (int i = 0, pick = 0; i < list.size() && pick < N - 1; i++) {
				int px = findSet(list.get(i).from);
				int py = findSet(list.get(i).to);
				if (px != py) {
					p[py] = px;
					pick++;
					ans += list.get(i).cost;
				}
			}
			// 출력
			System.out.println("#" + tc + " " + Math.round(E * ans));
		} // tc

	}

	// 대표자 초기화
	static void initP() {
		for (int i = 0; i < N + 1; i++) {
			p[i] = i;
		}
	}

	// 대표자 찾아줘
	static int findSet(int x) {
		if (x != p[x]) {
			p[x] = findSet(p[x]);
		}

		return p[x];
	}

}
