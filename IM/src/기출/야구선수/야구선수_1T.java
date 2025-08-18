 package 기출.야구선수;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class 야구선수_1T {

	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("./src/기출/야구선수/input.txt");
		Scanner sc = new Scanner(file);
//		Scanner sc = new Scanner(System.in);
		//tc개수 T
		int T = sc.nextInt();
		for(int tc = 1; tc <= T; tc++) {
			//선수 전체 명수 N
			int  N = sc.nextInt();
			//실력차 K
			int K = sc.nextInt();
//			System.out.println("K: " + K);
			//선수를 담을 배열 생성
			int[] player = new int[N];
			for(int i = 0; i < N; i++) {
				player[i] = sc.nextInt();
			}
			//배열 정렬 오름차순으로
			Arrays.sort(player);
			//선수 실력중 범위를 지정하면서 비교 시작
			int left = 0;
			int right = 0;
			int teamSize=0;
			//right가 N미만일동안 탐색
			while(right < N) {
				int diff = player[right]-player[left];
				//player[right] - player[left]의 차가 K이하면 right++
				if(diff <= K) {
					//teamsize 갱신
					teamSize = Math.max(teamSize, right-left+1);
					right++;
				}
				//반대의 경우는 팀이 성립할 수 없으므로 
				else {
					//left++
					left++;
				}
			}
			//출력
			System.out.println("#"+tc+" "+teamSize);
			
			
		}//테스트 케이스 실행
		
		
	}
}
