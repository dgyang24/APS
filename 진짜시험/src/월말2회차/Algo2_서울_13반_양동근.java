package 월말2회차;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Algo2_서울_13반_양동근 {
	// 숫자 개수 보관할 hashmap생성
	static Map<Integer, Integer> map;
	//세트 개수
	static int cnt;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		// 테스트케이스 개수 T
		int T = sc.nextInt();
		// 테스트케이스 실행
		for (int tc = 1; tc <= T; tc++) {
			// map초기화
			map = new HashMap<>();
			// 각 숫자별 개수 초기화
			for (int i = 1; i <= 9; i++) {
				map.put(i, 0);
			}
			// 입력값을 바탕으로 5개 숫자 개수 세기
			for (int i = 0; i < 5; i++) {
				int n = sc.nextInt();
				map.put(n, map.get(n) + 1);
			}
			// 조건만족하는 숫자 담을 sb 
			StringBuilder sb = new StringBuilder();
			// 숫자 1~9까지 하나씩 넣어가면서 2세트가 충족하는지 순회
			for(int i = 1; i <= 9; i++) {
				
				isStraight(i, map);
				if(cnt == 2) {
					sb.append(i + " ");
				}
				isTriple(i, map);
				if(cnt == 2) {
					sb.append(i + " ");
				}
				
			}
			if(sb.isEmpty()) {
				sb.append("0");
			}
			//출력
			System.out.println("#"+tc + " " + sb);
		} // tc 실행

	}

	// straight인가요
	static public void isStraight(int n, Map<Integer, Integer> map) {
		// 세트 개수 초기화
		cnt = 0;
		//스트레이트?
		boolean isS = true;
		// map복사
		Map<Integer, Integer> tmpM = new HashMap<>();
		// 각 숫자별 개수 초기화
		for (int i = 1; i <= 9; i++) {
			tmpM.put(i, map.get(i));
		}
		// map에 n을 넣고
		tmpM.put(n, tmpM.get(n) + 1);
		// i~7까지
		for (int i = 1; i <= 7; i++) {
			isS = true;
			// j = i -> i+2까지 1개라도 0이면 false break;
			for (int j = i; j <= i + 2; j++) {
				if (tmpM.get(j) == 0) {
					isS = false;
					break;
				}
			}
			if(isS) cnt++;
		}

	}

	// triple인가요
	static public void isTriple(int n, Map<Integer, Integer> map) {
		// 세트 개수 초기화
		cnt = 0;
		// map복사
		Map<Integer, Integer> tmpM = new HashMap<>();
		// 각 숫자별 개수 초기화
		for (int i = 1; i <= 9; i++) {
			tmpM.put(i, map.get(i));
		}
		// map에 n을 넣고
		tmpM.put(n, tmpM.get(n) + 1);
//		System.out.println(tmpM);
		
		//1~9까지 순회하면서 get i의 값이 3이면 true + break;
		for(int i = 1; i <= 9; i++) {
			if(tmpM.get(i) == 3) {
				cnt++;
			}
		}
	}

}
