package 일타싸피;
import java.util.ArrayList;
import java.util.List;
import java.net.*;
import java.io.*;

public class 정답자_13기 {

    // 닉네임을 사용자에 맞게 변경해 주세요.
    static final String NICKNAME = "13기";

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

    // Custom Constants
    static final int EIGHT_BALL = 5;
    static final double EPS = 1e-9;
    static final double BALL_RADIUS = 2.865;
    static final double BALL_SIZE = 5.73;
    static final double CONTACT_ADJ = 0.98;
    static final double DIST_COEF = 0.35;
    static final double POWER_MAX = 45.0;

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
                    order = (int) balls[0][1];
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
                // - order: 1인 경우 선공, 2인 경우 후공을 의미
                // - balls[][]: 일타싸피 정보를 수신해서 각 공의 좌표를 배열로 저장
                // 예) balls[0][0]: 흰 공의 X좌표
                // balls[0][1]: 흰 공의 Y좌표
                // balls[1][0]: 1번 공의 X좌표
                // balls[4][0]: 4번 공의 X좌표
                // balls[5][0]: 마지막 번호(8번) 공의 X좌표

                // 여기서부터 코드를 작성하세요.
                // 아래에 있는 것은 샘플로 작성된 코드이므로 자유롭게 변경할 수 있습니다.

                List<Integer> myBalls = new ArrayList<>();
                if (order == 1) {
                    if (balls[1][0] > -0.5f && balls[1][1] > -0.5f) {
                        myBalls.add(1);
                    }
                    if (balls[3][0] > -0.5f && balls[3][1] > -0.5f) {
                        myBalls.add(3);
                    }
                } else {
                    if (balls[2][0] > -0.5f && balls[2][1] > -0.5f) {
                        myBalls.add(2);
                    }
                    if (balls[4][0] > -0.5f && balls[4][1] > -0.5f) {
                        myBalls.add(4);
                    }
                }

                if (myBalls.size() == 0) {
                    myBalls.add(EIGHT_BALL);
                }

                double[] whiteBall = { balls[0][0], balls[0][1] };

                int currBall = 1;
                double[] targetHole = { HOLES[0][0], HOLES[0][1] };
                double maxScore = Double.MIN_VALUE;
                for (int ballIdx : myBalls) {
                    // 점수 가장 높은 hole을 노림
                    for (int[] hole : HOLES) {
                        double[] targetBall = { balls[ballIdx][0], balls[ballIdx][1] };

                        double dx1 = targetBall[0] - whiteBall[0];
                        double dy1 = targetBall[1] - whiteBall[1];
                        double dx2 = hole[0] - targetBall[0];
                        double dy2 = hole[1] - targetBall[1];

                        double dotProduct = dx1 * dx2 + dy1 * dy2;
                        double length = Math.sqrt((dx1 * dx1 + dy1 * dy1) * (dx2 * dx2 + dy2 * dy2)) + EPS;
                        double cos = dotProduct / length;

                        // 코사인 값이 높을수록 각도가 유사
                        double currScore = cos;

                        if (currScore > maxScore) {
                            maxScore = currScore;
                            targetHole[0] = hole[0];
                            targetHole[1] = hole[1];
                            currBall = ballIdx;
                        }
                    }
                }

                double[] targetBall = { balls[currBall][0], balls[currBall][1] };
                double[] contactPoint = findContactPoint(targetBall, targetHole);

                angle = (float) findAngle(whiteBall, contactPoint);
                power = (float) calculatePower(whiteBall, targetBall, targetHole);

                // 주어진 데이터(공의 좌표)를 활용하여 두 개의 값을 최종 결정하고 나면,
                // 나머지 코드에서 일타싸피로 값을 보내 자동으로 플레이를 진행하게 합니다.
                // - angle: 흰 공을 때려서 보낼 방향(각도)
                // - power: 흰 공을 때릴 힘의 세기
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

    static double radianToDegree(double radian) {
        return radian * 180.0 / Math.PI;
    }

    static double distance(double[] from, double[] to) {
        return Math.sqrt(Math.pow(from[1] - to[1], 2) + Math.pow(from[0] - to[0], 2));
    }

    static double[] findContactPoint(double[] targetBall, double[] targetHole) {
        double dx = targetHole[0] - targetBall[0];
        double dy = targetHole[1] - targetBall[1];

        double length = Math.sqrt(dx * dx + dy * dy) + EPS;
        double unitX = dx / length;
        double unitY = dy / length;

        double[] contactPoint = new double[2];
        // 공 크기만큼 뒤에 있는 좌표를 노림, 오차로 인해 빗겨치는 것을 방지하기 위해 계수로 보정
        contactPoint[0] = targetBall[0] - unitX * BALL_SIZE * CONTACT_ADJ;
        contactPoint[1] = targetBall[1] - unitY * BALL_SIZE * CONTACT_ADJ;

        return contactPoint;

    }

    static double findAngle(double[] source, double[] target) {
        double rad = Math.atan2(target[1] - source[1], target[0] - source[0]);
        double angle = radianToDegree(rad);
        angle += angle < 0 ? 360 : 0;

        // Starting from Y axis, Clockwise
        angle = 90 - angle;
        angle += angle < 0 ? 360 : 0;
        return angle;
    }

    static double calculatePower(double[] whiteBall, double[] targetBall, double[] targetHole) {
        double dist = distance(whiteBall, targetBall) + distance(targetBall, targetHole);
        double power = dist * DIST_COEF;

        power = power > POWER_MAX ? POWER_MAX : power;
        return power;
    }
}