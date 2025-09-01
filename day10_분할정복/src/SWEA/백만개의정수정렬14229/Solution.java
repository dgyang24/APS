package SWEA.백만개의정수정렬14229;

import java.util.Scanner;

public class Solution {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int[] arr = new int[1000001];
		for(int i = 0; i < arr.length; i++)
			arr[i] = sc.nextInt();
		quickSort(0, arr.length, arr);
		System.out.println(arr[500000]);
	}

	
	static void quickSort(int start, int end, int[] arr) {
		if(start < end) {
			int pivot = partition(start,end, arr);
			quickSort(start,pivot-1, arr);
			quickSort(pivot+1, end, arr);
		}
	}
	static int partition(int start, int end, int[] arr) {
		int pivot = arr[start]; 
		int L = start + 1;
		int R = end;
		while(L <= R) {
			while(L <= R && arr[L] <= pivot)
				L++;
			while(arr[R] > pivot)
				R--;
			if(L < R) {
				int tmp = arr[L];
				arr[L] = arr[R];
				arr[R] = tmp;
			}
		}
		int tmp = arr[start];
		arr[start] = arr[R];
		arr[R] = tmp;
		return R;
	}
}
