package 자기소계2.SWEA.보물상자비밀번호5658;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Solution {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();
		for(int tc = 1; tc <= T; tc++) {
			//글자 개수 N
			int N = sc.nextInt();
			//글자 단위 겸 암호 돌릴 횟수 N/4
			int M = N/4;
			//몇번째 글자인지 K
			int K = sc.nextInt();
			//암호문 sb
			StringBuilder sb = new StringBuilder(sc.next());
			//1차로 중복제거용 집합 hashset
			Set<Integer> set = new HashSet<>();
			//암호돌리기 시작 N/4번 N/4단위로!
			for(int a = 0; a < M; a++) {
				for(int i = 0; i < N; i+=M) {
					//임시 암호 넣을 문자열
					String tmp = "";
					//문자열 순회하면서 i ~ n/4까지하나씩 넣고
					for(int j = i; j < i+M; j++) {
						tmp += sb.charAt(j);
						
					}
					//해쉬 셋에 넣기(10진수로 바꿔서)
					set.add(Integer.parseInt(tmp, 16));
				}
				//한번 문자열 끝났으면 sb재배치 
				StringBuilder c = sb;
				sb = new StringBuilder();
				sb.append(c.charAt(N-1));
				sb.append(c.substring(0, N-1));
			}
			
			//해쉬셋 사이즈만큼 배열 생성
			Integer[] arr = set.toArray(new Integer[set.size()]);
			//오름차순정렬하고
			Arrays.sort(arr);
			//내림차순으로 배열K번째  출력
			System.out.println("#"+tc+" "+arr[arr.length-K]);
			
		}//테스트 케이스

	}

}
