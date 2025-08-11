package 자기소계;

import java.util.Scanner;

public class Algo1_서울_13_양동근 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();

        for (int tc = 1; tc <= T; tc++) {
            String input = sc.next().toUpperCase();
            StringBuilder sb = new StringBuilder();
            int count = 1;

            for (int i = 0; i < input.length() - 1; i++) {
                char tmp = input.charAt(i);
                char tmp2 = input.charAt(i + 1);

                if (tmp == tmp2) {
                    count++;
                } else {
                    sb.append(tmp).append(count);
                    count = 1;
                }
            }

            // 마지막 문자 처리
            sb.append(input.charAt(input.length() - 1)).append(count);

            System.out.println("#" + tc + " " + sb);
        }
    }
}

