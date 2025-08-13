package 과목평가.T02_알고리즘_기본;
import java.util.Scanner;

public class Algo1_서울_13_양동근 {

	public static void main(String[] args) {
		// 스캐너
		Scanner sc = new Scanner(System.in);
		//테스트 케이스 수
		int T = sc.nextInt();
		//문자 담을 스트링
		String input = sc.next();  
		//결과값담을 스트링 빌더
		StringBuilder sb = new StringBuilder();
		//각 문자 개수
		int count = 0;
		//테스트케이스만큼 반복
		for(int tc = 1; tc <= T; tc++) {
			//문자열을 순회하면서 길이 -1까지
			for(int i = 0; i < input.length()-1; i++) {
				//문자열을 다 소문자로 바꿔버림
				char tmp =  input.charAt(i);
				char tmp2 = input.charAt(i+1);
				//다음값과 같으면 +1
				if(tmp == tmp2) {
					count++;
				}else {
					//다음값과 다르면 sb에 문자와 count입력 후  count초기화
					sb.append(tmp).append(count);
					count = 0;
				}
			}
			//#T ab 형식으로 출력
			System.out.println("#"+tc+ " " + sb);
			sb = new StringBuilder();
		}
		char a = 'a';
		char A = 'A';
		
		
		

	}

}
