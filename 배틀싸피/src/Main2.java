import java.net.*;
import java.io.*;
import java.util.*;

public class Main2 {
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8747;
    private static String ARGS = "";
    private static Socket socket = null;

    private static String[][] mapData;
    private static Map<String, String[]> myAllies = new HashMap<>();
    private static Map<String, String[]> enemies = new HashMap<>();
    private static String[] codes;
    private static int megaBombCount = 0;
    
    public static void main(String[] args) {
        ARGS = args.length > 0 ? args[0] : "";
        
        String NICKNAME = "기본코드";
        String gameData = init(NICKNAME);
        
        int[][] DIRS = {{0,1}, {1,0}, {0,-1}, {-1,0}};
        String[] MOVE_CMDS = {"R A", "D A", "L A", "U A"};
        String START_SYMBOL = "M";
        String WALL_SYMBOL = "R";
        String WATER_SYMBOL = "W";
        Set<String> obstacles = Set.of(WALL_SYMBOL, WATER_SYMBOL);
        
        while (gameData != null && gameData.length() > 0) {
            parseData(gameData);
            printData(gameData);
            
            String output = "A";
            String commandFromCode = null;
            
            // --- 적 탱크 근거리 공격 (최우선) ---
            int[] myPos = findPosition(mapData, START_SYMBOL);
            if (myPos != null) {
                int[] nearestEnemyPos = findNearestEnemyPos(myPos);
                if (nearestEnemyPos != null && isAdjacent(myPos, nearestEnemyPos)) {
                    String direction = getDirection(myPos, nearestEnemyPos);
                    output = direction + " A";
                    System.out.println("[전략] 근거리 적 탱크(" + mapData[nearestEnemyPos[0]][nearestEnemyPos[1]] + ") 발견! 일반 포탄을 발사합니다.");
                    gameData = submit(output);
                    continue;
                }
            }
            // ----------------------------------------------------

            if (megaBombCount < 3) {
                // 1단계: 암호 해독 및 메가 포탄 획득
                if (codes.length > 0) {
                    System.out.println("[전략] 암호문 발견! 메가 포탄 획득에 집중합니다.");
                    String encryptedCode = codes[0];
                    String decryptedCode = caesarDecrypt(encryptedCode, 17);
                    commandFromCode = "G " + decryptedCode;
                } else {
                    // 암호문이 없으면 보급소로 이동하여 대기
                    System.out.println("[전략] 암호문이 없습니다. 보급소로 이동하여 대기합니다.");
                    String TARGET_SYMBOL = "F";
                    int[] targetPos = findPosition(mapData, TARGET_SYMBOL);
                    
                    if (myPos != null && targetPos != null) {
                        output = getNextMove(mapData, myPos, targetPos, obstacles, DIRS, MOVE_CMDS);
                    } else {
                        // 보급소도 없으면 X로 이동 (최후의 수단)
                    	
                        String[] TARGET_SYMBOL_X1 = {"E1", "E2", "E3", "X"};
                        int[] targetPos_X = null;
                        for (String target : TARGET_SYMBOL_X1) {
                        	targetPos_X = findPosition(mapData, target);
                        	if (targetPos_X != null) break;
                        }
                        
//                        int[] targetPos_X = findPosition(mapData, TARGET_SYMBOL_X);
                        if (myPos != null && targetPos_X != null) {
                            output = getNextMove(mapData, myPos, targetPos_X, obstacles, DIRS, MOVE_CMDS);
                        } else {
                            System.out.println("[상태] 목표를 찾을 수 없습니다. 대기합니다.");
                        }
                    }
                }
            } else {
                // 2단계: 적 포탑 공격
                System.out.println("[전략] 메가 포탄 5개 획득! 이제 적 포탑(X)을 찾아 공격합니다.");
                String TARGET_SYMBOL = "X";
                int[] targetPos = findPosition(mapData, TARGET_SYMBOL);

                if (myPos != null && targetPos != null) {
                    if (isAdjacent(myPos, targetPos)) {
                        String direction = getDirection(myPos, targetPos);
                        output = direction + " F";
                        System.out.println("[공격 준비] 적 포탑(X)이 사정권에 들어왔습니다. 메가 포탄을 발사합니다.");
                    } else {
                        output = getNextMove(mapData, myPos, targetPos, obstacles, DIRS, MOVE_CMDS);
                        System.out.println("[이동 중] 적 포탑(X)을 향해 전진합니다.");
                    }
                } else {
                    System.out.println("[상태] 적 포탑(X)을 찾을 수 없습니다. 대기합니다.");
                }
            }
            
            if (commandFromCode != null) {
                gameData = submit(commandFromCode);
                if (gameData != null && !gameData.isEmpty()) {
                    megaBombCount++;
                    System.out.println("[메가 포탄 획득] 총 획득 개수: " + megaBombCount);
                }
            } else {
                gameData = submit(output);
            }
        }
        
        close();
    }
    
