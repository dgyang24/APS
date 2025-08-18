package BAEK.토너먼트1057;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int lim = sc.nextInt();
		int han = sc.nextInt();
		int cnt = 0;
		//토너먼트 결승까지 진행
		while(true) {
			//홀수일경우 +1 /2 짝수면 그냥 /2
			if(lim%2 != 0)
				lim = (lim+1)/2;
			else
				lim /=2;
			if(han%2 != 0)
				han = (han+1)/2;
			else
				han /= 2;
			
			cnt++;
			
			if(han == lim) break;
		}
		System.out.println(cnt);

	}

}
