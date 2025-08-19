package SWEA.보물상자비밀번호5658;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class Solution {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		// tc개수 T
		int T = sc.nextInt();
		// tc만큼 반복
		for (int tc = 1; tc <= T; tc++) {
			// 문자 길이 N
			int N = sc.nextInt();
			// M:끊는단위 N/4 & 암호돌리기 횟수
			int M = N / 4;
			// 몇번째로 큰 K
			int K = sc.nextInt();
			// 암호문 s
			StringBuilder s = new StringBuilder(sc.next());
			// 중복값 제거를 위한 해쉬셋
			HashSet<Integer> set = new HashSet<>();
			// M번 순회하면서 끝 문자를 앞으로 붙임
			for (int i = 0; i < M; i++) {
				// 문자열 s를 순회하면서
				for (int k = 0; k < s.length(); k += M) {
					// M길이만큼 잘라서 해쉬셋에 넣기 (정수변환해서)
					StringBuilder sb = new StringBuilder();
					for (int j = 0; j < M; j++) {
						sb.append(s.charAt(k + j));
					}
					set.add(Integer.parseInt(sb.toString(), 16));
//					System.out.println("sb: "+sb);
				}

				// 끝 문자를 맨 앞으로 + 나머지 문자 뒤로 붙이기
				char tmp = s.charAt(N - 1);
				String tmps = s.substring(0, N - 1);
//				System.out.println(tmps);
				StringBuilder tmpsb = new StringBuilder();
				tmpsb.append(tmp).append(tmps);
				s = tmpsb;

//				System.out.println("s: " + s);
			}
			List<Integer> list = new ArrayList<>(set);
			Collections.sort(list);// 오름
			// 내림으로 바꾸기
			List<Integer> result = new ArrayList<>();
			for (int i = list.size() - 1; i >= 0; i--) {
				result.add(list.get(i));
			}

//			System.out.println(set.toString());
			// 출력
			System.out.println("#" + tc + " " + result.get(K - 1));

		} // tc 반복

	}

}
