import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class 공격탱크_동근 {
	/////////////////////////////////
	// 메인 프로그램 통신 변수 정의
	/////////////////////////////////
	private static final String HOST = "127.0.0.1";
	private static final int PORT = 8747;
	private static String ARGS = "";
	private static Socket socket = null;

	///////////////////////////////
	// 입력 데이터 변수 정의
	///////////////////////////////
	private static String[][] mapData; // 맵 정보. 예) mapData[0][1] - [0, 1]의 지형/지물
	private static Map<String, String[]> myAllies = new HashMap<>(); // 아군 정보. 예) myAllies['M'] - 플레이어 본인의 정보
	private static Map<String, String[]> enemies = new HashMap<>(); // 적군 정보. 예) enemies['X'] - 적 포탑의 정보
	private static String[] codes; // 주어진 암호문. 예) codes[0] - 첫 번째 암호문

	public static void main(String[] args) {
		ARGS = args.length > 0 ? args[0] : "";

		///////////////////////////////
		// 닉네임 설정 및 최초 연결
		///////////////////////////////
		String NICKNAME = "쏘지마세요-공격";
		String gameData = init(NICKNAME);

		///////////////////////////////
		// 알고리즘 메인 부분 구현 시작
		///////////////////////////////

		int[][] DIRS = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
		String[] MOVE_CMDS = { "R A", "D A", "L A", "U A" };
		String[] FIRE_CMDS = { "R F", "D F", "L F", "U F" };
		Queue<String> actions = new LinkedList<>();

		// 최초 데이터 파싱
		parseData(gameData);

		while (gameData != null && gameData.length() > 0) {
		    printData(gameData);

		    // ================== [실시간 전략 판단 시작] ==================
		    // 매 턴 새로운 판단을 위해 기존 행동 계획을 초기화
		    actions.clear();

		    Map<String, int[]> unitPositions = findAllUnits(mapData);
		    int[] myPos = unitPositions.get("M");

		    if (myPos != null) {
		        // --- 1순위: 즉시 대응 가능한 위협 스캔 ---
		        boolean immediateThreatFound = false;
		        String[] allEnemies = { "E1", "E2", "E3", "X" };
		        for (String enemy : allEnemies) {
		            int[] enemyPos = unitPositions.get(enemy);
		            if (enemyPos != null) {
		                int fireDirection = canShoot(myPos[0], myPos[1], enemyPos[0], enemyPos[1], mapData);
		                if (fireDirection != -1) {
		                    System.out.println("[전략 1] 긴급 대응! 사거리 내 적 발견: " + enemy);
		                    actions.offer(FIRE_CMDS[fireDirection]);
		                    immediateThreatFound = true;
		                    break;
		                }
		            }
		        }

		        // --- 2순위: 위협이 없다면, 주 임무를 위해 '최적의 한 수'를 계산 ---
		        if (!immediateThreatFound) {
		            int[] targetPos = unitPositions.get("X");
		            if (targetPos != null) {
		                // 현재 상황에서 가장 좋은 전체 경로를 계산
		                Queue<String> bestFullPath = dijkstra(mapData, myPos, targetPos, DIRS, MOVE_CMDS, FIRE_CMDS);
		                
		                // [핵심] 계산된 경로에서 '이번 턴에 할 첫 번째 행동'만 가져옴
		                if (!bestFullPath.isEmpty()) {
		                    System.out.println("[전략 2] 주 임무 수행: 적 포탑(X)을 향해 한 칸 이동합니다.");
		                    actions.offer(bestFullPath.poll());
		                }
		            }
		        }
		    }
		    // ================== [실시간 전략 판단 끝] ==================

		    String output = actions.isEmpty() ? "S" : actions.poll();
		    gameData = submit(output);
		    if (gameData != null && gameData.length() > 0) {
		        parseData(gameData);
		    }
		}

		///////////////////////////////
		// 알고리즘 메인 부분 구현 끝
		///////////////////////////////

		// 반복문을 빠져나왔을 때 메인 프로그램과의 연결을 완전히 해제하기 위해 close() 호출
		close();
	}

	////////////////////////////////////
	// 알고리즘 함수/메서드 부분 구현 시작
	////////////////////////////////////
	
	
	// 1. 절대 이동 불가 장애물인지 식별하는 함수
	private static boolean isPermanentObstacle(String tile) {
	    return tile.equals("R") || tile.equals("W") || tile.equals("F");
	}

	// 2. 아군 유닛인지 식별하는 함수 (M(자신)은 장애물이 아님)
	private static boolean isAlly(String tile) {
	    return tile.equals("H") || tile.equals("M1") || tile.equals("M2") || tile.equals("M3");
	}
	private static class DijkstraNode implements Comparable<DijkstraNode> {
	    int row, col, cost; // cost는 해당 지점까지 도달하는데 걸린 총 턴 수
	    Queue<String> actions;

	    DijkstraNode(int row, int col, int cost, Queue<String> actions) {
	        this.row = row;
	        this.col = col;
	        this.cost = cost;
	        this.actions = actions;
	    }

	    @Override
	    public int compareTo(DijkstraNode other) {
	        return this.cost - other.cost; // 비용이 낮은 노드가 우선순위를 가짐
	    }
	}
	// 경로 탐색 알고리즘
	private static Queue<String> dijkstra(String[][] grid, int[] start, int[] target, int[][] dirs,
	                                      String[] moveCmds, String[] fireCmds) {
	    int rows = grid.length;
	    int cols = grid[0].length;
	    Queue<DijkstraNode> pq = new PriorityQueue<>();
	    int[][] minCosts = new int[rows][cols];
	    for (int i = 0; i < rows; i++) {
	        java.util.Arrays.fill(minCosts[i], Integer.MAX_VALUE);
	    }

	    pq.offer(new DijkstraNode(start[0], start[1], 0, new LinkedList<>()));
	    minCosts[start[0]][start[1]] = 0;

	    while (!pq.isEmpty()) {
	        DijkstraNode current = pq.poll();
	        int r = current.row;
	        int c = current.col;
	        int cost = current.cost;
	        Queue<String> actions = current.actions;

	        if (cost > minCosts[r][c]) continue;

	        int fireDirection = canShoot(r, c, target[0], target[1], grid);
	        if (fireDirection != -1) {
	            Queue<String> result = new LinkedList<>(actions);
	            result.offer(fireCmds[fireDirection]);
	            return result;
	        }

	        // 4방향 탐색
	        for (int d = 0; d < dirs.length; d++) {
	            int nr = r + dirs[d][0];
	            int nc = c + dirs[d][1];

	            if (nr < 0 || nr >= rows || nc < 0 || nc >= cols) continue;

	            String nextTile = grid[nr][nc];
	            
	            if (isPermanentObstacle(nextTile)) {
	                continue;
	            }

	            int moveCost;
	            Queue<String> newActions = new LinkedList<>(actions);
	            
	            // [핵심 수정] 나무(T)를 만났을 때의 처리 로직 추가
	            if (nextTile.equals("T")) {
	                moveCost = 2; // 비용 2턴 (사격 1턴 + 이동 1턴)
	                newActions.offer(fireCmds[d]); // 먼저 해당 방향으로 사격
	                newActions.offer(moveCmds[d]); // 그 다음 해당 방향으로 이동
	            } else if (isAlly(nextTile)) {
	                moveCost = 10; // 아군은 높은 비용
	                newActions.offer(moveCmds[d]);
	            } else {
	                // G, S, 적군은 모두 일반 비용
	                moveCost = 1;
	                newActions.offer(moveCmds[d]);
	            }
	            
	            if (minCosts[nr][nc] > cost + moveCost) {
	                minCosts[nr][nc] = cost + moveCost;
	                pq.offer(new DijkstraNode(nr, nc, minCosts[nr][nc], newActions));
	            }
	        }
	    }
	    return new LinkedList<>();
	}
	// 모든 유닛의 위치를 한 번에 찾는 효율적인 함수
	private static Map<String, int[]> findAllUnits(String[][] grid) {
	    Map<String, int[]> positions = new HashMap<>();
	    for (int r = 0; r < grid.length; r++) {
	        for (int c = 0; c < grid[0].length; c++) {
	            String unit = grid[r][c];
	            // 적 탱크(E1, E2, E3), 아군 탱크(M), 적 포탑(X) 등을 식별
	            if (unit.equals("M") || unit.startsWith("E") || unit.equals("X")) {
	                positions.put(unit, new int[]{r, c});
	            }
	        }
	    }
	    return positions;
	}
	



	// 언제쏠래?
	// [새로운 함수] 원거리 사격 가능 여부와 방향을 판단하는 함수
	//현재 위치에서 목표를 향해 사격할 수 있는지 확인합니다.
	//사격이 가능하면 방향(0:R, 1:D, 2:L, 3:U)을, 불가능하면 -1을 반환합니다.
	 	private static int canShoot(int r, int c, int targetR, int targetC, String[][] grid) {
		// 1. 같은 행에 있는 경우 (좌/우 사격)
		if (r == targetR) {
			int dist = Math.abs(c - targetC);
			if (dist > 0 && dist <= 3) {
				// 오른쪽으로 사격
				if (c < targetC) {
					for (int i = 1; i < dist; i++) {
						String tile = grid[r][c + i];
						if (tile.equals("R") || tile.equals("T"))
							return -1; // 중간에 장애물
					}
					return 0; // 0: Right
				}
				// 왼쪽으로 사격
				else {
					for (int i = 1; i < dist; i++) {
						String tile = grid[r][c - i];
						if (tile.equals("R") || tile.equals("T"))
							return -1; // 중간에 장애물
					}
					return 2; // 2: Left
				}
			}
		}

		// 2. 같은 열에 있는 경우 (상/하 사격)
		if (c == targetC) {
			int dist = Math.abs(r - targetR);
			if (dist > 0 && dist <= 3) {
				// 아래로 사격
				if (r < targetR) {
					for (int i = 1; i < dist; i++) {
						String tile = grid[r + i][c];
						if (tile.equals("R") || tile.equals("T"))
							return -1; // 중간에 장애물
					}
					return 1; // 1: Down
				}
				// 위로 사격
				else {
					for (int i = 1; i < dist; i++) {
						String tile = grid[r - i][c];
						if (tile.equals("R") || tile.equals("T"))
							return -1; // 중간에 장애물
					}
					return 3; // 3: Up
				}
			}
		}

		// 사격 불가
		return -1;
	}

	////////////////////////////////////
	// 알고리즘 함수/메서드 부분 구현 끝
	////////////////////////////////////

	///////////////////////////////
	// 메인 프로그램 통신 메서드 정의
	///////////////////////////////

	// 메인 프로그램 연결 및 초기화
	private static String init(String nickname) {
		try {
			System.out.println("[STATUS] Trying to connect to " + HOST + ":" + PORT + "...");
			socket = new Socket();
			socket.connect(new InetSocketAddress(HOST, PORT));
			System.out.println("[STATUS] Connected");
			String initCommand = "INIT " + nickname;

			return submit(initCommand);
		} catch (Exception e) {
			System.out
					.println("[ERROR] Failed to connect. Please check if the main program is waiting for connection.");
			e.printStackTrace();
			return null;
		}
	}

	// 메인 프로그램으로 데이터(명령어) 전송
	private static String submit(String stringToSend) {
		try {
			OutputStream os = socket.getOutputStream();
			String sendData = ARGS + stringToSend + " ";
			os.write(sendData.getBytes("UTF-8"));
			os.flush();

			return receive();
		} catch (Exception e) {
			System.out.println("[ERROR] Failed to send data. Please check if connection to the main program is valid.");
			e.printStackTrace();
		}
		return null;
	}

	// 메인 프로그램으로부터 데이터 수신
	private static String receive() {
		try {
			InputStream is = socket.getInputStream();
			byte[] bytes = new byte[1024];
			int length = is.read(bytes);
			if (length <= 0) {
				System.out.println("[STATUS] No receive data from the main program.");
				close();
				return null;
			}

			String gameData = new String(bytes, 0, length, "UTF-8");
			if (gameData.length() > 0 && gameData.charAt(0) >= '1' && gameData.charAt(0) <= '9') {
				return gameData;
			}

			System.out.println("[STATUS] No receive data from the main program.");
			close();
			return null;
		} catch (Exception e) {
			System.out.println(
					"[ERROR] Failed to receive data. Please check if connection to the main program is valid.");
			e.printStackTrace();
		}
		return null;
	}

	// 연결 해제
	private static void close() {
		try {
			if (socket != null && !socket.isClosed()) {
				socket.close();
				System.out.println("[STATUS] Connection closed");
			}
		} catch (Exception e) {
			System.out.println("[ERROR] Network connection has been corrupted.");
			e.printStackTrace();
		}
	}

	///////////////////////////////
	// 입력 데이터 파싱
	///////////////////////////////

	// 입력 데이터를 파싱하여 각각의 배열/맵에 저장
	private static void parseData(String gameData) {
		// 입력 데이터를 행으로 나누기
		String[] gameDataRows = gameData.split("\n");
		int rowIndex = 0;

		// 첫 번째 행 데이터 읽기
		String[] header = gameDataRows[rowIndex].split(" ");
		int mapHeight = header.length >= 1 ? Integer.parseInt(header[0]) : 0; // 맵의 세로 크기
		int mapWidth = header.length >= 2 ? Integer.parseInt(header[1]) : 0; // 맵의 가로 크기
		int numOfAllies = header.length >= 3 ? Integer.parseInt(header[2]) : 0; // 아군의 수
		int numOfEnemies = header.length >= 4 ? Integer.parseInt(header[3]) : 0; // 적군의 수
		int numOfCodes = header.length >= 5 ? Integer.parseInt(header[4]) : 0; // 암호문의 수
		rowIndex++;

		// 기존의 맵 정보를 초기화하고 다시 읽어오기
		mapData = new String[mapHeight][mapWidth];
		for (int i = 0; i < mapHeight; i++) {
			String[] col = gameDataRows[rowIndex + i].split(" ");
			for (int j = 0; j < col.length; j++) {
				mapData[i][j] = col[j];
			}
		}
		rowIndex += mapHeight;

		// 기존의 아군 정보를 초기화하고 다시 읽어오기
		myAllies.clear();
		for (int i = rowIndex; i < rowIndex + numOfAllies; i++) {
			String[] ally = gameDataRows[i].split(" ");
			String allyName = ally.length >= 1 ? ally[0] : "-";
			String[] allyData = new String[ally.length - 1];
			System.arraycopy(ally, 1, allyData, 0, ally.length - 1);
			myAllies.put(allyName, allyData);
		}
		rowIndex += numOfAllies;

		// 기존의 적군 정보를 초기화하고 다시 읽어오기
		enemies.clear();
		for (int i = rowIndex; i < rowIndex + numOfEnemies; i++) {
			String[] enemy = gameDataRows[i].split(" ");
			String enemyName = enemy.length >= 1 ? enemy[0] : "-";
			String[] enemyData = new String[enemy.length - 1];
			System.arraycopy(enemy, 1, enemyData, 0, enemy.length - 1);
			enemies.put(enemyName, enemyData);
		}
		rowIndex += numOfEnemies;

		// 기존의 암호문 정보를 초기화하고 다시 읽어오기
		codes = new String[numOfCodes];
		for (int i = 0; i < numOfCodes; i++) {
			codes[i] = gameDataRows[rowIndex + i];
		}
	}

	// 파싱한 데이터를 화면에 출력
	private static void printData(String gameData) {
		System.out.printf("\n----------입력 데이터----------\n%s\n----------------------------\n", gameData);

		System.out.printf("\n[맵 정보] (%d x %d)\n", mapData.length, mapData[0].length);
		for (String[] row : mapData) {
			for (String cell : row) {
				System.out.printf("%s ", cell);
			}
			System.out.println();
		}

		System.out.printf("\n[아군 정보] (아군 수: %d)\n", myAllies.size());
		for (String key : myAllies.keySet()) {
			String[] value = myAllies.get(key);
			if (key.equals("M")) {
				System.out.printf("M (내 탱크) - 체력: %s, 방향: %s, 보유한 일반 포탄: %s, 보유한 메가 포탄: %s\n", value[0], value[1],
						value[2], value[3]);
			} else if (key.equals("H")) {
				System.out.printf("H (아군 포탑) - 체력: %s\n", value[0]);
			} else {
				System.out.printf("%s (아군 탱크) - 체력: %s\n", key, value[0]);
			}
		}

		System.out.printf("\n[적군 정보] (적군 수: %d)\n", enemies.size());
		for (String key : enemies.keySet()) {
			String[] value = enemies.get(key);
			if (key.equals("X")) {
				System.out.printf("X (적군 포탑) - 체력: %s\n", value[0]);
			} else {
				System.out.printf("%s (적군 탱크) - 체력: %s\n", key, value[0]);
			}
		}

		System.out.printf("\n[암호문 정보] (암호문 수: %d)\n", codes.length);
		for (String code : codes) {
			System.out.println(code);
		}
	}
}
