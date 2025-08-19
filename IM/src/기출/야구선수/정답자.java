package 기출.야구선수;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class 정답자 {
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("./src/기출/야구선수/input.txt");
		Scanner sc = new Scanner(file);
//		Scanner sc = new Scanner(System.in);
		StringBuilder sb;
		int T = sc.nextInt();
		
		for(int tc=1; tc<=T; tc++) {
			sb = new StringBuilder();
			sb.append("#").append(tc).append(" ");
			
			int n = sc.nextInt();
			int k = sc.nextInt();
			// 범위만큼 배열을 만들어줌
			int[] arr = new int[1001];
			
			int min = Integer.MAX_VALUE;
			int max = Integer.MIN_VALUE;
			
			// 이후 반복문을 돌 때 범위를 정해주기 위해서 최대, 최소값을 찾아주고
			// 인덱스에 해당하는 값을 1증가 -> 카운트배열처럼
			for(int i=0; i<n; i++) {
				int num = sc.nextInt();
				
				if(num < min) {
					min = num;
				}
				if(num > max) {
					max = num;
				}
				arr[num]++;
			}
			
			
			int sumMax = 0;
			// 숫자가 있는 곳부터 마지막 숫자가 있는 곳까지
			for(int a=min; a<=max; a++) {
				int sum = 0;
				//차이가 k를 초과할 수 없기 때문에 시작 인덱스에서 k번째 인덱스까지만 더해줌
				for(int i=0; i<=k; i++) {
					// 인덱스 초과하면 안 더해
					if(a+i <= 1000) {
						sum += arr[a+i];
					}
				}
				// 지금 합이 현재 최대값보다 크니?
				if(sum > sumMax) {
					sumMax = sum;
				}
			}
			// 결과 붙여서 출력
			sb.append(sumMax);
			System.out.println(sb);
			
		}
	}
}
