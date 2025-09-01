package 월말2회차;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Algo1_서울_13반_양동근 {
	static Queue<Character> q;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		// testCase 개수 T만큼 반복
		int T = sc.nextInt();
		for (int tc = 1; tc <= T; tc++) {
			// 문자열 받고, Queue로 전달
			String str = sc.next();
			q = new LinkedList<>();
			for (int i = 0; i < str.length(); i++)
				q.offer(str.charAt(i));

			// 적합한 문자열 개수
			int cnt = 0;
			// 괄호 회전 시작
			// q 길이만큼 회전하면서
			for (int i = 0; i < q.size(); i++) {

				// 검사를 먼저하고 충족하면 cnt++
				if (isTrue(q))
					cnt++;
				// 그다음 앞문자를 맨뒤로 보내기
				q.offer(q.poll());
			}
			// 괄호회전 끝나고 적합한문자열 개수 출력
			System.out.println("#" + tc + " " + cnt);
		} // 테스트 케이스 반복 끝
	}

	// q가 올바른 괄호 쌍인지 판단
	public static boolean isTrue(Queue<Character> q) {
		// 괄호판단 스택 생성
		Stack<Character> stack = new Stack<>();
		Queue<Character> tmpQ = new LinkedList<>();
		// q 깊은 복사
		for (int i = 0; i < q.size(); i++) {
			tmpQ.offer(q.peek());
			// q형상보존
			q.offer(q.poll());
		}
		int size = tmpQ.size();
		// tmpQ문자를 하나씩 넣으면서,
		for (int i = 0; i < size; i++) {
			char c = tmpQ.poll();
//			System.out.println(c);
			// '('가 나오면 넣고,
			if (c == '(') {
				stack.push(c);
			} else if (c == ')') {
				// ')'가 나오면
				if (!stack.isEmpty() && stack.peek() == '(') {
					stack.pop();
				}
				else if(stack.isEmpty() || stack.peek() == ')'){
					stack.push(c);
				}

			}
		} // q 털기 끝
//		System.out.println(stack);
		// 위 과정을 거치고 스텍이 비어있으면 true 아님 false
		if (stack.isEmpty())
			return true;
		return false;
	}

}
