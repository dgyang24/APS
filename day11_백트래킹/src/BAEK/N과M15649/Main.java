package BAEK.N과M15649;

import java.util.Scanner;

public class Main {
	static int[] arr;
	static int[] tmp;
	static boolean[] visited;
	static int N;
	static int M;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		M = sc.nextInt();
		arr = new int[N];
		tmp = new int[M];
		visited = new boolean[N];
		for(int i = 0; i < N; i++)
			arr[i] = i+1;
		
		perm(0);

	}
	static void perm(int idx) {
		//종료조건
		if(idx == M) {
			for(int n : tmp) {
				System.out.print(n + " ");
			}
			System.out.println();
			return;
		}
		//재귀
		for(int i = 0; i < N; i++) {
			if(visited[i]) continue;
			visited[i] = true;
			tmp[idx] = arr[i];
			perm(idx+1);
			visited[i] = false;
			
		}
		
	}

}
