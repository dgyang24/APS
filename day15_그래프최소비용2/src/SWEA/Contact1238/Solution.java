package SWEA.Contact1238;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Solution {
	static class Edge {
		int from, to;

		public Edge(int from, int to) {
			this.from = from;
			this.to = to;
		}
		
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		for (int tc = 1; tc <= 10; tc++) {
			// 정점의수 (최대 인덱스기준)
			int V = 101;
			// 간선의 수
			int E = sc.nextInt();
			// 시작점
			int start = sc.nextInt();
			// 인접리스트선언
			List<Edge>[] adj = new ArrayList[V];
			for (int i = 0; i < V; i++)
				adj[i] = new ArrayList<>();
			// from, to 객체 할당 방향 유
			for (int i = 0; i < E / 2; i++) {
				int from = sc.nextInt();
				int to = sc.nextInt();

				adj[from].add(new Edge(from, to));
//				System.out.println(adj[from]);
			}
			
			// 방문 배열
			boolean[] visited = new boolean[V];

			// q가 비어있을 때까지 방향대로 ㄱㄱ
			LinkedList<Integer> q = new LinkedList<>();
			q.add(start);
			visited[start] = true;
			// 임시저장 list
			List<Integer> ans = new ArrayList<>();
			while(true) {
				ans.clear();
				int size = q.size();
//				System.out.println("q: " + q);
//				System.out.println("tmp: " + tmp);
				//2. q를 하나씩 빼고, 각 edge의 to를 q에 저장 - 1싸이클 관리
				for(int i = 0; i < size; i++) {
					int e = q.poll();
					ans.add(e);
					for(Edge next : adj[e]) {
						int nextNode = next.to;
						if(visited[nextNode]) continue;
						//방문하지 않은 경우에, 방문기록하고 to를 집어넣기
						visited[nextNode] = true;
						q.add(nextNode);
						
					}
					
				}
				
				//다음 갈 곳이 있으면 그동안 갔던 곳 방문처리
				if(!q.isEmpty()) ans.clear();
				//q가 다 빠졌으면 break;
				else if(q.isEmpty()) break;
				
				
				
			}//전화걸기
			
			Collections.sort(ans);
//			System.out.println(ans);
			System.out.println("#" + tc + " " + ans.get(ans.size() - 1));

		}

	}

}