    //---------------------------------------------------------

    private static int[] findNearestEnemyPos(int[] myPos) {
        if (myPos == null) return null;
        int nearestDist = Integer.MAX_VALUE;
        int[] nearestEnemyPos = null;

        for (Map.Entry<String, String[]> entry : enemies.entrySet()) {
            if (entry.getValue().length < 3) {
                continue;
            }
            int r = Integer.parseInt(entry.getValue()[1]);
            int c = Integer.parseInt(entry.getValue()[2]);
            int dist = Math.abs(myPos[0] - r) + Math.abs(myPos[1] - c);
            
            if (dist < nearestDist) {
                nearestDist = dist;
                nearestEnemyPos = new int[]{r, c};
            }
        }
        return nearestEnemyPos;
    }
    
    // 다음 이동 커맨드 결정
    private static String getNextMove(String[][] map, int[] start, int[] target, Set<String> obstacles, int[][] dirs, String[] moveCmds) {
        if (start == null || target == null) return "A";
        
        Queue<String> shortestPath = bfs(map, start, target, obstacles, dirs, moveCmds);
        if (shortestPath.isEmpty()) {
            System.out.println("[경로] 목표(" + map[target[0]][target[1]] + ")로 가는 경로를 찾을 수 없습니다. 대기합니다.");
            return "A";
        }
        
        String firstMove = shortestPath.peek();
        boolean hasTreeOnPath = hasTreeOnPath(map, start, shortestPath);
        
        if (hasTreeOnPath) {
            return firstMove.replace("A", "F");
        } else {
            return firstMove;
        }
    }

    private static boolean isAdjacent(int[] pos1, int[] pos2) {
        return Math.abs(pos1[0] - pos2[0]) + Math.abs(pos1[1] - pos2[1]) == 1;
    }
    
    private static String getDirection(int[] from, int[] to) {
        if (from[0] < to[0]) return "D"; 
        if (from[0] > to[0]) return "U"; 
        if (from[1] < to[1]) return "R"; 
        if (from[1] > to[1]) return "L";
        return "";
    }
    
    private static boolean hasTreeOnPath(String[][] grid, int[] start, Queue<String> path) {
        int[] currentPos = start.clone();
        for (String cmd : path) {
            currentPos = getPositionAfterMove(currentPos, cmd);
            if (currentPos[0] < grid.length && currentPos[1] < grid[0].length &&
                grid[currentPos[0]][currentPos[1]].equals("T")) {
                return true;
            }
        }
        return false;
    }
    
