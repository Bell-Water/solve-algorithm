import java.util.*;
import java.io.*;

/*

9시 20분

* 수열 S에서 원하는 위치에 있는 수 골라 최대 K번 삭제
*
* 짝수와 다음짝수 사이에 거리 구하기
* -> 거리합으로 줄일 수 있는 최대 수 구하기?
*
* 3 0 1 2 0
*
* */
class Main {
    public static void main(String[] args) throws IOException{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int length = Integer.parseInt(st.nextToken());
        int limit = Integer.parseInt(st.nextToken());

        String[] numbers = br.readLine().split(" ");
        List<Integer> diff = new ArrayList<>();

        int num = 0;
        for(int i=0; i<length; i++) {
            int temp = Integer.parseInt(numbers[i]);
            if(temp%2==0) {
                diff.add(num);
                num = 0;
            } else {
                num++;
            }
        }

        if(diff.size() == 0) {
            System.out.println(0);
            return;
        }

        diff.remove(0);
//        diff.add(0, 0);

//        for(int i=0; i<diff.size(); i++) {
//            System.out.println(diff.get(i));
//        }

        int left = 0;
        int right = 0;

        int tempTotal = 0;

        int answer = 0;
        int tempAnswer = 1;
        while(true) {

            if(tempTotal <= limit) {
                answer = Math.max(tempAnswer, answer);
            }

            if(right == diff.size()) {
                break;
            }

            // 0 0 0
            // 왼쪽 ~ 오른쪽 까지 더한게 =< limit -> 오른쪽 한 칸 이동
            if(tempTotal <= limit) {
                // 왼쪽 ~ 오른쪽 저장
//                right++;
                tempTotal += diff.get(right++);
                tempAnswer++;
            }

            // 왼쪽 ~ 오른쪽 까지 더한게 limit -> 왼쪽 한 칸 이동
            else {
                tempTotal -= diff.get(left);
                left++;
                tempAnswer--;

                if(left > right) {
//                    right++;
                    tempTotal += diff.get(right++);
                    tempAnswer = 1;
                }
            }
        }

        System.out.println(answer);
    }
}