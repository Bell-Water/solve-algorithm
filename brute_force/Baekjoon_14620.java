import java.util.*;
import java.io.*;

/*
 * 꽃 심으면 1년 후에 핌
 * 
 * 꽃의 싸잇이 3개 -> 3개의 꽃이 하나도 안 죽고 꽃잎이 만개하길 원함
 * (1,1) ~ (N,N)
 * 씨앗 -> 꽃 될 때
 *  - 다른 꽃잎 or 곷술과 닿으면 두 꽃 다 죽음
 *  - 화단 밖으로 꽃잎 나가면 -> 꽃 죽음
 * 
 * => 꽃을 심기 위한 최소비용
 * 
 * - 화단 한 변 길이 6~10
 * - 100
 * 세 점 찍어놓고 체크 -> 
 * 
*/
public class Main {

    static int[] dx = {-1,0,1,0};
    static int[] dy = {0,1,0,-1};
    static int[][] map;

    static boolean[][] check;
    static int min = Integer.MAX_VALUE;

    public static void DFS(int size, int num, int[][] points) {
        
        // 좌표 3개일 때
        if(num == 3) {
            // 3. 3개 심었을 때 죽는지 안죽는지 체크 + 안죽으면 땅 가격 체크
            
            // 0과 1 위치 같을 때
//            if(points[0][0] == points[1][0] && points[0][1] == points[1][1])
//                return;
//            if(points[0][0] == points[2][0] && points[0][1] == points[2][1])
//                return;
//            if(points[1][0] == points[2][0] && points[1][1] == points[2][1])
//                return;
//
//            // 0 - 1,2 비교 / 1 - 2 비교
//            for(int i=0; i<4; i++) {
//                int tx = points[0][0] + dx[i];
//                int ty = points[0][1] + dy[i];
//
//                if((tx == points[1][0] && ty == points[1][1]) || (tx == points[2][0] && ty == points[2][1]))
//                    return;
//
//                int tx2 = points[1][0] + dx[i];
//                int ty2 = points[1][1] + dy[i];
//
//                if(tx2 == points[2][0] && ty2 == points[2][1])
//                    return;
//            }
            
            int temp = map[points[0][0]][points[0][1]] + map[points[1][0]][points[1][1]] + map[points[2][0]][points[2][1]];
            
            for(int i=0; i<4; i++) {
                for(int j=0; j<3; j++) {
                    temp += map[points[j][0] + dx[i]][points[j][1] + dy[i]];
                }
            }

            min = Math.min(min, temp);
//            if(temp == min) {
//                System.out.println(temp);
//                System.out.println(points[0][0] + " : " + points[0][1]);
//                System.out.println(points[1][0] + " : " + points[1][1]);
//                System.out.println(points[2][0] + " : " + points[2][1]);
//            }
            return;
        }
        
        for(int i=1; i<size-1; i++) {
            for(int j=1; j<size-1; j++) {
                if(check[i][j])
                    continue;

                boolean isCheck = false;

                for(int k=0; k<4; k++) {
                    int tx = i + dx[k];
                    int ty = j + dy[k];

                    if(check[tx][ty] == true) {
                        isCheck = true;
                        break;
                    }
                }

                if(isCheck)
                    continue;

                points[num][0] = i;
                points[num][1] = j;
                check[i][j] = true;
                for(int k=0; k<4; k++) {
                    int tx = i + dx[k];
                    int ty = j + dy[k];
                    check[tx][ty] = true;
                }

                DFS(size, num+1, points);

                check[i][j] = false;
                for(int k=0; k<4; k++) {
                    int tx = i + dx[k];
                    int ty = j + dy[k];
                    check[tx][ty] = false;
                }
            }
        }

    }

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        
        // 1. 맵 입력 받기
        int size = Integer.parseInt(br.readLine());
        map = new int[size][size];
        check = new boolean[size][size];

        for(int i=0; i<size; i++) {
            st = new StringTokenizer(br.readLine());

            for(int j=0; j<size; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        
        int[][] save = new int[3][2];

        // 2. 삼 중 반복문으로 꽃 위치 3개 지정
        DFS(size, 0, save);

        System.out.println(min);
    
    }
}