package SWEA.규영이와인영이의카드게임6808;

public class Solution {
	//규영 숫자
	static int[] arr1; 
	static int[] arr2;
	//승패 횟수
	
	//
	static int sumG;
	static int sumY;
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	static private void playGame(int idxG, int idxY, int sumCard) {
		//종료 조건
		if(idxG == 9) return;
		//승패 판별 
		if(arr1[idxG] > arr2[idxY]) {
			sumG += sumCard;
		}else if(arr1[idxG] < arr2[idxY]) {
			sumY += sumCard;
		}
		//재귀 부분
		//인영이거 다 돌았으면
		if(idxY == 9) {
			
			playGame(idxG+1, (idxY+1)%9, arr1[idxG]+arr2[idxY]);
		}else {
			//규영인 고정, 인영이 거 돌리기
			playGame(idxG, idxY+1, arr1[idxG]+arr2[idxY]);
		}
		
	}
}
