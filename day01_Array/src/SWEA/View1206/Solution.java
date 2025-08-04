package SWEA.View1206;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Solution {

	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("./src/SWEA/View1206/sample_input.txt");
//		Scanner sc = new Scanner(System.in);
		Scanner sc = new Scanner(file);
		for(int test_case = 1; test_case <= 10; test_case++)
		{
			//입력값을 바탕으로 배열 생성
			int N = sc.nextInt();
			int sum = 0;
			int[] arr = new int[N];
			for(int i = 0; i < N; i++) {
				int num = sc.nextInt();
				arr[i] = num;
			}
			//index 2~length-3까지 돌면서 index i가 i-2, i-1, i+1, i+2 중에 가장 큰 값보다 커야됨
			for(int i = 2; i < N-2; i++) {
				int largest = 0;
				for(int j = 1; j <= 2; j++) {
					if(arr[i+j] > largest) {
						largest = arr[i+j];
					}
					if(arr[i-j] > largest) {
						largest = arr[i-j];
					}
				}
				//주변의 가장 큰값과 비교해서 크면 차를 sum에 넣기
				if(arr[i] > largest) {
					sum+= arr[i] - largest;
				}
			}
			System.out.println("#"+test_case+" "+sum);
		}

	}

}
