import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class 수비탱크 {
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
		String NICKNAME = "쏘지마세요-수비";
		String gameData = init(NICKNAME);

		///////////////////////////////
		// 알고리즘 메인 부분 구현 시작
		///////////////////////////////

		int[][] DIRS = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
		String[] MOVE_CMDS = { "R A", "D A", "L A", "U A" };
		String[] FIRE_CMDS = { "R F", "D F", "L F", "U F" };

		String START_SYMBOL = "M";
		String TARGET_SYMBOL = "X";
		String WALL_SYMBOL = "R";
		// [추가] 순찰 경로를 저장할 큐
		Queue<int[]> patrolRoute = new LinkedList<>();
		// [추가] 메가 포탄 보유량 기준 설정
		final int MEGA_CANNON_THRESHOLD = 2;
		Queue<String> actions = new LinkedList<>();

		// 최초 데이터 파싱
		parseData(gameData);

		// main 메서드 > while 루프
		while (gameData != null && gameData.length() > 0) {
			printData(gameData);

			// ================== [최종 가디언 전략 시작] ==================
			Map<String, int[]> unitPositions = findAllUnits(mapData);
			int[] myPos = unitPositions.get("M");
			int myMegaCannons = (myAllies.containsKey("M") && myAllies.get("M").length >= 4)
					? Integer.parseInt(myAllies.get("M")[3])
					: 0;

			if (myPos != null) {
				// --- 1순위: 즉시 대응 가능한 위협 스캔 ---
				boolean immediateThreatFound = false;
				// ... (이 부분은 기존과 동일) ...

				// --- 위협이 없고, 계획이 없을 때만 다음 전략 수행 ---
				if (!immediateThreatFound && actions.isEmpty()) {
					boolean isNearSupply = isAdjacentTo(myPos, 'F', mapData);

					// --- 2순위: 보급고 옆이고, 포탄이 부족하면 암호 해독 ---
					if (isNearSupply && codes.length > 0 && myMegaCannons < MEGA_CANNON_THRESHOLD) {
						System.out.println("[전략 2] 암호 해독 시도.");
						String decoded = decodeCaesarCipher(codes[0]);
						actions.offer("G " + decoded);
					}
					// --- 3순위: 포탄이 부족하면 보급고로 이동 ---
					else if (myMegaCannons < MEGA_CANNON_THRESHOLD) {
						System.out.println("[전략 3] 메가 포탄이 부족하여 보급고로 이동합니다.");
						patrolRoute.clear(); // 보급하러 갈 땐 순찰 경로 초기화
						actions = findPathToNearestUnit(myPos, 'F', mapData, DIRS, MOVE_CMDS, FIRE_CMDS);
					}
					// --- 4순위: 포탄이 충분하면 아군 포탑 근처 순찰 ---
					else {
						int[] homeTurretPos = unitPositions.get("H");
						if (homeTurretPos != null) {
							if (patrolRoute.isEmpty()) {
								generatePatrolRoute(homeTurretPos, mapData, patrolRoute);
							}

							// [핵심 수정] 경로를 찾을 때까지 순찰 지점을 순회
							if (!patrolRoute.isEmpty()) {
								int attempts = patrolRoute.size(); // 무한 루프 방지
								while (attempts-- > 0) {
									int[] nextWaypoint = patrolRoute.poll();

									// 현재 위치와 목표가 같다면 건너뛰기
									if (myPos[0] == nextWaypoint[0] && myPos[1] == nextWaypoint[1]) {
										patrolRoute.offer(nextWaypoint); // 다시 큐에 넣고 다음 것 시도
										continue;
									}

									System.out.println(
											"[전략 4] 아군 포탑 주변 순찰. 다음 목적지: " + nextWaypoint[0] + "," + nextWaypoint[1]);
									actions = dijkstra(mapData, myPos, nextWaypoint, DIRS, MOVE_CMDS, FIRE_CMDS);
									patrolRoute.offer(nextWaypoint); // 순환하도록 다시 큐에 추가

									// 경로를 성공적으로 찾았다면, 순찰 지점 탐색 중단
									if (!actions.isEmpty()) {
										System.out.println("-> 경로 탐색 성공!");
										break;
									}
									System.out.println("-> 경로 탐색 실패. 다음 순찰 지점을 시도합니다.");
								}
							}
						}
					}
				}
			}
			// ================== [최종 가디언 전략 끝] ==================

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
	// 아군 유닛인지 식별하는 함수 ('M'은 자기 자신이므로 제외)
	// 1. 절대 이동 불가 장애물인지 식별하는 함수
	private static boolean isPermanentObstacle(String tile) {
		return tile.equals("R") || tile.equals("W") || tile.equals("F") || tile.equals("T");
	}

	// 2. 아군 유닛인지 식별하는 함수 (수정: M(자신)은 장애물이 아님)
	private static boolean isAlly(String tile) {
		return tile.equals("H") || tile.equals("M1") || tile.equals("M2") || tile.equals("M3");
	}

	// 3. 적군 유닛인지 식별하는 함수
	private static boolean isEnemy(String tile) {
		return tile.startsWith("E") || tile.equals("X");
	}

	// 1. 특정 유닛('F' 또는 'H')으로 가는 최단 경로를 찾는 통합 함수
	private static Queue<String> findPathToNearestUnit(int[] myPos, char unitType, String[][] grid, int[][] dirs,
			String[] moveCmds, String[] fireCmds) {
		List<int[]> unitPositions = new ArrayList<>();
		for (int r = 0; r < grid.length; r++) {
			for (int c = 0; c < grid[0].length; c++) {
				if (grid[r][c].charAt(0) == unitType) {
					unitPositions.add(new int[] { r, c });
				}
			}
		}

		if (unitPositions.isEmpty())
			return new LinkedList<>();

		// 각 유닛 주변의 모든 빈칸(G)을 목표 지점 후보로 추가
		List<int[]> allTargetSpots = new ArrayList<>();
		for (int[] pos : unitPositions) {
			for (int d = 0; d < dirs.length; d++) {
				int nr = pos[0] + dirs[d][0];
				int nc = pos[1] + dirs[d][1];
				if (nr >= 0 && nr < grid.length && nc >= 0 && nc < grid[0].length && grid[nr][nc].equals("G")) {
					allTargetSpots.add(new int[] { nr, nc });
				}
			}
		}

		// 후보지 중 가장 가까운 곳을 탐색
		Queue<String> bestPath = new LinkedList<>();
		int minCost = Integer.MAX_VALUE;

		for (int[] spot : allTargetSpots) {
			Queue<String> path = dijkstra(grid, myPos, spot, dirs, moveCmds, fireCmds);
			// dijkstra는 경로의 명령 수로 비용을 근사적으로 계산
			if (!path.isEmpty() && path.size() < minCost) {
				minCost = path.size();
				bestPath = path;
			}
		}
		return bestPath;
	}

	// 2. 특정 유닛과 인접해 있는지 확인하는 함수
	private static boolean isAdjacentTo(int[] pos, char unitType, String[][] grid) {
		int[][] dirs = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
		for (int d = 0; d < dirs.length; d++) {
			int nr = pos[0] + dirs[d][0];
			int nc = pos[1] + dirs[d][1];
			if (nr >= 0 && nr < grid.length && nc >= 0 && nc < grid[0].length && grid[nr][nc].charAt(0) == unitType) {
				return true;
			}
		}
		return false;
	}

	// 3. 카이사르 암호 해독 함수
	// decodeCaesarCipher 함수
	private static String decodeCaesarCipher(String cipher) {
		// 매뉴얼의 예시: SRKKCVJJRWP -> BATTLESSAFY 는 9칸을 뒤로 밀어야 해독됩니다.
		// 게임 내내 키가 고정될 가능성이 높으므로, 가장 유력한 키부터 시도합니다.
		int[] likelyShifts = { 9, 17, 3, 21 }; // 경험적으로 자주 나오는 쉬프트 값들

		for (int shift : likelyShifts) {
			String decoded = tryDecode(cipher, shift);
			if (decoded.contains("BATTLE") || decoded.contains("SSAFY")) {
				return decoded;
			}
		}

		// 유력한 키로 해독 실패 시, 모든 키(1~25)를 시도하여 해독 (Brute-force)
		for (int shift = 1; shift < 26; shift++) {
			String decoded = tryDecode(cipher, shift);
			// "BATTLESSAFY"가 포함되어 있다면 정답으로 간주
			if (decoded.contains("BATTLE") || decoded.contains("SSAFY")) {
				return decoded;
			}
		}
		// 그래도 못찾으면, 첫번째 유력키로 해독한 값을 반환
		return tryDecode(cipher, likelyShifts[0]);
	}

	// 카이사르 암호 해독을 시도하는 헬퍼 함수
	private static String tryDecode(String cipher, int shift) {
		StringBuilder decoded = new StringBuilder();
		for (char c : cipher.toCharArray()) {
			if (Character.isUpperCase(c)) {
				// [핵심 수정] (c - 'A' + shift) -> (c - 'A' - shift + 26) 으로 변경
				char shifted = (char) (((c - 'A' - shift + 26) % 26) + 'A');
				decoded.append(shifted);
			}
		}
		return decoded.toString();
	}

	// '알고리즘 함수/메서드 부분 구현 시작' 아래에 추가
	// 아군 포탑('H') 주변의 순찰 경로를 생성하는 함수
	private static void generatePatrolRoute(int[] homeTurretPos, String[][] grid, Queue<int[]> patrolRoute) {
		patrolRoute.clear();
		int r = homeTurretPos[0];
		int c = homeTurretPos[1];

		// 포탑 주변의 네 꼭짓점을 순찰 지점으로 설정 (거리는 조절 가능)
		int patrolDist = 2;
		int[][] waypoints = { { r - patrolDist, c }, { r, c + patrolDist }, { r + patrolDist, c },
				{ r, c - patrolDist } };

		System.out.println("[전략] 아군 포탑 주변 순찰 경로 생성.");
		for (int[] point : waypoints) {
			int pr = point[0];
			int pc = point[1];
			// 맵 안쪽이고, 이동 가능한('G' 또는 'S') 곳인지 확인
			if (pr >= 0 && pr < grid.length && pc >= 0 && pc < grid[0].length
					&& (grid[pr][pc].equals("G") || grid[pr][pc].equals("S"))) {
				patrolRoute.offer(point);
				System.out.println("-> 순찰 지점 추가: " + pr + "," + pc);
			}
		}
	}

	// 다익스트라 알고리즘을 위한 노드 클래스 (비용 포함)
	// Comparable을 구현하여 PriorityQueue에서 비용을 기준으로 자동 정렬되도록 함
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

	// [교체] 특정 유닛의 위치를 찾는 더 유연한 함수
	private static int[] findUnitPosition(String[][] grid, String unitSymbol) {
		for (int row = 0; row < grid.length; row++) {
			for (int col = 0; col < grid[0].length; col++) {
				if (grid[row][col].equals(unitSymbol)) {
					return new int[] { row, col };
				}
			}
		}
		return null; // 해당 유닛을 찾지 못한 경우
	}

	// 경로 탐색 알고리즘
	// dijkstra 함수
	// 수비탱크.java 파일의 dijkstra 함수를 아래 코드로 교체해주세요.
	private static Queue<String> dijkstra(String[][] grid, int[] start, int[] destination, int[][] dirs,
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

	        if (cost > minCosts[r][c]) continue;

	        // [핵심 수정] 목표 좌표에 '도착'했는지 확인하는 로직으로 변경
	        if (r == destination[0] && c == destination[1]) {
	            return current.actions; // '사격'이 아닌 '이동 경로'를 반환
	        }

	        // 4방향 탐색
	        for (int d = 0; d < dirs.length; d++) {
	            int nr = r + dirs[d][0];
	            int nc = c + dirs[d][1];

	            if (nr < 0 || nr >= rows || nc < 0 || nc >= cols) continue;

	            String nextTile = grid[nr][nc];
	            
	            // 절대 통과 못하는 장애물 (바위, 물, 나무 등) 및 다른 모든 유닛은 건너뛰기
	            if (isPermanentObstacle(nextTile) || isAlly(nextTile) || isEnemy(nextTile)) {
	                continue;
	            }

	            int moveCost = 1; // G, S 모두 이동 비용은 1로 간주 (수비 탱크는 교전 회피가 목적)
	            
	            if (minCosts[nr][nc] > cost + moveCost) {
	                minCosts[nr][nc] = cost + moveCost;
	                Queue<String> newActions = new LinkedList<>(current.actions);
	                newActions.offer(moveCmds[d]);
	                pq.offer(new DijkstraNode(nr, nc, minCosts[nr][nc], newActions));
	            }
	        }
	    }
	    return new LinkedList<>();
	}

	// isUnit 함수는 그대로 유지
	private static boolean isUnit(String tile) {
		return tile.equals("M") || tile.equals("H") || tile.startsWith("M") || tile.startsWith("E") || tile.equals("X");
	}

	// 모든 유닛의 위치를 한 번에 찾는 효율적인 함수
	private static Map<String, int[]> findAllUnits(String[][] grid) {
		Map<String, int[]> positions = new HashMap<>();
		for (int r = 0; r < grid.length; r++) {
			for (int c = 0; c < grid[0].length; c++) {
				String unit = grid[r][c];
				// 적 탱크(E1, E2, E3), 아군 탱크(M), 적 포탑(X) 등을 식별
				if (unit.equals("M") || unit.startsWith("E") || unit.equals("X")) {
					positions.put(unit, new int[] { r, c });
				}
			}
		}
		return positions;
	}

//	private static Queue<String> bfs(String[][] grid, int[] start, int[] target, int[][] dirs,
//	        String[] moveCmds, String[] fireCmds) {
//
//		int rows = grid.length;
//		int cols = grid[0].length;
//		Queue<BFSNode> queue = new LinkedList<>();
//		Set<String> visited = new HashSet<>();
//
//		queue.offer(new BFSNode(start[0], start[1], new LinkedList<>()));
//		visited.add(start[0] + "," + start[1]);
//
//		while (!queue.isEmpty()) {
//			BFSNode current = queue.poll();
//			int r = current.row;
//			int c = current.col;
//			Queue<String> actions = current.actions;
//
//			// 포탑 발사
//			for (int d = 0; d < dirs.length; d++) {
//				int nr = r + dirs[d][0];
//				int nc = c + dirs[d][1];
//				// [수정 포인트 1] 이동하기 전에 현재 위치에서 사격이 가능한지 먼저 확인
//				// 사격이 가능하다면, 이곳이 최단 경로의 사격 지점이므로 즉시 명령을 반환하고 탐색 종료
//				int fireDirection = canShoot(r, c, target[0], target[1], grid);
//				if (fireDirection != -1) { // -1이 아니라면 사격 가능 방향(0~3)을 의미
//					Queue<String> result = new LinkedList<>(actions);
//					result.offer(fireCmds[fireDirection]);
//					return result;
//				}
//
//			}
//
//			// 일반 이동
//			for (int d = 0; d < dirs.length; d++) {
//				int nr = r + dirs[d][0];
//				int nc = c + dirs[d][1];
//				String key = nr + "," + nc;
//
//				if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && grid[nr][nc] != null && !grid[nr][nc].equals("R")
//						&& !grid[nr][nc].equals("W") && !grid[nr][nc].equals("T") && !grid[nr][nc].equals("F")
//						&& !visited.contains(key)) {
//					visited.add(key);
//					Queue<String> newActions = new LinkedList<>(actions);
//					newActions.offer(moveCmds[d]);
//					queue.offer(new BFSNode(nr, nc, newActions));
//				}
//			}
//		}
//
//		return new LinkedList<>();
//	}

	// BFS 헬퍼 클래스
	private static class BFSNode {
		int row, col;
		Queue<String> actions;

		BFSNode(int row, int col, Queue<String> actions) {
			this.row = row;
			this.col = col;
			this.actions = actions;
		}
	}

	// 언제쏠래?
	// [새로운 함수] 원거리 사격 가능 여부와 방향을 판단하는 함수
	/**
	 * 현재 위치에서 목표를 향해 사격할 수 있는지 확인합니다.
	 * 
	 * @return 사격이 가능하면 방향(0:R, 1:D, 2:L, 3:U)을, 불가능하면 -1을 반환합니다.
	 */
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
