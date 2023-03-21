package brute_force;

import java.util.*;
import java.io.*;

/*
*
* 길이가 N인 수식 ( 1~ 19 )
* 수식 - 0~9의 정수와 연산자(+,-,x)로 이루어짐
* 연산자 우선순위 모두 동일 -> 수식 계산시 왼쪽부터 순서대로
*
* 괄호 안에 식은 무조건 먼저 계산
* But 괄호 안에는 연산자 하나만 들어 있음
* 중첩된 괄호 사용 x
*
* 수식이 주어졌을 때, 괄호를 적절히 추가해 만들 수 있는 식의 결과의 '최댓값'을 구하는 프로그램 작성
* 괄호개수 제한 x
*
* 풀이
*
* 길이 19 -> 수식 최대 9개
*
* 완전탐색 2^9 -> 최대 512번
* 괄호 o or 괄호 x
* */
public class Baekjoon_16637 {

    static int max = Integer.MIN_VALUE;
    static int size;
    static int[] numbers;
    static char[] signs;

    public static int calc(int a, int b, char sign) {

        int result = 0;
        switch(sign) {
            case '+':
                result = a+b;
                break;
            case '-':
                result = a-b;
                break;
            case '*':
                result = a*b;
                break;
        }

        return result;
    }

    public static void DFS(int basic, int cnt1, int cnt2) {

        if(cnt2 >= size/2) {

            max = Math.max(basic, max);
            return;

        }

        // 그대로 가기
        DFS(calc(basic, numbers[cnt1], signs[cnt2]), cnt1+1, cnt2+1);

        // 미리 가기
        if(cnt2 < size/2-1) {
            int temp = calc(numbers[cnt1], numbers[cnt1 + 1], signs[cnt2 + 1]);
            DFS(calc(basic, temp, signs[cnt2]), cnt1 + 2, cnt2 + 2);
        }

    }

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        size = Integer.parseInt(br.readLine());
        String s = br.readLine();
        numbers = new int[size/2+1];
        signs = new char[size/2];

        for(int i=0; i<size; i++) {
            if(i%2==0)
                numbers[i/2] = s.charAt(i)-'0';
            else
                signs[i/2] = s.charAt(i);
        }

        DFS(numbers[0], 1, 0);
        System.out.println(max);
    }
}
