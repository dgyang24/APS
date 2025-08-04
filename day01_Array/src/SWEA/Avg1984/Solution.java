package SWEA.Avg1984;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Solution {

	public static void main(String[] args) throws FileNotFoundException{
		File file = new File("./src/SWEA/Avg1984/input.txt");
		Scanner sc = new Scanner(file);
//		Scanner sc = new Scanner(System.in);
		int T;
		T=sc.nextInt();

		for(int test_case = 1; test_case <= T; test_case++)
		{
			//배열 생성
			int[] arr = new int[10];
			int sum = 0; //요소들의 총합
			for(int i = 0; i < 10; i++) {
				int num = sc.nextInt();
				arr[i] = num;
				sum+= num;
			}
			int max = Integer.MIN_VALUE; //최댓값 비교대상
			int min = Integer.MAX_VALUE; //최솟값 비교대상
			int maxCnt = 0; //최댓값 개수
			int minCnt = 0; //최솟값 개수
			
			//배열을 순회하면서, 최댓값, 최솟값 도출			
			for(int i = 0; i < 10; i++) {
				if(arr[i] > max) {
					max = arr[i];
				}
				if(arr[i] < min) {
					min = arr[i];
				}
			}
			//최대,최솟값과 동일한 값이 있으면 개수 카운트
			//e.g.) [1,1,2,3,4,5,100,100] <- 이런 배열인 경우
			for(int i = 0; i<10; i++) {
				if(arr[i] == max)
					maxCnt++;
				if(arr[i]==min)
					minCnt++;
			}
			//총합-(최대,최소값의 합)/전체길이 -(최대,최소값의 개수의 합)
			//주의: 나눈 값이 실수 일 경우 소수점 첫째자리에서 반올림해야하기 때문에
			//정수/정수가 아닌 실수가 포함된 수식이어야 함,
			//나눈값(실수)을 반올림하고 정수로 형변환하여 출력
			int result = (int) Math.round(((double)(sum - (max*maxCnt + min*minCnt))/(arr.length-(maxCnt+minCnt))));

			
			
			System.out.println("#" + test_case + " " + result);
			
		}


	}

}
