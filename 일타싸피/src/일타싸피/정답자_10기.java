package 일타싸피;

import java.net.*;
import java.io.*;

public class 정답자_10기 {

	// 닉네임을 사용자에 맞게 변경해 주세요.
	static final String NICKNAME = "10기";
	
	// 일타싸피 프로그램을 로컬에서 실행할 경우 변경하지 않습니다.
	static final String HOST = "127.0.0.1";

	// 일타싸피 프로그램과 통신할 때 사용하는 코드값으로 변경하지 않습니다.
	static final int PORT = 1447;
	static final int CODE_SEND = 9901;
	static final int CODE_REQUEST = 9902;
	static final int SIGNAL_ORDER = 9908;
	static final int SIGNAL_CLOSE = 9909;

	// 게임 환경에 대한 상수입니다.
	static final int TABLE_WIDTH = 254;
	static final int TABLE_HEIGHT = 127;
	static final int NUMBER_OF_BALLS = 6;
	static final int[][] HOLES = { { 0, 0 }, { 127, 0 }, { 254, 0 }, { 0, 127 }, { 127, 127 }, { 254, 127 } };
	
	public static void main(String[] args) {

		Socket socket = null;
		String recv_data = null;
		byte[] bytes = new byte[1024];
		float[][] balls = new float[NUMBER_OF_BALLS][2];
		int order = 0;

		try {
			socket = new Socket();
			System.out.println("Trying Connect: " + HOST + ":" + PORT);
			socket.connect(new InetSocketAddress(HOST, PORT));
			System.out.println("Connected: " + HOST + ":" + PORT);

			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();

			String send_data = CODE_SEND + "/" + NICKNAME + "/";
			bytes = send_data.getBytes("UTF-8");
			os.write(bytes);
			os.flush();
			System.out.println("Ready to play!\n--------------------");

			while (socket != null) {
				System.out.println("---------------------------------------------------------------------------");
				// Receive Data
				bytes = new byte[1024];
				int count_byte = is.read(bytes);
				recv_data = new String(bytes, 0, count_byte, "UTF-8");
				System.out.println("Data Received: " + recv_data);

				// Read Game Data
				String[] split_data = recv_data.split("/");
				int idx = 0;
				try {
					for (int i = 0; i < NUMBER_OF_BALLS; i++) {
						for (int j = 0; j < 2; j++) {
							balls[i][j] = Float.parseFloat(split_data[idx++]);
						}
					}
				} catch (Exception e) {
					bytes = (CODE_REQUEST + "/" + CODE_REQUEST).getBytes("UTF-8");
					os.write(bytes);
					os.flush();
					System.out.println("Received Data has been currupted, Resend Requested.");
					continue;
				}

				// Check Signal for Player Order or Close Connection
				if (balls[0][0] == SIGNAL_ORDER) {
					order = (int)balls[0][1];
					System.out.println("\n* You will be the " + (order == 1 ? "first" : "second") + " player. *\n");
					continue;
				} else if (balls[0][0] == SIGNAL_CLOSE) {
					break;
				}

				// Show Balls' Position
				for (int i = 0; i < NUMBER_OF_BALLS; i++) {
					System.out.println("Ball " + i + ": " + balls[i][0] + ", " + balls[i][1]);
				}

				float angle = 0.0f;
				float power = 0.0f;

				//////////////////////////////
				// 이 위는 일타싸피와 통신하여 데이터를 주고 받기 위해 작성된 부분이므로 수정하면 안됩니다.
				//
				// 모든 수신값은 변수, 배열에서 확인할 수 있습니다.
				//   - order: 1인 경우 선공, 2인 경우 후공을 의미
				//   - balls[][]: 일타싸피 정보를 수신해서 각 공의 좌표를 배열로 저장
				//     예) balls[0][0]: 흰 공의 X좌표
				//         balls[0][1]: 흰 공의 Y좌표
				//         balls[1][0]: 1번 공의 X좌표
				//         balls[4][0]: 4번 공의 X좌표
				//         balls[5][0]: 마지막 번호(8번) 공의 X좌표
				
				/*
				 * 나의 전략
				 * 
				 * 흰공이 목적구의 어디를 치고 지나가야 할지 좌표를 구해서 
				 * 흰공의 위치과 치고 지나갈 때의 위치로 각도를 구해보자
				 * 
				 */
				
				
				// whiteBall_x, whiteBall_y: 흰 공의 X, Y좌표를 나타내기 위해 사용한 변수
				float whiteBall_x = balls[0][0];
				float whiteBall_y = balls[0][1];
				
				// targetBall_x, targetBall_y: 목적구의 X, Y좌표를 나타내기 위해 사용한 변수
				float targetBall_x = -1;
				float targetBall_y = -1;
				
				// 순서에 따라 쳐야하는 목적구가 다르기 때문에 순서에 해당하는 목적구 중 남아있는 것의 위치정보로 초기화
				if(order == 1) {
					for(int i=1; i<balls.length; i+=2) {
						if(balls[i][0] != -1) {
							targetBall_x = balls[i][0];
							targetBall_y = balls[i][1];
							break;
						}
					}
				} else {
					for(int i=2; i<balls.length; i+=2) {
						if(balls[i][0] != -1) {
							targetBall_x = balls[i][0];
							targetBall_y = balls[i][1];
							break;
						}
					}
				}
				// 모두 해당되지 않는다면 마지막 목적구만 남은 상태
				if(targetBall_x == -1) {
					targetBall_x = balls[5][0];
					targetBall_y = balls[5][1];
				}
				// 어떤 구멍에 넣을지 결정
				float hole_x = 0;
				float hole_y = 0;
				
				// 1사분면
				boolean condition1 = whiteBall_x - targetBall_x <= 0 && whiteBall_y - targetBall_y <= 0;
				// 2사분면
				boolean condition2 = whiteBall_x - targetBall_x >= 0 && whiteBall_y - targetBall_y <= 0;
				// 3사분면
				boolean condition3 = whiteBall_x - targetBall_x >= 0 && whiteBall_y - targetBall_y >= 0;
				// 4사분면
				boolean condition4 = whiteBall_x - targetBall_x <= 0 && whiteBall_y - targetBall_y >= 0;
				// 흰공과 목적구의 위치에 따라 공을 넣을 구멍 설정
				if(condition1) {
					hole_x = HOLES[5][0];
					hole_y = HOLES[5][1];
				} else if(condition2) {
					hole_x = HOLES[3][0];
					hole_y = HOLES[3][1];
				} else if(condition3) {
					hole_x = HOLES[0][0];
					hole_y = HOLES[0][1];
				} else if(condition4) {
					hole_x = HOLES[2][0];
					hole_y = HOLES[2][1];
				}
				 
				float m = (targetBall_y-hole_y)/(targetBall_x- hole_x);
				float r = (float) 5.7;
				
				// 공을 넣을 구멍과 목적구의 기울기를 구함 2r^2 = x^2 + mx^2 인거라 생각했는데 r^2로 했을 때의 결과가 좋다
				float dx = (float)Math.sqrt((float) Math.pow(r, 2) / (float)(Math.pow(m, 2) + 1));
				float dy = m * dx;
				float hit_x = targetBall_x;
				float hit_y = targetBall_y;
				
				
				// 목적구가 흰 공을 중심으로 어떤 사분면에 위치하느냐에 따라 
				// 목적구를 칠 때의 흰공 위치 설정
				if(condition3) {
					hit_x -= dx;
					hit_y -= dy;
				} else if(condition2) {
					hit_x += dx;
					hit_y += dy;
				} else if(condition4) {
					hit_x -= dx;
					hit_y -= dy;
				} else if(condition1) {
					hit_x -= dx;
					hit_y -= dy;
				}
				
				// width, height: 목적구와 흰 공의 X좌표 간의 거리, Y좌표 간의 거리
				float width = Math.abs(hit_x - whiteBall_x);
				float height = Math.abs(hit_y - whiteBall_y);

				// radian: width와 height를 두 변으로 하는 직각삼각형의 각도를 구한 결과
				//   - 1radian = 180 / PI (도)
				//   - 1도 = PI / 180 (radian)
				// angle : 아크탄젠트로 얻은 각도 radian을 degree로 환산한 결과
				double radian = height > 0? Math.atan(width / height): 0;
				angle = (float) ((180.0 / Math.PI) * radian);

				// 목적구가 흰 공을 중심으로 3사분면에 위치했을 때 각도를 재계산
				if (condition3)
				{
					radian = Math.atan(width / height);
					angle = (float) (((180.0 / Math.PI) * radian) + 180);
				}

				// 목적구가 흰 공을 중심으로 4사분면에 위치했을 때 각도를 재계산
				else if (condition4)
				{
					radian = Math.atan(height / width);
					angle = (float) (((180.0 / Math.PI) * radian) + 90);
				}
				// 목적구가 흰 공을 중심으로 2사분면에 위치했을 때 각도를 재계산
				else if (condition2)
				{
					radian = Math.atan(height / width);
					angle = (float) (((180.0 / Math.PI) * radian) + 270);
				}
				// 최대한 세게 친다
				power = 100;


				// 주어진 데이터(공의 좌표)를 활용하여 두 개의 값을 최종 결정하고 나면,
				// 나머지 코드에서 일타싸피로 값을 보내 자동으로 플레이를 진행하게 합니다.
				//   - angle: 흰 공을 때려서 보낼 방향(각도)
				//   - power: 흰 공을 때릴 힘의 세기
				// 
				// 이 때 주의할 점은 power는 100을 초과할 수 없으며,
				// power = 0인 경우 힘이 제로(0)이므로 아무런 반응이 나타나지 않습니다.
				//
				// 아래는 일타싸피와 통신하는 나머지 부분이므로 수정하면 안됩니다.
				//////////////////////////////

				String merged_data = angle + "/" + power + "/";
				bytes = merged_data.getBytes("UTF-8");
				os.write(bytes);
				os.flush();
				System.out.println("Data Sent: " + merged_data);
			}

			os.close();
			is.close();
			socket.close();
			System.out.println("Connection Closed.\n--------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
