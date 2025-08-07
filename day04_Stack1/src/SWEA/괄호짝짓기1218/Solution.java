package SWEA.괄호짝짓기1218;

import java.io.File;
import java.util.Scanner;
import java.util.Stack;

class Solution {
	public static void main(String args[]) throws Exception {
		File file = new File("./src/SWEA/괄호짝짓기1218/input.txt");
		Scanner sc = new Scanner(file);

		for (int test_case = 1; test_case <= 10; test_case++) { 
			boolean isTrue = true;// 이거 올바른 괄호인지
			// 여는/닫는 문자열 출력
			String openStr = "([{<";
			String closeStr = ")]}>";
			// 검사기 Stack
			Stack<String> stack = new Stack<>();
			// 비교 문장 입력받기
			int N = sc.nextInt(); // 길이
			String str = sc.next();// 검색대상
			// 여는괄호 들어오면 모두 push
			for (int i = 0; i < N; i++) {
				// 검색을 위해 char -> String 변환
				String s = Character.toString(str.charAt(i));
				if (openStr.contains(s)) {
					stack.push(s);
				} else {
					// 닫는 괄호이면 pop할건데, 그전에
					// 1.stack이 공백이면 안됨
					if (!stack.isEmpty()) {
						String c = stack.pop();
						// 2. pop이 닫는 괄호랑 다른 인덱스면 포함관계 x -> false & 바로 종료
						if (openStr.indexOf(c) != closeStr.indexOf(s)) {
							isTrue = false;
							break;
						} 
					}
				}
			}
			int result = 1;
			if (!isTrue)
				result = 0;
			System.out.println("#" + test_case + " " + result);

		}
	}
}