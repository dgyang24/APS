import java.util.Scanner;

public class Algo3_서울_13반_양동근 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		//tc개수
		int T = sc.nextInt();
		for(int tc = 1; tc <= T; tc++) {
			//행길이
			int N = sc.nextInt();
			//열길이
			int M = sc.nextInt();
			
			//N크기를 가진 String배열 생성
			String[] arr = new String[N];
			//열길이만큼 sb에 추가해서 배열에 넣기
			for(int i = 0; i < N; i++) {
				StringBuilder sb = new StringBuilder();
				for(int j = 0; j < M; j++) {
					sb.append(sc.next());
				}
				arr[i] = sb.toString();
			}
			//최댓값
			int max = Integer.MIN_VALUE;
			//회문검사
			//길이가 N -> 1까지 순회하면서
			for(int len = M; len >0; len--) {
				//i 0 -> N-len까지
				for(int i = 0; i <= N-len; i++) {
					boolean isPal = true;
					for(int j = i; j < len/2; j++) {
						//j i - i+len-1까지 회문검사
						if(!arr[j].equals(arr[i+len-1-j])) {
							isPal = false;
							break;
						}
					}
					//맞으면 길이 비교해서 최댓값 넣기
					if(isPal) {
						max = Math.max(max, len);
					}
				}
				
				
			}
			//출력
			System.out.println("#"+tc+" "+max);
			
		}//tc반복
		

	}

}
