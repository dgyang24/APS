import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

/*
 * 공격형 탱크 (Striker Tank)
 * 최단거리로 포탑 파괴를 최우선 목표로 삼음
 * 노랑 또는 핑크
 */
public class StrikerTank_안태리 {
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
	private static String[][] mapData; // 맵 정보
	private static Map<String, String[]> myAllies = new HashMap<>();
	private static Map<String, String[]> enemies = new HashMap<>();
	private static String[] codes; // 미사용

	///////////////////////////////
	// 상수
	///////////////////////////////
	private static final int[][] DIRS = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } }; // R,D,L,U
	private static final String[] MOVE_CMDS = { "R A", "D A", "L A", "U A" };
	private static final String[] FIRE_CMDS = { "R F", "D F", "L F", "U F" };
	private static final String[] MFIRE_CMDS = { "R F M", "D F M", "L F M", "U F M" };
	private static final String START_SYMBOL = "M";
	private static final String TARGET_SYMBOL = "X";
	private static final String WALL_SYMBOL = "R";
	private static final String WATER_SYMBOL = "W";
	private static final String TREE_SYMBOL = "T";
	private static final String SAND_SYMBOL = "S";
	private static final String SUPPLY_SYMBOL = "F";
	private static final int SHOT_RANGE = 3;

	private static final int INF = Integer.MAX_VALUE;

	public static void main(String[] args) {
		ARGS = args.length > 0 ? args[0] : "";
		String NICKNAME = "서울13_안태리";
		String gameData = init(NICKNAME);
		if (gameData == null || gameData.isEmpty())
			return;

		parseData(gameData);

		Queue<String> moveQueue = new LinkedList<>(); // 이동만 담음

		// 초기 경로 계산
		int[][] positions = findPositions(mapData, START_SYMBOL, TARGET_SYMBOL);
		int[] start = positions[0];
		int[] target = positions[1];
		if (start != null && target != null) {
			moveQueue = dijkstraMoves(mapData, start, target);
		}

		while (gameData != null && gameData.length() > 0) {
			printData(gameData);

			// 필요 시 경로 갱신
			if (moveQueue.isEmpty()) {
				positions = findPositions(mapData, START_SYMBOL, TARGET_SYMBOL);
				start = positions[0];
				target = positions[1];
				moveQueue = (start != null && target != null) ? dijkstraMoves(mapData, start, target)
						: new LinkedList<>();
			}

			// 한 턴에 하나만 전송: 샷, 이동 중에 우선순위 하나 리턴
			String output = decideOneCommand(moveQueue);

			gameData = submit(output);
			if (gameData != null && gameData.length() > 0)
				parseData(gameData);
		}
		close();
	}

	/*
	 * 매 턴 1개 명령 결정하기 (샷 우선하려고)
	 * 1) X가 직선 사거리(3) 이내면 사격(나무가 있으면 나무부터 깨기)
	 * 2) 다음 이동 칸이 나무면 먼저 그 방향으로 사격(이동은 다음 턴)
	 * 3) 아니면 이동, 없으면 대기("A")
	 */
	private static String decideOneCommand(Queue<String> moveQueue) {
		int normal = getMyInt(2);
		int mega = getMyInt(3);
		boolean hasAmmo = (normal + mega) > 0;
		int myHp = getMyInt(0);

		// 주변에 전차가 있는가?
		for (int i = 1; i <= 3; i++) {
			if (myHp <= 30)
				break; // 체력 낮으면 안쏨

			// E1, E2, E3 중 하나라도 있으면 사격
			String mark = "E" + i;
			Integer dirToMark = findShotDirToMark(mark);

			if (dirToMark != null) {
				return FIRE_CMDS[dirToMark];
			}

		}

		// X를 쏠수있는 곳인가?
		if (hasAmmo) {
			Integer dirToX = findShotDirToX(); // 나무가 끼어도 해당 방향 반환(나무부터 제거)
			if (dirToX != null) {
				return normal > 0 ? FIRE_CMDS[dirToX] : MFIRE_CMDS[dirToX];
			}
		}

		// 다음 이동 칸이 나무면 사격 하기
		if (!moveQueue.isEmpty() && hasAmmo) {
			String next = moveQueue.peek();
			int d = cmdToDir(next);
			int[] me = findOne(mapData, START_SYMBOL);
			if (me != null) {
				int nr = me[0] + DIRS[d][0];
				int nc = me[1] + DIRS[d][1];
				
				if (in(nr, nc, mapData.length, mapData[0].length) && TREE_SYMBOL.equals(mapData[nr][nc])) {
					// 아군 체크
					if (mapData[nr][nc].equals("M1") || mapData[nr][nc].equals("M2"))
						return moveQueue.poll(); // 아군이면 그냥 이동
					return normal > 0 ? FIRE_CMDS[d] : MFIRE_CMDS[d];
				}
			}
		}

		// 3) 이동 실행(없으면 대기)
		return moveQueue.isEmpty() ? "A" : moveQueue.poll();
	}

	/*
	 * X(포탑) 향해 직선 사격 가능한 방향 찾기
	 * 4방향, 최대 3칸 스캔
	 * 
	 * X 발견 시 그 방향 반환
	 */
	private static Integer findShotDirToX() {
		int[] me = findOne(mapData, START_SYMBOL);
		if (me == null)
			return null;

		for (int d = 0; d < 4; d++) {
			boolean seenTree = false;
			for (int s = 1; s <= SHOT_RANGE; s++) {
				int nr = me[0] + DIRS[d][0] * s;
				int nc = me[1] + DIRS[d][1] * s;

				// 범위 체크
				if (!in(nr, nc, mapData.length, mapData[0].length))
					break;
				String cell = mapData[nr][nc];

				// 아군 체크
				if (cell.equals("M1") || cell.equals("M2"))
					break;

				// 벽 체크
				if (WALL_SYMBOL.equals(cell) || SUPPLY_SYMBOL.equals(cell))
					break;

				// 나무 있었는가?
				if (TREE_SYMBOL.equals(cell)) {
					seenTree = true;
				} // 일단 체크만 해봄... 추후에 활용가능할지?

				if (TARGET_SYMBOL.equals(cell)) {
					// X가 보이면 나무가 있든 없든 이 방향으로 사격(나무가 있으면 먼저 깨짐)
					return d;
				}
			}

		}
		return null;
	}

	/*
	 * mark 향해 직선 사격 가능한 방향 찾기
	 * 4방향, 최대 3칸 스캔
	 * 
	 * 
	 * mark 사격 가능시 그 방향 반환
	 */
	private static Integer findShotDirToMark(String mark) {
		int[] target = findOne(mapData, START_SYMBOL);
		if (target == null)
			return null;

		for (int d = 0; d < 4; d++) {
			// boolean seenTree = false;
			for (int s = 1; s <= SHOT_RANGE; s++) {
				int nr = target[0] + DIRS[d][0] * s;
				int nc = target[1] + DIRS[d][1] * s;

				// 범위 체크
				if (!in(nr, nc, mapData.length, mapData[0].length))
					break;
				String cell = mapData[nr][nc];

				// 아군 체크
				if (cell.equals("M1") || cell.equals("M2"))
					break;

				// 벽 체크
				if (WALL_SYMBOL.equals(cell) || SUPPLY_SYMBOL.equals(cell))
					break;

				// 나무 있었는가?
				if (TREE_SYMBOL.equals(cell)) {
					// seenTree = true;
					break;
				} // 나무로 막히면 굳이 안쏨

				if (mark.equals(cell)) {
					return d;
				}
			}

		}
		return null;
	}

	/*
	 * 이동 전용 다익스트라
	 * 나무(T): 비용 2 (사격+이동으로 2턴 가정)
	 * 바위/물/모래: 통과 불가
	 * 결과 큐에는 MOVE 명령만 저장
	 */
	private static Queue<String> dijkstraMoves(String[][] grid, int[] start, int[] target) {
		String[] myInfo = myAllies.get("M");

		int myHp = Integer.parseInt(myInfo[0]);

		int R = grid.length, C = grid[0].length;
		int[][] dist = new int[R][C];
		for (int[] row : dist)
			Arrays.fill(row, INF);

		PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(n -> n.turn));
		dist[start[0]][start[1]] = 0;
		pq.offer(new Node(start[0], start[1], 0, new LinkedList<>()));

		Queue<String> answer = null;
		while (!pq.isEmpty()) {
			Node cur = pq.poll();
			if (cur.turn != dist[cur.r][cur.c])
				continue;
			if (cur.r == target[0] && cur.c == target[1]) {
				answer = cur.moves;
				break;
			}

			for (int d = 0; d < 4; d++) {
				int nr = cur.r + DIRS[d][0];
				int nc = cur.c + DIRS[d][1];

				// 범위 체크
				if (!in(nr, nc, R, C))
					continue;
				String cell = grid[nr][nc];

				// 돌, 물, 모래 체크
				if (cell == null || WALL_SYMBOL.equals(cell) || WATER_SYMBOL.equals(cell) || SUPPLY_SYMBOL.equals(cell))
					continue;
				
				if (cell.equals("M1") || cell.equals("M2"))
					continue; // 아군이 있으면 못감

				if (SAND_SYMBOL.equals(cell) && myHp < 50)
					continue; // 모래는 체력 50 이상일때만

				// 여기 왔다는건 갈수있는 지형
				int w = TREE_SYMBOL.equals(cell) ? 2 : 1; // 나무는 2턴
				int nt = cur.turn + w;
				if (nt < dist[nr][nc]) {
					dist[nr][nc] = nt;
					Queue<String> nm = new LinkedList<>(cur.moves);
					nm.offer(MOVE_CMDS[d]);
					pq.offer(new Node(nr, nc, nt, nm));
				}
			}
		}
		return answer != null ? answer : new LinkedList<>();
	}

	private static class Node {
		int r, c, turn;
		Queue<String> moves;

		Node(int r, int c, int turn, Queue<String> moves) {
			this.r = r;
			this.c = c;
			this.turn = turn;
			this.moves = moves;
		}
	}

	/*
	 * 범위 체크
	 */
	private static boolean in(int r, int c, int R, int C) {
		return r >= 0 && r < R && c >= 0 && c < C;
	}

	/*
	 * 주어진 mark 찾기
	 */
	private static int[] findOne(String[][] g, String mark) {
		for (int i = 0; i < g.length; i++)
			for (int j = 0; j < g[0].length; j++)
				if (mark.equals(g[i][j]))
					return new int[] { i, j };
		return null;
	}

	/*
	 * 내위치랑 포탑 위치 찾기
	 */
	private static int[][] findPositions(String[][] grid, String startMark, String targetMark) {
		int[] start = null, target = null;
		for (int i = 0; i < grid.length; i++)
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j].equals(startMark))
					start = new int[] { i, j };
				else if (grid[i][j].equals(targetMark))
					target = new int[] { i, j };
			}
		return new int[][] { start, target };
	}

	private static int getMyInt(int idx) {
		return Integer.parseInt(myAllies.get("M")[idx]);
	}

	private static int cmdToDir(String moveCmd) {
		char c = moveCmd.charAt(0);
		if (c == 'R')
			return 0;
		if (c == 'D')
			return 1;
		if (c == 'L')
			return 2;
		return 3;
	}

	// __________________________이 아래는 건드리지말자!!!!!!!!!!!!!!
	// ____________________________________________

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