    private static int[] findPosition(String[][] grid, String symbol) {
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                if (grid[r][c].equals(symbol)) {
                    return new int[]{r, c};
                }
            }
        }
        return null;
    }

    private static int[] getPositionAfterMove(int[] start, String moveCmd) {
        int r = start[0];
        int c = start[1];
        if (moveCmd.startsWith("R A") || moveCmd.startsWith("R F")) c++;
        else if (moveCmd.startsWith("D A") || moveCmd.startsWith("D F")) r++;
        else if (moveCmd.startsWith("L A") || moveCmd.startsWith("L F")) c--;
        else if (moveCmd.startsWith("U A") || moveCmd.startsWith("U F")) r--;
        return new int[]{r, c};
    }
    
    //---------------------------------------------------------
    
    private static String caesarDecrypt(String encryptedText, int shift) {
        StringBuilder decrypted = new StringBuilder();
        shift = shift % 26;
        for (char character : encryptedText.toCharArray()) {
            if (character >= 'A' && character <= 'Z') {
                int originalAlphabetPosition = character - 'A';
                int newAlphabetPosition = (originalAlphabetPosition - shift + 26) % 26;
                char newCharacter = (char) ('A' + newAlphabetPosition);
                decrypted.append(newCharacter);
            } else {
                decrypted.append(character);
            }
        }
        return decrypted.toString();
    }
    
    private static Queue<String> bfs(
            String[][] grid,
            int[] start,
            int[] target,
            Set<String> obstacles,
            int[][] dirs,
            String[] moveCmds) {
        
        int rows = grid.length;
        int cols = grid[0].length;
        Queue<BFSNode> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        
        queue.offer(new BFSNode(start[0], start[1], new LinkedList<>()));
        visited.add(start[0] + "," + start[1]);

        while (!queue.isEmpty()) {
            BFSNode current = queue.poll();
            int r = current.row;
            int c = current.col;
            Queue<String> actions = current.actions;

            if (r == target[0] && c == target[1]) {
                return actions;
            }

            for (int d = 0; d < dirs.length; d++) {
                int nr = r + dirs[d][0];
                int nc = c + dirs[d][1];
                String key = nr + "," + nc;

                if (nr >= 0 && nr < rows && nc >= 0 && nc < cols &&
                    !obstacles.contains(grid[nr][nc]) && !visited.contains(key)) {
                    
                    visited.add(key);
                    Queue<String> newActions = new LinkedList<>(actions);
                    newActions.offer(moveCmds[d]);
                    queue.offer(new BFSNode(nr, nc, newActions));
                }
            }
        }
        return new LinkedList<>();
    }

    private static class BFSNode {
        int row, col;
        Queue<String> actions;
        
        BFSNode(int row, int col, Queue<String> actions) {
            this.row = row;
            this.col = col;
            this.actions = actions;
        }
    }
    
    //---------------------------------------------------------
    
    private static String init(String nickname) {
        try {
            System.out.println("[STATUS] Trying to connect to " + HOST + ":" + PORT + "...");
            socket = new Socket();
            socket.connect(new InetSocketAddress(HOST, PORT));
            System.out.println("[STATUS] Connected");
            String initCommand = "INIT " + nickname;
            return submit(initCommand);
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to connect. Please check if the main program is waiting for connection.");
            e.printStackTrace();
            return null;
        }
    }

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
            System.out.println("[ERROR] Failed to receive data. Please check if connection to the main program is valid.");
            e.printStackTrace();
        }
        return null;
    }

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

    //---------------------------------------------------------

    private static void parseData(String gameData) {
        String[] gameDataRows = gameData.split("\n");
        int rowIndex = 0;
        String[] header = gameDataRows[rowIndex].split(" ");
        int mapHeight = header.length >= 1 ? Integer.parseInt(header[0]) : 0;
        int mapWidth = header.length >= 2 ? Integer.parseInt(header[1]) : 0;
        int numOfAllies = header.length >= 3 ? Integer.parseInt(header[2]) : 0;
        int numOfEnemies = header.length >= 4 ? Integer.parseInt(header[3]) : 0;
        int numOfCodes = header.length >= 5 ? Integer.parseInt(header[4]) : 0;
        rowIndex++;

        mapData = new String[mapHeight][mapWidth];
        for (int i = 0; i < mapHeight; i++) {
            String[] col = gameDataRows[rowIndex + i].split(" ");
            for (int j = 0; j < col.length; j++) {
                mapData[i][j] = col[j];
            }
        }
        rowIndex += mapHeight;

        myAllies.clear();
        for (int i = rowIndex; i < rowIndex + numOfAllies; i++) {
            String[] ally = gameDataRows[i].split(" ");
            String allyName = ally.length >= 1 ? ally[0] : "-";
            String[] allyData = new String[ally.length - 1];
            System.arraycopy(ally, 1, allyData, 0, ally.length - 1);
            myAllies.put(allyName, allyData);
        }
        rowIndex += numOfAllies;

        enemies.clear();
        for (int i = rowIndex; i < rowIndex + numOfEnemies; i++) {
            String[] enemy = gameDataRows[i].split(" ");
            String enemyName = enemy.length >= 1 ? enemy[0] : "-";
            String[] enemyData = new String[enemy.length - 1];
            System.arraycopy(enemy, 1, enemyData, 0, enemy.length - 1);
            enemies.put(enemyName, enemyData);
        }
        rowIndex += numOfEnemies;

        codes = new String[numOfCodes];
        for (int i = 0; i < numOfCodes; i++) {
            codes[i] = gameDataRows[rowIndex + i];
        }
    }

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
                System.out.printf("M (내 탱크) - 체력: %s, 방향: %s, 보유한 일반 포탄: %s, 보유한 메가 포탄: %s\n",
                        value[0], value[1], value[2], value[3]);
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