import java.util.*;
import java.io.*;

/*

구멍 뚤린 이차원 게임판
각 구멍 - 핀 1개
핀 수평,수직 방향으로 인접한 핀 뛰어넘어서 그 핀의 다음 칸으로 이동하는 것만 허용
인접한 핀의 다음 칸은 비어있고, 그 인접한 핀은 제거
핀 적절히 움직여서 - 핀의 개수 최소화, 그렇게 남기기 위해 필요한 최소 이동 횟수

핀이 움직일 수 있는 방법
    1. 좌 or 우 방향 핀 + 그 방향 다음 칸 = 빈칸
    2. 위 or 아래 방향 핀 + 그 방향 다음 칸 = 빈칸

*/
public class Main {

    static char[][] map;
    static int rowSize = 5;
    static int columnSize = 9;
    static int[] dx = {-1,0,1,0};
    static int[] dy = {0,1,0,-1};

    static int minMove = Integer.MAX_VALUE;
    static int minCount = Integer.MAX_VALUE;

    static int countPin() {

        int count = 0;
        for(int i=0; i<rowSize; i++) {
            for(int j=0; j<columnSize; j++) {
                if(map[i][j] == 'o')
                    count++;
            }
        }

        return count;
    }

    static void DFS(int count) {

        for(int i=0; i<rowSize; i++) {
            for(int j=0; j<columnSize; j++) {
                // 1. 핀인지
                if(map[i][j] == 'o') {

                    // 2. 옆에 핀이 있는지
                    for(int k=0; k<4; k++) {
                        int tx = i + dx[k];
                        int ty = j + dy[k];

                        if(tx >= 0 && tx < rowSize && ty >= 0 && ty < columnSize) {
                            if(map[tx][ty] == 'o') {
                                // 3. 그 다음 칸이 빈칸인지
                                int ttx = tx + dx[k];
                                int tty = ty + dy[k];

                                if(ttx >= 0 && ttx < rowSize && tty >= 0 && tty < columnSize) {
                                    if(map[ttx][tty] == '.') {
                                        // 핀 이동 + 옆에 핀 제거
                                        // 원래 좌표 (i,j) 제거할 좌표(tx,ty), 이동할 좌표(ttx,tty)
                                        map[i][j] = '.';
                                        map[tx][ty] = '.';
                                        map[ttx][tty] = 'o';
                                        // 핀 무브+1
                                        DFS(count+1);

                                        map[i][j] = 'o';
                                        map[tx][ty] = 'o';
                                        map[ttx][tty] = '.';
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // 더 옮길 핀 없다는 의미
        // 핀 개수 비교
        int tempCount = countPin();
        // 핀 개수 더 적으면 -> 핀 개수, 핀 무브 수정
        if(tempCount < minCount) {
            minCount = tempCount;
            minMove = count;
        }
        // 핀 개수 같으면 -> 핀 무브만 수정
        else if(tempCount == minCount) {
            if(minMove > count)
                minMove = count;
        }

    }
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int testCase = Integer.parseInt(br.readLine());

        for(int test = 0; test < testCase; test++) {
            minMove = Integer.MAX_VALUE;
            minCount = Integer.MAX_VALUE;

            if(test!=0)
                br.readLine();

            map = new char[rowSize][columnSize];

            for(int i=0; i<rowSize; i++) {
                String temp = br.readLine();

                for(int j=0; j<columnSize; j++) {
                    map[i][j] = temp.charAt(j);
                }
            }

            DFS(0);
            sb.append(minCount).append(" ").append(minMove).append("\n");
        }

        System.out.println(sb.toString());
    }
}