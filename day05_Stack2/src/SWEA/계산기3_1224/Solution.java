package SWEA.계산기3_1224;

import java.util.Scanner;
import java.util.Stack;

class Solution {
	public static void main(String args[]) throws Exception {
		Scanner sc = new Scanner(System.in);

		for (int test_case = 1; test_case <= 10; test_case++) {
			// str 길이
			int T = sc.nextInt();
			// 연산식 str
			String str = sc.next();
			// 후위 표기식 계산용 stack, 결과 값 sb
			Stack<Character> stack = new Stack<>();
			StringBuilder sb = new StringBuilder();
			int sum = 0;

			// 후인 표기식 변환 시작~!
			for (int i = 0; i < T; i++) {
				char tmp = str.charAt(i);
				// 1. tmp가 숫자이면 sb에다 집어넣기~!
				if (tmp >= '0' && tmp <= '9')
					sb.append(tmp);

				// 2. '('이면 push
				else if (tmp == '(')
					stack.push(tmp);
				// 3. ')'이면 peek가 '('이 아닐동안 다 pop해서 sb에 add
				else if (tmp == ')') {
					while (stack.peek() != '(')
						sb.append(stack.pop());
					// '('빼야지
					stack.pop();
				}
				// 4. 이외에는 연산 비교
				else {
					// stack안에 아무것도 없으면 바로 넣어
					if (stack.isEmpty())
						stack.push(tmp);
					else {
						// 우선 순위에 따라서 tmp가 클 때는 push하고
						if (calcOp(tmp) > calcOp(stack.peek())) {
							stack.push(tmp);
						} else {
							// 나머지 경우(tmp가 우선 순위가 같거나 작을때)에는 pop -> add하고/ push
							sb.append(stack.pop());
							stack.push(tmp);
						}
					}
				}
			} // 후인 표기식 제작
				// 남은 스택에 있는거 sb에 다 털어버려잇~!
			while (!stack.isEmpty())
				sb.append(stack.pop());

			// 후위표기식 계산 시작~!
			// 연산할 스택
			Stack<Integer> result = new Stack<>();
			// sb를 순회하면서 피연산자면 스택에 넣고,
			for (int i = 0; i < sb.length(); i++) {
				int tmp = sb.charAt(i);
				if (tmp >= '0' && tmp <= '9')
					result.push(tmp-'0'); //스택쌓을때는 int
				else {
					// 연산자를 만나면 앞쪽 2개 피연산자를 이용하여 연산
					int x = result.pop();
					int y = result.pop();
					int z = 0;
					if (tmp == '*') {
						z = x * y;
						result.push(z);
					} else {
						z = x + y;
						result.push(z);
					}

				}
			}
			System.out.println("#" + test_case + " " + result.pop());
		} // 테스트 케이스 돌리기
	}

	public static int calcOp(char op) {
		if (op == '*')
			return 2;
		else if (op == '+')
			return 1;
		else
			return 0; // '('말고는 없으니까
	}
}
