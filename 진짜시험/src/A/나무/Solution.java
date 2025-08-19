package A.나무;

import java.util.Scanner;

public class Solution {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();
		for(int tc = 1; tc <= T; tc++) {
			int N = sc.nextInt();
			int[] tree = new int[N];
			int max = 0;
			for(int i = 0; i < N; i++) {
				tree[i] = sc.nextInt(); 
				if(tree[i] > max)
					max = tree[i];
			}
			
			int day = 1;
			
			boolean isSame = true;
			//첨값같을 때 확인
			for(int i = 0; i < N; i++) {
				if(tree[i] != max) {
					isSame = false;
					break;
				}
			}
			if(isSame) {
				day = 0;
			}else {
				//물주기 시작
				while(true) {
					//트리를 순회
					for(int i = 0; i < N; i++) {
						//홀수날
						if(day%2 == 1) {
							//현재트리가 +2가 최대값과 같으면 컨티뉴
							if((tree[i]+2) == max){
								continue;
							}
							if((tree[i]+1) <= max) {
								//+1이 최대값과 같거나 작으면 +1
								tree[i]+=1;
								break;
							}
						}
						
						//짝수날
						if(day%2 == 0) {
//							System.out.println("tree"+ i+"번" +tree[i]);
							//현재트리 +2가 최대값과 같거나 작으면 +2
							if((tree[i]+2) <= max) {
//								System.out.println("tree"+ i+"번" +tree[i]);
								tree[i] += 2;
//								System.out.println("후후"+"tree"+ i+"번" +tree[i]);
								break;
							}
						}
						
					}
//					System.out.println(Arrays.toString(tree));
					int min = Integer.MAX_VALUE;
					//최소값갱신
					for(int i = 0; i < N; i++) {
						if(tree[i] <= min)
							min = tree[i];
					}
					
					//만약 최소값이 최대값보다 같으면 브레잌
					if(min == max) {
						break;
					}
//					System.out.println("day: " + day);
//					System.out.println("min: " + min);
//					System.out.println("max: " + max);
					//day++
					day++;
					
					
					
				}//물주기 끝
			}

			//출력
			System.out.println("#"+tc+" "+day);
			
		}

	}

}
