import java.util.*;
import java.io.*;

/*
*
* 호석 - 홀수 좋아함
*
* 연산
*   수의 각 자리 숫자 중 홀수의 개수 종이에 적음
*       수가 한 자리 -> 더 이상 아무것도 안하고 종료
*       수가 두 자리 -> 2개로 나눠서 합을 구하여 새로운 수로 생각
*       수가 세 자리 이상 -> 임의의 위치에서 끊어서 3개의 수로 분할, 3개를 더한 값을 새로운 수로 생각
*
*
* 세개 숫자로 분리
*
* 1000000000
*
* */
public class Main {

    static int max = Integer.MIN_VALUE;
    static int min = Integer.MAX_VALUE;

    public static void DFS(int count, String s) {

        /*
         * 각 자리에 홀 수 찾음
         *   3자리 이상 -> 3개로 임의로 나눔 -> 나눈 걸 더함
         *   2자리 -> 2개로 나눔 -> 더함
         *   1자리 -> 그대로
         * */
//        int temp = 0;

        for(int i=0; i<s.length(); i++) {
            if((s.charAt(i)-'0')%2==1) {
//                temp++;
                count++;
            }
        }

        if(s.length() == 1) {
//            System.out.println(count);
            max = Math.max(max, count);
            min = Math.min(min, count);

            return;
        } else if(s.length() == 2) {
            int first = s.charAt(0) - '0';
            int second = s.charAt(1) - '0';

            DFS(count, String.valueOf(first+second));

        } else {

            for(int i=1; i<s.length()-1; i++) {
                for(int j=i+1; j<s.length(); j++) {

                    String s1 = s.substring(0, i);
                    String s2 = s.substring(i, j);
                    String s3 = s.substring(j);

                    int total = Integer.parseInt(s1) + Integer.parseInt(s2) + Integer.parseInt(s3);

                    DFS(count, String.valueOf(total));
                }
            }
        }

    }

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine();

        DFS(0, s);

        System.out.println(min + " " + max);
    }
}