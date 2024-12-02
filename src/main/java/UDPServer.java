import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPServer {
    public static void main(String[] args) throws Exception {
        String studentId = ""; //학번
        String alert = "*올바른 이름을 입력해주세요*"; //올바르지 않은 이름 입력시 경고문

        //50000 포트에서 데이터그램 소켓 생성
        try (DatagramSocket serverSocket = new DatagramSocket(50000)) {
            System.out.println("UDP 서버가 시작되었습니다. 데이터그램 수신 대기중...");

            while (true) {
                //매 수신마다 수신 버퍼를 초기화
                byte[] receiveData = new byte[1024];

                //수신된 데이터그램을 저장할 공간 생성
                DatagramPacket receivePacket =
                        new DatagramPacket(receiveData, receiveData.length);

                //데이터그램 수신
                serverSocket.receive(receivePacket);

                //수신된 데이터(이름)를 문자열로 변환하고 출력
                String name = new String(receivePacket.getData()).trim();
                System.out.println("클라이언트로부터 수신한 이름 : " + name);


                //송신자의 IP 주소와 포트번호 가져오기
                InetAddress ipAddress = receivePacket.getAddress();
                int port = receivePacket.getPort();

                //응답 메세지 결정
                byte[] sendData;
                if ("김태현".equals(name)) {//클라이언트 쪽에서 송신한 이름이 서버가 응답해줄 학번의 학생 이름인 경우
                    //학번을 바이트 배열로 변환
                    sendData = studentId.getBytes();
                } else {
                    sendData = alert.getBytes();
                }
                //클라이언트에게 보낼 데이터그램 생성
                DatagramPacket sendPacket =
                        new DatagramPacket(sendData, sendData.length, ipAddress, port);

                //소켓을 통해 데이터그램 전송
                serverSocket.send(sendPacket);
            }// while 루프 종료, 다음 데이터그램을 기다리기 위해 루프로 돌아감
        } catch (Exception e) {
            System.err.println("서버 오류 : " + e.getMessage());
        }
    }
}
