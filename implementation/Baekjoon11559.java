package pr_java;
import java.util.*;
import java.io.*;

/*
 * 중력 영향
 * 같은 뿌요 4개 이상 상하 좌우 -> 한꺼번에 없어짐
 * 터질 수 있는 여러그룸 -> 동시에 터짐, 한번의 연쇄 추가
 * 
 * 입력
 * 	12개의 줄 - 필드 정보
 *  각 줄 = 6개 문자
 *  
 *  종류 R, G, B, P, Y
 * 
 * 출력
 * 	몇 연쇄가 되는지 출력
 * 
 * 2중 반복
 * 	if(연결된 거 4개 이상)
 * 		칠하기
 * 	else
 * 		안칠하기
 * 
 * if(터진거 == 0개)
 * 	종료
 * else
 * 	중력 적용 -> 다시 반복문
 * 	
 * */
public class Main {

	static int[] dx = {-1,0,1,0};
	static int[] dy = {0,1,0,-1};
	
	static int rowSize = 12;
	static int colSize = 6;
	
	/*
	 * DFS로 연결되어 있는지 체크
	 * */
	
	public static void gravity(char[][] map) {
		
		for(int i=0; i<colSize; i++) {
			for(int j=rowSize-2; j >= 0; j--) {
				
				if(map[j][i] != '.') {
					// 아래에 . 아닌겨 있을 때까지 row++
					int t = j+1;
					while(t < rowSize && map[t][i] == '.') {
						t++;
					}
					
					char c = map[j][i];
					map[j][i] = '.';
					map[t-1][i] = c;
				}
			}
		}
	}
	
	/*
	 * 2. 연쇄 체크
	 * 
	 * 배열 2중 반복문
	 * 	같은 색 연결된 점 찾기
	 * 
	 * 터진거 0개 -> 0 종료
	 * 
	 * 마지막에 중력 작용
	 * */
	public static int checkPop(char[][] map) {
		
		int count = 0;
		boolean isPop = true;
		
		while(isPop) {
			
//			for(int i=0; i<rowSize; i++) {
//				System.out.println(Arrays.toString(map[i]));
//			}
//			System.out.println();
			
			isPop = false;
			
			for(int i=0; i<rowSize; i++) {
				for(int j=0; j<colSize; j++) {
					// 맵이 빈칸이 아닐 때
					if(map[i][j] != '.') {
						
						boolean[][] check = new boolean[rowSize][colSize];
						List<int[]> list = new ArrayList<>(72);
						
						list.add(new int[] {i,j});
						check[i][j] = true;
						int start = 0;
						int last = list.size();
						
						boolean b = true;
						while(true) {
							for(int k=start; k<last; k++) {
								int[] point = list.get(k);
								int x = point[0];
								int y = point[1];
								for(int l=0; l<4; l++) {
									int tx = x + dx[l];
									int ty = y + dy[l];
									
									if(tx>=0 && ty>=0 && tx<rowSize && ty<colSize) {
										// 안 들렀고 && 색깔이 같으면
										if(!check[tx][ty] && map[x][y] == map[tx][ty]) {
											check[tx][ty] = true;
											list.add(new int[] {tx,ty});
										}
									}
								}
							}
							
							if(last == list.size())
								break;
							
							start = last;
							last = list.size();
						}
						
						if(list.size() >= 4) {
							isPop = true;
							for(int k=0; k<list.size(); k++) {
								int[] temp = list.get(k);
								
								map[temp[0]][temp[1]] = '.';
							}
						}
					}
				}
			}
			
//			for(int i=0; i<rowSize; i++) {
//				System.out.println(Arrays.toString(map[i]));
//			}
//			System.out.println();
			
			// 터진거 있으면 
			if(isPop) {
				count++;
				
				// 2. 중력 적용
				gravity(map);
			}
		}
		
		return count;
	}
	
	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		char[][] map = new char[rowSize][colSize];
		
		// 1. 입력 - [12][6]
		for(int i=0; i<rowSize; i++) {
			String temp = br.readLine();
			for(int j=0; j<colSize; j++) {
				map[i][j] = temp.charAt(j);
			}
		}
		
		// 함수 호출
		System.out.println(checkPop(map));
//		System.out.println("종료");
	}
	
}
