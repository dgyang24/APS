package SWEA.이진수표현10726;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Solution {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();
		for(int tc = 1; tc <= T; tc++) {
			int N = sc.nextInt();
			int M = sc.nextInt();
			//비트 담을 큐
			Queue<Integer> q = new LinkedList<>();
			
			//M을 이진수로 표현하기
			while(M != 0) {
				q.add(M%2);
				M /= 2;
			}
			//q사이즈가 N보다 작으면 OFF
			if(q.size() < N) {
				System.out.println("#"+tc+" "+"OFF");
			}else {
				boolean isON = true;
				//뒤에서 N번째 비트는 q의 앞 N개임 -> 하나씩 꺼내면서 0이면 off 다 1이면 on
				for(int i = 0; i < N; i++) {
					if(q.poll() == 0) {
						isON = false;
						break;
					}
				}
				if(isON) {
					System.out.println("#"+tc+" "+"ON");
				}else {
					System.out.println("#"+tc+" "+"OFF");
				}
				
			}
			
			
		}

	}

}
