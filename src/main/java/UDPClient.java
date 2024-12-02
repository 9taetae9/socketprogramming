import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;

public class UDPClient {
    public static void main(String[] args) throws Exception {
        //클라이언트 소켓 생성
        try (DatagramSocket clientSocket = new DatagramSocket();
             //입력 스트림 생성 (이름 입력)
             BufferedReader inFromUser =
                     new BufferedReader(new InputStreamReader(System.in))) {


            //DNS를 통해 hostname을 IP 주소로 변환
            InetAddress ipAddress = InetAddress.getByName("localhost");

            System.out.print("이름을 입력하세요 : ");
            String name = inFromUser.readLine();
            byte[] sendData = new byte[1024];
            byte[] receiveData = new byte[1024];

            sendData = name.getBytes();

            //데이터그램 생성(데이터, 데이터 길이, ip 주소, 포트 번호)
            DatagramPacket sendPacket =
                    new DatagramPacket(sendData, sendData.length, ipAddress, 50000);

            //서버로 이름 전송
            clientSocket.send(sendPacket);

            //학번 수신
            DatagramPacket receivePacket =
                    new DatagramPacket(receiveData, receiveData.length);

            //서버로부터 데이터그램 수신
            clientSocket.receive(receivePacket);

            String studentId =
                    new String(receivePacket.getData()).trim();

            System.out.println("서버로부터 수신한 학번: " + studentId);
            clientSocket.close();
        } catch (Exception e) {
            System.out.println("클라이언트 오류 : " + e.getMessage());
        }
    }
}
