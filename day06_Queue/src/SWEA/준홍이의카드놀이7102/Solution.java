package SWEA.준홍이의카드놀이7102;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;


public class Solution {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();
		int max = 0;
		for(int tc = 1; tc <= T; tc++){
			int N = sc.nextInt();
			int M = sc.nextInt();
			Queue<Integer> q = new LinkedList<>();
			for(int i = 1; i <= N; i++) {
				for(int j = 1; j <= M; j++) {
					int sum = i+j;
					q.add(sum);
					if(sum > max)
						max = sum;
				}
			}
			//카운트 배열이용하여 각 숫자에 카운트
			int[] cnt = new int[max+1];
			//q 다 빼버리면서 cnt 인덱스와 맞는 숫자에 넣기
			while(!q.isEmpty()) {
				int n = q.remove();
				cnt[n]++;
			}
			
			int most = 0;
			for(int i = 0; i < cnt.length; i++) {
				if(cnt[i] > most)
					most = cnt[i];
			}
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < cnt.length; i++) {
				if(cnt[i] == most)
					sb.append(i).append(" ");
			}
			System.out.println("#" + tc + " " + sb);
			
			
		}
	}

}
