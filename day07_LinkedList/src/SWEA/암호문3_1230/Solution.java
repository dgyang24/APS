package SWEA.암호문3_1230;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class Solution {

	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("./src/SWEA/암호문3_1230/input.txt");
		Scanner sc = new Scanner(file);
//		Scanner sc = new Scanner(System.in);
		for (int tc = 1; tc <= 10; tc++) {
			// 암호문 개수 size
			int size = sc.nextInt();
			// 원본 암호문 LinkedList 생성
			LinkedList<Integer> list = new LinkedList<>();
			for (int i = 0; i < size; i++)
				list.add(sc.nextInt());
			// 명령어 개수 N
			int N = sc.nextInt();
			// N만큼 순회하면서 각 명령어를 실행
			for (int i = 0; i < N; i++) {
				// 명령어 s
				String s = sc.next();
//				System.out.println(s);
				// 명령어 s가 I이면
				if (s.equals("I")) {
					int x = sc.nextInt();
					int y = sc.nextInt();
					// j = x, j < y / .add(j,들어갈 암호)
					for (int j = x; j < x + y; j++) {
						int pw = sc.nextInt();
						list.add(j, pw);
					}
				} else if (s.equals("D")) {
					// 명령어가 D이면
					int x = sc.nextInt();
					int y = sc.nextInt();
					// j = x, j < y / .remove(j)
					for (int j = x; j < x + y; j++) {
						list.remove(j);
					}
				} else {
					// 명령어가 A이면
					int y = sc.nextInt();
					// j = 0, j<y / .addLast(암호)
					for (int j = 0; j < y; j++) {
						int pw = sc.nextInt();
						list.addLast(pw);
					}
				}
			}

			// 결과 sb에 indexOf(i) 안에 초반 10개 삽입
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < 10; i++) {
				sb.append(list.get(i)).append(" ");
			}
			// 출력
			System.out.println("#" + tc + " " + sb);
		}

	}

}
