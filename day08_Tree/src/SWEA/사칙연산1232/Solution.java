package SWEA.사칙연산1232;

import java.util.Scanner;

public class Solution {
	static Scanner sc = new Scanner(System.in);
	//정점 개수
	static int N;
	//크기가 [N+1][3]인 배열 생성
	static int[][] tree = new int[N+1][4];
	static double sum;
	static String[] arr = new String[N+1];
	public static void main(String[] args) {
		for(int tc = 1; tc <= 10; tc++) {
			int N = sc.nextInt();
			//합 초기화
			sum = 0;
			//정점 개수 만큼 반복
			for(int i = 0; i < N; i++) {
				//정점 위치 받고
				int idx = sc.nextInt();
				//다음 글자가 숫자면 위치에 값 할당
				String s = sc.next();
				if(Character.isDigit(s.charAt(0))) {
					tree[idx][0] = Integer.parseInt(s);
				}
				//연산자면 연산자 할당하고 자식들받아서 할당
				else {
					arr[idx] = s;
					int lc = sc.nextInt();
					int rc = sc.nextInt();
					tree[idx][1] = lc;
					tree[idx][2] = rc;
					//부모 할당
					tree[lc][3] = idx;
					tree[rc][3] = idx;
				}
				
			}
			inOrder(1);
			//출력
			System.out.println("#"+tc+" "+sum);
		}
	}
	//중위순회 하면서 계산
	public static void inOrder(int v) {
		if(v == 0 || arr[v] == null) return;
		inOrder(tree[v][1]);
		if(arr[v].equals("+") ) {
			sum = (double) tree[v][1]+tree[v][2];
		}else if(arr[v].equals("*") ) {
			sum = (double) tree[v][1]*tree[v][2];
		}else if(arr[v].equals("-") ) {
			sum = (double) tree[v][1]-tree[v][2];
		}else {
			sum = (double) tree[v][1]/tree[v][2];
		}
		inOrder(tree[v][2]);
	}
}
